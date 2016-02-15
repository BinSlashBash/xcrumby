package com.crumby;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.Window;
import com.crumby.lib.events.PagerEvent;
import com.crumby.lib.events.TerminateEvent;
import com.crumby.lib.fragment.adapter.SwipePageAdapter;
import com.crumby.lib.widget.thirdparty.ZoomOutPageTransformer;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ImageViewer
  extends Activity
{
  private SwipePageAdapter adapter;
  private ViewPager pager;
  
  public void onBackPressed()
  {
    Intent localIntent = new Intent(this, GalleryViewer.class);
    localIntent.setFlags(131072);
    startActivity(localIntent);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getWindow().requestFeature(9);
    setContentView(2130903091);
    this.pager = ((ViewPager)findViewById(2131492993));
    this.pager.setPageTransformer(false, new ZoomOutPageTransformer());
    CrDb.d("image viewer", "created!");
    BusProvider.BUS.get().register(this);
    paramBundle = getActionBar();
    paramBundle.setBackgroundDrawable(getResources().getDrawable(2131427504));
    paramBundle.setDisplayShowHomeEnabled(false);
    paramBundle.setIcon(null);
    onBackPressed();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    CrDb.d("image viewer", "destroyed!");
    BusProvider.BUS.get().unregister(this);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    onBackPressed();
    return true;
  }
  
  @Subscribe
  public void onPagerEvent(PagerEvent paramPagerEvent) {}
  
  @Subscribe
  public void onTerminateEvent(TerminateEvent paramTerminateEvent)
  {
    finish();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/ImageViewer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */