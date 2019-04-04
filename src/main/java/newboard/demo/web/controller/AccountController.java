package newboard.demo.web.controller;

import newboard.demo.web.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/join")
    public String createAccount(){
        return "join";
    }

    @PostMapping("/join")
    public String join(Account account){

        Account newAccount =Account.builder()
                .accountId(account.getAccountId())
                .password(account.getPassword())
                .name(account.getName())
                .email(account.getEmail())
                .build();
        System.out.println(newAccount.toString());
        accountRepository.save(newAccount);

        return "index";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){

        if(HttpSessionUtils.getLoginAccount(session)==null){
            System.out.println("로그인을 해야 로그아웃이 가능합니다.");
            return "index";
        }

        session.removeAttribute(HttpSessionUtils.ACCOUNT_SESSION_KEY);
        System.out.println("로그아웃 되었습니다.");

        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String accountId, @RequestParam String password,HttpSession session,Model model){
        Account loginAccount = accountRepository.findByAccountId(accountId);

        if(loginAccount==null){
            model.addAttribute("fail","유효하지 않은 아이디 입니다.");
            return "login";
        }
        if(!loginAccount.getPassword().equals(password)){
            model.addAttribute("fail","유효하지 않은 비밀번호 입니다.");
            return "login";
        }
        System.out.println(loginAccount.getName()+"님 환영합니다!");
        session.setAttribute(HttpSessionUtils.ACCOUNT_SESSION_KEY,loginAccount);
        return "index";
    }

    @GetMapping("/update/{id}")
    public String createUpdate(@PathVariable Long id,HttpSession session,Model model){

        if(HttpSessionUtils.getLoginAccount(session)==null){
            model.addAttribute("fail","로그인 먼저하세요");
            return "login";
        }

        Account account = accountRepository.findById(id).get();

        if(account.getId()!=HttpSessionUtils.getLoginAccount(session).getId()){
            System.out.println("예외사항:타인의 정보를 수정하려함");
            return "index";
        }
        return "update";
    }

    @PutMapping("/update/{id}")
    public String updateAccount(@PathVariable Long id,Account update,HttpSession session){
        Account origin = HttpSessionUtils.getLoginAccount(session);

        origin.setAccountId(update.getAccountId());
        origin.setPassword(update.getPassword());
        origin.setName(update.getName());
        origin.setEmail(update.getEmail());
        origin.setModifiedDate(LocalDateTime.now());

        accountRepository.save(origin);

        session.removeAttribute(HttpSessionUtils.ACCOUNT_SESSION_KEY);
        session.setAttribute(HttpSessionUtils.ACCOUNT_SESSION_KEY,origin);

        System.out.println(HttpSessionUtils.getLoginAccount(session).toString());

        return "index";
    }

    @GetMapping("/delete")
    public String getDelete(HttpSession session,Model model){

        if(!HttpSessionUtils.isLogin(session)){
            model.addAttribute("fail","회원탈퇴를 위해서는 로그인을 먼저해주세요.");
            return "login";
        }
        return "delete";

    }
    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam String password, HttpSession session, Model model){

        Account account = accountRepository.findById(id).get();

        if(!account.getPassword().equals(password)){
            model.addAttribute("failDelete","비밀번호가 일치하지 않습니다.");
            return "delete";
        }

        session.removeAttribute(HttpSessionUtils.ACCOUNT_SESSION_KEY);
        accountRepository.deleteById(id);

        System.out.println("회원을 탈퇴했습니다.");
        return "index";
    }

}
