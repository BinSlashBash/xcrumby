package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;

public interface ep extends IInterface {

    /* renamed from: com.google.android.gms.internal.ep.a */
    public static abstract class C0873a extends Binder implements ep {

        /* renamed from: com.google.android.gms.internal.ep.a.a */
        private static class C0872a implements ep {
            private IBinder kn;

            C0872a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public void m2116Y(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    obtain.writeString(str);
                    this.kn.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m2117Z(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    obtain.writeString(str);
                    this.kn.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m2118a(double d, double d2, boolean z) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    obtain.writeDouble(d);
                    obtain.writeDouble(d2);
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.kn.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m2119a(String str, String str2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    this.kn.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m2120a(String str, byte[] bArr, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeLong(j);
                    this.kn.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m2121a(boolean z, double d, boolean z2) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeDouble(d);
                    if (!z2) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.kn.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void aa(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    obtain.writeString(str);
                    this.kn.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }

            public void dH() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.kn.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void dO() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.kn.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void disconnect() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.kn.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m2122e(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.kn.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m2123e(String str, boolean z) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    obtain.writeString(str);
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.kn.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static ep m2124y(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.internal.ICastDeviceController");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ep)) ? new C0872a(iBinder) : (ep) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean z = false;
            switch (code) {
                case Std.STD_FILE /*1*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    disconnect();
                    return true;
                case Std.STD_URL /*2*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    String readString = data.readString();
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    m884e(readString, z);
                    return true;
                case Std.STD_URI /*3*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    m883e(data.readString(), data.readString());
                    return true;
                case Std.STD_CLASS /*4*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    dO();
                    return true;
                case Std.STD_JAVA_TYPE /*5*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    m877Y(data.readString());
                    return true;
                case Std.STD_CURRENCY /*6*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    dH();
                    return true;
                case Std.STD_PATTERN /*7*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    m879a(data.readDouble(), data.readDouble(), data.readInt() != 0);
                    return true;
                case Std.STD_LOCALE /*8*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    boolean z2 = data.readInt() != 0;
                    double readDouble = data.readDouble();
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    m882a(z2, readDouble, z);
                    return true;
                case Std.STD_CHARSET /*9*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    m880a(data.readString(), data.readString(), data.readLong());
                    return true;
                case Std.STD_TIME_ZONE /*10*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    m881a(data.readString(), data.createByteArray(), data.readLong());
                    return true;
                case Std.STD_INET_ADDRESS /*11*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    m878Z(data.readString());
                    return true;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    aa(data.readString());
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.cast.internal.ICastDeviceController");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m877Y(String str) throws RemoteException;

    void m878Z(String str) throws RemoteException;

    void m879a(double d, double d2, boolean z) throws RemoteException;

    void m880a(String str, String str2, long j) throws RemoteException;

    void m881a(String str, byte[] bArr, long j) throws RemoteException;

    void m882a(boolean z, double d, boolean z2) throws RemoteException;

    void aa(String str) throws RemoteException;

    void dH() throws RemoteException;

    void dO() throws RemoteException;

    void disconnect() throws RemoteException;

    void m883e(String str, String str2) throws RemoteException;

    void m884e(String str, boolean z) throws RemoteException;
}
