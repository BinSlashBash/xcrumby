package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import java.util.List;

/* renamed from: com.google.android.gms.cast.a */
public class C0236a implements Creator<ApplicationMetadata> {
    static void m97a(ApplicationMetadata applicationMetadata, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, applicationMetadata.getVersionCode());
        C0262b.m222a(parcel, 2, applicationMetadata.getApplicationId(), false);
        C0262b.m222a(parcel, 3, applicationMetadata.getName(), false);
        C0262b.m233b(parcel, 4, applicationMetadata.getImages(), false);
        C0262b.m223a(parcel, 5, applicationMetadata.xK, false);
        C0262b.m222a(parcel, 6, applicationMetadata.getSenderAppIdentifier(), false);
        C0262b.m219a(parcel, 7, applicationMetadata.dz(), i, false);
        C0262b.m211F(parcel, p);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m98j(x0);
    }

    public ApplicationMetadata m98j(Parcel parcel) {
        Uri uri = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        List list = null;
        List list2 = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    list2 = C0261a.m182c(parcel, n, WebImage.CREATOR);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    list = C0261a.m171A(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    uri = (Uri) C0261a.m176a(parcel, n, Uri.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new ApplicationMetadata(i, str3, str2, list2, list, str, uri);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m99w(x0);
    }

    public ApplicationMetadata[] m99w(int i) {
        return new ApplicationMetadata[i];
    }
}
