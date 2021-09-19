package com.zikozee.server.metadata;

import io.grpc.Metadata;

/**
 * @author : zikoz
 * @created : 19 Sep, 2021
 */

public class ServerConstants {

    public static final Metadata.Key<String> TOKEN =
            Metadata.Key.of("client-token", Metadata.ASCII_STRING_MARSHALLER);
}
