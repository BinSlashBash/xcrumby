/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ic;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class id
implements Parcelable.Creator<ic> {
    static void a(ic ic2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        Set<Integer> set = ic2.ja();
        if (set.contains(1)) {
            b.c(parcel, 1, ic2.getVersionCode());
        }
        if (set.contains(2)) {
            b.a(parcel, 2, ic2.jb(), n2, true);
        }
        if (set.contains(3)) {
            b.a(parcel, 3, ic2.getAdditionalName(), true);
        }
        if (set.contains(4)) {
            b.a(parcel, 4, ic2.jc(), n2, true);
        }
        if (set.contains(5)) {
            b.a(parcel, 5, ic2.getAddressCountry(), true);
        }
        if (set.contains(6)) {
            b.a(parcel, 6, ic2.getAddressLocality(), true);
        }
        if (set.contains(7)) {
            b.a(parcel, 7, ic2.getAddressRegion(), true);
        }
        if (set.contains(8)) {
            b.b(parcel, 8, ic2.jd(), true);
        }
        if (set.contains(9)) {
            b.c(parcel, 9, ic2.getAttendeeCount());
        }
        if (set.contains(10)) {
            b.b(parcel, 10, ic2.je(), true);
        }
        if (set.contains(11)) {
            b.a(parcel, 11, ic2.jf(), n2, true);
        }
        if (set.contains(12)) {
            b.b(parcel, 12, ic2.jg(), true);
        }
        if (set.contains(13)) {
            b.a(parcel, 13, ic2.getBestRating(), true);
        }
        if (set.contains(14)) {
            b.a(parcel, 14, ic2.getBirthDate(), true);
        }
        if (set.contains(15)) {
            b.a(parcel, 15, ic2.jh(), n2, true);
        }
        if (set.contains(17)) {
            b.a(parcel, 17, ic2.getContentSize(), true);
        }
        if (set.contains(16)) {
            b.a(parcel, 16, ic2.getCaption(), true);
        }
        if (set.contains(19)) {
            b.b(parcel, 19, ic2.ji(), true);
        }
        if (set.contains(18)) {
            b.a(parcel, 18, ic2.getContentUrl(), true);
        }
        if (set.contains(21)) {
            b.a(parcel, 21, ic2.getDateModified(), true);
        }
        if (set.contains(20)) {
            b.a(parcel, 20, ic2.getDateCreated(), true);
        }
        if (set.contains(23)) {
            b.a(parcel, 23, ic2.getDescription(), true);
        }
        if (set.contains(22)) {
            b.a(parcel, 22, ic2.getDatePublished(), true);
        }
        if (set.contains(25)) {
            b.a(parcel, 25, ic2.getEmbedUrl(), true);
        }
        if (set.contains(24)) {
            b.a(parcel, 24, ic2.getDuration(), true);
        }
        if (set.contains(27)) {
            b.a(parcel, 27, ic2.getFamilyName(), true);
        }
        if (set.contains(26)) {
            b.a(parcel, 26, ic2.getEndDate(), true);
        }
        if (set.contains(29)) {
            b.a(parcel, 29, ic2.jj(), n2, true);
        }
        if (set.contains(28)) {
            b.a(parcel, 28, ic2.getGender(), true);
        }
        if (set.contains(31)) {
            b.a(parcel, 31, ic2.getHeight(), true);
        }
        if (set.contains(30)) {
            b.a(parcel, 30, ic2.getGivenName(), true);
        }
        if (set.contains(34)) {
            b.a(parcel, 34, ic2.jk(), n2, true);
        }
        if (set.contains(32)) {
            b.a(parcel, 32, ic2.getId(), true);
        }
        if (set.contains(33)) {
            b.a(parcel, 33, ic2.getImage(), true);
        }
        if (set.contains(38)) {
            b.a(parcel, 38, ic2.getLongitude());
        }
        if (set.contains(39)) {
            b.a(parcel, 39, ic2.getName(), true);
        }
        if (set.contains(36)) {
            b.a(parcel, 36, ic2.getLatitude());
        }
        if (set.contains(37)) {
            b.a(parcel, 37, ic2.jl(), n2, true);
        }
        if (set.contains(42)) {
            b.a(parcel, 42, ic2.getPlayerType(), true);
        }
        if (set.contains(43)) {
            b.a(parcel, 43, ic2.getPostOfficeBoxNumber(), true);
        }
        if (set.contains(40)) {
            b.a(parcel, 40, ic2.jm(), n2, true);
        }
        if (set.contains(41)) {
            b.b(parcel, 41, ic2.jn(), true);
        }
        if (set.contains(46)) {
            b.a(parcel, 46, ic2.jo(), n2, true);
        }
        if (set.contains(47)) {
            b.a(parcel, 47, ic2.getStartDate(), true);
        }
        if (set.contains(44)) {
            b.a(parcel, 44, ic2.getPostalCode(), true);
        }
        if (set.contains(45)) {
            b.a(parcel, 45, ic2.getRatingValue(), true);
        }
        if (set.contains(51)) {
            b.a(parcel, 51, ic2.getThumbnailUrl(), true);
        }
        if (set.contains(50)) {
            b.a(parcel, 50, ic2.jp(), n2, true);
        }
        if (set.contains(49)) {
            b.a(parcel, 49, ic2.getText(), true);
        }
        if (set.contains(48)) {
            b.a(parcel, 48, ic2.getStreetAddress(), true);
        }
        if (set.contains(55)) {
            b.a(parcel, 55, ic2.getWidth(), true);
        }
        if (set.contains(54)) {
            b.a(parcel, 54, ic2.getUrl(), true);
        }
        if (set.contains(53)) {
            b.a(parcel, 53, ic2.getType(), true);
        }
        if (set.contains(52)) {
            b.a(parcel, 52, ic2.getTickerSymbol(), true);
        }
        if (set.contains(56)) {
            b.a(parcel, 56, ic2.getWorstRating(), true);
        }
        b.F(parcel, n3);
    }

    public ic aL(Parcel parcel) {
        int n2 = a.o(parcel);
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n3 = 0;
        ic ic2 = null;
        ArrayList<String> arrayList = null;
        ic ic3 = null;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        ArrayList<ic> arrayList2 = null;
        int n4 = 0;
        ArrayList<ic> arrayList3 = null;
        ic ic4 = null;
        ArrayList<ic> arrayList4 = null;
        String string5 = null;
        String string6 = null;
        ic ic5 = null;
        String string7 = null;
        String string8 = null;
        String string9 = null;
        ArrayList<ic> arrayList5 = null;
        String string10 = null;
        String string11 = null;
        String string12 = null;
        String string13 = null;
        String string14 = null;
        String string15 = null;
        String string16 = null;
        String string17 = null;
        String string18 = null;
        ic ic6 = null;
        String string19 = null;
        String string20 = null;
        String string21 = null;
        String string22 = null;
        ic ic7 = null;
        double d2 = 0.0;
        ic ic8 = null;
        double d3 = 0.0;
        String string23 = null;
        ic ic9 = null;
        ArrayList<ic> arrayList6 = null;
        String string24 = null;
        String string25 = null;
        String string26 = null;
        String string27 = null;
        ic ic10 = null;
        String string28 = null;
        String string29 = null;
        String string30 = null;
        ic ic11 = null;
        String string31 = null;
        String string32 = null;
        String string33 = null;
        String string34 = null;
        String string35 = null;
        String string36 = null;
        block57 : while (parcel.dataPosition() < n2) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block57;
                }
                case 1: {
                    n3 = a.g(parcel, n5);
                    hashSet.add(1);
                    continue block57;
                }
                case 2: {
                    ic2 = (ic)a.a(parcel, n5, ic.CREATOR);
                    hashSet.add(2);
                    continue block57;
                }
                case 3: {
                    arrayList = a.A(parcel, n5);
                    hashSet.add(3);
                    continue block57;
                }
                case 4: {
                    ic3 = (ic)a.a(parcel, n5, ic.CREATOR);
                    hashSet.add(4);
                    continue block57;
                }
                case 5: {
                    string2 = a.n(parcel, n5);
                    hashSet.add(5);
                    continue block57;
                }
                case 6: {
                    string3 = a.n(parcel, n5);
                    hashSet.add(6);
                    continue block57;
                }
                case 7: {
                    string4 = a.n(parcel, n5);
                    hashSet.add(7);
                    continue block57;
                }
                case 8: {
                    arrayList2 = a.c(parcel, n5, ic.CREATOR);
                    hashSet.add(8);
                    continue block57;
                }
                case 9: {
                    n4 = a.g(parcel, n5);
                    hashSet.add(9);
                    continue block57;
                }
                case 10: {
                    arrayList3 = a.c(parcel, n5, ic.CREATOR);
                    hashSet.add(10);
                    continue block57;
                }
                case 11: {
                    ic4 = (ic)a.a(parcel, n5, ic.CREATOR);
                    hashSet.add(11);
                    continue block57;
                }
                case 12: {
                    arrayList4 = a.c(parcel, n5, ic.CREATOR);
                    hashSet.add(12);
                    continue block57;
                }
                case 13: {
                    string5 = a.n(parcel, n5);
                    hashSet.add(13);
                    continue block57;
                }
                case 14: {
                    string6 = a.n(parcel, n5);
                    hashSet.add(14);
                    continue block57;
                }
                case 15: {
                    ic5 = (ic)a.a(parcel, n5, ic.CREATOR);
                    hashSet.add(15);
                    continue block57;
                }
                case 17: {
                    string8 = a.n(parcel, n5);
                    hashSet.add(17);
                    continue block57;
                }
                case 16: {
                    string7 = a.n(parcel, n5);
                    hashSet.add(16);
                    continue block57;
                }
                case 19: {
                    arrayList5 = a.c(parcel, n5, ic.CREATOR);
                    hashSet.add(19);
                    continue block57;
                }
                case 18: {
                    string9 = a.n(parcel, n5);
                    hashSet.add(18);
                    continue block57;
                }
                case 21: {
                    string11 = a.n(parcel, n5);
                    hashSet.add(21);
                    continue block57;
                }
                case 20: {
                    string10 = a.n(parcel, n5);
                    hashSet.add(20);
                    continue block57;
                }
                case 23: {
                    string13 = a.n(parcel, n5);
                    hashSet.add(23);
                    continue block57;
                }
                case 22: {
                    string12 = a.n(parcel, n5);
                    hashSet.add(22);
                    continue block57;
                }
                case 25: {
                    string15 = a.n(parcel, n5);
                    hashSet.add(25);
                    continue block57;
                }
                case 24: {
                    string14 = a.n(parcel, n5);
                    hashSet.add(24);
                    continue block57;
                }
                case 27: {
                    string17 = a.n(parcel, n5);
                    hashSet.add(27);
                    continue block57;
                }
                case 26: {
                    string16 = a.n(parcel, n5);
                    hashSet.add(26);
                    continue block57;
                }
                case 29: {
                    ic6 = (ic)a.a(parcel, n5, ic.CREATOR);
                    hashSet.add(29);
                    continue block57;
                }
                case 28: {
                    string18 = a.n(parcel, n5);
                    hashSet.add(28);
                    continue block57;
                }
                case 31: {
                    string20 = a.n(parcel, n5);
                    hashSet.add(31);
                    continue block57;
                }
                case 30: {
                    string19 = a.n(parcel, n5);
                    hashSet.add(30);
                    continue block57;
                }
                case 34: {
                    ic7 = (ic)a.a(parcel, n5, ic.CREATOR);
                    hashSet.add(34);
                    continue block57;
                }
                case 32: {
                    string21 = a.n(parcel, n5);
                    hashSet.add(32);
                    continue block57;
                }
                case 33: {
                    string22 = a.n(parcel, n5);
                    hashSet.add(33);
                    continue block57;
                }
                case 38: {
                    d3 = a.l(parcel, n5);
                    hashSet.add(38);
                    continue block57;
                }
                case 39: {
                    string23 = a.n(parcel, n5);
                    hashSet.add(39);
                    continue block57;
                }
                case 36: {
                    d2 = a.l(parcel, n5);
                    hashSet.add(36);
                    continue block57;
                }
                case 37: {
                    ic8 = (ic)a.a(parcel, n5, ic.CREATOR);
                    hashSet.add(37);
                    continue block57;
                }
                case 42: {
                    string24 = a.n(parcel, n5);
                    hashSet.add(42);
                    continue block57;
                }
                case 43: {
                    string25 = a.n(parcel, n5);
                    hashSet.add(43);
                    continue block57;
                }
                case 40: {
                    ic9 = (ic)a.a(parcel, n5, ic.CREATOR);
                    hashSet.add(40);
                    continue block57;
                }
                case 41: {
                    arrayList6 = a.c(parcel, n5, ic.CREATOR);
                    hashSet.add(41);
                    continue block57;
                }
                case 46: {
                    ic10 = (ic)a.a(parcel, n5, ic.CREATOR);
                    hashSet.add(46);
                    continue block57;
                }
                case 47: {
                    string28 = a.n(parcel, n5);
                    hashSet.add(47);
                    continue block57;
                }
                case 44: {
                    string26 = a.n(parcel, n5);
                    hashSet.add(44);
                    continue block57;
                }
                case 45: {
                    string27 = a.n(parcel, n5);
                    hashSet.add(45);
                    continue block57;
                }
                case 51: {
                    string31 = a.n(parcel, n5);
                    hashSet.add(51);
                    continue block57;
                }
                case 50: {
                    ic11 = (ic)a.a(parcel, n5, ic.CREATOR);
                    hashSet.add(50);
                    continue block57;
                }
                case 49: {
                    string30 = a.n(parcel, n5);
                    hashSet.add(49);
                    continue block57;
                }
                case 48: {
                    string29 = a.n(parcel, n5);
                    hashSet.add(48);
                    continue block57;
                }
                case 55: {
                    string35 = a.n(parcel, n5);
                    hashSet.add(55);
                    continue block57;
                }
                case 54: {
                    string34 = a.n(parcel, n5);
                    hashSet.add(54);
                    continue block57;
                }
                case 53: {
                    string33 = a.n(parcel, n5);
                    hashSet.add(53);
                    continue block57;
                }
                case 52: {
                    string32 = a.n(parcel, n5);
                    hashSet.add(52);
                    continue block57;
                }
                case 56: 
            }
            string36 = a.n(parcel, n5);
            hashSet.add(56);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ic(hashSet, n3, ic2, arrayList, ic3, string2, string3, string4, arrayList2, n4, arrayList3, ic4, arrayList4, string5, string6, ic5, string7, string8, string9, arrayList5, string10, string11, string12, string13, string14, string15, string16, string17, string18, ic6, string19, string20, string21, string22, ic7, d2, ic8, d3, string23, ic9, arrayList6, string24, string25, string26, string27, ic10, string28, string29, string30, ic11, string31, string32, string33, string34, string35, string36);
    }

    public ic[] bO(int n2) {
        return new ic[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aL(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bO(n2);
    }
}

