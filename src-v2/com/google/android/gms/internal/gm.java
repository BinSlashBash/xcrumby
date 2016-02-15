/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 */
package com.google.android.gms.internal;

import android.database.CharArrayBuffer;

public final class gm {
    /*
     * Enabled aggressive block sorting
     */
    public static void b(String string2, CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer.data == null || charArrayBuffer.data.length < string2.length()) {
            charArrayBuffer.data = string2.toCharArray();
        } else {
            string2.getChars(0, string2.length(), charArrayBuffer.data, 0);
        }
        charArrayBuffer.sizeCopied = string2.length();
    }
}

