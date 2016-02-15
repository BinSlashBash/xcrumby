package com.crumby.impl.furaffinity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.lib.authentication.UserData;
import com.uservoice.uservoicesdk.UserVoice;

public class FurAffinityAccountLayout
  extends LinearLayout
  implements UserData
{
  public FurAffinityAccountLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public FurAffinityAccountLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FurAffinityAccountLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void fillWith(Object paramObject)
  {
    ((TextView)findViewById(2131492979)).setText("Logged in as: " + (String)paramObject);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    findViewById(2131492980).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newNavigationEvent("uservoice", "furaffinity click");
        UserVoice.launchForum(FurAffinityAccountLayout.this.getContext());
      }
    });
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/furaffinity/FurAffinityAccountLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */