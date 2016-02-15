package com.crumby.impl.e621;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.lib.authentication.UserData;
import com.crumby.lib.events.UrlChangeEvent;
import com.squareup.otto.Bus;

public class e621AccountLayout
  extends LinearLayout
  implements UserData
{
  String user;
  
  public e621AccountLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public e621AccountLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public e621AccountLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void fillWith(Object paramObject)
  {
    this.user = ((String)paramObject);
    ((TextView)findViewById(2131492957)).setText("Logged in as: " + this.user);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    findViewById(2131492958).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newNavigationEvent("account", "e621 favorites");
        BusProvider.BUS.get().post(new UrlChangeEvent("https://e621.net/post?tags=" + Uri.encode(new StringBuilder().append("fav:").append(e621AccountLayout.this.user).toString())));
      }
    });
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/e621/e621AccountLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */