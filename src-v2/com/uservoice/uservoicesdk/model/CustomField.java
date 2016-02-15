/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.model.BaseModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomField
extends BaseModel {
    private String name;
    private List<String> predefinedValues;
    private boolean required;

    public String getName() {
        return this.name;
    }

    public List<String> getPredefinedValues() {
        return this.predefinedValues;
    }

    public boolean isPredefined() {
        if (this.predefinedValues.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean isRequired() {
        return this.required;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void load(JSONObject object) throws JSONException {
        super.load((JSONObject)object);
        this.name = this.getString((JSONObject)object, "name");
        boolean bl2 = !object.getBoolean("allow_blank");
        this.required = bl2;
        this.predefinedValues = new ArrayList<String>();
        if (!object.has("possible_values")) {
            return;
        }
        object = object.getJSONArray("possible_values");
        int n2 = 0;
        while (n2 < object.length()) {
            JSONObject jSONObject = object.getJSONObject(n2);
            this.predefinedValues.add(this.getString(jSONObject, "value"));
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void save(JSONObject jSONObject) throws JSONException {
        super.save(jSONObject);
        jSONObject.put("name", this.name);
        boolean bl2 = !this.required;
        jSONObject.put("allow_blank", bl2);
        JSONArray jSONArray = new JSONArray();
        Iterator<String> iterator = this.predefinedValues.iterator();
        do {
            if (!iterator.hasNext()) {
                jSONObject.put("possible_values", jSONArray);
                return;
            }
            String string2 = iterator.next();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("value", string2);
            jSONArray.put(jSONObject2);
        } while (true);
    }
}

