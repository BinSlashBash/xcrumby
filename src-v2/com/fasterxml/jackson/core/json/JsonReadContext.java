/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.json.DupDetector;

public final class JsonReadContext
extends JsonStreamContext {
    protected JsonReadContext _child = null;
    protected int _columnNr;
    protected String _currentName;
    protected final DupDetector _dups;
    protected int _lineNr;
    protected final JsonReadContext _parent;

    public JsonReadContext(JsonReadContext jsonReadContext, DupDetector dupDetector, int n2, int n3, int n4) {
        this._parent = jsonReadContext;
        this._dups = dupDetector;
        this._type = n2;
        this._lineNr = n3;
        this._columnNr = n4;
        this._index = -1;
    }

    private void _checkDup(DupDetector dupDetector, String string2) throws JsonProcessingException {
        if (dupDetector.isDup(string2)) {
            throw new JsonParseException("Duplicate field '" + string2 + "'", dupDetector.findLocation());
        }
    }

    @Deprecated
    public static JsonReadContext createRootContext() {
        return JsonReadContext.createRootContext(null);
    }

    @Deprecated
    public static JsonReadContext createRootContext(int n2, int n3) {
        return JsonReadContext.createRootContext(n2, n3, null);
    }

    public static JsonReadContext createRootContext(int n2, int n3, DupDetector dupDetector) {
        return new JsonReadContext(null, dupDetector, 0, n2, n3);
    }

    public static JsonReadContext createRootContext(DupDetector dupDetector) {
        return new JsonReadContext(null, dupDetector, 0, 1, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonReadContext createChildArrayContext(int n2, int n3) {
        Object object = this._child;
        if (object != null) {
            object.reset(1, n2, n3);
            return object;
        }
        object = this._dups == null ? null : this._dups.child();
        this._child = object = new JsonReadContext(this, (DupDetector)object, 1, n2, n3);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonReadContext createChildObjectContext(int n2, int n3) {
        Object object = this._child;
        if (object != null) {
            object.reset(2, n2, n3);
            return object;
        }
        object = this._dups == null ? null : this._dups.child();
        this._child = object = new JsonReadContext(this, (DupDetector)object, 2, n2, n3);
        return object;
    }

    public boolean expectComma() {
        int n2;
        this._index = n2 = this._index + 1;
        if (this._type != 0 && n2 > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getCurrentName() {
        return this._currentName;
    }

    @Override
    public JsonReadContext getParent() {
        return this._parent;
    }

    public JsonLocation getStartLocation(Object object) {
        return new JsonLocation(object, -1, this._lineNr, this._columnNr);
    }

    protected void reset(int n2, int n3, int n4) {
        this._type = n2;
        this._index = -1;
        this._lineNr = n3;
        this._columnNr = n4;
        this._currentName = null;
        if (this._dups != null) {
            this._dups.reset();
        }
    }

    public void setCurrentName(String string2) throws JsonProcessingException {
        this._currentName = string2;
        if (this._dups != null) {
            this._checkDup(this._dups, string2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public String toString() {
        var1_1 = new StringBuilder(64);
        switch (this._type) {
            case 0: {
                var1_1.append("/");
                ** break;
            }
            case 1: {
                var1_1.append('[');
                var1_1.append(this.getCurrentIndex());
                var1_1.append(']');
            }
lbl10: // 3 sources:
            default: {
                return var1_1.toString();
            }
            case 2: 
        }
        var1_1.append('{');
        if (this._currentName != null) {
            var1_1.append('\"');
            CharTypes.appendQuoted(var1_1, this._currentName);
            var1_1.append('\"');
        } else {
            var1_1.append('?');
        }
        var1_1.append('}');
        return var1_1.toString();
    }
}

