package com.google.android.gms.drive.internal;

import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.internal.kn;
import com.google.android.gms.internal.ko;
import com.google.android.gms.internal.kp;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;
import java.io.IOException;
import org.json.zip.JSONzip;

/* renamed from: com.google.android.gms.drive.internal.y */
public final class C1315y extends kp<C1315y> {
    public String FC;
    public long FD;
    public long FE;
    public int versionCode;

    public C1315y() {
        fH();
    }

    public static C1315y m2671g(byte[] bArr) throws ks {
        return (C1315y) kt.m1166a(new C1315y(), bArr);
    }

    public void m2672a(ko koVar) throws IOException {
        koVar.m1157i(1, this.versionCode);
        koVar.m1152b(2, this.FC);
        koVar.m1154c(3, this.FD);
        koVar.m1154c(4, this.FE);
        super.m2324a(koVar);
    }

    public /* synthetic */ kt m2673b(kn knVar) throws IOException {
        return m2675m(knVar);
    }

    public int m2674c() {
        int c = (((super.m1172c() + ko.m1143j(1, this.versionCode)) + ko.m1142g(2, this.FC)) + ko.m1141e(3, this.FD)) + ko.m1141e(4, this.FE);
        this.adY = c;
        return c;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof C1315y)) {
            return false;
        }
        C1315y c1315y = (C1315y) o;
        if (this.versionCode != c1315y.versionCode) {
            return false;
        }
        if (this.FC == null) {
            if (c1315y.FC != null) {
                return false;
            }
        } else if (!this.FC.equals(c1315y.FC)) {
            return false;
        }
        if (this.FD != c1315y.FD || this.FE != c1315y.FE) {
            return false;
        }
        if (this.adU == null || this.adU.isEmpty()) {
            return c1315y.adU == null || c1315y.adU.isEmpty();
        } else {
            return this.adU.equals(c1315y.adU);
        }
    }

    public C1315y fH() {
        this.versionCode = 1;
        this.FC = UnsupportedUrlFragment.DISPLAY_NAME;
        this.FD = -1;
        this.FE = -1;
        this.adU = null;
        this.adY = -1;
        return this;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((this.FC == null ? 0 : this.FC.hashCode()) + ((this.versionCode + 527) * 31)) * 31) + ((int) (this.FD ^ (this.FD >>> 32)))) * 31) + ((int) (this.FE ^ (this.FE >>> 32)))) * 31;
        if (!(this.adU == null || this.adU.isEmpty())) {
            i = this.adU.hashCode();
        }
        return hashCode + i;
    }

    public C1315y m2675m(kn knVar) throws IOException {
        while (true) {
            int mh = knVar.mh();
            switch (mh) {
                case JSONzip.zipEmptyObject /*0*/:
                    break;
                case Std.STD_LOCALE /*8*/:
                    this.versionCode = knVar.mk();
                    continue;
                case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                    this.FC = knVar.readString();
                    continue;
                case C0065R.styleable.TwoWayView_android_fadingEdge /*24*/:
                    this.FD = knVar.mm();
                    continue;
                case TransportMediator.FLAG_KEY_MEDIA_STOP /*32*/:
                    this.FE = knVar.mm();
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
