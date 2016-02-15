package com.google.android.gms.internal;

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

    public bi(JSONObject jSONObject) throws JSONException {
        String str = null;
        this.mX = jSONObject.getString("id");
        JSONArray jSONArray = jSONObject.getJSONArray("adapters");
        List arrayList = new ArrayList(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        this.mY = Collections.unmodifiableList(arrayList);
        this.mZ = jSONObject.optString("allocation_id", null);
        this.na = bo.m666a(jSONObject, "imp_urls");
        JSONObject optJSONObject = jSONObject.optJSONObject("ad");
        this.mW = optJSONObject != null ? optJSONObject.toString() : null;
        optJSONObject = jSONObject.optJSONObject("data");
        if (optJSONObject != null) {
            str = optJSONObject.toString();
        }
        this.nb = str;
    }
}
