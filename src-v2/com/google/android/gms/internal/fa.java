/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fu;

public final class fa
extends fu<a, Drawable> {
    public fa() {
        super(10);
    }

    public static final class a {
        public final int CR;
        public final int CS;

        public a(int n2, int n3) {
            this.CR = n2;
            this.CS = n3;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            boolean bl2 = true;
            if (!(object instanceof a)) {
                return false;
            }
            boolean bl3 = bl2;
            if (this == object) return bl3;
            object = (a)object;
            if (object.CR != this.CR) return false;
            bl3 = bl2;
            if (object.CS == this.CS) return bl3;
            return false;
        }

        public int hashCode() {
            return fo.hashCode(this.CR, this.CS);
        }
    }

}

