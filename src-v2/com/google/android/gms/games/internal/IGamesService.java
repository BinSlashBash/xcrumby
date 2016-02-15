/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.games.internal;

import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;
import com.google.android.gms.games.internal.IGamesCallbacks;
import com.google.android.gms.games.internal.multiplayer.InvitationClusterCreator;
import com.google.android.gms.games.internal.multiplayer.ZInvitationCluster;
import com.google.android.gms.games.internal.request.GameRequestCluster;
import com.google.android.gms.games.internal.request.GameRequestClusterCreator;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.ParticipantResultCreator;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;

public interface IGamesService
extends IInterface {
    public void A(boolean var1) throws RemoteException;

    public int a(IGamesCallbacks var1, byte[] var2, String var3, String var4) throws RemoteException;

    public Intent a(int var1, int var2, boolean var3) throws RemoteException;

    public Intent a(int var1, byte[] var2, int var3, String var4) throws RemoteException;

    public Intent a(ZInvitationCluster var1, String var2, String var3) throws RemoteException;

    public Intent a(GameRequestCluster var1, String var2) throws RemoteException;

    public Intent a(RoomEntity var1, int var2) throws RemoteException;

    public Intent a(ParticipantEntity[] var1, String var2, String var3, Uri var4, Uri var5) throws RemoteException;

    public void a(long var1, String var3) throws RemoteException;

    public void a(IBinder var1, Bundle var2) throws RemoteException;

    public void a(IGamesCallbacks var1) throws RemoteException;

    public void a(IGamesCallbacks var1, int var2) throws RemoteException;

    public void a(IGamesCallbacks var1, int var2, int var3, int var4) throws RemoteException;

    public void a(IGamesCallbacks var1, int var2, int var3, boolean var4, boolean var5) throws RemoteException;

    public void a(IGamesCallbacks var1, int var2, int var3, String[] var4, Bundle var5) throws RemoteException;

    public void a(IGamesCallbacks var1, int var2, boolean var3, boolean var4) throws RemoteException;

    public void a(IGamesCallbacks var1, int var2, int[] var3) throws RemoteException;

    public void a(IGamesCallbacks var1, long var2) throws RemoteException;

    public void a(IGamesCallbacks var1, long var2, String var4) throws RemoteException;

    public void a(IGamesCallbacks var1, Bundle var2, int var3, int var4) throws RemoteException;

    public void a(IGamesCallbacks var1, IBinder var2, int var3, String[] var4, Bundle var5, boolean var6, long var7) throws RemoteException;

    public void a(IGamesCallbacks var1, IBinder var2, String var3, boolean var4, long var5) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, int var3) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, int var3, int var4, int var5, boolean var6) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, int var3, IBinder var4, Bundle var5) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, int var3, boolean var4) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, int var3, boolean var4, boolean var5) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, int var3, boolean var4, boolean var5, boolean var6, boolean var7) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, int var3, int[] var4) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, long var3) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, long var3, String var5) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, IBinder var3, Bundle var4) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, String var3) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, String var3, int var4, int var5) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, String var3, int var4, int var5, int var6) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, String var3, int var4, int var5, int var6, boolean var7) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, String var3, int var4, boolean var5, boolean var6) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, String var3, boolean var4) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, String var3, String[] var4) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, boolean var3) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, byte[] var3, String var4, ParticipantResult[] var5) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, byte[] var3, ParticipantResult[] var4) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, int[] var3) throws RemoteException;

    public void a(IGamesCallbacks var1, String var2, String[] var3, int var4, byte[] var5, int var6) throws RemoteException;

    public void a(IGamesCallbacks var1, boolean var2) throws RemoteException;

    public void a(IGamesCallbacks var1, boolean var2, Bundle var3) throws RemoteException;

    public void a(IGamesCallbacks var1, int[] var2) throws RemoteException;

    public void a(IGamesCallbacks var1, String[] var2) throws RemoteException;

    public Intent aA(String var1) throws RemoteException;

    public String aD(String var1) throws RemoteException;

    public String aE(String var1) throws RemoteException;

    public void aF(String var1) throws RemoteException;

    public int aG(String var1) throws RemoteException;

    public Uri aH(String var1) throws RemoteException;

    public void aI(String var1) throws RemoteException;

    public ParcelFileDescriptor aJ(String var1) throws RemoteException;

    public void aY(int var1) throws RemoteException;

    public int b(byte[] var1, String var2, String[] var3) throws RemoteException;

    public Intent b(int var1, int var2, boolean var3) throws RemoteException;

    public void b(long var1, String var3) throws RemoteException;

    public void b(IGamesCallbacks var1) throws RemoteException;

    public void b(IGamesCallbacks var1, int var2, boolean var3, boolean var4) throws RemoteException;

    public void b(IGamesCallbacks var1, long var2) throws RemoteException;

    public void b(IGamesCallbacks var1, long var2, String var4) throws RemoteException;

    public void b(IGamesCallbacks var1, String var2) throws RemoteException;

    public void b(IGamesCallbacks var1, String var2, int var3, int var4, int var5, boolean var6) throws RemoteException;

    public void b(IGamesCallbacks var1, String var2, int var3, IBinder var4, Bundle var5) throws RemoteException;

    public void b(IGamesCallbacks var1, String var2, int var3, boolean var4) throws RemoteException;

    public void b(IGamesCallbacks var1, String var2, int var3, boolean var4, boolean var5) throws RemoteException;

    public void b(IGamesCallbacks var1, String var2, IBinder var3, Bundle var4) throws RemoteException;

    public void b(IGamesCallbacks var1, String var2, String var3) throws RemoteException;

    public void b(IGamesCallbacks var1, String var2, String var3, int var4, int var5, int var6, boolean var7) throws RemoteException;

    public void b(IGamesCallbacks var1, String var2, String var3, boolean var4) throws RemoteException;

    public void b(IGamesCallbacks var1, String var2, boolean var3) throws RemoteException;

    public void b(IGamesCallbacks var1, boolean var2) throws RemoteException;

    public void b(IGamesCallbacks var1, String[] var2) throws RemoteException;

    public void b(String var1, String var2, int var3) throws RemoteException;

    public void c(long var1, String var3) throws RemoteException;

    public void c(IGamesCallbacks var1) throws RemoteException;

    public void c(IGamesCallbacks var1, int var2, boolean var3, boolean var4) throws RemoteException;

    public void c(IGamesCallbacks var1, long var2) throws RemoteException;

    public void c(IGamesCallbacks var1, long var2, String var4) throws RemoteException;

    public void c(IGamesCallbacks var1, String var2) throws RemoteException;

    public void c(IGamesCallbacks var1, String var2, int var3, boolean var4, boolean var5) throws RemoteException;

    public void c(IGamesCallbacks var1, String var2, String var3) throws RemoteException;

    public void c(IGamesCallbacks var1, String var2, boolean var3) throws RemoteException;

    public void c(IGamesCallbacks var1, boolean var2) throws RemoteException;

    public void c(IGamesCallbacks var1, String[] var2) throws RemoteException;

    public void c(String var1, String var2, int var3) throws RemoteException;

    public void d(IGamesCallbacks var1) throws RemoteException;

    public void d(IGamesCallbacks var1, int var2, boolean var3, boolean var4) throws RemoteException;

    public void d(IGamesCallbacks var1, String var2) throws RemoteException;

    public void d(IGamesCallbacks var1, String var2, int var3, boolean var4, boolean var5) throws RemoteException;

    public void d(IGamesCallbacks var1, String var2, String var3) throws RemoteException;

    public void d(IGamesCallbacks var1, String var2, boolean var3) throws RemoteException;

    public Bundle dG() throws RemoteException;

    public void e(IGamesCallbacks var1) throws RemoteException;

    public void e(IGamesCallbacks var1, int var2, boolean var3, boolean var4) throws RemoteException;

    public void e(IGamesCallbacks var1, String var2) throws RemoteException;

    public void e(IGamesCallbacks var1, String var2, String var3) throws RemoteException;

    public ParcelFileDescriptor f(Uri var1) throws RemoteException;

    public void f(IGamesCallbacks var1) throws RemoteException;

    public void f(IGamesCallbacks var1, String var2) throws RemoteException;

    public void g(IGamesCallbacks var1) throws RemoteException;

    public void g(IGamesCallbacks var1, String var2) throws RemoteException;

    public int gA() throws RemoteException;

    public Intent gB() throws RemoteException;

    public int gC() throws RemoteException;

    public int gD() throws RemoteException;

    public void gF() throws RemoteException;

    public DataHolder gG() throws RemoteException;

    public boolean gH() throws RemoteException;

    public DataHolder gI() throws RemoteException;

    public void gJ() throws RemoteException;

    public Intent gK() throws RemoteException;

    public void gL() throws RemoteException;

    public String gl() throws RemoteException;

    public String gm() throws RemoteException;

    public Intent gp() throws RemoteException;

    public Intent gq() throws RemoteException;

    public Intent gr() throws RemoteException;

    public Intent gs() throws RemoteException;

    public Intent gw() throws RemoteException;

    public Intent gx() throws RemoteException;

    public int gy() throws RemoteException;

    public String gz() throws RemoteException;

    public RoomEntity h(IGamesCallbacks var1, String var2) throws RemoteException;

    public void h(IGamesCallbacks var1) throws RemoteException;

    public void i(IGamesCallbacks var1) throws RemoteException;

    public void i(IGamesCallbacks var1, String var2) throws RemoteException;

    public void j(IGamesCallbacks var1) throws RemoteException;

    public void j(IGamesCallbacks var1, String var2) throws RemoteException;

    public void j(String var1, String var2) throws RemoteException;

    public void k(IGamesCallbacks var1, String var2) throws RemoteException;

    public void k(String var1, String var2) throws RemoteException;

    public void l(IGamesCallbacks var1, String var2) throws RemoteException;

    public void l(String var1, int var2) throws RemoteException;

    public void m(IGamesCallbacks var1, String var2) throws RemoteException;

    public void m(String var1, int var2) throws RemoteException;

    public void n(IGamesCallbacks var1, String var2) throws RemoteException;

    public void n(String var1, int var2) throws RemoteException;

    public void o(long var1) throws RemoteException;

    public void o(IGamesCallbacks var1, String var2) throws RemoteException;

    public void o(String var1, int var2) throws RemoteException;

    public void p(long var1) throws RemoteException;

    public void p(IGamesCallbacks var1, String var2) throws RemoteException;

    public void q(long var1) throws RemoteException;

    public void q(IGamesCallbacks var1, String var2) throws RemoteException;

    public void r(long var1) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IGamesService {
        public Stub() {
            this.attachInterface((IInterface)this, "com.google.android.gms.games.internal.IGamesService");
        }

        public static IGamesService N(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IGamesService");
            if (iInterface != null && iInterface instanceof IGamesService) {
                return (IGamesService)iInterface;
            }
            return new Proxy(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            String[] arrstring = null;
            Object object2 = null;
            IBinder iBinder = null;
            Object object3 = null;
            IBinder iBinder2 = null;
            IBinder iBinder3 = null;
            Object object4 = null;
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            boolean bl5 = false;
            boolean bl6 = false;
            int n4 = 0;
            boolean bl7 = false;
            boolean bl8 = false;
            boolean bl9 = false;
            boolean bl10 = false;
            boolean bl11 = false;
            boolean bl12 = false;
            boolean bl13 = false;
            boolean bl14 = false;
            boolean bl15 = false;
            boolean bl16 = false;
            boolean bl17 = false;
            boolean bl18 = false;
            boolean bl19 = false;
            boolean bl20 = false;
            boolean bl21 = false;
            boolean bl22 = false;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.games.internal.IGamesService");
                    return true;
                }
                case 5001: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.o(object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 5002: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 5003: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gz();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 5004: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.dG();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 5005: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = object.readStrongBinder();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a((IBinder)object4, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 5006: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.gF();
                    parcel.writeNoException();
                    return true;
                }
                case 5007: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gl();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 5064: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.aD(object.readString());
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 5065: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.j(object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5012: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gm();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 5013: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gG();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 5014: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5015: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    if (object.readInt() != 0) {
                        bl22 = true;
                    }
                    this.a((IGamesCallbacks)object4, n2, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 5016: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 5017: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.b(IGamesCallbacks.Stub.M(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 5018: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.b(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5019: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    n2 = object.readInt();
                    n3 = object.readInt();
                    n4 = object.readInt();
                    bl2 = object.readInt() != 0;
                    this.a((IGamesCallbacks)object4, (String)object3, n2, n3, n4, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 5020: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    n2 = object.readInt();
                    n3 = object.readInt();
                    n4 = object.readInt();
                    bl2 = object.readInt() != 0;
                    this.b((IGamesCallbacks)object4, (String)object3, n2, n3, n4, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 5021: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object3 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object4 = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a((IGamesCallbacks)object3, (Bundle)object4, object.readInt(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 5022: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.c(IGamesCallbacks.Stub.M(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 5023: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    arrstring = object.readStrongBinder();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a((IGamesCallbacks)object4, (String)object3, (IBinder)arrstring, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 5024: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    arrstring = object.readStrongBinder();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.b((IGamesCallbacks)object4, (String)object3, (IBinder)arrstring, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 5025: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object3 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    arrstring = object.readString();
                    n2 = object.readInt();
                    object2 = object.readStrongBinder();
                    if (object.readInt() != 0) {
                        object4 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.a((IGamesCallbacks)object3, (String)arrstring, n2, (IBinder)object2, (Bundle)object4);
                    parcel.writeNoException();
                    return true;
                }
                case 5026: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.d(IGamesCallbacks.Stub.M(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 5027: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.e(IGamesCallbacks.Stub.M(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 5028: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.m(object.readString(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 5029: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.l(object.readString(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 5058: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 5059: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.p(object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 5030: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object3 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object2 = object.readStrongBinder();
                    n2 = object.readInt();
                    iBinder = object.createStringArray();
                    object4 = arrstring;
                    if (object.readInt() != 0) {
                        object4 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object);
                    }
                    bl2 = object.readInt() != 0;
                    this.a((IGamesCallbacks)object3, (IBinder)object2, n2, (String[])iBinder, (Bundle)object4, bl2, object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 5031: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readStrongBinder();
                    arrstring = object.readString();
                    bl2 = object.readInt() != 0;
                    this.a((IGamesCallbacks)object4, (IBinder)object3, (String)arrstring, bl2, object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 5032: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.c(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5033: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    n2 = this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.createByteArray(), object.readString(), object.readString());
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 5034: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    n2 = this.b(object.createByteArray(), object.readString(), object.createStringArray());
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 5035: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.aE(object.readString());
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 5036: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.aY(object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 5037: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.d(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5038: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5039: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    arrstring = object.readString();
                    n2 = object.readInt();
                    n3 = object.readInt();
                    n4 = object.readInt();
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.a((IGamesCallbacks)object4, (String)object3, (String)arrstring, n2, n3, n4, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 5040: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    arrstring = object.readString();
                    n2 = object.readInt();
                    n3 = object.readInt();
                    n4 = object.readInt();
                    bl2 = bl3;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.b((IGamesCallbacks)object4, (String)object3, (String)arrstring, n2, n3, n4, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 5041: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.b(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5042: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.e(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5043: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.f(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5044: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    n2 = object.readInt();
                    n3 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = object.readInt() != 0;
                    this.a((IGamesCallbacks)object4, n2, n3, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 5045: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = object.readInt() != 0;
                    this.a((IGamesCallbacks)object4, (String)object3, n2, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 5046: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = bl4;
                    if (object.readInt() != 0) {
                        bl22 = true;
                    }
                    this.b((IGamesCallbacks)object4, n2, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 5047: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.f(IGamesCallbacks.Stub.M(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 5048: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = bl5;
                    if (object.readInt() != 0) {
                        bl22 = true;
                    }
                    this.c((IGamesCallbacks)object4, n2, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 5049: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.g(IGamesCallbacks.Stub.M(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 5050: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.aF(object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5051: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.b(object.readString(), object.readString(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 5052: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.g(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5053: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.h(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 5060: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    n2 = this.aG(object.readString());
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 5054: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    bl2 = bl6;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.a((IGamesCallbacks)object4, (String)object3, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 5061: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.i(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5055: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.n(object.readString(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 5067: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    bl2 = this.gH();
                    parcel.writeNoException();
                    n2 = n4;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 5068: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    bl2 = bl7;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.A(bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 5056: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.h(IGamesCallbacks.Stub.M(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 5057: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.j(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 5062: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.i(IGamesCallbacks.Stub.M(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 5063: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    bl2 = bl8;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a((IGamesCallbacks)object4, bl2, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 5066: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.aH(object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 5501: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = object.readInt() != 0;
                    this.b((IGamesCallbacks)object4, (String)object3, n2, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 5502: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gI();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 6001: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    bl2 = bl9;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.a((IGamesCallbacks)object4, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 6002: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    arrstring = object.readString();
                    bl2 = bl10;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.a((IGamesCallbacks)object4, (String)object3, (String)arrstring, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 6003: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = bl11;
                    if (object.readInt() != 0) {
                        bl22 = true;
                    }
                    this.d((IGamesCallbacks)object4, n2, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 6004: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = bl12;
                    if (object.readInt() != 0) {
                        bl22 = true;
                    }
                    this.e((IGamesCallbacks)object4, n2, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 6501: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = object.readInt() != 0;
                    bl4 = object.readInt() != 0;
                    if (object.readInt() != 0) {
                        bl13 = true;
                    }
                    this.a((IGamesCallbacks)object4, (String)object3, n2, bl2, bl22, bl4, bl13);
                    parcel.writeNoException();
                    return true;
                }
                case 6502: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    bl2 = bl14;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.b((IGamesCallbacks)object4, (String)object3, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 6503: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    bl2 = bl15;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.b((IGamesCallbacks)object4, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 6504: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    bl2 = bl16;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.c((IGamesCallbacks)object4, (String)object3, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 6505: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    bl2 = bl17;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.d((IGamesCallbacks)object4, (String)object3, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 6506: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    arrstring = object.readString();
                    bl2 = bl18;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.b((IGamesCallbacks)object4, (String)object3, (String)arrstring, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 6507: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = object.readInt() != 0 ? (Uri)Uri.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.f((Uri)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 7001: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.k(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 7002: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readLong(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 7003: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object3 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    arrstring = object.readString();
                    n2 = object.readInt();
                    iBinder = object.readStrongBinder();
                    object4 = object2;
                    if (object.readInt() != 0) {
                        object4 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.b((IGamesCallbacks)object3, (String)arrstring, n2, iBinder, (Bundle)object4);
                    parcel.writeNoException();
                    return true;
                }
                case 8001: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readString(), object.readInt(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 8002: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.aI(object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8003: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.createIntArray());
                    parcel.writeNoException();
                    return true;
                }
                case 8004: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object3 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    n2 = object.readInt();
                    n3 = object.readInt();
                    arrstring = object.createStringArray();
                    object4 = iBinder;
                    if (object.readInt() != 0) {
                        object4 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.a((IGamesCallbacks)object3, n2, n3, arrstring, (Bundle)object4);
                    parcel.writeNoException();
                    return true;
                }
                case 8005: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.l(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8006: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.m(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8007: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.createByteArray(), object.readString(), (ParticipantResult[])object.createTypedArray((Parcelable.Creator)ParticipantResult.CREATOR));
                    parcel.writeNoException();
                    return true;
                }
                case 8008: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.createByteArray(), (ParticipantResult[])object.createTypedArray((Parcelable.Creator)ParticipantResult.CREATOR));
                    parcel.writeNoException();
                    return true;
                }
                case 8009: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.n(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8010: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.o(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8011: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.c(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8012: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.b(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 8013: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.q(object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 8014: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.p(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8024: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    n2 = this.gA();
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 8025: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.k(object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8015: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.d(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8016: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.e(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8017: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.createIntArray());
                    parcel.writeNoException();
                    return true;
                }
                case 8026: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.c(object.readString(), object.readString(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 8018: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readLong(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8019: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(object.readLong(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8020: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.b(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readLong(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8021: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.b(object.readLong(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 8022: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.gJ();
                    parcel.writeNoException();
                    return true;
                }
                case 8023: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    n2 = object.readInt();
                    bl2 = bl19;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.a((IGamesCallbacks)object4, (String)object3, n2, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 8027: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    bl2 = bl20;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.c((IGamesCallbacks)object4, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 9001: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = object.readInt() != 0;
                    this.c((IGamesCallbacks)object4, (String)object3, n2, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 9002: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.q(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 9003: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gp();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9004: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.aA(object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9005: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gq();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9006: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gr();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9007: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gs();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9008: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    n2 = object.readInt();
                    n3 = object.readInt();
                    bl2 = object.readInt() != 0;
                    object = this.a(n2, n3, bl2);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9009: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    n2 = object.readInt();
                    n3 = object.readInt();
                    bl2 = object.readInt() != 0;
                    object = this.b(n2, n3, bl2);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9010: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gw();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9011: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = object.readInt() != 0 ? (RoomEntity)RoomEntity.CREATOR.createFromParcel((Parcel)object) : null;
                    object = this.a((RoomEntity)object4, object.readInt());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9012: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gx();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9013: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gK();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9031: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    arrstring = (ParticipantEntity[])object.createTypedArray(ParticipantEntity.CREATOR);
                    object2 = object.readString();
                    iBinder = object.readString();
                    object4 = object.readInt() != 0 ? (Uri)Uri.CREATOR.createFromParcel((Parcel)object) : null;
                    if (object.readInt() != 0) {
                        object3 = (Uri)Uri.CREATOR.createFromParcel((Parcel)object);
                    }
                    object = this.a((ParticipantEntity[])arrstring, (String)object2, (String)iBinder, (Uri)object4, (Uri)object3);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9019: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    n2 = this.gy();
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 9020: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = object.readInt() != 0;
                    this.d((IGamesCallbacks)object4, (String)object3, n2, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 9028: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    arrstring = object.readString();
                    n2 = object.readInt();
                    bl2 = object.readInt() != 0;
                    bl22 = object.readInt() != 0;
                    this.a((IGamesCallbacks)object4, (String)object3, (String)arrstring, n2, bl2, bl22);
                    parcel.writeNoException();
                    return true;
                }
                case 9030: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.aJ(object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 10001: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.c(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 10002: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.r(object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 10003: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.c(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readLong(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 10004: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.c(object.readLong(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 10005: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.createStringArray(), object.readInt(), object.createByteArray(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 10006: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.createStringArray());
                    parcel.writeNoException();
                    return true;
                }
                case 10007: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.b(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.createStringArray());
                    parcel.writeNoException();
                    return true;
                }
                case 10008: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readString(), object.createStringArray());
                    parcel.writeNoException();
                    return true;
                }
                case 10009: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readInt(), object.readInt(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 10010: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readString(), object.readInt(), object.readInt(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 10011: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 10012: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.a(object.readInt(), object.createByteArray(), object.readInt(), object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 10013: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    n2 = this.gC();
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 10023: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    n2 = this.gD();
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 10015: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object = this.gB();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 10022: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = iBinder2;
                    if (object.readInt() != 0) {
                        object4 = GameRequestCluster.CREATOR.at((Parcel)object);
                    }
                    object = this.a((GameRequestCluster)object4, object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 10014: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.o(object.readString(), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 10016: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 10017: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = IGamesCallbacks.Stub.M(object.readStrongBinder());
                    object3 = object.readString();
                    n2 = object.readInt();
                    bl2 = bl21;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.b((IGamesCallbacks)object4, (String)object3, n2, bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 10021: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    object4 = iBinder3;
                    if (object.readInt() != 0) {
                        object4 = ZInvitationCluster.CREATOR.as((Parcel)object);
                    }
                    object = this.a((ZInvitationCluster)object4, object.readString(), object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 10018: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readInt(), object.createIntArray());
                    parcel.writeNoException();
                    return true;
                }
                case 10019: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.a(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.readString(), object.readInt(), object.createIntArray());
                    parcel.writeNoException();
                    return true;
                }
                case 10020: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.c(IGamesCallbacks.Stub.M(object.readStrongBinder()), object.createStringArray());
                    parcel.writeNoException();
                    return true;
                }
                case 11001: {
                    object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                    this.j(IGamesCallbacks.Stub.M(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 11002: 
            }
            object.enforceInterface("com.google.android.gms.games.internal.IGamesService");
            this.gL();
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements IGamesService {
            private IBinder kn;

            Proxy(IBinder iBinder) {
                this.kn = iBinder;
            }

            @Override
            public void A(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(5068, parcel2, parcel, 0);
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
            public int a(IGamesCallbacks iGamesCallbacks, byte[] arrby, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeByteArray(arrby);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(5033, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
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
            public Intent a(int n2, int n3, boolean bl2) throws RemoteException {
                int n4 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    n2 = n4;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(9008, parcel, parcel2, 0);
                    parcel2.readException();
                    Intent intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
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
            public Intent a(int n2, byte[] object, int n3, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeInt(n2);
                    parcel.writeByteArray((byte[])object);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    this.kn.transact(10012, parcel, parcel2, 0);
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return object;
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
            public Intent a(ZInvitationCluster zInvitationCluster, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    if (zInvitationCluster != null) {
                        parcel.writeInt(1);
                        zInvitationCluster.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(10021, parcel, parcel2, 0);
                    parcel2.readException();
                    zInvitationCluster = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return zInvitationCluster;
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
            public Intent a(GameRequestCluster gameRequestCluster, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    if (gameRequestCluster != null) {
                        parcel.writeInt(1);
                        gameRequestCluster.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    this.kn.transact(10022, parcel, parcel2, 0);
                    parcel2.readException();
                    gameRequestCluster = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return gameRequestCluster;
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
            public Intent a(RoomEntity roomEntity, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    if (roomEntity != null) {
                        parcel.writeInt(1);
                        roomEntity.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(9011, parcel, parcel2, 0);
                    parcel2.readException();
                    roomEntity = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return roomEntity;
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
            public Intent a(ParticipantEntity[] intent, String string2, String string3, Uri uri, Uri uri2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeTypedArray((Parcelable[])intent, 0);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (uri2 != null) {
                        parcel.writeInt(1);
                        uri2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(9031, parcel, parcel2, 0);
                    parcel2.readException();
                    intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void a(long l2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeLong(l2);
                    parcel.writeString(string2);
                    this.kn.transact(8019, parcel, parcel2, 0);
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
            public void a(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
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
            public void a(IGamesCallbacks iGamesCallbacks, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeInt(n2);
                    this.kn.transact(10016, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    this.kn.transact(10009, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, int n2, int n3, boolean bl2, boolean bl3) throws RemoteException {
                int n4 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n4 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(5044, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, int n2, int n3, String[] arrstring, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeStringArray(arrstring);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(IGamesCallbacks iGamesCallbacks, int n2, boolean bl2, boolean bl3) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n3 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(5015, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, int n2, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeInt(n2);
                    parcel.writeIntArray(arrn);
                    this.kn.transact(10018, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeLong(l2);
                    this.kn.transact(5058, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, long l2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeLong(l2);
                    parcel.writeString(string2);
                    this.kn.transact(8018, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, Bundle bundle, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
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
            public void a(IGamesCallbacks iGamesCallbacks, IBinder iBinder, int n2, String[] arrstring, Bundle bundle, boolean bl2, long l2) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n2);
                    parcel.writeStringArray(arrstring);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                }
                catch (Throwable var1_2) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw var1_2;
                }
                n2 = bl2 ? n3 : 0;
                parcel.writeInt(n2);
                parcel.writeLong(l2);
                this.kn.transact(5030, parcel, parcel2, 0);
                parcel2.readException();
                parcel2.recycle();
                parcel.recycle();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(IGamesCallbacks iGamesCallbacks, IBinder iBinder, String string2, boolean bl2, long l2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    parcel.writeLong(l2);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(5014, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.kn.transact(10011, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, int n2, int n3, int n4, boolean bl2) throws RemoteException {
                int n5 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    n2 = n5;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, int n2, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, int n2, boolean bl2) throws RemoteException {
                int n3 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    n2 = n3;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(8023, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, int n2, boolean bl2, boolean bl3) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n3 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(5045, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, int n2, boolean bl2, boolean bl3, boolean bl4, boolean bl5) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl4 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl5 ? n3 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(6501, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, int n2, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeIntArray(arrn);
                    this.kn.transact(10019, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeLong(l2);
                    this.kn.transact(5016, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, long l2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeLong(l2);
                    parcel.writeString(string3);
                    this.kn.transact(7002, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(5038, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, String string3, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, String string3, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    this.kn.transact(10010, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, String string3, int n2, int n3, int n4, boolean bl2) throws RemoteException {
                int n5 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    n2 = n5;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, String string3, int n2, boolean bl2, boolean bl3) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n3 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(9028, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, String string3, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, String string3, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(10008, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(5054, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, byte[] arrby, String string3, ParticipantResult[] arrparticipantResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    parcel.writeString(string3);
                    parcel.writeTypedArray((Parcelable[])arrparticipantResult, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, byte[] arrby, ParticipantResult[] arrparticipantResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    parcel.writeTypedArray((Parcelable[])arrparticipantResult, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeIntArray(arrn);
                    this.kn.transact(8017, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, String string2, String[] arrstring, int n2, byte[] arrby, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n3);
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
            public void a(IGamesCallbacks iGamesCallbacks, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(6001, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, boolean bl2, Bundle bundle) throws RemoteException {
                int n2 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    if (!bl2) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(5063, parcel, parcel2, 0);
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
            public void a(IGamesCallbacks iGamesCallbacks, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeIntArray(arrn);
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
            public void a(IGamesCallbacks iGamesCallbacks, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeStringArray(arrstring);
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
            public Intent aA(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    this.kn.transact(9004, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String aD(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    this.kn.transact(5064, parcel, parcel2, 0);
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
            public String aE(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    this.kn.transact(5035, parcel, parcel2, 0);
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
            public void aF(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    this.kn.transact(5050, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int aG(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    this.kn.transact(5060, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
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
            public Uri aH(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    this.kn.transact(5066, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readInt() != 0 ? (Uri)Uri.CREATOR.createFromParcel(parcel2) : null;
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void aI(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    this.kn.transact(8002, parcel, parcel2, 0);
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
            public ParcelFileDescriptor aJ(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    this.kn.transact(9030, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readInt() != 0 ? (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void aY(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
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

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public int b(byte[] arrby, String string2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeByteArray(arrby);
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(5034, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
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
            public Intent b(int n2, int n3, boolean bl2) throws RemoteException {
                int n4 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    n2 = n4;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(9009, parcel, parcel2, 0);
                    parcel2.readException();
                    Intent intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void b(long l2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeLong(l2);
                    parcel.writeString(string2);
                    this.kn.transact(8021, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
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
            public void b(IGamesCallbacks iGamesCallbacks, int n2, boolean bl2, boolean bl3) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n3 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(5046, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeLong(l2);
                    this.kn.transact(8012, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, long l2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeLong(l2);
                    parcel.writeString(string2);
                    this.kn.transact(8020, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
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
            public void b(IGamesCallbacks iGamesCallbacks, String string2, int n2, int n3, int n4, boolean bl2) throws RemoteException {
                int n5 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    n2 = n5;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(5020, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, String string2, int n2, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(7003, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, String string2, int n2, boolean bl2) throws RemoteException {
                int n3 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    n2 = n3;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(10017, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, String string2, int n2, boolean bl2, boolean bl3) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n3 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(5501, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, String string2, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(5041, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, String string2, String string3, int n2, int n3, int n4, boolean bl2) throws RemoteException {
                int n5 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    n2 = n5;
                    if (bl2) {
                        n2 = 1;
                    }
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void b(IGamesCallbacks iGamesCallbacks, String string2, String string3, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(6506, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, String string2, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(6502, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(6503, parcel, parcel2, 0);
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
            public void b(IGamesCallbacks iGamesCallbacks, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(10007, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void b(String string2, String string3, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    this.kn.transact(5051, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void c(long l2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeLong(l2);
                    parcel.writeString(string2);
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
            public void c(IGamesCallbacks iGamesCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
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
            public void c(IGamesCallbacks iGamesCallbacks, int n2, boolean bl2, boolean bl3) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n3 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(5048, parcel, parcel2, 0);
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
            public void c(IGamesCallbacks iGamesCallbacks, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeLong(l2);
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
            public void c(IGamesCallbacks iGamesCallbacks, long l2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeLong(l2);
                    parcel.writeString(string2);
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
            public void c(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(5032, parcel, parcel2, 0);
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
            public void c(IGamesCallbacks iGamesCallbacks, String string2, int n2, boolean bl2, boolean bl3) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n3 : 0;
                    parcel.writeInt(n2);
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
            public void c(IGamesCallbacks iGamesCallbacks, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(8011, parcel, parcel2, 0);
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
            public void c(IGamesCallbacks iGamesCallbacks, String string2, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(6504, parcel, parcel2, 0);
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
            public void c(IGamesCallbacks iGamesCallbacks, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(8027, parcel, parcel2, 0);
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
            public void c(IGamesCallbacks iGamesCallbacks, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(10020, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void c(String string2, String string3, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    this.kn.transact(8026, parcel, parcel2, 0);
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
            public void d(IGamesCallbacks iGamesCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    this.kn.transact(5026, parcel, parcel2, 0);
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
            public void d(IGamesCallbacks iGamesCallbacks, int n2, boolean bl2, boolean bl3) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n3 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(6003, parcel, parcel2, 0);
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
            public void d(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
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
            public void d(IGamesCallbacks iGamesCallbacks, String string2, int n2, boolean bl2, boolean bl3) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n3 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(9020, parcel, parcel2, 0);
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
            public void d(IGamesCallbacks iGamesCallbacks, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(8015, parcel, parcel2, 0);
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
            public void d(IGamesCallbacks iGamesCallbacks, String string2, boolean bl2) throws RemoteException {
                int n2 = 0;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(6505, parcel, parcel2, 0);
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
            public Bundle dG() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(5004, parcel, parcel2, 0);
                    parcel2.readException();
                    Bundle bundle = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
                    return bundle;
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
            public void e(IGamesCallbacks iGamesCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
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
            public void e(IGamesCallbacks iGamesCallbacks, int n2, boolean bl2, boolean bl3) throws RemoteException {
                int n3 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? n3 : 0;
                    parcel.writeInt(n2);
                    this.kn.transact(6004, parcel, parcel2, 0);
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
            public void e(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(5042, parcel, parcel2, 0);
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
            public void e(IGamesCallbacks iGamesCallbacks, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(8016, parcel, parcel2, 0);
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
            public ParcelFileDescriptor f(Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(6507, parcel, parcel2, 0);
                    parcel2.readException();
                    uri = parcel2.readInt() != 0 ? (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                    return uri;
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
            public void f(IGamesCallbacks iGamesCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    this.kn.transact(5047, parcel, parcel2, 0);
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
            public void f(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(5043, parcel, parcel2, 0);
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
            public void g(IGamesCallbacks iGamesCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    this.kn.transact(5049, parcel, parcel2, 0);
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
            public void g(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(5052, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int gA() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(8024, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
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
            public Intent gB() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(10015, parcel, parcel2, 0);
                    parcel2.readException();
                    Intent intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int gC() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(10013, parcel, parcel2, 0);
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
            public int gD() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(10023, parcel, parcel2, 0);
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
            public void gF() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
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
            public DataHolder gG() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(5013, parcel, parcel2, 0);
                    parcel2.readException();
                    DataHolder dataHolder = parcel2.readInt() != 0 ? DataHolder.CREATOR.createFromParcel(parcel2) : null;
                    return dataHolder;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean gH() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                        this.kn.transact(5067, parcel, parcel2, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public DataHolder gI() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(5502, parcel, parcel2, 0);
                    parcel2.readException();
                    DataHolder dataHolder = parcel2.readInt() != 0 ? DataHolder.CREATOR.createFromParcel(parcel2) : null;
                    return dataHolder;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void gJ() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(8022, parcel, parcel2, 0);
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
            public Intent gK() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(9013, parcel, parcel2, 0);
                    parcel2.readException();
                    Intent intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void gL() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(11002, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String gl() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(5007, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String gm() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(5012, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
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
            public Intent gp() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(9003, parcel, parcel2, 0);
                    parcel2.readException();
                    Intent intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
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
            public Intent gq() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(9005, parcel, parcel2, 0);
                    parcel2.readException();
                    Intent intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
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
            public Intent gr() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(9006, parcel, parcel2, 0);
                    parcel2.readException();
                    Intent intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
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
            public Intent gs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(9007, parcel, parcel2, 0);
                    parcel2.readException();
                    Intent intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
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
            public Intent gw() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(9010, parcel, parcel2, 0);
                    parcel2.readException();
                    Intent intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
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
            public Intent gx() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(9012, parcel, parcel2, 0);
                    parcel2.readException();
                    Intent intent = parcel2.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel2) : null;
                    return intent;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int gy() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(9019, parcel, parcel2, 0);
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
            public String gz() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    this.kn.transact(5003, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
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
            public RoomEntity h(IGamesCallbacks object, String string2) throws RemoteException {
                Object var3_4 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    object = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)object);
                    parcel.writeString(string2);
                    this.kn.transact(5053, parcel, parcel2, 0);
                    parcel2.readException();
                    object = var3_4;
                    if (parcel2.readInt() != 0) {
                        object = (RoomEntity)RoomEntity.CREATOR.createFromParcel(parcel2);
                    }
                    return object;
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
            public void h(IGamesCallbacks iGamesCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    this.kn.transact(5056, parcel, parcel2, 0);
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
            public void i(IGamesCallbacks iGamesCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    this.kn.transact(5062, parcel, parcel2, 0);
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
            public void i(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(5061, parcel, parcel2, 0);
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
            public void j(IGamesCallbacks iGamesCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
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
            public void j(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(5057, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void j(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(5065, parcel, parcel2, 0);
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
            public void k(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(7001, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void k(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.kn.transact(8025, parcel, parcel2, 0);
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
            public void l(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(8005, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void l(String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.kn.transact(5029, parcel, parcel2, 0);
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
            public void m(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(8006, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void m(String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.kn.transact(5028, parcel, parcel2, 0);
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
            public void n(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
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

            @Override
            public void n(String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.kn.transact(5055, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void o(long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeLong(l2);
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
            public void o(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
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
            public void o(String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    this.kn.transact(10014, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void p(long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeLong(l2);
                    this.kn.transact(5059, parcel, parcel2, 0);
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
            public void p(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(8014, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void q(long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeLong(l2);
                    this.kn.transact(8013, parcel, parcel2, 0);
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
            public void q(IGamesCallbacks iGamesCallbacks, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    iGamesCallbacks = iGamesCallbacks != null ? iGamesCallbacks.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iGamesCallbacks);
                    parcel.writeString(string2);
                    this.kn.transact(9002, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void r(long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                    parcel.writeLong(l2);
                    this.kn.transact(10002, parcel, parcel2, 0);
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

