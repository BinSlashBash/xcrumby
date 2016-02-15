/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.analytics;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.analytics.ExceptionParser;
import com.google.android.gms.analytics.aa;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class StandardExceptionParser
implements ExceptionParser {
    private final TreeSet<String> vL = new TreeSet();

    public StandardExceptionParser(Context context, Collection<String> collection) {
        this.setIncludedPackages(context, collection);
    }

    protected StackTraceElement getBestStackTraceElement(Throwable arrstackTraceElement) {
        if ((arrstackTraceElement = arrstackTraceElement.getStackTrace()) == null || arrstackTraceElement.length == 0) {
            return null;
        }
        int n2 = arrstackTraceElement.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            StackTraceElement stackTraceElement = arrstackTraceElement[i2];
            String string2 = stackTraceElement.getClassName();
            Iterator<String> iterator = this.vL.iterator();
            while (iterator.hasNext()) {
                if (!string2.startsWith(iterator.next())) continue;
                return stackTraceElement;
            }
        }
        return arrstackTraceElement[0];
    }

    protected Throwable getCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    @Override
    public String getDescription(String string2, Throwable throwable) {
        return this.getDescription(this.getCause(throwable), this.getBestStackTraceElement(this.getCause(throwable)), string2);
    }

    protected String getDescription(Throwable object, StackTraceElement stackTraceElement, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(object.getClass().getSimpleName());
        if (stackTraceElement != null) {
            String string3;
            String[] arrstring = stackTraceElement.getClassName().split("\\.");
            object = string3 = "unknown";
            if (arrstring != null) {
                object = string3;
                if (arrstring.length > 0) {
                    object = arrstring[arrstring.length - 1];
                }
            }
            stringBuilder.append(String.format(" (@%s:%s:%s)", object, stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()));
        }
        if (string2 != null) {
            stringBuilder.append(String.format(" {%s}", string2));
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setIncludedPackages(Context arractivityInfo, Collection<String> object) {
        int n2;
        this.vL.clear();
        Object object2 = new HashSet();
        if (object != null) {
            object2.addAll(object);
        }
        if (arractivityInfo != null) {
            try {
                object = arractivityInfo.getApplicationContext().getPackageName();
                this.vL.add((String)object);
                arractivityInfo = arractivityInfo.getApplicationContext().getPackageManager().getPackageInfo((String)object, (int)15).activities;
                if (arractivityInfo != null) {
                    int n3 = arractivityInfo.length;
                    for (n2 = 0; n2 < n3; ++n2) {
                        object2.add(arractivityInfo[n2].packageName);
                    }
                }
            }
            catch (PackageManager.NameNotFoundException var1_2) {
                aa.x("No package found");
            }
        }
        arractivityInfo = object2.iterator();
        block3 : while (arractivityInfo.hasNext()) {
            object = (String)arractivityInfo.next();
            object2 = this.vL.iterator();
            n2 = 1;
            do {
                if (object2.hasNext()) {
                    String string2 = (String)object2.next();
                    if (!object.startsWith(string2)) {
                        if (string2.startsWith((String)object)) {
                            this.vL.remove(string2);
                        }
                        if (n2 == 0) continue block3;
                    }
                } else {
                    this.vL.add((String)object);
                    continue block3;
                }
                n2 = 0;
            } while (true);
            break;
        }
        return;
    }
}

