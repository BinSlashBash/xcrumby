package com.google.android.gms.common.api;

public final class Scope {
    private final String Bu;

    public Scope(String scopeUri) {
        this.Bu = scopeUri;
    }

    public String en() {
        return this.Bu;
    }
}
