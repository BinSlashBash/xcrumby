package com.google.android.gms.internal;

import com.crumby.C0065R;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.internal.C0339c.C1360f;
import com.google.android.gms.internal.C0339c.C1364j;
import java.io.IOException;
import org.json.zip.JSONzip;

public interface it {

    /* renamed from: com.google.android.gms.internal.it.a */
    public static final class C1393a extends kp<C1393a> {
        public long aaY;
        public C1364j aaZ;
        public C1360f fK;

        public C1393a() {
            lV();
        }

        public static C1393a m3115l(byte[] bArr) throws ks {
            return (C1393a) kt.m1166a(new C1393a(), bArr);
        }

        public void m3116a(ko koVar) throws IOException {
            koVar.m1151b(1, this.aaY);
            if (this.fK != null) {
                koVar.m1147a(2, this.fK);
            }
            if (this.aaZ != null) {
                koVar.m1147a(3, this.aaZ);
            }
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m3117b(kn knVar) throws IOException {
            return m3119n(knVar);
        }

        public int m3118c() {
            int c = super.m1172c() + ko.m1139d(1, this.aaY);
            if (this.fK != null) {
                c += ko.m1134b(2, this.fK);
            }
            if (this.aaZ != null) {
                c += ko.m1134b(3, this.aaZ);
            }
            this.adY = c;
            return c;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1393a)) {
                return false;
            }
            C1393a c1393a = (C1393a) o;
            if (this.aaY != c1393a.aaY) {
                return false;
            }
            if (this.fK == null) {
                if (c1393a.fK != null) {
                    return false;
                }
            } else if (!this.fK.equals(c1393a.fK)) {
                return false;
            }
            if (this.aaZ == null) {
                if (c1393a.aaZ != null) {
                    return false;
                }
            } else if (!this.aaZ.equals(c1393a.aaZ)) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1393a.adU == null || c1393a.adU.isEmpty();
            } else {
                return this.adU.equals(c1393a.adU);
            }
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.aaZ == null ? 0 : this.aaZ.hashCode()) + (((this.fK == null ? 0 : this.fK.hashCode()) + ((((int) (this.aaY ^ (this.aaY >>> 32))) + 527) * 31)) * 31)) * 31;
            if (!(this.adU == null || this.adU.isEmpty())) {
                i = this.adU.hashCode();
            }
            return hashCode + i;
        }

        public C1393a lV() {
            this.aaY = 0;
            this.fK = null;
            this.aaZ = null;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        public C1393a m3119n(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_LOCALE /*8*/:
                        this.aaY = knVar.mj();
                        continue;
                    case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                        if (this.fK == null) {
                            this.fK = new C1360f();
                        }
                        knVar.m1126a(this.fK);
                        continue;
                    case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                        if (this.aaZ == null) {
                            this.aaZ = new C1364j();
                        }
                        knVar.m1126a(this.aaZ);
                        continue;
                    default:
                        if (!m2325a(knVar, mh)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }
    }
}
