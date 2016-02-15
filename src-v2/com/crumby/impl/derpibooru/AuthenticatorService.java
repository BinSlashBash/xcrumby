/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.os.IBinder
 */
package com.crumby.impl.derpibooru;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.crumby.lib.authentication.GenericAuthenticator;
import com.crumby.lib.authentication.ServerAuthenticate;

public abstract class AuthenticatorService
extends Service {
    protected abstract ServerAuthenticate getServerAuthenticate();

    public IBinder onBind(Intent intent) {
        return new GenericAuthenticator((Context)this, this.getServerAuthenticate()).getIBinder();
    }
}

