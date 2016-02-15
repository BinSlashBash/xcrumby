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
package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface e
extends IInterface {
    public boolean a(e var1) throws RemoteException;

    public void activate() throws RemoteException;

    public String getName() throws RemoteException;

    public String getShortName() throws RemoteException;

    public int hashCodeRemote() throws RemoteException;

    public static abstract class com.google.android.gms.maps.model.internal.e$a
    extends Binder
    implements e {
        public static e aF(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
            if (iInterface != null && iInterface instanceof e) {
                return (e)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    object = this.getName();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    object = this.getShortName();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    this.activate();
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    boolean bl2 = this.a(com.google.android.gms.maps.model.internal.e$a.aF(object.readStrongBinder()));
                    parcel.writeNoException();
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    return true;
                }
                case 5: 
            }
            object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
            n2 = this.hashCodeRemote();
            parcel.writeNoException();
            parcel.writeInt(n2);
            return true;
        }

        private static class a
        implements e {
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
            public boolean a(e e2) throws RemoteException {
                boolean bl2 = false;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    e2 = e2 != null ? e2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)e2);
                    this.kn.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    if (n2 != 0) {
                        bl2 = true;
                    }
                    return bl2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void activate() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    this.kn.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
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
            public String getName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
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

            @Override
            public String getShortName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int hashCodeRemote() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
                    this.kn.transact(5, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

