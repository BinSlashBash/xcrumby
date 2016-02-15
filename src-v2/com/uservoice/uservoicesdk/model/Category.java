/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.model.BaseModel;
import org.json.JSONException;
import org.json.JSONObject;

public class Category
extends BaseModel {
    private String name;

    public String getName() {
        return this.name;
    }

    @Override
    public void load(JSONObject jSONObject) throws JSONException {
        super.load(jSONObject);
        this.name = this.getString(jSONObject, "name");
    }

    public String toString() {
        return this.name;
    }
}

