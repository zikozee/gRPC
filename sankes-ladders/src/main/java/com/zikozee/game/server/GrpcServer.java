package com.zikozee.game.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author : zikoz
 * @created : 15 Sep, 2021
 */

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(6565)
                .addService(new GameService())
                .build();

        server.start();

        server.awaitTermination();
    }
}
