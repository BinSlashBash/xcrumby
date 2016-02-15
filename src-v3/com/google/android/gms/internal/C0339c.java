package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.ads.AdSize;
import com.google.android.gms.internal.C0355d.C1367a;
import java.io.IOException;
import org.apache.commons.codec.binary.BaseNCodec;
import org.json.zip.JSONzip;

/* renamed from: com.google.android.gms.internal.c */
public interface C0339c {

    /* renamed from: com.google.android.gms.internal.c.a */
    public static final class C1355a extends kp<C1355a> {
        public int eE;
        public int eF;
        public int level;

        public C1355a() {
            m2926b();
        }

        public C1355a m2924a(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_LOCALE /*8*/:
                        mh = knVar.mk();
                        switch (mh) {
                            case Std.STD_FILE /*1*/:
                            case Std.STD_URL /*2*/:
                            case Std.STD_URI /*3*/:
                                this.level = mh;
                                break;
                            default:
                                continue;
                        }
                    case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                        this.eE = knVar.mk();
                        continue;
                    case C0065R.styleable.TwoWayView_android_fadingEdge /*24*/:
                        this.eF = knVar.mk();
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

        public void m2925a(ko koVar) throws IOException {
            if (this.level != 1) {
                koVar.m1157i(1, this.level);
            }
            if (this.eE != 0) {
                koVar.m1157i(2, this.eE);
            }
            if (this.eF != 0) {
                koVar.m1157i(3, this.eF);
            }
            super.m2324a(koVar);
        }

        public C1355a m2926b() {
            this.level = 1;
            this.eE = 0;
            this.eF = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        public /* synthetic */ kt m2927b(kn knVar) throws IOException {
            return m2924a(knVar);
        }

        public int m2928c() {
            int c = super.m1172c();
            if (this.level != 1) {
                c += ko.m1143j(1, this.level);
            }
            if (this.eE != 0) {
                c += ko.m1143j(2, this.eE);
            }
            if (this.eF != 0) {
                c += ko.m1143j(3, this.eF);
            }
            this.adY = c;
            return c;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1355a)) {
                return false;
            }
            C1355a c1355a = (C1355a) o;
            if (this.level != c1355a.level || this.eE != c1355a.eE || this.eF != c1355a.eF) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1355a.adU == null || c1355a.adU.isEmpty();
            } else {
                return this.adU.equals(c1355a.adU);
            }
        }

        public int hashCode() {
            int i = (((((this.level + 527) * 31) + this.eE) * 31) + this.eF) * 31;
            int hashCode = (this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode();
            return hashCode + i;
        }
    }

    /* renamed from: com.google.android.gms.internal.c.b */
    public static final class C1356b extends kp<C1356b> {
        private static volatile C1356b[] eG;
        public int[] eH;
        public int eI;
        public boolean eJ;
        public boolean eK;
        public int name;

        public C1356b() {
            m2934e();
        }

        public static C1356b[] m2929d() {
            if (eG == null) {
                synchronized (kr.adX) {
                    if (eG == null) {
                        eG = new C1356b[0];
                    }
                }
            }
            return eG;
        }

        public void m2930a(ko koVar) throws IOException {
            if (this.eK) {
                koVar.m1148a(1, this.eK);
            }
            koVar.m1157i(2, this.eI);
            if (this.eH != null && this.eH.length > 0) {
                for (int i : this.eH) {
                    koVar.m1157i(3, i);
                }
            }
            if (this.name != 0) {
                koVar.m1157i(4, this.name);
            }
            if (this.eJ) {
                koVar.m1148a(6, this.eJ);
            }
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m2931b(kn knVar) throws IOException {
            return m2933c(knVar);
        }

        public int m2932c() {
            int i = 0;
            int c = super.m1172c();
            if (this.eK) {
                c += ko.m1135b(1, this.eK);
            }
            int j = ko.m1143j(2, this.eI) + c;
            if (this.eH == null || this.eH.length <= 0) {
                c = j;
            } else {
                for (int cX : this.eH) {
                    i += ko.cX(cX);
                }
                c = (j + i) + (this.eH.length * 1);
            }
            if (this.name != 0) {
                c += ko.m1143j(4, this.name);
            }
            if (this.eJ) {
                c += ko.m1135b(6, this.eJ);
            }
            this.adY = c;
            return c;
        }

        public C1356b m2933c(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                int b;
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_LOCALE /*8*/:
                        this.eK = knVar.ml();
                        continue;
                    case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                        this.eI = knVar.mk();
                        continue;
                    case C0065R.styleable.TwoWayView_android_fadingEdge /*24*/:
                        b = kw.m1176b(knVar, 24);
                        mh = this.eH == null ? 0 : this.eH.length;
                        Object obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.eH, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.eH = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                        int cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.eH == null ? 0 : this.eH.length;
                        Object obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.eH, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.eH = obj2;
                        knVar.cS(cR);
                        continue;
                    case TransportMediator.FLAG_KEY_MEDIA_STOP /*32*/:
                        this.name = knVar.mk();
                        continue;
                    case C0065R.styleable.TwoWayView_android_fadeScrollbars /*48*/:
                        this.eJ = knVar.ml();
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

        public C1356b m2934e() {
            this.eH = kw.aea;
            this.eI = 0;
            this.name = 0;
            this.eJ = false;
            this.eK = false;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1356b)) {
                return false;
            }
            C1356b c1356b = (C1356b) o;
            if (!kr.equals(this.eH, c1356b.eH) || this.eI != c1356b.eI || this.name != c1356b.name || this.eJ != c1356b.eJ || this.eK != c1356b.eK) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1356b.adU == null || c1356b.adU.isEmpty();
            } else {
                return this.adU.equals(c1356b.adU);
            }
        }

        public int hashCode() {
            int i = 1231;
            int hashCode = ((this.eJ ? 1231 : 1237) + ((((((kr.hashCode(this.eH) + 527) * 31) + this.eI) * 31) + this.name) * 31)) * 31;
            if (!this.eK) {
                i = 1237;
            }
            i = (hashCode + i) * 31;
            hashCode = (this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode();
            return hashCode + i;
        }
    }

