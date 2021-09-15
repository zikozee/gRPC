package com.zikozee.client.loadbalancing;

import io.grpc.Attributes;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;

import javax.annotation.Nullable;
import java.net.URI;

/**
 * @author : zikoz
 * @created : 15 Sep, 2021
 */

public class TempNameResolverProvider extends NameResolverProvider {
    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected int priority() {
        return 5;
    }

    @Override
    public String getDefaultScheme() {
        return "dns";
    }

    @Nullable
    @Override
    public NameResolver newNameResolver(URI targetUri, Attributes params) {
        System.out.println("looking for service: " + targetUri.toString());
        return new TempNameResolver(targetUri.toString());
    }
}
