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

public interface t
extends IInterface {
    public boolean a(boolean var1) throws RemoteException;

    public void b(String var1, boolean var2) throws RemoteException;

    public String c(String var1) throws RemoteException;

    public String getId() throws RemoteException;

    public static abstract class com.google.android.gms.internal.t$a
    extends Binder
    implements t {
        public static t b(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            if (iInterface != null && iInterface instanceof t) {
                return (t)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            boolean bl2 = false;
            int n4 = 0;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    object = this.getId();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    bl2 = object.readInt() != 0;
                    bl2 = this.a(bl2);
                    parcel.writeNoException();
                    n2 = n4;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    object = this.c(object.readString());
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 4: 
            }
            object.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            String string2 = object.readString();
            if (object.readInt() != 0) {
                bl2 = true;
            }
            this.b(string2, bl2);
            parcel.writeNoException();
            return true;
        }

        private static class a
        implements t {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean a(boolean bl2) throws RemoteException {
                boolean bl3 = true;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    int n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    n2 = parcel2.readInt();
                    bl2 = n2 != 0 ? bl3 : false;
                    return bl2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public void b(String string2, boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                int n2;
                block4 : {
                    n2 = 0;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    parcel.writeString(string2);
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel.writeInt(n2);
                    this.kn.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String c(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    parcel.writeString(string2);
                    this.kn.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

