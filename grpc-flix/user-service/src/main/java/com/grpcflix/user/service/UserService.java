package com.grpcflix.user.service;

import com.grpcflix.user.repository.UserRepository;
import com.zikozee.grpcflix.common.Genre;
import com.zikozee.grpcflix.user.UserGenreUpdateRequest;
import com.zikozee.grpcflix.user.UserResponse;
import com.zikozee.grpcflix.user.UserSearchRequest;
import com.zikozee.grpcflix.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Ezekiel Eromosei
 * @created : 04 Oct, 2021
 */

@GrpcService
@RequiredArgsConstructor
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository repository;


    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.repository.findById(request.getLoginId())
                .ifPresent(user -> builder.setName(user.getName())
                        .setLoginId(user.getLogin())
                        .setGenre(Genre.valueOf(user.getGenre().toUpperCase())));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Transactional
    @Override
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.repository.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().toString());
                    builder.setName(user.getName())
                            .setLoginId(user.getLogin())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
