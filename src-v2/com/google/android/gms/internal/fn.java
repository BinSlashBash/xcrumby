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
import com.google.android.gms.dynamic.d;

public interface fn
extends IInterface {
    public d a(d var1, int var2, int var3) throws RemoteException;

    public static abstract class com.google.android.gms.internal.fn$a
    extends Binder
    implements fn {
        public static fn D(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
            if (iInterface != null && iInterface instanceof fn) {
                return (fn)iInterface;
            }
            return new a(iBinder);
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
                    parcel.writeString("com.google.android.gms.common.internal.ISignInButtonCreator");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
            object = this.a(d.a.K(object.readStrongBinder()), object.readInt(), object.readInt());
            parcel.writeNoException();
            object = object != null ? object.asBinder() : null;
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class a
        implements fn {
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
            public d a(d d2, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
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

