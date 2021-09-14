package com.zikozee.server;

import com.zikozee.model.TransferRequest;
import com.zikozee.model.TransferResponse;
import com.zikozee.model.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author : zikoz
 * @created : 14 Sep, 2021
 */

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {


    //POST  This will be called from the Rest controller

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferStreamingRequest(responseObserver);
    }
}
