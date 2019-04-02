package newboard.demo.web.controller;

import newboard.demo.web.domain.Question;
import newboard.demo.web.domain.QuestionRepository;
import newboard.demo.web.domain.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("board")
public class DashBoardController {
    @Autowired
    QuestionRepository boardRepository;

    @GetMapping
    public String board(Model model){
        List<Question> questions = boardRepository.findAll();
        model.addAttribute("questions", questions);
        return "dashBoard";
    }

    @GetMapping("/write")
    public String question(HttpSession session){
        if(!HttpSessionUtils.isLogin(session)){
            return "login";
        }
        return "question";
    }

    @PostMapping("/write")
    public String createQuestion(@RequestParam String title, @RequestParam String content,HttpSession session){
        Question question = Question.builder()
                .title(title)
                .content(content)
                .writer(HttpSessionUtils.getLoginAccount(session))
                .build();
        boardRepository.save(question);
        return "redirect:/board";
    }







}
