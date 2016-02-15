/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.webkit.CookieManager
 */
package com.crumby.lib.widget.thirdparty;

import java.io.IOException;
import java.io.Serializable;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebkitCookieManagerProxy
extends CookieManager {
    private android.webkit.CookieManager webkitCookieManager = android.webkit.CookieManager.getInstance();

    public WebkitCookieManagerProxy() {
        this(null, null);
    }

    public WebkitCookieManagerProxy(CookieStore cookieStore, CookiePolicy cookiePolicy) {
        super(null, cookiePolicy);
    }

    @Override
    public Map<String, List<String>> get(URI serializable, Map<String, List<String>> object) throws IOException {
        if (serializable == null || object == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        object = serializable.toString();
        serializable = new HashMap();
        if ((object = this.webkitCookieManager.getCookie((String)object)) != null) {
            serializable.put("Cookie", Arrays.asList(object));
        }
        return serializable;
    }

    @Override
    public CookieStore getCookieStore() {
        throw new UnsupportedOperationException();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void put(URI object, Map<String, List<String>> map) throws IOException {
        if (object != null && map != null) {
            object = object.toString();
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String string2 = iterator.next();
                if (string2 == null || !string2.equalsIgnoreCase("Set-Cookie2") && !string2.equalsIgnoreCase("Set-Cookie")) continue;
                for (String string3 : map.get(string2)) {
                    this.webkitCookieManager.setCookie((String)object, string3);
                }
            }
        }
    }
}

