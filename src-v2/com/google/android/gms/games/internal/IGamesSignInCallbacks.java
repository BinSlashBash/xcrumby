/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.games.internal;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;

public interface IGamesSignInCallbacks
extends IInterface {
    public void H(DataHolder var1) throws RemoteException;

    public void I(DataHolder var1) throws RemoteException;

    public void aZ(int var1) throws RemoteException;

    public void b(int var1, Intent var2) throws RemoteException;

    public void ba(int var1) throws RemoteException;

    public void e(DataHolder var1) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IGamesSignInCallbacks {
        public Stub() {
            this.attachInterface((IInterface)this, "com.google.android.gms.games.internal.IGamesSignInCallbacks");
        }

        public static IGamesSignInCallbacks O(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IGamesSignInCallbacks");
            if (iInterface != null && iInterface instanceof IGamesSignInCallbacks) {
                return (IGamesSignInCallbacks)iInterface;
            }
            return new Proxy(iBinder);
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            DataHolder dataHolder = null;
            DataHolder dataHolder2 = null;
            DataHolder dataHolder3 = null;
            DataHolder dataHolder4 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    return true;
                }
                case 5001: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    n2 = parcel.readInt();
                    if (parcel.readInt() != 0) {
                        dataHolder4 = (Intent)Intent.CREATOR.createFromParcel(parcel);
                    }
                    this.b(n2, (Intent)dataHolder4);
                    parcel2.writeNoException();
                    return true;
                }
                case 5002: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    dataHolder4 = dataHolder;
                    if (parcel.readInt() != 0) {
                        dataHolder4 = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.H(dataHolder4);
                    parcel2.writeNoException();
                    return true;
                }
                case 5003: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    dataHolder4 = dataHolder2;
                    if (parcel.readInt() != 0) {
                        dataHolder4 = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.I(dataHolder4);
                    parcel2.writeNoException();
                    return true;
                }
                case 5004: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    this.aZ(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 5005: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    dataHolder4 = dataHolder3;
                    if (parcel.readInt() != 0) {
                        dataHolder4 = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.e(dataHolder4);
                    parcel2.writeNoException();
                    return true;
                }
                case 5006: 
            }
            parcel.enforceInterface("com.google.android.gms.games.internal.IGamesSignInCallbacks");
            this.ba(parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IGamesSignInCallbacks {
            private IBinder kn;

            Proxy(IBinder iBinder) {
                this.kn = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void H(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5002, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void I(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5003, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void aZ(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    parcel.writeInt(n2);
                    this.kn.transact(5004, parcel, parcel2, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void b(int n2, Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    parcel.writeInt(n2);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5001, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void ba(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    parcel.writeInt(n2);
                    this.kn.transact(5006, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void e(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5005, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

