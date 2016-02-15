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
import com.google.android.gms.internal.br;

public interface bq
extends IInterface {
    public br m(String var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.bq$a
    extends Binder
    implements bq {
        public com.google.android.gms.internal.bq$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.ads.internal.mediation.client.IAdapterCreator");
        }

        public static bq i(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.IAdapterCreator");
            if (iInterface != null && iInterface instanceof bq) {
                return (bq)iInterface;
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
                    parcel.writeString("com.google.android.gms.ads.internal.mediation.client.IAdapterCreator");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IAdapterCreator");
            object = this.m(object.readString());
            parcel.writeNoException();
            object = object != null ? object.asBinder() : null;
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class a
        implements bq {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public br m(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IAdapterCreator");
                    parcel.writeString((String)object);
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    object = br.a.j(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

