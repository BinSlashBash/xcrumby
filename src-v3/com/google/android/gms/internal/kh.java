package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.data.DataHolder;

public interface kh extends IInterface {

    /* renamed from: com.google.android.gms.internal.kh.a */
    public static abstract class C0933a extends Binder implements kh {
        public C0933a() {
            attachInterface(this, "com.google.android.gms.wearable.internal.IWearableListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            kk kkVar = null;
            switch (code) {
                case Std.STD_FILE /*1*/:
                    DataHolder createFromParcel;
                    data.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    if (data.readInt() != 0) {
                        createFromParcel = DataHolder.CREATOR.createFromParcel(data);
                    }
                    m1116M(createFromParcel);
                    return true;
                case Std.STD_URL /*2*/:
                    ki kiVar;
                    data.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    if (data.readInt() != 0) {
                        kiVar = (ki) ki.CREATOR.createFromParcel(data);
                    }
                    m1117a(kiVar);
                    return true;
                case Std.STD_URI /*3*/:
                    data.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    if (data.readInt() != 0) {
                        kkVar = (kk) kk.CREATOR.createFromParcel(data);
                    }
                    m1118a(kkVar);
                    return true;
                case Std.STD_CLASS /*4*/:
                    data.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    if (data.readInt() != 0) {
                        kkVar = (kk) kk.CREATOR.createFromParcel(data);
                    }
                    m1119b(kkVar);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.wearable.internal.IWearableListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m1116M(DataHolder dataHolder) throws RemoteException;

    void m1117a(ki kiVar) throws RemoteException;

    void m1118a(kk kkVar) throws RemoteException;

    void m1119b(kk kkVar) throws RemoteException;
}
