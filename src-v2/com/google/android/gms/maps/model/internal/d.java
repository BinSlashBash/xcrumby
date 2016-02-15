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
import java.util.ArrayList;
import java.util.List;

public interface d
extends IInterface {
    public boolean b(d var1) throws RemoteException;

    public int getActiveLevelIndex() throws RemoteException;

    public int getDefaultLevelIndex() throws RemoteException;

    public List<IBinder> getLevels() throws RemoteException;

    public int hashCodeRemote() throws RemoteException;

    public boolean isUnderground() throws RemoteException;

    public static abstract class com.google.android.gms.maps.model.internal.d$a
    extends Binder
    implements d {
        public static d aE(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
            if (iInterface != null && iInterface instanceof d) {
                return (d)iInterface;
            }
            return new a(iBinder);
        }

        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            int n4 = 0;
            int n5 = 0;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    n2 = this.getActiveLevelIndex();
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    n2 = this.getDefaultLevelIndex();
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    object = this.getLevels();
                    parcel.writeNoException();
                    parcel.writeBinderList((List)object);
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    boolean bl2 = this.isUnderground();
                    parcel.writeNoException();
                    n2 = n5;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    boolean bl3 = this.b(com.google.android.gms.maps.model.internal.d$a.aE(object.readStrongBinder()));
                    parcel.writeNoException();
                    n2 = n4;
                    if (bl3) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 6: 
            }
            object.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
            n2 = this.hashCodeRemote();
            parcel.writeNoException();
            parcel.writeInt(n2);
            return true;
        }

        private static class a
        implements d {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public IBinder asBinder() {
                return this.kn;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean b(d d2) throws RemoteException {
                boolean bl2 = false;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    this.kn.transact(5, parcel, parcel2, 0);
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
            public int getActiveLevelIndex() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getDefaultLevelIndex() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<IBinder> getLevels() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.kn.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    ArrayList arrayList = parcel2.createBinderArrayList();
                    return arrayList;
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
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.kn.transact(6, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isUnderground() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                        this.kn.transact(4, parcel, parcel2, 0);
                        parcel2.readException();
                        int n2 = parcel2.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable var5_5) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw var5_5;
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                return bl2;
            }
        }

    }

}

