package com.crumby.lib.authentication;

public interface ServerAuthenticate {
    void userSignIn(String str, String str2, String str3) throws Exception;

    String userSignInAndGetAuth(String str, String str2, String str3) throws Exception;
}
