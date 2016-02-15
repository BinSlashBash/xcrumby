package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.wallet.i */
public class C0556i implements Creator<LineItem> {
    static void m1516a(LineItem lineItem, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, lineItem.getVersionCode());
        C0262b.m222a(parcel, 2, lineItem.description, false);
        C0262b.m222a(parcel, 3, lineItem.abv, false);
        C0262b.m222a(parcel, 4, lineItem.abw, false);
        C0262b.m222a(parcel, 5, lineItem.abc, false);
        C0262b.m234c(parcel, 6, lineItem.abx);
        C0262b.m222a(parcel, 7, lineItem.abd, false);
        C0262b.m211F(parcel, p);
    }

    public LineItem be(Parcel parcel) {
        int i = 0;
        String str = null;
        int o = C0261a.m196o(parcel);
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str5 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new LineItem(i2, str5, str4, str3, str2, i, str);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public LineItem[] cq(int i) {
        return new LineItem[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return be(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cq(x0);
    }
}
