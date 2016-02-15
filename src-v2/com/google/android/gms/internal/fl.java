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

public interface fl
extends IInterface {
    public void b(int var1, IBinder var2, Bundle var3) throws RemoteException;

    public static abstract class com.google.android.gms.internal.fl$a
    extends Binder
    implements fl {
        public com.google.android.gms.internal.fl$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.common.internal.IGmsCallbacks");
        }

        public static fl B(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsCallbacks");
            if (iInterface != null && iInterface instanceof fl) {
                return (fl)iInterface;
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
                    parcel2.writeString("com.google.android.gms.common.internal.IGmsCallbacks");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.google.android.gms.common.internal.IGmsCallbacks");
            n2 = parcel.readInt();
            IBinder iBinder = parcel.readStrongBinder();
            parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
            this.b(n2, iBinder, (Bundle)parcel);
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements fl {
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
            public void b(int n2, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeStrongBinder(iBinder);
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
        }

    }

}

