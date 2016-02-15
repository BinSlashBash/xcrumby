/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.bi;
import com.google.android.gms.internal.bo;
import com.google.android.gms.internal.dw;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class bj {
    public final List<bi> nc;
    public final long nd;
    public final List<String> ne;
    public final List<String> nf;
    public final List<String> ng;
    public final String nh;
    public final long ni;
    public int nj;
    public int nk;

    /*
     * Enabled aggressive block sorting
     */
    public bj(String object) throws JSONException {
        object = new JSONObject((String)object);
        if (dw.n(2)) {
            dw.y("Mediation Response JSON: " + object.toString(2));
        }
        JSONArray jSONArray = object.getJSONArray("ad_networks");
        ArrayList<bi> arrayList = new ArrayList<bi>(jSONArray.length());
        int n2 = -1;
        for (int i2 = 0; i2 < jSONArray.length(); ++i2) {
            bi bi2 = new bi(jSONArray.getJSONObject(i2));
            arrayList.add(bi2);
            int n3 = n2;
            if (n2 < 0) {
                n3 = n2;
                if (this.a(bi2)) {
                    n3 = i2;
                }
            }
            n2 = n3;
        }
        this.nj = n2;
        this.nk = jSONArray.length();
        this.nc = Collections.unmodifiableList(arrayList);
        this.nh = object.getString("qdata");
        if ((object = object.optJSONObject("settings")) == null) {
            this.nd = -1;
            this.ne = null;
            this.nf = null;
            this.ng = null;
            this.ni = -1;
            return;
        }
        this.nd = object.optLong("ad_network_timeout_millis", -1);
        this.ne = bo.a((JSONObject)object, "click_urls");
        this.nf = bo.a((JSONObject)object, "imp_urls");
        this.ng = bo.a((JSONObject)object, "nofill_urls");
        long l2 = object.optLong("refresh", -1);
        l2 = l2 > 0 ? (l2 *= 1000) : -1;
        this.ni = l2;
    }

    private boolean a(bi object) {
        object = object.mY.iterator();
        while (object.hasNext()) {
            if (!((String)object.next()).equals("com.google.ads.mediation.admob.AdMobAdapter")) continue;
            return true;
        }
        return false;
    }
}

