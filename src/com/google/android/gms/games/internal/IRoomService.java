package com.google.android.gms.games.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;

public abstract interface IRoomService
  extends IInterface
{
  public abstract void B(boolean paramBoolean)
    throws RemoteException;
  
  public abstract void a(IBinder paramIBinder, IRoomServiceCallbacks paramIRoomServiceCallbacks)
    throws RemoteException;
  
  public abstract void a(DataHolder paramDataHolder, boolean paramBoolean)
    throws RemoteException;
  
  public abstract void a(String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract void a(byte[] paramArrayOfByte, String paramString, int paramInt)
    throws RemoteException;
  
  public abstract void a(byte[] paramArrayOfByte, String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract void aM(String paramString)
    throws RemoteException;
  
  public abstract void aN(String paramString)
    throws RemoteException;
  
  public abstract void gM()
    throws RemoteException;
  
  public abstract void gN()
    throws RemoteException;
  
  public abstract void gO()
    throws RemoteException;
  
  public abstract void gP()
    throws RemoteException;
  
  public abstract void p(String paramString, int paramInt)
    throws RemoteException;
  
  public abstract void q(String paramString, int paramInt)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IRoomService
  {
    public Stub()
    {
      attachInterface(this, "com.google.android.gms.games.internal.IRoomService");
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      boolean bool2 = false;
      boolean bool1 = false;
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.google.android.gms.games.internal.IRoomService");
        return true;
      case 1001: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        a(paramParcel1.readStrongBinder(), IRoomServiceCallbacks.Stub.Q(paramParcel1.readStrongBinder()));
        return true;
      case 1002: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        gM();
        return true;
      case 1003: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        gN();
        return true;
      case 1004: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        a(paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString());
        return true;
      case 1005: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        gO();
        return true;
      case 1006: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        if (paramParcel1.readInt() != 0) {}
        for (paramParcel2 = DataHolder.CREATOR.createFromParcel(paramParcel1);; paramParcel2 = null)
        {
          if (paramParcel1.readInt() != 0) {
            bool1 = true;
          }
          a(paramParcel2, bool1);
          return true;
        }
      case 1007: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        gP();
        return true;
      case 1008: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        bool1 = bool2;
        if (paramParcel1.readInt() != 0) {
          bool1 = true;
        }
        B(bool1);
        return true;
      case 1009: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        a(paramParcel1.createByteArray(), paramParcel1.readString(), paramParcel1.readInt());
        return true;
      case 1010: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        a(paramParcel1.createByteArray(), paramParcel1.createStringArray());
        return true;
      case 1011: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        p(paramParcel1.readString(), paramParcel1.readInt());
        return true;
      case 1012: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        q(paramParcel1.readString(), paramParcel1.readInt());
        return true;
      case 1013: 
        paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
        aM(paramParcel1.readString());
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.games.internal.IRoomService");
      aN(paramParcel1.readString());
      return true;
    }
    
    private static class Proxy
      implements IRoomService
    {
      private IBinder kn;
      
      /* Error */
      public void B(boolean paramBoolean)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore_2
        //   2: invokestatic 22	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   5: astore_3
        //   6: aload_3
        //   7: ldc 24
        //   9: invokevirtual 28	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   12: iload_1
        //   13: ifeq +29 -> 42
        //   16: aload_3
        //   17: iload_2
        //   18: invokevirtual 32	android/os/Parcel:writeInt	(I)V
        //   21: aload_0
        //   22: getfield 34	com/google/android/gms/games/internal/IRoomService$Stub$Proxy:kn	Landroid/os/IBinder;
        //   25: sipush 1008
        //   28: aload_3
        //   29: aconst_null
        //   30: iconst_1
        //   31: invokeinterface 40 5 0
        //   36: pop
        //   37: aload_3
        //   38: invokevirtual 44	android/os/Parcel:recycle	()V
        //   41: return
        //   42: iconst_0
        //   43: istore_2
        //   44: goto -28 -> 16
        //   47: astore 4
        //   49: aload_3
        //   50: invokevirtual 44	android/os/Parcel:recycle	()V
        //   53: aload 4
        //   55: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	56	0	this	Proxy
        //   0	56	1	paramBoolean	boolean
        //   1	43	2	i	int
        //   5	45	3	localParcel	Parcel
        //   47	7	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   6	12	47	finally
        //   16	37	47	finally
      }
      
      public void a(IBinder paramIBinder, IRoomServiceCallbacks paramIRoomServiceCallbacks)
        throws RemoteException
      {
        Object localObject = null;
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          localParcel.writeStrongBinder(paramIBinder);
          paramIBinder = (IBinder)localObject;
          if (paramIRoomServiceCallbacks != null) {
            paramIBinder = paramIRoomServiceCallbacks.asBinder();
          }
          localParcel.writeStrongBinder(paramIBinder);
          this.kn.transact(1001, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void a(DataHolder paramDataHolder, boolean paramBoolean)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
            if (paramDataHolder != null)
            {
              localParcel.writeInt(1);
              paramDataHolder.writeToParcel(localParcel, 0);
              break label85;
              localParcel.writeInt(i);
              this.kn.transact(1006, localParcel, null, 1);
            }
            else
            {
              localParcel.writeInt(0);
            }
          }
          finally
          {
            localParcel.recycle();
          }
          label85:
          do
          {
            i = 0;
            break;
          } while (!paramBoolean);
        }
      }
      
      public void a(String paramString1, String paramString2, String paramString3)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          localParcel.writeString(paramString1);
          localParcel.writeString(paramString2);
          localParcel.writeString(paramString3);
          this.kn.transact(1004, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void a(byte[] paramArrayOfByte, String paramString, int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          localParcel.writeByteArray(paramArrayOfByte);
          localParcel.writeString(paramString);
          localParcel.writeInt(paramInt);
          this.kn.transact(1009, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void a(byte[] paramArrayOfByte, String[] paramArrayOfString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          localParcel.writeByteArray(paramArrayOfByte);
          localParcel.writeStringArray(paramArrayOfString);
          this.kn.transact(1010, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void aM(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          localParcel.writeString(paramString);
          this.kn.transact(1013, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void aN(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          localParcel.writeString(paramString);
          this.kn.transact(1014, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public IBinder asBinder()
      {
        return this.kn;
      }
      
      public void gM()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          this.kn.transact(1002, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void gN()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          this.kn.transact(1003, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void gO()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          this.kn.transact(1005, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void gP()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          this.kn.transact(1007, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void p(String paramString, int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          localParcel.writeString(paramString);
          localParcel.writeInt(paramInt);
          this.kn.transact(1011, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void q(String paramString, int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.games.internal.IRoomService");
          localParcel.writeString(paramString);
          localParcel.writeInt(paramInt);
          this.kn.transact(1012, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/games/internal/IRoomService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */