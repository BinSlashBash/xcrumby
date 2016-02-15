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
package com.google.android.gms.plus.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;

public interface c
extends IInterface {
    public d a(d var1, int var2, int var3, String var4, int var5) throws RemoteException;

    public d a(d var1, int var2, int var3, String var4, String var5) throws RemoteException;

    public static abstract class com.google.android.gms.plus.internal.c$a
    extends Binder
    implements c {
        public static c aP(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
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
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    object = this.a(d.a.K(object.readStrongBinder()), object.readInt(), object.readInt(), object.readString(), object.readInt());
                    parcel.writeNoException();
                    object = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 2: 
            }
            object.enforceInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
            d d2 = this.a(d.a.K(object.readStrongBinder()), object.readInt(), object.readInt(), object.readString(), object.readString());
            parcel.writeNoException();
            object = var5_5;
            if (d2 != null) {
                object = d2.asBinder();
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
            public d a(d d2, int n2, int n3, String string2, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    parcel.writeInt(n4);
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    d2 = d.a.K(parcel2.readStrongBinder());
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
            public d a(d d2, int n2, int n3, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
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

