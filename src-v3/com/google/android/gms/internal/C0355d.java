package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.ads.AdSize;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import java.io.IOException;
import org.apache.commons.codec.binary.BaseNCodec;
import org.json.zip.JSONzip;

/* renamed from: com.google.android.gms.internal.d */
public interface C0355d {

    /* renamed from: com.google.android.gms.internal.d.a */
    public static final class C1367a extends kp<C1367a> {
        private static volatile C1367a[] fM;
        public String fN;
        public C1367a[] fO;
        public C1367a[] fP;
        public C1367a[] fQ;
        public String fR;
        public String fS;
        public long fT;
        public boolean fU;
        public C1367a[] fV;
        public int[] fW;
        public boolean fX;
        public int type;

        public C1367a() {
            m3001s();
        }

        public static C1367a[] m2996r() {
            if (fM == null) {
                synchronized (kr.adX) {
                    if (fM == null) {
                        fM = new C1367a[0];
                    }
                }
            }
            return fM;
        }

        public void m2997a(ko koVar) throws IOException {
            int i = 0;
            koVar.m1157i(1, this.type);
            if (!this.fN.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                koVar.m1152b(2, this.fN);
            }
            if (this.fO != null && this.fO.length > 0) {
                for (kt ktVar : this.fO) {
                    if (ktVar != null) {
                        koVar.m1147a(3, ktVar);
                    }
                }
            }
            if (this.fP != null && this.fP.length > 0) {
                for (kt ktVar2 : this.fP) {
                    if (ktVar2 != null) {
                        koVar.m1147a(4, ktVar2);
                    }
                }
            }
            if (this.fQ != null && this.fQ.length > 0) {
                for (kt ktVar22 : this.fQ) {
                    if (ktVar22 != null) {
                        koVar.m1147a(5, ktVar22);
                    }
                }
            }
            if (!this.fR.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                koVar.m1152b(6, this.fR);
            }
            if (!this.fS.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                koVar.m1152b(7, this.fS);
            }
            if (this.fT != 0) {
                koVar.m1151b(8, this.fT);
            }
            if (this.fX) {
                koVar.m1148a(9, this.fX);
            }
            if (this.fW != null && this.fW.length > 0) {
                for (int i2 : this.fW) {
                    koVar.m1157i(10, i2);
                }
            }
            if (this.fV != null && this.fV.length > 0) {
                while (i < this.fV.length) {
                    kt ktVar3 = this.fV[i];
                    if (ktVar3 != null) {
                        koVar.m1147a(11, ktVar3);
                    }
                    i++;
                }
            }
            if (this.fU) {
                koVar.m1148a(12, this.fU);
            }
            super.m2324a(koVar);
        }

        public /* synthetic */ kt m2998b(kn knVar) throws IOException {
            return m3000l(knVar);
        }

