package newboard.demo.web.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Answer {
    @Id
    @GeneratedValue
    private Long id;
    private String answer;
    @ManyToOne //Answer(Many) writer(one)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private Account writer;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private  LocalDateTime modifiedDate;

    public String formattedCreateDate(){
        return formattedDate(createDate,"yyyy년 M월 d일 a h시 m분 s초");
    }
    public String formattedModifiedeDate(){
        return formattedDate(modifiedDate,"yyyy년 M월 d일 a h시 m분 s초");
    }

    private String formattedDate(LocalDateTime dateTime, String format) {
        if(dateTime==null){
            return "";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTime.toString();
    }

}
