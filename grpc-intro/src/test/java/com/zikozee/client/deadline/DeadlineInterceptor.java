package com.zikozee.client.deadline;

import io.grpc.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : zikoz
 * @created : 19 Sep, 2021
 */

public class DeadlineInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        Deadline deadline = callOptions.getDeadline();  // in case deadline is set in a particular calling method, it picks up the deadline set
        if(Objects.isNull(deadline))
            callOptions = callOptions.withDeadline(Deadline.after(4, TimeUnit.SECONDS));

        return channel.newCall(methodDescriptor, callOptions);
    }
}
