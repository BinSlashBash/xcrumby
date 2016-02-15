/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.model.AccessToken;

public class AccessTokenResult<T> {
    private AccessToken accessToken;
    private T model;

    public AccessTokenResult(T t2, AccessToken accessToken) {
        this.model = t2;
        this.accessToken = accessToken;
    }

    public AccessToken getAccessToken() {
        return this.accessToken;
    }

    public T getModel() {
        return this.model;
    }
}

