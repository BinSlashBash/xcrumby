/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class MemberKey {
    static final Class<?>[] NO_CLASSES = new Class[0];
    final Class<?>[] _argTypes;
    final String _name;

    public MemberKey(String arrclass, Class<?>[] arrclass2) {
        this._name = arrclass;
        arrclass = arrclass2;
        if (arrclass2 == null) {
            arrclass = NO_CLASSES;
        }
        this._argTypes = arrclass;
    }

    public MemberKey(Constructor<?> constructor) {
        this("", constructor.getParameterTypes());
    }

    public MemberKey(Method method) {
        this(method.getName(), method.getParameterTypes());
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object arrclass) {
        if (arrclass == this) {
            return true;
        }
        if (arrclass == null) {
            return false;
        }
        if (arrclass.getClass() != this.getClass()) {
            return false;
        }
        arrclass = (MemberKey)arrclass;
        if (!this._name.equals(arrclass._name)) {
            return false;
        }
        arrclass = arrclass._argTypes;
        int n2 = this._argTypes.length;
        if (arrclass.length != n2) {
            return false;
        }
        int n3 = 0;
        while (n3 < n2) {
            Class class_ = arrclass[n3];
            Class class_2 = this._argTypes[n3];
            if (class_ != class_2 && !class_.isAssignableFrom(class_2) && !class_2.isAssignableFrom(class_)) {
                return false;
            }
            ++n3;
        }
        return true;
    }

    public int hashCode() {
        return this._name.hashCode() + this._argTypes.length;
    }

    public String toString() {
        return this._name + "(" + this._argTypes.length + "-args)";
    }
}

