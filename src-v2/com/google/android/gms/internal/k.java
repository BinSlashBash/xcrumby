/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.e;
import com.google.android.gms.internal.j;
import com.google.android.gms.internal.n;
import com.google.android.gms.internal.o;
import com.google.android.gms.internal.q;
import java.io.IOException;

public class k
extends j {
    private k(Context context, n n2, o o2) {
        super(context, n2, o2);
    }

    public static k a(String string2, Context context) {
        e e2 = new e();
        k.a(string2, context, e2);
        return new k(context, e2, new q(239));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void b(Context object) {
        long l2;
        block6 : {
            block5 : {
                l2 = 1;
                super.b((Context)object);
                object = this.f((Context)object);
                try {
                    if (!object.isLimitAdTrackingEnabled()) break block5;
                    break block6;
                    catch (IOException iOException) {
                        this.a(28, 1);
                        return;
                    }
                }
                catch (IOException var1_3) {
                    return;
                }
            }
            l2 = 0;
        }
        this.a(28, l2);
        object = object.getId();
        if (object == null) return;
        this.a(30, (String)object);
        return;
        catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
            // empty catch block
        }
    }

    a f(Context object) throws IOException, GooglePlayServicesNotAvailableException {
        AdvertisingIdClient.Info info;
        block5 : {
            int n2 = 0;
            try {
                info = AdvertisingIdClient.getAdvertisingIdInfo((Context)object);
                object = info.getId();
                if (object == null || !object.matches("^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")) break block5;
            }
            catch (GooglePlayServicesRepairableException var1_2) {
                throw new IOException(var1_2);
            }
            byte[] arrby = new byte[16];
            int n3 = 0;
            while (n2 < object.length()) {
                int n4;
                block6 : {
                    if (n2 != 8 && n2 != 13 && n2 != 18) {
                        n4 = n2;
                        if (n2 != 23) break block6;
                    }
                    n4 = n2 + 1;
                }
                arrby[n3] = (byte)((Character.digit(object.charAt(n4), 16) << 4) + Character.digit(object.charAt(n4 + 1), 16));
                ++n3;
                n2 = n4 + 2;
            }
            object = this.jP.a(arrby, true);
        }
        return new a((String)object, info.isLimitAdTrackingEnabled());
    }

    class a {
        private String ka;
        private boolean kb;

        public a(String string2, boolean bl2) {
            this.ka = string2;
            this.kb = bl2;
        }

        public String getId() {
            return this.ka;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.kb;
        }
    }

}

