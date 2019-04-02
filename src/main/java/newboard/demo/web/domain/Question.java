package newboard.demo.web.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
