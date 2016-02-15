/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import java.io.Serializable;

public final class ClassKey
implements Comparable<ClassKey>,
Serializable {
    private static final long serialVersionUID = 1;
    private Class<?> _class;
    private String _className;
    private int _hashCode;

    public ClassKey() {
        this._class = null;
        this._className = null;
        this._hashCode = 0;
    }

    public ClassKey(Class<?> class_) {
        this._class = class_;
        this._className = class_.getName();
        this._hashCode = this._className.hashCode();
    }

    @Override
    public int compareTo(ClassKey classKey) {
        return this._className.compareTo(classKey._className);
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
        if (((ClassKey)object)._class == this._class) return true;
        return false;
    }

    public int hashCode() {
        return this._hashCode;
    }

    public void reset(Class<?> class_) {
        this._class = class_;
        this._className = class_.getName();
        this._hashCode = this._className.hashCode();
    }

    public String toString() {
        return this._className;
    }
}

