package com.zikozee.client.loadbalancing;

import com.zikozee.client.rpctypes.BalanceStreamObserver;
import com.zikozee.model.Balance;
import com.zikozee.model.BalanceCheckRequest;
import com.zikozee.model.BankServiceGrpc;
import com.zikozee.model.DepositRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolverRegistry;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : zikoz
 * @created : 15 Sep, 2021
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientSideLoadBalancingTest {



    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;



    @BeforeAll
    public void setUp(){
        ServiceRegistry.register("bank-service", List.of("localhost:6565", "localhost:7575"));
        NameResolverRegistry.getDefaultRegistry().register(new TempNameResolverProvider());

        ManagedChannel managedChannel = ManagedChannelBuilder
//                .forAddress("localhost", 8585)
                .forTarget("bank-service")
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);

    }

    @Test
    void balanceTest(){
        for (int i = 1; i < 100; i++) {
            BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(ThreadLocalRandom.current().nextInt(1, 11))
                    .build();
            Balance balance = this.blockingStub.getBalance(balanceCheckRequest);
            System.out.println("Received: "+ balance.getAmount());
        }
    }

    @Test
    void cashStreamingRequest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<DepositRequest> streamObserver = this.bankServiceStub.cashDeposit(new BalanceStreamObserver(latch));
        for (int i = 0; i < 10; i++) {
            DepositRequest depositRequest = DepositRequest.newBuilder().setAccountNumber(8).setAmount(10).build();
            System.out.println("adding " + depositRequest.getAmount() + " to current balance");
            streamObserver.onNext(depositRequest);
        }
        streamObserver.onCompleted();
        latch.await();
    }
}
