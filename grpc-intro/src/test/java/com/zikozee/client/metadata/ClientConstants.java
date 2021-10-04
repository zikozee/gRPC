package com.zikozee.client.metadata;

import com.zikozee.model.WithdrawalError;
import io.grpc.Metadata;
import io.grpc.protobuf.ProtoUtils;

/**
 * @author : zikoz
 * @created : 19 Sep, 2021
 */

public class ClientConstants {

    public static final Metadata.Key<WithdrawalError> WITHDRAWAL_ERROR_KEY = ProtoUtils.keyForProto(WithdrawalError.getDefaultInstance());
    public static final Metadata.Key<String> USER_TOKEN = Metadata.Key.of("user-token", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata METADATA = new Metadata();


    static {
        METADATA.put(Metadata.Key.of("client-token", Metadata.ASCII_STRING_MARSHALLER), "bank-client-secre");
    }

    public static Metadata getClientToken(){
        return METADATA;
    }
}
