package com.zikozee.client.metadata;

import com.zikozee.client.deadline.DeadlineInterceptor;
import com.zikozee.client.rpctypes.MoneyStreamingResponse;
import com.zikozee.model.Balance;
import com.zikozee.model.BalanceCheckRequest;
import com.zikozee.model.BankServiceGrpc;
import com.zikozee.model.WithdrawRequest;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author : zikoz
 * @created : 13 Sep, 2021
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MetadataClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setUp(){
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .intercept(MetadataUtils.newAttachHeadersInterceptor(ClientConstants.getClientToken()))
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
        for (int i = 0; i < 20; i++) {
            try{
                int random = ThreadLocalRandom.current().nextInt(1, 4);
                System.out.println("Random : " + random);
                Balance balance = this.blockingStub
                        .withCallCredentials(new UserSessionToken("user-secret-" + random + ":standard"))
                        .getBalance(balanceCheckRequest);
                System.out.println("Received: "+ balance.getAmount());
            }catch (StatusRuntimeException e){
                e.printStackTrace();
            }
        }

    }

    @Test
    void withdrawTest() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(50).build();
       try{
           this.blockingStub
                   .withDeadline(Deadline.after(2, TimeUnit.SECONDS)) //Overriding global deadline
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
