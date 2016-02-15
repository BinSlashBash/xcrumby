/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.GoogleMapOptionsCreator;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;

public interface IMapFragmentDelegate
extends IInterface {
    public IGoogleMapDelegate getMap() throws RemoteException;

    public boolean isReady() throws RemoteException;

    public void onCreate(Bundle var1) throws RemoteException;

    public d onCreateView(d var1, d var2, Bundle var3) throws RemoteException;

    public void onDestroy() throws RemoteException;

    public void onDestroyView() throws RemoteException;

    public void onInflate(d var1, GoogleMapOptions var2, Bundle var3) throws RemoteException;

    public void onLowMemory() throws RemoteException;

    public void onPause() throws RemoteException;

    public void onResume() throws RemoteException;

    public void onSaveInstanceState(Bundle var1) throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.IMapFragmentDelegate$a
    extends Binder
    implements IMapFragmentDelegate {
        public static IMapFragmentDelegate af(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
            if (iInterface != null && iInterface instanceof IMapFragmentDelegate) {
                return (IMapFragmentDelegate)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            GoogleMapOptions googleMapOptions = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    object = this.getMap();
                    parcel.writeNoException();
                    object = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    d d2 = d.a.K(object.readStrongBinder());
                    googleMapOptions = object.readInt() != 0 ? GoogleMapOptions.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onInflate(d2, googleMapOptions, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onCreate((Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    d d3 = d.a.K(object.readStrongBinder());
                    d d4 = d.a.K(object.readStrongBinder());
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    d3 = this.onCreateView(d3, d4, (Bundle)object);
                    parcel.writeNoException();
                    object = googleMapOptions;
                    if (d3 != null) {
                        object = d3.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.onResume();
                    parcel.writeNoException();
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.onPause();
                    parcel.writeNoException();
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.onDestroyView();
                    parcel.writeNoException();
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.onDestroy();
                    parcel.writeNoException();
                    return true;
                }
                case 9: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.onLowMemory();
                    parcel.writeNoException();
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onSaveInstanceState((Bundle)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 11: 
            }
            object.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
            boolean bl2 = this.isReady();
            parcel.writeNoException();
            n2 = bl2 ? 1 : 0;
            parcel.writeInt(n2);
            return true;
        }

        private static class a
        implements IMapFragmentDelegate {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public IGoogleMapDelegate getMap() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    IGoogleMapDelegate iGoogleMapDelegate = IGoogleMapDelegate.a.ac(parcel2.readStrongBinder());
                    return iGoogleMapDelegate;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isReady() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                        this.kn.transact(11, parcel, parcel2, 0);
                        parcel2.readException();
                        int n2 = parcel2.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable var5_5) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw var5_5;
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                return bl2;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCreate(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(3, parcel, parcel2, 0);
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
            public d onCreateView(d d2, d d3, Bundle bundle) throws RemoteException {
                Object var4_5 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    d2 = var4_5;
                    if (d3 != null) {
                        d2 = d3.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)d2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onDestroy() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.kn.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onDestroyView() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.kn.transact(7, parcel, parcel2, 0);
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
            public void onInflate(d d2, GoogleMapOptions googleMapOptions, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    if (googleMapOptions != null) {
                        parcel.writeInt(1);
                        googleMapOptions.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onLowMemory() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.kn.transact(9, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onPause() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.kn.transact(6, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onResume() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.kn.transact(5, parcel, parcel2, 0);
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
            public void onSaveInstanceState(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(10, parcel, parcel2, 0);
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        bundle.readFromParcel(parcel2);
                    }
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

