package com.crumby.impl.derpibooru;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.crumby.lib.authentication.GenericAuthenticator;
import com.crumby.lib.authentication.ServerAuthenticate;

public abstract class AuthenticatorService
  extends Service
{
  protected abstract ServerAuthenticate getServerAuthenticate();
  
  public IBinder onBind(Intent paramIntent)
  {
    return new GenericAuthenticator(this, getServerAuthenticate()).getIBinder();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/AuthenticatorService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */