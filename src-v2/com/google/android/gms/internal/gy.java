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
import com.google.android.gms.identity.intents.UserAddressRequest;
import com.google.android.gms.internal.gx;

public interface gy
extends IInterface {
    public void a(gx var1, UserAddressRequest var2, Bundle var3) throws RemoteException;

    public static abstract class com.google.android.gms.internal.gy$a
    extends Binder
    implements gy {
        public static gy T(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.identity.intents.internal.IAddressService");
            if (iInterface != null && iInterface instanceof gy) {
                return (gy)iInterface;
            }
            return new a(iBinder);
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
                    parcel2.writeString("com.google.android.gms.identity.intents.internal.IAddressService");
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.google.android.gms.identity.intents.internal.IAddressService");
            gx gx2 = gx.a.S(parcel.readStrongBinder());
            UserAddressRequest userAddressRequest = parcel.readInt() != 0 ? (UserAddressRequest)UserAddressRequest.CREATOR.createFromParcel(parcel) : null;
            parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
            this.a(gx2, userAddressRequest, (Bundle)parcel);
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements gy {
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
            public void a(gx gx2, UserAddressRequest userAddressRequest, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.identity.intents.internal.IAddressService");
                    gx2 = gx2 != null ? gx2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)gx2);
                    if (userAddressRequest != null) {
                        parcel.writeInt(1);
                        userAddressRequest.writeToParcel(parcel, 0);
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

            public IBinder asBinder() {
                return this.kn;
            }
        }

    }

}

