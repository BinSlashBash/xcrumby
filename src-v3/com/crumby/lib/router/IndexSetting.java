package com.crumby.lib.router;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class IndexSetting {
    private Map<String, IndexAttributeValue> attributes;
    private final FragmentIndex index;
    private boolean showInSearch;

    public IndexSetting(FragmentIndex index) {
        this.index = index;
        this.showInSearch = true;
        buildAttributes();
    }

    private void buildAttributes() {
        this.attributes = new HashMap();
        for (IndexField indexField : this.index.getSettingAttributes().indexFields) {
            IndexAttributeValue value = null;
            if (indexField.defaultValue instanceof Number) {
                value = new IndexAttributeValue((Number) indexField.defaultValue);
            } else if (indexField.defaultValue instanceof Boolean) {
                value = new IndexAttributeValue((Boolean) indexField.defaultValue);
            } else if (indexField.defaultValue instanceof String) {
                value = new IndexAttributeValue((String) indexField.defaultValue);
            } else if (indexField.defaultValue instanceof Character) {
                value = new IndexAttributeValue((Character) indexField.defaultValue);
            }
            this.attributes.put(indexField.key, value);
        }
    }

    public IndexSetting(IndexSetting setting) {
        this.showInSearch = setting.showInSearch;
        this.index = setting.index;
        buildAttributes();
        for (Entry<String, IndexAttributeValue> entry : setting.attributes.entrySet()) {
            this.attributes.put(entry.getKey(), ((IndexAttributeValue) entry.getValue()).copy());
        }
    }

    public String getDisplayName() {
        return this.index.getDisplayName();
    }

    public String getFaviconUrl() {
        return this.index.getFaviconUrl();
    }

    public JsonObject getAsJson() throws Exception {
        JsonObject settingsObject = new JsonObject();
        for (Entry<String, IndexAttributeValue> entry : this.attributes.entrySet()) {
            settingsObject.add((String) entry.getKey(), ((IndexAttributeValue) entry.getValue()).getAsJsonPrimitive());
        }
        return settingsObject;
    }

    public String getFragmentClassName() {
        return this.index.getFragmentClassName();
    }

    public void restoreFromJson(JsonObject settingJson) throws Exception {
        for (Entry<String, JsonElement> entry : settingJson.entrySet()) {
            String key = (String) entry.getKey();
            JsonElement valueJson = (JsonElement) entry.getValue();
            IndexAttributeValue oldValue = (IndexAttributeValue) this.attributes.get(key);
            if (oldValue != null) {
                oldValue.setValueFromJson(valueJson.getAsJsonPrimitive());
                this.attributes.put(key, oldValue);
            }
        }
    }

    public IndexField[] getFields() {
        return this.index.getSettingAttributes().indexFields;
    }

    public IndexAttributeValue getAttributeValue(String key) {
        return (IndexAttributeValue) this.attributes.get(key);
    }

    public boolean getAttributeBoolean(String key) {
        if (this.attributes.get(key) == null) {
            return false;
        }
        return ((IndexAttributeValue) this.attributes.get(key)).getAsBoolean();
    }

    public String getBaseUrl() {
        return this.index.getBaseUrl();
    }

    public FragmentIndex getIndex() {
        return this.index;
    }
}
