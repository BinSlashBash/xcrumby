/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.games.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.games.internal.ConnectionInfo;
import com.google.android.gms.games.internal.ConnectionInfoCreator;

public interface IRoomServiceCallbacks
extends IInterface {
    public void P(IBinder var1) throws RemoteException;

    public void a(ParcelFileDescriptor var1, int var2) throws RemoteException;

    public void a(ConnectionInfo var1) throws RemoteException;

    public void a(String var1, byte[] var2, int var3) throws RemoteException;

    public void a(String var1, String[] var2) throws RemoteException;

    public void aO(String var1) throws RemoteException;

    public void aP(String var1) throws RemoteException;

    public void aQ(String var1) throws RemoteException;

    public void aR(String var1) throws RemoteException;

    public void aS(String var1) throws RemoteException;

    public void aT(String var1) throws RemoteException;

    public void aU(String var1) throws RemoteException;

    public void b(String var1, String[] var2) throws RemoteException;

    public void bb(int var1) throws RemoteException;

    public void c(int var1, int var2, String var3) throws RemoteException;

    public void c(String var1, String[] var2) throws RemoteException;

    public void d(String var1, String[] var2) throws RemoteException;

    public void e(String var1, String[] var2) throws RemoteException;

    public void f(String var1, String[] var2) throws RemoteException;

    public void g(String var1, String[] var2) throws RemoteException;

    public void gQ() throws RemoteException;

    public void gR() throws RemoteException;

    public void onP2PConnected(String var1) throws RemoteException;

    public void onP2PDisconnected(String var1) throws RemoteException;

    public void r(String var1, int var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IRoomServiceCallbacks {
        public Stub() {
            this.attachInterface((IInterface)this, "com.google.android.gms.games.internal.IRoomServiceCallbacks");
        }

        public static IRoomServiceCallbacks Q(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
            if (iInterface != null && iInterface instanceof IRoomServiceCallbacks) {
                return (IRoomServiceCallbacks)iInterface;
            }
            return new Proxy(iBinder);
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel object, int n3) throws RemoteException {
            Object var6_5 = null;
            Object var5_6 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, (Parcel)object, n3);
                }
                case 1598968902: {
                    object.writeString("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    return true;
                }
                case 1001: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.c(parcel.readInt(), parcel.readInt(), parcel.readString());
                    return true;
                }
                case 1002: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.a(parcel.readString(), parcel.createByteArray(), parcel.readInt());
                    return true;
                }
                case 1003: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.aO(parcel.readString());
                    return true;
                }
                case 1004: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.aP(parcel.readString());
                    return true;
                }
                case 1005: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.aQ(parcel.readString());
                    return true;
                }
                case 1006: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.aR(parcel.readString());
                    return true;
                }
                case 1007: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.aS(parcel.readString());
                    return true;
                }
                case 1008: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.a(parcel.readString(), parcel.createStringArray());
                    return true;
                }
                case 1009: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.b(parcel.readString(), parcel.createStringArray());
                    return true;
                }
                case 1010: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.c(parcel.readString(), parcel.createStringArray());
                    return true;
                }
                case 1011: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.d(parcel.readString(), parcel.createStringArray());
                    return true;
                }
                case 1012: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.e(parcel.readString(), parcel.createStringArray());
                    return true;
                }
                case 1013: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.f(parcel.readString(), parcel.createStringArray());
                    return true;
                }
                case 1014: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.onP2PConnected(parcel.readString());
                    return true;
                }
                case 1015: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.onP2PDisconnected(parcel.readString());
                    return true;
                }
                case 1016: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.gQ();
                    return true;
                }
                case 1017: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.g(parcel.readString(), parcel.createStringArray());
                    return true;
                }
                case 1018: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.aT(parcel.readString());
                    return true;
                }
                case 1019: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.aU(parcel.readString());
                    return true;
                }
                case 1020: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.bb(parcel.readInt());
                    return true;
                }
                case 1021: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.P(parcel.readStrongBinder());
                    return true;
                }
                case 1022: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    object = var5_6;
                    if (parcel.readInt() != 0) {
                        object = ConnectionInfo.CREATOR.ap(parcel);
                    }
                    this.a((ConnectionInfo)object);
                    return true;
                }
                case 1023: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.gR();
                    return true;
                }
                case 1024: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    object = var6_5;
                    if (parcel.readInt() != 0) {
                        object = (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
                    }
                    this.a((ParcelFileDescriptor)object, parcel.readInt());
                    return true;
                }
                case 1025: 
            }
            parcel.enforceInterface("com.google.android.gms.games.internal.IRoomServiceCallbacks");
            this.r(parcel.readString(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IRoomServiceCallbacks {
            private IBinder kn;

            Proxy(IBinder iBinder) {
                this.kn = iBinder;
            }

            @Override
            public void P(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeStrongBinder(iBinder);
                    this.kn.transact(1021, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(ParcelFileDescriptor parcelFileDescriptor, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(1024, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(ConnectionInfo connectionInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    if (connectionInfo != null) {
                        parcel.writeInt(1);
                        connectionInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(1022, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void a(String string2, byte[] arrby, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n2);
                    this.kn.transact(1002, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void a(String string2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(1008, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void aO(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(1003, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void aP(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(1004, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void aQ(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(1005, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void aR(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(1006, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void aS(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(1007, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void aT(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(1018, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void aU(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(1019, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public void b(String string2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(1009, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void bb(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeInt(n2);
                    this.kn.transact(1020, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void c(int n2, int n3, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    this.kn.transact(1001, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void c(String string2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(1010, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void d(String string2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(1011, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void e(String string2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(1012, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void f(String string2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(1013, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void g(String string2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(1017, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void gQ() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.kn.transact(1016, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void gR() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    this.kn.transact(1023, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onP2PConnected(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(1014, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onP2PDisconnected(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(1015, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void r(String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomServiceCallbacks");
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.kn.transact(1025, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

