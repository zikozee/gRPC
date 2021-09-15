package com.zikozee.client.rpctypes;

import com.zikozee.model.TransferResponse;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

/**
 * @author : zikoz
 * @created : 14 Sep, 2021
 */

public class TransferStreamingResponse implements StreamObserver<TransferResponse> {

    private final CountDownLatch latch;

    public TransferStreamingResponse(CountDownLatch latch) {
        this.latch = latch;
    }


    @Override
    public void onNext(TransferResponse transferResponse) {
        System.out.println("Status: " + transferResponse.getStatus());
        transferResponse.getAccountsList()
                .stream()
                .map(account -> account.getAccountNumber() + " : " + account.getAmount())
                .forEach(System.out::println);
        System.out.println("-----------------------------------");
    }

    @Override
    public void onError(Throwable throwable) {
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        this.latch.countDown();
    }
}
