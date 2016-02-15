/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.authentication;

public interface ServerAuthenticate {
    public void userSignIn(String var1, String var2, String var3) throws Exception;

    public String userSignInAndGetAuth(String var1, String var2, String var3) throws Exception;
}

