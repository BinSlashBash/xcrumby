/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
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

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public interface iz
extends IInterface {
    public void a(d var1, WalletFragmentOptions var2, Bundle var3) throws RemoteException;

    public int getState() throws RemoteException;

    public void initialize(WalletFragmentInitParams var1) throws RemoteException;

    public void onActivityResult(int var1, int var2, Intent var3) throws RemoteException;

    public void onCreate(Bundle var1) throws RemoteException;

    public d onCreateView(d var1, d var2, Bundle var3) throws RemoteException;

    public void onPause() throws RemoteException;

    public void onResume() throws RemoteException;

    public void onSaveInstanceState(Bundle var1) throws RemoteException;

    public void onStart() throws RemoteException;

    public void onStop() throws RemoteException;

    public void setEnabled(boolean var1) throws RemoteException;

    public void updateMaskedWalletRequest(MaskedWalletRequest var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.iz$a
    extends Binder
    implements iz {
        public static iz aS(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
            if (iInterface != null && iInterface instanceof iz) {
                return (iz)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            WalletFragmentOptions walletFragmentOptions = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    d d2 = d.a.K(object.readStrongBinder());
                    walletFragmentOptions = object.readInt() != 0 ? (WalletFragmentOptions)WalletFragmentOptions.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a(d2, walletFragmentOptions, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onCreate((Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    d d3 = d.a.K(object.readStrongBinder());
                    d d4 = d.a.K(object.readStrongBinder());
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    d3 = this.onCreateView(d3, d4, (Bundle)object);
                    parcel.writeNoException();
                    object = walletFragmentOptions;
                    if (d3 != null) {
                        object = d3.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    this.onStart();
                    parcel.writeNoException();
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    this.onResume();
                    parcel.writeNoException();
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    this.onPause();
                    parcel.writeNoException();
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    this.onStop();
                    parcel.writeNoException();
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onSaveInstanceState((Bundle)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    n2 = object.readInt();
                    n3 = object.readInt();
                    object = object.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onActivityResult(n2, n3, (Intent)object);
                    parcel.writeNoException();
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    object = object.readInt() != 0 ? (WalletFragmentInitParams)WalletFragmentInitParams.CREATOR.createFromParcel((Parcel)object) : null;
                    this.initialize((WalletFragmentInitParams)object);
                    parcel.writeNoException();
                    return true;
                }
                case 11: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    object = object.readInt() != 0 ? (MaskedWalletRequest)MaskedWalletRequest.CREATOR.createFromParcel((Parcel)object) : null;
                    this.updateMaskedWalletRequest((MaskedWalletRequest)object);
                    parcel.writeNoException();
                    return true;
                }
                case 12: {
                    object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    boolean bl2 = object.readInt() != 0;
                    this.setEnabled(bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 13: 
            }
            object.enforceInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
            n2 = this.getState();
            parcel.writeNoException();
            parcel.writeInt(n2);
            return true;
        }

        private static class a
        implements iz {
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
            public void a(d d2, WalletFragmentOptions walletFragmentOptions, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    if (walletFragmentOptions != null) {
                        parcel.writeInt(1);
                        walletFragmentOptions.writeToParcel(parcel, 0);
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

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public int getState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    this.kn.transact(13, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
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
            public void initialize(WalletFragmentInitParams walletFragmentInitParams) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    if (walletFragmentInitParams != null) {
                        parcel.writeInt(1);
                        walletFragmentInitParams.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(10, parcel, parcel2, 0);
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
            public void onActivityResult(int n2, int n3, Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(9, parcel, parcel2, 0);
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
            public void onCreate(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
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
            public d onCreateView(d d2, d d3, Bundle bundle) throws RemoteException {
                Object var4_5 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    d2 = var4_5;
                    if (d3 != null) {
                        d2 = d3.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)d2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onPause() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
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
            public void onResume() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    this.kn.transact(5, parcel, parcel2, 0);
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
            public void onSaveInstanceState(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        bundle.readFromParcel(parcel2);
                    }
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onStart() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
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
            public void onStop() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    this.kn.transact(7, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setEnabled(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(12, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
                    if (maskedWalletRequest != null) {
                        parcel.writeInt(1);
                        maskedWalletRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(11, parcel, parcel2, 0);
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

