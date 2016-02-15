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
import com.google.android.gms.internal.iv;
import com.google.android.gms.internal.jd;
import com.google.android.gms.internal.je;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
import com.google.android.gms.wallet.d;

public interface jb
extends IInterface {
    public void a(Bundle var1, je var2) throws RemoteException;

    public void a(iv var1, Bundle var2, je var3) throws RemoteException;

    public void a(FullWalletRequest var1, Bundle var2, je var3) throws RemoteException;

    public void a(MaskedWalletRequest var1, Bundle var2, jd var3) throws RemoteException;

    public void a(MaskedWalletRequest var1, Bundle var2, je var3) throws RemoteException;

    public void a(NotifyTransactionStatusRequest var1, Bundle var2) throws RemoteException;

    public void a(d var1, Bundle var2, je var3) throws RemoteException;

    public void a(String var1, String var2, Bundle var3, je var4) throws RemoteException;

    public static abstract class com.google.android.gms.internal.jb$a
    extends Binder
    implements jb {
        public static jb aU(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IOwService");
            if (iInterface != null && iInterface instanceof jb) {
                return (jb)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel object, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, (Parcel)object, n3);
                }
                case 1598968902: {
                    object.writeString("com.google.android.gms.wallet.internal.IOwService");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                    object = parcel.readInt() != 0 ? (MaskedWalletRequest)MaskedWalletRequest.CREATOR.createFromParcel(parcel) : null;
                    Bundle bundle = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a((MaskedWalletRequest)object, bundle, je.a.aX(parcel.readStrongBinder()));
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                    object = parcel.readInt() != 0 ? (FullWalletRequest)FullWalletRequest.CREATOR.createFromParcel(parcel) : null;
                    Bundle bundle = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a((FullWalletRequest)object, bundle, je.a.aX(parcel.readStrongBinder()));
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    object = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a(string2, string3, (Bundle)object, je.a.aX(parcel.readStrongBinder()));
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                    object = parcel.readInt() != 0 ? (NotifyTransactionStatusRequest)NotifyTransactionStatusRequest.CREATOR.createFromParcel(parcel) : null;
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a((NotifyTransactionStatusRequest)object, (Bundle)parcel);
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                    object = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a((Bundle)object, je.a.aX(parcel.readStrongBinder()));
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                    object = parcel.readInt() != 0 ? (d)d.CREATOR.createFromParcel(parcel) : null;
                    Bundle bundle = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a((d)object, bundle, je.a.aX(parcel.readStrongBinder()));
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                    object = parcel.readInt() != 0 ? (MaskedWalletRequest)MaskedWalletRequest.CREATOR.createFromParcel(parcel) : null;
                    Bundle bundle = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a((MaskedWalletRequest)object, bundle, jd.a.aW(parcel.readStrongBinder()));
                    return true;
                }
                case 8: 
            }
            parcel.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
            object = parcel.readInt() != 0 ? (iv)iv.CREATOR.createFromParcel(parcel) : null;
            Bundle bundle = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
            this.a((iv)object, bundle, je.a.aX(parcel.readStrongBinder()));
            return true;
        }

        private static class a
        implements jb {
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
            public void a(Bundle bundle, je je2) throws RemoteException {
                Object var3_4 = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    bundle = var3_4;
                    if (je2 != null) {
                        bundle = je2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)bundle);
                    this.kn.transact(5, parcel, null, 1);
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
            public void a(iv iv2, Bundle bundle, je je2) throws RemoteException {
                Object var4_5 = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                    if (iv2 != null) {
                        parcel.writeInt(1);
                        iv2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    iv2 = var4_5;
                    if (je2 != null) {
                        iv2 = je2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)iv2);
                    this.kn.transact(8, parcel, null, 1);
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
            public void a(FullWalletRequest fullWalletRequest, Bundle bundle, je je2) throws RemoteException {
                Object var4_5 = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                    if (fullWalletRequest != null) {
                        parcel.writeInt(1);
                        fullWalletRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    fullWalletRequest = var4_5;
                    if (je2 != null) {
                        fullWalletRequest = je2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)fullWalletRequest);
                    this.kn.transact(2, parcel, null, 1);
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
            public void a(MaskedWalletRequest maskedWalletRequest, Bundle bundle, jd jd2) throws RemoteException {
                Object var4_5 = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                    if (maskedWalletRequest != null) {
                        parcel.writeInt(1);
                        maskedWalletRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    maskedWalletRequest = var4_5;
                    if (jd2 != null) {
                        maskedWalletRequest = jd2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)maskedWalletRequest);
                    this.kn.transact(7, parcel, null, 1);
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
            public void a(MaskedWalletRequest maskedWalletRequest, Bundle bundle, je je2) throws RemoteException {
                Object var4_5 = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                    if (maskedWalletRequest != null) {
                        parcel.writeInt(1);
                        maskedWalletRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    maskedWalletRequest = var4_5;
                    if (je2 != null) {
                        maskedWalletRequest = je2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)maskedWalletRequest);
                    this.kn.transact(1, parcel, null, 1);
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
            public void a(NotifyTransactionStatusRequest notifyTransactionStatusRequest, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                    if (notifyTransactionStatusRequest != null) {
                        parcel.writeInt(1);
                        notifyTransactionStatusRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(4, parcel, null, 1);
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
            public void a(d d2, Bundle bundle, je je2) throws RemoteException {
                Object var4_5 = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                    if (d2 != null) {
                        parcel.writeInt(1);
                        d2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    d2 = var4_5;
                    if (je2 != null) {
                        d2 = je2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)d2);
                    this.kn.transact(6, parcel, null, 1);
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
            public void a(String string2, String string3, Bundle bundle, je je2) throws RemoteException {
                Object var5_6 = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    string2 = var5_6;
                    if (je2 != null) {
                        string2 = je2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)string2);
                    this.kn.transact(3, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }
        }

    }

}

