/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.picasso;

public interface Callback {
    public void onError(Exception var1);

    public void onSuccess();

    public static class EmptyCallback
    implements Callback {
        @Override
        public void onError(Exception exception) {
        }

        @Override
        public void onSuccess() {
        }
    }

}

