package com.zikozee.server;

import com.zikozee.model.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

/**
 * @author : zikoz
 * @created : 13 Sep, 2021
 */

public class BankService extends BankServiceGrpc.BankServiceImplBase{

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {

        int accountNumber = request.getAccountNumber();

        Balance balance = Balance.newBuilder()
                .setAmount(AccountDatabase.getBalance(accountNumber))
                .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    @Override //Stream
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount(); //10, 20, 30...
        int balance = AccountDatabase.getBalance(accountNumber);

        if(balance < amount) {
            Status status = Status.FAILED_PRECONDITION.withDescription("Not enough money, You hae only: " + balance);
            responseObserver.onError(status.asRuntimeException());
        }

        //when all validations passed

        //delivering 10 USD 4 times
        for (int i = 0; i < (amount/10); i++) {
            Money money = Money.newBuilder().setValue(10).build();
            responseObserver.onNext(money);
            AccountDatabase.deductBalance(accountNumber, 10);
        }

        responseObserver.onCompleted();
    }
}
