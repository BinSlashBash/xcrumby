/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.otto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class EventProducer {
    private final int hashCode;
    private final Method method;
    final Object target;
    private boolean valid = true;

    EventProducer(Object object, Method method) {
        if (object == null) {
            throw new NullPointerException("EventProducer target cannot be null.");
        }
        if (method == null) {
            throw new NullPointerException("EventProducer method cannot be null.");
        }
        this.target = object;
        this.method = method;
        method.setAccessible(true);
        this.hashCode = (method.hashCode() + 31) * 31 + object.hashCode();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (EventProducer)object;
        if (!this.method.equals(object.method)) return false;
        if (this.target == object.target) return true;
        return false;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public void invalidate() {
        this.valid = false;
    }

    public boolean isValid() {
        return this.valid;
    }

    public Object produceEvent() throws InvocationTargetException {
        if (!this.valid) {
            throw new IllegalStateException(this.toString() + " has been invalidated and can no longer produce events.");
        }
        try {
            Object object = this.method.invoke(this.target, new Object[0]);
            return object;
        }
        catch (IllegalAccessException var1_2) {
            throw new AssertionError(var1_2);
        }
        catch (InvocationTargetException var1_3) {
            if (var1_3.getCause() instanceof Error) {
                throw (Error)var1_3.getCause();
            }
            throw var1_3;
        }
    }

    public String toString() {
        return "[EventProducer " + this.method + "]";
    }
}

