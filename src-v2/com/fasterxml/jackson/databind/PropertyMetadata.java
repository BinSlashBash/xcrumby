/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import java.io.Serializable;

public class PropertyMetadata
implements Serializable {
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
    protected PropertyMetadata(Boolean bl2, String string2) {
        this(bl2, string2, null);
    }

    protected PropertyMetadata(Boolean bl2, String string2, Integer n2) {
        this._required = bl2;
        this._description = string2;
        this._index = n2;
    }

    @Deprecated
    public static PropertyMetadata construct(boolean bl2, String string2) {
        return PropertyMetadata.construct(bl2, string2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static PropertyMetadata construct(boolean bl2, String object, Integer n2) {
        PropertyMetadata propertyMetadata = bl2 ? STD_REQUIRED : STD_OPTIONAL;
        PropertyMetadata propertyMetadata2 = propertyMetadata;
        if (object != null) {
            propertyMetadata2 = propertyMetadata.withDescription((String)object);
        }
        object = propertyMetadata2;
        if (n2 == null) return object;
        return propertyMetadata2.withIndex(n2);
    }

    public String getDescription() {
        return this._description;
    }

    public Integer getIndex() {
        return this._index;
    }

    public Boolean getRequired() {
        return this._required;
    }

    public boolean hasIndex() {
        if (this._index != null) {
            return true;
        }
        return false;
    }

    public boolean isRequired() {
        if (this._required != null && this._required.booleanValue()) {
            return true;
        }
        return false;
    }

    protected Object readResolve() {
        if (this._description == null && this._index == null) {
            if (this._required == null) {
                return STD_REQUIRED_OR_OPTIONAL;
            }
            if (this._required.booleanValue()) {
                return STD_REQUIRED;
            }
            return STD_OPTIONAL;
        }
        return this;
    }

    public PropertyMetadata withDescription(String string2) {
        return new PropertyMetadata(this._required, string2, this._index);
    }

    public PropertyMetadata withIndex(Integer n2) {
        return new PropertyMetadata(this._required, this._description, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public PropertyMetadata withRequired(Boolean bl2) {
        if (bl2 == null ? this._required == null : this._required != null && this._required.booleanValue() == bl2.booleanValue()) {
            return this;
        }
        return new PropertyMetadata(bl2, this._description, this._index);
    }
}

