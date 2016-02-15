package com.uservoice.uservoicesdk.rest;

public abstract class Callback<T> {
    public abstract void onError(RestResult restResult);

    public abstract void onModel(T t);
}
