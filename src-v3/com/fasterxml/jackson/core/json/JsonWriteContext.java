package com.fasterxml.jackson.core.json;

import com.crumby.impl.device.DeviceFragment;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;

public class JsonWriteContext extends JsonStreamContext {
    public static final int STATUS_EXPECT_NAME = 5;
    public static final int STATUS_EXPECT_VALUE = 4;
    public static final int STATUS_OK_AFTER_COLON = 2;
    public static final int STATUS_OK_AFTER_COMMA = 1;
    public static final int STATUS_OK_AFTER_SPACE = 3;
    public static final int STATUS_OK_AS_IS = 0;
    protected JsonWriteContext _child;
    protected String _currentName;
    protected final DupDetector _dups;
    protected boolean _gotName;
    protected final JsonWriteContext _parent;

    protected JsonWriteContext(int type, JsonWriteContext parent, DupDetector dups) {
        this._child = null;
        this._type = type;
        this._parent = parent;
        this._dups = dups;
        this._index = -1;
    }

    protected JsonWriteContext reset(int type) {
        this._type = type;
        this._index = -1;
        this._currentName = null;
        this._gotName = false;
        if (this._dups != null) {
            this._dups.reset();
        }
        return this;
    }

    @Deprecated
    public static JsonWriteContext createRootContext() {
        return createRootContext(null);
    }

    public static JsonWriteContext createRootContext(DupDetector dd) {
        return new JsonWriteContext(0, null, dd);
    }

    public JsonWriteContext createChildArrayContext() {
        JsonWriteContext ctxt = this._child;
        if (ctxt != null) {
            return ctxt.reset(STATUS_OK_AFTER_COMMA);
        }
        ctxt = new JsonWriteContext(STATUS_OK_AFTER_COMMA, this, this._dups == null ? null : this._dups.child());
        this._child = ctxt;
        return ctxt;
    }

    public JsonWriteContext createChildObjectContext() {
        JsonWriteContext ctxt = this._child;
        if (ctxt != null) {
            return ctxt.reset(STATUS_OK_AFTER_COLON);
        }
        ctxt = new JsonWriteContext(STATUS_OK_AFTER_COLON, this, this._dups == null ? null : this._dups.child());
        this._child = ctxt;
        return ctxt;
    }

    public final JsonWriteContext getParent() {
        return this._parent;
    }

    public final String getCurrentName() {
        return this._currentName;
    }

    public final int writeFieldName(String name) throws JsonProcessingException {
        this._gotName = true;
        this._currentName = name;
        if (this._dups != null) {
            _checkDup(this._dups, name);
        }
        if (this._index < 0) {
            return 0;
        }
        return STATUS_OK_AFTER_COMMA;
    }

    private final void _checkDup(DupDetector dd, String name) throws JsonProcessingException {
        if (dd.isDup(name)) {
            throw new JsonGenerationException("Duplicate field '" + name + "'");
        }
    }

    public final int writeValue() {
        if (this._type == STATUS_OK_AFTER_COLON) {
            this._gotName = false;
            this._index += STATUS_OK_AFTER_COMMA;
            return STATUS_OK_AFTER_COLON;
        } else if (this._type == STATUS_OK_AFTER_COMMA) {
            int ix = this._index;
            this._index += STATUS_OK_AFTER_COMMA;
            if (ix >= 0) {
                return STATUS_OK_AFTER_COMMA;
            }
            return 0;
        } else {
            this._index += STATUS_OK_AFTER_COMMA;
            if (this._index != 0) {
                return STATUS_OK_AFTER_SPACE;
            }
            return 0;
        }
    }

    protected void appendDesc(StringBuilder sb) {
        if (this._type == STATUS_OK_AFTER_COLON) {
            sb.append('{');
            if (this._currentName != null) {
                sb.append('\"');
                sb.append(this._currentName);
                sb.append('\"');
            } else {
                sb.append('?');
            }
            sb.append('}');
        } else if (this._type == STATUS_OK_AFTER_COMMA) {
            sb.append('[');
            sb.append(getCurrentIndex());
            sb.append(']');
        } else {
            sb.append(DeviceFragment.REGEX_BASE);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        appendDesc(sb);
        return sb.toString();
    }
}
