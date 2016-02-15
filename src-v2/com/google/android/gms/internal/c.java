/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.d;
import com.google.android.gms.internal.kn;
import com.google.android.gms.internal.ko;
import com.google.android.gms.internal.kp;
import com.google.android.gms.internal.kq;
import com.google.android.gms.internal.kr;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;
import com.google.android.gms.internal.kw;
import java.io.IOException;
import java.util.List;

public interface c {

    public static final class a
    extends kp<a> {
        public int eE;
        public int eF;
        public int level;

        public a() {
            this.b();
        }

        public a a(kn kn2) throws IOException {
            block9 : do {
                int n2 = kn2.mh();
                switch (n2) {
                    default: {
                        if (this.a(kn2, n2)) continue block9;
                    }
                    case 0: {
                        return this;
                    }
                    case 8: {
                        n2 = kn2.mk();
                        switch (n2) {
                            default: {
                                continue block9;
                            }
                            case 1: 
                            case 2: 
                            case 3: 
                        }
                        this.level = n2;
                        continue block9;
                    }
                    case 16: {
                        this.eE = kn2.mk();
                        continue block9;
                    }
                    case 24: 
                }
                this.eF = kn2.mk();
            } while (true);
        }

        @Override
        public void a(ko ko2) throws IOException {
            if (this.level != 1) {
                ko2.i(1, this.level);
            }
            if (this.eE != 0) {
                ko2.i(2, this.eE);
            }
            if (this.eF != 0) {
                ko2.i(3, this.eF);
            }
            super.a(ko2);
        }

        public a b() {
            this.level = 1;
            this.eE = 0;
            this.eF = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.a(kn2);
        }

        @Override
        public int c() {
            int n2;
            int n3 = n2 = super.c();
            if (this.level != 1) {
                n3 = n2 + ko.j(1, this.level);
            }
            n2 = n3;
            if (this.eE != 0) {
                n2 = n3 + ko.j(2, this.eE);
            }
            n3 = n2;
            if (this.eF != 0) {
                n3 = n2 + ko.j(3, this.eF);
            }
            this.adY = n3;
            return n3;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
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
            if (this.level != object.level) return bl3;
            bl3 = bl2;
            if (this.eE != object.eE) return bl3;
            bl3 = bl2;
            if (this.eF != object.eF) return bl3;
            if (this.adU != null) {
                if (!this.adU.isEmpty()) return this.adU.equals(object.adU);
            }
            if (object.adU == null) return true;
            bl3 = bl2;
            if (!object.adU.isEmpty()) return bl3;
            return true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n2;
            int n3 = this.level;
            int n4 = this.eE;
            int n5 = this.eF;
            if (this.adU == null || this.adU.isEmpty()) {
                n2 = 0;
                do {
                    return n2 + (((n3 + 527) * 31 + n4) * 31 + n5) * 31;
                    break;
                } while (true);
            }
            n2 = this.adU.hashCode();
            return n2 + (((n3 + 527) * 31 + n4) * 31 + n5) * 31;
        }
    }

    public static final class b
    extends kp<b> {
        private static volatile b[] eG;
        public int[] eH;
        public int eI;
        public boolean eJ;
        public boolean eK;
        public int name;

        public b() {
            this.e();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static b[] d() {
            if (eG == null) {
                Object object = kr.adX;
                synchronized (object) {
                    if (eG == null) {
                        eG = new b[0];
                    }
                }
            }
            return eG;
        }

        @Override
        public void a(ko ko2) throws IOException {
            if (this.eK) {
                ko2.a(1, this.eK);
            }
            ko2.i(2, this.eI);
            if (this.eH != null && this.eH.length > 0) {
                for (int i2 = 0; i2 < this.eH.length; ++i2) {
                    ko2.i(3, this.eH[i2]);
                }
            }
            if (this.name != 0) {
                ko2.i(4, this.name);
            }
            if (this.eJ) {
                ko2.a(6, this.eJ);
            }
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.c(kn2);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int c() {
            int n2;
            int n3 = 0;
            int n4 = n2 = super.c();
            if (this.eK) {
                n4 = n2 + ko.b(1, this.eK);
            }
            n2 = ko.j(2, this.eI) + n4;
            if (this.eH != null && this.eH.length > 0) {
                for (n4 = 0; n4 < this.eH.length; n3 += ko.cX((int)this.eH[n4]), ++n4) {
                }
                n3 = n2 + n3 + this.eH.length * 1;
            } else {
                n3 = n2;
            }
            n4 = n3;
            if (this.name != 0) {
                n4 = n3 + ko.j(4, this.name);
            }
            n3 = n4;
            if (this.eJ) {
                n3 = n4 + ko.b(6, this.eJ);
            }
            this.adY = n3;
            return n3;
        }

        /*
         * Enabled aggressive block sorting
         */
        public b c(kn kn2) throws IOException {
            block9 : do {
                int n2 = kn2.mh();
                switch (n2) {
                    int[] arrn;
                    int n3;
                    default: {
                        if (this.a(kn2, n2)) continue block9;
                    }
                    case 0: {
                        return this;
                    }
                    case 8: {
                        this.eK = kn2.ml();
                        continue block9;
                    }
                    case 16: {
                        this.eI = kn2.mk();
                        continue block9;
                    }
                    case 24: {
                        n3 = kw.b(kn2, 24);
                        n2 = this.eH == null ? 0 : this.eH.length;
                        arrn = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.eH, 0, arrn, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arrn.length - 1) {
                            arrn[n3] = kn2.mk();
                            kn2.mh();
                            ++n3;
                        }
                        arrn[n3] = kn2.mk();
                        this.eH = arrn;
                        continue block9;
                    }
                    case 26: {
                        int n4 = kn2.cR(kn2.mn());
                        n2 = kn2.getPosition();
                        n3 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n3;
                        }
                        kn2.cT(n2);
                        n2 = this.eH == null ? 0 : this.eH.length;
                        arrn = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.eH, 0, arrn, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arrn.length) {
                            arrn[n3] = kn2.mk();
                            ++n3;
                        }
                        this.eH = arrn;
                        kn2.cS(n4);
                        continue block9;
                    }
                    case 32: {
                        this.name = kn2.mk();
                        continue block9;
                    }
                    case 48: 
                }
                this.eJ = kn2.ml();
            } while (true);
        }

        public b e() {
            this.eH = kw.aea;
            this.eI = 0;
            this.name = 0;
            this.eJ = false;
            this.eK = false;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            boolean bl2 = false;
            if (object == this) {
                return true;
            }
            boolean bl3 = bl2;
            if (!(object instanceof b)) return bl3;
            object = (b)object;
            bl3 = bl2;
            if (!kr.equals(this.eH, object.eH)) return bl3;
            bl3 = bl2;
            if (this.eI != object.eI) return bl3;
            bl3 = bl2;
            if (this.name != object.name) return bl3;
            bl3 = bl2;
            if (this.eJ != object.eJ) return bl3;
            bl3 = bl2;
            if (this.eK != object.eK) return bl3;
            if (this.adU != null) {
                if (!this.adU.isEmpty()) return this.adU.equals(object.adU);
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
            int n2;
            int n3 = 1231;
            int n4 = kr.hashCode(this.eH);
            int n5 = this.eI;
            int n6 = this.name;
            int n7 = this.eJ ? 1231 : 1237;
            if (!this.eK) {
                n3 = 1237;
            }
            if (this.adU != null && !this.adU.isEmpty()) {
                n2 = this.adU.hashCode();
                return n2 + ((n7 + (((n4 + 527) * 31 + n5) * 31 + n6) * 31) * 31 + n3) * 31;
            }
            n2 = 0;
            return n2 + ((n7 + (((n4 + 527) * 31 + n5) * 31 + n6) * 31) * 31 + n3) * 31;
        }
    }

