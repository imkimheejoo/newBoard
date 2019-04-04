package newboard.demo.web.controller;

import newboard.demo.web.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    public Result isValid(Long id,HttpSession session){
        if(!HttpSessionUtils.isLogin(session)){
            return Result.fail("로그인을 해야 수정/삭제 하실 수 있습니다.");
        }
        Account loginAccount = HttpSessionUtils.getLoginAccount(session);
        Answer answer = answerRepository.findById(id).get();
        if(!loginAccount.equals(answer.getWriter())){
            return Result.fail("다른 사용자의 답변은 수정/삭제 하실 수 없습니다.");
        }
        return Result.ok();
    }

    @GetMapping("/update/{id}")
    public String updateAnswer(@PathVariable Long id,HttpSession session,Model model){
        Result valid = isValid(id,session);
        if(!valid.isSuccess()){
            model.addAttribute("fail",valid.getErrorMessage());
            return "login";
        }
        Answer answer = answerRepository.findById(id).get();
        model.addAttribute("origin",answer);
        return "updateAnswer";
    }
    @PutMapping("/update/{questionId}/{id}")
    public String update(@PathVariable Long questionId, @PathVariable Long id,@RequestParam String answer){
        Answer origin = answerRepository.findById(id).get();
        origin.setAnswer(answer);
        answerRepository.save(origin);

        return "redirect:/board/{questionId}";
    }

    @GetMapping("/delete/{questionId}/{id}")
    public String deleteAnswer(@PathVariable Long questionId,@PathVariable Long id, HttpSession session,Model model){
        Result valid = isValid(id,session);
        if(!valid.isSuccess()){
            model.addAttribute("fail",valid.getErrorMessage());
            return "login";
        }
        answerRepository.delete(answerRepository.findById(id).get());

        return "redirect:/board/{questionId}";
    }

}
