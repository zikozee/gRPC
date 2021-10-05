package com.grpcflix.aggregator.controller;

import com.grpcflix.aggregator.dto.RecommendedMovie;
import com.grpcflix.aggregator.dto.UserGenre;
import com.grpcflix.aggregator.service.UserMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @created : 05 Oct, 2021
 */

@RestController
@RequiredArgsConstructor
public class AggregatorController {

    private final UserMovieService userMovieService;

    @GetMapping(path="user/{loginId}")
    public ResponseEntity<List<RecommendedMovie>> getMovies(@PathVariable("loginId") String loginId){
        return new ResponseEntity<>(userMovieService.getUserMovieSuggestions(loginId), HttpStatus.OK);
    }

    @PutMapping(path = "user")
    public void setUserGenre(@RequestBody UserGenre userGenre){
        this.userMovieService.setUserGenre(userGenre);
    }
}
