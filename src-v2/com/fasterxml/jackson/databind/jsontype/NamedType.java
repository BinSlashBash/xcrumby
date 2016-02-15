/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype;

import java.io.Serializable;

public final class NamedType
implements Serializable {
    private static final long serialVersionUID = 1;
    protected final Class<?> _class;
    protected final int _hashCode;
    protected String _name;

    public NamedType(Class<?> class_) {
        this(class_, null);
    }

    public NamedType(Class<?> class_, String string2) {
        this._class = class_;
        this._hashCode = class_.getName().hashCode();
        this.setName(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        if (this._class == ((NamedType)object)._class) return true;
        return false;
    }

    public String getName() {
        return this._name;
    }

    public Class<?> getType() {
        return this._class;
    }

    public boolean hasName() {
        if (this._name != null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this._hashCode;
    }

    public void setName(String string2) {
        String string3;
        block2 : {
            if (string2 != null) {
                string3 = string2;
                if (string2.length() != 0) break block2;
            }
            string3 = null;
        }
        this._name = string3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        String string2;
        StringBuilder stringBuilder = new StringBuilder().append("[NamedType, class ").append(this._class.getName()).append(", name: ");
        if (this._name == null) {
            string2 = "null";
            do {
                return stringBuilder.append(string2).append("]").toString();
                break;
            } while (true);
        }
        string2 = "'" + this._name + "'";
        return stringBuilder.append(string2).append("]").toString();
    }
}

