package com.zikozee.server.loadbalancing;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author : zikoz
 * @created : 13 Sep, 2021
 */

public class GrpcServer2 {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(7575)
                .addService(new BankService())
                .build();

        server.start();

        // so server does not terminate
        server.awaitTermination();
    }
}
