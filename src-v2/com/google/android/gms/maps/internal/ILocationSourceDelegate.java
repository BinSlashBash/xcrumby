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
import com.google.android.gms.maps.internal.h;

public interface ILocationSourceDelegate
extends IInterface {
    public void activate(h var1) throws RemoteException;

    public void deactivate() throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.ILocationSourceDelegate$a
    extends Binder
    implements ILocationSourceDelegate {
        public com.google.android.gms.maps.internal.ILocationSourceDelegate$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.maps.internal.ILocationSourceDelegate");
        }

        public static ILocationSourceDelegate ae(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ILocationSourceDelegate");
            if (iInterface != null && iInterface instanceof ILocationSourceDelegate) {
                return (ILocationSourceDelegate)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.maps.internal.ILocationSourceDelegate");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.ILocationSourceDelegate");
                    this.activate(h.a.ak(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.google.android.gms.maps.internal.ILocationSourceDelegate");
            this.deactivate();
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements ILocationSourceDelegate {
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
            public void activate(h h2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ILocationSourceDelegate");
                    h2 = h2 != null ? h2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)h2);
                    this.kn.transact(1, parcel, parcel2, 0);
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
            public void deactivate() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.ILocationSourceDelegate");
                    this.kn.transact(2, parcel, parcel2, 0);
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

