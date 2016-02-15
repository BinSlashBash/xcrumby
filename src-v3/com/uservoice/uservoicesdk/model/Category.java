package com.uservoice.uservoicesdk.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Category extends BaseModel {
    private String name;

    public void load(JSONObject object) throws JSONException {
        super.load(object);
        this.name = getString(object, "name");
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }
}
