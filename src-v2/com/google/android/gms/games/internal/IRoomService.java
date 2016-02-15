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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;
import com.google.android.gms.games.internal.IRoomServiceCallbacks;

public interface IRoomService
extends IInterface {
    public void B(boolean var1) throws RemoteException;

    public void a(IBinder var1, IRoomServiceCallbacks var2) throws RemoteException;

    public void a(DataHolder var1, boolean var2) throws RemoteException;

    public void a(String var1, String var2, String var3) throws RemoteException;

    public void a(byte[] var1, String var2, int var3) throws RemoteException;

    public void a(byte[] var1, String[] var2) throws RemoteException;

    public void aM(String var1) throws RemoteException;

    public void aN(String var1) throws RemoteException;

    public void gM() throws RemoteException;

    public void gN() throws RemoteException;

    public void gO() throws RemoteException;

    public void gP() throws RemoteException;

    public void p(String var1, int var2) throws RemoteException;

    public void q(String var1, int var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IRoomService {
        public Stub() {
            this.attachInterface((IInterface)this, "com.google.android.gms.games.internal.IRoomService");
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel object, int n3) throws RemoteException {
            boolean bl2 = false;
            boolean bl3 = false;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, (Parcel)object, n3);
                }
                case 1598968902: {
                    object.writeString("com.google.android.gms.games.internal.IRoomService");
                    return true;
                }
                case 1001: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.a(parcel.readStrongBinder(), IRoomServiceCallbacks.Stub.Q(parcel.readStrongBinder()));
                    return true;
                }
                case 1002: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.gM();
                    return true;
                }
                case 1003: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.gN();
                    return true;
                }
                case 1004: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.a(parcel.readString(), parcel.readString(), parcel.readString());
                    return true;
                }
                case 1005: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.gO();
                    return true;
                }
                case 1006: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    object = parcel.readInt() != 0 ? DataHolder.CREATOR.createFromParcel(parcel) : null;
                    if (parcel.readInt() != 0) {
                        bl3 = true;
                    }
                    this.a((DataHolder)object, bl3);
                    return true;
                }
                case 1007: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.gP();
                    return true;
                }
                case 1008: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    bl3 = bl2;
                    if (parcel.readInt() != 0) {
                        bl3 = true;
                    }
                    this.B(bl3);
                    return true;
                }
                case 1009: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.a(parcel.createByteArray(), parcel.readString(), parcel.readInt());
                    return true;
                }
                case 1010: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.a(parcel.createByteArray(), parcel.createStringArray());
                    return true;
                }
                case 1011: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.p(parcel.readString(), parcel.readInt());
                    return true;
                }
                case 1012: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.q(parcel.readString(), parcel.readInt());
                    return true;
                }
                case 1013: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
                    this.aM(parcel.readString());
                    return true;
                }
                case 1014: 
            }
            parcel.enforceInterface("com.google.android.gms.games.internal.IRoomService");
            this.aN(parcel.readString());
            return true;
        }

        private static class Proxy
        implements IRoomService {
            private IBinder kn;

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void B(boolean bl2) throws RemoteException {
                int n2 = 1;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    if (!bl2) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(1008, parcel, null, 1);
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
            public void a(IBinder iBinder, IRoomServiceCallbacks iRoomServiceCallbacks) throws RemoteException {
                Object var3_4 = null;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var3_4;
                    if (iRoomServiceCallbacks != null) {
                        iBinder = iRoomServiceCallbacks.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    this.kn.transact(1001, parcel, null, 1);
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
            public void a(DataHolder dataHolder, boolean bl2) throws RemoteException {
                int n2 = 1;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                }
                catch (Throwable var1_2) {
                    parcel.recycle();
                    throw var1_2;
                }
                if (!bl2) {
                    n2 = 0;
                }
                parcel.writeInt(n2);
                this.kn.transact(1006, parcel, null, 1);
                parcel.recycle();
            }

            @Override
            public void a(String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    this.kn.transact(1004, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void a(byte[] arrby, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    parcel.writeByteArray(arrby);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.kn.transact(1009, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void a(byte[] arrby, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    parcel.writeByteArray(arrby);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(1010, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void aM(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    parcel.writeString(string2);
                    this.kn.transact(1013, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void aN(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    parcel.writeString(string2);
                    this.kn.transact(1014, parcel, null, 1);
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
            public void gM() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    this.kn.transact(1002, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void gN() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    this.kn.transact(1003, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void gO() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    this.kn.transact(1005, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void gP() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    this.kn.transact(1007, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void p(String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.kn.transact(1011, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void q(String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.kn.transact(1012, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

