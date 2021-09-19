package com.zikozee.server.metadata;

import io.grpc.*;

import java.util.Objects;

/**
 * @author : zikoz
 * @created : 19 Sep, 2021
 */

public class AuthInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String clientToken = metadata.get(ServerConstants.USER_TOKEN);
        if(this.validate(clientToken)){
            serverCallHandler.startCall(serverCall, metadata);
        }else{
            Status status = Status.UNAUTHENTICATED.withDescription("invalid token/expired token");
            serverCall.close(status, metadata);
        }
        return new ServerCall.Listener<ReqT>() {};
    }

    private boolean validate(String token){
        return Objects.nonNull(token) && token.equals("user-secret-3");
    }
}
