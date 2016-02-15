package com.uservoice.uservoicesdk.model;

public class AccessTokenResult<T> {
    private AccessToken accessToken;
    private T model;

    public AccessTokenResult(T model, AccessToken accessToken) {
        this.model = model;
        this.accessToken = accessToken;
    }

    public T getModel() {
        return this.model;
    }

    public AccessToken getAccessToken() {
        return this.accessToken;
    }
}
