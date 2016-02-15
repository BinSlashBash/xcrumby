package com.crumby.lib.authentication;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.res.Resources;
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
import com.crumby.CrDb;
import com.crumby.impl.derpibooru.DerpibooruAuthenticatorService;
import com.crumby.impl.furaffinity.FurAffinityAuthenticatorService;
import com.crumby.lib.events.LoginEvent;
import com.squareup.otto.Bus;

public class AuthenticatorActivity
  extends AccountAuthenticatorActivity
{
  public static final String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
  public static final String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
  public static final String ARG_AUTH_TYPE = "AUTH_TYPE";
  public static final String ARG_ERROR_WITH_ACCOUNT = "ERROR_WITH_ACCOUNT";
  public static final String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
  public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
  public static final String PARAM_USER_PASS = "USER_PASS";
  private static boolean sawAccountLimitAlert;
  private final int REQ_SIGNUP = 1;
  private final String TAG = getClass().getSimpleName();
  private AccountManager mAccountManager;
  private String mAuthTokenType;
  private String oldAccountName;
  
  private void end(String paramString)
  {
    Toast localToast = Toast.makeText(getApplicationContext(), "", 1);
    localToast.setGravity(48, 0, 0);
    TextView localTextView = (TextView)View.inflate(getApplicationContext(), 2130903119, null);
    localTextView.setText("Huzzah! You have signed into " + paramString + "!");
    localToast.setView(localTextView);
    localToast.show();
    finish();
    BusProvider.BUS.get().post(new LoginEvent(paramString));
  }
  
  private void finishLogin(Intent paramIntent)
  {
    Log.d("udinic", this.TAG + "> finishLogin");
    Object localObject = paramIntent.getStringExtra("authAccount");
    String str1 = paramIntent.getStringExtra("USER_PASS");
    String str2 = paramIntent.getStringExtra("accountType");
    localObject = new Account((String)localObject, str2);
    if (!getIntent().getBooleanExtra("IS_ADDING_ACCOUNT", false)) {
      this.mAccountManager.removeAccount(new Account(this.oldAccountName, str2), null, null);
    }
    Log.d("udinic", this.TAG + "> finishLogin > addAccountExplicitly");
    Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "added", str2);
    String str3 = paramIntent.getStringExtra("authtoken");
    String str4 = this.mAuthTokenType;
    this.mAccountManager.addAccountExplicitly((Account)localObject, str1, null);
    this.mAccountManager.setAuthToken((Account)localObject, str4, str3);
    setAccountAuthenticatorResult(paramIntent.getExtras());
    setResult(-1, paramIntent);
    end(str2);
    CrDb.d("viewer", "saw feedback alert");
  }
  
  public static ServerAuthenticate getServerAuthenticate(String paramString)
  {
    if ("derpibooru".equals(paramString)) {
      return DerpibooruAuthenticatorService.SERVER_AUTHENTICATE;
    }
    if ("furaffinity".equals(paramString)) {
      return FurAffinityAuthenticatorService.SERVER_AUTHENTICATE;
    }
    return null;
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 1) && (paramInt2 == -1))
    {
      finishLogin(paramIntent);
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onBackPressed()
  {
    super.onBackPressed();
    Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, "back pressed", getIntent().getStringExtra("ACCOUNT_TYPE"));
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903040);
    this.mAccountManager = AccountManager.get(getBaseContext());
    this.oldAccountName = getIntent().getStringExtra("ACCOUNT_NAME");
    this.mAuthTokenType = "BULLSHIT";
    if (this.oldAccountName != null) {
      ((TextView)findViewById(2131492889)).setText(this.oldAccountName);
    }
    boolean bool = getIntent().getBooleanExtra("ERROR_WITH_ACCOUNT", false);
    String str = getIntent().getStringExtra("ACCOUNT_TYPE");
    ((TextView)findViewById(2131492885)).setText("SIGN INTO " + str.toUpperCase());
    paramBundle = "Username";
    if ("derpibooru".equals(str)) {
      paramBundle = "Email";
    }
    ((TextView)findViewById(2131492888)).setText(paramBundle);
    if (bool) {
      ((TextView)findViewById(2131492887)).setText("Your account credentials are not valid (according to the server). Please re-input your password.");
    }
    Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "show", str);
    findViewById(2131492884).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.NAVIGATION, "click done", AuthenticatorActivity.this.getIntent().getStringExtra("ACCOUNT_TYPE"));
        AuthenticatorActivity.this.finish();
      }
    });
    findViewById(2131492891).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AuthenticatorActivity.this.submit();
      }
    });
  }
  
  public void submit()
  {
    final String str1 = ((TextView)findViewById(2131492889)).getText().toString();
    final String str2 = ((TextView)findViewById(2131492890)).getText().toString();
    final String str3 = getIntent().getStringExtra("ACCOUNT_TYPE");
    if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2))) {
      return;
    }
    findViewById(2131492892).setVisibility(0);
    findViewById(2131492884).setVisibility(8);
    new AsyncTask()
    {
      protected Intent doInBackground(String... paramAnonymousVarArgs)
      {
        Log.d("udinic", AuthenticatorActivity.this.TAG + "> Started authenticating");
        paramAnonymousVarArgs = new Bundle();
        try
        {
          Object localObject = AuthenticatorActivity.getServerAuthenticate(str3).userSignInAndGetAuth(str1, str2, AuthenticatorActivity.this.mAuthTokenType);
          paramAnonymousVarArgs.putString("authAccount", str1);
          paramAnonymousVarArgs.putString("accountType", str3);
          paramAnonymousVarArgs.putString("authtoken", (String)localObject);
          paramAnonymousVarArgs.putString("USER_PASS", str2);
          localObject = new Intent();
          ((Intent)localObject).putExtras(paramAnonymousVarArgs);
          return (Intent)localObject;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            paramAnonymousVarArgs.putString("ERR_MSG", localException.getMessage());
          }
        }
      }
      
      protected void onPostExecute(Intent paramAnonymousIntent)
      {
        if (paramAnonymousIntent.hasExtra("ERR_MSG"))
        {
          Analytics.INSTANCE.newEvent(AnalyticsCategories.ACCOUNT, "invalid add account", str3);
          ((ImageView)AuthenticatorActivity.this.findViewById(2131492886)).setImageDrawable(AuthenticatorActivity.this.getResources().getDrawable(2130837618));
          ((TextView)AuthenticatorActivity.this.findViewById(2131492887)).setText(paramAnonymousIntent.getStringExtra("ERR_MSG"));
        }
        for (;;)
        {
          AuthenticatorActivity.this.findViewById(2131492892).setVisibility(8);
          AuthenticatorActivity.this.findViewById(2131492884).setVisibility(0);
          return;
          AuthenticatorActivity.this.finishLogin(paramAnonymousIntent);
        }
      }
    }.execute(new String[0]);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/authentication/AuthenticatorActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */