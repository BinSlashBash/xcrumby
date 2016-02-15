package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.games.request.GameRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.gms.common.internal.safeparcel.a */
public class C0261a {

    /* renamed from: com.google.android.gms.common.internal.safeparcel.a.a */
    public static class C0260a extends RuntimeException {
        public C0260a(String str, Parcel parcel) {
            super(str + " Parcel: pos=" + parcel.dataPosition() + " size=" + parcel.dataSize());
        }
    }

    public static ArrayList<String> m171A(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        ArrayList<String> createStringArrayList = parcel.createStringArrayList();
        parcel.setDataPosition(a + dataPosition);
        return createStringArrayList;
    }

    public static Parcel m172B(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        obtain.appendFrom(parcel, dataPosition, a);
        parcel.setDataPosition(a + dataPosition);
        return obtain;
    }

    public static Parcel[] m173C(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        Parcel[] parcelArr = new Parcel[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            int readInt2 = parcel.readInt();
            if (readInt2 != 0) {
                int dataPosition2 = parcel.dataPosition();
                Parcel obtain = Parcel.obtain();
                obtain.appendFrom(parcel, dataPosition2, readInt2);
                parcelArr[i2] = obtain;
                parcel.setDataPosition(readInt2 + dataPosition2);
            } else {
                parcelArr[i2] = null;
            }
        }
        parcel.setDataPosition(dataPosition + a);
        return parcelArr;
    }

    public static int m174R(int i) {
        return GameRequest.TYPE_ALL & i;
    }

    public static int m175a(Parcel parcel, int i) {
        return (i & SupportMenu.CATEGORY_MASK) != SupportMenu.CATEGORY_MASK ? (i >> 16) & GameRequest.TYPE_ALL : parcel.readInt();
    }

