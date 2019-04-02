package newboard.demo.web.domain;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.InitBinder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Valid
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String accountId;
    @NotNull
    private String password;
    @NotNull
    private String name;
    private String email;
    @CreatedDate

    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
