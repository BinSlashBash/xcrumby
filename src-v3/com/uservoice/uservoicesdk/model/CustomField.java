package com.uservoice.uservoicesdk.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomField extends BaseModel {
    private String name;
    private List<String> predefinedValues;
    private boolean required;

    public void load(JSONObject object) throws JSONException {
        super.load(object);
        this.name = getString(object, "name");
        this.required = !object.getBoolean("allow_blank");
        this.predefinedValues = new ArrayList();
        if (object.has("possible_values")) {
            JSONArray values = object.getJSONArray("possible_values");
            for (int i = 0; i < values.length(); i++) {
                this.predefinedValues.add(getString(values.getJSONObject(i), "value"));
            }
        }
    }

    public void save(JSONObject object) throws JSONException {
        super.save(object);
        object.put("name", this.name);
        object.put("allow_blank", !this.required);
        Object jsonPredefinedValues = new JSONArray();
        for (Object value : this.predefinedValues) {
            Object predefinedValue = new JSONObject();
            predefinedValue.put("value", value);
            jsonPredefinedValues.put(predefinedValue);
        }
        object.put("possible_values", jsonPredefinedValues);
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean isPredefined() {
        return this.predefinedValues.size() > 0;
    }

    public List<String> getPredefinedValues() {
        return this.predefinedValues;
    }

    public String getName() {
        return this.name;
    }
}
