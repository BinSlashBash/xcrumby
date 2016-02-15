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
package com.google.android.gms.drive.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.drive.internal.OnEventResponse;

public interface w
extends IInterface {
    public void a(OnEventResponse var1) throws RemoteException;

    public static abstract class com.google.android.gms.drive.internal.w$a
    extends Binder
    implements w {
        public com.google.android.gms.drive.internal.w$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.drive.internal.IEventCallback");
        }

        public static w I(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.internal.IEventCallback");
            if (iInterface != null && iInterface instanceof w) {
                return (w)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.drive.internal.IEventCallback");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.google.android.gms.drive.internal.IEventCallback");
            object = object.readInt() != 0 ? (OnEventResponse)OnEventResponse.CREATOR.createFromParcel((Parcel)object) : null;
            this.a((OnEventResponse)object);
            parcel.writeNoException();
            return true;
        }

        private static class a
        implements w {
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
            public void a(OnEventResponse onEventResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IEventCallback");
                    if (onEventResponse != null) {
                        parcel.writeInt(1);
                        onEventResponse.writeToParcel(parcel, 0);
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
        }

    }

}

