package com.squareup.picasso;

import android.graphics.Bitmap;

public interface Cache {
    public static final Cache NONE;

    /* renamed from: com.squareup.picasso.Cache.1 */
    static class C11661 implements Cache {
        C11661() {
        }

        public Bitmap get(String key) {
            return null;
        }

        public void set(String key, Bitmap bitmap) {
        }

        public int size() {
            return 0;
        }

        public int maxSize() {
            return 0;
        }

        public void clear() {
        }
    }

    void clear();

    Bitmap get(String str);

    int maxSize();

    void set(String str, Bitmap bitmap);

    int size();

    static {
        NONE = new C11661();
    }
}
