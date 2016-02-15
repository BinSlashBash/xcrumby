package com.crumby.lib.download;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.lib.widget.firstparty.main_menu.DownloadMenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DownloadListAdapter
  extends BaseAdapter
  implements ImageDownloadListener, AbsListView.OnScrollListener, ScrollStateKeeper
{
  int currentScrollState;
  ArrayList<ImageDownload> downloads;
  int greatestViewPosition;
  private final TextView header;
  LayoutInflater inflater;
  private final ListView list;
  private final Runnable refresh;
  private final Handler refreshHandler;
  
  public DownloadListAdapter(ListView paramListView, LayoutInflater paramLayoutInflater, TextView paramTextView)
  {
    this.list = paramListView;
    this.inflater = paramLayoutInflater;
    this.downloads = new ArrayList();
    this.refreshHandler = new Handler();
    this.refresh = new Runnable()
    {
      public void run()
      {
        DownloadListAdapter.this.currentScrollState = 0;
        DownloadListAdapter.this.notifyDataSetChanged();
      }
    };
    this.header = paramTextView;
    paramTextView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "hide header", null);
        paramAnonymousView.setVisibility(8);
      }
    });
    paramListView.setOnScrollListener(this);
  }
  
  public boolean canShowListItems()
  {
    return this.currentScrollState != 2;
  }
  
  public int getCount()
  {
    return this.downloads.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.downloads.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return 0L;
  }
  
  public int getScrollState()
  {
    return this.currentScrollState;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    paramViewGroup = paramView;
    if (paramView == null)
    {
      CrDb.d("download list adapter", "creating new view for position " + paramInt);
      paramViewGroup = this.inflater.inflate(2130903100, null);
      ((DownloadMenuItem)paramViewGroup).setKeeper(this);
    }
    CrDb.d("download list adapter", "getting view for position " + paramInt);
    paramView = (DownloadMenuItem)paramViewGroup;
    paramView.update((ImageDownload)this.downloads.get(paramInt), paramInt);
    return paramView;
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    this.refreshHandler.removeCallbacks(this.refresh);
    if ((this.currentScrollState == 2) && (paramInt != 2))
    {
      int i = this.list.getLastVisiblePosition();
      if (this.greatestViewPosition < i)
      {
        this.greatestViewPosition = i;
        Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "scroll stop", i + "");
      }
      i = 0;
      while (i < this.list.getChildCount())
      {
        ((DownloadMenuItem)this.list.getChildAt(i)).invalidateDownloadChangeFlag();
        i += 1;
      }
      notifyDataSetChanged();
    }
    this.currentScrollState = paramInt;
  }
  
  public void pause()
  {
    this.currentScrollState = 2;
  }
  
  public void refresh()
  {
    this.refreshHandler.removeCallbacks(this.refresh);
    this.refreshHandler.postDelayed(this.refresh, 100L);
  }
  
  public void terminate()
  {
    this.downloads.clear();
    this.header.setVisibility(8);
    notifyDataSetChanged();
  }
  
  public void unpause()
  {
    this.currentScrollState = 0;
    refresh();
  }
  
  public void update(ImageDownload paramImageDownload)
  {
    CrDb.d("download list adapter", "got refresh request for " + paramImageDownload.getDownloadUri());
    if (this.currentScrollState == 2)
    {
      CrDb.d("download list adapter", "list view not ready for " + paramImageDownload.getDownloadUri());
      return;
    }
    int i = this.list.getFirstVisiblePosition();
    int j = this.list.getLastVisiblePosition();
    while (i <= j)
    {
      if (paramImageDownload == getItem(i))
      {
        CrDb.d("download list adapter", "refreshing view at position" + i + " for " + paramImageDownload.getDownloadUri());
        refresh();
        return;
      }
      i += 1;
    }
    CrDb.d("download list adapter", "list view is not showing download at the moment " + paramImageDownload.getDownloadUri());
  }
  
  public void update(ArrayList<ImageDownload> paramArrayList)
  {
    CrDb.d("download list adapter", "adding new downloads of size: " + paramArrayList.size());
    paramArrayList.size();
    paramArrayList.removeAll(this.downloads);
    int i = 0;
    Collections.reverse(paramArrayList);
    paramArrayList = paramArrayList.iterator();
    Object localObject;
    while (paramArrayList.hasNext())
    {
      localObject = (ImageDownload)paramArrayList.next();
      if (((ImageDownload)localObject).isValid()) {
        this.downloads.add(0, localObject);
      } else {
        i += 1;
      }
    }
    if (i != 0)
    {
      if (this.header.getVisibility() != 0)
      {
        this.header.setVisibility(0);
        this.header.setScaleY(0.5F);
        this.header.setScaleX(0.5F);
        this.header.setAlpha(0.0F);
        this.header.animate().alpha(1.0F).scaleY(1.0F).scaleX(1.0F).setStartDelay(400L);
      }
      localObject = "<b>" + (i + 0) + " images were ignored.</b><br/>";
      paramArrayList = (ArrayList<ImageDownload>)localObject;
      if (i > 0)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "invalid", i + "");
        paramArrayList = (String)localObject + "For this website, you can only download images from their page, not in the gallery.<br/>";
      }
      localObject = paramArrayList;
      if (0 > 0)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "already saved", 0 + "");
        localObject = paramArrayList + 0 + " images are already in your recent downloads.</i><br/>";
      }
      this.header.setText(Html.fromHtml((String)localObject));
    }
    for (;;)
    {
      notifyDataSetChanged();
      return;
      this.header.setVisibility(8);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/download/DownloadListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */