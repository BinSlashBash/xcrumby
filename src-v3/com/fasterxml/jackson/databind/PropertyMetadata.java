package com.fasterxml.jackson.databind;

import java.io.Serializable;

public class PropertyMetadata implements Serializable {
    public static final PropertyMetadata STD_OPTIONAL;
    public static final PropertyMetadata STD_REQUIRED;
    public static final PropertyMetadata STD_REQUIRED_OR_OPTIONAL;
    private static final long serialVersionUID = -1;
    protected final String _description;
    protected final Integer _index;
    protected final Boolean _required;

    static {
        STD_REQUIRED = new PropertyMetadata(Boolean.TRUE, null, null);
        STD_OPTIONAL = new PropertyMetadata(Boolean.FALSE, null, null);
        STD_REQUIRED_OR_OPTIONAL = new PropertyMetadata(null, null, null);
    }

    @Deprecated
    protected PropertyMetadata(Boolean req, String desc) {
        this(req, desc, null);
    }

    protected PropertyMetadata(Boolean req, String desc, Integer index) {
        this._required = req;
        this._description = desc;
        this._index = index;
    }

    @Deprecated
    public static PropertyMetadata construct(boolean req, String desc) {
        return construct(req, desc, null);
    }

    public static PropertyMetadata construct(boolean req, String desc, Integer index) {
        PropertyMetadata md = req ? STD_REQUIRED : STD_OPTIONAL;
        if (desc != null) {
            md = md.withDescription(desc);
        }
        if (index != null) {
            return md.withIndex(index);
        }
        return md;
    }

    protected Object readResolve() {
        if (this._description != null || this._index != null) {
            return this;
        }
        if (this._required == null) {
            return STD_REQUIRED_OR_OPTIONAL;
        }
        return this._required.booleanValue() ? STD_REQUIRED : STD_OPTIONAL;
    }

    public PropertyMetadata withDescription(String desc) {
        return new PropertyMetadata(this._required, desc, this._index);
    }

    public PropertyMetadata withIndex(Integer index) {
        return new PropertyMetadata(this._required, this._description, index);
    }

    public PropertyMetadata withRequired(Boolean b) {
        if (b == null) {
            if (this._required == null) {
                return this;
            }
        } else if (this._required != null && this._required.booleanValue() == b.booleanValue()) {
            return this;
        }
        return new PropertyMetadata(b, this._description, this._index);
    }

    public String getDescription() {
        return this._description;
    }

    public boolean isRequired() {
        return this._required != null && this._required.booleanValue();
    }

    public Boolean getRequired() {
        return this._required;
    }

    public Integer getIndex() {
        return this._index;
    }

    public boolean hasIndex() {
        return this._index != null;
    }
}
