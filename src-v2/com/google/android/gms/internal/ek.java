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
import com.google.android.gms.internal.ej;

public interface ek
extends IInterface {
    public void a(ej var1) throws RemoteException;

    public void a(ej var1, int var2) throws RemoteException;

    public void a(ej var1, int var2, String var3, byte[] var4) throws RemoteException;

    public void a(ej var1, int var2, byte[] var3) throws RemoteException;

    public void b(ej var1) throws RemoteException;

    public void b(ej var1, int var2) throws RemoteException;

    public void c(ej var1) throws RemoteException;

    public int dv() throws RemoteException;

    public int dw() throws RemoteException;

    public static abstract class com.google.android.gms.internal.ek$a
    extends Binder
    implements ek {
        public static ek w(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.appstate.internal.IAppStateService");
            if (iInterface != null && iInterface instanceof ek) {
                return (ek)iInterface;
            }
            return new a(iBinder);
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.appstate.internal.IAppStateService");
                    return true;
                }
                case 5001: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    n2 = this.dv();
                    parcel2.writeNoException();
                    parcel2.writeInt(n2);
                    return true;
                }
                case 5002: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    n2 = this.dw();
                    parcel2.writeNoException();
                    parcel2.writeInt(n2);
                    return true;
                }
                case 5003: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.a(ej.a.v(parcel.readStrongBinder()), parcel.readInt(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 5004: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.a(ej.a.v(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 5005: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.a(ej.a.v(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 5006: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.a(ej.a.v(parcel.readStrongBinder()), parcel.readInt(), parcel.readString(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 5007: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.b(ej.a.v(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 5008: {
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    this.b(ej.a.v(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 5009: 
            }
            parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
            this.c(ej.a.v(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements ek {
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
            public void a(ej ej2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    ej2 = ej2 != null ? ej2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ej2);
                    this.kn.transact(5005, parcel, parcel2, 0);
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
            public void a(ej ej2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    ej2 = ej2 != null ? ej2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ej2);
                    parcel.writeInt(n2);
                    this.kn.transact(5004, parcel, parcel2, 0);
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
            public void a(ej ej2, int n2, String string2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    ej2 = ej2 != null ? ej2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ej2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    this.kn.transact(5006, parcel, parcel2, 0);
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
            public void a(ej ej2, int n2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    ej2 = ej2 != null ? ej2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ej2);
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    this.kn.transact(5003, parcel, parcel2, 0);
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
            public void b(ej ej2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    ej2 = ej2 != null ? ej2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ej2);
                    this.kn.transact(5008, parcel, parcel2, 0);
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
            public void b(ej ej2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    ej2 = ej2 != null ? ej2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ej2);
                    parcel.writeInt(n2);
                    this.kn.transact(5007, parcel, parcel2, 0);
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
            public void c(ej ej2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    ej2 = ej2 != null ? ej2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ej2);
                    this.kn.transact(5009, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int dv() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    this.kn.transact(5001, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int dw() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    this.kn.transact(5002, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

