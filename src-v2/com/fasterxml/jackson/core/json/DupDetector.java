/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import java.util.HashSet;

public class DupDetector {
    protected String _firstName;
    protected String _secondName;
    protected HashSet<String> _seen;
    protected final Object _source;

    private DupDetector(Object object) {
        this._source = object;
    }

    public static DupDetector rootDetector(JsonGenerator jsonGenerator) {
        return new DupDetector(jsonGenerator);
    }

    public static DupDetector rootDetector(JsonParser jsonParser) {
        return new DupDetector(jsonParser);
    }

    public DupDetector child() {
        return new DupDetector(this._source);
    }

    public JsonLocation findLocation() {
        if (this._source instanceof JsonParser) {
            return ((JsonParser)this._source).getCurrentLocation();
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isDup(String string2) throws JsonParseException {
        boolean bl2 = true;
        if (this._firstName == null) {
            this._firstName = string2;
            return false;
        }
        boolean bl3 = bl2;
        if (string2.equals(this._firstName)) return bl3;
        if (this._secondName == null) {
            this._secondName = string2;
            return false;
        }
        bl3 = bl2;
        if (string2.equals(this._secondName)) return bl3;
        if (this._seen == null) {
            this._seen = new HashSet(16);
            this._seen.add(this._firstName);
            this._seen.add(this._secondName);
        }
        bl3 = bl2;
        if (!this._seen.add(string2)) return bl3;
        return false;
    }

    public void reset() {
        this._firstName = null;
        this._secondName = null;
        this._seen = null;
    }
}

