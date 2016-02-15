package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;

public final class fa extends fu<C0389a, Drawable> {

    /* renamed from: com.google.android.gms.internal.fa.a */
    public static final class C0389a {
        public final int CR;
        public final int CS;

        public C0389a(int i, int i2) {
            this.CR = i;
            this.CS = i2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0389a)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C0389a c0389a = (C0389a) obj;
            return c0389a.CR == this.CR && c0389a.CS == this.CS;
        }

        public int hashCode() {
            return fo.hashCode(Integer.valueOf(this.CR), Integer.valueOf(this.CS));
        }
    }

    public fa() {
        super(10);
    }
}
