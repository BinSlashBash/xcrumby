/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.accounts.AbstractAccountAuthenticator
 *  android.accounts.Account
 *  android.accounts.AccountAuthenticatorResponse
 *  android.accounts.AccountManager
 *  android.accounts.NetworkErrorException
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.crumby.lib.authentication;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.lib.authentication.AuthenticatorActivity;
import com.crumby.lib.authentication.ServerAuthenticate;

public class GenericAuthenticator
extends AbstractAccountAuthenticator {
    private String TAG = "Authenticator";
    private final Context mContext;
    private final ServerAuthenticate serverAuthenticate;

    public GenericAuthenticator(Context context, ServerAuthenticate serverAuthenticate) {
        super(context);
        this.mContext = context;
        this.serverAuthenticate = serverAuthenticate;
    }

    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String string2, String string3, String[] intent, Bundle bundle) throws NetworkErrorException {
        Log.d((String)"udinic", (String)(this.TAG + "> addAccount"));
        if (bundle.getBoolean("doNotCreateAccount")) {
            return null;
        }
        intent = new Intent(this.mContext, (Class)AuthenticatorActivity.class);
        intent.putExtra("ACCOUNT_TYPE", string2);
        intent.putExtra("AUTH_TYPE", string3);
        intent.putExtra("IS_ADDING_ACCOUNT", true);
        intent.putExtra("accountAuthenticatorResponse", (Parcelable)accountAuthenticatorResponse);
        accountAuthenticatorResponse = new Bundle();
        accountAuthenticatorResponse.putParcelable("intent", (Parcelable)intent);
        Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "authenticator adding", string2);
        return accountAuthenticatorResponse;
    }

    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String string2) {
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Bundle getAuthToken(AccountAuthenticatorResponse var1_1, Account var2_2, String var3_3, Bundle var4_4) throws NetworkErrorException {
        Log.d((String)"udinic", (String)(this.TAG + "> getAuthToken"));
        var7_5 = AccountManager.get((Context)this.mContext);
        var6_6 = var7_5.peekAuthToken(var2_2, var3_3);
        Log.d((String)"udinic", (String)(this.TAG + "> peekAuthToken returned - " + var6_6));
        if (TextUtils.isEmpty((CharSequence)var6_6)) ** GOTO lbl-1000
        var5_7 = var6_6;
        if (var4_4.getBoolean("forceRelogin")) lbl-1000: // 2 sources:
        {
            var7_5 = var7_5.getPassword(var2_2);
            var5_7 = var6_6;
            if (var7_5 != null) {
                try {
                    Log.d((String)"udinic", (String)(this.TAG + "> re-authenticating with the existing password"));
                    var5_7 = this.serverAuthenticate.userSignInAndGetAuth(var2_2.name, (String)var7_5, var3_3);
                }
                catch (Exception var5_8) {
                    var5_8.printStackTrace();
                    var5_7 = var6_6;
                }
            }
        }
        if (!TextUtils.isEmpty((CharSequence)var5_7)) {
            var1_1 = new Bundle();
            var1_1.putString("authAccount", var2_2.name);
            var1_1.putString("accountType", var2_2.type);
            var1_1.putString("authtoken", var5_7);
            return var1_1;
        }
        if (var4_4.getBoolean("doNotCreateAccount")) {
            return null;
        }
        var4_4 = new Intent(this.mContext, (Class)AuthenticatorActivity.class);
        var4_4.putExtra("accountAuthenticatorResponse", (Parcelable)var1_1);
        var4_4.putExtra("ACCOUNT_TYPE", var2_2.type);
        var4_4.putExtra("AUTH_TYPE", var3_3);
        var4_4.putExtra("ACCOUNT_NAME", var2_2.name);
        var1_1 = new Bundle();
        var1_1.putParcelable("intent", (Parcelable)var4_4);
        return var1_1;
    }

    public String getAuthTokenLabel(String string2) {
        return string2 + " (Label)";
    }

    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] arrstring) throws NetworkErrorException {
        accountAuthenticatorResponse = new Bundle();
        accountAuthenticatorResponse.putBoolean("booleanResult", false);
        return accountAuthenticatorResponse;
    }

    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String string2, Bundle bundle) throws NetworkErrorException {
        return null;
    }
}

