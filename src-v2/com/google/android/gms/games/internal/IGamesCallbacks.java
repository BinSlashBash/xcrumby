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
package com.google.android.gms.games.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;

public interface IGamesCallbacks
extends IInterface {
    public void A(DataHolder var1) throws RemoteException;

    public void B(DataHolder var1) throws RemoteException;

    public void C(DataHolder var1) throws RemoteException;

    public void D(DataHolder var1) throws RemoteException;

    public void E(DataHolder var1) throws RemoteException;

    public void F(DataHolder var1) throws RemoteException;

    public void a(int var1, Bundle var2) throws RemoteException;

    public void a(int var1, String var2, boolean var3) throws RemoteException;

    public void a(DataHolder var1, DataHolder var2) throws RemoteException;

    public void a(DataHolder var1, String[] var2) throws RemoteException;

    public void aU(int var1) throws RemoteException;

    public void aV(int var1) throws RemoteException;

    public void b(int var1, int var2, String var3) throws RemoteException;

    public void b(int var1, Bundle var2) throws RemoteException;

    public void b(DataHolder var1) throws RemoteException;

    public void b(DataHolder var1, String[] var2) throws RemoteException;

    public void c(int var1, Bundle var2) throws RemoteException;

    public void c(DataHolder var1) throws RemoteException;

    public void c(DataHolder var1, String[] var2) throws RemoteException;

    public void d(int var1, String var2) throws RemoteException;

    public void d(DataHolder var1) throws RemoteException;

    public void d(DataHolder var1, String[] var2) throws RemoteException;

    public void du() throws RemoteException;

    public void e(int var1, String var2) throws RemoteException;

    public void e(DataHolder var1) throws RemoteException;

    public void e(DataHolder var1, String[] var2) throws RemoteException;

    public void f(int var1, String var2) throws RemoteException;

    public void f(DataHolder var1) throws RemoteException;

    public void f(DataHolder var1, String[] var2) throws RemoteException;

    public void g(DataHolder var1) throws RemoteException;

    public void h(DataHolder var1) throws RemoteException;

    public void i(DataHolder var1) throws RemoteException;

    public void j(DataHolder var1) throws RemoteException;

    public void k(DataHolder var1) throws RemoteException;

    public void l(DataHolder var1) throws RemoteException;

    public void m(DataHolder var1) throws RemoteException;

    public void n(DataHolder var1) throws RemoteException;

    public void o(DataHolder var1) throws RemoteException;

    public void onInvitationRemoved(String var1) throws RemoteException;

    public void onLeftRoom(int var1, String var2) throws RemoteException;

    public void onP2PConnected(String var1) throws RemoteException;

    public void onP2PDisconnected(String var1) throws RemoteException;

    public void onRealTimeMessageReceived(RealTimeMessage var1) throws RemoteException;

    public void onRequestRemoved(String var1) throws RemoteException;

    public void onTurnBasedMatchRemoved(String var1) throws RemoteException;

    public void p(DataHolder var1) throws RemoteException;

    public void q(DataHolder var1) throws RemoteException;

    public void r(DataHolder var1) throws RemoteException;

    public void s(DataHolder var1) throws RemoteException;

    public void t(DataHolder var1) throws RemoteException;

    public void u(DataHolder var1) throws RemoteException;

    public void v(DataHolder var1) throws RemoteException;

    public void w(DataHolder var1) throws RemoteException;

    public void x(DataHolder var1) throws RemoteException;

    public void y(DataHolder var1) throws RemoteException;

    public void z(DataHolder var1) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IGamesCallbacks {
        public Stub() {
            this.attachInterface((IInterface)this, "com.google.android.gms.games.internal.IGamesCallbacks");
        }

        public static IGamesCallbacks M(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IGamesCallbacks");
            if (iInterface != null && iInterface instanceof IGamesCallbacks) {
                return (IGamesCallbacks)iInterface;
            }
            return new Proxy(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            String string2 = null;
            DataHolder dataHolder = null;
            String string3 = null;
            String string4 = null;
            String string5 = null;
            String string6 = null;
            String string7 = null;
            String string8 = null;
            String string9 = null;
            String string10 = null;
            String string11 = null;
            String string12 = null;
            String string13 = null;
            String string14 = null;
            String string15 = null;
            String string16 = null;
            String string17 = null;
            String string18 = null;
            String string19 = null;
            String string20 = null;
            String string21 = null;
            String string22 = null;
            String string23 = null;
            String string24 = null;
            String string25 = null;
            String string26 = null;
            String string27 = null;
            String string28 = null;
            String string29 = null;
            String string30 = null;
            String string31 = null;
            String string32 = null;
            String string33 = null;
            String string34 = null;
            String string35 = null;
            String string36 = null;
            String string37 = null;
            String string38 = null;
            String string39 = null;
            String string40 = null;
            String string41 = null;
            Object object = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.games.internal.IGamesCallbacks");
                    return true;
                }
                case 5001: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.d(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 5002: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.b((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5003: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.e(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 5004: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string2;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.c((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5005: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = parcel.readInt() != 0 ? DataHolder.CREATOR.createFromParcel(parcel) : null;
                    if (parcel.readInt() != 0) {
                        dataHolder = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.a((DataHolder)object, dataHolder);
                    parcel2.writeNoException();
                    return true;
                }
                case 5006: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string3;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.d((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5007: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string4;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.e((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5008: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string5;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.f((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5009: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string6;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.g((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5010: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string7;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.h((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5011: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string8;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.i((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5016: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.du();
                    parcel2.writeNoException();
                    return true;
                }
                case 5017: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string9;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.k((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5037: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string10;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.l((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5018: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string11;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.s((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5019: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string12;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.t((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5020: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.onLeftRoom(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 5021: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string13;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.u((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5022: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string14;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.v((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5023: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string15;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.w((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5024: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string16;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.x((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5025: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string17;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.y((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5026: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string18;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.a((DataHolder)object, parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 5027: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string19;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.b((DataHolder)object, parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 5028: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string20;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.c((DataHolder)object, parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 5029: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string21;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.d((DataHolder)object, parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 5030: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string22;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.e((DataHolder)object, parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 5031: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string23;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.f((DataHolder)object, parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 5032: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string24;
                    if (parcel.readInt() != 0) {
                        object = (RealTimeMessage)RealTimeMessage.CREATOR.createFromParcel(parcel);
                    }
                    this.onRealTimeMessageReceived((RealTimeMessage)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5033: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.b(parcel.readInt(), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 5034: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    n2 = parcel.readInt();
                    object = parcel.readString();
                    boolean bl2 = parcel.readInt() != 0;
                    this.a(n2, (String)object, bl2);
                    parcel2.writeNoException();
                    return true;
                }
                case 5038: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string25;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.z((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5035: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string26;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.A((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5036: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.aU(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 5039: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string27;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.B((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 5040: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.aV(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 6001: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.onP2PConnected(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 6002: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.onP2PDisconnected(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 8001: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string28;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.C((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 8002: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    n2 = parcel.readInt();
                    object = string29;
                    if (parcel.readInt() != 0) {
                        object = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.a(n2, (Bundle)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 8003: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string30;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.n((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 8004: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string31;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.o((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 8005: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string32;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.p((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 8006: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string33;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.q((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 8007: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.f(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 8008: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string34;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.r((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 8009: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.onTurnBasedMatchRemoved(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 8010: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.onInvitationRemoved(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 9001: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string35;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.j((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 10001: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string36;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.m((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 10002: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.onRequestRemoved(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 10003: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string37;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.D((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 10004: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string38;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.E((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 10005: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    n2 = parcel.readInt();
                    object = string39;
                    if (parcel.readInt() != 0) {
                        object = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.b(n2, (Bundle)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 10006: {
                    parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    object = string40;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.F((DataHolder)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 11001: 
            }
            parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
            n2 = parcel.readInt();
            object = string41;
            if (parcel.readInt() != 0) {
                object = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
            }
            this.c(n2, (Bundle)object);
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IGamesCallbacks {
            private IBinder kn;

            Proxy(IBinder iBinder) {
                this.kn = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void A(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5035, parcel, parcel2, 0);
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
            public void B(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5039, parcel, parcel2, 0);
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
            public void C(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(8001, parcel, parcel2, 0);
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
            public void D(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(10003, parcel, parcel2, 0);
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
            public void E(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(10004, parcel, parcel2, 0);
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
            public void F(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(10006, parcel, parcel2, 0);
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
            public void a(int n2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(8002, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void a(int n2, String string2, boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n3 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel2.writeInt(n2);
                    parcel2.writeString(string2);
                    n2 = n3;
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(5034, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(DataHolder dataHolder, DataHolder dataHolder2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (dataHolder2 != null) {
                        parcel.writeInt(1);
                        dataHolder2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void a(DataHolder dataHolder, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(5026, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void aU(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeInt(n2);
                    this.kn.transact(5036, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void aV(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeInt(n2);
                    this.kn.transact(5040, parcel, parcel2, 0);
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
            public void b(int n2, int n3, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    this.kn.transact(5033, parcel, parcel2, 0);
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
            public void b(int n2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(10005, parcel, parcel2, 0);
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
            public void b(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5002, parcel, parcel2, 0);
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
            public void b(DataHolder dataHolder, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(5027, parcel, parcel2, 0);
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
            public void c(int n2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(11001, parcel, parcel2, 0);
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
            public void c(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void c(DataHolder dataHolder, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(5028, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void d(int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.kn.transact(5001, parcel, parcel2, 0);
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
            public void d(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void d(DataHolder dataHolder, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(5029, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void du() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.kn.transact(5016, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void e(int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.kn.transact(5003, parcel, parcel2, 0);
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
            public void e(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void e(DataHolder dataHolder, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(5030, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void f(int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.kn.transact(8007, parcel, parcel2, 0);
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
            public void f(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void f(DataHolder dataHolder, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(5031, parcel, parcel2, 0);
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
            public void g(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5009, parcel, parcel2, 0);
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
            public void h(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5010, parcel, parcel2, 0);
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
            public void i(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5011, parcel, parcel2, 0);
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
            public void j(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(9001, parcel, parcel2, 0);
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
            public void k(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5017, parcel, parcel2, 0);
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
            public void l(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5037, parcel, parcel2, 0);
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
            public void m(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(10001, parcel, parcel2, 0);
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
            public void n(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(8003, parcel, parcel2, 0);
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
            public void o(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(8004, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onInvitationRemoved(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(8010, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onLeftRoom(int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.kn.transact(5020, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onP2PConnected(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(6001, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onP2PDisconnected(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(6002, parcel, parcel2, 0);
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
            public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (realTimeMessage != null) {
                        parcel.writeInt(1);
                        realTimeMessage.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5032, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onRequestRemoved(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(10002, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onTurnBasedMatchRemoved(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    parcel.writeString(string2);
                    this.kn.transact(8009, parcel, parcel2, 0);
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
            public void p(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(8005, parcel, parcel2, 0);
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
            public void q(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(8006, parcel, parcel2, 0);
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
            public void r(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(8008, parcel, parcel2, 0);
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
            public void s(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5018, parcel, parcel2, 0);
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
            public void t(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5019, parcel, parcel2, 0);
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
            public void u(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5021, parcel, parcel2, 0);
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
            public void v(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5022, parcel, parcel2, 0);
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
            public void w(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5023, parcel, parcel2, 0);
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
            public void x(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5024, parcel, parcel2, 0);
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
            public void y(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5025, parcel, parcel2, 0);
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
            public void z(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5038, parcel, parcel2, 0);
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

