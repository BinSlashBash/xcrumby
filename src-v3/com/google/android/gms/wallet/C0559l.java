package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;

/* renamed from: com.google.android.gms.wallet.l */
public class C0559l implements Creator<MaskedWalletRequest> {
    static void m1519a(MaskedWalletRequest maskedWalletRequest, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, maskedWalletRequest.getVersionCode());
        C0262b.m222a(parcel, 2, maskedWalletRequest.abi, false);
        C0262b.m225a(parcel, 3, maskedWalletRequest.abV);
        C0262b.m225a(parcel, 4, maskedWalletRequest.abW);
        C0262b.m225a(parcel, 5, maskedWalletRequest.abX);
        C0262b.m222a(parcel, 6, maskedWalletRequest.abY, false);
        C0262b.m222a(parcel, 7, maskedWalletRequest.abd, false);
        C0262b.m222a(parcel, 8, maskedWalletRequest.abZ, false);
        C0262b.m219a(parcel, 9, maskedWalletRequest.abr, i, false);
        C0262b.m225a(parcel, 10, maskedWalletRequest.aca);
        C0262b.m225a(parcel, 11, maskedWalletRequest.acb);
        C0262b.m228a(parcel, 12, maskedWalletRequest.acc, i, false);
        C0262b.m225a(parcel, 13, maskedWalletRequest.acd);
        C0262b.m225a(parcel, 14, maskedWalletRequest.ace);
        C0262b.m233b(parcel, 15, maskedWalletRequest.acf, false);
        C0262b.m211F(parcel, p);
    }

    public MaskedWalletRequest bh(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        Cart cart = null;
        boolean z4 = false;
        boolean z5 = false;
        CountrySpecification[] countrySpecificationArr = null;
        boolean z6 = true;
        boolean z7 = true;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    z2 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    z3 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    cart = (Cart) C0261a.m176a(parcel, n, Cart.CREATOR);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    z4 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    z5 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    countrySpecificationArr = (CountrySpecification[]) C0261a.m181b(parcel, n, CountrySpecification.CREATOR);
                    break;
                case CommonStatusCodes.ERROR /*13*/:
                    z6 = C0261a.m183c(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    z7 = C0261a.m183c(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                    arrayList = C0261a.m182c(parcel, n, CountrySpecification.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new MaskedWalletRequest(i, str, z, z2, z3, str2, str3, str4, cart, z4, z5, countrySpecificationArr, z6, z7, arrayList);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bh(x0);
    }

    public MaskedWalletRequest[] ct(int i) {
        return new MaskedWalletRequest[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ct(x0);
    }
}
