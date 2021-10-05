package com.grpcflix.user.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author : Ezekiel Eromosei
 * @created : 04 Oct, 2021
 */

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {

    @Id
    private String login;
    private String name;
    private String genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
