/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal;

public abstract class NamedRunnable
implements Runnable {
    private final String name;

    public /* varargs */ NamedRunnable(String string2, Object ... arrobject) {
        this.name = String.format(string2, arrobject);
    }

    protected abstract void execute();

    @Override
    public final void run() {
        String string2 = Thread.currentThread().getName();
        Thread.currentThread().setName(this.name);
        try {
            this.execute();
            return;
        }
        finally {
            Thread.currentThread().setName(string2);
        }
    }
}

