package com.zikozee.server.deadline;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author : zikoz
 * @created : 13 Sep, 2021
 */

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(6565)
                .addService(new DeadlineService())
                .build();

        server.start();

        // so server does not terminate
        server.awaitTermination();
    }
}
