/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ep
extends IInterface {
    public void Y(String var1) throws RemoteException;

    public void Z(String var1) throws RemoteException;

    public void a(double var1, double var3, boolean var5) throws RemoteException;

    public void a(String var1, String var2, long var3) throws RemoteException;

    public void a(String var1, byte[] var2, long var3) throws RemoteException;

    public void a(boolean var1, double var2, boolean var4) throws RemoteException;

    public void aa(String var1) throws RemoteException;

    public void dH() throws RemoteException;

    public void dO() throws RemoteException;

    public void disconnect() throws RemoteException;

    public void e(String var1, String var2) throws RemoteException;

    public void e(String var1, boolean var2) throws RemoteException;

    public static abstract class com.google.android.gms.internal.ep$a
    extends Binder
    implements ep {
        public static ep y(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.internal.ICastDeviceController");
            if (iInterface != null && iInterface instanceof ep) {
                return (ep)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel object, int n3) throws RemoteException {
            boolean bl2 = false;
            boolean bl3 = false;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, (Parcel)object, n3);
                }
                case 1598968902: {
                    object.writeString("com.google.android.gms.cast.internal.ICastDeviceController");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.disconnect();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    object = parcel.readString();
                    if (parcel.readInt() != 0) {
                        bl3 = true;
                    }
                    this.e((String)object, bl3);
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.e(parcel.readString(), parcel.readString());
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.dO();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.Y(parcel.readString());
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.dH();
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    double d2 = parcel.readDouble();
                    double d3 = parcel.readDouble();
                    bl3 = parcel.readInt() != 0;
                    this.a(d2, d3, bl3);
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    bl3 = parcel.readInt() != 0;
                    double d4 = parcel.readDouble();
                    if (parcel.readInt() != 0) {
                        bl2 = true;
                    }
                    this.a(bl3, d4, bl2);
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.a(parcel.readString(), parcel.readString(), parcel.readLong());
                    return true;
                }
                case 10: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.a(parcel.readString(), parcel.createByteArray(), parcel.readLong());
                    return true;
                }
                case 11: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.Z(parcel.readString());
                    return true;
                }
                case 12: 
            }
            parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
            this.aa(parcel.readString());
            return true;
        }

        private static class a
        implements ep {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            @Override
            public void Y(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcel.writeString(string2);
                    this.kn.transact(5, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void Z(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcel.writeString(string2);
                    this.kn.transact(11, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(double d2, double d3, boolean bl2) throws RemoteException {
                int n2 = 1;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcel.writeDouble(d2);
                    parcel.writeDouble(d3);
                    if (!bl2) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(7, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void a(String string2, String string3, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeLong(l2);
                    this.kn.transact(9, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void a(String string2, byte[] arrby, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    parcel.writeLong(l2);
                    this.kn.transact(10, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(boolean bl2, double d2, boolean bl3) throws RemoteException {
                int n2 = 1;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    int n3 = bl2 ? 1 : 0;
                    parcel.writeInt(n3);
                    parcel.writeDouble(d2);
                    n3 = bl3 ? n2 : 0;
                    parcel.writeInt(n3);
                    this.kn.transact(8, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void aa(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcel.writeString(string2);
                    this.kn.transact(12, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public void dH() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.kn.transact(6, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dO() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.kn.transact(4, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void disconnect() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.kn.transact(1, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void e(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(3, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void e(String string2, boolean bl2) throws RemoteException {
                int n2 = 1;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcel.writeString(string2);
                    if (!bl2) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(2, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

