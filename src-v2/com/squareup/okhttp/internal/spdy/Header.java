/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import okio.ByteString;

public final class Header {
    public static final ByteString RESPONSE_STATUS = ByteString.encodeUtf8(":status");
    public static final ByteString TARGET_AUTHORITY;
    public static final ByteString TARGET_HOST;
    public static final ByteString TARGET_METHOD;
    public static final ByteString TARGET_PATH;
    public static final ByteString TARGET_SCHEME;
    public static final ByteString VERSION;
    final int hpackSize;
    public final ByteString name;
    public final ByteString value;

    static {
        TARGET_METHOD = ByteString.encodeUtf8(":method");
        TARGET_PATH = ByteString.encodeUtf8(":path");
        TARGET_SCHEME = ByteString.encodeUtf8(":scheme");
        TARGET_AUTHORITY = ByteString.encodeUtf8(":authority");
        TARGET_HOST = ByteString.encodeUtf8(":host");
        VERSION = ByteString.encodeUtf8(":version");
    }

    public Header(String string2, String string3) {
        this(ByteString.encodeUtf8(string2), ByteString.encodeUtf8(string3));
    }

    public Header(ByteString byteString, String string2) {
        this(byteString, ByteString.encodeUtf8(string2));
    }

    public Header(ByteString byteString, ByteString byteString2) {
        this.name = byteString;
        this.value = byteString2;
        this.hpackSize = byteString.size() + 32 + byteString2.size();
    }

    public boolean equals(Object object) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (object instanceof Header) {
            object = (Header)object;
            bl3 = bl2;
            if (this.name.equals(object.name)) {
                bl3 = bl2;
                if (this.value.equals(object.value)) {
                    bl3 = true;
                }
            }
        }
        return bl3;
    }

    public int hashCode() {
        return (this.name.hashCode() + 527) * 31 + this.value.hashCode();
    }

    public String toString() {
        return String.format("%s: %s", this.name.utf8(), this.value.utf8());
    }
}

