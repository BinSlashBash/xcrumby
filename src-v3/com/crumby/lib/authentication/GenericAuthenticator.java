package com.crumby.lib.authentication;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.GalleryViewer;

public class GenericAuthenticator extends AbstractAccountAuthenticator {
    private String TAG;
    private final Context mContext;
    private final ServerAuthenticate serverAuthenticate;

    public GenericAuthenticator(Context context, ServerAuthenticate serverAuthenticate) {
        super(context);
        this.TAG = "Authenticator";
        this.mContext = context;
        this.serverAuthenticate = serverAuthenticate;
    }

    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Log.d("udinic", this.TAG + "> addAccount");
        if (options.getBoolean(GalleryViewer.DO_NOT_CREATE_ACCOUNT)) {
            return null;
        }
        Intent intent = new Intent(this.mContext, AuthenticatorActivity.class);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra("accountAuthenticatorResponse", response);
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent", intent);
        Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "authenticator adding", accountType);
        return bundle;
    }

    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.d("udinic", this.TAG + "> getAuthToken");
        AccountManager am = AccountManager.get(this.mContext);
        String authToken = am.peekAuthToken(account, authTokenType);
        Log.d("udinic", this.TAG + "> peekAuthToken returned - " + authToken);
        if (TextUtils.isEmpty(authToken) || options.getBoolean(GalleryViewer.FORCE_RELOGIN)) {
            String password = am.getPassword(account);
            if (password != null) {
                try {
                    Log.d("udinic", this.TAG + "> re-authenticating with the existing password");
                    authToken = this.serverAuthenticate.userSignInAndGetAuth(account.name, password, authTokenType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (!TextUtils.isEmpty(authToken)) {
            Bundle result = new Bundle();
            result.putString("authAccount", account.name);
            result.putString("accountType", account.type);
            result.putString("authtoken", authToken);
            return result;
        } else if (options.getBoolean(GalleryViewer.DO_NOT_CREATE_ACCOUNT)) {
            return null;
        } else {
            Intent intent = new Intent(this.mContext, AuthenticatorActivity.class);
            intent.putExtra("accountAuthenticatorResponse", response);
            intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, account.type);
            intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
            intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_NAME, account.name);
            Bundle bundle = new Bundle();
            bundle.putParcelable("intent", intent);
            return bundle;
        }
    }

    public String getAuthTokenLabel(String authTokenType) {
        return authTokenType + " (Label)";
    }

    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        Bundle result = new Bundle();
        result.putBoolean("booleanResult", false);
        return result;
    }

    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }
}
