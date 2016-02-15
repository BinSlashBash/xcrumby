/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.util.Iterator;
import org.json.Cookie;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CookieList {
    public static JSONObject toJSONObject(String object) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        object = new JSONTokener((String)object);
        while (object.more()) {
            String string2 = Cookie.unescape(object.nextTo('='));
            object.next('=');
            jSONObject.put(string2, Cookie.unescape(object.nextTo(';')));
            object.next();
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        boolean bl2 = false;
        Iterator iterator = jSONObject.keys();
        StringBuffer stringBuffer = new StringBuffer();
        while (iterator.hasNext()) {
            String string2 = iterator.next().toString();
            if (jSONObject.isNull(string2)) continue;
            if (bl2) {
                stringBuffer.append(';');
            }
            stringBuffer.append(Cookie.escape(string2));
            stringBuffer.append("=");
            stringBuffer.append(Cookie.escape(jSONObject.getString(string2)));
            bl2 = true;
        }
        return stringBuffer.toString();
    }
}