    public static final class c
    extends kp<c> {
        private static volatile c[] eL;
        public String eM;
        public long eN;
        public long eO;
        public boolean eP;
        public long eQ;

        public c() {
            this.g();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static c[] f() {
            if (eL == null) {
                Object object = kr.adX;
                synchronized (object) {
                    if (eL == null) {
                        eL = new c[0];
                    }
                }
            }
            return eL;
        }

        @Override
        public void a(ko ko2) throws IOException {
            if (!this.eM.equals("")) {
                ko2.b(1, this.eM);
            }
            if (this.eN != 0) {
                ko2.b(2, this.eN);
            }
            if (this.eO != Integer.MAX_VALUE) {
                ko2.b(3, this.eO);
            }
            if (this.eP) {
                ko2.a(4, this.eP);
            }
            if (this.eQ != 0) {
                ko2.b(5, this.eQ);
            }
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.d(kn2);
        }

        @Override
        public int c() {
            int n2;
            int n3 = n2 = super.c();
            if (!this.eM.equals("")) {
                n3 = n2 + ko.g(1, this.eM);
            }
            n2 = n3;
            if (this.eN != 0) {
                n2 = n3 + ko.d(2, this.eN);
            }
            n3 = n2;
            if (this.eO != Integer.MAX_VALUE) {
                n3 = n2 + ko.d(3, this.eO);
            }
            n2 = n3;
            if (this.eP) {
                n2 = n3 + ko.b(4, this.eP);
            }
            n3 = n2;
            if (this.eQ != 0) {
                n3 = n2 + ko.d(5, this.eQ);
            }
            this.adY = n3;
            return n3;
        }

        public c d(kn kn2) throws IOException {
            block8 : do {
                int n2 = kn2.mh();
                switch (n2) {
                    default: {
                        if (this.a(kn2, n2)) continue block8;
                    }
                    case 0: {
                        return this;
                    }
                    case 10: {
                        this.eM = kn2.readString();
                        continue block8;
                    }
                    case 16: {
                        this.eN = kn2.mj();
                        continue block8;
                    }
                    case 24: {
                        this.eO = kn2.mj();
                        continue block8;
                    }
                    case 32: {
                        this.eP = kn2.ml();
                        continue block8;
                    }
                    case 40: 
                }
                this.eQ = kn2.mj();
            } while (true);
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
            if (!(object instanceof c)) return bl3;
            object = (c)object;
            if (this.eM == null) {
                bl3 = bl2;
                if (object.eM != null) return bl3;
            } else if (!this.eM.equals(object.eM)) {
                return false;
            }
            bl3 = bl2;
            if (this.eN != object.eN) return bl3;
            bl3 = bl2;
            if (this.eO != object.eO) return bl3;
            bl3 = bl2;
            if (this.eP != object.eP) return bl3;
            bl3 = bl2;
            if (this.eQ != object.eQ) return bl3;
            if (this.adU != null && !this.adU.isEmpty()) {
                return this.adU.equals(object.adU);
            }
            if (object.adU == null) return true;
            bl3 = bl2;
            if (!object.adU.isEmpty()) return bl3;
            return true;
        }

        public c g() {
            this.eM = "";
            this.eN = 0;
            this.eO = Integer.MAX_VALUE;
            this.eP = false;
            this.eQ = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int hashCode() {
            int n2 = 0;
            int n3 = this.eM == null ? 0 : this.eM.hashCode();
            int n4 = (int)(this.eN ^ this.eN >>> 32);
            int n5 = (int)(this.eO ^ this.eO >>> 32);
            int n6 = this.eP ? 1231 : 1237;
            int n7 = (int)(this.eQ ^ this.eQ >>> 32);
            int n8 = n2;
            if (this.adU == null) return ((n6 + (((n3 + 527) * 31 + n4) * 31 + n5) * 31) * 31 + n7) * 31 + n8;
            if (this.adU.isEmpty()) {
                n8 = n2;
                return ((n6 + (((n3 + 527) * 31 + n4) * 31 + n5) * 31) * 31 + n7) * 31 + n8;
            }
            n8 = this.adU.hashCode();
            return ((n6 + (((n3 + 527) * 31 + n4) * 31 + n5) * 31) * 31 + n7) * 31 + n8;
        }
    }

    public static final class d
    extends kp<d> {
        public d.a[] eR;
        public d.a[] eS;
        public c[] eT;

        public d() {
            this.h();
        }

        @Override
        public void a(ko ko2) throws IOException {
            int n2;
            int n3 = 0;
            if (this.eR != null && this.eR.length > 0) {
                for (n2 = 0; n2 < this.eR.length; ++n2) {
                    d.a a2 = this.eR[n2];
                    if (a2 == null) continue;
                    ko2.a(1, a2);
                }
            }
            if (this.eS != null && this.eS.length > 0) {
                for (n2 = 0; n2 < this.eS.length; ++n2) {
                    d.a a2 = this.eS[n2];
                    if (a2 == null) continue;
                    ko2.a(2, a2);
                }
            }
            if (this.eT != null && this.eT.length > 0) {
                for (n2 = n3; n2 < this.eT.length; ++n2) {
                    c c2 = this.eT[n2];
                    if (c2 == null) continue;
                    ko2.a(3, c2);
                }
            }
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.e(kn2);
        }

        @Override
        public int c() {
            int n2;
            int n3;
            int n4 = 0;
            int n5 = n2 = super.c();
            if (this.eR != null) {
                n5 = n2;
                if (this.eR.length > 0) {
                    for (n5 = 0; n5 < this.eR.length; ++n5) {
                        d.a a2 = this.eR[n5];
                        n3 = n2;
                        if (a2 != null) {
                            n3 = n2 + ko.b(1, a2);
                        }
                        n2 = n3;
                    }
                    n5 = n2;
                }
            }
            n2 = n5;
            if (this.eS != null) {
                n2 = n5;
                if (this.eS.length > 0) {
                    n2 = n5;
                    for (n5 = 0; n5 < this.eS.length; ++n5) {
                        d.a a2 = this.eS[n5];
                        n3 = n2;
                        if (a2 != null) {
                            n3 = n2 + ko.b(2, a2);
                        }
                        n2 = n3;
                    }
                }
            }
            n3 = n2;
            if (this.eT != null) {
                n3 = n2;
                if (this.eT.length > 0) {
                    n5 = n4;
                    do {
                        n3 = n2;
                        if (n5 >= this.eT.length) break;
                        c c2 = this.eT[n5];
                        n3 = n2;
                        if (c2 != null) {
                            n3 = n2 + ko.b(3, c2);
                        }
                        ++n5;
                        n2 = n3;
                    } while (true);
                }
            }
            this.adY = n3;
            return n3;
        }

        /*
         * Enabled aggressive block sorting
         */
        public d e(kn kn2) throws IOException {
            block6 : do {
                int n2;
                d.a[] arra;
                int n3 = kn2.mh();
                switch (n3) {
                    default: {
                        if (this.a(kn2, n3)) continue block6;
                    }
                    case 0: {
                        return this;
                    }
                    case 10: {
                        n2 = kw.b(kn2, 10);
                        n3 = this.eR == null ? 0 : this.eR.length;
                        arra = new d.a[n2 + n3];
                        n2 = n3;
                        if (n3 != 0) {
                            System.arraycopy(this.eR, 0, arra, 0, n3);
                            n2 = n3;
                        }
                        while (n2 < arra.length - 1) {
                            arra[n2] = new d.a();
                            kn2.a(arra[n2]);
                            kn2.mh();
                            ++n2;
                        }
                        arra[n2] = new d.a();
                        kn2.a(arra[n2]);
                        this.eR = arra;
                        continue block6;
                    }
                    case 18: {
                        n2 = kw.b(kn2, 18);
                        n3 = this.eS == null ? 0 : this.eS.length;
                        arra = new d.a[n2 + n3];
                        n2 = n3;
                        if (n3 != 0) {
                            System.arraycopy(this.eS, 0, arra, 0, n3);
                            n2 = n3;
                        }
                        while (n2 < arra.length - 1) {
                            arra[n2] = new d.a();
                            kn2.a(arra[n2]);
                            kn2.mh();
                            ++n2;
                        }
                        arra[n2] = new d.a();
                        kn2.a(arra[n2]);
                        this.eS = arra;
                        continue block6;
                    }
                    case 26: 
                }
                n2 = kw.b(kn2, 26);
                n3 = this.eT == null ? 0 : this.eT.length;
                arra = new c[n2 + n3];
                n2 = n3;
                if (n3 != 0) {
                    System.arraycopy(this.eT, 0, arra, 0, n3);
                    n2 = n3;
                }
                while (n2 < arra.length - 1) {
                    arra[n2] = new c();
                    kn2.a(arra[n2]);
                    kn2.mh();
                    ++n2;
                }
                arra[n2] = new c();
                kn2.a(arra[n2]);
                this.eT = arra;
            } while (true);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            boolean bl2 = false;
            if (object == this) {
                return true;
            }
            boolean bl3 = bl2;
            if (!(object instanceof d)) return bl3;
            object = (d)object;
            bl3 = bl2;
            if (!kr.equals(this.eR, object.eR)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.eS, object.eS)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.eT, object.eT)) return bl3;
            if (this.adU != null) {
                if (!this.adU.isEmpty()) return this.adU.equals(object.adU);
            }
            if (object.adU == null) return true;
            bl3 = bl2;
            if (!object.adU.isEmpty()) return bl3;
            return true;
        }

        public d h() {
            this.eR = d.a.r();
            this.eS = d.a.r();
            this.eT = c.f();
            this.adU = null;
            this.adY = -1;
            return this;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n2;
            int n3 = kr.hashCode(this.eR);
            int n4 = kr.hashCode(this.eS);
            int n5 = kr.hashCode(this.eT);
            if (this.adU == null || this.adU.isEmpty()) {
                n2 = 0;
                do {
                    return n2 + (((n3 + 527) * 31 + n4) * 31 + n5) * 31;
                    break;
                } while (true);
            }
            n2 = this.adU.hashCode();
            return n2 + (((n3 + 527) * 31 + n4) * 31 + n5) * 31;
        }
    }

