package com.grpcflix.aggregator.service;

import com.grpcflix.aggregator.dto.RecommendedMovie;
import com.grpcflix.aggregator.dto.UserGenre;
import com.zikozee.grpcflix.common.Genre;
import com.zikozee.grpcflix.movie.MovieSearchRequest;
import com.zikozee.grpcflix.movie.MovieSearchResponse;
import com.zikozee.grpcflix.movie.MovieServiceGrpc;
import com.zikozee.grpcflix.user.UserGenreUpdateRequest;
import com.zikozee.grpcflix.user.UserResponse;
import com.zikozee.grpcflix.user.UserSearchRequest;
import com.zikozee.grpcflix.user.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Ezekiel Eromosei
 * @created : 05 Oct, 2021
 */

@Service
//@RequiredArgsConstructor
public class UserMovieService {


    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;

    public List<RecommendedMovie> getUserMovieSuggestions(String loginId){
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId).build();
        UserResponse userResponse = this.userStub.getUserGenre(userSearchRequest);

        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder().setGenre(userResponse.getGenre()).build();
        MovieSearchResponse movieSearchResponse = this.movieStub.getMovies(movieSearchRequest);
        return movieSearchResponse.getMovieList().stream()
                .map(movieDto -> new RecommendedMovie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRating()))
                .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre){
        UserGenreUpdateRequest updateRequest = UserGenreUpdateRequest.newBuilder()
                .setLoginId(userGenre.getLoginId())
                .setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
                .build();
        UserResponse userResponse = this.userStub.updateUserGenre(updateRequest);
    }
}