    /* renamed from: com.google.android.gms.internal.c.c */
    public static final class C1357c extends kp<C1357c> {
        private static volatile C1357c[] eL;
        public String eM;
        public long eN;
        public long eO;
        public boolean eP;
        public long eQ;

        public C1357c() {
            m2940g();
        }

        public static C1357c[] m2935f() {
            if (eL == null) {
                synchronized (kr.adX) {
                    if (eL == null) {
                        eL = new C1357c[0];
                    }
                }
            }
            return eL;
        }

        public void m2936a(ko koVar) throws IOException {
            if (!this.eM.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                koVar.m1152b(1, this.eM);
            }
            if (this.eN != 0) {
                koVar.m1151b(2, this.eN);
            }
            if (this.eO != 2147483647L) {
                koVar.m1151b(3, this.eO);
            }
            if (this.eP) {
                koVar.m1148a(4, this.eP);
            }
            if (this.eQ != 0) {
                koVar.m1151b(5, this.eQ);
            }
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m2937b(kn knVar) throws IOException {
            return m2939d(knVar);
        }

        public int m2938c() {
            int c = super.m1172c();
            if (!this.eM.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                c += ko.m1142g(1, this.eM);
            }
            if (this.eN != 0) {
                c += ko.m1139d(2, this.eN);
            }
            if (this.eO != 2147483647L) {
                c += ko.m1139d(3, this.eO);
            }
            if (this.eP) {
                c += ko.m1135b(4, this.eP);
            }
            if (this.eQ != 0) {
                c += ko.m1139d(5, this.eQ);
            }
            this.adY = c;
            return c;
        }

        public C1357c m2939d(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_TIME_ZONE /*10*/:
                        this.eM = knVar.readString();
                        continue;
                    case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                        this.eN = knVar.mj();
                        continue;
                    case C0065R.styleable.TwoWayView_android_fadingEdge /*24*/:
                        this.eO = knVar.mj();
                        continue;
                    case TransportMediator.FLAG_KEY_MEDIA_STOP /*32*/:
                        this.eP = knVar.ml();
                        continue;
                    case JSONzip.substringLimit /*40*/:
                        this.eQ = knVar.mj();
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

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1357c)) {
                return false;
            }
            C1357c c1357c = (C1357c) o;
            if (this.eM == null) {
                if (c1357c.eM != null) {
                    return false;
                }
            } else if (!this.eM.equals(c1357c.eM)) {
                return false;
            }
            if (this.eN != c1357c.eN || this.eO != c1357c.eO || this.eP != c1357c.eP || this.eQ != c1357c.eQ) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1357c.adU == null || c1357c.adU.isEmpty();
            } else {
                return this.adU.equals(c1357c.adU);
            }
        }

        public C1357c m2940g() {
            this.eM = UnsupportedUrlFragment.DISPLAY_NAME;
            this.eN = 0;
            this.eO = 2147483647L;
            this.eP = false;
            this.eQ = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((((this.eP ? 1231 : 1237) + (((((((this.eM == null ? 0 : this.eM.hashCode()) + 527) * 31) + ((int) (this.eN ^ (this.eN >>> 32)))) * 31) + ((int) (this.eO ^ (this.eO >>> 32)))) * 31)) * 31) + ((int) (this.eQ ^ (this.eQ >>> 32)))) * 31;
            if (!(this.adU == null || this.adU.isEmpty())) {
                i = this.adU.hashCode();
            }
            return hashCode + i;
        }
    }

    /* renamed from: com.google.android.gms.internal.c.d */
    public static final class C1358d extends kp<C1358d> {
        public C1367a[] eR;
        public C1367a[] eS;
        public C1357c[] eT;

        public C1358d() {
            m2945h();
        }

        public void m2941a(ko koVar) throws IOException {
            int i = 0;
            if (this.eR != null && this.eR.length > 0) {
                for (kt ktVar : this.eR) {
                    if (ktVar != null) {
                        koVar.m1147a(1, ktVar);
                    }
                }
            }
            if (this.eS != null && this.eS.length > 0) {
                for (kt ktVar2 : this.eS) {
                    if (ktVar2 != null) {
                        koVar.m1147a(2, ktVar2);
                    }
                }
            }
            if (this.eT != null && this.eT.length > 0) {
                while (i < this.eT.length) {
                    kt ktVar3 = this.eT[i];
                    if (ktVar3 != null) {
                        koVar.m1147a(3, ktVar3);
                    }
                    i++;
                }
            }
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m2942b(kn knVar) throws IOException {
            return m2944e(knVar);
        }

        public int m2943c() {
            int i;
            int i2 = 0;
            int c = super.m1172c();
            if (this.eR != null && this.eR.length > 0) {
                i = c;
                for (kt ktVar : this.eR) {
                    if (ktVar != null) {
                        i += ko.m1134b(1, ktVar);
                    }
                }
                c = i;
            }
            if (this.eS != null && this.eS.length > 0) {
                i = c;
                for (kt ktVar2 : this.eS) {
                    if (ktVar2 != null) {
                        i += ko.m1134b(2, ktVar2);
                    }
                }
                c = i;
            }
            if (this.eT != null && this.eT.length > 0) {
                while (i2 < this.eT.length) {
                    kt ktVar3 = this.eT[i2];
                    if (ktVar3 != null) {
                        c += ko.m1134b(3, ktVar3);
                    }
                    i2++;
                }
            }
            this.adY = c;
            return c;
        }

        public C1358d m2944e(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                int b;
                Object obj;
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_TIME_ZONE /*10*/:
                        b = kw.m1176b(knVar, 10);
                        mh = this.eR == null ? 0 : this.eR.length;
                        obj = new C1367a[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.eR, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1367a();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1367a();
                        knVar.m1126a(obj[mh]);
                        this.eR = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                        b = kw.m1176b(knVar, 18);
                        mh = this.eS == null ? 0 : this.eS.length;
                        obj = new C1367a[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.eS, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1367a();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1367a();
                        knVar.m1126a(obj[mh]);
                        this.eS = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                        b = kw.m1176b(knVar, 26);
                        mh = this.eT == null ? 0 : this.eT.length;
                        obj = new C1357c[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.eT, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1357c();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1357c();
                        knVar.m1126a(obj[mh]);
                        this.eT = obj;
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

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1358d)) {
                return false;
            }
            C1358d c1358d = (C1358d) o;
            if (!kr.equals(this.eR, c1358d.eR) || !kr.equals(this.eS, c1358d.eS) || !kr.equals(this.eT, c1358d.eT)) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1358d.adU == null || c1358d.adU.isEmpty();
            } else {
                return this.adU.equals(c1358d.adU);
            }
        }

        public C1358d m2945h() {
            this.eR = C1367a.m2996r();
            this.eS = C1367a.m2996r();
            this.eT = C1357c.m2935f();
            this.adU = null;
            this.adY = -1;
            return this;
        }

        public int hashCode() {
            int hashCode = (((((kr.hashCode(this.eR) + 527) * 31) + kr.hashCode(this.eS)) * 31) + kr.hashCode(this.eT)) * 31;
            int hashCode2 = (this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode();
            return hashCode2 + hashCode;
        }
    }

    /* renamed from: com.google.android.gms.internal.c.e */
    public static final class C1359e extends kp<C1359e> {
        private static volatile C1359e[] eU;
        public int key;
        public int value;

        public C1359e() {
            m2951j();
        }

        public static C1359e[] m2946i() {
            if (eU == null) {
                synchronized (kr.adX) {
                    if (eU == null) {
                        eU = new C1359e[0];
                    }
                }
            }
            return eU;
        }

        public void m2947a(ko koVar) throws IOException {
            koVar.m1157i(1, this.key);
            koVar.m1157i(2, this.value);
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m2948b(kn knVar) throws IOException {
            return m2950f(knVar);
        }

        public int m2949c() {
            int c = (super.m1172c() + ko.m1143j(1, this.key)) + ko.m1143j(2, this.value);
            this.adY = c;
            return c;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1359e)) {
                return false;
            }
            C1359e c1359e = (C1359e) o;
            if (this.key != c1359e.key || this.value != c1359e.value) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1359e.adU == null || c1359e.adU.isEmpty();
            } else {
                return this.adU.equals(c1359e.adU);
            }
        }

        public C1359e m2950f(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_LOCALE /*8*/:
                        this.key = knVar.mk();
                        continue;
                    case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                        this.value = knVar.mk();
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

        public int hashCode() {
            int i = (((this.key + 527) * 31) + this.value) * 31;
            int hashCode = (this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode();
            return hashCode + i;
        }

        public C1359e m2951j() {
            this.key = 0;
            this.value = 0;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    /* renamed from: com.google.android.gms.internal.c.f */
    public static final class C1360f extends kp<C1360f> {
        public String[] eV;
        public String[] eW;
        public C1367a[] eX;
        public C1359e[] eY;
        public C1356b[] eZ;
        public C1356b[] fa;
        public C1356b[] fb;
        public C1361g[] fc;
        public String fd;
        public String fe;
        public String ff;
        public String fg;
        public C1355a fh;
        public float fi;
        public boolean fj;
        public String[] fk;
        public int fl;

        public C1360f() {
            m2957k();
        }

        public static C1360f m2952a(byte[] bArr) throws ks {
            return (C1360f) kt.m1166a(new C1360f(), bArr);
        }

        public void m2953a(ko koVar) throws IOException {
            int i = 0;
            if (this.eW != null && this.eW.length > 0) {
                for (String str : this.eW) {
                    if (str != null) {
                        koVar.m1152b(1, str);
                    }
                }
            }
            if (this.eX != null && this.eX.length > 0) {
                for (kt ktVar : this.eX) {
                    if (ktVar != null) {
                        koVar.m1147a(2, ktVar);
                    }
                }
            }
            if (this.eY != null && this.eY.length > 0) {
                for (kt ktVar2 : this.eY) {
                    if (ktVar2 != null) {
                        koVar.m1147a(3, ktVar2);
                    }
                }
            }
            if (this.eZ != null && this.eZ.length > 0) {
                for (kt ktVar22 : this.eZ) {
                    if (ktVar22 != null) {
                        koVar.m1147a(4, ktVar22);
                    }
                }
            }
            if (this.fa != null && this.fa.length > 0) {
                for (kt ktVar222 : this.fa) {
                    if (ktVar222 != null) {
                        koVar.m1147a(5, ktVar222);
                    }
                }
            }
            if (this.fb != null && this.fb.length > 0) {
                for (kt ktVar2222 : this.fb) {
                    if (ktVar2222 != null) {
                        koVar.m1147a(6, ktVar2222);
                    }
                }
            }
            if (this.fc != null && this.fc.length > 0) {
                for (kt ktVar22222 : this.fc) {
                    if (ktVar22222 != null) {
                        koVar.m1147a(7, ktVar22222);
                    }
                }
            }
            if (!this.fd.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                koVar.m1152b(9, this.fd);
            }
            if (!this.fe.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                koVar.m1152b(10, this.fe);
            }
            if (!this.ff.equals("0")) {
                koVar.m1152b(12, this.ff);
            }
            if (!this.fg.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                koVar.m1152b(13, this.fg);
            }
            if (this.fh != null) {
                koVar.m1147a(14, this.fh);
            }
            if (Float.floatToIntBits(this.fi) != Float.floatToIntBits(0.0f)) {
                koVar.m1150b(15, this.fi);
            }
            if (this.fk != null && this.fk.length > 0) {
                for (String str2 : this.fk) {
                    if (str2 != null) {
                        koVar.m1152b(16, str2);
                    }
                }
            }
            if (this.fl != 0) {
                koVar.m1157i(17, this.fl);
            }
            if (this.fj) {
                koVar.m1148a(18, this.fj);
            }
            if (this.eV != null && this.eV.length > 0) {
                while (i < this.eV.length) {
                    String str3 = this.eV[i];
                    if (str3 != null) {
                        koVar.m1152b(19, str3);
                    }
                    i++;
                }
            }
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m2954b(kn knVar) throws IOException {
            return m2956g(knVar);
        }

        public int m2955c() {
            int i;
            int i2;
            int i3;
            int i4 = 0;
            int c = super.m1172c();
            if (this.eW == null || this.eW.length <= 0) {
                i = c;
            } else {
                i2 = 0;
                i3 = 0;
                for (String str : this.eW) {
                    if (str != null) {
                        i3++;
                        i2 += ko.cf(str);
                    }
                }
                i = (c + i2) + (i3 * 1);
            }
            if (this.eX != null && this.eX.length > 0) {
                i2 = i;
                for (kt ktVar : this.eX) {
                    if (ktVar != null) {
                        i2 += ko.m1134b(2, ktVar);
                    }
                }
                i = i2;
            }
            if (this.eY != null && this.eY.length > 0) {
                i2 = i;
                for (kt ktVar2 : this.eY) {
                    if (ktVar2 != null) {
                        i2 += ko.m1134b(3, ktVar2);
                    }
                }
                i = i2;
            }
            if (this.eZ != null && this.eZ.length > 0) {
                i2 = i;
                for (kt ktVar22 : this.eZ) {
                    if (ktVar22 != null) {
                        i2 += ko.m1134b(4, ktVar22);
                    }
                }
                i = i2;
            }
            if (this.fa != null && this.fa.length > 0) {
                i2 = i;
                for (kt ktVar222 : this.fa) {
                    if (ktVar222 != null) {
                        i2 += ko.m1134b(5, ktVar222);
                    }
                }
                i = i2;
            }
            if (this.fb != null && this.fb.length > 0) {
                i2 = i;
                for (kt ktVar2222 : this.fb) {
                    if (ktVar2222 != null) {
                        i2 += ko.m1134b(6, ktVar2222);
                    }
                }
                i = i2;
            }
            if (this.fc != null && this.fc.length > 0) {
                i2 = i;
                for (kt ktVar22222 : this.fc) {
                    if (ktVar22222 != null) {
                        i2 += ko.m1134b(7, ktVar22222);
                    }
                }
                i = i2;
            }
            if (!this.fd.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                i += ko.m1142g(9, this.fd);
            }
            if (!this.fe.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                i += ko.m1142g(10, this.fe);
            }
            if (!this.ff.equals("0")) {
                i += ko.m1142g(12, this.ff);
            }
            if (!this.fg.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                i += ko.m1142g(13, this.fg);
            }
            if (this.fh != null) {
                i += ko.m1134b(14, this.fh);
            }
            if (Float.floatToIntBits(this.fi) != Float.floatToIntBits(0.0f)) {
                i += ko.m1137c(15, this.fi);
            }
            if (this.fk != null && this.fk.length > 0) {
                i3 = 0;
                c = 0;
                for (String str2 : this.fk) {
                    if (str2 != null) {
                        c++;
                        i3 += ko.cf(str2);
                    }
                }
                i = (i + i3) + (c * 2);
            }
            if (this.fl != 0) {
                i += ko.m1143j(17, this.fl);
            }
            if (this.fj) {
                i += ko.m1135b(18, this.fj);
            }
            if (this.eV != null && this.eV.length > 0) {
                i2 = 0;
                i3 = 0;
                while (i4 < this.eV.length) {
                    String str3 = this.eV[i4];
                    if (str3 != null) {
                        i3++;
                        i2 += ko.cf(str3);
                    }
                    i4++;
                }
                i = (i + i2) + (i3 * 2);
            }
            this.adY = i;
            return i;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1360f)) {
                return false;
            }
            C1360f c1360f = (C1360f) o;
            if (!kr.equals(this.eV, c1360f.eV) || !kr.equals(this.eW, c1360f.eW) || !kr.equals(this.eX, c1360f.eX) || !kr.equals(this.eY, c1360f.eY) || !kr.equals(this.eZ, c1360f.eZ) || !kr.equals(this.fa, c1360f.fa) || !kr.equals(this.fb, c1360f.fb) || !kr.equals(this.fc, c1360f.fc)) {
                return false;
            }
            if (this.fd == null) {
                if (c1360f.fd != null) {
                    return false;
                }
            } else if (!this.fd.equals(c1360f.fd)) {
                return false;
            }
            if (this.fe == null) {
                if (c1360f.fe != null) {
                    return false;
                }
            } else if (!this.fe.equals(c1360f.fe)) {
                return false;
            }
            if (this.ff == null) {
                if (c1360f.ff != null) {
                    return false;
                }
            } else if (!this.ff.equals(c1360f.ff)) {
                return false;
            }
            if (this.fg == null) {
                if (c1360f.fg != null) {
                    return false;
                }
            } else if (!this.fg.equals(c1360f.fg)) {
                return false;
            }
            if (this.fh == null) {
                if (c1360f.fh != null) {
                    return false;
                }
            } else if (!this.fh.equals(c1360f.fh)) {
                return false;
            }
            if (Float.floatToIntBits(this.fi) != Float.floatToIntBits(c1360f.fi) || this.fj != c1360f.fj || !kr.equals(this.fk, c1360f.fk) || this.fl != c1360f.fl) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1360f.adU == null || c1360f.adU.isEmpty();
            } else {
                return this.adU.equals(c1360f.adU);
            }
        }

        public C1360f m2956g(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                int b;
                Object obj;
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_TIME_ZONE /*10*/:
                        b = kw.m1176b(knVar, 10);
                        mh = this.eW == null ? 0 : this.eW.length;
                        obj = new String[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.eW, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.readString();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.readString();
                        this.eW = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                        b = kw.m1176b(knVar, 18);
                        mh = this.eX == null ? 0 : this.eX.length;
                        obj = new C1367a[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.eX, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1367a();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1367a();
                        knVar.m1126a(obj[mh]);
                        this.eX = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                        b = kw.m1176b(knVar, 26);
                        mh = this.eY == null ? 0 : this.eY.length;
                        obj = new C1359e[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.eY, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1359e();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1359e();
                        knVar.m1126a(obj[mh]);
                        this.eY = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_duplicateParentState /*34*/:
                        b = kw.m1176b(knVar, 34);
                        mh = this.eZ == null ? 0 : this.eZ.length;
                        obj = new C1356b[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.eZ, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1356b();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1356b();
                        knVar.m1126a(obj[mh]);
                        this.eZ = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_isScrollContainer /*42*/:
                        b = kw.m1176b(knVar, 42);
                        mh = this.fa == null ? 0 : this.fa.length;
                        obj = new C1356b[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fa, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1356b();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1356b();
                        knVar.m1126a(obj[mh]);
                        this.fa = obj;
                        continue;
                    case AdSize.PORTRAIT_AD_HEIGHT /*50*/:
                        b = kw.m1176b(knVar, 50);
                        mh = this.fb == null ? 0 : this.fb.length;
                        obj = new C1356b[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fb, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1356b();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1356b();
                        knVar.m1126a(obj[mh]);
                        this.fb = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_rotation /*58*/:
                        b = kw.m1176b(knVar, 58);
                        mh = this.fc == null ? 0 : this.fc.length;
                        obj = new C1361g[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fc, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1361g();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1361g();
                        knVar.m1126a(obj[mh]);
                        this.fc = obj;
                        continue;
                    case 74:
                        this.fd = knVar.readString();
                        continue;
                    case 82:
                        this.fe = knVar.readString();
                        continue;
                    case 98:
                        this.ff = knVar.readString();
                        continue;
                    case 106:
                        this.fg = knVar.readString();
                        continue;
                    case 114:
                        if (this.fh == null) {
                            this.fh = new C1355a();
                        }
                        knVar.m1126a(this.fh);
                        continue;
                    case 125:
                        this.fi = knVar.readFloat();
                        continue;
                    case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                        b = kw.m1176b(knVar, TransportMediator.KEYCODE_MEDIA_RECORD);
                        mh = this.fk == null ? 0 : this.fk.length;
                        obj = new String[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fk, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.readString();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.readString();
                        this.fk = obj;
                        continue;
                    case 136:
                        this.fl = knVar.mk();
                        continue;
                    case 144:
                        this.fj = knVar.ml();
                        continue;
                    case 154:
                        b = kw.m1176b(knVar, 154);
                        mh = this.eV == null ? 0 : this.eV.length;
                        obj = new String[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.eV, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.readString();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.readString();
                        this.eV = obj;
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

        public int hashCode() {
            int i = 0;
            int hashCode = ((((((this.fj ? 1231 : 1237) + (((((this.fh == null ? 0 : this.fh.hashCode()) + (((this.fg == null ? 0 : this.fg.hashCode()) + (((this.ff == null ? 0 : this.ff.hashCode()) + (((this.fe == null ? 0 : this.fe.hashCode()) + (((this.fd == null ? 0 : this.fd.hashCode()) + ((((((((((((((((kr.hashCode(this.eV) + 527) * 31) + kr.hashCode(this.eW)) * 31) + kr.hashCode(this.eX)) * 31) + kr.hashCode(this.eY)) * 31) + kr.hashCode(this.eZ)) * 31) + kr.hashCode(this.fa)) * 31) + kr.hashCode(this.fb)) * 31) + kr.hashCode(this.fc)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + Float.floatToIntBits(this.fi)) * 31)) * 31) + kr.hashCode(this.fk)) * 31) + this.fl) * 31;
            if (!(this.adU == null || this.adU.isEmpty())) {
                i = this.adU.hashCode();
            }
            return hashCode + i;
        }

        public C1360f m2957k() {
            this.eV = kw.aef;
            this.eW = kw.aef;
            this.eX = C1367a.m2996r();
            this.eY = C1359e.m2946i();
            this.eZ = C1356b.m2929d();
            this.fa = C1356b.m2929d();
            this.fb = C1356b.m2929d();
            this.fc = C1361g.m2958l();
            this.fd = UnsupportedUrlFragment.DISPLAY_NAME;
            this.fe = UnsupportedUrlFragment.DISPLAY_NAME;
            this.ff = "0";
            this.fg = UnsupportedUrlFragment.DISPLAY_NAME;
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

    /* renamed from: com.google.android.gms.internal.c.g */
    public static final class C1361g extends kp<C1361g> {
        private static volatile C1361g[] fm;
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

        public C1361g() {
            m2963m();
        }

        public static C1361g[] m2958l() {
            if (fm == null) {
                synchronized (kr.adX) {
                    if (fm == null) {
                        fm = new C1361g[0];
                    }
                }
            }
            return fm;
        }

        public void m2959a(ko koVar) throws IOException {
            int i = 0;
            if (this.fn != null && this.fn.length > 0) {
                for (int i2 : this.fn) {
                    koVar.m1157i(1, i2);
                }
            }
            if (this.fo != null && this.fo.length > 0) {
                for (int i22 : this.fo) {
                    koVar.m1157i(2, i22);
                }
            }
            if (this.fp != null && this.fp.length > 0) {
                for (int i222 : this.fp) {
                    koVar.m1157i(3, i222);
                }
            }
            if (this.fq != null && this.fq.length > 0) {
                for (int i2222 : this.fq) {
                    koVar.m1157i(4, i2222);
                }
            }
            if (this.fr != null && this.fr.length > 0) {
                for (int i22222 : this.fr) {
                    koVar.m1157i(5, i22222);
                }
            }
            if (this.fs != null && this.fs.length > 0) {
                for (int i222222 : this.fs) {
                    koVar.m1157i(6, i222222);
                }
            }
            if (this.ft != null && this.ft.length > 0) {
                for (int i2222222 : this.ft) {
                    koVar.m1157i(7, i2222222);
                }
            }
            if (this.fu != null && this.fu.length > 0) {
                for (int i22222222 : this.fu) {
                    koVar.m1157i(8, i22222222);
                }
            }
            if (this.fv != null && this.fv.length > 0) {
                for (int i222222222 : this.fv) {
                    koVar.m1157i(9, i222222222);
                }
            }
            if (this.fw != null && this.fw.length > 0) {
                while (i < this.fw.length) {
                    koVar.m1157i(10, this.fw[i]);
                    i++;
                }
            }
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m2960b(kn knVar) throws IOException {
            return m2962h(knVar);
        }

        public int m2961c() {
            int i;
            int i2;
            int i3 = 0;
            int c = super.m1172c();
            if (this.fn == null || this.fn.length <= 0) {
                i = c;
            } else {
                i2 = 0;
                for (int cX : this.fn) {
                    i2 += ko.cX(cX);
                }
                i = (c + i2) + (this.fn.length * 1);
            }
            if (this.fo != null && this.fo.length > 0) {
                c = 0;
                for (int cX2 : this.fo) {
                    c += ko.cX(cX2);
                }
                i = (i + c) + (this.fo.length * 1);
            }
            if (this.fp != null && this.fp.length > 0) {
                c = 0;
                for (int cX22 : this.fp) {
                    c += ko.cX(cX22);
                }
                i = (i + c) + (this.fp.length * 1);
            }
            if (this.fq != null && this.fq.length > 0) {
                c = 0;
                for (int cX222 : this.fq) {
                    c += ko.cX(cX222);
                }
                i = (i + c) + (this.fq.length * 1);
            }
            if (this.fr != null && this.fr.length > 0) {
                c = 0;
                for (int cX2222 : this.fr) {
                    c += ko.cX(cX2222);
                }
                i = (i + c) + (this.fr.length * 1);
            }
            if (this.fs != null && this.fs.length > 0) {
                c = 0;
                for (int cX22222 : this.fs) {
                    c += ko.cX(cX22222);
                }
                i = (i + c) + (this.fs.length * 1);
            }
            if (this.ft != null && this.ft.length > 0) {
                c = 0;
                for (int cX222222 : this.ft) {
                    c += ko.cX(cX222222);
                }
                i = (i + c) + (this.ft.length * 1);
            }
            if (this.fu != null && this.fu.length > 0) {
                c = 0;
                for (int cX2222222 : this.fu) {
                    c += ko.cX(cX2222222);
                }
                i = (i + c) + (this.fu.length * 1);
            }
            if (this.fv != null && this.fv.length > 0) {
                c = 0;
                for (int cX22222222 : this.fv) {
                    c += ko.cX(cX22222222);
                }
                i = (i + c) + (this.fv.length * 1);
            }
            if (this.fw != null && this.fw.length > 0) {
                i2 = 0;
                while (i3 < this.fw.length) {
                    i2 += ko.cX(this.fw[i3]);
                    i3++;
                }
                i = (i + i2) + (this.fw.length * 1);
            }
            this.adY = i;
            return i;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1361g)) {
                return false;
            }
            C1361g c1361g = (C1361g) o;
            if (!kr.equals(this.fn, c1361g.fn) || !kr.equals(this.fo, c1361g.fo) || !kr.equals(this.fp, c1361g.fp) || !kr.equals(this.fq, c1361g.fq) || !kr.equals(this.fr, c1361g.fr) || !kr.equals(this.fs, c1361g.fs) || !kr.equals(this.ft, c1361g.ft) || !kr.equals(this.fu, c1361g.fu) || !kr.equals(this.fv, c1361g.fv) || !kr.equals(this.fw, c1361g.fw)) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1361g.adU == null || c1361g.adU.isEmpty();
            } else {
                return this.adU.equals(c1361g.adU);
            }
        }

        public C1361g m2962h(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                int b;
                Object obj;
                int cR;
                Object obj2;
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_LOCALE /*8*/:
                        b = kw.m1176b(knVar, 8);
                        mh = this.fn == null ? 0 : this.fn.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fn, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fn = obj;
                        continue;
                    case Std.STD_TIME_ZONE /*10*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fn == null ? 0 : this.fn.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fn, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fn = obj2;
                        knVar.cS(cR);
                        continue;
                    case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                        b = kw.m1176b(knVar, 16);
                        mh = this.fo == null ? 0 : this.fo.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fo, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fo = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fo == null ? 0 : this.fo.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fo, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fo = obj2;
                        knVar.cS(cR);
                        continue;
                    case C0065R.styleable.TwoWayView_android_fadingEdge /*24*/:
                        b = kw.m1176b(knVar, 24);
                        mh = this.fp == null ? 0 : this.fp.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fp, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fp = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fp == null ? 0 : this.fp.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fp, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fp = obj2;
                        knVar.cS(cR);
                        continue;
                    case TransportMediator.FLAG_KEY_MEDIA_STOP /*32*/:
                        b = kw.m1176b(knVar, 32);
                        mh = this.fq == null ? 0 : this.fq.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fq, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fq = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_duplicateParentState /*34*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fq == null ? 0 : this.fq.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fq, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fq = obj2;
                        knVar.cS(cR);
                        continue;
                    case JSONzip.substringLimit /*40*/:
                        b = kw.m1176b(knVar, 40);
                        mh = this.fr == null ? 0 : this.fr.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fr, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fr = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_isScrollContainer /*42*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fr == null ? 0 : this.fr.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fr, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fr = obj2;
                        knVar.cS(cR);
                        continue;
                    case C0065R.styleable.TwoWayView_android_fadeScrollbars /*48*/:
                        b = kw.m1176b(knVar, 48);
                        mh = this.fs == null ? 0 : this.fs.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fs, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fs = obj;
                        continue;
                    case AdSize.PORTRAIT_AD_HEIGHT /*50*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fs == null ? 0 : this.fs.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fs, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fs = obj2;
                        knVar.cS(cR);
                        continue;
                    case C0065R.styleable.TwoWayView_android_scaleX /*56*/:
                        b = kw.m1176b(knVar, 56);
                        mh = this.ft == null ? 0 : this.ft.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.ft, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.ft = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_rotation /*58*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.ft == null ? 0 : this.ft.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.ft, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.ft = obj2;
                        knVar.cS(cR);
                        continue;
                    case BaseNCodec.PEM_CHUNK_SIZE /*64*/:
                        b = kw.m1176b(knVar, 64);
                        mh = this.fu == null ? 0 : this.fu.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fu, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fu = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_layoutDirection /*66*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fu == null ? 0 : this.fu.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fu, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fu = obj2;
                        knVar.cS(cR);
                        continue;
                    case 72:
                        b = kw.m1176b(knVar, 72);
                        mh = this.fv == null ? 0 : this.fv.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fv, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fv = obj;
                        continue;
                    case 74:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fv == null ? 0 : this.fv.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fv, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fv = obj2;
                        knVar.cS(cR);
                        continue;
                    case 80:
                        b = kw.m1176b(knVar, 80);
                        mh = this.fw == null ? 0 : this.fw.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fw, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fw = obj;
                        continue;
                    case 82:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fw == null ? 0 : this.fw.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fw, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fw = obj2;
                        knVar.cS(cR);
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

        public int hashCode() {
            int hashCode = (((((((((((((((((((kr.hashCode(this.fn) + 527) * 31) + kr.hashCode(this.fo)) * 31) + kr.hashCode(this.fp)) * 31) + kr.hashCode(this.fq)) * 31) + kr.hashCode(this.fr)) * 31) + kr.hashCode(this.fs)) * 31) + kr.hashCode(this.ft)) * 31) + kr.hashCode(this.fu)) * 31) + kr.hashCode(this.fv)) * 31) + kr.hashCode(this.fw)) * 31;
            int hashCode2 = (this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode();
            return hashCode2 + hashCode;
        }

        public C1361g m2963m() {
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

    /* renamed from: com.google.android.gms.internal.c.h */
    public static final class C1362h extends kp<C1362h> {
        public static final kq<C1367a, C1362h> fx;
        private static final C1362h[] fy;
        public int[] fA;
        public int[] fB;
        public int fC;
        public int[] fD;
        public int fE;
        public int fF;
        public int[] fz;

        static {
            fx = kq.m1162a(11, C1362h.class, 810);
            fy = new C1362h[0];
        }

        public C1362h() {
            m2968n();
        }

        public void m2964a(ko koVar) throws IOException {
            int i = 0;
            if (this.fz != null && this.fz.length > 0) {
                for (int i2 : this.fz) {
                    koVar.m1157i(1, i2);
                }
            }
            if (this.fA != null && this.fA.length > 0) {
                for (int i22 : this.fA) {
                    koVar.m1157i(2, i22);
                }
            }
            if (this.fB != null && this.fB.length > 0) {
                for (int i222 : this.fB) {
                    koVar.m1157i(3, i222);
                }
            }
            if (this.fC != 0) {
                koVar.m1157i(4, this.fC);
            }
            if (this.fD != null && this.fD.length > 0) {
                while (i < this.fD.length) {
                    koVar.m1157i(5, this.fD[i]);
                    i++;
                }
            }
            if (this.fE != 0) {
                koVar.m1157i(6, this.fE);
            }
            if (this.fF != 0) {
                koVar.m1157i(7, this.fF);
            }
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m2965b(kn knVar) throws IOException {
            return m2967i(knVar);
        }

        public int m2966c() {
            int i;
            int i2;
            int i3 = 0;
            int c = super.m1172c();
            if (this.fz == null || this.fz.length <= 0) {
                i = c;
            } else {
                i2 = 0;
                for (int cX : this.fz) {
                    i2 += ko.cX(cX);
                }
                i = (c + i2) + (this.fz.length * 1);
            }
            if (this.fA != null && this.fA.length > 0) {
                c = 0;
                for (int cX2 : this.fA) {
                    c += ko.cX(cX2);
                }
                i = (i + c) + (this.fA.length * 1);
            }
            if (this.fB != null && this.fB.length > 0) {
                c = 0;
                for (int cX22 : this.fB) {
                    c += ko.cX(cX22);
                }
                i = (i + c) + (this.fB.length * 1);
            }
            if (this.fC != 0) {
                i += ko.m1143j(4, this.fC);
            }
            if (this.fD != null && this.fD.length > 0) {
                i2 = 0;
                while (i3 < this.fD.length) {
                    i2 += ko.cX(this.fD[i3]);
                    i3++;
                }
                i = (i + i2) + (this.fD.length * 1);
            }
            if (this.fE != 0) {
                i += ko.m1143j(6, this.fE);
            }
            if (this.fF != 0) {
                i += ko.m1143j(7, this.fF);
            }
            this.adY = i;
            return i;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1362h)) {
                return false;
            }
            C1362h c1362h = (C1362h) o;
            if (!kr.equals(this.fz, c1362h.fz) || !kr.equals(this.fA, c1362h.fA) || !kr.equals(this.fB, c1362h.fB) || this.fC != c1362h.fC || !kr.equals(this.fD, c1362h.fD) || this.fE != c1362h.fE || this.fF != c1362h.fF) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1362h.adU == null || c1362h.adU.isEmpty();
            } else {
                return this.adU.equals(c1362h.adU);
            }
        }

        public int hashCode() {
            int hashCode = (((((((((((((kr.hashCode(this.fz) + 527) * 31) + kr.hashCode(this.fA)) * 31) + kr.hashCode(this.fB)) * 31) + this.fC) * 31) + kr.hashCode(this.fD)) * 31) + this.fE) * 31) + this.fF) * 31;
            int hashCode2 = (this.adU == null || this.adU.isEmpty()) ? 0 : this.adU.hashCode();
            return hashCode2 + hashCode;
        }

        public C1362h m2967i(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                int b;
                Object obj;
                int cR;
                Object obj2;
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_LOCALE /*8*/:
                        b = kw.m1176b(knVar, 8);
                        mh = this.fz == null ? 0 : this.fz.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fz, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fz = obj;
                        continue;
                    case Std.STD_TIME_ZONE /*10*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fz == null ? 0 : this.fz.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fz, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fz = obj2;
                        knVar.cS(cR);
                        continue;
                    case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                        b = kw.m1176b(knVar, 16);
                        mh = this.fA == null ? 0 : this.fA.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fA, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fA = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fA == null ? 0 : this.fA.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fA, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fA = obj2;
                        knVar.cS(cR);
                        continue;
                    case C0065R.styleable.TwoWayView_android_fadingEdge /*24*/:
                        b = kw.m1176b(knVar, 24);
                        mh = this.fB == null ? 0 : this.fB.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fB, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fB = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fB == null ? 0 : this.fB.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fB, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fB = obj2;
                        knVar.cS(cR);
                        continue;
                    case TransportMediator.FLAG_KEY_MEDIA_STOP /*32*/:
                        this.fC = knVar.mk();
                        continue;
                    case JSONzip.substringLimit /*40*/:
                        b = kw.m1176b(knVar, 40);
                        mh = this.fD == null ? 0 : this.fD.length;
                        obj = new int[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fD, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = knVar.mk();
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = knVar.mk();
                        this.fD = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_isScrollContainer /*42*/:
                        cR = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            knVar.mk();
                            mh++;
                        }
                        knVar.cT(b);
                        b = this.fD == null ? 0 : this.fD.length;
                        obj2 = new int[(mh + b)];
                        if (b != 0) {
                            System.arraycopy(this.fD, 0, obj2, 0, b);
                        }
                        while (b < obj2.length) {
                            obj2[b] = knVar.mk();
                            b++;
                        }
                        this.fD = obj2;
                        knVar.cS(cR);
                        continue;
                    case C0065R.styleable.TwoWayView_android_fadeScrollbars /*48*/:
                        this.fE = knVar.mk();
                        continue;
                    case C0065R.styleable.TwoWayView_android_scaleX /*56*/:
                        this.fF = knVar.mk();
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

        public C1362h m2968n() {
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

    /* renamed from: com.google.android.gms.internal.c.i */
    public static final class C1363i extends kp<C1363i> {
        private static volatile C1363i[] fG;
        public C1367a fH;
        public C1358d fI;
        public String name;

        public C1363i() {
            m2974p();
        }

        public static C1363i[] m2969o() {
            if (fG == null) {
                synchronized (kr.adX) {
                    if (fG == null) {
                        fG = new C1363i[0];
                    }
                }
            }
            return fG;
        }

        public void m2970a(ko koVar) throws IOException {
            if (!this.name.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                koVar.m1152b(1, this.name);
            }
            if (this.fH != null) {
                koVar.m1147a(2, this.fH);
            }
            if (this.fI != null) {
                koVar.m1147a(3, this.fI);
            }
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m2971b(kn knVar) throws IOException {
            return m2973j(knVar);
        }

        public int m2972c() {
            int c = super.m1172c();
            if (!this.name.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                c += ko.m1142g(1, this.name);
            }
            if (this.fH != null) {
                c += ko.m1134b(2, this.fH);
            }
            if (this.fI != null) {
                c += ko.m1134b(3, this.fI);
            }
            this.adY = c;
            return c;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1363i)) {
                return false;
            }
            C1363i c1363i = (C1363i) o;
            if (this.name == null) {
                if (c1363i.name != null) {
                    return false;
                }
            } else if (!this.name.equals(c1363i.name)) {
                return false;
            }
            if (this.fH == null) {
                if (c1363i.fH != null) {
                    return false;
                }
            } else if (!this.fH.equals(c1363i.fH)) {
                return false;
            }
            if (this.fI == null) {
                if (c1363i.fI != null) {
                    return false;
                }
            } else if (!this.fI.equals(c1363i.fI)) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1363i.adU == null || c1363i.adU.isEmpty();
            } else {
                return this.adU.equals(c1363i.adU);
            }
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.fI == null ? 0 : this.fI.hashCode()) + (((this.fH == null ? 0 : this.fH.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + 527) * 31)) * 31)) * 31;
            if (!(this.adU == null || this.adU.isEmpty())) {
                i = this.adU.hashCode();
            }
            return hashCode + i;
        }

        public C1363i m2973j(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_TIME_ZONE /*10*/:
                        this.name = knVar.readString();
                        continue;
                    case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                        if (this.fH == null) {
                            this.fH = new C1367a();
                        }
                        knVar.m1126a(this.fH);
                        continue;
                    case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                        if (this.fI == null) {
                            this.fI = new C1358d();
                        }
                        knVar.m1126a(this.fI);
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

        public C1363i m2974p() {
            this.name = UnsupportedUrlFragment.DISPLAY_NAME;
            this.fH = null;
            this.fI = null;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }

    /* renamed from: com.google.android.gms.internal.c.j */
    public static final class C1364j extends kp<C1364j> {
        public C1363i[] fJ;
        public C1360f fK;
        public String fL;

        public C1364j() {
            m2980q();
        }

        public static C1364j m2975b(byte[] bArr) throws ks {
            return (C1364j) kt.m1166a(new C1364j(), bArr);
        }

        public void m2976a(ko koVar) throws IOException {
            if (this.fJ != null && this.fJ.length > 0) {
                for (kt ktVar : this.fJ) {
                    if (ktVar != null) {
                        koVar.m1147a(1, ktVar);
                    }
                }
            }
            if (this.fK != null) {
                koVar.m1147a(2, this.fK);
            }
            if (!this.fL.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                koVar.m1152b(3, this.fL);
            }
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m2977b(kn knVar) throws IOException {
            return m2979k(knVar);
        }

        public int m2978c() {
            int c = super.m1172c();
            if (this.fJ != null && this.fJ.length > 0) {
                for (kt ktVar : this.fJ) {
                    if (ktVar != null) {
                        c += ko.m1134b(1, ktVar);
                    }
                }
            }
            if (this.fK != null) {
                c += ko.m1134b(2, this.fK);
            }
            if (!this.fL.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                c += ko.m1142g(3, this.fL);
            }
            this.adY = c;
            return c;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1364j)) {
                return false;
            }
            C1364j c1364j = (C1364j) o;
            if (!kr.equals(this.fJ, c1364j.fJ)) {
                return false;
            }
            if (this.fK == null) {
                if (c1364j.fK != null) {
                    return false;
                }
            } else if (!this.fK.equals(c1364j.fK)) {
                return false;
            }
            if (this.fL == null) {
                if (c1364j.fL != null) {
                    return false;
                }
            } else if (!this.fL.equals(c1364j.fL)) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1364j.adU == null || c1364j.adU.isEmpty();
            } else {
                return this.adU.equals(c1364j.adU);
            }
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.fL == null ? 0 : this.fL.hashCode()) + (((this.fK == null ? 0 : this.fK.hashCode()) + ((kr.hashCode(this.fJ) + 527) * 31)) * 31)) * 31;
            if (!(this.adU == null || this.adU.isEmpty())) {
                i = this.adU.hashCode();
            }
            return hashCode + i;
        }

        public C1364j m2979k(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_TIME_ZONE /*10*/:
                        int b = kw.m1176b(knVar, 10);
                        mh = this.fJ == null ? 0 : this.fJ.length;
                        Object obj = new C1363i[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fJ, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1363i();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1363i();
                        knVar.m1126a(obj[mh]);
                        this.fJ = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                        if (this.fK == null) {
                            this.fK = new C1360f();
                        }
                        knVar.m1126a(this.fK);
                        continue;
                    case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                        this.fL = knVar.readString();
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

        public C1364j m2980q() {
            this.fJ = C1363i.m2969o();
            this.fK = null;
            this.fL = UnsupportedUrlFragment.DISPLAY_NAME;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }
}