    public static final class e
    extends kp<e> {
        private static volatile e[] eU;
        public int key;
        public int value;

        public e() {
            this.j();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static e[] i() {
            if (eU == null) {
                Object object = kr.adX;
                synchronized (object) {
                    if (eU == null) {
                        eU = new e[0];
                    }
                }
            }
            return eU;
        }

        @Override
        public void a(ko ko2) throws IOException {
            ko2.i(1, this.key);
            ko2.i(2, this.value);
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.f(kn2);
        }

        @Override
        public int c() {
            int n2;
            this.adY = n2 = super.c() + ko.j(1, this.key) + ko.j(2, this.value);
            return n2;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            boolean bl2 = false;
            if (object == this) {
                return true;
            }
            boolean bl3 = bl2;
            if (!(object instanceof e)) return bl3;
            object = (e)object;
            bl3 = bl2;
            if (this.key != object.key) return bl3;
            bl3 = bl2;
            if (this.value != object.value) return bl3;
            if (this.adU != null) {
                if (!this.adU.isEmpty()) return this.adU.equals(object.adU);
            }
            if (object.adU == null) return true;
            bl3 = bl2;
            if (!object.adU.isEmpty()) return bl3;
            return true;
        }

        public e f(kn kn2) throws IOException {
            block5 : do {
                int n2 = kn2.mh();
                switch (n2) {
                    default: {
                        if (this.a(kn2, n2)) continue block5;
                    }
                    case 0: {
                        return this;
                    }
                    case 8: {
                        this.key = kn2.mk();
                        continue block5;
                    }
                    case 16: 
                }
                this.value = kn2.mk();
            } while (true);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n2;
            int n3 = this.key;
            int n4 = this.value;
            if (this.adU == null || this.adU.isEmpty()) {
                n2 = 0;
                do {
                    return n2 + ((n3 + 527) * 31 + n4) * 31;
                    break;
                } while (true);
            }
            n2 = this.adU.hashCode();
            return n2 + ((n3 + 527) * 31 + n4) * 31;
        }

        public e j() {
            this.key = 0;
            this.value = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    public static final class f
    extends kp<f> {
        public String[] eV;
        public String[] eW;
        public d.a[] eX;
        public e[] eY;
        public b[] eZ;
        public b[] fa;
        public b[] fb;
        public g[] fc;
        public String fd;
        public String fe;
        public String ff;
        public String fg;
        public a fh;
        public float fi;
        public boolean fj;
        public String[] fk;
        public int fl;

        public f() {
            this.k();
        }

        public static f a(byte[] arrby) throws ks {
            return kt.a(new f(), arrby);
        }

        @Override
        public void a(ko ko2) throws IOException {
            Object object;
            int n2;
            int n3 = 0;
            if (this.eW != null && this.eW.length > 0) {
                for (n2 = 0; n2 < this.eW.length; ++n2) {
                    object = this.eW[n2];
                    if (object == null) continue;
                    ko2.b(1, (String)object);
                }
            }
            if (this.eX != null && this.eX.length > 0) {
                for (n2 = 0; n2 < this.eX.length; ++n2) {
                    object = this.eX[n2];
                    if (object == null) continue;
                    ko2.a(2, (kt)object);
                }
            }
            if (this.eY != null && this.eY.length > 0) {
                for (n2 = 0; n2 < this.eY.length; ++n2) {
                    object = this.eY[n2];
                    if (object == null) continue;
                    ko2.a(3, (kt)object);
                }
            }
            if (this.eZ != null && this.eZ.length > 0) {
                for (n2 = 0; n2 < this.eZ.length; ++n2) {
                    object = this.eZ[n2];
                    if (object == null) continue;
                    ko2.a(4, (kt)object);
                }
            }
            if (this.fa != null && this.fa.length > 0) {
                for (n2 = 0; n2 < this.fa.length; ++n2) {
                    object = this.fa[n2];
                    if (object == null) continue;
                    ko2.a(5, (kt)object);
                }
            }
            if (this.fb != null && this.fb.length > 0) {
                for (n2 = 0; n2 < this.fb.length; ++n2) {
                    object = this.fb[n2];
                    if (object == null) continue;
                    ko2.a(6, (kt)object);
                }
            }
            if (this.fc != null && this.fc.length > 0) {
                for (n2 = 0; n2 < this.fc.length; ++n2) {
                    object = this.fc[n2];
                    if (object == null) continue;
                    ko2.a(7, (kt)object);
                }
            }
            if (!this.fd.equals("")) {
                ko2.b(9, this.fd);
            }
            if (!this.fe.equals("")) {
                ko2.b(10, this.fe);
            }
            if (!this.ff.equals("0")) {
                ko2.b(12, this.ff);
            }
            if (!this.fg.equals("")) {
                ko2.b(13, this.fg);
            }
            if (this.fh != null) {
                ko2.a(14, this.fh);
            }
            if (Float.floatToIntBits(this.fi) != Float.floatToIntBits(0.0f)) {
                ko2.b(15, this.fi);
            }
            if (this.fk != null && this.fk.length > 0) {
                for (n2 = 0; n2 < this.fk.length; ++n2) {
                    object = this.fk[n2];
                    if (object == null) continue;
                    ko2.b(16, (String)object);
                }
            }
            if (this.fl != 0) {
                ko2.i(17, this.fl);
            }
            if (this.fj) {
                ko2.a(18, this.fj);
            }
            if (this.eV != null && this.eV.length > 0) {
                for (n2 = n3; n2 < this.eV.length; ++n2) {
                    object = this.eV[n2];
                    if (object == null) continue;
                    ko2.b(19, (String)object);
                }
            }
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.g(kn2);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int c() {
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            Object object;
            int n7 = 0;
            int n8 = super.c();
            if (this.eW != null && this.eW.length > 0) {
                n2 = 0;
                n6 = 0;
                for (n3 = 0; n3 < this.eW.length; ++n3) {
                    object = this.eW[n3];
                    n5 = n2;
                    n4 = n6;
                    if (object != null) {
                        n4 = n6 + 1;
                        n5 = n2 + ko.cf((String)object);
                    }
                    n2 = n5;
                    n6 = n4;
                }
                n2 = n8 + n2 + n6 * 1;
            } else {
                n2 = n8;
            }
            n3 = n2;
            if (this.eX != null) {
                n3 = n2;
                if (this.eX.length > 0) {
                    n3 = n2;
                    for (n2 = 0; n2 < this.eX.length; ++n2) {
                        object = this.eX[n2];
                        n6 = n3;
                        if (object != null) {
                            n6 = n3 + ko.b(2, (kt)object);
                        }
                        n3 = n6;
                    }
                }
            }
            n2 = n3;
            if (this.eY != null) {
                n2 = n3;
                if (this.eY.length > 0) {
                    for (n2 = 0; n2 < this.eY.length; ++n2) {
                        object = this.eY[n2];
                        n6 = n3;
                        if (object != null) {
                            n6 = n3 + ko.b(3, (kt)object);
                        }
                        n3 = n6;
                    }
                    n2 = n3;
                }
            }
            n3 = n2;
            if (this.eZ != null) {
                n3 = n2;
                if (this.eZ.length > 0) {
                    n3 = n2;
                    for (n2 = 0; n2 < this.eZ.length; ++n2) {
                        object = this.eZ[n2];
                        n6 = n3;
                        if (object != null) {
                            n6 = n3 + ko.b(4, (kt)object);
                        }
                        n3 = n6;
                    }
                }
            }
            n2 = n3;
            if (this.fa != null) {
                n2 = n3;
                if (this.fa.length > 0) {
                    for (n2 = 0; n2 < this.fa.length; ++n2) {
                        object = this.fa[n2];
                        n6 = n3;
                        if (object != null) {
                            n6 = n3 + ko.b(5, (kt)object);
                        }
                        n3 = n6;
                    }
                    n2 = n3;
                }
            }
            n3 = n2;
            if (this.fb != null) {
                n3 = n2;
                if (this.fb.length > 0) {
                    n3 = n2;
                    for (n2 = 0; n2 < this.fb.length; ++n2) {
                        object = this.fb[n2];
                        n6 = n3;
                        if (object != null) {
                            n6 = n3 + ko.b(6, (kt)object);
                        }
                        n3 = n6;
                    }
                }
            }
            n2 = n3;
            if (this.fc != null) {
                n2 = n3;
                if (this.fc.length > 0) {
                    for (n2 = 0; n2 < this.fc.length; ++n2) {
                        object = this.fc[n2];
                        n6 = n3;
                        if (object != null) {
                            n6 = n3 + ko.b(7, (kt)object);
                        }
                        n3 = n6;
                    }
                    n2 = n3;
                }
            }
            n3 = n2;
            if (!this.fd.equals("")) {
                n3 = n2 + ko.g(9, this.fd);
            }
            n2 = n3;
            if (!this.fe.equals("")) {
                n2 = n3 + ko.g(10, this.fe);
            }
            n3 = n2;
            if (!this.ff.equals("0")) {
                n3 = n2 + ko.g(12, this.ff);
            }
            n2 = n3;
            if (!this.fg.equals("")) {
                n2 = n3 + ko.g(13, this.fg);
            }
            n6 = n2;
            if (this.fh != null) {
                n6 = n2 + ko.b(14, this.fh);
            }
            n3 = n6;
            if (Float.floatToIntBits(this.fi) != Float.floatToIntBits(0.0f)) {
                n3 = n6 + ko.c(15, this.fi);
            }
            n2 = n3;
            if (this.fk != null) {
                n2 = n3;
                if (this.fk.length > 0) {
                    n6 = 0;
                    n4 = 0;
                    for (n2 = 0; n2 < this.fk.length; ++n2) {
                        object = this.fk[n2];
                        n8 = n6;
                        n5 = n4;
                        if (object != null) {
                            n5 = n4 + 1;
                            n8 = n6 + ko.cf((String)object);
                        }
                        n6 = n8;
                        n4 = n5;
                    }
                    n2 = n3 + n6 + n4 * 2;
                }
            }
            n6 = n2;
            if (this.fl != 0) {
                n6 = n2 + ko.j(17, this.fl);
            }
            n3 = n6;
            if (this.fj) {
                n3 = n6 + ko.b(18, this.fj);
            }
            n2 = n3;
            if (this.eV != null) {
                n2 = n3;
                if (this.eV.length > 0) {
                    n6 = 0;
                    n4 = 0;
                    for (n2 = n7; n2 < this.eV.length; ++n2) {
                        object = this.eV[n2];
                        n8 = n6;
                        n5 = n4;
                        if (object != null) {
                            n5 = n4 + 1;
                            n8 = n6 + ko.cf((String)object);
                        }
                        n6 = n8;
                        n4 = n5;
                    }
                    n2 = n3 + n6 + n4 * 2;
                }
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
            if (!(object instanceof f)) return bl3;
            object = (f)object;
            bl3 = bl2;
            if (!kr.equals(this.eV, object.eV)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.eW, object.eW)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.eX, object.eX)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.eY, object.eY)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.eZ, object.eZ)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fa, object.fa)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fb, object.fb)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fc, object.fc)) return bl3;
            if (this.fd == null) {
                bl3 = bl2;
                if (object.fd != null) return bl3;
            } else if (!this.fd.equals(object.fd)) {
                return false;
            }
            if (this.fe == null) {
                bl3 = bl2;
                if (object.fe != null) return bl3;
            } else if (!this.fe.equals(object.fe)) {
                return false;
            }
            if (this.ff == null) {
                bl3 = bl2;
                if (object.ff != null) return bl3;
            } else if (!this.ff.equals(object.ff)) {
                return false;
            }
            if (this.fg == null) {
                bl3 = bl2;
                if (object.fg != null) return bl3;
            } else if (!this.fg.equals(object.fg)) {
                return false;
            }
            if (this.fh == null) {
                bl3 = bl2;
                if (object.fh != null) return bl3;
            } else if (!this.fh.equals(object.fh)) {
                return false;
            }
            bl3 = bl2;
            if (Float.floatToIntBits(this.fi) != Float.floatToIntBits(object.fi)) return bl3;
            bl3 = bl2;
            if (this.fj != object.fj) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fk, object.fk)) return bl3;
            bl3 = bl2;
            if (this.fl != object.fl) return bl3;
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
        public f g(kn kn2) throws IOException {
            block20 : do {
                int n2;
                String[] arrstring;
                int n3 = kn2.mh();
                switch (n3) {
                    default: {
                        if (this.a(kn2, n3)) continue block20;
                    }
                    case 0: {
                        return this;
                    }
                    case 10: {
                        n2 = kw.b(kn2, 10);
                        n3 = this.eW == null ? 0 : this.eW.length;
                        arrstring = new String[n2 + n3];
                        n2 = n3;
                        if (n3 != 0) {
                            System.arraycopy(this.eW, 0, arrstring, 0, n3);
                            n2 = n3;
                        }
                        while (n2 < arrstring.length - 1) {
                            arrstring[n2] = kn2.readString();
                            kn2.mh();
                            ++n2;
                        }
                        arrstring[n2] = kn2.readString();
                        this.eW = arrstring;
                        continue block20;
                    }
                    case 18: {
                        n2 = kw.b(kn2, 18);
                        n3 = this.eX == null ? 0 : this.eX.length;
                        arrstring = new d.a[n2 + n3];
                        n2 = n3;
                        if (n3 != 0) {
                            System.arraycopy(this.eX, 0, arrstring, 0, n3);
                            n2 = n3;
                        }
                        while (n2 < arrstring.length - 1) {
                            arrstring[n2] = new d.a();
                            kn2.a((kt)((Object)arrstring[n2]));
                            kn2.mh();
                            ++n2;
                        }
                        arrstring[n2] = new d.a();
                        kn2.a((kt)((Object)arrstring[n2]));
                        this.eX = arrstring;
                        continue block20;
                    }
                    case 26: {
                        n2 = kw.b(kn2, 26);
                        n3 = this.eY == null ? 0 : this.eY.length;
                        arrstring = new e[n2 + n3];
                        n2 = n3;
                        if (n3 != 0) {
                            System.arraycopy(this.eY, 0, arrstring, 0, n3);
                            n2 = n3;
                        }
                        while (n2 < arrstring.length - 1) {
                            arrstring[n2] = new e();
                            kn2.a((kt)((Object)arrstring[n2]));
                            kn2.mh();
                            ++n2;
                        }
                        arrstring[n2] = new e();
                        kn2.a((kt)((Object)arrstring[n2]));
                        this.eY = arrstring;
                        continue block20;
                    }
                    case 34: {
                        n2 = kw.b(kn2, 34);
                        n3 = this.eZ == null ? 0 : this.eZ.length;
                        arrstring = new b[n2 + n3];
                        n2 = n3;
                        if (n3 != 0) {
                            System.arraycopy(this.eZ, 0, arrstring, 0, n3);
                            n2 = n3;
                        }
                        while (n2 < arrstring.length - 1) {
                            arrstring[n2] = new b();
                            kn2.a((kt)((Object)arrstring[n2]));
                            kn2.mh();
                            ++n2;
                        }
                        arrstring[n2] = new b();
                        kn2.a((kt)((Object)arrstring[n2]));
                        this.eZ = arrstring;
                        continue block20;
                    }
                    case 42: {
                        n2 = kw.b(kn2, 42);
                        n3 = this.fa == null ? 0 : this.fa.length;
                        arrstring = new b[n2 + n3];
                        n2 = n3;
                        if (n3 != 0) {
                            System.arraycopy(this.fa, 0, arrstring, 0, n3);
                            n2 = n3;
                        }
                        while (n2 < arrstring.length - 1) {
                            arrstring[n2] = new b();
                            kn2.a((kt)((Object)arrstring[n2]));
                            kn2.mh();
                            ++n2;
                        }
                        arrstring[n2] = new b();
                        kn2.a((kt)((Object)arrstring[n2]));
                        this.fa = arrstring;
                        continue block20;
                    }
                    case 50: {
                        n2 = kw.b(kn2, 50);
                        n3 = this.fb == null ? 0 : this.fb.length;
                        arrstring = new b[n2 + n3];
                        n2 = n3;
                        if (n3 != 0) {
                            System.arraycopy(this.fb, 0, arrstring, 0, n3);
                            n2 = n3;
                        }
                        while (n2 < arrstring.length - 1) {
                            arrstring[n2] = new b();
                            kn2.a((kt)((Object)arrstring[n2]));
                            kn2.mh();
                            ++n2;
                        }
                        arrstring[n2] = new b();
                        kn2.a((kt)((Object)arrstring[n2]));
                        this.fb = arrstring;
                        continue block20;
                    }
                    case 58: {
                        n2 = kw.b(kn2, 58);
                        n3 = this.fc == null ? 0 : this.fc.length;
                        arrstring = new g[n2 + n3];
                        n2 = n3;
                        if (n3 != 0) {
                            System.arraycopy(this.fc, 0, arrstring, 0, n3);
                            n2 = n3;
                        }
                        while (n2 < arrstring.length - 1) {
                            arrstring[n2] = new g();
                            kn2.a((kt)((Object)arrstring[n2]));
                            kn2.mh();
                            ++n2;
                        }
                        arrstring[n2] = new g();
                        kn2.a((kt)((Object)arrstring[n2]));
                        this.fc = arrstring;
                        continue block20;
                    }
                    case 74: {
                        this.fd = kn2.readString();
                        continue block20;
                    }
                    case 82: {
                        this.fe = kn2.readString();
                        continue block20;
                    }
                    case 98: {
                        this.ff = kn2.readString();
                        continue block20;
                    }
                    case 106: {
                        this.fg = kn2.readString();
                        continue block20;
                    }
                    case 114: {
                        if (this.fh == null) {
                            this.fh = new a();
                        }
                        kn2.a(this.fh);
                        continue block20;
                    }
                    case 125: {
                        this.fi = kn2.readFloat();
                        continue block20;
                    }
                    case 130: {
                        n2 = kw.b(kn2, 130);
                        n3 = this.fk == null ? 0 : this.fk.length;
                        arrstring = new String[n2 + n3];
                        n2 = n3;
                        if (n3 != 0) {
                            System.arraycopy(this.fk, 0, arrstring, 0, n3);
                            n2 = n3;
                        }
                        while (n2 < arrstring.length - 1) {
                            arrstring[n2] = kn2.readString();
                            kn2.mh();
                            ++n2;
                        }
                        arrstring[n2] = kn2.readString();
                        this.fk = arrstring;
                        continue block20;
                    }
                    case 136: {
                        this.fl = kn2.mk();
                        continue block20;
                    }
                    case 144: {
                        this.fj = kn2.ml();
                        continue block20;
                    }
                    case 154: 
                }
                n2 = kw.b(kn2, 154);
                n3 = this.eV == null ? 0 : this.eV.length;
                arrstring = new String[n2 + n3];
                n2 = n3;
                if (n3 != 0) {
                    System.arraycopy(this.eV, 0, arrstring, 0, n3);
                    n2 = n3;
                }
                while (n2 < arrstring.length - 1) {
                    arrstring[n2] = kn2.readString();
                    kn2.mh();
                    ++n2;
                }
                arrstring[n2] = kn2.readString();
                this.eV = arrstring;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         */
        public int hashCode() {
            int n2 = 0;
            int n3 = kr.hashCode(this.eV);
            int n4 = kr.hashCode(this.eW);
            int n5 = kr.hashCode(this.eX);
            int n6 = kr.hashCode(this.eY);
            int n7 = kr.hashCode(this.eZ);
            int n8 = kr.hashCode(this.fa);
            int n9 = kr.hashCode(this.fb);
            int n10 = kr.hashCode(this.fc);
            int n11 = this.fd == null ? 0 : this.fd.hashCode();
            int n12 = this.fe == null ? 0 : this.fe.hashCode();
            int n13 = this.ff == null ? 0 : this.ff.hashCode();
            int n14 = this.fg == null ? 0 : this.fg.hashCode();
            int n15 = this.fh == null ? 0 : this.fh.hashCode();
            int n16 = Float.floatToIntBits(this.fi);
            int n17 = this.fj ? 1231 : 1237;
            int n18 = kr.hashCode(this.fk);
            int n19 = this.fl;
            int n20 = n2;
            if (this.adU == null) return (((n17 + ((n15 + (n14 + (n13 + (n12 + (n11 + ((((((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31) * 31) * 31) * 31) * 31) * 31 + n16) * 31) * 31 + n18) * 31 + n19) * 31 + n20;
            if (this.adU.isEmpty()) {
                n20 = n2;
                return (((n17 + ((n15 + (n14 + (n13 + (n12 + (n11 + ((((((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31) * 31) * 31) * 31) * 31) * 31 + n16) * 31) * 31 + n18) * 31 + n19) * 31 + n20;
            }
            n20 = this.adU.hashCode();
            return (((n17 + ((n15 + (n14 + (n13 + (n12 + (n11 + ((((((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31) * 31) * 31) * 31) * 31) * 31 + n16) * 31) * 31 + n18) * 31 + n19) * 31 + n20;
        }

        public f k() {
            this.eV = kw.aef;
            this.eW = kw.aef;
            this.eX = d.a.r();
            this.eY = e.i();
            this.eZ = b.d();
            this.fa = b.d();
            this.fb = b.d();
            this.fc = g.l();
            this.fd = "";
            this.fe = "";
            this.ff = "0";
            this.fg = "";
            this.fh = null;
            this.fi = 0.0f;
            this.fj = false;
            this.fk = kw.aef;
            this.fl = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    public static final class g
    extends kp<g> {
        private static volatile g[] fm;
        public int[] fn;
        public int[] fo;
        public int[] fp;
        public int[] fq;
        public int[] fr;
        public int[] fs;
        public int[] ft;
        public int[] fu;
        public int[] fv;
        public int[] fw;

        public g() {
            this.m();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static g[] l() {
            if (fm == null) {
                Object object = kr.adX;
                synchronized (object) {
                    if (fm == null) {
                        fm = new g[0];
                    }
                }
            }
            return fm;
        }

        @Override
        public void a(ko ko2) throws IOException {
            int n2;
            int n3 = 0;
            if (this.fn != null && this.fn.length > 0) {
                for (n2 = 0; n2 < this.fn.length; ++n2) {
                    ko2.i(1, this.fn[n2]);
                }
            }
            if (this.fo != null && this.fo.length > 0) {
                for (n2 = 0; n2 < this.fo.length; ++n2) {
                    ko2.i(2, this.fo[n2]);
                }
            }
            if (this.fp != null && this.fp.length > 0) {
                for (n2 = 0; n2 < this.fp.length; ++n2) {
                    ko2.i(3, this.fp[n2]);
                }
            }
            if (this.fq != null && this.fq.length > 0) {
                for (n2 = 0; n2 < this.fq.length; ++n2) {
                    ko2.i(4, this.fq[n2]);
                }
            }
            if (this.fr != null && this.fr.length > 0) {
                for (n2 = 0; n2 < this.fr.length; ++n2) {
                    ko2.i(5, this.fr[n2]);
                }
            }
            if (this.fs != null && this.fs.length > 0) {
                for (n2 = 0; n2 < this.fs.length; ++n2) {
                    ko2.i(6, this.fs[n2]);
                }
            }
            if (this.ft != null && this.ft.length > 0) {
                for (n2 = 0; n2 < this.ft.length; ++n2) {
                    ko2.i(7, this.ft[n2]);
                }
            }
            if (this.fu != null && this.fu.length > 0) {
                for (n2 = 0; n2 < this.fu.length; ++n2) {
                    ko2.i(8, this.fu[n2]);
                }
            }
            if (this.fv != null && this.fv.length > 0) {
                for (n2 = 0; n2 < this.fv.length; ++n2) {
                    ko2.i(9, this.fv[n2]);
                }
            }
            if (this.fw != null && this.fw.length > 0) {
                for (n2 = n3; n2 < this.fw.length; ++n2) {
                    ko2.i(10, this.fw[n2]);
                }
            }
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.h(kn2);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int c() {
            int n2;
            int n3;
            int n4 = 0;
            int n5 = super.c();
            if (this.fn != null && this.fn.length > 0) {
                n2 = 0;
                for (n3 = 0; n3 < this.fn.length; n2 += ko.cX((int)this.fn[n3]), ++n3) {
                }
                n2 = n5 + n2 + this.fn.length * 1;
            } else {
                n2 = n5;
            }
            n3 = n2;
            if (this.fo != null) {
                n3 = n2;
                if (this.fo.length > 0) {
                    n5 = 0;
                    for (n3 = 0; n3 < this.fo.length; n5 += ko.cX((int)this.fo[n3]), ++n3) {
                    }
                    n3 = n2 + n5 + this.fo.length * 1;
                }
            }
            n2 = n3;
            if (this.fp != null) {
                n2 = n3;
                if (this.fp.length > 0) {
                    n5 = 0;
                    for (n2 = 0; n2 < this.fp.length; n5 += ko.cX((int)this.fp[n2]), ++n2) {
                    }
                    n2 = n3 + n5 + this.fp.length * 1;
                }
            }
            n3 = n2;
            if (this.fq != null) {
                n3 = n2;
                if (this.fq.length > 0) {
                    n5 = 0;
                    for (n3 = 0; n3 < this.fq.length; n5 += ko.cX((int)this.fq[n3]), ++n3) {
                    }
                    n3 = n2 + n5 + this.fq.length * 1;
                }
            }
            n2 = n3;
            if (this.fr != null) {
                n2 = n3;
                if (this.fr.length > 0) {
                    n5 = 0;
                    for (n2 = 0; n2 < this.fr.length; n5 += ko.cX((int)this.fr[n2]), ++n2) {
                    }
                    n2 = n3 + n5 + this.fr.length * 1;
                }
            }
            n3 = n2;
            if (this.fs != null) {
                n3 = n2;
                if (this.fs.length > 0) {
                    n5 = 0;
                    for (n3 = 0; n3 < this.fs.length; n5 += ko.cX((int)this.fs[n3]), ++n3) {
                    }
                    n3 = n2 + n5 + this.fs.length * 1;
                }
            }
            n2 = n3;
            if (this.ft != null) {
                n2 = n3;
                if (this.ft.length > 0) {
                    n5 = 0;
                    for (n2 = 0; n2 < this.ft.length; n5 += ko.cX((int)this.ft[n2]), ++n2) {
                    }
                    n2 = n3 + n5 + this.ft.length * 1;
                }
            }
            n3 = n2;
            if (this.fu != null) {
                n3 = n2;
                if (this.fu.length > 0) {
                    n5 = 0;
                    for (n3 = 0; n3 < this.fu.length; n5 += ko.cX((int)this.fu[n3]), ++n3) {
                    }
                    n3 = n2 + n5 + this.fu.length * 1;
                }
            }
            n2 = n3;
            if (this.fv != null) {
                n2 = n3;
                if (this.fv.length > 0) {
                    n5 = 0;
                    for (n2 = 0; n2 < this.fv.length; n5 += ko.cX((int)this.fv[n2]), ++n2) {
                    }
                    n2 = n3 + n5 + this.fv.length * 1;
                }
            }
            n3 = n2;
            if (this.fw != null) {
                n3 = n2;
                if (this.fw.length > 0) {
                    n5 = 0;
                    for (n3 = n4; n3 < this.fw.length; n5 += ko.cX((int)this.fw[n3]), ++n3) {
                    }
                    n3 = n2 + n5 + this.fw.length * 1;
                }
            }
            this.adY = n3;
            return n3;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            boolean bl2 = false;
            if (object == this) {
                return true;
            }
            boolean bl3 = bl2;
            if (!(object instanceof g)) return bl3;
            object = (g)object;
            bl3 = bl2;
            if (!kr.equals(this.fn, object.fn)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fo, object.fo)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fp, object.fp)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fq, object.fq)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fr, object.fr)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fs, object.fs)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.ft, object.ft)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fu, object.fu)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fv, object.fv)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fw, object.fw)) return bl3;
            if (this.adU != null) {
                if (!this.adU.isEmpty()) return this.adU.equals(object.adU);
            }
            if (object.adU == null) return true;
            bl3 = bl2;
            if (!object.adU.isEmpty()) return bl3;
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public g h(kn kn2) throws IOException {
            block23 : do {
                int[] arrn;
                int n2;
                int n3;
                int n4 = kn2.mh();
                switch (n4) {
                    default: {
                        if (this.a(kn2, n4)) continue block23;
                    }
                    case 0: {
                        return this;
                    }
                    case 8: {
                        n2 = kw.b(kn2, 8);
                        n4 = this.fn == null ? 0 : this.fn.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fn, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = kn2.mk();
                            kn2.mh();
                            ++n2;
                        }
                        arrn[n2] = kn2.mk();
                        this.fn = arrn;
                        continue block23;
                    }
                    case 10: {
                        n3 = kn2.cR(kn2.mn());
                        n4 = kn2.getPosition();
                        n2 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n2;
                        }
                        kn2.cT(n4);
                        n4 = this.fn == null ? 0 : this.fn.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fn, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length) {
                            arrn[n2] = kn2.mk();
                            ++n2;
                        }
                        this.fn = arrn;
                        kn2.cS(n3);
                        continue block23;
                    }
                    case 16: {
                        n2 = kw.b(kn2, 16);
                        n4 = this.fo == null ? 0 : this.fo.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fo, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = kn2.mk();
                            kn2.mh();
                            ++n2;
                        }
                        arrn[n2] = kn2.mk();
                        this.fo = arrn;
                        continue block23;
                    }
                    case 18: {
                        n3 = kn2.cR(kn2.mn());
                        n4 = kn2.getPosition();
                        n2 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n2;
                        }
                        kn2.cT(n4);
                        n4 = this.fo == null ? 0 : this.fo.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fo, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length) {
                            arrn[n2] = kn2.mk();
                            ++n2;
                        }
                        this.fo = arrn;
                        kn2.cS(n3);
                        continue block23;
                    }
                    case 24: {
                        n2 = kw.b(kn2, 24);
                        n4 = this.fp == null ? 0 : this.fp.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fp, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = kn2.mk();
                            kn2.mh();
                            ++n2;
                        }
                        arrn[n2] = kn2.mk();
                        this.fp = arrn;
                        continue block23;
                    }
                    case 26: {
                        n3 = kn2.cR(kn2.mn());
                        n4 = kn2.getPosition();
                        n2 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n2;
                        }
                        kn2.cT(n4);
                        n4 = this.fp == null ? 0 : this.fp.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fp, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length) {
                            arrn[n2] = kn2.mk();
                            ++n2;
                        }
                        this.fp = arrn;
                        kn2.cS(n3);
                        continue block23;
                    }
                    case 32: {
                        n2 = kw.b(kn2, 32);
                        n4 = this.fq == null ? 0 : this.fq.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fq, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = kn2.mk();
                            kn2.mh();
                            ++n2;
                        }
                        arrn[n2] = kn2.mk();
                        this.fq = arrn;
                        continue block23;
                    }
                    case 34: {
                        n3 = kn2.cR(kn2.mn());
                        n4 = kn2.getPosition();
                        n2 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n2;
                        }
                        kn2.cT(n4);
                        n4 = this.fq == null ? 0 : this.fq.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fq, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length) {
                            arrn[n2] = kn2.mk();
                            ++n2;
                        }
                        this.fq = arrn;
                        kn2.cS(n3);
                        continue block23;
                    }
                    case 40: {
                        n2 = kw.b(kn2, 40);
                        n4 = this.fr == null ? 0 : this.fr.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fr, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = kn2.mk();
                            kn2.mh();
                            ++n2;
                        }
                        arrn[n2] = kn2.mk();
                        this.fr = arrn;
                        continue block23;
                    }
                    case 42: {
                        n3 = kn2.cR(kn2.mn());
                        n4 = kn2.getPosition();
                        n2 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n2;
                        }
                        kn2.cT(n4);
                        n4 = this.fr == null ? 0 : this.fr.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fr, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length) {
                            arrn[n2] = kn2.mk();
                            ++n2;
                        }
                        this.fr = arrn;
                        kn2.cS(n3);
                        continue block23;
                    }
                    case 48: {
                        n2 = kw.b(kn2, 48);
                        n4 = this.fs == null ? 0 : this.fs.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fs, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = kn2.mk();
                            kn2.mh();
                            ++n2;
                        }
                        arrn[n2] = kn2.mk();
                        this.fs = arrn;
                        continue block23;
                    }
                    case 50: {
                        n3 = kn2.cR(kn2.mn());
                        n4 = kn2.getPosition();
                        n2 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n2;
                        }
                        kn2.cT(n4);
                        n4 = this.fs == null ? 0 : this.fs.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fs, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length) {
                            arrn[n2] = kn2.mk();
                            ++n2;
                        }
                        this.fs = arrn;
                        kn2.cS(n3);
                        continue block23;
                    }
                    case 56: {
                        n2 = kw.b(kn2, 56);
                        n4 = this.ft == null ? 0 : this.ft.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.ft, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = kn2.mk();
                            kn2.mh();
                            ++n2;
                        }
                        arrn[n2] = kn2.mk();
                        this.ft = arrn;
                        continue block23;
                    }
                    case 58: {
                        n3 = kn2.cR(kn2.mn());
                        n4 = kn2.getPosition();
                        n2 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n2;
                        }
                        kn2.cT(n4);
                        n4 = this.ft == null ? 0 : this.ft.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.ft, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length) {
                            arrn[n2] = kn2.mk();
                            ++n2;
                        }
                        this.ft = arrn;
                        kn2.cS(n3);
                        continue block23;
                    }
                    case 64: {
                        n2 = kw.b(kn2, 64);
                        n4 = this.fu == null ? 0 : this.fu.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fu, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = kn2.mk();
                            kn2.mh();
                            ++n2;
                        }
                        arrn[n2] = kn2.mk();
                        this.fu = arrn;
                        continue block23;
                    }
                    case 66: {
                        n3 = kn2.cR(kn2.mn());
                        n4 = kn2.getPosition();
                        n2 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n2;
                        }
                        kn2.cT(n4);
                        n4 = this.fu == null ? 0 : this.fu.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fu, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length) {
                            arrn[n2] = kn2.mk();
                            ++n2;
                        }
                        this.fu = arrn;
                        kn2.cS(n3);
                        continue block23;
                    }
                    case 72: {
                        n2 = kw.b(kn2, 72);
                        n4 = this.fv == null ? 0 : this.fv.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fv, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = kn2.mk();
                            kn2.mh();
                            ++n2;
                        }
                        arrn[n2] = kn2.mk();
                        this.fv = arrn;
                        continue block23;
                    }
                    case 74: {
                        n3 = kn2.cR(kn2.mn());
                        n4 = kn2.getPosition();
                        n2 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n2;
                        }
                        kn2.cT(n4);
                        n4 = this.fv == null ? 0 : this.fv.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fv, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length) {
                            arrn[n2] = kn2.mk();
                            ++n2;
                        }
                        this.fv = arrn;
                        kn2.cS(n3);
                        continue block23;
                    }
                    case 80: {
                        n2 = kw.b(kn2, 80);
                        n4 = this.fw == null ? 0 : this.fw.length;
                        arrn = new int[n2 + n4];
                        n2 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.fw, 0, arrn, 0, n4);
                            n2 = n4;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = kn2.mk();
                            kn2.mh();
                            ++n2;
                        }
                        arrn[n2] = kn2.mk();
                        this.fw = arrn;
                        continue block23;
                    }
                    case 82: 
                }
                n3 = kn2.cR(kn2.mn());
                n4 = kn2.getPosition();
                n2 = 0;
                while (kn2.ms() > 0) {
                    kn2.mk();
                    ++n2;
                }
                kn2.cT(n4);
                n4 = this.fw == null ? 0 : this.fw.length;
                arrn = new int[n2 + n4];
                n2 = n4;
                if (n4 != 0) {
                    System.arraycopy(this.fw, 0, arrn, 0, n4);
                    n2 = n4;
                }
                while (n2 < arrn.length) {
                    arrn[n2] = kn2.mk();
                    ++n2;
                }
                this.fw = arrn;
                kn2.cS(n3);
            } while (true);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n2;
            int n3 = kr.hashCode(this.fn);
            int n4 = kr.hashCode(this.fo);
            int n5 = kr.hashCode(this.fp);
            int n6 = kr.hashCode(this.fq);
            int n7 = kr.hashCode(this.fr);
            int n8 = kr.hashCode(this.fs);
            int n9 = kr.hashCode(this.ft);
            int n10 = kr.hashCode(this.fu);
            int n11 = kr.hashCode(this.fv);
            int n12 = kr.hashCode(this.fw);
            if (this.adU == null || this.adU.isEmpty()) {
                n2 = 0;
                do {
                    return n2 + ((((((((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n11) * 31 + n12) * 31;
                    break;
                } while (true);
            }
            n2 = this.adU.hashCode();
            return n2 + ((((((((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n11) * 31 + n12) * 31;
        }

        public g m() {
            this.fn = kw.aea;
            this.fo = kw.aea;
            this.fp = kw.aea;
            this.fq = kw.aea;
            this.fr = kw.aea;
            this.fs = kw.aea;
            this.ft = kw.aea;
            this.fu = kw.aea;
            this.fv = kw.aea;
            this.fw = kw.aea;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    public static final class h
    extends kp<h> {
        public static final kq<d.a, h> fx = kq.a(11, h.class, 810);
        private static final h[] fy = new h[0];
        public int[] fA;
        public int[] fB;
        public int fC;
        public int[] fD;
        public int fE;
        public int fF;
        public int[] fz;

        public h() {
            this.n();
        }

        @Override
        public void a(ko ko2) throws IOException {
            int n2;
            int n3 = 0;
            if (this.fz != null && this.fz.length > 0) {
                for (n2 = 0; n2 < this.fz.length; ++n2) {
                    ko2.i(1, this.fz[n2]);
                }
            }
            if (this.fA != null && this.fA.length > 0) {
                for (n2 = 0; n2 < this.fA.length; ++n2) {
                    ko2.i(2, this.fA[n2]);
                }
            }
            if (this.fB != null && this.fB.length > 0) {
                for (n2 = 0; n2 < this.fB.length; ++n2) {
                    ko2.i(3, this.fB[n2]);
                }
            }
            if (this.fC != 0) {
                ko2.i(4, this.fC);
            }
            if (this.fD != null && this.fD.length > 0) {
                for (n2 = n3; n2 < this.fD.length; ++n2) {
                    ko2.i(5, this.fD[n2]);
                }
            }
            if (this.fE != 0) {
                ko2.i(6, this.fE);
            }
            if (this.fF != 0) {
                ko2.i(7, this.fF);
            }
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.i(kn2);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int c() {
            int n2;
            int n3;
            int n4 = 0;
            int n5 = super.c();
            if (this.fz != null && this.fz.length > 0) {
                n2 = 0;
                for (n3 = 0; n3 < this.fz.length; n2 += ko.cX((int)this.fz[n3]), ++n3) {
                }
                n2 = n5 + n2 + this.fz.length * 1;
            } else {
                n2 = n5;
            }
            n3 = n2;
            if (this.fA != null) {
                n3 = n2;
                if (this.fA.length > 0) {
                    n5 = 0;
                    for (n3 = 0; n3 < this.fA.length; n5 += ko.cX((int)this.fA[n3]), ++n3) {
                    }
                    n3 = n2 + n5 + this.fA.length * 1;
                }
            }
            n2 = n3;
            if (this.fB != null) {
                n2 = n3;
                if (this.fB.length > 0) {
                    n5 = 0;
                    for (n2 = 0; n2 < this.fB.length; n5 += ko.cX((int)this.fB[n2]), ++n2) {
                    }
                    n2 = n3 + n5 + this.fB.length * 1;
                }
            }
            n3 = n2;
            if (this.fC != 0) {
                n3 = n2 + ko.j(4, this.fC);
            }
            n2 = n3;
            if (this.fD != null) {
                n2 = n3;
                if (this.fD.length > 0) {
                    n5 = 0;
                    for (n2 = n4; n2 < this.fD.length; n5 += ko.cX((int)this.fD[n2]), ++n2) {
                    }
                    n2 = n3 + n5 + this.fD.length * 1;
                }
            }
            n3 = n2;
            if (this.fE != 0) {
                n3 = n2 + ko.j(6, this.fE);
            }
            n2 = n3;
            if (this.fF != 0) {
                n2 = n3 + ko.j(7, this.fF);
            }
            this.adY = n2;
            return n2;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            boolean bl2 = false;
            if (object == this) {
                return true;
            }
            boolean bl3 = bl2;
            if (!(object instanceof h)) return bl3;
            object = (h)object;
            bl3 = bl2;
            if (!kr.equals(this.fz, object.fz)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fA, object.fA)) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fB, object.fB)) return bl3;
            bl3 = bl2;
            if (this.fC != object.fC) return bl3;
            bl3 = bl2;
            if (!kr.equals(this.fD, object.fD)) return bl3;
            bl3 = bl2;
            if (this.fE != object.fE) return bl3;
            bl3 = bl2;
            if (this.fF != object.fF) return bl3;
            if (this.adU != null) {
                if (!this.adU.isEmpty()) return this.adU.equals(object.adU);
            }
            if (object.adU == null) return true;
            bl3 = bl2;
            if (!object.adU.isEmpty()) return bl3;
            return true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n2;
            int n3 = kr.hashCode(this.fz);
            int n4 = kr.hashCode(this.fA);
            int n5 = kr.hashCode(this.fB);
            int n6 = this.fC;
            int n7 = kr.hashCode(this.fD);
            int n8 = this.fE;
            int n9 = this.fF;
            if (this.adU == null || this.adU.isEmpty()) {
                n2 = 0;
                do {
                    return n2 + (((((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31;
                    break;
                } while (true);
            }
            n2 = this.adU.hashCode();
            return n2 + (((((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31;
        }

        /*
         * Enabled aggressive block sorting
         */
        public h i(kn kn2) throws IOException {
            block14 : do {
                int n2 = kn2.mh();
                switch (n2) {
                    int[] arrn;
                    int n3;
                    int n4;
                    default: {
                        if (this.a(kn2, n2)) continue block14;
                    }
                    case 0: {
                        return this;
                    }
                    case 8: {
                        n3 = kw.b(kn2, 8);
                        n2 = this.fz == null ? 0 : this.fz.length;
                        arrn = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fz, 0, arrn, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arrn.length - 1) {
                            arrn[n3] = kn2.mk();
                            kn2.mh();
                            ++n3;
                        }
                        arrn[n3] = kn2.mk();
                        this.fz = arrn;
                        continue block14;
                    }
                    case 10: {
                        n4 = kn2.cR(kn2.mn());
                        n2 = kn2.getPosition();
                        n3 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n3;
                        }
                        kn2.cT(n2);
                        n2 = this.fz == null ? 0 : this.fz.length;
                        arrn = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fz, 0, arrn, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arrn.length) {
                            arrn[n3] = kn2.mk();
                            ++n3;
                        }
                        this.fz = arrn;
                        kn2.cS(n4);
                        continue block14;
                    }
                    case 16: {
                        n3 = kw.b(kn2, 16);
                        n2 = this.fA == null ? 0 : this.fA.length;
                        arrn = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fA, 0, arrn, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arrn.length - 1) {
                            arrn[n3] = kn2.mk();
                            kn2.mh();
                            ++n3;
                        }
                        arrn[n3] = kn2.mk();
                        this.fA = arrn;
                        continue block14;
                    }
                    case 18: {
                        n4 = kn2.cR(kn2.mn());
                        n2 = kn2.getPosition();
                        n3 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n3;
                        }
                        kn2.cT(n2);
                        n2 = this.fA == null ? 0 : this.fA.length;
                        arrn = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fA, 0, arrn, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arrn.length) {
                            arrn[n3] = kn2.mk();
                            ++n3;
                        }
                        this.fA = arrn;
                        kn2.cS(n4);
                        continue block14;
                    }
                    case 24: {
                        n3 = kw.b(kn2, 24);
                        n2 = this.fB == null ? 0 : this.fB.length;
                        arrn = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fB, 0, arrn, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arrn.length - 1) {
                            arrn[n3] = kn2.mk();
                            kn2.mh();
                            ++n3;
                        }
                        arrn[n3] = kn2.mk();
                        this.fB = arrn;
                        continue block14;
                    }
                    case 26: {
                        n4 = kn2.cR(kn2.mn());
                        n2 = kn2.getPosition();
                        n3 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n3;
                        }
                        kn2.cT(n2);
                        n2 = this.fB == null ? 0 : this.fB.length;
                        arrn = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fB, 0, arrn, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arrn.length) {
                            arrn[n3] = kn2.mk();
                            ++n3;
                        }
                        this.fB = arrn;
                        kn2.cS(n4);
                        continue block14;
                    }
                    case 32: {
                        this.fC = kn2.mk();
                        continue block14;
                    }
                    case 40: {
                        n3 = kw.b(kn2, 40);
                        n2 = this.fD == null ? 0 : this.fD.length;
                        arrn = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fD, 0, arrn, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arrn.length - 1) {
                            arrn[n3] = kn2.mk();
                            kn2.mh();
                            ++n3;
                        }
                        arrn[n3] = kn2.mk();
                        this.fD = arrn;
                        continue block14;
                    }
                    case 42: {
                        n4 = kn2.cR(kn2.mn());
                        n2 = kn2.getPosition();
                        n3 = 0;
                        while (kn2.ms() > 0) {
                            kn2.mk();
                            ++n3;
                        }
                        kn2.cT(n2);
                        n2 = this.fD == null ? 0 : this.fD.length;
                        arrn = new int[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fD, 0, arrn, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arrn.length) {
                            arrn[n3] = kn2.mk();
                            ++n3;
                        }
                        this.fD = arrn;
                        kn2.cS(n4);
                        continue block14;
                    }
                    case 48: {
                        this.fE = kn2.mk();
                        continue block14;
                    }
                    case 56: 
                }
                this.fF = kn2.mk();
            } while (true);
        }

        public h n() {
            this.fz = kw.aea;
            this.fA = kw.aea;
            this.fB = kw.aea;
            this.fC = 0;
            this.fD = kw.aea;
            this.fE = 0;
            this.fF = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    public static final class i
    extends kp<i> {
        private static volatile i[] fG;
        public d.a fH;
        public d fI;
        public String name;

        public i() {
            this.p();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static i[] o() {
            if (fG == null) {
                Object object = kr.adX;
                synchronized (object) {
                    if (fG == null) {
                        fG = new i[0];
                    }
                }
            }
            return fG;
        }

        @Override
        public void a(ko ko2) throws IOException {
            if (!this.name.equals("")) {
                ko2.b(1, this.name);
            }
            if (this.fH != null) {
                ko2.a(2, this.fH);
            }
            if (this.fI != null) {
                ko2.a(3, this.fI);
            }
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.j(kn2);
        }

        @Override
        public int c() {
            int n2;
            int n3 = n2 = super.c();
            if (!this.name.equals("")) {
                n3 = n2 + ko.g(1, this.name);
            }
            n2 = n3;
            if (this.fH != null) {
                n2 = n3 + ko.b(2, this.fH);
            }
            n3 = n2;
            if (this.fI != null) {
                n3 = n2 + ko.b(3, this.fI);
            }
            this.adY = n3;
            return n3;
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
            if (!(object instanceof i)) return bl3;
            object = (i)object;
            if (this.name == null) {
                bl3 = bl2;
                if (object.name != null) return bl3;
            } else if (!this.name.equals(object.name)) {
                return false;
            }
            if (this.fH == null) {
                bl3 = bl2;
                if (object.fH != null) return bl3;
            } else if (!this.fH.equals(object.fH)) {
                return false;
            }
            if (this.fI == null) {
                bl3 = bl2;
                if (object.fI != null) return bl3;
            } else if (!this.fI.equals(object.fI)) {
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
            int n3 = this.name == null ? 0 : this.name.hashCode();
            int n4 = this.fH == null ? 0 : this.fH.hashCode();
            int n5 = this.fI == null ? 0 : this.fI.hashCode();
            int n6 = n2;
            if (this.adU == null) return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
            if (this.adU.isEmpty()) {
                n6 = n2;
                return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
            }
            n6 = this.adU.hashCode();
            return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
        }

        public i j(kn kn2) throws IOException {
            block6 : do {
                int n2 = kn2.mh();
                switch (n2) {
                    default: {
                        if (this.a(kn2, n2)) continue block6;
                    }
                    case 0: {
                        return this;
                    }
                    case 10: {
                        this.name = kn2.readString();
                        continue block6;
                    }
                    case 18: {
                        if (this.fH == null) {
                            this.fH = new d.a();
                        }
                        kn2.a(this.fH);
                        continue block6;
                    }
                    case 26: 
                }
                if (this.fI == null) {
                    this.fI = new d();
                }
                kn2.a(this.fI);
            } while (true);
        }

        public i p() {
            this.name = "";
            this.fH = null;
            this.fI = null;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    public static final class j
    extends kp<j> {
        public i[] fJ;
        public f fK;
        public String fL;

        public j() {
            this.q();
        }

        public static j b(byte[] arrby) throws ks {
            return kt.a(new j(), arrby);
        }

        @Override
        public void a(ko ko2) throws IOException {
            if (this.fJ != null && this.fJ.length > 0) {
                for (int i2 = 0; i2 < this.fJ.length; ++i2) {
                    i i3 = this.fJ[i2];
                    if (i3 == null) continue;
                    ko2.a(1, i3);
                }
            }
            if (this.fK != null) {
                ko2.a(2, this.fK);
            }
            if (!this.fL.equals("")) {
                ko2.b(3, this.fL);
            }
            super.a(ko2);
        }

        @Override
        public /* synthetic */ kt b(kn kn2) throws IOException {
            return this.k(kn2);
        }

        @Override
        public int c() {
            int n2;
            int n3 = n2 = super.c();
            if (this.fJ != null) {
                n3 = n2;
                if (this.fJ.length > 0) {
                    int n4 = 0;
                    do {
                        n3 = n2;
                        if (n4 >= this.fJ.length) break;
                        i i2 = this.fJ[n4];
                        n3 = n2;
                        if (i2 != null) {
                            n3 = n2 + ko.b(1, i2);
                        }
                        ++n4;
                        n2 = n3;
                    } while (true);
                }
            }
            n2 = n3;
            if (this.fK != null) {
                n2 = n3 + ko.b(2, this.fK);
            }
            n3 = n2;
            if (!this.fL.equals("")) {
                n3 = n2 + ko.g(3, this.fL);
            }
            this.adY = n3;
            return n3;
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
            if (!(object instanceof j)) return bl3;
            object = (j)object;
            bl3 = bl2;
            if (!kr.equals(this.fJ, object.fJ)) return bl3;
            if (this.fK == null) {
                bl3 = bl2;
                if (object.fK != null) return bl3;
            } else if (!this.fK.equals(object.fK)) {
                return false;
            }
            if (this.fL == null) {
                bl3 = bl2;
                if (object.fL != null) return bl3;
            } else if (!this.fL.equals(object.fL)) {
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
            int n3 = kr.hashCode(this.fJ);
            int n4 = this.fK == null ? 0 : this.fK.hashCode();
            int n5 = this.fL == null ? 0 : this.fL.hashCode();
            int n6 = n2;
            if (this.adU == null) return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
            if (this.adU.isEmpty()) {
                n6 = n2;
                return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
            }
            n6 = this.adU.hashCode();
            return (n5 + (n4 + (n3 + 527) * 31) * 31) * 31 + n6;
        }

        /*
         * Enabled aggressive block sorting
         */
        public j k(kn kn2) throws IOException {
            block6 : do {
                int n2 = kn2.mh();
                switch (n2) {
                    default: {
                        if (this.a(kn2, n2)) continue block6;
                    }
                    case 0: {
                        return this;
                    }
                    case 10: {
                        int n3 = kw.b(kn2, 10);
                        n2 = this.fJ == null ? 0 : this.fJ.length;
                        i[] arri = new i[n3 + n2];
                        n3 = n2;
                        if (n2 != 0) {
                            System.arraycopy(this.fJ, 0, arri, 0, n2);
                            n3 = n2;
                        }
                        while (n3 < arri.length - 1) {
                            arri[n3] = new i();
                            kn2.a(arri[n3]);
                            kn2.mh();
                            ++n3;
                        }
                        arri[n3] = new i();
                        kn2.a(arri[n3]);
                        this.fJ = arri;
                        continue block6;
                    }
                    case 18: {
                        if (this.fK == null) {
                            this.fK = new f();
                        }
                        kn2.a(this.fK);
                        continue block6;
                    }
                    case 26: 
                }
                this.fL = kn2.readString();
            } while (true);
        }

        public j q() {
            this.fJ = i.o();
            this.fK = null;
            this.fL = "";
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

}

