/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.bo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class bi {
    public final String mW;
    public final String mX;
    public final List<String> mY;
    public final String mZ;
    public final List<String> na;
    public final String nb;

    /*
     * Enabled aggressive block sorting
     */
    public bi(JSONObject object) throws JSONException {
        Object var4_2 = null;
        this.mX = object.getString("id");
        Object object2 = object.getJSONArray("adapters");
        ArrayList<String> arrayList = new ArrayList<String>(object2.length());
        for (int i2 = 0; i2 < object2.length(); ++i2) {
            arrayList.add(object2.getString(i2));
        }
        this.mY = Collections.unmodifiableList(arrayList);
        this.mZ = object.optString("allocation_id", null);
        this.na = bo.a((JSONObject)object, "imp_urls");
        object2 = object.optJSONObject("ad");
        object2 = object2 != null ? object2.toString() : null;
        this.mW = object2;
        object2 = object.optJSONObject("data");
        object = var4_2;
        if (object2 != null) {
            object = object2.toString();
        }
        this.nb = object;
    }
}

