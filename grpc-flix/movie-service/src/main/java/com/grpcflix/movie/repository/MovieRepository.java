package com.grpcflix.movie.repository;

import com.grpcflix.movie.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @created : 05 Oct, 2021
 */

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {

    List<Movie> getMovieByGenreOrderByYearDesc(String genre);
}
