/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public final class HttpMethod {
    public static final Set<String> METHODS = new LinkedHashSet<String>(Arrays.asList("OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "PATCH"));

    private HttpMethod() {
    }

    public static boolean hasRequestBody(String string2) {
        if (string2.equals("POST") || string2.equals("PUT") || string2.equals("PATCH") || string2.equals("DELETE")) {
            return true;
        }
        return false;
    }

    public static boolean invalidatesCache(String string2) {
        if (string2.equals("POST") || string2.equals("PATCH") || string2.equals("PUT") || string2.equals("DELETE")) {
            return true;
        }
        return false;
    }
}

