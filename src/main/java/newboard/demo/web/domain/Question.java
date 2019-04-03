package newboard.demo.web.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    @Lob
    private String content;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private Account writer;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "question")
    private List<Answer> answerList;


    public String formattedCreateDate() {
        return formattedLocalDateTime(createDate, "yyyy년 M월 d일 a h시 m분 s초");
    }

    public String formattedModifiedDate() {
        return formattedLocalDateTime(modifiedDate, "yyyy년 M월 d일 a h시 m분 s초");
    }
    public String formattedLocalDateTime(LocalDateTime dateTime, String format) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(dateTimeFormatter);
    }
}
