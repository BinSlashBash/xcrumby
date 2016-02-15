package com.crumby.lib.fragment.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.widget.firstparty.DisplayError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SwipePageAdapter
  extends FragmentStatePagerAdapter
  implements GalleryConsumer
{
  public static int NOT_FOUND = -1;
  private Context context;
  private LruCache<Integer, GalleryProducer> fragmentProducers;
  private Map<Integer, GalleryViewerFragment> fragments;
  private List<GalleryImage> images;
  private GalleryProducer producer;
  
  public SwipePageAdapter(Activity paramActivity, GalleryProducer paramGalleryProducer, LruCache<Integer, GalleryProducer> paramLruCache)
  {
    super(paramActivity.getFragmentManager());
    this.context = paramActivity.getApplicationContext();
    this.images = new ArrayList();
    this.fragments = new HashMap();
    this.fragmentProducers = paramLruCache;
    setPagerContents(paramGalleryProducer);
  }
  
  private GalleryViewerFragment buildFragmentFromImage(int paramInt)
  {
    GalleryImage localGalleryImage = (GalleryImage)this.images.get(paramInt);
    GalleryViewerFragment localGalleryViewerFragment = FragmentRouter.INSTANCE.getGalleryFragmentInstance(localGalleryImage.getLinkUrl());
    localGalleryViewerFragment.setImage(localGalleryImage);
    this.fragments.put(Integer.valueOf(paramInt), localGalleryViewerFragment);
    return localGalleryViewerFragment;
  }
  
  public boolean addImages(List<GalleryImage> paramList)
  {
    this.images.addAll(paramList);
    notifyDataSetChanged();
    return false;
  }
  
  public void clear()
  {
    if (this.producer != null) {
      this.producer.removeConsumer(this);
    }
    this.producer = null;
    this.images.clear();
    notifyDataSetChanged();
  }
  
  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    GalleryViewerFragment localGalleryViewerFragment = (GalleryViewerFragment)this.fragments.remove(Integer.valueOf(paramInt));
    if ((localGalleryViewerFragment != null) && (localGalleryViewerFragment.getProducer() != null) && (this.fragmentProducers != null) && (this.fragmentProducers.get(Integer.valueOf(paramInt)) != null)) {
      this.fragmentProducers.put(Integer.valueOf(paramInt), localGalleryViewerFragment.getProducer());
    }
    super.destroyItem(paramViewGroup, paramInt, paramObject);
  }
  
  public void dispatchWaitResume()
  {
    Iterator localIterator = this.fragments.values().iterator();
    while (localIterator.hasNext()) {
      ((GalleryViewerFragment)localIterator.next()).waitOnResume();
    }
  }
  
  public void finishLoading() {}
  
  public int getCount()
  {
    return this.images.size();
  }
  
  public GalleryViewerFragment getFragment(int paramInt)
  {
    return (GalleryViewerFragment)this.fragments.get(Integer.valueOf(paramInt));
  }
  
  public Fragment getItem(int paramInt)
  {
    Object localObject2 = null;
    Object localObject1;
    if (paramInt < this.images.size())
    {
      localObject2 = (GalleryViewerFragment)this.fragments.get(Integer.valueOf(paramInt));
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = buildFragmentFromImage(paramInt);
      }
      if (this.fragmentProducers.get(Integer.valueOf(paramInt)) == null) {
        break label102;
      }
      ((GalleryViewerFragment)localObject1).setProducer((GalleryProducer)this.fragmentProducers.get(Integer.valueOf(paramInt)));
      localObject2 = localObject1;
    }
    for (;;)
    {
      if (paramInt > this.images.size() - 2) {
        this.producer.requestFetch();
      }
      return (Fragment)localObject2;
      label102:
      localObject2 = localObject1;
      if (((GalleryViewerFragment)localObject1).getProducer() != null)
      {
        this.fragmentProducers.put(Integer.valueOf(paramInt), ((GalleryViewerFragment)localObject1).getProducer());
        localObject2 = localObject1;
      }
    }
  }
  
  public CharSequence getPageTitle(int paramInt)
  {
    if (this.images.size() == 1) {
      return "";
    }
    return paramInt + "";
  }
  
  public void omniSearchIsNotShowing()
  {
    Iterator localIterator = this.fragments.values().iterator();
    while (localIterator.hasNext())
    {
      GalleryViewerFragment localGalleryViewerFragment = (GalleryViewerFragment)localIterator.next();
      if ((localGalleryViewerFragment instanceof GalleryImageFragment)) {
        ((GalleryImageFragment)localGalleryViewerFragment).omniSearchIsNotShowingHack();
      }
    }
  }
  
  public void omniSearchIsShowing()
  {
    Iterator localIterator = this.fragments.values().iterator();
    while (localIterator.hasNext())
    {
      GalleryViewerFragment localGalleryViewerFragment = (GalleryViewerFragment)localIterator.next();
      if ((localGalleryViewerFragment instanceof GalleryImageFragment)) {
        ((GalleryImageFragment)localGalleryViewerFragment).omniSearchIsShowingHack();
      }
    }
  }
  
  public void redrawFragments()
  {
    Iterator localIterator = this.fragments.values().iterator();
    while (localIterator.hasNext()) {
      ((GalleryViewerFragment)localIterator.next()).redraw();
    }
  }
  
  public int removeFragmentProducerByHost(GalleryImage paramGalleryImage)
  {
    Iterator localIterator = this.fragments.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (((GalleryViewerFragment)localEntry.getValue()).getImage() == paramGalleryImage)
      {
        removeProducer(((Integer)localEntry.getKey()).intValue());
        return ((Integer)localEntry.getKey()).intValue();
      }
    }
    return NOT_FOUND;
  }
  
  public void removeProducer(int paramInt)
  {
    this.fragmentProducers.remove(Integer.valueOf(paramInt));
  }
  
  public void setPagerContents(GalleryProducer paramGalleryProducer)
  {
    clear();
    this.producer = paramGalleryProducer;
    if (!this.producer.isInitialized()) {
      this.producer.initialize(this, null, null);
    }
    for (;;)
    {
      this.producer.requestStartFetch();
      return;
      this.producer.addConsumer(this);
      addImages(this.producer.getImages());
    }
  }
  
  public void setStartingFragment(GalleryViewerFragment paramGalleryViewerFragment, int paramInt)
  {
    if (paramGalleryViewerFragment == null) {
      return;
    }
    this.fragments.put(Integer.valueOf(paramInt), paramGalleryViewerFragment);
  }
  
  public void showError(Exception paramException)
  {
    Analytics.INSTANCE.newError(DisplayError.VIEWPAGER_CANNOT_FETCH, paramException.getMessage());
    Toast.makeText(this.context, "Could not fetch additional pages. " + paramException.getMessage(), 1).show();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/SwipePageAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */