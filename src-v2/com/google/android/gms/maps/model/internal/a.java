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
package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;

public interface a
extends IInterface {
    public d b(Bitmap var1) throws RemoteException;

    public d bK(int var1) throws RemoteException;

    public d ba(String var1) throws RemoteException;

    public d bb(String var1) throws RemoteException;

    public d bc(String var1) throws RemoteException;

    public d c(float var1) throws RemoteException;

    public d iH() throws RemoteException;

    public static abstract class com.google.android.gms.maps.model.internal.a$a
    extends Binder
    implements com.google.android.gms.maps.model.internal.a {
        public static com.google.android.gms.maps.model.internal.a aB(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
            if (iInterface != null && iInterface instanceof com.google.android.gms.maps.model.internal.a) {
                return (com.google.android.gms.maps.model.internal.a)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            Object var7_5 = null;
            Object var8_6 = null;
            Object var9_7 = null;
            d d2 = null;
            Object var10_9 = null;
            d d3 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    object = this.bK(object.readInt());
                    parcel.writeNoException();
                    object = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    d2 = this.ba(object.readString());
                    parcel.writeNoException();
                    object = d3;
                    if (d2 != null) {
                        object = d2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    d2 = this.bb(object.readString());
                    parcel.writeNoException();
                    object = var7_5;
                    if (d2 != null) {
                        object = d2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    d2 = this.iH();
                    parcel.writeNoException();
                    object = var8_6;
                    if (d2 != null) {
                        object = d2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    d2 = this.c(object.readFloat());
                    parcel.writeNoException();
                    object = var9_7;
                    if (d2 != null) {
                        object = d2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    object = object.readInt() != 0 ? (Bitmap)Bitmap.CREATOR.createFromParcel((Parcel)object) : null;
                    d3 = this.b((Bitmap)object);
                    parcel.writeNoException();
                    object = d2;
                    if (d3 != null) {
                        object = d3.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 7: 
            }
            object.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
            d2 = this.bc(object.readString());
            parcel.writeNoException();
            object = var10_9;
            if (d2 != null) {
                object = d2.asBinder();
            }
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class a
        implements com.google.android.gms.maps.model.internal.a {
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
            public d b(Bitmap object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(6, parcel, parcel2, 0);
                    parcel2.readException();
                    object = d.a.K(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public d bK(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    parcel.writeInt(n2);
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    d d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public d ba(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    parcel.writeString((String)object);
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    object = d.a.K(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public d bb(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    parcel.writeString((String)object);
                    this.kn.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    object = d.a.K(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public d bc(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    parcel.writeString((String)object);
                    this.kn.transact(7, parcel, parcel2, 0);
                    parcel2.readException();
                    object = d.a.K(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public d c(float f2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    parcel.writeFloat(f2);
                    this.kn.transact(5, parcel, parcel2, 0);
                    parcel2.readException();
                    d d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public d iH() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    this.kn.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    d d2 = d.a.K(parcel2.readStrongBinder());
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

