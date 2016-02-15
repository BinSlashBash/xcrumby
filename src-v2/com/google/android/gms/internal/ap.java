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
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ai;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.ao;
import com.google.android.gms.internal.ar;
import com.google.android.gms.internal.co;

public interface ap
extends IInterface {
    public d Q() throws RemoteException;

    public ak R() throws RemoteException;

    public void a(ak var1) throws RemoteException;

    public void a(ao var1) throws RemoteException;

    public void a(ar var1) throws RemoteException;

    public void a(co var1) throws RemoteException;

    public boolean a(ah var1) throws RemoteException;

    public void ac() throws RemoteException;

    public void destroy() throws RemoteException;

    public boolean isReady() throws RemoteException;

    public void pause() throws RemoteException;

    public void resume() throws RemoteException;

    public void showInterstitial() throws RemoteException;

    public void stopLoading() throws RemoteException;

    public static abstract class com.google.android.gms.internal.ap$a
    extends Binder
    implements ap {
        public com.google.android.gms.internal.ap$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.ads.internal.client.IAdManager");
        }

        public static ap f(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManager");
            if (iInterface != null && iInterface instanceof ap) {
                return (ap)iInterface;
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
            d d2 = null;
            d d3 = null;
            Object object2 = null;
            int n4 = 0;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.ads.internal.client.IAdManager");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    d2 = this.Q();
                    parcel.writeNoException();
                    object = object2;
                    if (d2 != null) {
                        object = d2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    this.destroy();
                    parcel.writeNoException();
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    boolean bl2 = this.isReady();
                    parcel.writeNoException();
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    object2 = d2;
                    if (object.readInt() != 0) {
                        object2 = ah.CREATOR.a((Parcel)object);
                    }
                    boolean bl3 = this.a((ah)object2);
                    parcel.writeNoException();
                    n2 = n4;
                    if (bl3) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    this.pause();
                    parcel.writeNoException();
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    this.resume();
                    parcel.writeNoException();
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    this.a(ao.a.e(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    this.a(ar.a.h(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 9: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    this.showInterstitial();
                    parcel.writeNoException();
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    this.stopLoading();
                    parcel.writeNoException();
                    return true;
                }
                case 11: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    this.ac();
                    parcel.writeNoException();
                    return true;
                }
                case 12: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    object = this.R();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 13: {
                    object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
                    object2 = d3;
                    if (object.readInt() != 0) {
                        object2 = ak.CREATOR.b((Parcel)object);
                    }
                    this.a((ak)object2);
                    parcel.writeNoException();
                    return true;
                }
                case 14: 
            }
            object.enforceInterface("com.google.android.gms.ads.internal.client.IAdManager");
            this.a(co.a.p(object.readStrongBinder()));
            parcel.writeNoException();
            return true;
        }

        private static class a
        implements ap {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            @Override
            public d Q() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ak R() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
                    this.kn.transact(12, parcel, parcel2, 0);
                    parcel2.readException();
                    ak ak2 = parcel2.readInt() != 0 ? ak.CREATOR.b(parcel2) : null;
                    return ak2;
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
            public void a(ak ak2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
                    if (ak2 != null) {
                        parcel.writeInt(1);
                        ak2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(13, parcel, parcel2, 0);
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
            public void a(ao ao2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
                    ao2 = ao2 != null ? ao2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ao2);
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
            public void a(ar ar2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
                    ar2 = ar2 != null ? ar2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ar2);
                    this.kn.transact(8, parcel, parcel2, 0);
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
            public void a(co co2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
                    co2 = co2 != null ? co2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)co2);
                    this.kn.transact(14, parcel, parcel2, 0);
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
            public boolean a(ah ah2) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block6 : {
                    block5 : {
                        bl2 = true;
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        try {
                            parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
                            if (ah2 != null) {
                                parcel.writeInt(1);
                                ah2.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            this.kn.transact(4, parcel, parcel2, 0);
                            parcel2.readException();
                            int n2 = parcel2.readInt();
                            if (n2 == 0) break block5;
                            break block6;
                        }
                        catch (Throwable var1_2) {
                            parcel2.recycle();
                            parcel.recycle();
                            throw var1_2;
                        }
                    }
                    bl2 = false;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl2;
            }

            @Override
            public void ac() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
                    this.kn.transact(11, parcel, parcel2, 0);
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

            @Override
            public void destroy() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
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
            public boolean isReady() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
                        this.kn.transact(3, parcel, parcel2, 0);
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

            @Override
            public void pause() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
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
            public void resume() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
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
            public void showInterstitial() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
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
            public void stopLoading() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManager");
                    this.kn.transact(10, parcel, parcel2, 0);
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

