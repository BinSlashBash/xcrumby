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
package com.google.android.gms.games.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.games.internal.IGamesSignInCallbacks;

public interface IGamesSignInService
extends IInterface {
    public void a(IGamesSignInCallbacks var1, String var2, String var3) throws RemoteException;

    public void a(IGamesSignInCallbacks var1, String var2, String var3, String var4) throws RemoteException;

    public void a(IGamesSignInCallbacks var1, String var2, String var3, String[] var4) throws RemoteException;

    public void a(IGamesSignInCallbacks var1, String var2, String var3, String[] var4, String var5) throws RemoteException;

    public String aK(String var1) throws RemoteException;

    public String aL(String var1) throws RemoteException;

    public void b(IGamesSignInCallbacks var1, String var2, String var3, String var4) throws RemoteException;

    public void b(IGamesSignInCallbacks var1, String var2, String var3, String[] var4) throws RemoteException;

    public String f(String var1, boolean var2) throws RemoteException;

    public void l(String var1, String var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IGamesSignInService {
        public Stub() {
            this.attachInterface((IInterface)this, "com.google.android.gms.games.internal.IGamesSignInService");
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.games.internal.IGamesSignInService");
                    return true;
                }
                case 5001: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesSignInService");
                    object = this.aK(object.readString());
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 5002: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesSignInService");
                    object = this.aL(object.readString());
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 5009: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesSignInService");
                    String string2 = object.readString();
                    boolean bl2 = object.readInt() != 0;
                    object = this.f(string2, bl2);
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 5003: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesSignInService");
                    this.a(IGamesSignInCallbacks.Stub.O(object.readStrongBinder()), object.readString(), object.readString(), object.createStringArray(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5004: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesSignInService");
                    this.a(IGamesSignInCallbacks.Stub.O(object.readStrongBinder()), object.readString(), object.readString(), object.createStringArray());
                    parcel.writeNoException();
                    return true;
                }
                case 5005: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesSignInService");
                    this.a(IGamesSignInCallbacks.Stub.O(object.readStrongBinder()), object.readString(), object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5006: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesSignInService");
                    this.a(IGamesSignInCallbacks.Stub.O(object.readStrongBinder()), object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5007: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesSignInService");
                    this.b(IGamesSignInCallbacks.Stub.O(object.readStrongBinder()), object.readString(), object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5008: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesSignInService");
                    this.b(IGamesSignInCallbacks.Stub.O(object.readStrongBinder()), object.readString(), object.readString(), object.createStringArray());
                    parcel.writeNoException();
                    return true;
                }
                case 9001: 
            }
            object.enforceInterface("com.google.android.gms.games.internal.IGamesSignInService");
            this.l(object.readString(), object.readString());
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements IGamesSignInService {
            private IBinder kn;

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(IGamesSignInCallbacks iGamesSignInCallbacks, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInService");
                    iGamesSignInCallbacks = iGamesSignInCallbacks != null ? iGamesSignInCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesSignInCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
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
            public void a(IGamesSignInCallbacks iGamesSignInCallbacks, String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInService");
                    iGamesSignInCallbacks = iGamesSignInCallbacks != null ? iGamesSignInCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesSignInCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
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
            public void a(IGamesSignInCallbacks iGamesSignInCallbacks, String string2, String string3, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInService");
                    iGamesSignInCallbacks = iGamesSignInCallbacks != null ? iGamesSignInCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesSignInCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
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
            public void a(IGamesSignInCallbacks iGamesSignInCallbacks, String string2, String string3, String[] arrstring, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInService");
                    iGamesSignInCallbacks = iGamesSignInCallbacks != null ? iGamesSignInCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesSignInCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
                    parcel.writeString(string4);
                    this.kn.transact(5003, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String aK(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInService");
                    parcel.writeString(string2);
                    this.kn.transact(5001, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String aL(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInService");
                    parcel.writeString(string2);
                    this.kn.transact(5002, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
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
            public void b(IGamesSignInCallbacks iGamesSignInCallbacks, String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInService");
                    iGamesSignInCallbacks = iGamesSignInCallbacks != null ? iGamesSignInCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesSignInCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
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
            public void b(IGamesSignInCallbacks iGamesSignInCallbacks, String string2, String string3, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInService");
                    iGamesSignInCallbacks = iGamesSignInCallbacks != null ? iGamesSignInCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesSignInCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(5008, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String f(String string2, boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                int n2;
                block4 : {
                    n2 = 0;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInService");
                    parcel.writeString(string2);
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel.writeInt(n2);
                    this.kn.transact(5009, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void l(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesSignInService");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(9001, parcel, parcel2, 0);
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

