package com.crumby.impl.derpibooru;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.crumby.lib.authentication.GenericAuthenticator;
import com.crumby.lib.authentication.ServerAuthenticate;

public abstract class AuthenticatorService extends Service {
    protected abstract ServerAuthenticate getServerAuthenticate();

    public IBinder onBind(Intent intent) {
        return new GenericAuthenticator(this, getServerAuthenticate()).getIBinder();
    }
}
