package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.common.images.b */
public class C0259b implements Creator<WebImage> {
    static void m168a(WebImage webImage, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, webImage.getVersionCode());
        C0262b.m219a(parcel, 2, webImage.getUrl(), i, false);
        C0262b.m234c(parcel, 3, webImage.getWidth());
        C0262b.m234c(parcel, 4, webImage.getHeight());
        C0262b.m211F(parcel, p);
    }

    public WebImage[] m169K(int i) {
        return new WebImage[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m170l(x0);
    }

    public WebImage m170l(Parcel parcel) {
        int i = 0;
        int o = C0261a.m196o(parcel);
        Uri uri = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < o) {
            Uri uri2;
            int g;
            int n = C0261a.m194n(parcel);
            int i4;
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i4 = i;
                    i = i2;
                    uri2 = uri;
                    g = C0261a.m187g(parcel, n);
                    n = i4;
                    break;
                case Std.STD_URL /*2*/:
                    g = i3;
                    i4 = i2;
                    uri2 = (Uri) C0261a.m176a(parcel, n, Uri.CREATOR);
                    n = i;
                    i = i4;
                    break;
                case Std.STD_URI /*3*/:
                    uri2 = uri;
                    g = i3;
                    i4 = i;
                    i = C0261a.m187g(parcel, n);
                    n = i4;
                    break;
                case Std.STD_CLASS /*4*/:
                    n = C0261a.m187g(parcel, n);
                    i = i2;
                    uri2 = uri;
                    g = i3;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    n = i;
                    i = i2;
                    uri2 = uri;
                    g = i3;
                    break;
            }
            i3 = g;
            uri = uri2;
            i2 = i;
            i = n;
        }
        if (parcel.dataPosition() == o) {
            return new WebImage(i3, uri, i2, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m169K(x0);
    }
}
