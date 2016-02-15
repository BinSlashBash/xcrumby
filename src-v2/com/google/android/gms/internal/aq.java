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
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.bq;

public interface aq
extends IInterface {
    public IBinder a(d var1, ak var2, String var3, bq var4, int var5) throws RemoteException;

    public static abstract class com.google.android.gms.internal.aq$a
    extends Binder
    implements aq {
        public static aq g(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManagerCreator");
            if (iInterface != null && iInterface instanceof aq) {
                return (aq)iInterface;
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
                    parcel2.writeString("com.google.android.gms.ads.internal.client.IAdManagerCreator");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.google.android.gms.ads.internal.client.IAdManagerCreator");
            d d2 = d.a.K(parcel.readStrongBinder());
            ak ak2 = parcel.readInt() != 0 ? ak.CREATOR.b(parcel) : null;
            parcel = this.a(d2, ak2, parcel.readString(), bq.a.i(parcel.readStrongBinder()), parcel.readInt());
            parcel2.writeNoException();
            parcel2.writeStrongBinder((IBinder)parcel);
            return true;
        }

        private static class a
        implements aq {
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
            public IBinder a(d d2, ak ak2, String string2, bq bq2, int n2) throws RemoteException {
                Object var6_7 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManagerCreator");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    if (ak2 != null) {
                        parcel.writeInt(1);
                        ak2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    d2 = var6_7;
                    if (bq2 != null) {
                        d2 = bq2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)d2);
                    parcel.writeInt(n2);
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    d2 = parcel2.readStrongBinder();
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

