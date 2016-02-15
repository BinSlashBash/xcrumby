package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.cast.ApplicationMetadata;

public interface eq extends IInterface {

    /* renamed from: com.google.android.gms.internal.eq.a */
    public static abstract class C0874a extends Binder implements eq {
        public C0874a() {
            attachInterface(this, "com.google.android.gms.cast.internal.ICastDeviceControllerListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean z = false;
            switch (code) {
                case Std.STD_FILE /*1*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    m894z(data.readInt());
                    return true;
                case Std.STD_URL /*2*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    ApplicationMetadata applicationMetadata = data.readInt() != 0 ? (ApplicationMetadata) ApplicationMetadata.CREATOR.createFromParcel(data) : null;
                    String readString = data.readString();
                    String readString2 = data.readString();
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    m888a(applicationMetadata, readString, readString2, z);
                    return true;
                case Std.STD_URI /*3*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    m885A(data.readInt());
                    return true;
                case Std.STD_CLASS /*4*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    String readString3 = data.readString();
                    double readDouble = data.readDouble();
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    m891b(readString3, readDouble, z);
                    return true;
                case Std.STD_JAVA_TYPE /*5*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    m893d(data.readString(), data.readString());
                    return true;
                case Std.STD_CURRENCY /*6*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    m892b(data.readString(), data.createByteArray());
                    return true;
                case Std.STD_PATTERN /*7*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    m887C(data.readInt());
                    return true;
                case Std.STD_LOCALE /*8*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    m886B(data.readInt());
                    return true;
                case Std.STD_CHARSET /*9*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    onApplicationDisconnected(data.readInt());
                    return true;
                case Std.STD_TIME_ZONE /*10*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    m890a(data.readString(), data.readLong(), data.readInt());
                    return true;
                case Std.STD_INET_ADDRESS /*11*/:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    m889a(data.readString(), data.readLong());
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m885A(int i) throws RemoteException;

    void m886B(int i) throws RemoteException;

    void m887C(int i) throws RemoteException;

    void m888a(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) throws RemoteException;

    void m889a(String str, long j) throws RemoteException;

    void m890a(String str, long j, int i) throws RemoteException;

    void m891b(String str, double d, boolean z) throws RemoteException;

    void m892b(String str, byte[] bArr) throws RemoteException;

    void m893d(String str, String str2) throws RemoteException;

    void onApplicationDisconnected(int i) throws RemoteException;

    void m894z(int i) throws RemoteException;
}
