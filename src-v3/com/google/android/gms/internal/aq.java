package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.dynamic.C0306d;
import com.google.android.gms.dynamic.C0306d.C0820a;
import com.google.android.gms.internal.bq.C0845a;

public interface aq extends IInterface {

    /* renamed from: com.google.android.gms.internal.aq.a */
    public static abstract class C0833a extends Binder implements aq {

        /* renamed from: com.google.android.gms.internal.aq.a.a */
        private static class C0832a implements aq {
            private IBinder kn;

            C0832a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public IBinder m2023a(C0306d c0306d, ak akVar, String str, bq bqVar, int i) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManagerCreator");
                    obtain.writeStrongBinder(c0306d != null ? c0306d.asBinder() : null);
                    if (akVar != null) {
                        obtain.writeInt(1);
                        akVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    if (bqVar != null) {
                        iBinder = bqVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.kn.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    iBinder = obtain2.readStrongBinder();
                    return iBinder;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }
        }

        public static aq m2024g(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManagerCreator");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof aq)) ? new C0832a(iBinder) : (aq) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Std.STD_FILE /*1*/:
                    data.enforceInterface("com.google.android.gms.ads.internal.client.IAdManagerCreator");
                    IBinder a = m626a(C0820a.m1755K(data.readStrongBinder()), data.readInt() != 0 ? ak.CREATOR.m615b(data) : null, data.readString(), C0845a.m2053i(data.readStrongBinder()), data.readInt());
                    reply.writeNoException();
                    reply.writeStrongBinder(a);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.ads.internal.client.IAdManagerCreator");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    IBinder m626a(C0306d c0306d, ak akVar, String str, bq bqVar, int i) throws RemoteException;
}
