/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.accounts.AccountAuthenticatorActivity
 *  android.accounts.AccountManager
 *  android.accounts.AccountManagerCallback
 *  android.accounts.AccountManagerFuture
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Handler
 *  android.text.TextUtils
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  android.widget.Toast
 */
package com.crumby.lib.authentication;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.impl.derpibooru.DerpibooruAuthenticatorService;
import com.crumby.impl.furaffinity.FurAffinityAuthenticatorService;
import com.crumby.lib.authentication.ServerAuthenticate;
import com.crumby.lib.events.LoginEvent;
import com.squareup.otto.Bus;

public class AuthenticatorActivity
extends AccountAuthenticatorActivity {
    public static final String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String ARG_AUTH_TYPE = "AUTH_TYPE";
    public static final String ARG_ERROR_WITH_ACCOUNT = "ERROR_WITH_ACCOUNT";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
    public static final String PARAM_USER_PASS = "USER_PASS";
    private static boolean sawAccountLimitAlert;
    private final int REQ_SIGNUP = 1;
    private final String TAG;
    private AccountManager mAccountManager;
    private String mAuthTokenType;
    private String oldAccountName;

    public AuthenticatorActivity() {
        this.TAG = this.getClass().getSimpleName();
    }

    private void end(String string2) {
        Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"", (int)1);
        toast.setGravity(48, 0, 0);
        TextView textView = (TextView)View.inflate((Context)this.getApplicationContext(), (int)2130903119, (ViewGroup)null);
        textView.setText((CharSequence)("Huzzah! You have signed into " + string2 + "!"));
        toast.setView((View)textView);
        toast.show();
        this.finish();
        BusProvider.BUS.get().post(new LoginEvent(string2));
    }

    private void finishLogin(Intent intent) {
        Log.d((String)"udinic", (String)(this.TAG + "> finishLogin"));
        String string2 = intent.getStringExtra("authAccount");
        String string3 = intent.getStringExtra("USER_PASS");
        String string4 = intent.getStringExtra("accountType");
        string2 = new Account(string2, string4);
        if (!this.getIntent().getBooleanExtra("IS_ADDING_ACCOUNT", false)) {
            this.mAccountManager.removeAccount(new Account(this.oldAccountName, string4), null, null);
        }
        Log.d((String)"udinic", (String)(this.TAG + "> finishLogin > addAccountExplicitly"));
        Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "added", string4);
        String string5 = intent.getStringExtra("authtoken");
        String string6 = this.mAuthTokenType;
        this.mAccountManager.addAccountExplicitly((Account)string2, string3, null);
        this.mAccountManager.setAuthToken((Account)string2, string6, string5);
        this.setAccountAuthenticatorResult(intent.getExtras());
        this.setResult(-1, intent);
        this.end(string4);
        CrDb.d("viewer", "saw feedback alert");
    }

    public static ServerAuthenticate getServerAuthenticate(String string2) {
        if ("derpibooru".equals(string2)) {
            return DerpibooruAuthenticatorService.SERVER_AUTHENTICATE;
        }
        if ("furaffinity".equals(string2)) {
            return FurAffinityAuthenticatorService.SERVER_AUTHENTICATE;
        }
        return null;
    }

    protected void onActivityResult(int n2, int n3, Intent intent) {
        if (n2 == 1 && n3 == -1) {
            this.finishLogin(intent);
            return;
        }
        super.onActivityResult(n2, n3, intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, "back pressed", this.getIntent().getStringExtra("ACCOUNT_TYPE"));
    }

    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.setContentView(2130903040);
        this.mAccountManager = AccountManager.get((Context)this.getBaseContext());
        this.oldAccountName = this.getIntent().getStringExtra("ACCOUNT_NAME");
        this.mAuthTokenType = "BULLSHIT";
        if (this.oldAccountName != null) {
            ((TextView)this.findViewById(2131492889)).setText((CharSequence)this.oldAccountName);
        }
        boolean bl2 = this.getIntent().getBooleanExtra("ERROR_WITH_ACCOUNT", false);
        String string2 = this.getIntent().getStringExtra("ACCOUNT_TYPE");
        ((TextView)this.findViewById(2131492885)).setText((CharSequence)("SIGN INTO " + string2.toUpperCase()));
        object = "Username";
        if ("derpibooru".equals(string2)) {
            object = "Email";
        }
        ((TextView)this.findViewById(2131492888)).setText((CharSequence)object);
        if (bl2) {
            ((TextView)this.findViewById(2131492887)).setText((CharSequence)"Your account credentials are not valid (according to the server). Please re-input your password.");
        }
        Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "show", string2);
        this.findViewById(2131492884).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, "click done", AuthenticatorActivity.this.getIntent().getStringExtra("ACCOUNT_TYPE"));
                AuthenticatorActivity.this.finish();
            }
        });
        this.findViewById(2131492891).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AuthenticatorActivity.this.submit();
            }
        });
    }

    public void submit() {
        final String string2 = ((TextView)this.findViewById(2131492889)).getText().toString();
        final String string3 = ((TextView)this.findViewById(2131492890)).getText().toString();
        final String string4 = this.getIntent().getStringExtra("ACCOUNT_TYPE");
        if (TextUtils.isEmpty((CharSequence)string2) || TextUtils.isEmpty((CharSequence)string3)) {
            return;
        }
        this.findViewById(2131492892).setVisibility(0);
        this.findViewById(2131492884).setVisibility(8);
        new AsyncTask<String, Void, Intent>(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            protected /* varargs */ Intent doInBackground(String ... bundle) {
                String string22;
                Log.d((String)"udinic", (String)(AuthenticatorActivity.this.TAG + "> Started authenticating"));
                bundle = new Bundle();
                try {
                    string22 = AuthenticatorActivity.getServerAuthenticate(string4).userSignInAndGetAuth(string2, string3, AuthenticatorActivity.this.mAuthTokenType);
                    bundle.putString("authAccount", string2);
                    bundle.putString("accountType", string4);
                    bundle.putString("authtoken", string22);
                    bundle.putString("USER_PASS", string3);
                }
                catch (Exception var2_3) {
                    bundle.putString("ERR_MSG", var2_3.getMessage());
                }
                string22 = new Intent();
                string22.putExtras(bundle);
                return string22;
            }

            /*
             * Enabled aggressive block sorting
             */
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra("ERR_MSG")) {
                    Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "invalid add account", string4);
                    ((ImageView)AuthenticatorActivity.this.findViewById(2131492886)).setImageDrawable(AuthenticatorActivity.this.getResources().getDrawable(2130837618));
                    ((TextView)AuthenticatorActivity.this.findViewById(2131492887)).setText((CharSequence)intent.getStringExtra("ERR_MSG"));
                } else {
                    AuthenticatorActivity.this.finishLogin(intent);
                }
                AuthenticatorActivity.this.findViewById(2131492892).setVisibility(8);
                AuthenticatorActivity.this.findViewById(2131492884).setVisibility(0);
            }
        }.execute((Object[])new String[0]);
    }

}

