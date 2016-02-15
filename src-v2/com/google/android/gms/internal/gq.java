/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class gq {
    /*
     * Enabled aggressive block sorting
     */
    public static void a(StringBuilder stringBuilder, HashMap<String, String> hashMap) {
        stringBuilder.append("{");
        Iterator<String> iterator = hashMap.keySet().iterator();
        boolean bl2 = true;
        do {
            if (!iterator.hasNext()) {
                stringBuilder.append("}");
                return;
            }
            String string2 = iterator.next();
            if (!bl2) {
                stringBuilder.append(",");
            } else {
                bl2 = false;
            }
            String string3 = hashMap.get(string2);
            stringBuilder.append("\"").append(string2).append("\":");
            if (string3 == null) {
                stringBuilder.append("null");
                continue;
            }
            stringBuilder.append("\"").append(string3).append("\"");
        } while (true);
    }
}

