/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.plus.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.StatusCreator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;
import com.google.android.gms.internal.gg;
import com.google.android.gms.internal.gh;
import com.google.android.gms.internal.ih;
import com.google.android.gms.internal.ii;

public interface b
extends IInterface {
    public void Z(Status var1) throws RemoteException;

    public void a(int var1, Bundle var2, Bundle var3) throws RemoteException;

    public void a(int var1, Bundle var2, ParcelFileDescriptor var3) throws RemoteException;

    public void a(int var1, Bundle var2, gg var3) throws RemoteException;

    public void a(int var1, ih var2) throws RemoteException;

    public void a(DataHolder var1, String var2) throws RemoteException;

    public void a(DataHolder var1, String var2, String var3) throws RemoteException;

    public void be(String var1) throws RemoteException;

    public void bf(String var1) throws RemoteException;

    public void e(int var1, Bundle var2) throws RemoteException;

    public static abstract class com.google.android.gms.plus.internal.b$a
    extends Binder
    implements b {
        public com.google.android.gms.plus.internal.b$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.plus.internal.IPlusCallbacks");
        }

        public static b aO(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
            if (iInterface != null && iInterface instanceof b) {
                return (b)iInterface;
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
            void var5_29;
            gg gg2 = null;
            Object var7_6 = null;
            Object var8_7 = null;
            Object var9_8 = null;
            Object var5_9 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.plus.internal.IPlusCallbacks");
                    return true;
                }
                case 1: {
                    void var5_11;
                    parcel.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    n2 = parcel.readInt();
                    if (parcel.readInt() != 0) {
                        Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    } else {
                        Object var5_12 = null;
                    }
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a(n2, (Bundle)var5_11, (Bundle)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    void var5_14;
                    parcel.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    n2 = parcel.readInt();
                    if (parcel.readInt() != 0) {
                        Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    } else {
                        Object var5_15 = null;
                    }
                    parcel = parcel.readInt() != 0 ? (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                    this.a(n2, (Bundle)var5_14, (ParcelFileDescriptor)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    this.be(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    void var5_17;
                    parcel.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if (parcel.readInt() != 0) {
                        DataHolder dataHolder = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.a((DataHolder)var5_17, parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    void var5_19;
                    parcel.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    n2 = parcel.readInt();
                    if (parcel.readInt() != 0) {
                        Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    } else {
                        Object var5_20 = null;
                    }
                    if (parcel.readInt() != 0) {
                        gg2 = gg.CREATOR.x(parcel);
                    }
                    this.a(n2, (Bundle)var5_19, gg2);
                    parcel2.writeNoException();
                    return true;
                }
                case 6: {
                    void var5_23;
                    parcel.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    Object var5_21 = var7_6;
                    if (parcel.readInt() != 0) {
                        DataHolder dataHolder = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.a((DataHolder)var5_23, parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    n2 = parcel.readInt();
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.e(n2, (Bundle)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    this.bf(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 9: {
                    void var5_26;
                    parcel.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    n2 = parcel.readInt();
                    Object var5_24 = var8_7;
                    if (parcel.readInt() != 0) {
                        ih ih2 = ih.CREATOR.aN(parcel);
                    }
                    this.a(n2, (ih)var5_26);
                    parcel2.writeNoException();
                    return true;
                }
                case 10: 
            }
            parcel.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
            Object var5_27 = var9_8;
            if (parcel.readInt() != 0) {
                Status status = Status.CREATOR.createFromParcel(parcel);
            }
            this.Z((Status)var5_29);
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements b {
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
            public void Z(Status status) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if (status != null) {
                        parcel.writeInt(1);
                        status.writeToParcel(parcel, 0);
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
            public void a(int n2, Bundle bundle, Bundle bundle2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle2 != null) {
                        parcel.writeInt(1);
                        bundle2.writeToParcel(parcel, 0);
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
            public void a(int n2, Bundle bundle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
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
            public void a(int n2, Bundle bundle, gg gg2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (gg2 != null) {
                        parcel.writeInt(1);
                        gg2.writeToParcel(parcel, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(int n2, ih ih2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    parcel.writeInt(n2);
                    if (ih2 != null) {
                        parcel.writeInt(1);
                        ih2.writeToParcel(parcel, 0);
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
            public void a(DataHolder dataHolder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    this.kn.transact(4, parcel, parcel2, 0);
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
            public void a(DataHolder dataHolder, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(6, parcel, parcel2, 0);
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
            public void be(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    parcel.writeString(string2);
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
            public void bf(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(8, parcel, parcel2, 0);
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
            public void e(int n2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(7, parcel, parcel2, 0);
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

