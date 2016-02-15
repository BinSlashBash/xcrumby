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
package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.model.internal.f;

public interface l
extends IInterface {
    public boolean a(f var1) throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.l$a
    extends Binder
    implements l {
        public com.google.android.gms.maps.internal.l$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.maps.internal.IOnMarkerClickListener");
        }

        public static l ao(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMarkerClickListener");
            if (iInterface != null && iInterface instanceof l) {
                return (l)iInterface;
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
                    parcel2.writeString("com.google.android.gms.maps.internal.IOnMarkerClickListener");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.google.android.gms.maps.internal.IOnMarkerClickListener");
            boolean bl2 = this.a(f.a.aG(parcel.readStrongBinder()));
            parcel2.writeNoException();
            n2 = bl2 ? 1 : 0;
            parcel2.writeInt(n2);
            return true;
        }

        private static class a
        implements l {
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
            public boolean a(f f2) throws RemoteException {
                boolean bl2 = true;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMarkerClickListener");
                    f2 = f2 != null ? f2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)f2);
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    if (n2 == 0) {
                        bl2 = false;
                    }
                    return bl2;
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

