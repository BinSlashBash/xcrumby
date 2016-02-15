/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.router;

import java.util.Iterator;
import java.util.List;

public class FragmentLinkSqlBuilder {
    public static String build(List<Integer> object) {
        if (object.isEmpty()) {
            return null;
        }
        Object object2 = "(";
        Iterator<Integer> iterator = object.iterator();
        object = object2;
        while (iterator.hasNext()) {
            object2 = iterator.next();
            object = (String)object + object2 + ",";
        }
        object = object.substring(0, object.length() - 1) + ")";
        return "SELECT * FROM fragment_links_master WHERE id IN " + (String)object;
    }
}

