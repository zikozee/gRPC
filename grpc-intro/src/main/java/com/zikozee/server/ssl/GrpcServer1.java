package com.zikozee.server.ssl;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;

import java.io.File;
import java.io.IOException;

/**
 * @author : zikoz
 * @created : 13 Sep, 2021
 */

public class GrpcServer1 {

    public static void main(String[] args) throws IOException, InterruptedException {


        SslContext sslContext = GrpcSslContexts.configure(SslContextBuilder.
                forServer(new File("C:/Users/zikoz/Desktop/gPRC/ssl_tls/localhost.crt"),
                        new File("C:/Users/zikoz/Desktop/gPRC/ssl_tls/localhost.pem"))).build();

        Server server = NettyServerBuilder.forPort(6565)
                .sslContext(sslContext)
                .addService(new BankService())
                .build();

        server.start();

        // so server does not terminate
        server.awaitTermination();
    }
}
