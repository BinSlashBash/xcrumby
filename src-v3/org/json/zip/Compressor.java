package org.json.zip;

import com.crumby.C0065R;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.Kim;

public class Compressor extends JSONzip {
    final BitWriter bitwriter;

    public Compressor(BitWriter bitwriter) {
        this.bitwriter = bitwriter;
    }

    private static int bcd(char digit) {
        if (digit >= '0' && digit <= '9') {
            return digit - 48;
        }
        switch (digit) {
            case C0065R.styleable.TwoWayView_android_hapticFeedbackEnabled /*43*/:
                return 12;
            case C0065R.styleable.TwoWayView_android_contentDescription /*45*/:
                return 11;
            case C0065R.styleable.TwoWayView_android_scrollbarFadeDuration /*46*/:
                return 10;
            default:
                return 13;
        }
    }

    public void flush() throws JSONException {
        pad(8);
    }

    private void one() throws JSONException {
        write(1, 1);
    }

    public void pad(int factor) throws JSONException {
        try {
            this.bitwriter.pad(factor);
        } catch (Throwable e) {
            JSONException jSONException = new JSONException(e);
        }
    }

    private void write(int integer, int width) throws JSONException {
        try {
            this.bitwriter.write(integer, width);
        } catch (Throwable e) {
            JSONException jSONException = new JSONException(e);
        }
    }

    private void write(int integer, Huff huff) throws JSONException {
        huff.write(integer, this.bitwriter);
    }

    private void write(Kim kim, Huff huff) throws JSONException {
        write(kim, 0, kim.length, huff);
    }

    private void write(Kim kim, int from, int thru, Huff huff) throws JSONException {
        for (int at = from; at < thru; at++) {
            write(kim.get(at), huff);
        }
    }

    private void writeAndTick(int integer, Keep keep) throws JSONException {
        int width = keep.bitsize();
        keep.tick(integer);
        write(integer, width);
    }

    private void writeArray(JSONArray jsonarray) throws JSONException {
        boolean stringy = false;
        int length = jsonarray.length();
        if (length == 0) {
            write(1, 3);
            return;
        }
        Object value = jsonarray.get(0);
        if (value == null) {
            value = JSONObject.NULL;
        }
        if (value instanceof String) {
            stringy = true;
            write(6, 3);
            writeString((String) value);
        } else {
            write(7, 3);
            writeValue(value);
        }
        for (int i = 1; i < length; i++) {
            value = jsonarray.get(i);
            if (value == null) {
                value = JSONObject.NULL;
            }
            if ((value instanceof String) != stringy) {
                zero();
            }
            one();
            if (value instanceof String) {
                writeString((String) value);
            } else {
                writeValue(value);
            }
        }
        zero();
        zero();
    }

    private void writeJSON(Object value) throws JSONException {
        if (JSONObject.NULL.equals(value)) {
            write(4, 3);
        } else if (Boolean.FALSE.equals(value)) {
            write(3, 3);
        } else if (Boolean.TRUE.equals(value)) {
            write(2, 3);
        } else {
            if (value instanceof Map) {
                value = new JSONObject((Map) value);
            } else if (value instanceof Collection) {
                value = new JSONArray((Collection) value);
            } else if (value.getClass().isArray()) {
                value = new JSONArray(value);
            }
            if (value instanceof JSONObject) {
                writeObject((JSONObject) value);
            } else if (value instanceof JSONArray) {
                writeArray((JSONArray) value);
            } else {
                throw new JSONException("Unrecognized object");
            }
        }
    }

    private void writeName(String name) throws JSONException {
        Kim kim = new Kim(name);
        int integer = this.namekeep.find(kim);
        if (integer != -1) {
            one();
            writeAndTick(integer, this.namekeep);
            return;
        }
        zero();
        write(kim, this.namehuff);
        write((int) JSONzip.end, this.namehuff);
        this.namekeep.register(kim);
    }

    private void writeObject(JSONObject jsonobject) throws JSONException {
        boolean first = true;
        Iterator keys = jsonobject.keys();
        while (keys.hasNext()) {
            Object key = keys.next();
            if (key instanceof String) {
                if (first) {
                    first = false;
                    write(5, 3);
                } else {
                    one();
                }
                writeName((String) key);
                Object value = jsonobject.get((String) key);
                if (value instanceof String) {
                    zero();
                    writeString((String) value);
                } else {
                    one();
                    writeValue(value);
                }
            }
        }
        if (first) {
            write(0, 3);
        } else {
            zero();
        }
    }

    private void writeString(String string) throws JSONException {
        if (string.length() == 0) {
            zero();
            zero();
            write((int) JSONzip.end, this.substringhuff);
            zero();
            return;
        }
        Kim kim = new Kim(string);
        int integer = this.stringkeep.find(kim);
        if (integer != -1) {
            one();
            writeAndTick(integer, this.stringkeep);
            return;
        }
        writeSubstring(kim);
        this.stringkeep.register(kim);
    }

    private void writeSubstring(Kim kim) throws JSONException {
        this.substringkeep.reserve();
        zero();
        int from = 0;
        int thru = kim.length;
        int until = thru - 3;
        int previousFrom = -1;
        int previousThru = 0;
        while (true) {
            int integer = -1;
            int at = from;
            while (at <= until) {
                integer = this.substringkeep.match(kim, at, thru);
                if (integer != -1) {
                    break;
                }
                at++;
            }
            if (integer == -1) {
                break;
            }
            if (from != at) {
                zero();
                write(kim, from, at, this.substringhuff);
                write((int) JSONzip.end, this.substringhuff);
                if (previousFrom != -1) {
                    this.substringkeep.registerOne(kim, previousFrom, previousThru);
                    previousFrom = -1;
                }
            }
            one();
            writeAndTick(integer, this.substringkeep);
            from = at + this.substringkeep.length(integer);
            if (previousFrom != -1) {
                this.substringkeep.registerOne(kim, previousFrom, previousThru);
            }
            previousFrom = at;
            previousThru = from + 1;
        }
        zero();
        if (from < thru) {
            write(kim, from, thru, this.substringhuff);
            if (previousFrom != -1) {
                this.substringkeep.registerOne(kim, previousFrom, previousThru);
            }
        }
        write((int) JSONzip.end, this.substringhuff);
        zero();
        this.substringkeep.registerMany(kim);
    }

    private void writeValue(Object value) throws JSONException {
        if (value instanceof Number) {
            String string = JSONObject.numberToString((Number) value);
            int integer = this.values.find(string);
            if (integer != -1) {
                write(2, 2);
                writeAndTick(integer, this.values);
                return;
            }
            if ((value instanceof Integer) || (value instanceof Long)) {
                long longer = ((Number) value).longValue();
                if (longer >= 0 && longer < JSONzip.int14) {
                    write(0, 2);
                    if (longer < 16) {
                        zero();
                        write((int) longer, 4);
                        return;
                    }
                    one();
                    if (longer < 128) {
                        zero();
                        write((int) longer, 7);
                        return;
                    }
                    one();
                    write((int) longer, 14);
                    return;
                }
            }
            write(1, 2);
            for (int i = 0; i < string.length(); i++) {
                write(bcd(string.charAt(i)), 4);
            }
            write(endOfNumber, 4);
            this.values.register(string);
            return;
        }
        write(3, 2);
        writeJSON(value);
    }

    private void zero() throws JSONException {
        write(0, 1);
    }

    public void zip(JSONObject jsonobject) throws JSONException {
        begin();
        writeJSON(jsonobject);
    }

    public void zip(JSONArray jsonarray) throws JSONException {
        begin();
        writeJSON(jsonarray);
    }
}
