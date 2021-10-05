package com.grpcflix.movie.service;

import com.grpcflix.movie.repository.MovieRepository;
import com.zikozee.grpcflix.movie.MovieDto;
import com.zikozee.grpcflix.movie.MovieSearchRequest;
import com.zikozee.grpcflix.movie.MovieSearchResponse;
import com.zikozee.grpcflix.movie.MovieServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Ezekiel Eromosei
 * @created : 05 Oct, 2021
 */

@GrpcService
@RequiredArgsConstructor
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {

    private final MovieRepository repository;

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        List<MovieDto> movieDtoList = this.repository.getMovieByGenreOrderByYearDesc(request.getGenre().toString())
                .stream()
                .map(movie -> MovieDto.newBuilder()
                        .setTitle(movie.getTitle())
                        .setYear(movie.getYear())
                        .setRating(movie.getRating())
                        .build())
                .collect(Collectors.toList());


        responseObserver.onNext(MovieSearchResponse.newBuilder().addAllMovie(movieDtoList).build());
        responseObserver.onCompleted();
    }
}