        public int m2999c() {
            int i;
            int i2 = 0;
            int c = super.m1172c() + ko.m1143j(1, this.type);
            if (!this.fN.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                c += ko.m1142g(2, this.fN);
            }
            if (this.fO != null && this.fO.length > 0) {
                i = c;
                for (kt ktVar : this.fO) {
                    if (ktVar != null) {
                        i += ko.m1134b(3, ktVar);
                    }
                }
                c = i;
            }
            if (this.fP != null && this.fP.length > 0) {
                i = c;
                for (kt ktVar2 : this.fP) {
                    if (ktVar2 != null) {
                        i += ko.m1134b(4, ktVar2);
                    }
                }
                c = i;
            }
            if (this.fQ != null && this.fQ.length > 0) {
                i = c;
                for (kt ktVar22 : this.fQ) {
                    if (ktVar22 != null) {
                        i += ko.m1134b(5, ktVar22);
                    }
                }
                c = i;
            }
            if (!this.fR.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                c += ko.m1142g(6, this.fR);
            }
            if (!this.fS.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                c += ko.m1142g(7, this.fS);
            }
            if (this.fT != 0) {
                c += ko.m1139d(8, this.fT);
            }
            if (this.fX) {
                c += ko.m1135b(9, this.fX);
            }
            if (this.fW != null && this.fW.length > 0) {
                int i3 = 0;
                for (int cX : this.fW) {
                    i3 += ko.cX(cX);
                }
                c = (c + i3) + (this.fW.length * 1);
            }
            if (this.fV != null && this.fV.length > 0) {
                while (i2 < this.fV.length) {
                    kt ktVar3 = this.fV[i2];
                    if (ktVar3 != null) {
                        c += ko.m1134b(11, ktVar3);
                    }
                    i2++;
                }
            }
            if (this.fU) {
                c += ko.m1135b(12, this.fU);
            }
            this.adY = c;
            return c;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof C1367a)) {
                return false;
            }
            C1367a c1367a = (C1367a) o;
            if (this.type != c1367a.type) {
                return false;
            }
            if (this.fN == null) {
                if (c1367a.fN != null) {
                    return false;
                }
            } else if (!this.fN.equals(c1367a.fN)) {
                return false;
            }
            if (!kr.equals(this.fO, c1367a.fO) || !kr.equals(this.fP, c1367a.fP) || !kr.equals(this.fQ, c1367a.fQ)) {
                return false;
            }
            if (this.fR == null) {
                if (c1367a.fR != null) {
                    return false;
                }
            } else if (!this.fR.equals(c1367a.fR)) {
                return false;
            }
            if (this.fS == null) {
                if (c1367a.fS != null) {
                    return false;
                }
            } else if (!this.fS.equals(c1367a.fS)) {
                return false;
            }
            if (this.fT != c1367a.fT || this.fU != c1367a.fU || !kr.equals(this.fV, c1367a.fV) || !kr.equals(this.fW, c1367a.fW) || this.fX != c1367a.fX) {
                return false;
            }
            if (this.adU == null || this.adU.isEmpty()) {
                return c1367a.adU == null || c1367a.adU.isEmpty();
            } else {
                return this.adU.equals(c1367a.adU);
            }
        }

        public int hashCode() {
            int i = 1231;
            int i2 = 0;
            int hashCode = ((((((this.fU ? 1231 : 1237) + (((((this.fS == null ? 0 : this.fS.hashCode()) + (((this.fR == null ? 0 : this.fR.hashCode()) + (((((((((this.fN == null ? 0 : this.fN.hashCode()) + ((this.type + 527) * 31)) * 31) + kr.hashCode(this.fO)) * 31) + kr.hashCode(this.fP)) * 31) + kr.hashCode(this.fQ)) * 31)) * 31)) * 31) + ((int) (this.fT ^ (this.fT >>> 32)))) * 31)) * 31) + kr.hashCode(this.fV)) * 31) + kr.hashCode(this.fW)) * 31;
            if (!this.fX) {
                i = 1237;
            }
            hashCode = (hashCode + i) * 31;
            if (!(this.adU == null || this.adU.isEmpty())) {
                i2 = this.adU.hashCode();
            }
            return hashCode + i2;
        }

        public C1367a m3000l(kn knVar) throws IOException {
            while (true) {
                int mh = knVar.mh();
                int b;
                Object obj;
                int i;
                switch (mh) {
                    case JSONzip.zipEmptyObject /*0*/:
                        break;
                    case Std.STD_LOCALE /*8*/:
                        mh = knVar.mk();
                        switch (mh) {
                            case Std.STD_FILE /*1*/:
                            case Std.STD_URL /*2*/:
                            case Std.STD_URI /*3*/:
                            case Std.STD_CLASS /*4*/:
                            case Std.STD_JAVA_TYPE /*5*/:
                            case Std.STD_CURRENCY /*6*/:
                            case Std.STD_PATTERN /*7*/:
                            case Std.STD_LOCALE /*8*/:
                                this.type = mh;
                                break;
                            default:
                                continue;
                        }
                    case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                        this.fN = knVar.readString();
                        continue;
                    case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                        b = kw.m1176b(knVar, 26);
                        mh = this.fO == null ? 0 : this.fO.length;
                        obj = new C1367a[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fO, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1367a();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1367a();
                        knVar.m1126a(obj[mh]);
                        this.fO = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_duplicateParentState /*34*/:
                        b = kw.m1176b(knVar, 34);
                        mh = this.fP == null ? 0 : this.fP.length;
                        obj = new C1367a[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fP, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1367a();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1367a();
                        knVar.m1126a(obj[mh]);
                        this.fP = obj;
                        continue;
                    case C0065R.styleable.TwoWayView_android_isScrollContainer /*42*/:
                        b = kw.m1176b(knVar, 42);
                        mh = this.fQ == null ? 0 : this.fQ.length;
                        obj = new C1367a[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fQ, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1367a();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1367a();
                        knVar.m1126a(obj[mh]);
                        this.fQ = obj;
                        continue;
                    case AdSize.PORTRAIT_AD_HEIGHT /*50*/:
                        this.fR = knVar.readString();
                        continue;
                    case C0065R.styleable.TwoWayView_android_rotation /*58*/:
                        this.fS = knVar.readString();
                        continue;
                    case BaseNCodec.PEM_CHUNK_SIZE /*64*/:
                        this.fT = knVar.mj();
                        continue;
                    case 72:
                        this.fX = knVar.ml();
                        continue;
                    case 80:
                        int b2 = kw.m1176b(knVar, 80);
                        Object obj2 = new int[b2];
                        i = 0;
                        b = 0;
                        while (i < b2) {
                            if (i != 0) {
                                knVar.mh();
                            }
                            int mk = knVar.mk();
                            switch (mk) {
                                case Std.STD_FILE /*1*/:
                                case Std.STD_URL /*2*/:
                                case Std.STD_URI /*3*/:
                                case Std.STD_CLASS /*4*/:
                                case Std.STD_JAVA_TYPE /*5*/:
                                case Std.STD_CURRENCY /*6*/:
                                case Std.STD_PATTERN /*7*/:
                                case Std.STD_LOCALE /*8*/:
                                case Std.STD_CHARSET /*9*/:
                                case Std.STD_TIME_ZONE /*10*/:
                                case Std.STD_INET_ADDRESS /*11*/:
                                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                                case CommonStatusCodes.ERROR /*13*/:
                                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                                case C0065R.styleable.TwoWayView_android_paddingRight /*17*/:
                                    mh = b + 1;
                                    obj2[b] = mk;
                                    break;
                                default:
                                    mh = b;
                                    break;
                            }
                            i++;
                            b = mh;
                        }
                        if (b != 0) {
                            mh = this.fW == null ? 0 : this.fW.length;
                            if (mh != 0 || b != obj2.length) {
                                Object obj3 = new int[(mh + b)];
                                if (mh != 0) {
                                    System.arraycopy(this.fW, 0, obj3, 0, mh);
                                }
                                System.arraycopy(obj2, 0, obj3, mh, b);
                                this.fW = obj3;
                                break;
                            }
                            this.fW = obj2;
                            break;
                        }
                        continue;
                    case 82:
                        i = knVar.cR(knVar.mn());
                        b = knVar.getPosition();
                        mh = 0;
                        while (knVar.ms() > 0) {
                            switch (knVar.mk()) {
                                case Std.STD_FILE /*1*/:
                                case Std.STD_URL /*2*/:
                                case Std.STD_URI /*3*/:
                                case Std.STD_CLASS /*4*/:
                                case Std.STD_JAVA_TYPE /*5*/:
                                case Std.STD_CURRENCY /*6*/:
                                case Std.STD_PATTERN /*7*/:
                                case Std.STD_LOCALE /*8*/:
                                case Std.STD_CHARSET /*9*/:
                                case Std.STD_TIME_ZONE /*10*/:
                                case Std.STD_INET_ADDRESS /*11*/:
                                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                                case CommonStatusCodes.ERROR /*13*/:
                                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                                case C0065R.styleable.TwoWayView_android_paddingRight /*17*/:
                                    mh++;
                                    break;
                                default:
                                    break;
                            }
                        }
                        if (mh != 0) {
                            knVar.cT(b);
                            b = this.fW == null ? 0 : this.fW.length;
                            Object obj4 = new int[(mh + b)];
                            if (b != 0) {
                                System.arraycopy(this.fW, 0, obj4, 0, b);
                            }
                            while (knVar.ms() > 0) {
                                int mk2 = knVar.mk();
                                switch (mk2) {
                                    case Std.STD_FILE /*1*/:
                                    case Std.STD_URL /*2*/:
                                    case Std.STD_URI /*3*/:
                                    case Std.STD_CLASS /*4*/:
                                    case Std.STD_JAVA_TYPE /*5*/:
                                    case Std.STD_CURRENCY /*6*/:
                                    case Std.STD_PATTERN /*7*/:
                                    case Std.STD_LOCALE /*8*/:
                                    case Std.STD_CHARSET /*9*/:
                                    case Std.STD_TIME_ZONE /*10*/:
                                    case Std.STD_INET_ADDRESS /*11*/:
                                    case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                                    case CommonStatusCodes.ERROR /*13*/:
                                    case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                                    case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                                    case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                                    case C0065R.styleable.TwoWayView_android_paddingRight /*17*/:
                                        mh = b + 1;
                                        obj4[b] = mk2;
                                        b = mh;
                                        break;
                                    default:
                                        break;
                                }
                            }
                            this.fW = obj4;
                        }
                        knVar.cS(i);
                        continue;
                    case AdSize.LARGE_AD_HEIGHT /*90*/:
                        b = kw.m1176b(knVar, 90);
                        mh = this.fV == null ? 0 : this.fV.length;
                        obj = new C1367a[(b + mh)];
                        if (mh != 0) {
                            System.arraycopy(this.fV, 0, obj, 0, mh);
                        }
                        while (mh < obj.length - 1) {
                            obj[mh] = new C1367a();
                            knVar.m1126a(obj[mh]);
                            knVar.mh();
                            mh++;
                        }
                        obj[mh] = new C1367a();
                        knVar.m1126a(obj[mh]);
                        this.fV = obj;
                        continue;
                    case 96:
                        this.fU = knVar.ml();
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

        public C1367a m3001s() {
            this.type = 1;
            this.fN = UnsupportedUrlFragment.DISPLAY_NAME;
            this.fO = C1367a.m2996r();
            this.fP = C1367a.m2996r();
            this.fQ = C1367a.m2996r();
            this.fR = UnsupportedUrlFragment.DISPLAY_NAME;
            this.fS = UnsupportedUrlFragment.DISPLAY_NAME;
            this.fT = 0;
            this.fU = false;
            this.fV = C1367a.m2996r();
            this.fW = kw.aea;
            this.fX = false;
            this.adU = null;
            this.adY = -1;
            return this;
        }
    }
}
