/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.c;
import com.google.android.gms.internal.kn;
import com.google.android.gms.internal.ko;
import com.google.android.gms.internal.kp;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;
import java.io.IOException;
import java.util.List;

public interface it {

    public static final class a
    extends kp<a> {
        public long aaY;
        public c.j aaZ;
        public c.f fK;

        public a() {
            this.lV();
        }

        public static a l(byte[] arrby) throws ks {
            return kt.a(new a(), arrby);
        }

        @Override
        public void a(ko ko2) throws IOException {
            ko2.b(1, this.aaY);
            if (this.fK != null) {
                ko2.a(2, this.fK);
            }
            if (this.aaZ != null) {
                ko2.a(3, this.aaZ);
            }
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.n(kn2);
        }

        @Override
        public int c() {
            int n2;
            int n3 = n2 = super.c() + ko.d(1, this.aaY);
            if (this.fK != null) {
                n3 = n2 + ko.b(2, this.fK);
            }
            n2 = n3;
            if (this.aaZ != null) {
                n2 = n3 + ko.b(3, this.aaZ);
            }
            this.adY = n2;
            return n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            boolean bl2 = false;
            if (object == this) {
                return true;
            }
            boolean bl3 = bl2;
            if (!(object instanceof a)) return bl3;
            object = (a)object;
            bl3 = bl2;
            if (this.aaY != object.aaY) return bl3;
            if (this.fK == null) {
                bl3 = bl2;
                if (object.fK != null) return bl3;
            } else if (!this.fK.equals(object.fK)) {
                return false;
            }
            if (this.aaZ == null) {
                bl3 = bl2;
                if (object.aaZ != null) return bl3;
            } else if (!this.aaZ.equals(object.aaZ)) {
                return false;
            }
            if (this.adU != null && !this.adU.isEmpty()) {
                return this.adU.equals(object.adU);
            }
            if (object.adU == null) return true;
            bl3 = bl2;
            if (!object.adU.isEmpty()) return bl3;
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int hashCode() {
            int n2 = 0;
            int n3 = (int)(this.aaY ^ this.aaY >>> 32);
            int n4 = this.fK == null ? 0 : this.fK.hashCode();
            int n5 = this.aaZ == null ? 0 : this.aaZ.hashCode();
            int n6 = n2;
            if (this.adU == null) return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
            if (this.adU.isEmpty()) {
                n6 = n2;
                return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
            }
            n6 = this.adU.hashCode();
            return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
        }

        public a lV() {
            this.aaY = 0;
            this.fK = null;
            this.aaZ = null;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        public a n(kn kn2) throws IOException {
            block6 : do {
                int n2 = kn2.mh();
                switch (n2) {
                    default: {
                        if (this.a(kn2, n2)) continue block6;
                    }
                    case 0: {
                        return this;
                    }
                    case 8: {
                        this.aaY = kn2.mj();
                        continue block6;
                    }
                    case 18: {
                        if (this.fK == null) {
                            this.fK = new c.f();
                        }
                        kn2.a(this.fK);
                        continue block6;
                    }
                    case 26: 
                }
                if (this.aaZ == null) {
                    this.aaZ = new c.j();
                }
                kn2.a(this.aaZ);
            } while (true);
        }
    }

}

