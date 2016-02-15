/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive.internal;

import com.google.android.gms.internal.kn;
import com.google.android.gms.internal.ko;
import com.google.android.gms.internal.kp;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;
import java.io.IOException;
import java.util.List;

public final class y
extends kp<y> {
    public String FC;
    public long FD;
    public long FE;
    public int versionCode;

    public y() {
        this.fH();
    }

    public static y g(byte[] arrby) throws ks {
        return kt.a(new y(), arrby);
    }

    @Override
    public void a(ko ko2) throws IOException {
        ko2.i(1, this.versionCode);
        ko2.b(2, this.FC);
        ko2.c(3, this.FD);
        ko2.c(4, this.FE);
        super.a(ko2);
    }

    @Override
    public /* synthetic */ kt b(kn kn2) throws IOException {
        return this.m(kn2);
    }

    @Override
    public int c() {
        int n2;
        this.adY = n2 = super.c() + ko.j(1, this.versionCode) + ko.g(2, this.FC) + ko.e(3, this.FD) + ko.e(4, this.FE);
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
        if (!(object instanceof y)) return bl3;
        object = (y)object;
        bl3 = bl2;
        if (this.versionCode != object.versionCode) return bl3;
        if (this.FC == null) {
            bl3 = bl2;
            if (object.FC != null) return bl3;
        } else if (!this.FC.equals(object.FC)) {
            return false;
        }
        bl3 = bl2;
        if (this.FD != object.FD) return bl3;
        bl3 = bl2;
        if (this.FE != object.FE) return bl3;
        if (this.adU != null && !this.adU.isEmpty()) {
            return this.adU.equals(object.adU);
        }
        if (object.adU == null) return true;
        bl3 = bl2;
        if (!object.adU.isEmpty()) return bl3;
        return true;
    }

    public y fH() {
        this.versionCode = 1;
        this.FC = "";
        this.FD = -1;
        this.FE = -1;
        this.adU = null;
        this.adY = -1;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n2 = 0;
        int n3 = this.versionCode;
        int n4 = this.FC == null ? 0 : this.FC.hashCode();
        int n5 = (int)(this.FD ^ this.FD >>> 32);
        int n6 = (int)(this.FE ^ this.FE >>> 32);
        int n7 = n2;
        if (this.adU == null) return (((n4 + (n3 + 527) * 31) * 31 + n5) * 31 + n6) * 31 + n7;
        if (this.adU.isEmpty()) {
            n7 = n2;
            return (((n4 + (n3 + 527) * 31) * 31 + n5) * 31 + n6) * 31 + n7;
        }
        n7 = this.adU.hashCode();
        return (((n4 + (n3 + 527) * 31) * 31 + n5) * 31 + n6) * 31 + n7;
    }

    public y m(kn kn2) throws IOException {
        block7 : do {
            int n2 = kn2.mh();
            switch (n2) {
                default: {
                    if (this.a(kn2, n2)) continue block7;
                }
                case 0: {
                    return this;
                }
                case 8: {
                    this.versionCode = kn2.mk();
                    continue block7;
                }
                case 18: {
                    this.FC = kn2.readString();
                    continue block7;
                }
                case 24: {
                    this.FD = kn2.mm();
                    continue block7;
                }
                case 32: 
            }
            this.FE = kn2.mm();
        } while (true);
    }
}

