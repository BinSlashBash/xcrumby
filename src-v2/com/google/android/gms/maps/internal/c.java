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
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.GoogleMapOptionsCreator;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptionsCreator;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.model.internal.a;

public interface c
extends IInterface {
    public IMapViewDelegate a(d var1, GoogleMapOptions var2) throws RemoteException;

    public IStreetViewPanoramaViewDelegate a(d var1, StreetViewPanoramaOptions var2) throws RemoteException;

    public void a(d var1, int var2) throws RemoteException;

    public void g(d var1) throws RemoteException;

    public IMapFragmentDelegate h(d var1) throws RemoteException;

    public IStreetViewPanoramaFragmentDelegate i(d var1) throws RemoteException;

    public ICameraUpdateFactoryDelegate ix() throws RemoteException;

    public com.google.android.gms.maps.model.internal.a iy() throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.c$a
    extends Binder
    implements c {
        public static c ab(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
            if (iInterface != null && iInterface instanceof c) {
                return (c)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            Object var5_5 = null;
            Object var8_12 = null;
            Object var9_13 = null;
            Object var6_14 = null;
            Object var10_17 = null;
            Object var7_18 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.internal.ICreator");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    this.g(d.a.K(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    IMapFragmentDelegate iMapFragmentDelegate = this.h(d.a.K(object.readStrongBinder()));
                    parcel.writeNoException();
                    object = var7_18;
                    if (iMapFragmentDelegate != null) {
                        object = iMapFragmentDelegate.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    d d2 = d.a.K(object.readStrongBinder());
                    object = object.readInt() != 0 ? GoogleMapOptions.CREATOR.createFromParcel((Parcel)object) : null;
                    IMapViewDelegate iMapViewDelegate = this.a(d2, (GoogleMapOptions)object);
                    parcel.writeNoException();
                    object = var5_5;
                    if (iMapViewDelegate != null) {
                        object = iMapViewDelegate.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegate = this.ix();
                    parcel.writeNoException();
                    object = var8_12;
                    if (iCameraUpdateFactoryDelegate != null) {
                        object = iCameraUpdateFactoryDelegate.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    com.google.android.gms.maps.model.internal.a a2 = this.iy();
                    parcel.writeNoException();
                    object = var9_13;
                    if (a2 != null) {
                        object = a2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    this.a(d.a.K(object.readStrongBinder()), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    d d3 = d.a.K(object.readStrongBinder());
                    object = object.readInt() != 0 ? StreetViewPanoramaOptions.CREATOR.createFromParcel((Parcel)object) : null;
                    IStreetViewPanoramaViewDelegate iStreetViewPanoramaViewDelegate = this.a(d3, (StreetViewPanoramaOptions)object);
                    parcel.writeNoException();
                    object = var6_14;
                    if (iStreetViewPanoramaViewDelegate != null) {
                        object = iStreetViewPanoramaViewDelegate.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 8: 
            }
            object.enforceInterface("com.google.android.gms.maps.internal.ICreator");
            IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate = this.i(d.a.K(object.readStrongBinder()));
            parcel.writeNoException();
            object = var10_17;
            if (iStreetViewPanoramaFragmentDelegate != null) {
                object = iStreetViewPanoramaFragmentDelegate.asBinder();
            }
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class a
        implements c {
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
            public IMapViewDelegate a(d object, GoogleMapOptions googleMapOptions) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_3;
                    void var2_8;
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    if (object != null) {
                        IBinder iBinder = object.asBinder();
                    } else {
                        Object var1_5 = null;
                    }
                    parcel.writeStrongBinder((IBinder)var1_3);
                    if (var2_8 != null) {
                        parcel.writeInt(1);
                        var2_8.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    IMapViewDelegate iMapViewDelegate = IMapViewDelegate.a.ag(parcel2.readStrongBinder());
                    return iMapViewDelegate;
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
            public IStreetViewPanoramaViewDelegate a(d object, StreetViewPanoramaOptions streetViewPanoramaOptions) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_3;
                    void var2_8;
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    if (object != null) {
                        IBinder iBinder = object.asBinder();
                    } else {
                        Object var1_5 = null;
                    }
                    parcel.writeStrongBinder((IBinder)var1_3);
                    if (var2_8 != null) {
                        parcel.writeInt(1);
                        var2_8.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(7, parcel, parcel2, 0);
                    parcel2.readException();
                    IStreetViewPanoramaViewDelegate iStreetViewPanoramaViewDelegate = IStreetViewPanoramaViewDelegate.a.az(parcel2.readStrongBinder());
                    return iStreetViewPanoramaViewDelegate;
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
            public void a(d d2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    parcel.writeInt(n2);
                    this.kn.transact(6, parcel, parcel2, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void g(d d2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    this.kn.transact(1, parcel, parcel2, 0);
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
            public IMapFragmentDelegate h(d object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_3;
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    if (object != null) {
                        IBinder iBinder = object.asBinder();
                    } else {
                        Object var1_5 = null;
                    }
                    parcel.writeStrongBinder((IBinder)var1_3);
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    IMapFragmentDelegate iMapFragmentDelegate = IMapFragmentDelegate.a.af(parcel2.readStrongBinder());
                    return iMapFragmentDelegate;
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
            public IStreetViewPanoramaFragmentDelegate i(d object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_3;
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    if (object != null) {
                        IBinder iBinder = object.asBinder();
                    } else {
                        Object var1_5 = null;
                    }
                    parcel.writeStrongBinder((IBinder)var1_3);
                    this.kn.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate = IStreetViewPanoramaFragmentDelegate.a.ay(parcel2.readStrongBinder());
                    return iStreetViewPanoramaFragmentDelegate;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ICameraUpdateFactoryDelegate ix() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    this.kn.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegate = ICameraUpdateFactoryDelegate.a.Z(parcel2.readStrongBinder());
                    return iCameraUpdateFactoryDelegate;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public com.google.android.gms.maps.model.internal.a iy() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    this.kn.transact(5, parcel, parcel2, 0);
                    parcel2.readException();
                    com.google.android.gms.maps.model.internal.a a2 = a.a.aB(parcel2.readStrongBinder());
                    return a2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

