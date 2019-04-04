package newboard.demo.web.domain;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

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
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean matchId(Long newId) {
        if (newId == null) {
            return false;
        }
        return newId.equals(id);
    }

}
