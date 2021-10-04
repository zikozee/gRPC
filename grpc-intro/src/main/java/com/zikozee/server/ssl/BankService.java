package com.zikozee.server.ssl;

import com.zikozee.model.*;
import com.zikozee.server.loadbalancing.CashStreamingRequest;
import com.zikozee.server.rpctypes.AccountDatabase;
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
        System.out.println("Received the request for " + accountNumber );

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

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequest(responseObserver);
    }
}
