package newboard.demo.web.domain;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Valid
public class Account extends AbstractEntity {

    private String accountId;
    private String password;
    private String name;
    private String email;

    public boolean matchPassword(String input){
        return password.equals(input);
    }

}
