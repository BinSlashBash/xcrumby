/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.squareup.picasso;

import android.graphics.Bitmap;

public interface Cache {
    public static final Cache NONE = new Cache(){

        @Override
        public void clear() {
        }

        @Override
        public Bitmap get(String string2) {
            return null;
        }

        @Override
        public int maxSize() {
            return 0;
        }

        @Override
        public void set(String string2, Bitmap bitmap) {
        }

        @Override
        public int size() {
            return 0;
        }
    };

    public void clear();

    public Bitmap get(String var1);

    public int maxSize();

    public void set(String var1, Bitmap var2);

    public int size();

}

