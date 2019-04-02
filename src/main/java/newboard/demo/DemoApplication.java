package newboard.demo;

import newboard.demo.web.domain.Account;
import newboard.demo.web.domain.AccountRepository;
import newboard.demo.web.domain.Question;
import newboard.demo.web.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing

public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountRepository accountRepository;
            @Autowired
            QuestionRepository questionRepository;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account account = Account.builder()
                        .accountId("user")
                        .email("user@email.com")
                        .name("유저")
                        .password("pass")
                        .build();

                Account savedAccount = accountRepository.save(account);

                Account account2 = Account.builder()
                        .accountId("user2")
                        .email("user2@email.com")
                        .name("유저2")
                        .password("pass2")
                        .build();

                accountRepository.save(account2);

                Question question = Question.builder()
                        .title("제목")
                        .content("내용")
                        .writer(savedAccount)
                        .build();

                questionRepository.save(question);

            }
        };
    }
}
