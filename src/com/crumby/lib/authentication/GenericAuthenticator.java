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

public class GenericAuthenticator
  extends AbstractAccountAuthenticator
{
  private String TAG = "Authenticator";
  private final Context mContext;
  private final ServerAuthenticate serverAuthenticate;
  
  public GenericAuthenticator(Context paramContext, ServerAuthenticate paramServerAuthenticate)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.serverAuthenticate = paramServerAuthenticate;
  }
  
  public Bundle addAccount(AccountAuthenticatorResponse paramAccountAuthenticatorResponse, String paramString1, String paramString2, String[] paramArrayOfString, Bundle paramBundle)
    throws NetworkErrorException
  {
    Log.d("udinic", this.TAG + "> addAccount");
    if (paramBundle.getBoolean("doNotCreateAccount")) {
      return null;
    }
    paramArrayOfString = new Intent(this.mContext, AuthenticatorActivity.class);
    paramArrayOfString.putExtra("ACCOUNT_TYPE", paramString1);
    paramArrayOfString.putExtra("AUTH_TYPE", paramString2);
    paramArrayOfString.putExtra("IS_ADDING_ACCOUNT", true);
    paramArrayOfString.putExtra("accountAuthenticatorResponse", paramAccountAuthenticatorResponse);
    paramAccountAuthenticatorResponse = new Bundle();
    paramAccountAuthenticatorResponse.putParcelable("intent", paramArrayOfString);
    Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "authenticator adding", paramString1);
    return paramAccountAuthenticatorResponse;
  }
  
  public Bundle confirmCredentials(AccountAuthenticatorResponse paramAccountAuthenticatorResponse, Account paramAccount, Bundle paramBundle)
    throws NetworkErrorException
  {
    return null;
  }
  
  public Bundle editProperties(AccountAuthenticatorResponse paramAccountAuthenticatorResponse, String paramString)
  {
    return null;
  }
  
  public Bundle getAuthToken(AccountAuthenticatorResponse paramAccountAuthenticatorResponse, Account paramAccount, String paramString, Bundle paramBundle)
    throws NetworkErrorException
  {
    Log.d("udinic", this.TAG + "> getAuthToken");
    Object localObject = AccountManager.get(this.mContext);
    String str3 = ((AccountManager)localObject).peekAuthToken(paramAccount, paramString);
    Log.d("udinic", this.TAG + "> peekAuthToken returned - " + str3);
    String str1;
    if (!TextUtils.isEmpty(str3))
    {
      str1 = str3;
      if (!paramBundle.getBoolean("forceRelogin")) {}
    }
    else
    {
      localObject = ((AccountManager)localObject).getPassword(paramAccount);
      str1 = str3;
      if (localObject == null) {}
    }
    try
    {
      Log.d("udinic", this.TAG + "> re-authenticating with the existing password");
      str1 = this.serverAuthenticate.userSignInAndGetAuth(paramAccount.name, (String)localObject, paramString);
      if (!TextUtils.isEmpty(str1))
      {
        paramAccountAuthenticatorResponse = new Bundle();
        paramAccountAuthenticatorResponse.putString("authAccount", paramAccount.name);
        paramAccountAuthenticatorResponse.putString("accountType", paramAccount.type);
        paramAccountAuthenticatorResponse.putString("authtoken", str1);
        return paramAccountAuthenticatorResponse;
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        localException.printStackTrace();
        String str2 = str3;
      }
      if (paramBundle.getBoolean("doNotCreateAccount")) {
        return null;
      }
      paramBundle = new Intent(this.mContext, AuthenticatorActivity.class);
      paramBundle.putExtra("accountAuthenticatorResponse", paramAccountAuthenticatorResponse);
      paramBundle.putExtra("ACCOUNT_TYPE", paramAccount.type);
      paramBundle.putExtra("AUTH_TYPE", paramString);
      paramBundle.putExtra("ACCOUNT_NAME", paramAccount.name);
      paramAccountAuthenticatorResponse = new Bundle();
      paramAccountAuthenticatorResponse.putParcelable("intent", paramBundle);
    }
    return paramAccountAuthenticatorResponse;
  }
  
  public String getAuthTokenLabel(String paramString)
  {
    return paramString + " (Label)";
  }
  
  public Bundle hasFeatures(AccountAuthenticatorResponse paramAccountAuthenticatorResponse, Account paramAccount, String[] paramArrayOfString)
    throws NetworkErrorException
  {
    paramAccountAuthenticatorResponse = new Bundle();
    paramAccountAuthenticatorResponse.putBoolean("booleanResult", false);
    return paramAccountAuthenticatorResponse;
  }
  
  public Bundle updateCredentials(AccountAuthenticatorResponse paramAccountAuthenticatorResponse, Account paramAccount, String paramString, Bundle paramBundle)
    throws NetworkErrorException
  {
    return null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/authentication/GenericAuthenticator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */