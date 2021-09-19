package com.zikozee.client.metadata;

import io.grpc.Metadata;

/**
 * @author : zikoz
 * @created : 19 Sep, 2021
 */

public class ClientConstants {

    public static final Metadata.Key<String> USER_TOKEN = Metadata.Key.of("user-token", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata METADATA = new Metadata();


    static {
        METADATA.put(Metadata.Key.of("client-token", Metadata.ASCII_STRING_MARSHALLER), "bank-client-secre");
    }

    public static Metadata getClientToken(){
        return METADATA;
    }
}
