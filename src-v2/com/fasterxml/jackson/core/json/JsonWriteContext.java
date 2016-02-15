/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.json.DupDetector;

public class JsonWriteContext
extends JsonStreamContext {
    public static final int STATUS_EXPECT_NAME = 5;
    public static final int STATUS_EXPECT_VALUE = 4;
    public static final int STATUS_OK_AFTER_COLON = 2;
    public static final int STATUS_OK_AFTER_COMMA = 1;
    public static final int STATUS_OK_AFTER_SPACE = 3;
    public static final int STATUS_OK_AS_IS = 0;
    protected JsonWriteContext _child = null;
    protected String _currentName;
    protected final DupDetector _dups;
    protected boolean _gotName;
    protected final JsonWriteContext _parent;

    protected JsonWriteContext(int n2, JsonWriteContext jsonWriteContext, DupDetector dupDetector) {
        this._type = n2;
        this._parent = jsonWriteContext;
        this._dups = dupDetector;
        this._index = -1;
    }

    private final void _checkDup(DupDetector dupDetector, String string2) throws JsonProcessingException {
        if (dupDetector.isDup(string2)) {
            throw new JsonGenerationException("Duplicate field '" + string2 + "'");
        }
    }

    @Deprecated
    public static JsonWriteContext createRootContext() {
        return JsonWriteContext.createRootContext(null);
    }

    public static JsonWriteContext createRootContext(DupDetector dupDetector) {
        return new JsonWriteContext(0, null, dupDetector);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void appendDesc(StringBuilder stringBuilder) {
        if (this._type == 2) {
            stringBuilder.append('{');
            if (this._currentName != null) {
                stringBuilder.append('\"');
                stringBuilder.append(this._currentName);
                stringBuilder.append('\"');
            } else {
                stringBuilder.append('?');
            }
            stringBuilder.append('}');
            return;
        }
        if (this._type == 1) {
            stringBuilder.append('[');
            stringBuilder.append(this.getCurrentIndex());
            stringBuilder.append(']');
            return;
        }
        stringBuilder.append("/");
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonWriteContext createChildArrayContext() {
        Object object = this._child;
        if (object != null) {
            return object.reset(1);
        }
        object = this._dups == null ? null : this._dups.child();
        this._child = object = new JsonWriteContext(1, this, (DupDetector)object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonWriteContext createChildObjectContext() {
        Object object = this._child;
        if (object != null) {
            return object.reset(2);
        }
        object = this._dups == null ? null : this._dups.child();
        this._child = object = new JsonWriteContext(2, this, (DupDetector)object);
        return object;
    }

    @Override
    public final String getCurrentName() {
        return this._currentName;
    }

    @Override
    public final JsonWriteContext getParent() {
        return this._parent;
    }

    protected JsonWriteContext reset(int n2) {
        this._type = n2;
        this._index = -1;
        this._currentName = null;
        this._gotName = false;
        if (this._dups != null) {
            this._dups.reset();
        }
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        this.appendDesc(stringBuilder);
        return stringBuilder.toString();
    }

    public final int writeFieldName(String string2) throws JsonProcessingException {
        int n2 = 1;
        this._gotName = true;
        this._currentName = string2;
        if (this._dups != null) {
            this._checkDup(this._dups, string2);
        }
        if (this._index < 0) {
            n2 = 0;
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int writeValue() {
        int n2 = 0;
        if (this._type == 2) {
            this._gotName = false;
            ++this._index;
            return 2;
        }
        if (this._type == 1) {
            int n3;
            if ((n3 = this._index++) < 0) return n2;
            return 1;
        }
        ++this._index;
        if (this._index == 0) return n2;
        return 3;
    }
}

