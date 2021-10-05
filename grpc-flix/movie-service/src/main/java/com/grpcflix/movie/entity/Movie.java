package com.grpcflix.movie.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;


/**
 * @author : Ezekiel Eromosei
 * @created : 05 Oct, 2021
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Movie {

    @Id
    private int id;
    private String title;
    private int year;
    private double rating;
    private String genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
