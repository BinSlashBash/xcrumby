/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.view.View
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.view.View;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class fc {
    private final View AV;
    private final a CT;

    public fc(String string2, Collection<String> collection, int n2, View view, String string3) {
        this.CT = new a(string2, collection, n2, string3);
        this.AV = view;
    }

    public String eC() {
        return this.CT.eC();
    }

    public int eD() {
        return this.CT.eD();
    }

    public List<String> eE() {
        return this.CT.eE();
    }

    public String[] eF() {
        return this.CT.eE().toArray(new String[0]);
    }

    public String eG() {
        return this.CT.eG();
    }

    public View eH() {
        return this.AV;
    }

    public String getAccountName() {
        return this.CT.getAccountName();
    }

    public static final class a
    implements SafeParcelable {
        public static final fp CREATOR = new fp();
        private final int AU;
        private final String AW;
        private final List<String> CU = new ArrayList<String>();
        private final String wG;
        private final int xH;

        a(int n2, String string2, List<String> list, int n3, String string3) {
            this.xH = n2;
            this.wG = string2;
            this.CU.addAll(list);
            this.AU = n3;
            this.AW = string3;
        }

        public a(String string2, Collection<String> collection, int n2, String string3) {
            this(3, string2, new ArrayList<String>(collection), n2, string3);
        }

        public int describeContents() {
            return 0;
        }

        public String eC() {
            if (this.wG != null) {
                return this.wG;
            }
            return "<<default account>>";
        }

        public int eD() {
            return this.AU;
        }

        public List<String> eE() {
            return new ArrayList<String>(this.CU);
        }

        public String eG() {
            return this.AW;
        }

        public String getAccountName() {
            return this.wG;
        }

        public int getVersionCode() {
            return this.xH;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            fp.a(this, parcel, n2);
        }
    }

}

