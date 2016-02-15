/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONString;
import org.json.JSONTokener;

public class JSONObject {
    public static final Object NULL = new Null(null);
    private final Map map = new HashMap();

    public JSONObject() {
    }

    public JSONObject(Object object) {
        this();
        this.populateMap(object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JSONObject(Object object, String[] arrstring) {
        this();
        Class class_ = object.getClass();
        int n2 = 0;
        while (n2 < arrstring.length) {
            String string2 = arrstring[n2];
            try {
                this.putOpt(string2, class_.getField(string2).get(object));
            }
            catch (Exception var5_6) {}
            ++n2;
        }
    }

    public JSONObject(String string2) throws JSONException {
        this(new JSONTokener(string2));
    }

    public JSONObject(String object, Locale object2) throws JSONException {
        this();
        ResourceBundle resourceBundle = ResourceBundle.getBundle((String)object, (Locale)object2, Thread.currentThread().getContextClassLoader());
        Enumeration<String> enumeration = resourceBundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String string2 = enumeration.nextElement();
            if (!(string2 instanceof String)) continue;
            String[] arrstring = string2.split("\\.");
            int n2 = arrstring.length - 1;
            object2 = this;
            for (int i2 = 0; i2 < n2; ++i2) {
                JSONObject jSONObject;
                String string3 = arrstring[i2];
                object = jSONObject = object2.optJSONObject(string3);
                if (jSONObject == null) {
                    object = new JSONObject();
                    object2.put(string3, object);
                }
                object2 = object;
            }
            object2.put(arrstring[n2], resourceBundle.getString(string2));
        }
    }

    public JSONObject(Map object) {
        if (object != null) {
            for (Map.Entry entry : object.entrySet()) {
                Object v2 = entry.getValue();
                if (v2 == null) continue;
                this.map.put(entry.getKey(), JSONObject.wrap(v2));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JSONObject(JSONObject jSONObject, String[] arrstring) {
        this();
        int n2 = 0;
        while (n2 < arrstring.length) {
            try {
                this.putOnce(arrstring[n2], jSONObject.opt(arrstring[n2]));
            }
            catch (Exception var4_4) {}
            ++n2;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public JSONObject(JSONTokener var1_1) throws JSONException {
        this();
        if (var1_1.nextClean() == '{') ** GOTO lbl5
        throw var1_1.syntaxError("A JSONObject text must begin with '{'");
        {
            var1_1.back();
lbl5: // 2 sources:
            switch (var1_1.nextClean()) {
                default: {
                    var1_1.back();
                    var2_2 = var1_1.nextValue().toString();
                    if (var1_1.nextClean() != ':') {
                        throw var1_1.syntaxError("Expected a ':' after a key");
                    }
                    ** GOTO lbl14
                }
                case '\u0000': {
                    throw var1_1.syntaxError("A JSONObject text must end with '}'");
                }
lbl14: // 1 sources:
                this.putOnce(var2_2, var1_1.nextValue());
                switch (var1_1.nextClean()) {
                    default: {
                        throw var1_1.syntaxError("Expected a ',' or '}'");
                    }
                    case ',': 
                    case ';': {
                        if (var1_1.nextClean() != '}') continue block8;
                    }
                    case '}': 
                }
                case '}': 
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String doubleToString(double d2) {
        String string2;
        if (Double.isInfinite(d2)) return "null";
        if (Double.isNaN(d2)) {
            return "null";
        }
        String string3 = string2 = Double.toString(d2);
        if (string2.indexOf(46) <= 0) return string3;
        string3 = string2;
        if (string2.indexOf(101) >= 0) return string3;
        string3 = string2;
        if (string2.indexOf(69) >= 0) return string3;
        while (string2.endsWith("0")) {
            string2 = string2.substring(0, string2.length() - 1);
        }
        string3 = string2;
        if (!string2.endsWith(".")) return string3;
        return string2.substring(0, string2.length() - 1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String[] getNames(Object arrstring) {
        String[] arrstring2 = null;
        if (arrstring == null) {
            return arrstring2;
        }
        Field[] arrfield = arrstring.getClass().getFields();
        int n2 = arrfield.length;
        arrstring = arrstring2;
        if (n2 == 0) return arrstring;
        arrstring2 = new String[n2];
        int n3 = 0;
        do {
            arrstring = arrstring2;
            if (n3 >= n2) return arrstring;
            arrstring2[n3] = arrfield[n3].getName();
            ++n3;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String[] getNames(JSONObject arrstring) {
        int n2 = arrstring.length();
        if (n2 == 0) {
            return null;
        }
        Iterator iterator = arrstring.keys();
        String[] arrstring2 = new String[n2];
        n2 = 0;
        do {
            arrstring = arrstring2;
            if (!iterator.hasNext()) return arrstring;
            arrstring2[n2] = (String)iterator.next();
            ++n2;
        } while (true);
    }

    static final void indent(Writer writer, int n2) throws IOException {
        for (int i2 = 0; i2 < n2; ++i2) {
            writer.write(32);
        }
    }

    public static String numberToString(Number object) throws JSONException {
        String string2;
        if (object == null) {
            throw new JSONException("Null pointer");
        }
        JSONObject.testValidity(object);
        object = string2 = object.toString();
        if (string2.indexOf(46) > 0) {
            object = string2;
            if (string2.indexOf(101) < 0) {
                object = string2;
                if (string2.indexOf(69) < 0) {
                    while (string2.endsWith("0")) {
                        string2 = string2.substring(0, string2.length() - 1);
                    }
                    object = string2;
                    if (string2.endsWith(".")) {
                        object = string2.substring(0, string2.length() - 1);
                    }
                }
            }
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void populateMap(Object object) {
        int n2 = 0;
        Class class_ = object.getClass();
        Method[] arrmethod = class_.getClassLoader() != null && (n2 = 1) != 0 ? class_.getMethods() : class_.getDeclaredMethods();
        n2 = 0;
        while (n2 < arrmethod.length) {
            block11 : {
                Object object2;
                Method method;
                block13 : {
                    block12 : {
                        method = arrmethod[n2];
                        try {
                            if (!Modifier.isPublic(method.getModifiers())) break block11;
                            object2 = method.getName();
                            class_ = "";
                            if (object2.startsWith("get")) {
                                if ("getClass".equals(object2) || "getDeclaringClass".equals(object2)) break block12;
                                class_ = object2.substring(3);
                                break block13;
                            }
                            if (object2.startsWith("is")) {
                                class_ = object2.substring(2);
                            }
                            break block13;
                        }
                        catch (Exception var3_4) {
                            break block11;
                        }
                    }
                    class_ = "";
                }
                if (class_.length() > 0 && Character.isUpperCase(class_.charAt(0)) && method.getParameterTypes().length == 0) {
                    if (class_.length() == 1) {
                        object2 = class_.toLowerCase();
                    } else {
                        object2 = class_;
                        if (!Character.isUpperCase(class_.charAt(1))) {
                            object2 = new StringBuffer().append(class_.substring(0, 1).toLowerCase()).append(class_.substring(1)).toString();
                        }
                    }
                    if ((class_ = method.invoke(object, null)) != null) {
                        this.map.put(object2, JSONObject.wrap(class_));
                    }
                }
            }
            ++n2;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static Writer quote(String var0, Writer var1_1) throws IOException {
        if (var0 == null || var0.length() == 0) {
            var1_1.write("\"\"");
            return var1_1;
        }
        var2_2 = '\u0000';
        var5_3 = var0.length();
        var1_1.write(34);
        var3_4 = 0;
        do {
            var4_5 = var2_2;
            if (var3_4 >= var5_3) {
                var1_1.write(34);
                return var1_1;
            }
            var2_2 = var0.charAt(var3_4);
            switch (var2_2) {
                default: {
                    if (var2_2 >= ' ' && (var2_2 < '' || var2_2 >= '\u00a0') && (var2_2 < '\u2000' || var2_2 >= '\u2100')) break;
                    var1_1.write("\\u");
                    var6_6 = Integer.toHexString(var2_2);
                    var1_1.write("0000", 0, 4 - var6_6.length());
                    var1_1.write(var6_6);
                    ** GOTO lbl47
                }
                case '\"': 
                case '\\': {
                    var1_1.write(92);
                    var1_1.write(var2_2);
                    ** GOTO lbl47
                }
                case '/': {
                    if (var4_5 == '<') {
                        var1_1.write(92);
                    }
                    var1_1.write(var2_2);
                    ** GOTO lbl47
                }
                case '\b': {
                    var1_1.write("\\b");
                    ** GOTO lbl47
                }
                case '\t': {
                    var1_1.write("\\t");
                    ** GOTO lbl47
                }
                case '\n': {
                    var1_1.write("\\n");
                    ** GOTO lbl47
                }
                case '\f': {
                    var1_1.write("\\f");
                    ** GOTO lbl47
                }
                case '\r': {
                    var1_1.write("\\r");
                    ** GOTO lbl47
                }
            }
            var1_1.write(var2_2);
lbl47: // 9 sources:
            ++var3_4;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String quote(String string2) {
        StringWriter stringWriter = new StringWriter();
        StringBuffer stringBuffer = stringWriter.getBuffer();
        synchronized (stringBuffer) {
            try {
                return JSONObject.quote(string2, stringWriter).toString();
            }
            catch (IOException var0_1) {
                return "";
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Object stringToValue(String string2) {
        block8 : {
            if (string2.equals("")) {
                return string2;
            }
            if (string2.equalsIgnoreCase("true")) {
                return Boolean.TRUE;
            }
            if (string2.equalsIgnoreCase("false")) {
                return Boolean.FALSE;
            }
            if (string2.equalsIgnoreCase("null")) {
                return NULL;
            }
            char c2 = string2.charAt(0);
            if (c2 < '0' || c2 > '9') {
                if (c2 != '-') return string2;
            }
            try {
                if (string2.indexOf(46) <= -1 && string2.indexOf(101) <= -1 && string2.indexOf(69) <= -1) break block8;
                Double d2 = Double.valueOf(string2);
                if (d2.isInfinite()) return string2;
                if (d2.isNaN()) return string2;
                return d2;
            }
            catch (Exception var2_5) {
                return string2;
            }
        }
        Long l2 = new Long(string2);
        if (!string2.equals(l2.toString())) return string2;
        if (l2 != (long)l2.intValue()) return l2;
        return new Integer(l2.intValue());
    }

    public static void testValidity(Object object) throws JSONException {
        if (object != null && (object instanceof Double ? ((Double)object).isInfinite() || ((Double)object).isNaN() : object instanceof Float && (((Float)object).isInfinite() || ((Float)object).isNaN()))) {
            throw new JSONException("JSON does not allow non-finite numbers.");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String valueToString(Object object) throws JSONException {
        if (object == null || object.equals(null)) {
            return "null";
        }
        if (object instanceof JSONString) {
            try {
                object = ((JSONString)object).toJSONString();
            }
            catch (Exception var0_1) {
                throw new JSONException(var0_1);
            }
            if (!(object instanceof String)) throw new JSONException(new StringBuffer().append("Bad value from toJSONString: ").append(object).toString());
            return (String)object;
        }
        if (object instanceof Number) {
            return JSONObject.numberToString((Number)object);
        }
        if (object instanceof Boolean || object instanceof JSONObject || object instanceof JSONArray) {
            return object.toString();
        }
        if (object instanceof Map) {
            return new JSONObject((Map)object).toString();
        }
        if (object instanceof Collection) {
            return new JSONArray((Collection)object).toString();
        }
        if (!object.getClass().isArray()) return JSONObject.quote(object.toString());
        return new JSONArray(object).toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Object wrap(Object var0) {
        if (var0 != null) ** GOTO lbl4
        try {
            return JSONObject.NULL;
lbl4: // 1 sources:
            var1_2 = var0;
            if (var0 instanceof JSONObject != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof JSONArray != false) return var1_2;
            var1_2 = var0;
            if (JSONObject.NULL.equals(var0) != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof JSONString != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof Byte != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof Character != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof Short != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof Integer != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof Long != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof Boolean != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof Float != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof Double != false) return var1_2;
            var1_2 = var0;
            if (var0 instanceof String != false) return var1_2;
            if (var0 instanceof Collection) {
                return new JSONArray((Collection)var0);
            }
            if (var0.getClass().isArray()) {
                return new JSONArray(var0);
            }
            if (var0 instanceof Map) {
                return new JSONObject((Map)var0);
            }
            var1_2 = var0.getClass().getPackage();
            var1_2 = var1_2 != null ? var1_2.getName() : "";
            if (var1_2.startsWith("java.") != false) return var0.toString();
            if (var1_2.startsWith("javax.") != false) return var0.toString();
            if (var0.getClass().getClassLoader() != null) return new JSONObject(var0);
            return var0.toString();
        }
        catch (Exception var0_1) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static final Writer writeValue(Writer writer, Object object, int n2, int n3) throws JSONException, IOException {
        if (object == null || object.equals(null)) {
            writer.write("null");
            return writer;
        }
        if (object instanceof JSONObject) {
            ((JSONObject)object).write(writer, n2, n3);
            return writer;
        }
        if (object instanceof JSONArray) {
            ((JSONArray)object).write(writer, n2, n3);
            return writer;
        }
        if (object instanceof Map) {
            new JSONObject((Map)object).write(writer, n2, n3);
            return writer;
        }
        if (object instanceof Collection) {
            new JSONArray((Collection)object).write(writer, n2, n3);
            return writer;
        }
        if (object.getClass().isArray()) {
            new JSONArray(object).write(writer, n2, n3);
            return writer;
        }
        if (object instanceof Number) {
            writer.write(JSONObject.numberToString((Number)object));
            return writer;
        }
        if (object instanceof Boolean) {
            writer.write(object.toString());
            return writer;
        }
        if (!(object instanceof JSONString)) {
            JSONObject.quote(object.toString(), writer);
            return writer;
        }
        try {
            String string2 = ((JSONString)object).toJSONString();
            object = string2 != null ? string2.toString() : JSONObject.quote(object.toString());
        }
        catch (Exception var0_1) {
            throw new JSONException(var0_1);
        }
        writer.write((String)object);
        return writer;
    }

    public JSONObject accumulate(String string2, Object object) throws JSONException {
        JSONObject.testValidity(object);
        Object object2 = this.opt(string2);
        if (object2 == null) {
            object2 = object;
            if (object instanceof JSONArray) {
                object2 = new JSONArray().put(object);
            }
            this.put(string2, object2);
            return this;
        }
        if (object2 instanceof JSONArray) {
            ((JSONArray)object2).put(object);
            return this;
        }
        this.put(string2, new JSONArray().put(object2).put(object));
        return this;
    }

    public JSONObject append(String string2, Object object) throws JSONException {
        JSONObject.testValidity(object);
        Object object2 = this.opt(string2);
        if (object2 == null) {
            this.put(string2, new JSONArray().put(object));
            return this;
        }
        if (object2 instanceof JSONArray) {
            this.put(string2, ((JSONArray)object2).put(object));
            return this;
        }
        throw new JSONException(new StringBuffer().append("JSONObject[").append(string2).append("] is not a JSONArray.").toString());
    }

    public Object get(String string2) throws JSONException {
        if (string2 == null) {
            throw new JSONException("Null key.");
        }
        Object object = this.opt(string2);
        if (object == null) {
            throw new JSONException(new StringBuffer().append("JSONObject[").append(JSONObject.quote(string2)).append("] not found.").toString());
        }
        return object;
    }

    public boolean getBoolean(String string2) throws JSONException {
        Object object = this.get(string2);
        if (object.equals(Boolean.FALSE) || object instanceof String && ((String)object).equalsIgnoreCase("false")) {
            return false;
        }
        if (object.equals(Boolean.TRUE) || object instanceof String && ((String)object).equalsIgnoreCase("true")) {
            return true;
        }
        throw new JSONException(new StringBuffer().append("JSONObject[").append(JSONObject.quote(string2)).append("] is not a Boolean.").toString());
    }

    public double getDouble(String string2) throws JSONException {
        Object object = this.get(string2);
        try {
            if (object instanceof Number) {
                return ((Number)object).doubleValue();
            }
            double d2 = Double.parseDouble((String)object);
            return d2;
        }
        catch (Exception var4_3) {
            throw new JSONException(new StringBuffer().append("JSONObject[").append(JSONObject.quote(string2)).append("] is not a number.").toString());
        }
    }

    public int getInt(String string2) throws JSONException {
        Object object = this.get(string2);
        try {
            if (object instanceof Number) {
                return ((Number)object).intValue();
            }
            int n2 = Integer.parseInt((String)object);
            return n2;
        }
        catch (Exception var3_3) {
            throw new JSONException(new StringBuffer().append("JSONObject[").append(JSONObject.quote(string2)).append("] is not an int.").toString());
        }
    }

    public JSONArray getJSONArray(String string2) throws JSONException {
        Object object = this.get(string2);
        if (object instanceof JSONArray) {
            return (JSONArray)object;
        }
        throw new JSONException(new StringBuffer().append("JSONObject[").append(JSONObject.quote(string2)).append("] is not a JSONArray.").toString());
    }

    public JSONObject getJSONObject(String string2) throws JSONException {
        Object object = this.get(string2);
        if (object instanceof JSONObject) {
            return (JSONObject)object;
        }
        throw new JSONException(new StringBuffer().append("JSONObject[").append(JSONObject.quote(string2)).append("] is not a JSONObject.").toString());
    }

    public long getLong(String string2) throws JSONException {
        Object object = this.get(string2);
        try {
            if (object instanceof Number) {
                return ((Number)object).longValue();
            }
            long l2 = Long.parseLong((String)object);
            return l2;
        }
        catch (Exception var4_3) {
            throw new JSONException(new StringBuffer().append("JSONObject[").append(JSONObject.quote(string2)).append("] is not a long.").toString());
        }
    }

    public String getString(String string2) throws JSONException {
        Object object = this.get(string2);
        if (object instanceof String) {
            return (String)object;
        }
        throw new JSONException(new StringBuffer().append("JSONObject[").append(JSONObject.quote(string2)).append("] not a string.").toString());
    }

    public boolean has(String string2) {
        return this.map.containsKey(string2);
    }

    public JSONObject increment(String string2) throws JSONException {
        Object object = this.opt(string2);
        if (object == null) {
            this.put(string2, 1);
            return this;
        }
        if (object instanceof Integer) {
            this.put(string2, (Integer)object + 1);
            return this;
        }
        if (object instanceof Long) {
            this.put(string2, (Long)object + 1);
            return this;
        }
        if (object instanceof Double) {
            this.put(string2, (Double)object + 1.0);
            return this;
        }
        if (object instanceof Float) {
            this.put(string2, ((Float)object).floatValue() + 1.0f);
            return this;
        }
        throw new JSONException(new StringBuffer().append("Unable to increment [").append(JSONObject.quote(string2)).append("].").toString());
    }

    public boolean isNull(String string2) {
        return NULL.equals(this.opt(string2));
    }

    public Set keySet() {
        return this.map.keySet();
    }

    public Iterator keys() {
        return this.keySet().iterator();
    }

    public int length() {
        return this.map.size();
    }

    public JSONArray names() {
        JSONArray jSONArray = new JSONArray();
        Object object = this.keys();
        while (object.hasNext()) {
            jSONArray.put(object.next());
        }
        object = jSONArray;
        if (jSONArray.length() == 0) {
            object = null;
        }
        return object;
    }

    public Object opt(String string2) {
        if (string2 == null) {
            return null;
        }
        return this.map.get(string2);
    }

    public boolean optBoolean(String string2) {
        return this.optBoolean(string2, false);
    }

    public boolean optBoolean(String string2, boolean bl2) {
        try {
            boolean bl3 = this.getBoolean(string2);
            return bl3;
        }
        catch (Exception var1_2) {
            return bl2;
        }
    }

    public double optDouble(String string2) {
        return this.optDouble(string2, Double.NaN);
    }

    public double optDouble(String string2, double d2) {
        try {
            double d3 = this.getDouble(string2);
            return d3;
        }
        catch (Exception var1_2) {
            return d2;
        }
    }

    public int optInt(String string2) {
        return this.optInt(string2, 0);
    }

    public int optInt(String string2, int n2) {
        try {
            int n3 = this.getInt(string2);
            return n3;
        }
        catch (Exception var1_2) {
            return n2;
        }
    }

    public JSONArray optJSONArray(String object) {
        if ((object = this.opt((String)object)) instanceof JSONArray) {
            return (JSONArray)object;
        }
        return null;
    }

    public JSONObject optJSONObject(String object) {
        if ((object = this.opt((String)object)) instanceof JSONObject) {
            return (JSONObject)object;
        }
        return null;
    }

    public long optLong(String string2) {
        return this.optLong(string2, 0);
    }

    public long optLong(String string2, long l2) {
        try {
            long l3 = this.getLong(string2);
            return l3;
        }
        catch (Exception var1_2) {
            return l2;
        }
    }

    public String optString(String string2) {
        return this.optString(string2, "");
    }

    public String optString(String object, String string2) {
        if (NULL.equals(object = this.opt((String)object))) {
            return string2;
        }
        return object.toString();
    }

    public JSONObject put(String string2, double d2) throws JSONException {
        this.put(string2, new Double(d2));
        return this;
    }

    public JSONObject put(String string2, int n2) throws JSONException {
        this.put(string2, new Integer(n2));
        return this;
    }

    public JSONObject put(String string2, long l2) throws JSONException {
        this.put(string2, new Long(l2));
        return this;
    }

    public JSONObject put(String string2, Object object) throws JSONException {
        if (string2 == null) {
            throw new NullPointerException("Null key.");
        }
        if (object != null) {
            JSONObject.testValidity(object);
            this.map.put(string2, object);
            return this;
        }
        this.remove(string2);
        return this;
    }

    public JSONObject put(String string2, Collection collection) throws JSONException {
        this.put(string2, new JSONArray(collection));
        return this;
    }

    public JSONObject put(String string2, Map map) throws JSONException {
        this.put(string2, new JSONObject(map));
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JSONObject put(String string2, boolean bl2) throws JSONException {
        Boolean bl3 = bl2 ? Boolean.TRUE : Boolean.FALSE;
        this.put(string2, bl3);
        return this;
    }

    public JSONObject putOnce(String string2, Object object) throws JSONException {
        if (string2 != null && object != null) {
            if (this.opt(string2) != null) {
                throw new JSONException(new StringBuffer().append("Duplicate key \"").append(string2).append("\"").toString());
            }
            this.put(string2, object);
        }
        return this;
    }

    public JSONObject putOpt(String string2, Object object) throws JSONException {
        if (string2 != null && object != null) {
            this.put(string2, object);
        }
        return this;
    }

    public Object remove(String string2) {
        return this.map.remove(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JSONArray toJSONArray(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) return null;
        if (jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        int n2 = 0;
        do {
            JSONArray jSONArray3 = jSONArray2;
            if (n2 >= jSONArray.length()) return jSONArray3;
            jSONArray2.put(this.opt(jSONArray.getString(n2)));
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
        block10 : {
            int n4;
            Object object;
            boolean bl2;
            block9 : {
                bl2 = false;
                try {
                    n4 = this.length();
                    object = this.keys();
                    writer.write(123);
                    if (n4 != 1) break block9;
                    object = object.next();
                    writer.write(JSONObject.quote(object.toString()));
                    writer.write(58);
                    if (n2 > 0) {
                        writer.write(32);
                    }
                    JSONObject.writeValue(writer, this.map.get(object), n2, n3);
                    break block10;
                }
                catch (IOException var1_2) {
                    throw new JSONException(var1_2);
                }
            }
            if (n4 != 0) {
                n4 = n3 + n2;
                while (object.hasNext()) {
                    Object e2 = object.next();
                    if (bl2) {
                        writer.write(44);
                    }
                    if (n2 > 0) {
                        writer.write(10);
                    }
                    JSONObject.indent(writer, n4);
                    writer.write(JSONObject.quote(e2.toString()));
                    writer.write(58);
                    if (n2 > 0) {
                        writer.write(32);
                    }
                    JSONObject.writeValue(writer, this.map.get(e2), n2, n4);
                    bl2 = true;
                }
                if (n2 > 0) {
                    writer.write(10);
                }
                JSONObject.indent(writer, n3);
            }
        }
        writer.write(125);
        return writer;
    }

    private static final class Null {
        private Null() {
        }

        Null( var1_1) {
            this();
        }

        protected final Object clone() {
            return this;
        }

        public boolean equals(Object object) {
            if (object == null || object == this) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "null";
        }
    }

}

