package com.google.android.gms.drive.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;

/* renamed from: com.google.android.gms.drive.internal.w */
public interface C0285w extends IInterface {

    /* renamed from: com.google.android.gms.drive.internal.w.a */
    public static abstract class C0809a extends Binder implements C0285w {

        /* renamed from: com.google.android.gms.drive.internal.w.a.a */
        private static class C0808a implements C0285w {
            private IBinder kn;

            C0808a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public void m1733a(OnEventResponse onEventResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IEventCallback");
                    if (onEventResponse != null) {
                        obtain.writeInt(1);
                        onEventResponse.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.kn.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }
        }

        public C0809a() {
            attachInterface(this, "com.google.android.gms.drive.internal.IEventCallback");
        }

        public static C0285w m1734I(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.internal.IEventCallback");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0285w)) ? new C0808a(iBinder) : (C0285w) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Std.STD_FILE /*1*/:
                    data.enforceInterface("com.google.android.gms.drive.internal.IEventCallback");
                    m321a(data.readInt() != 0 ? (OnEventResponse) OnEventResponse.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.drive.internal.IEventCallback");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m321a(OnEventResponse onEventResponse) throws RemoteException;
}
