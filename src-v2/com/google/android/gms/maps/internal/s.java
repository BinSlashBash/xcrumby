/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;

public interface s
extends IInterface {
    public void f(d var1) throws RemoteException;

    public void onSnapshotReady(Bitmap var1) throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.s$a
    extends Binder
    implements s {
        public com.google.android.gms.maps.internal.s$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.maps.internal.ISnapshotReadyCallback");
        }

        public static s aw(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
            if (iInterface != null && iInterface instanceof s) {
                return (s)iInterface;
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
                    parcel2.writeString("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                    parcel = parcel.readInt() != 0 ? (Bitmap)Bitmap.CREATOR.createFromParcel(parcel) : null;
                    this.onSnapshotReady((Bitmap)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
            this.f(d.a.K(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements s {
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
            public void f(d d2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
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
            public void onSnapshotReady(Bitmap bitmap) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                    if (bitmap != null) {
                        parcel.writeInt(1);
                        bitmap.writeToParcel(parcel, 0);
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

