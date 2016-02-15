package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import java.util.List;

/* renamed from: com.google.android.gms.cast.b */
public class C0237b implements Creator<CastDevice> {
    static void m100a(CastDevice castDevice, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, castDevice.getVersionCode());
        C0262b.m222a(parcel, 2, castDevice.getDeviceId(), false);
        C0262b.m222a(parcel, 3, castDevice.yb, false);
        C0262b.m222a(parcel, 4, castDevice.getFriendlyName(), false);
        C0262b.m222a(parcel, 5, castDevice.getModelName(), false);
        C0262b.m222a(parcel, 6, castDevice.getDeviceVersion(), false);
        C0262b.m234c(parcel, 7, castDevice.getServicePort());
        C0262b.m233b(parcel, 8, castDevice.getIcons(), false);
        C0262b.m211F(parcel, p);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m101k(x0);
    }

    public CastDevice m101k(Parcel parcel) {
        int i = 0;
        List list = null;
        int o = C0261a.m196o(parcel);
        String str = null;
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
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    list = C0261a.m182c(parcel, n, WebImage.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new CastDevice(i2, str5, str4, str3, str2, str, i, list);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m102y(x0);
    }

    public CastDevice[] m102y(int i) {
        return new CastDevice[i];
    }
}
