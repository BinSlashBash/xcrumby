package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.fq;

/* renamed from: com.google.android.gms.dynamic.g */
public abstract class C0309g<T> {
    private final String Hx;
    private T Hy;

    /* renamed from: com.google.android.gms.dynamic.g.a */
    public static class C0308a extends Exception {
        public C0308a(String str) {
            super(str);
        }

        public C0308a(String str, Throwable th) {
            super(str, th);
        }
    }

    protected C0309g(String str) {
        this.Hx = str;
    }

    protected abstract T m358d(IBinder iBinder);

    protected final T m359z(Context context) throws C0308a {
        if (this.Hy == null) {
            fq.m985f(context);
            Context remoteContext = GooglePlayServicesUtil.getRemoteContext(context);
            if (remoteContext == null) {
                throw new C0308a("Could not get remote context.");
            }
            try {
                this.Hy = m358d((IBinder) remoteContext.getClassLoader().loadClass(this.Hx).newInstance());
            } catch (ClassNotFoundException e) {
                throw new C0308a("Could not load creator class.");
            } catch (InstantiationException e2) {
                throw new C0308a("Could not instantiate creator.");
            } catch (IllegalAccessException e3) {
                throw new C0308a("Could not access creator.");
            }
        }
        return this.Hy;
    }
}
