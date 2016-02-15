/*
 * Decompiled with CFR 0_110.
 */
package org.json.zip;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.Kim;
import org.json.zip.BitWriter;
import org.json.zip.Huff;
import org.json.zip.JSONzip;
import org.json.zip.Keep;
import org.json.zip.MapKeep;
import org.json.zip.TrieKeep;

public class Compressor
extends JSONzip {
    final BitWriter bitwriter;

    public Compressor(BitWriter bitWriter) {
        this.bitwriter = bitWriter;
    }

    private static int bcd(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - 48;
        }
        switch (c2) {
            default: {
                return 13;
            }
            case '.': {
                return 10;
            }
            case '-': {
                return 11;
            }
            case '+': 
        }
        return 12;
    }

    private void one() throws JSONException {
        this.write(1, 1);
    }

    private void write(int n2, int n3) throws JSONException {
        try {
            this.bitwriter.write(n2, n3);
            return;
        }
        catch (Throwable var3_3) {
            throw new JSONException(var3_3);
        }
    }

    private void write(int n2, Huff huff) throws JSONException {
        huff.write(n2, this.bitwriter);
    }

    private void write(Kim kim, int n2, int n3, Huff huff) throws JSONException {
        while (n2 < n3) {
            this.write(kim.get(n2), huff);
            ++n2;
        }
    }

    private void write(Kim kim, Huff huff) throws JSONException {
        this.write(kim, 0, kim.length, huff);
    }

    private void writeAndTick(int n2, Keep keep) throws JSONException {
        int n3 = keep.bitsize();
        keep.tick(n2);
        this.write(n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeArray(JSONArray jSONArray) throws JSONException {
        Object object;
        boolean bl2 = false;
        int n2 = jSONArray.length();
        if (n2 == 0) {
            this.write(1, 3);
            return;
        }
        Object object2 = object = jSONArray.get(0);
        if (object == null) {
            object2 = JSONObject.NULL;
        }
        if (object2 instanceof String) {
            bl2 = true;
            this.write(6, 3);
            this.writeString((String)object2);
        } else {
            this.write(7, 3);
            this.writeValue(object2);
        }
        int n3 = 1;
        do {
            if (n3 >= n2) {
                this.zero();
                this.zero();
                return;
            }
            object2 = object = jSONArray.get(n3);
            if (object == null) {
                object2 = JSONObject.NULL;
            }
            if (object2 instanceof String != bl2) {
                this.zero();
            }
            this.one();
            if (object2 instanceof String) {
                this.writeString((String)object2);
            } else {
                this.writeValue(object2);
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeJSON(Object object) throws JSONException {
        Object object2;
        if (JSONObject.NULL.equals(object)) {
            this.write(4, 3);
            return;
        }
        if (Boolean.FALSE.equals(object)) {
            this.write(3, 3);
            return;
        }
        if (Boolean.TRUE.equals(object)) {
            this.write(2, 3);
            return;
        }
        if (object instanceof Map) {
            object2 = new JSONObject((Map)object);
        } else if (object instanceof Collection) {
            object2 = new JSONArray((Collection)object);
        } else {
            object2 = object;
            if (object.getClass().isArray()) {
                object2 = new JSONArray(object);
            }
        }
        if (object2 instanceof JSONObject) {
            this.writeObject((JSONObject)object2);
            return;
        }
        if (object2 instanceof JSONArray) {
            this.writeArray((JSONArray)object2);
            return;
        }
        throw new JSONException("Unrecognized object");
    }

    private void writeName(String object) throws JSONException {
        int n2 = this.namekeep.find(object = new Kim((String)object));
        if (n2 != -1) {
            this.one();
            this.writeAndTick(n2, this.namekeep);
            return;
        }
        this.zero();
        this.write((Kim)object, this.namehuff);
        this.write(256, this.namehuff);
        this.namekeep.register(object);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeObject(JSONObject jSONObject) throws JSONException {
        boolean bl2 = true;
        Iterator iterator = jSONObject.keys();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (!(object instanceof String)) continue;
            if (bl2) {
                bl2 = false;
                this.write(5, 3);
            } else {
                this.one();
            }
            this.writeName((String)object);
            object = jSONObject.get((String)object);
            if (object instanceof String) {
                this.zero();
                this.writeString((String)object);
                continue;
            }
            this.one();
            this.writeValue(object);
        }
        if (bl2) {
            this.write(0, 3);
            return;
        }
        this.zero();
    }

    private void writeString(String object) throws JSONException {
        if (object.length() == 0) {
            this.zero();
            this.zero();
            this.write(256, this.substringhuff);
            this.zero();
            return;
        }
        int n2 = this.stringkeep.find(object = new Kim((String)object));
        if (n2 != -1) {
            this.one();
            this.writeAndTick(n2, this.stringkeep);
            return;
        }
        this.writeSubstring((Kim)object);
        this.stringkeep.register(object);
    }

    private void writeSubstring(Kim kim) throws JSONException {
        this.substringkeep.reserve();
        this.zero();
        int n2 = 0;
        int n3 = kim.length;
        int n4 = -1;
        int n5 = 0;
        do {
            int n6 = -1;
            int n7 = n2;
            do {
                if (n7 > n3 - 3 || (n6 = this.substringkeep.match(kim, n7, n3)) != -1) {
                    if (n6 != -1) break;
                    this.zero();
                    if (n2 < n3) {
                        this.write(kim, n2, n3, this.substringhuff);
                        if (n4 != -1) {
                            this.substringkeep.registerOne(kim, n4, n5);
                        }
                    }
                    this.write(256, this.substringhuff);
                    this.zero();
                    this.substringkeep.registerMany(kim);
                    return;
                }
                ++n7;
            } while (true);
            int n8 = n4;
            if (n2 != n7) {
                this.zero();
                this.write(kim, n2, n7, this.substringhuff);
                this.write(256, this.substringhuff);
                n8 = n4;
                if (n4 != -1) {
                    this.substringkeep.registerOne(kim, n4, n5);
                    n8 = -1;
                }
            }
            this.one();
            this.writeAndTick(n6, this.substringkeep);
            n2 = n7 + this.substringkeep.length(n6);
            if (n8 != -1) {
                this.substringkeep.registerOne(kim, n8, n5);
            }
            n4 = n7;
            n5 = n2 + 1;
        } while (true);
    }

    private void writeValue(Object object) throws JSONException {
        if (object instanceof Number) {
            long l2;
            String string2 = JSONObject.numberToString((Number)object);
            int n2 = this.values.find(string2);
            if (n2 != -1) {
                this.write(2, 2);
                this.writeAndTick(n2, this.values);
                return;
            }
            if ((object instanceof Integer || object instanceof Long) && (l2 = ((Number)object).longValue()) >= 0 && l2 < 16384) {
                this.write(0, 2);
                if (l2 < 16) {
                    this.zero();
                    this.write((int)l2, 4);
                    return;
                }
                this.one();
                if (l2 < 128) {
                    this.zero();
                    this.write((int)l2, 7);
                    return;
                }
                this.one();
                this.write((int)l2, 14);
                return;
            }
            this.write(1, 2);
            for (n2 = 0; n2 < string2.length(); ++n2) {
                this.write(Compressor.bcd(string2.charAt(n2)), 4);
            }
            this.write(endOfNumber, 4);
            this.values.register(string2);
            return;
        }
        this.write(3, 2);
        this.writeJSON(object);
    }

    private void zero() throws JSONException {
        this.write(0, 1);
    }

    public void flush() throws JSONException {
        this.pad(8);
    }

    public void pad(int n2) throws JSONException {
        try {
            this.bitwriter.pad(n2);
            return;
        }
        catch (Throwable var2_2) {
            throw new JSONException(var2_2);
        }
    }

    public void zip(JSONArray jSONArray) throws JSONException {
        this.begin();
        this.writeJSON(jSONArray);
    }

    public void zip(JSONObject jSONObject) throws JSONException {
        this.begin();
        this.writeJSON(jSONObject);
    }
}