    public static <T extends Parcelable> T m176a(Parcel parcel, int i, Creator<T> creator) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        Parcelable parcelable = (Parcelable) creator.createFromParcel(parcel);
        parcel.setDataPosition(a + dataPosition);
        return parcelable;
    }

    private static void m177a(Parcel parcel, int i, int i2) {
        int a = C0261a.m175a(parcel, i);
        if (a != i2) {
            throw new C0260a("Expected size " + i2 + " got " + a + " (0x" + Integer.toHexString(a) + ")", parcel);
        }
    }

    private static void m178a(Parcel parcel, int i, int i2, int i3) {
        if (i2 != i3) {
            throw new C0260a("Expected size " + i3 + " got " + i2 + " (0x" + Integer.toHexString(i2) + ")", parcel);
        }
    }

    public static void m179a(Parcel parcel, int i, List list, ClassLoader classLoader) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a != 0) {
            parcel.readList(list, classLoader);
            parcel.setDataPosition(a + dataPosition);
        }
    }

    public static void m180b(Parcel parcel, int i) {
        parcel.setDataPosition(C0261a.m175a(parcel, i) + parcel.dataPosition());
    }

    public static <T> T[] m181b(Parcel parcel, int i, Creator<T> creator) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        T[] createTypedArray = parcel.createTypedArray(creator);
        parcel.setDataPosition(a + dataPosition);
        return createTypedArray;
    }

    public static <T> ArrayList<T> m182c(Parcel parcel, int i, Creator<T> creator) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        ArrayList<T> createTypedArrayList = parcel.createTypedArrayList(creator);
        parcel.setDataPosition(a + dataPosition);
        return createTypedArrayList;
    }

    public static boolean m183c(Parcel parcel, int i) {
        C0261a.m177a(parcel, i, 4);
        return parcel.readInt() != 0;
    }

    public static Boolean m184d(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        if (a == 0) {
            return null;
        }
        C0261a.m178a(parcel, i, a, 4);
        return Boolean.valueOf(parcel.readInt() != 0);
    }

    public static byte m185e(Parcel parcel, int i) {
        C0261a.m177a(parcel, i, 4);
        return (byte) parcel.readInt();
    }

    public static short m186f(Parcel parcel, int i) {
        C0261a.m177a(parcel, i, 4);
        return (short) parcel.readInt();
    }

    public static int m187g(Parcel parcel, int i) {
        C0261a.m177a(parcel, i, 4);
        return parcel.readInt();
    }

    public static Integer m188h(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        if (a == 0) {
            return null;
        }
        C0261a.m178a(parcel, i, a, 4);
        return Integer.valueOf(parcel.readInt());
    }

    public static long m189i(Parcel parcel, int i) {
        C0261a.m177a(parcel, i, 8);
        return parcel.readLong();
    }

    public static BigInteger m190j(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        parcel.setDataPosition(a + dataPosition);
        return new BigInteger(createByteArray);
    }

    public static float m191k(Parcel parcel, int i) {
        C0261a.m177a(parcel, i, 4);
        return parcel.readFloat();
    }

    public static double m192l(Parcel parcel, int i) {
        C0261a.m177a(parcel, i, 8);
        return parcel.readDouble();
    }

    public static BigDecimal m193m(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        int readInt = parcel.readInt();
        parcel.setDataPosition(a + dataPosition);
        return new BigDecimal(new BigInteger(createByteArray), readInt);
    }

    public static int m194n(Parcel parcel) {
        return parcel.readInt();
    }

    public static String m195n(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        String readString = parcel.readString();
        parcel.setDataPosition(a + dataPosition);
        return readString;
    }

    public static int m196o(Parcel parcel) {
        int n = C0261a.m194n(parcel);
        int a = C0261a.m175a(parcel, n);
        int dataPosition = parcel.dataPosition();
        if (C0261a.m174R(n) != 20293) {
            throw new C0260a("Expected object header. Got 0x" + Integer.toHexString(n), parcel);
        }
        n = dataPosition + a;
        if (n >= dataPosition && n <= parcel.dataSize()) {
            return n;
        }
        throw new C0260a("Size read is invalid start=" + dataPosition + " end=" + n, parcel);
    }

    public static IBinder m197o(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        IBinder readStrongBinder = parcel.readStrongBinder();
        parcel.setDataPosition(a + dataPosition);
        return readStrongBinder;
    }

    public static Bundle m198p(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        Bundle readBundle = parcel.readBundle();
        parcel.setDataPosition(a + dataPosition);
        return readBundle;
    }

    public static byte[] m199q(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        parcel.setDataPosition(a + dataPosition);
        return createByteArray;
    }

    public static byte[][] m200r(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return (byte[][]) null;
        }
        int readInt = parcel.readInt();
        byte[][] bArr = new byte[readInt][];
        for (int i2 = 0; i2 < readInt; i2++) {
            bArr[i2] = parcel.createByteArray();
        }
        parcel.setDataPosition(dataPosition + a);
        return bArr;
    }

    public static boolean[] m201s(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        boolean[] createBooleanArray = parcel.createBooleanArray();
        parcel.setDataPosition(a + dataPosition);
        return createBooleanArray;
    }

    public static int[] m202t(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        int[] createIntArray = parcel.createIntArray();
        parcel.setDataPosition(a + dataPosition);
        return createIntArray;
    }

    public static long[] m203u(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        long[] createLongArray = parcel.createLongArray();
        parcel.setDataPosition(a + dataPosition);
        return createLongArray;
    }

    public static BigInteger[] m204v(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        BigInteger[] bigIntegerArr = new BigInteger[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            bigIntegerArr[i2] = new BigInteger(parcel.createByteArray());
        }
        parcel.setDataPosition(dataPosition + a);
        return bigIntegerArr;
    }

    public static float[] m205w(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        float[] createFloatArray = parcel.createFloatArray();
        parcel.setDataPosition(a + dataPosition);
        return createFloatArray;
    }

    public static double[] m206x(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        double[] createDoubleArray = parcel.createDoubleArray();
        parcel.setDataPosition(a + dataPosition);
        return createDoubleArray;
    }

    public static BigDecimal[] m207y(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        BigDecimal[] bigDecimalArr = new BigDecimal[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            byte[] createByteArray = parcel.createByteArray();
            bigDecimalArr[i2] = new BigDecimal(new BigInteger(createByteArray), parcel.readInt());
        }
        parcel.setDataPosition(dataPosition + a);
        return bigDecimalArr;
    }

    public static String[] m208z(Parcel parcel, int i) {
        int a = C0261a.m175a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        String[] createStringArray = parcel.createStringArray();
        parcel.setDataPosition(a + dataPosition);
        return createStringArray;
    }
}
