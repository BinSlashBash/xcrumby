/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.MemberKey;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;

public final class AnnotatedMethodMap
implements Iterable<AnnotatedMethod> {
    protected LinkedHashMap<MemberKey, AnnotatedMethod> _methods;

    public void add(AnnotatedMethod annotatedMethod) {
        if (this._methods == null) {
            this._methods = new LinkedHashMap();
        }
        this._methods.put(new MemberKey((Method)annotatedMethod.getAnnotated()), annotatedMethod);
    }

    public AnnotatedMethod find(String string2, Class<?>[] arrclass) {
        if (this._methods == null) {
            return null;
        }
        return this._methods.get(new MemberKey(string2, arrclass));
    }

    public AnnotatedMethod find(Method method) {
        if (this._methods == null) {
            return null;
        }
        return this._methods.get(new MemberKey(method));
    }

    public boolean isEmpty() {
        if (this._methods == null || this._methods.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Iterator<AnnotatedMethod> iterator() {
        if (this._methods != null) {
            return this._methods.values().iterator();
        }
        return Collections.emptyList().iterator();
    }

    public AnnotatedMethod remove(AnnotatedMethod annotatedMethod) {
        return this.remove((Method)annotatedMethod.getAnnotated());
    }

    public AnnotatedMethod remove(Method method) {
        if (this._methods != null) {
            return this._methods.remove(new MemberKey(method));
        }
        return null;
    }

    public int size() {
        if (this._methods == null) {
            return 0;
        }
        return this._methods.size();
    }
}

