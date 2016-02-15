package com.google.android.gms.plus.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.fk;
import com.google.android.gms.internal.fk.C0892a;
import com.google.android.gms.internal.gg;
import com.google.android.gms.plus.internal.C0476b.C1046a;
import java.util.List;
import org.json.zip.JSONzip;

/* renamed from: com.google.android.gms.plus.internal.d */
public interface C0478d extends IInterface {

    /* renamed from: com.google.android.gms.plus.internal.d.a */
    public static abstract class C1050a extends Binder implements C0478d {

        /* renamed from: com.google.android.gms.plus.internal.d.a.a */
        private static class C1049a implements C0478d {
            private IBinder kn;

            C1049a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public fk m2419a(C0476b c0476b, int i, int i2, int i3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.kn.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    fk A = C0892a.m2180A(obtain2.readStrongBinder());
                    return A;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2420a(gg ggVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    if (ggVar != null) {
                        obtain.writeInt(1);
                        ggVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.kn.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2421a(C0476b c0476b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    this.kn.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2422a(C0476b c0476b, int i, String str, Uri uri, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (uri != null) {
                        obtain.writeInt(1);
                        uri.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.kn.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2423a(C0476b c0476b, Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    if (uri != null) {
                        obtain.writeInt(1);
                        uri.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.kn.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2424a(C0476b c0476b, gg ggVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    if (ggVar != null) {
                        obtain.writeInt(1);
                        ggVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.kn.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2425a(C0476b c0476b, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    obtain.writeString(str);
                    this.kn.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2426a(C0476b c0476b, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.kn.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2427a(C0476b c0476b, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    obtain.writeStringList(list);
                    this.kn.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }

            public void m2428b(C0476b c0476b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    this.kn.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2429b(C0476b c0476b, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    obtain.writeString(str);
                    this.kn.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2430c(C0476b c0476b, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    obtain.writeString(str);
                    this.kn.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void clearDefaultAccount() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.kn.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2431d(C0476b c0476b, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    obtain.writeString(str);
                    this.kn.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m2432e(C0476b c0476b, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(c0476b != null ? c0476b.asBinder() : null);
                    obtain.writeString(str);
                    this.kn.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getAccountName() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.kn.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String iK() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.kn.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean iL() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.kn.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String iM() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.kn.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeMoment(String momentId) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeString(momentId);
                    this.kn.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0478d aQ(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0478d)) ? new C1049a(iBinder) : (C0478d) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            gg ggVar = null;
            String accountName;
            switch (code) {
                case Std.STD_FILE /*1*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1313a(C1046a.aO(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case Std.STD_URL /*2*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1314a(C1046a.aO(data.readStrongBinder()), data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case Std.STD_URI /*3*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1317b(C1046a.aO(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case Std.STD_CLASS /*4*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1308a(data.readInt() != 0 ? gg.CREATOR.m1024x(data) : null);
                    reply.writeNoException();
                    return true;
                case Std.STD_JAVA_TYPE /*5*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    accountName = getAccountName();
                    reply.writeNoException();
                    reply.writeString(accountName);
                    return true;
                case Std.STD_CURRENCY /*6*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    clearDefaultAccount();
                    reply.writeNoException();
                    return true;
                case Std.STD_LOCALE /*8*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1309a(C1046a.aO(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case Std.STD_CHARSET /*9*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1311a(C1046a.aO(data.readStrongBinder()), data.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1310a(C1046a.aO(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(data) : null, data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                    IBinder asBinder;
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    fk a = m1307a(C1046a.aO(data.readStrongBinder()), data.readInt(), data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    if (a != null) {
                        asBinder = a.asBinder();
                    }
                    reply.writeStrongBinder(asBinder);
                    return true;
                case C0065R.styleable.TwoWayView_android_paddingRight /*17*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    removeMoment(data.readString());
                    reply.writeNoException();
                    return true;
                case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1318c(C1046a.aO(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case C0065R.styleable.TwoWayView_android_focusable /*19*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1316b(C1046a.aO(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case C0065R.styleable.TwoWayView_android_duplicateParentState /*34*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1315a(C1046a.aO(data.readStrongBinder()), data.createStringArrayList());
                    reply.writeNoException();
                    return true;
                case JSONzip.substringLimit /*40*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1319d(C1046a.aO(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case C0065R.styleable.TwoWayView_android_keepScreenOn /*41*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    accountName = iK();
                    reply.writeNoException();
                    reply.writeString(accountName);
                    return true;
                case C0065R.styleable.TwoWayView_android_isScrollContainer /*42*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    boolean iL = iL();
                    reply.writeNoException();
                    reply.writeInt(iL ? 1 : 0);
                    return true;
                case C0065R.styleable.TwoWayView_android_hapticFeedbackEnabled /*43*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    accountName = iM();
                    reply.writeNoException();
                    reply.writeString(accountName);
                    return true;
                case C0065R.styleable.TwoWayView_android_onClick /*44*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m1320e(C1046a.aO(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case C0065R.styleable.TwoWayView_android_contentDescription /*45*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    C0476b aO = C1046a.aO(data.readStrongBinder());
                    if (data.readInt() != 0) {
                        ggVar = gg.CREATOR.m1024x(data);
                    }
                    m1312a(aO, ggVar);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.plus.internal.IPlusService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    fk m1307a(C0476b c0476b, int i, int i2, int i3, String str) throws RemoteException;

    void m1308a(gg ggVar) throws RemoteException;

    void m1309a(C0476b c0476b) throws RemoteException;

    void m1310a(C0476b c0476b, int i, String str, Uri uri, String str2, String str3) throws RemoteException;

    void m1311a(C0476b c0476b, Uri uri, Bundle bundle) throws RemoteException;

    void m1312a(C0476b c0476b, gg ggVar) throws RemoteException;

    void m1313a(C0476b c0476b, String str) throws RemoteException;

    void m1314a(C0476b c0476b, String str, String str2) throws RemoteException;

    void m1315a(C0476b c0476b, List<String> list) throws RemoteException;

    void m1316b(C0476b c0476b) throws RemoteException;

    void m1317b(C0476b c0476b, String str) throws RemoteException;

    void m1318c(C0476b c0476b, String str) throws RemoteException;

    void clearDefaultAccount() throws RemoteException;

    void m1319d(C0476b c0476b, String str) throws RemoteException;

    void m1320e(C0476b c0476b, String str) throws RemoteException;

    String getAccountName() throws RemoteException;

    String iK() throws RemoteException;

    boolean iL() throws RemoteException;

    String iM() throws RemoteException;

    void removeMoment(String str) throws RemoteException;
}
