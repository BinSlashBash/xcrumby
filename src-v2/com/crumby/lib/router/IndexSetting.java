/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.router;

import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.IndexAttributeValue;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class IndexSetting {
    private Map<String, IndexAttributeValue> attributes;
    private final FragmentIndex index;
    private boolean showInSearch;

    public IndexSetting(FragmentIndex fragmentIndex) {
        this.index = fragmentIndex;
        this.showInSearch = true;
        this.buildAttributes();
    }

    public IndexSetting(IndexSetting object) {
        this.showInSearch = object.showInSearch;
        this.index = object.index;
        this.buildAttributes();
        for (Map.Entry<String, IndexAttributeValue> entry : object.attributes.entrySet()) {
            this.attributes.put(entry.getKey(), entry.getValue().copy());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void buildAttributes() {
        this.attributes = new HashMap<String, IndexAttributeValue>();
        IndexField[] arrindexField = this.index.getSettingAttributes().indexFields;
        int n2 = arrindexField.length;
        int n3 = 0;
        while (n3 < n2) {
            void var3_4;
            IndexField indexField = arrindexField[n3];
            Object var3_5 = null;
            if (indexField.defaultValue instanceof Number) {
                IndexAttributeValue<Number> indexAttributeValue = new IndexAttributeValue<Number>((Number)indexField.defaultValue);
            } else if (indexField.defaultValue instanceof Boolean) {
                IndexAttributeValue<Boolean> indexAttributeValue = new IndexAttributeValue<Boolean>((Boolean)indexField.defaultValue);
            } else if (indexField.defaultValue instanceof String) {
                IndexAttributeValue<String> indexAttributeValue = new IndexAttributeValue<String>((String)indexField.defaultValue);
            } else if (indexField.defaultValue instanceof Character) {
                IndexAttributeValue<Character> indexAttributeValue = new IndexAttributeValue<Character>((Character)indexField.defaultValue);
            }
            this.attributes.put(indexField.key, (IndexAttributeValue)var3_4);
            ++n3;
        }
    }

    public JsonObject getAsJson() throws Exception {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, IndexAttributeValue> entry : this.attributes.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue().getAsJsonPrimitive());
        }
        return jsonObject;
    }

    public boolean getAttributeBoolean(String string2) {
        if (this.attributes.get(string2) == null) {
            return false;
        }
        return this.attributes.get(string2).getAsBoolean();
    }

    public IndexAttributeValue getAttributeValue(String string2) {
        return this.attributes.get(string2);
    }

    public String getBaseUrl() {
        return this.index.getBaseUrl();
    }

    public String getDisplayName() {
        return this.index.getDisplayName();
    }

    public String getFaviconUrl() {
        return this.index.getFaviconUrl();
    }

    public IndexField[] getFields() {
        return this.index.getSettingAttributes().indexFields;
    }

    public String getFragmentClassName() {
        return this.index.getFragmentClassName();
    }

    public FragmentIndex getIndex() {
        return this.index;
    }

    public void restoreFromJson(JsonObject object) throws Exception {
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            Object object2 = (Map.Entry)object.next();
            String string2 = (String)object2.getKey();
            object2 = (JsonElement)object2.getValue();
            IndexAttributeValue indexAttributeValue = this.attributes.get(string2);
            if (indexAttributeValue == null) continue;
            indexAttributeValue.setValueFromJson(object2.getAsJsonPrimitive());
            this.attributes.put(string2, indexAttributeValue);
        }
    }
}

