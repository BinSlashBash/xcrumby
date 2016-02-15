/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class b {
    private static int D(Parcel parcel, int n2) {
        parcel.writeInt(-65536 | n2);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void E(Parcel parcel, int n2) {
        int n3 = parcel.dataPosition();
        parcel.setDataPosition(n2 - 4);
        parcel.writeInt(n3 - n2);
        parcel.setDataPosition(n3);
    }

    public static void F(Parcel parcel, int n2) {
        b.E(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, byte by2) {
        b.b(parcel, n2, 4);
        parcel.writeInt((int)by2);
    }

    public static void a(Parcel parcel, int n2, double d2) {
        b.b(parcel, n2, 8);
        parcel.writeDouble(d2);
    }

    public static void a(Parcel parcel, int n2, float f2) {
        b.b(parcel, n2, 4);
        parcel.writeFloat(f2);
    }

    public static void a(Parcel parcel, int n2, long l2) {
        b.b(parcel, n2, 8);
        parcel.writeLong(l2);
    }

    public static void a(Parcel parcel, int n2, Bundle bundle, boolean bl2) {
        if (bundle == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        n2 = b.D(parcel, n2);
        parcel.writeBundle(bundle);
        b.E(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, IBinder iBinder, boolean bl2) {
        if (iBinder == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        n2 = b.D(parcel, n2);
        parcel.writeStrongBinder(iBinder);
        b.E(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, Parcel parcel2, boolean bl2) {
        if (parcel2 == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        n2 = b.D(parcel, n2);
        parcel.appendFrom(parcel2, 0, parcel2.dataSize());
        b.E(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, Parcelable parcelable, int n3, boolean bl2) {
        if (parcelable == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        n2 = b.D(parcel, n2);
        parcelable.writeToParcel(parcel, n3);
        b.E(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, Boolean bl2, boolean bl3) {
        int n3 = 0;
        if (bl2 == null) {
            if (bl3) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        b.b(parcel, n2, 4);
        n2 = n3;
        if (bl2.booleanValue()) {
            n2 = 1;
        }
        parcel.writeInt(n2);
    }

    public static void a(Parcel parcel, int n2, Integer n3, boolean bl2) {
        if (n3 == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        b.b(parcel, n2, 4);
        parcel.writeInt(n3.intValue());
    }

    public static void a(Parcel parcel, int n2, String string2, boolean bl2) {
        if (string2 == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        n2 = b.D(parcel, n2);
        parcel.writeString(string2);
        b.E(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, List<String> list, boolean bl2) {
        if (list == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        n2 = b.D(parcel, n2);
        parcel.writeStringList(list);
        b.E(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, short s2) {
        b.b(parcel, n2, 4);
        parcel.writeInt((int)s2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void a(Parcel parcel, int n2, boolean bl2) {
        b.b(parcel, n2, 4);
        n2 = bl2 ? 1 : 0;
        parcel.writeInt(n2);
    }

    public static void a(Parcel parcel, int n2, byte[] arrby, boolean bl2) {
        if (arrby == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        n2 = b.D(parcel, n2);
        parcel.writeByteArray(arrby);
        b.E(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, int[] arrn, boolean bl2) {
        if (arrn == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        n2 = b.D(parcel, n2);
        parcel.writeIntArray(arrn);
        b.E(parcel, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T extends Parcelable> void a(Parcel parcel, int n2, T[] arrT, int n3, boolean bl2) {
        if (arrT == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        int n4 = b.D(parcel, n2);
        int n5 = arrT.length;
        parcel.writeInt(n5);
        n2 = 0;
        do {
            if (n2 >= n5) {
                b.E(parcel, n4);
                return;
            }
            T t2 = arrT[n2];
            if (t2 == null) {
                parcel.writeInt(0);
            } else {
                b.a(parcel, t2, n3);
            }
            ++n2;
        } while (true);
    }

    public static void a(Parcel parcel, int n2, String[] arrstring, boolean bl2) {
        if (arrstring == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        n2 = b.D(parcel, n2);
        parcel.writeStringArray(arrstring);
        b.E(parcel, n2);
    }

    public static void a(Parcel parcel, int n2, byte[][] arrby, boolean bl2) {
        int n3 = 0;
        if (arrby == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        int n4 = b.D(parcel, n2);
        int n5 = arrby.length;
        parcel.writeInt(n5);
        for (n2 = n3; n2 < n5; ++n2) {
            parcel.writeByteArray(arrby[n2]);
        }
        b.E(parcel, n4);
    }

    private static <T extends Parcelable> void a(Parcel parcel, T t2, int n2) {
        int n3 = parcel.dataPosition();
        parcel.writeInt(1);
        int n4 = parcel.dataPosition();
        t2.writeToParcel(parcel, n2);
        n2 = parcel.dataPosition();
        parcel.setDataPosition(n3);
        parcel.writeInt(n2 - n4);
        parcel.setDataPosition(n2);
    }

    private static void b(Parcel parcel, int n2, int n3) {
        if (n3 >= 65535) {
            parcel.writeInt(-65536 | n2);
            parcel.writeInt(n3);
            return;
        }
        parcel.writeInt(n3 << 16 | n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T extends Parcelable> void b(Parcel parcel, int n2, List<T> list, boolean bl2) {
        if (list == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        int n3 = b.D(parcel, n2);
        int n4 = list.size();
        parcel.writeInt(n4);
        n2 = 0;
        do {
            if (n2 >= n4) {
                b.E(parcel, n3);
                return;
            }
            Parcelable parcelable = (Parcelable)list.get(n2);
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                b.a(parcel, parcelable, 0);
            }
            ++n2;
        } while (true);
    }

    public static void c(Parcel parcel, int n2, int n3) {
        b.b(parcel, n2, 4);
        parcel.writeInt(n3);
    }

    public static void c(Parcel parcel, int n2, List list, boolean bl2) {
        if (list == null) {
            if (bl2) {
                b.b(parcel, n2, 0);
            }
            return;
        }
        n2 = b.D(parcel, n2);
        parcel.writeList(list);
        b.E(parcel, n2);
    }

    public static int p(Parcel parcel) {
        return b.D(parcel, 20293);
    }
}

