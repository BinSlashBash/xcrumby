/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.StatusCreator;
import com.google.android.gms.internal.ix;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.MaskedWallet;

public interface je
extends IInterface {
    public void a(int var1, FullWallet var2, Bundle var3) throws RemoteException;

    public void a(int var1, MaskedWallet var2, Bundle var3) throws RemoteException;

    public void a(int var1, boolean var2, Bundle var3) throws RemoteException;

    public void a(Status var1, ix var2, Bundle var3) throws RemoteException;

    public void f(int var1, Bundle var2) throws RemoteException;

    public static abstract class com.google.android.gms.internal.je$a
    extends Binder
    implements je {
        public com.google.android.gms.internal.je$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
        }

        public static je aX(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
            if (iInterface != null && iInterface instanceof je) {
                return (je)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    n2 = parcel.readInt();
                    MaskedWallet maskedWallet = parcel.readInt() != 0 ? (MaskedWallet)MaskedWallet.CREATOR.createFromParcel(parcel) : null;
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a(n2, maskedWallet, (Bundle)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    n2 = parcel.readInt();
                    FullWallet fullWallet = parcel.readInt() != 0 ? (FullWallet)FullWallet.CREATOR.createFromParcel(parcel) : null;
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a(n2, fullWallet, (Bundle)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    n2 = parcel.readInt();
                    boolean bl2 = parcel.readInt() != 0;
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a(n2, bl2, (Bundle)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    n2 = parcel.readInt();
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.f(n2, (Bundle)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 5: 
            }
            parcel.enforceInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
            Status status = parcel.readInt() != 0 ? Status.CREATOR.createFromParcel(parcel) : null;
            ix ix2 = parcel.readInt() != 0 ? (ix)ix.CREATOR.createFromParcel(parcel) : null;
            parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
            this.a(status, ix2, (Bundle)parcel);
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements je {
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
            public void a(int n2, FullWallet fullWallet, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    parcel.writeInt(n2);
                    if (fullWallet != null) {
                        parcel.writeInt(1);
                        fullWallet.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(2, parcel, parcel2, 0);
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
            public void a(int n2, MaskedWallet maskedWallet, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    parcel.writeInt(n2);
                    if (maskedWallet != null) {
                        parcel.writeInt(1);
                        maskedWallet.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(1, parcel, parcel2, 0);
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
            public void a(int n2, boolean bl2, Bundle bundle) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    parcel.writeInt(n2);
                    n2 = bl2 ? n3 : 0;
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(3, parcel, parcel2, 0);
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
            public void a(Status status, ix ix2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    if (status != null) {
                        parcel.writeInt(1);
                        status.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (ix2 != null) {
                        parcel.writeInt(1);
                        ix2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5, parcel, parcel2, 0);
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
            public void f(int n2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(4, parcel, parcel2, 0);
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

