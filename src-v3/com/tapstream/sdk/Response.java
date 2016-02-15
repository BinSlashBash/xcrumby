package com.tapstream.sdk;

public class Response {
    public String data;
    public String message;
    public int status;

    public Response(int i, String str, String str2) {
        this.status = i;
        this.message = str;
        this.data = str2;
    }
}
