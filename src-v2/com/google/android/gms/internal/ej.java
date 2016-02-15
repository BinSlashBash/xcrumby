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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;

public interface ej
extends IInterface {
    public void a(int var1, DataHolder var2) throws RemoteException;

    public void a(DataHolder var1) throws RemoteException;

    public void b(int var1, int var2) throws RemoteException;

    public void du() throws RemoteException;

    public void v(int var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.ej$a
    extends Binder
    implements ej {
        public com.google.android.gms.internal.ej$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.appstate.internal.IAppStateCallbacks");
        }

        public static ej v(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
            if (iInterface != null && iInterface instanceof ej) {
                return (ej)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            DataHolder dataHolder = null;
            DataHolder dataHolder2 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    return true;
                }
                case 5001: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    n2 = parcel.readInt();
                    if (parcel.readInt() != 0) {
                        dataHolder2 = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.a(n2, dataHolder2);
                    parcel2.writeNoException();
                    return true;
                }
                case 5002: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    dataHolder2 = dataHolder;
                    if (parcel.readInt() != 0) {
                        dataHolder2 = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.a(dataHolder2);
                    parcel2.writeNoException();
                    return true;
                }
                case 5003: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    this.b(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 5004: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    this.du();
                    parcel2.writeNoException();
                    return true;
                }
                case 5005: 
            }
            parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
            this.v(parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements ej {
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
            public void a(int n2, DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    parcel.writeInt(n2);
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
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

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public void b(int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
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
            public void du() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    this.kn.transact(5004, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void v(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    parcel.writeInt(n2);
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

