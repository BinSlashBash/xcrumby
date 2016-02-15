package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.games.request.GameRequest;
import java.util.List;

/* renamed from: com.google.android.gms.common.internal.safeparcel.b */
public class C0262b {
    private static int m209D(Parcel parcel, int i) {
        parcel.writeInt(SupportMenu.CATEGORY_MASK | i);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void m210E(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        int i2 = dataPosition - i;
        parcel.setDataPosition(i - 4);
        parcel.writeInt(i2);
        parcel.setDataPosition(dataPosition);
    }

    public static void m211F(Parcel parcel, int i) {
        C0262b.m210E(parcel, i);
    }

    public static void m212a(Parcel parcel, int i, byte b) {
        C0262b.m232b(parcel, i, 4);
        parcel.writeInt(b);
    }

    public static void m213a(Parcel parcel, int i, double d) {
        C0262b.m232b(parcel, i, 8);
        parcel.writeDouble(d);
    }

    public static void m214a(Parcel parcel, int i, float f) {
        C0262b.m232b(parcel, i, 4);
        parcel.writeFloat(f);
    }

    public static void m215a(Parcel parcel, int i, long j) {
        C0262b.m232b(parcel, i, 8);
        parcel.writeLong(j);
    }

    public static void m216a(Parcel parcel, int i, Bundle bundle, boolean z) {
        if (bundle != null) {
            int D = C0262b.m209D(parcel, i);
            parcel.writeBundle(bundle);
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m217a(Parcel parcel, int i, IBinder iBinder, boolean z) {
        if (iBinder != null) {
            int D = C0262b.m209D(parcel, i);
            parcel.writeStrongBinder(iBinder);
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m218a(Parcel parcel, int i, Parcel parcel2, boolean z) {
        if (parcel2 != null) {
            int D = C0262b.m209D(parcel, i);
            parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m219a(Parcel parcel, int i, Parcelable parcelable, int i2, boolean z) {
        if (parcelable != null) {
            int D = C0262b.m209D(parcel, i);
            parcelable.writeToParcel(parcel, i2);
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m220a(Parcel parcel, int i, Boolean bool, boolean z) {
        int i2 = 0;
        if (bool != null) {
            C0262b.m232b(parcel, i, 4);
            if (bool.booleanValue()) {
                i2 = 1;
            }
            parcel.writeInt(i2);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m221a(Parcel parcel, int i, Integer num, boolean z) {
        if (num != null) {
            C0262b.m232b(parcel, i, 4);
            parcel.writeInt(num.intValue());
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m222a(Parcel parcel, int i, String str, boolean z) {
        if (str != null) {
            int D = C0262b.m209D(parcel, i);
            parcel.writeString(str);
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m223a(Parcel parcel, int i, List<String> list, boolean z) {
        if (list != null) {
            int D = C0262b.m209D(parcel, i);
            parcel.writeStringList(list);
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m224a(Parcel parcel, int i, short s) {
        C0262b.m232b(parcel, i, 4);
        parcel.writeInt(s);
    }

    public static void m225a(Parcel parcel, int i, boolean z) {
        C0262b.m232b(parcel, i, 4);
        parcel.writeInt(z ? 1 : 0);
    }

    public static void m226a(Parcel parcel, int i, byte[] bArr, boolean z) {
        if (bArr != null) {
            int D = C0262b.m209D(parcel, i);
            parcel.writeByteArray(bArr);
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m227a(Parcel parcel, int i, int[] iArr, boolean z) {
        if (iArr != null) {
            int D = C0262b.m209D(parcel, i);
            parcel.writeIntArray(iArr);
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static <T extends Parcelable> void m228a(Parcel parcel, int i, T[] tArr, int i2, boolean z) {
        if (tArr != null) {
            int D = C0262b.m209D(parcel, i);
            parcel.writeInt(r3);
            for (Parcelable parcelable : tArr) {
                if (parcelable == null) {
                    parcel.writeInt(0);
                } else {
                    C0262b.m231a(parcel, parcelable, i2);
                }
            }
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m229a(Parcel parcel, int i, String[] strArr, boolean z) {
        if (strArr != null) {
            int D = C0262b.m209D(parcel, i);
            parcel.writeStringArray(strArr);
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m230a(Parcel parcel, int i, byte[][] bArr, boolean z) {
        int i2 = 0;
        if (bArr != null) {
            int D = C0262b.m209D(parcel, i);
            int length = bArr.length;
            parcel.writeInt(length);
            while (i2 < length) {
                parcel.writeByteArray(bArr[i2]);
                i2++;
            }
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    private static <T extends Parcelable> void m231a(Parcel parcel, T t, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(1);
        int dataPosition2 = parcel.dataPosition();
        t.writeToParcel(parcel, i);
        int dataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition3 - dataPosition2);
        parcel.setDataPosition(dataPosition3);
    }

    private static void m232b(Parcel parcel, int i, int i2) {
        if (i2 >= GameRequest.TYPE_ALL) {
            parcel.writeInt(SupportMenu.CATEGORY_MASK | i);
            parcel.writeInt(i2);
            return;
        }
        parcel.writeInt((i2 << 16) | i);
    }

    public static <T extends Parcelable> void m233b(Parcel parcel, int i, List<T> list, boolean z) {
        if (list != null) {
            int D = C0262b.m209D(parcel, i);
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                Parcelable parcelable = (Parcelable) list.get(i2);
                if (parcelable == null) {
                    parcel.writeInt(0);
                } else {
                    C0262b.m231a(parcel, parcelable, 0);
                }
            }
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static void m234c(Parcel parcel, int i, int i2) {
        C0262b.m232b(parcel, i, 4);
        parcel.writeInt(i2);
    }

    public static void m235c(Parcel parcel, int i, List list, boolean z) {
        if (list != null) {
            int D = C0262b.m209D(parcel, i);
            parcel.writeList(list);
            C0262b.m210E(parcel, D);
        } else if (z) {
            C0262b.m232b(parcel, i, 0);
        }
    }

    public static int m236p(Parcel parcel) {
        return C0262b.m209D(parcel, 20293);
    }
}
