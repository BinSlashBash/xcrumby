/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.XmlResourceParser
 *  android.text.TextUtils
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.google.android.gms.analytics;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.j;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

abstract class k<T extends j> {
    Context mContext;
    a<T> sy;

    public k(Context context, a<T> a2) {
        this.mContext = context;
        this.sy = a2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private T a(XmlResourceParser var1_1) {
        block16 : {
            try {
                var1_1.next();
                var2_4 = var1_1.getEventType();
lbl4: // 2 sources:
                if (var2_4 == 1) return this.sy.cg();
                if (var1_1.getEventType() != 2) break block16;
                var4_6 = var1_1.getName().toLowerCase();
                if (var4_6.equals("screenname")) {
                    var4_6 = var1_1.getAttributeValue(null, "name");
                    var5_7 = var1_1.nextText().trim();
                    if (!TextUtils.isEmpty((CharSequence)var4_6) && !TextUtils.isEmpty((CharSequence)var5_7)) {
                        this.sy.a(var4_6, var5_7);
                    }
                    break block16;
                }
                if (var4_6.equals("string")) {
                    var4_6 = var1_1.getAttributeValue(null, "name");
                    var5_7 = var1_1.nextText().trim();
                    if (!TextUtils.isEmpty((CharSequence)var4_6) && var5_7 != null) {
                        this.sy.b(var4_6, var5_7);
                    }
                    break block16;
                }
                if (var4_6.equals("bool")) {
                    var5_7 = var1_1.getAttributeValue(null, "name");
                    var4_6 = var1_1.nextText().trim();
                    if (!TextUtils.isEmpty((CharSequence)var5_7) && !(var3_5 = TextUtils.isEmpty((CharSequence)var4_6))) {
                        try {
                            var3_5 = Boolean.parseBoolean(var4_6);
                            this.sy.c(var5_7, var3_5);
                        }
                        catch (NumberFormatException var5_8) {
                            aa.w("Error parsing bool configuration value: " + var4_6);
                        }
                    }
                    break block16;
                }
                if (!var4_6.equals("integer")) break block16;
                var5_7 = var1_1.getAttributeValue(null, "name");
                var4_6 = var1_1.nextText().trim();
                if (TextUtils.isEmpty((CharSequence)var5_7) || (var3_5 = TextUtils.isEmpty((CharSequence)var4_6))) break block16;
                ** try [egrp 4[TRYBLOCK] [19 : 380->401)] { 
lbl35: // 1 sources:
            }
            catch (XmlPullParserException var1_2) {
                aa.w("Error parsing tracker configuration file: " + (Object)var1_2);
            }
            return this.sy.cg();
            catch (IOException var1_3) {
                aa.w("Error parsing tracker configuration file: " + var1_3);
                return this.sy.cg();
            }
            {
                var2_4 = Integer.parseInt(var4_6);
                this.sy.a(var5_7, var2_4);
            }
lbl45: // 1 sources:
            catch (NumberFormatException var5_9) {
                aa.w("Error parsing int configuration value: " + var4_6);
            }
        }
        var2_4 = var1_1.next();
        ** GOTO lbl4
    }

    public T p(int n2) {
        T t2;
        try {
            t2 = this.a(this.mContext.getResources().getXml(n2));
        }
        catch (Resources.NotFoundException var2_3) {
            aa.z("inflate() called with unknown resourceId: " + (Object)var2_3);
            return null;
        }
        return t2;
    }

    public static interface a<U extends j> {
        public void a(String var1, int var2);

        public void a(String var1, String var2);

        public void b(String var1, String var2);

        public void c(String var1, boolean var2);

        public U cg();
    }

}

