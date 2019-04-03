package newboard.demo.web.controller;

import newboard.demo.web.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


}
