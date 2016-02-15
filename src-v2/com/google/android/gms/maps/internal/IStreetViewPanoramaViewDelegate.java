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
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;

public interface IStreetViewPanoramaViewDelegate
extends IInterface {
    public IStreetViewPanoramaDelegate getStreetViewPanorama() throws RemoteException;

    public d getView() throws RemoteException;

    public void onCreate(Bundle var1) throws RemoteException;

    public void onDestroy() throws RemoteException;

    public void onLowMemory() throws RemoteException;

    public void onPause() throws RemoteException;

    public void onResume() throws RemoteException;

    public void onSaveInstanceState(Bundle var1) throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate$a
    extends Binder
    implements IStreetViewPanoramaViewDelegate {
        public static IStreetViewPanoramaViewDelegate az(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
            if (iInterface != null && iInterface instanceof IStreetViewPanoramaViewDelegate) {
                return (IStreetViewPanoramaViewDelegate)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            d d2 = null;
            d d3 = null;
            IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate = null;
            d d4 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    iStreetViewPanoramaDelegate = this.getStreetViewPanorama();
                    parcel.writeNoException();
                    object = d4;
                    if (iStreetViewPanoramaDelegate != null) {
                        object = iStreetViewPanoramaDelegate.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    d4 = d2;
                    if (object.readInt() != 0) {
                        d4 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.onCreate((Bundle)d4);
                    parcel.writeNoException();
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    this.onResume();
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    this.onPause();
                    parcel.writeNoException();
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    this.onDestroy();
                    parcel.writeNoException();
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    this.onLowMemory();
                    parcel.writeNoException();
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    d4 = d3;
                    if (object.readInt() != 0) {
                        d4 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.onSaveInstanceState((Bundle)d4);
                    parcel.writeNoException();
                    if (d4 != null) {
                        parcel.writeInt(1);
                        d4.writeToParcel(parcel, 1);
                        do {
                            return true;
                            break;
                        } while (true);
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 8: 
            }
            object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
            d4 = this.getView();
            parcel.writeNoException();
            object = iStreetViewPanoramaDelegate;
            if (d4 != null) {
                object = d4.asBinder();
            }
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class a
        implements IStreetViewPanoramaViewDelegate {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public IStreetViewPanoramaDelegate getStreetViewPanorama() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate = IStreetViewPanoramaDelegate.a.ax(parcel2.readStrongBinder());
                    return iStreetViewPanoramaDelegate;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public d getView() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    this.kn.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    d d2 = d.a.K(parcel2.readStrongBinder());
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
            public void onCreate(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
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
            public void onDestroy() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    this.kn.transact(5, parcel, parcel2, 0);
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
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
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
            public void onPause() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    this.kn.transact(4, parcel, parcel2, 0);
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
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
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
            public void onSaveInstanceState(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(7, parcel, parcel2, 0);
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

