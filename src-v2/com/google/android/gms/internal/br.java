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
import com.google.android.gms.internal.bs;

public interface br
extends IInterface {
    public void a(d var1, ah var2, String var3, bs var4) throws RemoteException;

    public void a(d var1, ah var2, String var3, String var4, bs var5) throws RemoteException;

    public void a(d var1, ak var2, ah var3, String var4, bs var5) throws RemoteException;

    public void a(d var1, ak var2, ah var3, String var4, String var5, bs var6) throws RemoteException;

    public void destroy() throws RemoteException;

    public d getView() throws RemoteException;

    public void pause() throws RemoteException;

    public void resume() throws RemoteException;

    public void showInterstitial() throws RemoteException;

    public static abstract class com.google.android.gms.internal.br$a
    extends Binder
    implements br {
        public com.google.android.gms.internal.br$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
        }

        public static br j(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
            if (iInterface != null && iInterface instanceof br) {
                return (br)iInterface;
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
            Object object2 = null;
            Object object3 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    d d2 = d.a.K(object.readStrongBinder());
                    object3 = object.readInt() != 0 ? ak.CREATOR.b((Parcel)object) : null;
                    object2 = object.readInt() != 0 ? ah.CREATOR.a((Parcel)object) : null;
                    this.a(d2, (ak)object3, (ah)object2, object.readString(), bs.a.k(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    object2 = this.getView();
                    parcel.writeNoException();
                    object = object3;
                    if (object2 != null) {
                        object = object2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    d d3 = d.a.K(object.readStrongBinder());
                    object3 = object2;
                    if (object.readInt() != 0) {
                        object3 = ah.CREATOR.a((Parcel)object);
                    }
                    this.a(d3, (ah)object3, object.readString(), bs.a.k(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    this.showInterstitial();
                    parcel.writeNoException();
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    this.destroy();
                    parcel.writeNoException();
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    d d4 = d.a.K(object.readStrongBinder());
                    object3 = object.readInt() != 0 ? ak.CREATOR.b((Parcel)object) : null;
                    object2 = object.readInt() != 0 ? ah.CREATOR.a((Parcel)object) : null;
                    this.a(d4, (ak)object3, (ah)object2, object.readString(), object.readString(), bs.a.k(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    object2 = d.a.K(object.readStrongBinder());
                    object3 = object.readInt() != 0 ? ah.CREATOR.a((Parcel)object) : null;
                    this.a((d)object2, (ah)object3, object.readString(), object.readString(), bs.a.k(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    this.pause();
                    parcel.writeNoException();
                    return true;
                }
                case 9: 
            }
            object.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
            this.resume();
            parcel.writeNoException();
            return true;
        }

        private static class a
        implements br {
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
            public void a(d d2, ah ah2, String string2, bs bs2) throws RemoteException {
                Object var5_6 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    if (ah2 != null) {
                        parcel.writeInt(1);
                        ah2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    d2 = var5_6;
                    if (bs2 != null) {
                        d2 = bs2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)d2);
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
            public void a(d d2, ah ah2, String string2, String string3, bs bs2) throws RemoteException {
                Object var6_7 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    if (ah2 != null) {
                        parcel.writeInt(1);
                        ah2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    d2 = var6_7;
                    if (bs2 != null) {
                        d2 = bs2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)d2);
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
            public void a(d d2, ak ak2, ah ah2, String string2, bs bs2) throws RemoteException {
                Object var6_7 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    if (ak2 != null) {
                        parcel.writeInt(1);
                        ak2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (ah2 != null) {
                        parcel.writeInt(1);
                        ah2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    d2 = var6_7;
                    if (bs2 != null) {
                        d2 = bs2.asBinder();
                    }
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
            public void a(d d2, ak ak2, ah ah2, String string2, String string3, bs bs2) throws RemoteException {
                Object var7_8 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    if (ak2 != null) {
                        parcel.writeInt(1);
                        ak2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (ah2 != null) {
                        parcel.writeInt(1);
                        ah2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    d2 = var7_8;
                    if (bs2 != null) {
                        d2 = bs2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)d2);
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

            @Override
            public void destroy() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
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
            public d getView() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    this.kn.transact(2, parcel, parcel2, 0);
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
            public void pause() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
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
            public void resume() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
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
            public void showInterstitial() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    this.kn.transact(4, parcel, parcel2, 0);
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

