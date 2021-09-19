package com.zikozee.client.deadline;

import com.zikozee.client.rpctypes.BalanceStreamObserver;
import com.zikozee.client.rpctypes.MoneyStreamingResponse;
import com.zikozee.model.*;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author : zikoz
 * @created : 13 Sep, 2021
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeadlineClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setUp(){
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);

    }

    @Test
    void balanceTest(){
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(7)
                .build();
        try{
            Balance balance = this.blockingStub
                    .withDeadline(Deadline.after(2, TimeUnit.SECONDS)) // todo info :: setting deadline
                    .getBalance(balanceCheckRequest);
            System.out.println("Received: "+ balance.getAmount());
        }catch (StatusRuntimeException e){
            // go with default values
        }
    }

    @Test
    void withdrawTest() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(50).build();
       try{
           this.blockingStub
                   .withDeadline(Deadline.after(4, TimeUnit.SECONDS))
//                .withDeadlineAfter(4, TimeUnit.SECONDS)
                   .withdraw(withdrawRequest)
                   .forEachRemaining(money -> System.out.println("Received: " + money.getValue()));
       }catch (StatusRuntimeException e){
           //
       }
    }

    @Test
    void withdrawAsyncTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(10).setAmount(50).build();
        this.bankServiceStub.withdraw(withdrawRequest, new MoneyStreamingResponse(latch));

        latch.await();
//        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
    }

}
