package com.crumby.lib.authentication;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.derpibooru.DerpibooruAuthenticatorService;
import com.crumby.impl.derpibooru.DerpibooruFragment;
import com.crumby.impl.furaffinity.FurAffinityAuthenticatorService;
import com.crumby.impl.furaffinity.FurAffinityFragment;
import com.crumby.lib.events.LoginEvent;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {
    public static final String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String ARG_AUTH_TYPE = "AUTH_TYPE";
    public static final String ARG_ERROR_WITH_ACCOUNT = "ERROR_WITH_ACCOUNT";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
    public static final String PARAM_USER_PASS = "USER_PASS";
    private static boolean sawAccountLimitAlert;
    private final int REQ_SIGNUP;
    private final String TAG;
    private AccountManager mAccountManager;
    private String mAuthTokenType;
    private String oldAccountName;

    /* renamed from: com.crumby.lib.authentication.AuthenticatorActivity.1 */
    class C00901 implements OnClickListener {
        C00901() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, "click done", AuthenticatorActivity.this.getIntent().getStringExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE));
            AuthenticatorActivity.this.finish();
        }
    }

    /* renamed from: com.crumby.lib.authentication.AuthenticatorActivity.2 */
    class C00912 implements OnClickListener {
        C00912() {
        }

        public void onClick(View v) {
            AuthenticatorActivity.this.submit();
        }
    }

    /* renamed from: com.crumby.lib.authentication.AuthenticatorActivity.3 */
    class C00923 extends AsyncTask<String, Void, Intent> {
        final /* synthetic */ String val$accountType;
        final /* synthetic */ String val$userName;
        final /* synthetic */ String val$userPass;

        C00923(String str, String str2, String str3) {
            this.val$accountType = str;
            this.val$userName = str2;
            this.val$userPass = str3;
        }

        protected Intent doInBackground(String... params) {
            Log.d("udinic", AuthenticatorActivity.this.TAG + "> Started authenticating");
            Bundle data = new Bundle();
            try {
                String authtoken = AuthenticatorActivity.getServerAuthenticate(this.val$accountType).userSignInAndGetAuth(this.val$userName, this.val$userPass, AuthenticatorActivity.this.mAuthTokenType);
                data.putString("authAccount", this.val$userName);
                data.putString("accountType", this.val$accountType);
                data.putString("authtoken", authtoken);
                data.putString(AuthenticatorActivity.PARAM_USER_PASS, this.val$userPass);
            } catch (Exception e) {
                data.putString(AuthenticatorActivity.KEY_ERROR_MESSAGE, e.getMessage());
            }
            Intent res = new Intent();
            res.putExtras(data);
            return res;
        }

        protected void onPostExecute(Intent intent) {
            if (intent.hasExtra(AuthenticatorActivity.KEY_ERROR_MESSAGE)) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "invalid add account", this.val$accountType);
                ((ImageView) AuthenticatorActivity.this.findViewById(C0065R.id.login_image)).setImageDrawable(AuthenticatorActivity.this.getResources().getDrawable(C0065R.drawable.ic_action_error_dark));
                ((TextView) AuthenticatorActivity.this.findViewById(C0065R.id.login_message)).setText(intent.getStringExtra(AuthenticatorActivity.KEY_ERROR_MESSAGE));
            } else {
                AuthenticatorActivity.this.finishLogin(intent);
            }
            AuthenticatorActivity.this.findViewById(C0065R.id.loading_login).setVisibility(8);
            AuthenticatorActivity.this.findViewById(C0065R.id.authenticator_activity).setVisibility(0);
        }
    }

    public AuthenticatorActivity() {
        this.REQ_SIGNUP = 1;
        this.TAG = getClass().getSimpleName();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0065R.layout.act_login);
        this.mAccountManager = AccountManager.get(getBaseContext());
        this.oldAccountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        this.mAuthTokenType = "BULLSHIT";
        if (this.oldAccountName != null) {
            ((TextView) findViewById(C0065R.id.account_name)).setText(this.oldAccountName);
        }
        boolean errorWithAccount = getIntent().getBooleanExtra(ARG_ERROR_WITH_ACCOUNT, false);
        String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
        ((TextView) findViewById(C0065R.id.login_form_title)).setText("SIGN INTO " + accountType.toUpperCase());
        String userTitle = "Username";
        if (DerpibooruFragment.BREADCRUMB_NAME.equals(accountType)) {
            userTitle = "Email";
        }
        ((TextView) findViewById(C0065R.id.login_form_user_title)).setText(userTitle);
        if (errorWithAccount) {
            ((TextView) findViewById(C0065R.id.login_message)).setText("Your account credentials are not valid (according to the server). Please re-input your password.");
        }
        Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "show", accountType);
        findViewById(C0065R.id.authenticator_activity).setOnClickListener(new C00901());
        findViewById(C0065R.id.submit_account).setOnClickListener(new C00912());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == -1) {
            finishLogin(data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static ServerAuthenticate getServerAuthenticate(String accountType) {
        if (DerpibooruFragment.BREADCRUMB_NAME.equals(accountType)) {
            return DerpibooruAuthenticatorService.SERVER_AUTHENTICATE;
        }
        if (FurAffinityFragment.ACCOUNT_TYPE.equals(accountType)) {
            return FurAffinityAuthenticatorService.SERVER_AUTHENTICATE;
        }
        return null;
    }

    public void onBackPressed() {
        super.onBackPressed();
        Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, "back pressed", getIntent().getStringExtra(ARG_ACCOUNT_TYPE));
    }

    public void submit() {
        String userName = ((TextView) findViewById(C0065R.id.account_name)).getText().toString();
        String userPass = ((TextView) findViewById(C0065R.id.account_password)).getText().toString();
        String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPass)) {
            findViewById(C0065R.id.loading_login).setVisibility(0);
            findViewById(C0065R.id.authenticator_activity).setVisibility(8);
            new C00923(accountType, userName, userPass).execute(new String[0]);
        }
    }

    private void finishLogin(Intent intent) {
        Log.d("udinic", this.TAG + "> finishLogin");
        String accountName = intent.getStringExtra("authAccount");
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        String accountType = intent.getStringExtra("accountType");
        Account account = new Account(accountName, accountType);
        if (!getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            this.mAccountManager.removeAccount(new Account(this.oldAccountName, accountType), null, null);
        }
        Log.d("udinic", this.TAG + "> finishLogin > addAccountExplicitly");
        Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "added", accountType);
        String authtoken = intent.getStringExtra("authtoken");
        String authtokenType = this.mAuthTokenType;
        this.mAccountManager.addAccountExplicitly(account, accountPassword, null);
        this.mAccountManager.setAuthToken(account, authtokenType, authtoken);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(-1, intent);
        end(accountType);
        CrDb.m0d("viewer", "saw feedback alert");
    }

    private void end(String accountType) {
        Toast toast = Toast.makeText(getApplicationContext(), UnsupportedUrlFragment.DISPLAY_NAME, 1);
        toast.setGravity(48, 0, 0);
        TextView view = (TextView) View.inflate(getApplicationContext(), C0065R.layout.toast_alert_success, null);
        view.setText("Huzzah! You have signed into " + accountType + "!");
        toast.setView(view);
        toast.show();
        finish();
        BusProvider.BUS.get().post(new LoginEvent(accountType));
    }
}
