package newboard.demo.web.controller;

import newboard.demo.web.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/write/{id}")
    public String createAnswer(@PathVariable Long id, @RequestParam String answer, Model model, HttpSession session){
        if(!HttpSessionUtils.isLogin(session)){
            model.addAttribute("fail","로그인을 해야 답변을 다실 수 있습니다.");
            return "login";
        }
        Question question = questionRepository.findById(id).get();
        Answer newAnswer=Answer.builder()
                .answer(answer)
                .writer(HttpSessionUtils.getLoginAccount(session))
                .question(question)
                .build();
        answerRepository.save(newAnswer);
        return "redirect:/board/{id}";
    }

    @GetMapping("/update/{id}")
    public String updateAnswer(@PathVariable Long id,HttpSession session,Model model){
        if(!HttpSessionUtils.isLogin(session)){
            model.addAttribute("fail","로그인을 해야 수정 하실 수 있습니다.");
            return "login";
        }
        Account loginAccount = HttpSessionUtils.getLoginAccount(session);
        Answer answer = answerRepository.findById(id).get();
        if(!loginAccount.equals(answer.getWriter())){
            model.addAttribute("fail","다른사용자의 답변은 수정 하실 수 없습니다.");
            return "login";
        }
        model.addAttribute("origin",answer);
        return "updateAnswer";
    }
    @GetMapping("/delete/{questionId}/{id}")
    public String deleteAnswer(@PathVariable Long questionId,@PathVariable Long id, HttpSession session,Model model){
        if(!HttpSessionUtils.isLogin(session)){
            model.addAttribute("fail","로그인을 해야 삭제 하실 수 있습니다.");
            return "login";
        }
        Account loginAccount = HttpSessionUtils.getLoginAccount(session);
        Answer answer = answerRepository.findById(id).get();

        if(!loginAccount.equals(answer.getWriter())){
            model.addAttribute("fail","다른사용자의 답변은 삭제 하실 수 없습니다.");
            return "login";
        }
        answerRepository.delete(answer);

        return "redirect:/board/{questionId}";
    }

}
