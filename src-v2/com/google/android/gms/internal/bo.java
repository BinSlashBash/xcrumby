/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.bi;
import com.google.android.gms.internal.bj;
import com.google.android.gms.internal.dh;
import com.google.android.gms.internal.dj;
import com.google.android.gms.internal.du;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class bo {
    public static List<String> a(JSONObject object, String object2) throws JSONException {
        if ((object = object.optJSONArray((String)object2)) != null) {
            object2 = new ArrayList(object.length());
            for (int i2 = 0; i2 < object.length(); ++i2) {
                object2.add(object.getString(i2));
            }
            return Collections.unmodifiableList(object2);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void a(Context context, String string2, dh dh2, String string3, boolean bl2, List<String> object) {
        String string4 = bl2 ? "1" : "0";
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            String string5;
            object = string5 = ((String)iterator.next()).replaceAll("@gw_adlocid@", string3).replaceAll("@gw_adnetrefresh@", string4).replaceAll("@gw_qdata@", dh2.qt.nh).replaceAll("@gw_sdkver@", string2).replaceAll("@gw_sessid@", dj.qK).replaceAll("@gw_seqnum@", dh2.pj);
            if (dh2.nx != null) {
                object = string5.replaceAll("@gw_adnetid@", dh2.nx.mX).replaceAll("@gw_allocid@", dh2.nx.mZ);
            }
            new du(context, string2, (String)object).start();
        }
        return;
    }
}

