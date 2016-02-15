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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.cy;
import com.google.android.gms.internal.cz;
import com.google.android.gms.internal.da;

public interface db
extends IInterface {
    public cz b(cx var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.db$a
    extends Binder
    implements db {
        public com.google.android.gms.internal.db$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.ads.internal.request.IAdRequestService");
        }

        public static db s(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.request.IAdRequestService");
            if (iInterface != null && iInterface instanceof db) {
                return (db)iInterface;
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
                    parcel.writeString("com.google.android.gms.ads.internal.request.IAdRequestService");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.google.android.gms.ads.internal.request.IAdRequestService");
            object = object.readInt() != 0 ? cx.CREATOR.f((Parcel)object) : null;
            object = this.b((cx)object);
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                object.writeToParcel(parcel, 1);
                return true;
            }
            parcel.writeInt(0);
            return true;
        }

        private static class a
        implements db {
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
            public cz b(cx safeParcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.request.IAdRequestService");
                    if (safeParcelable != null) {
                        parcel.writeInt(1);
                        safeParcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    safeParcelable = parcel2.readInt() != 0 ? cz.CREATOR.g(parcel2) : null;
                    return safeParcelable;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

