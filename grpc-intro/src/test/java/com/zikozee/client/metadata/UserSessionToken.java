package com.zikozee.client.metadata;

import io.grpc.CallCredentials;
import io.grpc.Metadata;

import java.util.concurrent.Executor;

/**
 * @author : zikoz
 * @created : 19 Sep, 2021
 */

public class UserSessionToken extends CallCredentials {

    private String jwt;

    public UserSessionToken(String jwt){
        this.jwt = jwt;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {

        executor.execute(() -> {
            Metadata metadata = new Metadata();
            metadata.put(ClientConstants.USER_TOKEN, this.jwt);
            metadataApplier.apply(metadata);
            //metadataApplier.fail()
        });
    }

    @Override
    public void thisUsesUnstableApi() {
        //
    }
}
