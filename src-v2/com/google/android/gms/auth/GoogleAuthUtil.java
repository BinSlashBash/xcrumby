/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.accounts.AccountManager
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ApplicationInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.auth;

import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.UserRecoverableNotifiedException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.a;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.s;
import java.io.IOException;
import java.net.URISyntaxException;

public final class GoogleAuthUtil {
    public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
    public static final String KEY_ANDROID_PACKAGE_NAME;
    public static final String KEY_CALLER_UID;
    public static final String KEY_REQUEST_ACTIONS = "request_visible_actions";
    @Deprecated
    public static final String KEY_REQUEST_VISIBLE_ACTIVITIES = "request_visible_actions";
    public static final String KEY_SUPPRESS_PROGRESS_SCREEN = "suppressProgressScreen";
    private static final ComponentName wM;
    private static final ComponentName wN;
    private static final Intent wO;
    private static final Intent wP;

    static {
        if (Build.VERSION.SDK_INT >= 11) {
            // empty if block
        }
        KEY_CALLER_UID = "callerUid";
        if (Build.VERSION.SDK_INT >= 14) {
            // empty if block
        }
        KEY_ANDROID_PACKAGE_NAME = "androidPackageName";
        wM = new ComponentName("com.google.android.gms", "com.google.android.gms.auth.GetToken");
        wN = new ComponentName("com.google.android.gms", "com.google.android.gms.recovery.RecoveryService");
        wO = new Intent().setPackage("com.google.android.gms").setComponent(wM);
        wP = new Intent().setPackage("com.google.android.gms").setComponent(wN);
    }

    private GoogleAuthUtil() {
    }

    private static boolean P(String string2) {
        if ("NetworkError".equals(string2) || "ServiceUnavailable".equals(string2) || "Timeout".equals(string2)) {
            return true;
        }
        return false;
    }

    private static boolean Q(String string2) {
        if ("BadAuthentication".equals(string2) || "CaptchaRequired".equals(string2) || "DeviceManagementRequiredOrSyncDisabled".equals(string2) || "NeedPermission".equals(string2) || "NeedsBrowser".equals(string2) || "UserCancel".equals(string2) || "AppDownloadRequired".equals(string2)) {
            return true;
        }
        return false;
    }

    private static String a(Context context, String string2, String string3, Bundle bundle) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        try {
            string2 = GoogleAuthUtil.getToken(context, string2, string3, bundle2);
            return string2;
        }
        catch (GooglePlayServicesAvailabilityException var1_3) {
            GooglePlayServicesUtil.showErrorNotification(var1_3.getConnectionStatusCode(), context);
            throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
        }
        catch (UserRecoverableAuthException var0_1) {
            throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
        }
    }

    private static void b(Intent object) {
        if (object == null) {
            throw new IllegalArgumentException("Callack cannot be null.");
        }
        object = object.toUri(1);
        try {
            Intent.parseUri((String)object, (int)1);
            return;
        }
        catch (URISyntaxException var0_1) {
            throw new IllegalArgumentException("Parameter callback contains invalid data. It must be serializable using toUri() and parseUri().");
        }
    }

    /*
     * Exception decompiling
     */
    public static void clearToken(Context var0, String var1_1) throws GooglePlayServicesAvailabilityException, GoogleAuthException, IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:394)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:446)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    public static String getToken(Context context, String string2, String string3) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return GoogleAuthUtil.getToken(context, string2, string3, new Bundle());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String getToken(Context object, String string2, String string3, Bundle bundle) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        Context context;
        block10 : {
            context = object.getApplicationContext();
            fq.ak("Calling this from your main thread can lead to deadlock");
            GoogleAuthUtil.s(context);
            bundle = bundle == null ? new Bundle() : new Bundle(bundle);
            object = object.getApplicationInfo().packageName;
            bundle.putString("clientPackageName", (String)object);
            if (!bundle.containsKey(KEY_ANDROID_PACKAGE_NAME)) {
                bundle.putString(KEY_ANDROID_PACKAGE_NAME, (String)object);
            }
            if (!context.bindService(wO, (ServiceConnection)(object = new a()), 1)) throw new IOException("Could not bind to service with the given context.");
            string2 = s.a.a(object.dV()).a(string2, string3, bundle);
            string3 = string2.getString("authtoken");
            boolean bl2 = TextUtils.isEmpty((CharSequence)string3);
            if (bl2) break block10;
            context.unbindService((ServiceConnection)object);
            return string3;
        }
        try {
            block12 : {
                block11 : {
                    string3 = string2.getString("Error");
                    string2 = (Intent)string2.getParcelable("userRecoveryIntent");
                    if (!GoogleAuthUtil.Q(string3)) break block11;
                    throw new UserRecoverableAuthException(string3, (Intent)string2);
                }
                if (!GoogleAuthUtil.P(string3)) break block12;
                throw new IOException(string3);
            }
            try {
                throw new GoogleAuthException(string3);
            }
            catch (RemoteException var1_2) {
                Log.i((String)"GoogleAuthUtil", (String)"GMS remote exception ", (Throwable)var1_2);
                throw new IOException("remote exception");
            }
        }
        catch (Throwable var1_3) {
            context.unbindService((ServiceConnection)object);
            throw var1_3;
        }
        catch (InterruptedException interruptedException) {
            throw new GoogleAuthException("Interrupted");
        }
    }

    public static String getTokenWithNotification(Context context, String string2, String string3, Bundle bundle) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        bundle2.putBoolean("handle_notification", true);
        return GoogleAuthUtil.a(context, string2, string3, bundle2);
    }

    public static String getTokenWithNotification(Context context, String string2, String string3, Bundle bundle, Intent intent) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        GoogleAuthUtil.b(intent);
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        bundle2.putParcelable("callback_intent", (Parcelable)intent);
        bundle2.putBoolean("handle_notification", true);
        return GoogleAuthUtil.a(context, string2, string3, bundle2);
    }

    public static String getTokenWithNotification(Context context, String string2, String string3, Bundle bundle, String string4, Bundle bundle2) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        if (TextUtils.isEmpty((CharSequence)string4)) {
            throw new IllegalArgumentException("Authority cannot be empty or null.");
        }
        Bundle bundle3 = bundle;
        if (bundle == null) {
            bundle3 = new Bundle();
        }
        bundle = bundle2;
        if (bundle2 == null) {
            bundle = new Bundle();
        }
        ContentResolver.validateSyncExtrasBundle((Bundle)bundle);
        bundle3.putString("authority", string4);
        bundle3.putBundle("sync_extras", bundle);
        bundle3.putBoolean("handle_notification", true);
        return GoogleAuthUtil.a(context, string2, string3, bundle3);
    }

    @Deprecated
    public static void invalidateToken(Context context, String string2) {
        AccountManager.get((Context)context).invalidateAuthToken("com.google", string2);
    }

    private static void s(Context context) throws GooglePlayServicesAvailabilityException, GoogleAuthException {
        try {
            GooglePlayServicesUtil.s(context);
            return;
        }
        catch (GooglePlayServicesRepairableException var0_1) {
            throw new GooglePlayServicesAvailabilityException(var0_1.getConnectionStatusCode(), var0_1.getMessage(), var0_1.getIntent());
        }
        catch (GooglePlayServicesNotAvailableException var0_2) {
            throw new GoogleAuthException(var0_2.getMessage());
        }
    }
}

