package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.crumby.CrumbyApp;
import com.crumby.ViewIdGenerator;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.adapter.SwipePageAdapter;

public class HackyViewPager
  extends ViewPager
{
  public HackyViewPager(Context paramContext)
  {
    super(paramContext);
    setPageMargin((int)CrumbyApp.convertDpToPx(getResources(), 20.0F));
  }
  
  public HackyViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setPageMargin((int)CrumbyApp.convertDpToPx(getResources(), 20.0F));
  }
  
  private boolean currentFragmentAllowsPaging(MotionEvent paramMotionEvent)
  {
    try
    {
      boolean bool = ((SwipePageAdapter)getAdapter()).getFragment(getCurrentItem()).willAllowPaging(paramMotionEvent);
      return bool;
    }
    catch (NullPointerException paramMotionEvent) {}
    return false;
  }
  
  public void initialize(SwipePageAdapter paramSwipePageAdapter, ViewPager.OnPageChangeListener paramOnPageChangeListener, int paramInt)
  {
    setId(ViewIdGenerator.INSTANCE.generateViewId());
    setAdapter(paramSwipePageAdapter);
    setOnPageChangeListener(paramOnPageChangeListener);
    setCurrentItem(paramInt);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    try
    {
      boolean bool = super.onInterceptTouchEvent(paramMotionEvent);
      return bool;
    }
    catch (IllegalArgumentException paramMotionEvent)
    {
      paramMotionEvent.printStackTrace();
    }
    return false;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1 - getPageMargin(), paramInt2, paramInt3 - getPageMargin(), paramInt4);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/HackyViewPager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */