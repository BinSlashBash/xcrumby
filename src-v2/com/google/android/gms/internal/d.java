/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.kn;
import com.google.android.gms.internal.ko;
import com.google.android.gms.internal.kp;
import com.google.android.gms.internal.kr;
import com.google.android.gms.internal.kt;
import com.google.android.gms.internal.kw;
import java.io.IOException;
import java.util.List;

public interface d {

    public static final class a
    extends kp<a> {
        private static volatile a[] fM;
        public String fN;
        public a[] fO;
        public a[] fP;
        public a[] fQ;
        public String fR;
        public String fS;
        public long fT;
        public boolean fU;
        public a[] fV;
        public int[] fW;
        public boolean fX;
        public int type;

        public a() {
            this.s();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static a[] r() {
            if (fM == null) {
                Object object = kr.adX;
                synchronized (object) {
                    if (fM == null) {
                        fM = new a[0];
                    }
                }
            }
            return fM;
        }

        @Override
        public void a(ko ko2) throws IOException {
            int n2;
            a a2;
            int n3 = 0;
            ko2.i(1, this.type);
            if (!this.fN.equals("")) {
                ko2.b(2, this.fN);
            }
            if (this.fO != null && this.fO.length > 0) {
                for (n2 = 0; n2 < this.fO.length; ++n2) {
                    a2 = this.fO[n2];
                    if (a2 == null) continue;
                    ko2.a(3, a2);
                }
            }
            if (this.fP != null && this.fP.length > 0) {
                for (n2 = 0; n2 < this.fP.length; ++n2) {
                    a2 = this.fP[n2];
                    if (a2 == null) continue;
                    ko2.a(4, a2);
                }
            }
            if (this.fQ != null && this.fQ.length > 0) {
                for (n2 = 0; n2 < this.fQ.length; ++n2) {
                    a2 = this.fQ[n2];
                    if (a2 == null) continue;
                    ko2.a(5, a2);
                }
            }
            if (!this.fR.equals("")) {
                ko2.b(6, this.fR);
            }
            if (!this.fS.equals("")) {
                ko2.b(7, this.fS);
            }
            if (this.fT != 0) {
                ko2.b(8, this.fT);
            }
            if (this.fX) {
                ko2.a(9, this.fX);
            }
            if (this.fW != null && this.fW.length > 0) {
                for (n2 = 0; n2 < this.fW.length; ++n2) {
                    ko2.i(10, this.fW[n2]);
                }
            }
            if (this.fV != null && this.fV.length > 0) {
                for (n2 = n3; n2 < this.fV.length; ++n2) {
                    a2 = this.fV[n2];
                    if (a2 == null) continue;
                    ko2.a(11, a2);
                }
            }
            if (this.fU) {
                ko2.a(12, this.fU);
            }
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.l(kn2);
        }

        @Override
        public int c() {
            int n2;
            a a2;
            int n3;
            int n4 = 0;
            int n5 = n2 = super.c() + ko.j(1, this.type);
            if (!this.fN.equals("")) {
                n5 = n2 + ko.g(2, this.fN);
            }
            n2 = n5;
            if (this.fO != null) {
                n2 = n5;
                if (this.fO.length > 0) {
                    for (n2 = 0; n2 < this.fO.length; ++n2) {
                        a2 = this.fO[n2];
                        n3 = n5;
                        if (a2 != null) {
                            n3 = n5 + ko.b(3, a2);
                        }
                        n5 = n3;
                    }
                    n2 = n5;
                }
            }
            n5 = n2;
            if (this.fP != null) {
                n5 = n2;
                if (this.fP.length > 0) {
                    n5 = n2;
                    for (n2 = 0; n2 < this.fP.length; ++n2) {
                        a2 = this.fP[n2];
                        n3 = n5;
                        if (a2 != null) {
                            n3 = n5 + ko.b(4, a2);
                        }
                        n5 = n3;
                    }
                }
            }
            n2 = n5;
            if (this.fQ != null) {
                n2 = n5;
                if (this.fQ.length > 0) {
                    for (n2 = 0; n2 < this.fQ.length; ++n2) {
                        a2 = this.fQ[n2];
                        n3 = n5;
                        if (a2 != null) {
                            n3 = n5 + ko.b(5, a2);
                        }
                        n5 = n3;
                    }
                    n2 = n5;
                }
            }
            n5 = n2;
            if (!this.fR.equals("")) {
                n5 = n2 + ko.g(6, this.fR);
            }
            n2 = n5;
            if (!this.fS.equals("")) {
                n2 = n5 + ko.g(7, this.fS);
            }
            n5 = n2;
            if (this.fT != 0) {
                n5 = n2 + ko.d(8, this.fT);
            }
            n2 = n5;
            if (this.fX) {
                n2 = n5 + ko.b(9, this.fX);
            }
            n5 = n2;
            if (this.fW != null) {
                n5 = n2;
                if (this.fW.length > 0) {
                    n3 = 0;
                    for (n5 = 0; n5 < this.fW.length; ++n5) {
                        n3 += ko.cX(this.fW[n5]);
                    }
                    n5 = n2 + n3 + this.fW.length * 1;
                }
            }
            n2 = n5;
            if (this.fV != null) {
                n2 = n5;
                if (this.fV.length > 0) {
                    n3 = n4;
                    do {
                        n2 = n5;
                        if (n3 >= this.fV.length) break;
                        a2 = this.fV[n3];
                        n2 = n5;
                        if (a2 != null) {
                            n2 = n5 + ko.b(11, a2);
                        }
                        ++n3;
                        n5 = n2;
                    } while (true);
                }
            }
            n5 = n2;
            if (this.fU) {
                n5 = n2 + ko.b(12, this.fU);
            }
            this.adY = n5;
            return n5;
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
            if (this.type != object.type) return bl3;
            if (this.fN == null) {
                bl3 = bl2;
                if (object.fN != null) return bl3;
            } else if (!this.fN.equals(object.fN)) {
                return false;
            }
            bl3 = bl2;
            if (!kr.equals(this.fO, object.fO)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fP, object.fP)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fQ, object.fQ)) return bl3;
            if (this.fR == null) {
                bl3 = bl2;
                if (object.fR != null) return bl3;
            } else if (!this.fR.equals(object.fR)) {
                return false;
            }
            if (this.fS == null) {
                bl3 = bl2;
                if (object.fS != null) return bl3;
            } else if (!this.fS.equals(object.fS)) {
                return false;
            }
            bl3 = bl2;
            if (this.fT != object.fT) return bl3;
            bl3 = bl2;
            if (this.fU != object.fU) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fV, object.fV)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fW, object.fW)) return bl3;
            bl3 = bl2;
            if (this.fX != object.fX) return bl3;
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
            int n2 = 1231;
            int n3 = 0;
            int n4 = this.type;
            int n5 = this.fN == null ? 0 : this.fN.hashCode();
            int n6 = kr.hashCode(this.fO);
            int n7 = kr.hashCode(this.fP);
            int n8 = kr.hashCode(this.fQ);
            int n9 = this.fR == null ? 0 : this.fR.hashCode();
            int n10 = this.fS == null ? 0 : this.fS.hashCode();
            int n11 = (int)(this.fT ^ this.fT >>> 32);
            int n12 = this.fU ? 1231 : 1237;
            int n13 = kr.hashCode(this.fV);
            int n14 = kr.hashCode(this.fW);
            if (!this.fX) {
                n2 = 1237;
            }
            int n15 = n3;
            if (this.adU == null) return ((((n12 + ((n10 + (n9 + ((((n5 + (n4 + 527) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31) * 31) * 31 + n11) * 31) * 31 + n13) * 31 + n14) * 31 + n2) * 31 + n15;
            if (this.adU.isEmpty()) {
                n15 = n3;
                return ((((n12 + ((n10 + (n9 + ((((n5 + (n4 + 527) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31) * 31) * 31 + n11) * 31) * 31 + n13) * 31 + n14) * 31 + n2) * 31 + n15;
            }
            n15 = this.adU.hashCode();
            return ((((n12 + ((n10 + (n9 + ((((n5 + (n4 + 527) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31) * 31) * 31 + n11) * 31) * 31 + n13) * 31 + n14) * 31 + n2) * 31 + n15;
        }

        /*
         * Enabled aggressive block sorting
         */
        public a l(kn kn2) throws IOException {
            block28 : do {
                int n2 = kn2.mh();
                switch (n2) {
                    Object object;
                    int n3;
                    int n4;
                    default: {
                        if (this.a(kn2, n2)) continue block28;
                    }
                    case 0: {
                        return this;
                    }
                    case 8: {
                        n2 = kn2.mk();
                        switch (n2) {
                            default: {
                                continue block28;
                            }
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: 
                            case 7: 
                            case 8: 
                        }
                        this.type = n2;
                        continue block28;
                    }
                    case 18: {
                        this.fN = kn2.readString();
                        continue block28;
                    }
                    case 26: {
                        n3 = kw.b(kn2, 26);
                        n2 = this.fO == null ? 0 : this.fO.length;
                        object = new a[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fO, 0, object, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < object.length - 1) {
                            object[n3] = new a();
                            kn2.a((kt)object[n3]);
                            kn2.mh();
                            ++n3;
                        }
                        object[n3] = new a();
                        kn2.a((kt)object[n3]);
                        this.fO = object;
                        continue block28;
                    }
                    case 34: {
                        n3 = kw.b(kn2, 34);
                        n2 = this.fP == null ? 0 : this.fP.length;
                        object = new a[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fP, 0, object, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < object.length - 1) {
                            object[n3] = new a();
                            kn2.a((kt)object[n3]);
                            kn2.mh();
                            ++n3;
                        }
                        object[n3] = new a();
                        kn2.a((kt)object[n3]);
                        this.fP = object;
                        continue block28;
                    }
                    case 42: {
                        n3 = kw.b(kn2, 42);
                        n2 = this.fQ == null ? 0 : this.fQ.length;
                        object = new a[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fQ, 0, object, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < object.length - 1) {
                            object[n3] = new a();
                            kn2.a((kt)object[n3]);
                            kn2.mh();
                            ++n3;
                        }
                        object[n3] = new a();
                        kn2.a((kt)object[n3]);
                        this.fQ = object;
                        continue block28;
                    }
                    case 50: {
                        this.fR = kn2.readString();
                        continue block28;
                    }
                    case 58: {
                        this.fS = kn2.readString();
                        continue block28;
                    }
                    case 64: {
                        this.fT = kn2.mj();
                        continue block28;
                    }
                    case 72: {
                        this.fX = kn2.ml();
                        continue block28;
                    }
                    case 80: {
                        int n5 = kw.b(kn2, 80);
                        object = new int[n5];
                        n3 = 0;
                        n2 = 0;
                        do {
                            if (n3 >= n5) continue block28;
                            if (n3 != 0) {
                                kn2.mh();
                            }
                            int n6 = kn2.mk();
                            switch (n6) {
                                default: {
                                    break;
                                }
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: {
                                    n4 = n2 + 1;
                                    object[n2] = (a)n6;
                                    n2 = n4;
                                }
                            }
                            ++n3;
                        } while (true);
                    }
                    case 82: {
                        n4 = kn2.cR(kn2.mn());
                        n2 = kn2.getPosition();
                        n3 = 0;
                        block33 : while (kn2.ms() > 0) {
                            switch (kn2.mk()) {
                                default: {
                                    continue block33;
                                }
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: 
                            }
                            ++n3;
                        }
                        kn2.cS(n4);
                        continue block28;
                    }
                    case 90: {
                        n3 = kw.b(kn2, 90);
                        n2 = this.fV == null ? 0 : this.fV.length;
                        object = new a[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fV, 0, object, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < object.length - 1) {
                            object[n3] = new a();
                            kn2.a((kt)object[n3]);
                            kn2.mh();
                            ++n3;
                        }
                        object[n3] = new a();
                        kn2.a((kt)object[n3]);
                        this.fV = object;
                        continue block28;
                    }
                    case 96: 
                }
                this.fU = kn2.ml();
            } while (true);
        }

        public a s() {
            this.type = 1;
            this.fN = "";
            this.fO = a.r();
            this.fP = a.r();
            this.fQ = a.r();
            this.fR = "";
            this.fS = "";
            this.fT = 0;
            this.fU = false;
            this.fV = a.r();
            this.fW = kw.aea;
            this.fX = false;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

}

