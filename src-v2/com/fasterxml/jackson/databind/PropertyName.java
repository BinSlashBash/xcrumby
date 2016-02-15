/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.util.InternCache;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import java.io.Serializable;

public class PropertyName
implements Serializable {
    public static final PropertyName NO_NAME;
    public static final PropertyName USE_DEFAULT;
    private static final String _NO_NAME = "";
    private static final String _USE_DEFAULT = "";
    private static final long serialVersionUID = 7930806520033045126L;
    protected SerializableString _encodedSimple;
    protected final String _namespace;
    protected final String _simpleName;

    static {
        USE_DEFAULT = new PropertyName("", null);
        NO_NAME = new PropertyName(new String(""), null);
    }

    public PropertyName(String string2) {
        this(string2, null);
    }

    public PropertyName(String string2, String string3) {
        String string4 = string2;
        if (string2 == null) {
            string4 = "";
        }
        this._simpleName = string4;
        this._namespace = string3;
    }

    public static PropertyName construct(String string2, String string3) {
        String string4 = string2;
        if (string2 == null) {
            string4 = "";
        }
        if (string3 == null && string4.length() == 0) {
            return USE_DEFAULT;
        }
        return new PropertyName(string4, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl2 = true;
        boolean bl3 = false;
        if (object == this) {
            return true;
        }
        boolean bl4 = bl3;
        if (object == null) return bl4;
        bl4 = bl3;
        if (object.getClass() != this.getClass()) return bl4;
        object = (PropertyName)object;
        if (this._simpleName == null) {
            bl4 = bl3;
            if (object._simpleName != null) return bl4;
        } else if (!this._simpleName.equals(object._simpleName)) {
            return false;
        }
        if (this._namespace != null) return this._namespace.equals(object._namespace);
        if (object._namespace != null) return false;
        return bl2;
    }

    public String getNamespace() {
        return this._namespace;
    }

    public String getSimpleName() {
        return this._simpleName;
    }

    public boolean hasNamespace() {
        if (this._namespace != null) {
            return true;
        }
        return false;
    }

    public boolean hasSimpleName() {
        if (this._simpleName.length() > 0) {
            return true;
        }
        return false;
    }

    public boolean hasSimpleName(String string2) {
        if (string2 == null) {
            if (this._simpleName == null) {
                return true;
            }
            return false;
        }
        return string2.equals(this._simpleName);
    }

    public int hashCode() {
        if (this._namespace == null) {
            return this._simpleName.hashCode();
        }
        return this._namespace.hashCode() ^ this._simpleName.hashCode();
    }

    /*
     * Enabled aggressive block sorting
     */
    public PropertyName internSimpleName() {
        String string2;
        if (this._simpleName.length() == 0 || (string2 = InternCache.instance.intern(this._simpleName)) == this._simpleName) {
            return this;
        }
        return new PropertyName(string2, this._namespace);
    }

    public boolean isEmpty() {
        if (this._namespace == null && this._simpleName.isEmpty()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Object readResolve() {
        if (this._simpleName == null) return USE_DEFAULT;
        if ("".equals(this._simpleName)) {
            return USE_DEFAULT;
        }
        PropertyName propertyName = this;
        if (!this._simpleName.equals("")) return propertyName;
        propertyName = this;
        if (this._namespace != null) return propertyName;
        return NO_NAME;
    }

    public SerializableString simpleAsEncoded(MapperConfig<?> mapperConfig) {
        SerializableString serializableString;
        SerializableString serializableString2 = serializableString = this._encodedSimple;
        if (serializableString == null) {
            this._encodedSimple = serializableString2 = mapperConfig.compileString(this._simpleName);
        }
        return serializableString2;
    }

    public String toString() {
        if (this._namespace == null) {
            return this._simpleName;
        }
        return "{" + this._namespace + "}" + this._simpleName;
    }

    /*
     * Enabled aggressive block sorting
     */
    public PropertyName withNamespace(String string2) {
        if (string2 == null ? this._namespace == null : string2.equals(this._namespace)) {
            return this;
        }
        return new PropertyName(this._simpleName, string2);
    }

    public PropertyName withSimpleName(String string2) {
        String string3 = string2;
        if (string2 == null) {
            string3 = "";
        }
        if (string3.equals(this._simpleName)) {
            return this;
        }
        return new PropertyName(string3, this._namespace);
    }
}

