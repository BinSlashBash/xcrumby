/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.otto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class EventHandler {
    private final int hashCode;
    private final Method method;
    private final Object target;
    private boolean valid = true;

    EventHandler(Object object, Method method) {
        if (object == null) {
            throw new NullPointerException("EventHandler target cannot be null.");
        }
        if (method == null) {
            throw new NullPointerException("EventHandler method cannot be null.");
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
        object = (EventHandler)object;
        if (!this.method.equals(object.method)) return false;
        if (this.target == object.target) return true;
        return false;
    }

    public void handleEvent(Object object) throws InvocationTargetException {
        if (!this.valid) {
            throw new IllegalStateException(this.toString() + " has been invalidated and can no longer handle events.");
        }
        try {
            this.method.invoke(this.target, object);
            return;
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

    public int hashCode() {
        return this.hashCode;
    }

    public void invalidate() {
        this.valid = false;
    }

    public boolean isValid() {
        return this.valid;
    }

    public String toString() {
        return "[EventHandler " + this.method + "]";
    }
}

