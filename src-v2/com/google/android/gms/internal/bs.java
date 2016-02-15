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

public interface bs
extends IInterface {
    public void P() throws RemoteException;

    public void onAdClosed() throws RemoteException;

    public void onAdFailedToLoad(int var1) throws RemoteException;

    public void onAdLeftApplication() throws RemoteException;

    public void onAdLoaded() throws RemoteException;

    public void onAdOpened() throws RemoteException;

    public static abstract class com.google.android.gms.internal.bs$a
    extends Binder
    implements bs {
        public com.google.android.gms.internal.bs$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
        }

        public static bs k(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
            if (iInterface != null && iInterface instanceof bs) {
                return (bs)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    this.P();
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    this.onAdClosed();
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    this.onAdFailedToLoad(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    this.onAdLeftApplication();
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    this.onAdOpened();
                    parcel2.writeNoException();
                    return true;
                }
                case 6: 
            }
            parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
            this.onAdLoaded();
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements bs {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            @Override
            public void P() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    this.kn.transact(1, parcel, parcel2, 0);
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
            public void onAdClosed() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onAdFailedToLoad(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    parcel.writeInt(n2);
                    this.kn.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onAdLeftApplication() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
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
            public void onAdLoaded() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    this.kn.transact(6, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onAdOpened() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapterListener");
                    this.kn.transact(5, parcel, parcel2, 0);
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

