package com.google.android.gms.analytics;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: com.google.android.gms.analytics.k */
abstract class C0213k<T extends C0211j> {
    Context mContext;
    C0212a<T> sy;

    /* renamed from: com.google.android.gms.analytics.k.a */
    public interface C0212a<U extends C0211j> {
        void m54a(String str, int i);

        void m55a(String str, String str2);

        void m56b(String str, String str2);

        void m57c(String str, boolean z);

        U cg();
    }

    public C0213k(Context context, C0212a<T> c0212a) {
        this.mContext = context;
        this.sy = c0212a;
    }

    private T m58a(XmlResourceParser xmlResourceParser) {
        try {
            xmlResourceParser.next();
            int eventType = xmlResourceParser.getEventType();
            while (eventType != 1) {
                if (xmlResourceParser.getEventType() == 2) {
                    String toLowerCase = xmlResourceParser.getName().toLowerCase();
                    String trim;
                    if (toLowerCase.equals("screenname")) {
                        toLowerCase = xmlResourceParser.getAttributeValue(null, "name");
                        trim = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(toLowerCase) || TextUtils.isEmpty(trim))) {
                            this.sy.m55a(toLowerCase, trim);
                        }
                    } else if (toLowerCase.equals("string")) {
                        r0 = xmlResourceParser.getAttributeValue(null, "name");
                        trim = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(r0) || trim == null)) {
                            this.sy.m56b(r0, trim);
                        }
                    } else if (toLowerCase.equals("bool")) {
                        r0 = xmlResourceParser.getAttributeValue(null, "name");
                        trim = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(r0) || TextUtils.isEmpty(trim))) {
                            try {
                                this.sy.m57c(r0, Boolean.parseBoolean(trim));
                            } catch (NumberFormatException e) {
                                aa.m32w("Error parsing bool configuration value: " + trim);
                            }
                        }
                    } else if (toLowerCase.equals("integer")) {
                        toLowerCase = xmlResourceParser.getAttributeValue(null, "name");
                        trim = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(toLowerCase) || TextUtils.isEmpty(trim))) {
                            try {
                                this.sy.m54a(toLowerCase, Integer.parseInt(trim));
                            } catch (NumberFormatException e2) {
                                aa.m32w("Error parsing int configuration value: " + trim);
                            }
                        }
                    } else {
                        continue;
                    }
                }
                eventType = xmlResourceParser.next();
            }
        } catch (XmlPullParserException e3) {
            aa.m32w("Error parsing tracker configuration file: " + e3);
        } catch (IOException e4) {
            aa.m32w("Error parsing tracker configuration file: " + e4);
        }
        return this.sy.cg();
    }

    public T m59p(int i) {
        try {
            return m58a(this.mContext.getResources().getXml(i));
        } catch (NotFoundException e) {
            aa.m35z("inflate() called with unknown resourceId: " + e);
            return null;
        }
    }
}
