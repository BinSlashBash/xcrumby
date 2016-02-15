/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;

public class Property {
    public static JSONObject toJSONObject(Properties properties) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (properties != null && !properties.isEmpty()) {
            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String string2 = (String)enumeration.nextElement();
                jSONObject.put(string2, properties.getProperty(string2));
            }
        }
        return jSONObject;
    }

    public static Properties toProperties(JSONObject jSONObject) throws JSONException {
        Properties properties = new Properties();
        if (jSONObject != null) {
            Iterator iterator = jSONObject.keys();
            while (iterator.hasNext()) {
                String string2 = iterator.next().toString();
                properties.put(string2, jSONObject.getString(string2));
            }
        }
        return properties;
    }
}

