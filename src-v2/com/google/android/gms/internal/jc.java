/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.internal.iz;
import com.google.android.gms.internal.ja;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public interface jc
extends IInterface {
    public iz a(d var1, c var2, WalletFragmentOptions var3, ja var4) throws RemoteException;

    public static abstract class com.google.android.gms.internal.jc$a
    extends Binder
    implements jc {
        public static jc aV(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
            if (iInterface != null && iInterface instanceof jc) {
                return (jc)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            Object var6_5 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
            d d2 = d.a.K(parcel.readStrongBinder());
            c c2 = c.a.J(parcel.readStrongBinder());
            Object object = parcel.readInt() != 0 ? (WalletFragmentOptions)WalletFragmentOptions.CREATOR.createFromParcel(parcel) : null;
            object = this.a(d2, c2, (WalletFragmentOptions)object, ja.a.aT(parcel.readStrongBinder()));
            parcel2.writeNoException();
            parcel = var6_5;
            if (object != null) {
                parcel = object.asBinder();
            }
            parcel2.writeStrongBinder((IBinder)parcel);
            return true;
        }

        private static class a
        implements jc {
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
            public iz a(d object, c c2, WalletFragmentOptions walletFragmentOptions, ja ja2) throws RemoteException {
                Object var5_6 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
                    object = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)object);
                    object = c2 != null ? c2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)object);
                    if (walletFragmentOptions != null) {
                        parcel.writeInt(1);
                        walletFragmentOptions.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    object = var5_6;
                    if (ja2 != null) {
                        object = ja2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    object = iz.a.aS(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }
        }

    }

}

