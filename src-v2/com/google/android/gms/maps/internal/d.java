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
import com.google.android.gms.dynamic.d;
import com.google.android.gms.maps.model.internal.f;

public interface d
extends IInterface {
    public com.google.android.gms.dynamic.d f(f var1) throws RemoteException;

    public com.google.android.gms.dynamic.d g(f var1) throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.d$a
    extends Binder
    implements d {
        public com.google.android.gms.maps.internal.d$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.maps.internal.IInfoWindowAdapter");
        }

        public static d ad(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
            if (iInterface != null && iInterface instanceof d) {
                return (d)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            com.google.android.gms.dynamic.d d2 = null;
            com.google.android.gms.dynamic.d d3 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    d2 = this.f(f.a.aG(object.readStrongBinder()));
                    parcel.writeNoException();
                    object = d3;
                    if (d2 != null) {
                        object = d2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 2: 
            }
            object.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
            d3 = this.g(f.a.aG(object.readStrongBinder()));
            parcel.writeNoException();
            object = d2;
            if (d3 != null) {
                object = d3.asBinder();
            }
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class a
        implements d {
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
            public com.google.android.gms.dynamic.d f(f object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_3;
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    if (object != null) {
                        IBinder iBinder = object.asBinder();
                    } else {
                        Object var1_5 = null;
                    }
                    parcel.writeStrongBinder((IBinder)var1_3);
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    com.google.android.gms.dynamic.d d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
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
            public com.google.android.gms.dynamic.d g(f object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_3;
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    if (object != null) {
                        IBinder iBinder = object.asBinder();
                    } else {
                        Object var1_5 = null;
                    }
                    parcel.writeStrongBinder((IBinder)var1_3);
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    com.google.android.gms.dynamic.d d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

