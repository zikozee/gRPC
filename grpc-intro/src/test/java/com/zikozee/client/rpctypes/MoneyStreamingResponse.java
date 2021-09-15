package com.zikozee.client.rpctypes;

import com.zikozee.model.Money;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

/**
 * @author : zikoz
 * @created : 13 Sep, 2021
 */

public class MoneyStreamingResponse implements StreamObserver<Money> {

    private CountDownLatch latch;

    public MoneyStreamingResponse(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(Money money) {
        System.out.println("Received async: " + money.getValue());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getLocalizedMessage());
        latch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("Server is done !!!");
        latch.countDown();
    }
}
