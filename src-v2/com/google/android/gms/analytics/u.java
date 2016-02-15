/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.analytics;

import java.util.SortedSet;
import java.util.TreeSet;

class u {
    private static final u tH = new u();
    private SortedSet<a> tE = new TreeSet<a>();
    private StringBuilder tF = new StringBuilder();
    private boolean tG = false;

    private u() {
    }

    public static u cy() {
        return tH;
    }

    public void a(a a2) {
        synchronized (this) {
            if (!this.tG) {
                this.tE.add(a2);
                this.tF.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".charAt(a2.ordinal()));
            }
            return;
        }
    }

    public String cA() {
        synchronized (this) {
            if (this.tF.length() > 0) {
                this.tF.insert(0, ".");
            }
            String string2 = this.tF.toString();
            this.tF = new StringBuilder();
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String cz() {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            int n2 = 6;
            int n3 = 0;
            while (this.tE.size() > 0) {
                a a2 = this.tE.first();
                this.tE.remove((Object)a2);
                int n4 = a2.ordinal();
                while (n4 >= n2) {
                    stringBuilder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".charAt(n3));
                    n2 += 6;
                    n3 = 0;
                }
                n3 += 1 << a2.ordinal() % 6;
            }
            if (n3 > 0 || stringBuilder.length() == 0) {
                stringBuilder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".charAt(n3));
            }
            this.tE.clear();
            return stringBuilder.toString();
        }
    }

    public void t(boolean bl2) {
        synchronized (this) {
            this.tG = bl2;
            return;
        }
    }

    public static enum a {
        tI,
        tJ,
        tK,
        tL,
        tM,
        tN,
        tO,
        tP,
        tQ,
        tR,
        tS,
        tT,
        tU,
        tV,
        tW,
        tX,
        tY,
        tZ,
        ua,
        ub,
        uc,
        ud,
        ue,
        uf,
        ug,
        uh,
        ui,
        uj,
        uk,
        ul,
        um,
        un,
        uo,
        up,
        uq,
        ur,
        us,
        ut,
        uu,
        uv,
        uw,
        ux,
        uy,
        uz,
        uA,
        uB,
        uC,
        uD,
        uE,
        uF,
        uG,
        uH,
        uI,
        uJ,
        uK,
        uL,
        uM,
        uN,
        uO,
        uP,
        uQ,
        uR,
        uS;
        

        private a() {
        }
    }

}

