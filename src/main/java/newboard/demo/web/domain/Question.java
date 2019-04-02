package newboard.demo.web.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    Long id;

    String title;
    @Lob
    String content;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    Account writer;

    @CreatedDate
    LocalDateTime createDate;

    @LastModifiedDate
    LocalDateTime modifiedDate;

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
