package com.zikozee.server;

import com.zikozee.model.Balance;
import com.zikozee.model.DepositRequest;
import io.grpc.stub.StreamObserver;

/**
 * @author : zikoz
 * @created : 14 Sep, 2021
 */

public class CashStreamingRequest implements StreamObserver<DepositRequest> {

    private final StreamObserver<Balance> balanceStreamObserver;
    private int accountBalance;

    public CashStreamingRequest(StreamObserver<Balance> balanceStreamObserver) {
        this.balanceStreamObserver = balanceStreamObserver;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {
        int accountNumber = depositRequest.getAccountNumber();
        int amount = depositRequest.getAmount();
        accountBalance = AccountDatabase.addbalance(accountNumber, amount); // keeps adding on every onNext call
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        Balance balance = Balance.newBuilder().setAmount(this.accountBalance).build();
        this.balanceStreamObserver.onNext(balance); // final balance added
        this.balanceStreamObserver.onCompleted();

    }
}
