package com.zikozee.server.metadata;

import com.google.common.util.concurrent.Uninterruptibles;
import com.zikozee.model.*;
import com.zikozee.server.rpctypes.AccountDatabase;
import com.zikozee.server.rpctypes.CashStreamingRequest;
import io.grpc.Context;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * @author : zikoz
 * @created : 13 Sep, 2021
 */

public class MetadataService extends BankServiceGrpc.BankServiceImplBase{

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {

        int accountNumber = request.getAccountNumber();
        int amount = AccountDatabase.getBalance(accountNumber);

        UserRole userRole = ServerConstants.CTX_USER_ROLE.get();
        UserRole userRole1 = ServerConstants.CTX_USER_ROLE1.get();
        amount = UserRole.PRIME == userRole ? amount : (amount -15);

        System.out.println(userRole + " : " + userRole1);

        Balance balance = Balance.newBuilder()
                .setAmount(amount)
                .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    @Override //Stream
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount(); //10, 20, 30...
        int balance = AccountDatabase.getBalance(accountNumber);

        if (amount <10 || amount % 10 != 0){
            Metadata metadata = new Metadata();
            Metadata.Key<WithdrawalError> errorKey = ProtoUtils.keyForProto(WithdrawalError.getDefaultInstance());
            WithdrawalError withdrawalError = WithdrawalError.newBuilder().setAmount(balance).setErrorMessage(ErrorMessage.ONLY_TEN_MULTIPLES).build();
            metadata.put(errorKey, withdrawalError);
            responseObserver.onError(Status.FAILED_PRECONDITION.asRuntimeException(metadata));
            return;
        }

        if(balance < amount) {
            Metadata metadata = new Metadata();
            Metadata.Key<WithdrawalError> errorKey = ProtoUtils.keyForProto(WithdrawalError.getDefaultInstance());
            WithdrawalError withdrawalError = WithdrawalError.newBuilder().setAmount(balance).setErrorMessage(ErrorMessage.INSUFFICIENT_BALANCE).build();
            metadata.put(errorKey, withdrawalError);
            responseObserver.onError(Status.FAILED_PRECONDITION.asRuntimeException(metadata));
        }

        //when all validations passed

        //delivering 10 USD 4 times
        for (int i = 0; i < (amount/10); i++) {
            Money money = Money.newBuilder().setValue(10).build();
            //simulate time-consuming call
//            Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
            responseObserver.onNext(money);
            System.out.println("Delivered $10");
            AccountDatabase.deductBalance(accountNumber, 10);

//            if(!Context.current().isCancelled()){  //todo info context used to check if client is still alive
//                responseObserver.onNext(money);
//                System.out.println("Delivered $10");
//                AccountDatabase.deductBalance(accountNumber, 10);
//            }else {
//                break;
//            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequest(responseObserver);
    }
}
