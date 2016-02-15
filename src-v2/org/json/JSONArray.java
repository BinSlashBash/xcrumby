/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONArray {
    private final ArrayList myArrayList = new ArrayList();

    public JSONArray() {
    }

    public JSONArray(Object object) throws JSONException {
        this();
        if (object.getClass().isArray()) {
            int n2 = Array.getLength(object);
            for (int i2 = 0; i2 < n2; ++i2) {
                this.put(JSONObject.wrap(Array.get(object, i2)));
            }
        } else {
            throw new JSONException("JSONArray initial value should be a string or collection or array.");
        }
    }

    public JSONArray(String string2) throws JSONException {
        this(new JSONTokener(string2));
    }

    public JSONArray(Collection object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                this.myArrayList.add(JSONObject.wrap(object.next()));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public JSONArray(JSONTokener jSONTokener) throws JSONException {
        this();
        if (jSONTokener.nextClean() != '[') {
            throw jSONTokener.syntaxError("A JSONArray text must start with '['");
        }
        if (jSONTokener.nextClean() == ']') return;
        {
            jSONTokener.back();
            do {
                if (jSONTokener.nextClean() == ',') {
                    jSONTokener.back();
                    this.myArrayList.add(JSONObject.NULL);
                } else {
                    jSONTokener.back();
                    this.myArrayList.add(jSONTokener.nextValue());
                }
                switch (jSONTokener.nextClean()) {
                    default: {
                        throw jSONTokener.syntaxError("Expected a ',' or ']'");
                    }
                    case ',': {
                        if (jSONTokener.nextClean() != ']') break;
                    }
                    case ']': {
                        return;
                    }
                }
                jSONTokener.back();
            } while (true);
        }
    }

    public Object get(int n2) throws JSONException {
        Object object = this.opt(n2);
        if (object == null) {
            throw new JSONException(new StringBuffer().append("JSONArray[").append(n2).append("] not found.").toString());
        }
        return object;
    }

    public boolean getBoolean(int n2) throws JSONException {
        Object object = this.get(n2);
        if (object.equals(Boolean.FALSE) || object instanceof String && ((String)object).equalsIgnoreCase("false")) {
            return false;
        }
        if (object.equals(Boolean.TRUE) || object instanceof String && ((String)object).equalsIgnoreCase("true")) {
            return true;
        }
        throw new JSONException(new StringBuffer().append("JSONArray[").append(n2).append("] is not a boolean.").toString());
    }

    public double getDouble(int n2) throws JSONException {
        Object object = this.get(n2);
        try {
            if (object instanceof Number) {
                return ((Number)object).doubleValue();
            }
            double d2 = Double.parseDouble((String)object);
            return d2;
        }
        catch (Exception var4_3) {
            throw new JSONException(new StringBuffer().append("JSONArray[").append(n2).append("] is not a number.").toString());
        }
    }

    public int getInt(int n2) throws JSONException {
        Object object = this.get(n2);
        try {
            if (object instanceof Number) {
                return ((Number)object).intValue();
            }
            int n3 = Integer.parseInt((String)object);
            return n3;
        }
        catch (Exception var3_3) {
            throw new JSONException(new StringBuffer().append("JSONArray[").append(n2).append("] is not a number.").toString());
        }
    }

    public JSONArray getJSONArray(int n2) throws JSONException {
        Object object = this.get(n2);
        if (object instanceof JSONArray) {
            return (JSONArray)object;
        }
        throw new JSONException(new StringBuffer().append("JSONArray[").append(n2).append("] is not a JSONArray.").toString());
    }

    public JSONObject getJSONObject(int n2) throws JSONException {
        Object object = this.get(n2);
        if (object instanceof JSONObject) {
            return (JSONObject)object;
        }
        throw new JSONException(new StringBuffer().append("JSONArray[").append(n2).append("] is not a JSONObject.").toString());
    }

    public long getLong(int n2) throws JSONException {
        Object object = this.get(n2);
        try {
            if (object instanceof Number) {
                return ((Number)object).longValue();
            }
            long l2 = Long.parseLong((String)object);
            return l2;
        }
        catch (Exception var4_3) {
            throw new JSONException(new StringBuffer().append("JSONArray[").append(n2).append("] is not a number.").toString());
        }
    }

    public String getString(int n2) throws JSONException {
        Object object = this.get(n2);
        if (object instanceof String) {
            return (String)object;
        }
        throw new JSONException(new StringBuffer().append("JSONArray[").append(n2).append("] not a string.").toString());
    }

    public boolean isNull(int n2) {
        return JSONObject.NULL.equals(this.opt(n2));
    }

    public String join(String string2) throws JSONException {
        int n2 = this.length();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 > 0) {
                stringBuffer.append(string2);
            }
            stringBuffer.append(JSONObject.valueToString(this.myArrayList.get(i2)));
        }
        return stringBuffer.toString();
    }

    public int length() {
        return this.myArrayList.size();
    }

    public Object opt(int n2) {
        if (n2 < 0 || n2 >= this.length()) {
            return null;
        }
        return this.myArrayList.get(n2);
    }

    public boolean optBoolean(int n2) {
        return this.optBoolean(n2, false);
    }

    public boolean optBoolean(int n2, boolean bl2) {
        try {
            boolean bl3 = this.getBoolean(n2);
            return bl3;
        }
        catch (Exception var4_4) {
            return bl2;
        }
    }

    public double optDouble(int n2) {
        return this.optDouble(n2, Double.NaN);
    }

    public double optDouble(int n2, double d2) {
        try {
            double d3 = this.getDouble(n2);
            return d3;
        }
        catch (Exception var6_4) {
            return d2;
        }
    }

    public int optInt(int n2) {
        return this.optInt(n2, 0);
    }

    public int optInt(int n2, int n3) {
        try {
            n2 = this.getInt(n2);
            return n2;
        }
        catch (Exception var3_3) {
            return n3;
        }
    }

    public JSONArray optJSONArray(int n2) {
        Object object = this.opt(n2);
        if (object instanceof JSONArray) {
            return (JSONArray)object;
        }
        return null;
    }

    public JSONObject optJSONObject(int n2) {
        Object object = this.opt(n2);
        if (object instanceof JSONObject) {
            return (JSONObject)object;
        }
        return null;
    }

    public long optLong(int n2) {
        return this.optLong(n2, 0);
    }

    public long optLong(int n2, long l2) {
        try {
            long l3 = this.getLong(n2);
            return l3;
        }
        catch (Exception var6_4) {
            return l2;
        }
    }

    public String optString(int n2) {
        return this.optString(n2, "");
    }

    public String optString(int n2, String string2) {
        Object object = this.opt(n2);
        if (JSONObject.NULL.equals(object)) {
            return string2;
        }
        return object.toString();
    }

    public JSONArray put(double d2) throws JSONException {
        Double d3 = new Double(d2);
        JSONObject.testValidity(d3);
        this.put(d3);
        return this;
    }

    public JSONArray put(int n2) {
        this.put(new Integer(n2));
        return this;
    }

    public JSONArray put(int n2, double d2) throws JSONException {
        this.put(n2, new Double(d2));
        return this;
    }

    public JSONArray put(int n2, int n3) throws JSONException {
        this.put(n2, new Integer(n3));
        return this;
    }

    public JSONArray put(int n2, long l2) throws JSONException {
        this.put(n2, new Long(l2));
        return this;
    }

    public JSONArray put(int n2, Object object) throws JSONException {
        JSONObject.testValidity(object);
        if (n2 < 0) {
            throw new JSONException(new StringBuffer().append("JSONArray[").append(n2).append("] not found.").toString());
        }
        if (n2 < this.length()) {
            this.myArrayList.set(n2, object);
            return this;
        }
        while (n2 != this.length()) {
            this.put(JSONObject.NULL);
        }
        this.put(object);
        return this;
    }

    public JSONArray put(int n2, Collection collection) throws JSONException {
        this.put(n2, new JSONArray(collection));
        return this;
    }

    public JSONArray put(int n2, Map map) throws JSONException {
        this.put(n2, new JSONObject(map));
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JSONArray put(int n2, boolean bl2) throws JSONException {
        Boolean bl3 = bl2 ? Boolean.TRUE : Boolean.FALSE;
        this.put(n2, bl3);
        return this;
    }

    public JSONArray put(long l2) {
        this.put(new Long(l2));
        return this;
    }

    public JSONArray put(Object object) {
        this.myArrayList.add(object);
        return this;
    }

    public JSONArray put(Collection collection) {
        this.put(new JSONArray(collection));
        return this;
    }

    public JSONArray put(Map map) {
        this.put(new JSONObject(map));
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JSONArray put(boolean bl2) {
        Boolean bl3 = bl2 ? Boolean.TRUE : Boolean.FALSE;
        this.put(bl3);
        return this;
    }

    public Object remove(int n2) {
        Object object = this.opt(n2);
        this.myArrayList.remove(n2);
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JSONObject toJSONObject(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) return null;
        if (jSONArray.length() == 0) return null;
        if (this.length() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        int n2 = 0;
        do {
            JSONObject jSONObject2 = jSONObject;
            if (n2 >= jSONArray.length()) return jSONObject2;
            jSONObject.put(jSONArray.getString(n2), this.opt(n2));
            ++n2;
        } while (true);
    }

    public String toString() {
        try {
            String string2 = this.toString(0);
            return string2;
        }
        catch (Exception var1_2) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString(int n2) throws JSONException {
        StringWriter stringWriter = new StringWriter();
        StringBuffer stringBuffer = stringWriter.getBuffer();
        synchronized (stringBuffer) {
            return this.write(stringWriter, n2, 0).toString();
        }
    }

    public Writer write(Writer writer) throws JSONException {
        return this.write(writer, 0, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Writer write(Writer writer, int n2, int n3) throws JSONException {
        boolean bl2 = false;
        try {
            int n4 = this.length();
            writer.write(91);
            if (n4 == 1) {
                JSONObject.writeValue(writer, this.myArrayList.get(0), n2, n3);
            } else if (n4 != 0) {
                int n5 = n3 + n2;
                for (int i2 = 0; i2 < n4; ++i2) {
                    if (bl2) {
                        writer.write(44);
                    }
                    if (n2 > 0) {
                        writer.write(10);
                    }
                    JSONObject.indent(writer, n5);
                    JSONObject.writeValue(writer, this.myArrayList.get(i2), n2, n5);
                    bl2 = true;
                }
                if (n2 > 0) {
                    writer.write(10);
                }
                JSONObject.indent(writer, n3);
            }
            writer.write(93);
            return writer;
        }
        catch (IOException var1_2) {
            throw new JSONException(var1_2);
        }
    }
}

