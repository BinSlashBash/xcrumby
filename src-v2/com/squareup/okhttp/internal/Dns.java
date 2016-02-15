/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface Dns {
    public static final Dns DEFAULT = new Dns(){

        @Override
        public InetAddress[] getAllByName(String string2) throws UnknownHostException {
            if (string2 == null) {
                throw new UnknownHostException("host == null");
            }
            return InetAddress.getAllByName(string2);
        }
    };

    public InetAddress[] getAllByName(String var1) throws UnknownHostException;

}

