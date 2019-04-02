package newboard.demo.web.controller;

import newboard.demo.web.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("board")
public class DashBoardController {
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping
    public String board(Model model) {
        List<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "dashBoard";
    }

    @GetMapping("/write")
    public String question(HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return "login";
        }
        return "question";
    }

    @PostMapping("/write")
    public String createQuestion(@RequestParam String title, @RequestParam String content, HttpSession session) {
        Question question = Question.builder()
                .title(title)
                .content(content)
                .writer(HttpSessionUtils.getLoginAccount(session))
                .build();
        questionRepository.save(question);
        return "redirect:/board";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).get();
        model.addAttribute("question", question);
        return "show";
    }

    public Result isValid(Long id, HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return Result.fail("로그인부터 하세요");
        }
        Account loginAccount = HttpSessionUtils.getLoginAccount(session);
        Question question = questionRepository.findById(id).get();

        if (!loginAccount.equals(question.getWriter())) {
            return Result.fail("다른 사용자가 작성한 글은 수정/삭제 하싷 수 없습니다.");
        }
        return Result.ok();
    }

    @GetMapping("/update/{id}")
    public String updateQuetion(@PathVariable Long id, HttpSession session, Model model) {
        Result valid = isValid(id, session);

        if (!valid.isSuccess()) {
            model.addAttribute("fail", valid.getErrorMessage());
            return "login";
        }

        Question question = questionRepository.findById(id).get();
        model.addAttribute("updateQuestion", question);
        return "updateQuestion";
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable Long id, Question update, HttpSession session, Model model) {
        Question origin = questionRepository.findById(id).get();
        origin.setTitle(update.getTitle());
        origin.setContent(update.getContent());
        Question updateQ = questionRepository.save(origin);
        model.addAttribute("question", updateQ);
        return "show";
    }

    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session, Model model) {

        Result valid = isValid(id, session);

        if (!valid.isSuccess()) {
            model.addAttribute("fail", valid.getErrorMessage());
            return "login";
        }

        questionRepository.deleteById(id);
        return "redirect:/board";
    }
}

