/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.ay;
import java.util.Map;

class d
implements DataLayer.b {
    private final Context kI;

    public d(Context context) {
        this.kI = context;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void y(Map<String, Object> object) {
        Object v2 = object.get("gtm.url");
        object = v2 == null && (object = object.get("gtm")) != null && object instanceof Map ? ((Map)object).get("url") : v2;
        if (object == null || !(object instanceof String) || (object = Uri.parse((String)((String)object)).getQueryParameter("referrer")) == null) {
            return;
        }
        ay.e(this.kI, (String)object);
    }
}

