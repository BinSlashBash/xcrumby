/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class a {
    public static ArrayList<String> A(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        ArrayList arrayList = parcel.createStringArrayList();
        parcel.setDataPosition(n2 + n3);
        return arrayList;
    }

    public static Parcel B(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        Parcel parcel2 = Parcel.obtain();
        parcel2.appendFrom(parcel, n3, n2);
        parcel.setDataPosition(n2 + n3);
        return parcel2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Parcel[] C(Parcel parcel, int n2) {
        int n3 = a.a(parcel, n2);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        int n5 = parcel.readInt();
        Parcel[] arrparcel = new Parcel[n5];
        n2 = 0;
        do {
            if (n2 >= n5) {
                parcel.setDataPosition(n4 + n3);
                return arrparcel;
            }
            int n6 = parcel.readInt();
            if (n6 != 0) {
                int n7 = parcel.dataPosition();
                Parcel parcel2 = Parcel.obtain();
                parcel2.appendFrom(parcel, n7, n6);
                arrparcel[n2] = parcel2;
                parcel.setDataPosition(n6 + n7);
            } else {
                arrparcel[n2] = null;
            }
            ++n2;
        } while (true);
    }

    public static int R(int n2) {
        return 65535 & n2;
    }

    public static int a(Parcel parcel, int n2) {
        if ((n2 & -65536) != -65536) {
            return n2 >> 16 & 65535;
        }
        return parcel.readInt();
    }

    public static <T extends Parcelable> T a(Parcel parcel, int n2, Parcelable.Creator<T> parcelable) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        parcelable = (Parcelable)parcelable.createFromParcel(parcel);
        parcel.setDataPosition(n2 + n3);
        return (T)parcelable;
    }

    private static void a(Parcel parcel, int n2, int n3) {
        if ((n2 = a.a(parcel, n2)) != n3) {
            throw new a("Expected size " + n3 + " got " + n2 + " (0x" + Integer.toHexString(n2) + ")", parcel);
        }
    }

    private static void a(Parcel parcel, int n2, int n3, int n4) {
        if (n3 != n4) {
            throw new a("Expected size " + n4 + " got " + n3 + " (0x" + Integer.toHexString(n3) + ")", parcel);
        }
    }

    public static void a(Parcel parcel, int n2, List list, ClassLoader classLoader) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return;
        }
        parcel.readList(list, classLoader);
        parcel.setDataPosition(n2 + n3);
    }

    public static void b(Parcel parcel, int n2) {
        parcel.setDataPosition(a.a(parcel, n2) + parcel.dataPosition());
    }

    public static <T> T[] b(Parcel parcel, int n2, Parcelable.Creator<T> arrobject) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        arrobject = parcel.createTypedArray(arrobject);
        parcel.setDataPosition(n2 + n3);
        return arrobject;
    }

    public static <T> ArrayList<T> c(Parcel parcel, int n2, Parcelable.Creator<T> object) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        object = parcel.createTypedArrayList(object);
        parcel.setDataPosition(n2 + n3);
        return object;
    }

    public static boolean c(Parcel parcel, int n2) {
        a.a(parcel, n2, 4);
        if (parcel.readInt() != 0) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Boolean d(Parcel parcel, int n2) {
        boolean bl2;
        int n3 = a.a(parcel, n2);
        if (n3 == 0) {
            return null;
        }
        a.a(parcel, n2, n3, 4);
        if (parcel.readInt() != 0) {
            bl2 = true;
            do {
                return bl2;
                break;
            } while (true);
        }
        bl2 = false;
        return bl2;
    }

    public static byte e(Parcel parcel, int n2) {
        a.a(parcel, n2, 4);
        return (byte)parcel.readInt();
    }

    public static short f(Parcel parcel, int n2) {
        a.a(parcel, n2, 4);
        return (short)parcel.readInt();
    }

    public static int g(Parcel parcel, int n2) {
        a.a(parcel, n2, 4);
        return parcel.readInt();
    }

    public static Integer h(Parcel parcel, int n2) {
        int n3 = a.a(parcel, n2);
        if (n3 == 0) {
            return null;
        }
        a.a(parcel, n2, n3, 4);
        return parcel.readInt();
    }

    public static long i(Parcel parcel, int n2) {
        a.a(parcel, n2, 8);
        return parcel.readLong();
    }

    public static BigInteger j(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        byte[] arrby = parcel.createByteArray();
        parcel.setDataPosition(n2 + n3);
        return new BigInteger(arrby);
    }

    public static float k(Parcel parcel, int n2) {
        a.a(parcel, n2, 4);
        return parcel.readFloat();
    }

    public static double l(Parcel parcel, int n2) {
        a.a(parcel, n2, 8);
        return parcel.readDouble();
    }

    public static BigDecimal m(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        byte[] arrby = parcel.createByteArray();
        int n4 = parcel.readInt();
        parcel.setDataPosition(n2 + n3);
        return new BigDecimal(new BigInteger(arrby), n4);
    }

    public static int n(Parcel parcel) {
        return parcel.readInt();
    }

    public static String n(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        String string2 = parcel.readString();
        parcel.setDataPosition(n2 + n3);
        return string2;
    }

    public static int o(Parcel parcel) {
        int n2 = a.n(parcel);
        int n3 = a.a(parcel, n2);
        int n4 = parcel.dataPosition();
        if (a.R(n2) != 20293) {
            throw new a("Expected object header. Got 0x" + Integer.toHexString(n2), parcel);
        }
        n2 = n4 + n3;
        if (n2 < n4 || n2 > parcel.dataSize()) {
            throw new a("Size read is invalid start=" + n4 + " end=" + n2, parcel);
        }
        return n2;
    }

    public static IBinder o(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        IBinder iBinder = parcel.readStrongBinder();
        parcel.setDataPosition(n2 + n3);
        return iBinder;
    }

    public static Bundle p(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        Bundle bundle = parcel.readBundle();
        parcel.setDataPosition(n2 + n3);
        return bundle;
    }

    public static byte[] q(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        byte[] arrby = parcel.createByteArray();
        parcel.setDataPosition(n2 + n3);
        return arrby;
    }

    public static byte[][] r(Parcel parcel, int n2) {
        int n3 = a.a(parcel, n2);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        int n5 = parcel.readInt();
        byte[][] arrarrby = new byte[n5][];
        for (n2 = 0; n2 < n5; ++n2) {
            arrarrby[n2] = parcel.createByteArray();
        }
        parcel.setDataPosition(n4 + n3);
        return arrarrby;
    }

    public static boolean[] s(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        boolean[] arrbl = parcel.createBooleanArray();
        parcel.setDataPosition(n2 + n3);
        return arrbl;
    }

    public static int[] t(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        int[] arrn = parcel.createIntArray();
        parcel.setDataPosition(n2 + n3);
        return arrn;
    }

    public static long[] u(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        long[] arrl = parcel.createLongArray();
        parcel.setDataPosition(n2 + n3);
        return arrl;
    }

    public static BigInteger[] v(Parcel parcel, int n2) {
        int n3 = a.a(parcel, n2);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        int n5 = parcel.readInt();
        BigInteger[] arrbigInteger = new BigInteger[n5];
        for (n2 = 0; n2 < n5; ++n2) {
            arrbigInteger[n2] = new BigInteger(parcel.createByteArray());
        }
        parcel.setDataPosition(n4 + n3);
        return arrbigInteger;
    }

    public static float[] w(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        float[] arrf = parcel.createFloatArray();
        parcel.setDataPosition(n2 + n3);
        return arrf;
    }

    public static double[] x(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        double[] arrd = parcel.createDoubleArray();
        parcel.setDataPosition(n2 + n3);
        return arrd;
    }

    public static BigDecimal[] y(Parcel parcel, int n2) {
        int n3 = a.a(parcel, n2);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        int n5 = parcel.readInt();
        BigDecimal[] arrbigDecimal = new BigDecimal[n5];
        for (n2 = 0; n2 < n5; ++n2) {
            byte[] arrby = parcel.createByteArray();
            int n6 = parcel.readInt();
            arrbigDecimal[n2] = new BigDecimal(new BigInteger(arrby), n6);
        }
        parcel.setDataPosition(n4 + n3);
        return arrbigDecimal;
    }

    public static String[] z(Parcel parcel, int n2) {
        n2 = a.a(parcel, n2);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        String[] arrstring = parcel.createStringArray();
        parcel.setDataPosition(n2 + n3);
        return arrstring;
    }

    public static class a
    extends RuntimeException {
        public a(String string2, Parcel parcel) {
            super(string2 + " Parcel: pos=" + parcel.dataPosition() + " size=" + parcel.dataSize());
        }
    }

}

