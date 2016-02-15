/*
 * Decompiled with CFR 0_110.
 */
package org.json.zip;

import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.Kim;
import org.json.zip.BitReader;
import org.json.zip.Huff;
import org.json.zip.JSONzip;
import org.json.zip.Keep;
import org.json.zip.MapKeep;
import org.json.zip.TrieKeep;

public class Decompressor
extends JSONzip {
    BitReader bitreader;

    public Decompressor(BitReader bitReader) {
        this.bitreader = bitReader;
    }

    private boolean bit() throws JSONException {
        try {
            boolean bl2 = this.bitreader.bit();
            return bl2;
        }
        catch (Throwable var2_2) {
            throw new JSONException(var2_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object getAndTick(Keep keep, BitReader object) throws JSONException {
        try {
            int n2 = object.read(keep.bitsize());
            object = keep.value(n2);
            if (n2 >= keep.length) {
                throw new JSONException("Deep error.");
            }
            keep.tick(n2);
            return object;
        }
        catch (Throwable var1_2) {
            throw new JSONException(var1_2);
        }
    }

    private int read(int n2) throws JSONException {
        try {
            n2 = this.bitreader.read(n2);
            return n2;
        }
        catch (Throwable var2_2) {
            throw new JSONException(var2_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private JSONArray readArray(boolean bl2) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        Object object = bl2 ? this.readString() : this.readValue();
        jSONArray.put(object);
        do {
            if (!this.bit()) {
                if (!this.bit()) {
                    return jSONArray;
                }
                object = bl2 ? this.readValue() : this.readString();
                jSONArray.put(object);
                continue;
            }
            object = bl2 ? this.readString() : this.readValue();
            jSONArray.put(object);
        } while (true);
    }

    private Object readJSON() throws JSONException {
        switch (this.read(3)) {
            default: {
                return JSONObject.NULL;
            }
            case 5: {
                return this.readObject();
            }
            case 6: {
                return this.readArray(true);
            }
            case 7: {
                return this.readArray(false);
            }
            case 0: {
                return new JSONObject();
            }
            case 1: {
                return new JSONArray();
            }
            case 2: {
                return Boolean.TRUE;
            }
            case 3: 
        }
        return Boolean.FALSE;
    }

    private String readName() throws JSONException {
        Object object = new byte[65536];
        int n2 = 0;
        if (!this.bit()) {
            do {
                int n3;
                if ((n3 = this.namehuff.read(this.bitreader)) == 256) {
                    if (n2 != 0) break;
                    return "";
                }
                object[n2] = (byte)n3;
                ++n2;
            } while (true);
            object = new Kim((byte[])object, n2);
            this.namekeep.register(object);
            return object.toString();
        }
        return this.getAndTick(this.namekeep, this.bitreader).toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    private JSONObject readObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        do {
            String string2 = this.readName();
            String string3 = !this.bit() ? this.readString() : this.readValue();
            jSONObject.put(string2, string3);
        } while (this.bit());
        return jSONObject;
    }

    private String readString() throws JSONException {
        int n2 = 0;
        int n3 = -1;
        int n4 = 0;
        if (this.bit()) {
            return this.getAndTick(this.stringkeep, this.bitreader).toString();
        }
        Object object = new byte[65536];
        boolean bl2 = this.bit();
        this.substringkeep.reserve();
        block0 : do {
            int n5;
            if (bl2) {
                n5 = ((Kim)this.getAndTick(this.substringkeep, this.bitreader)).copy((byte[])object, n2);
                if (n3 != -1) {
                    this.substringkeep.registerOne(new Kim((byte[])object, n3, n4 + 1));
                }
                n4 = n5;
                bl2 = this.bit();
                n3 = n2;
                n2 = n5;
                continue;
            }
            do {
                if ((n5 = this.substringhuff.read(this.bitreader)) == 256) {
                    if (this.bit()) break;
                    if (n2 != 0) break block0;
                    return "";
                }
                object[n2] = (byte)n5;
                n2 = n5 = n2 + 1;
                if (n3 == -1) continue;
                this.substringkeep.registerOne(new Kim((byte[])object, n3, n4 + 1));
                n3 = -1;
                n2 = n5;
            } while (true);
            bl2 = true;
        } while (true);
        object = new Kim((byte[])object, n2);
        this.stringkeep.register(object);
        this.substringkeep.registerMany((Kim)object);
        return object.toString();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Object readValue() throws JSONException {
        int n2 = 4;
        switch (this.read(2)) {
            default: {
                throw new JSONException("Impossible.");
            }
            case 0: {
                if (!this.bit()) {
                    return new Integer(this.read(n2));
                }
                if (!this.bit()) {
                    n2 = 7;
                    return new Integer(this.read(n2));
                }
                n2 = 14;
                return new Integer(this.read(n2));
            }
            case 1: {
                Object object = new byte[256];
                n2 = 0;
                do {
                    int n3;
                    if ((n3 = this.read(4)) == endOfNumber) {
                        object = JSONObject.stringToValue(new String((byte[])object, 0, n2, "US-ASCII"));
                        this.values.register(object);
                        return object;
                    }
                    object[n2] = bcd[n3];
                    ++n2;
                } while (true);
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    throw new JSONException(unsupportedEncodingException);
                }
            }
            case 2: {
                return this.getAndTick(this.values, this.bitreader);
            }
            case 3: 
        }
        return this.readJSON();
    }

    public boolean pad(int n2) throws JSONException {
        try {
            boolean bl2 = this.bitreader.pad(n2);
            return bl2;
        }
        catch (Throwable var3_3) {
            throw new JSONException(var3_3);
        }
    }

    public Object unzip() throws JSONException {
        this.begin();
        return this.readJSON();
    }
}

