package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.lib.events.ReloadFragmentEvent;
import com.crumby.lib.events.StopLoadingFragmentEvent;
import com.squareup.otto.Bus;

public class FragmentStatusButton
  extends ImageButton
  implements View.OnClickListener
{
  private boolean hardRefresh;
  private final Drawable refreshDrawable = getResources().getDrawable(2130837635);
  private boolean refreshMode;
  private final Drawable stopDrawable = getResources().getDrawable(2130837610);
  
  public FragmentStatusButton(Context paramContext)
  {
    super(paramContext);
    setOnClickListener(this);
  }
  
  public FragmentStatusButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOnClickListener(this);
  }
  
  public FragmentStatusButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setOnClickListener(this);
  }
  
  public void onClick(View paramView)
  {
    if (this.refreshMode)
    {
      BusProvider.BUS.get().post(new ReloadFragmentEvent(false));
      Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "refresh", null);
      return;
    }
    BusProvider.BUS.get().post(new StopLoadingFragmentEvent());
    Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "stop", null);
    refreshMode();
  }
  
  public void refreshMode()
  {
    setImageDrawable(this.refreshDrawable);
    this.refreshMode = true;
  }
  
  public void stopLoadingMode()
  {
    setImageDrawable(this.stopDrawable);
    this.refreshMode = false;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/FragmentStatusButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */