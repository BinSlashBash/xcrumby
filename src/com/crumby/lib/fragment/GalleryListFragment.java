package com.crumby.lib.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.ErrorView;

public abstract class GalleryListFragment
  extends GalleryViewerFragment
  implements GalleryList, GalleryClickHandler
{
  public static int MINIMUM_THUMBNAIL_WIDTH = 100;
  public static int THUMBNAIL_HEIGHT = 150;
  protected GalleryListAdapter adapter;
  boolean clicked;
  protected ViewGroup description;
  private ErrorView errorView;
  protected AbsListView list;
  
  private void attachImageClickListener()
  {
    ImageClickListener localImageClickListener = new ImageClickListener(this, ((GalleryViewer)getActivity()).getMultiSelect(), "grid");
    this.list.setOnItemClickListener(localImageClickListener);
    this.list.setOnItemLongClickListener(localImageClickListener);
  }
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    if (paramGalleryImage.isALinkToGallery())
    {
      postUrlChangeToBus(paramGalleryImage.getLinkUrl(), null);
      return;
    }
    postUrlChangeToBusWithProducer(paramGalleryImage);
  }
  
  public View createFragmentView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = inflateAbslistView(paramLayoutInflater);
    this.errorView = ((ErrorView)paramLayoutInflater.findViewById(2131492959));
    attachImageClickListener();
    initializeAdapter();
    return paramLayoutInflater;
  }
  
  public GalleryListAdapter createListAdapter()
  {
    return new GalleryListAdapter();
  }
  
  public void deferSetDescription(String paramString) {}
  
  public GalleryConsumer getConsumer()
  {
    return this.adapter;
  }
  
  public Context getContext()
  {
    return getActivity();
  }
  
  public ErrorView getErrorView()
  {
    return this.errorView;
  }
  
  public AbsListView getList()
  {
    return this.list;
  }
  
  public void goToImage(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    if (this.clicked) {
      return;
    }
    this.clicked = true;
    applyGalleryImageChange(paramView, paramGalleryImage, paramInt);
  }
  
  public void hideClutter() {}
  
  protected View inflateAbslistView(LayoutInflater paramLayoutInflater)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903076, null);
    this.list = ((AbsListView)paramLayoutInflater.findViewById(2131492982));
    return paramLayoutInflater;
  }
  
  protected void initializeAdapter()
  {
    this.adapter = createListAdapter();
    this.adapter.initialize(this);
    this.adapter.startFetching();
    this.list.setClipChildren(false);
    this.list.setAdapter(this.adapter);
    this.list.setOnScrollListener(this.adapter);
  }
  
  public void onDestroyView()
  {
    if (this.adapter != null) {
      this.adapter.destroy();
    }
    if (getView() != null) {
      ((ViewGroup)getView()).removeView(this.list);
    }
    this.list.setAdapter(null);
    this.list = null;
    this.adapter = null;
    super.onDestroyView();
  }
  
  public void onResume()
  {
    super.onResume();
    resume();
  }
  
  public void prepareForRefresh() {}
  
  public void redraw()
  {
    super.redraw();
    if (this.adapter != null) {
      this.adapter.notifyDataSetChanged();
    }
  }
  
  public void resume()
  {
    if ((this.producer != null) && (!checkIfAllowedToResume())) {
      scrollToImageInList(this.producer.getCurrentImageFocus());
    }
  }
  
  public void scrollToImageInList(int paramInt)
  {
    if (paramInt == -1) {
      return;
    }
    final int i = this.adapter.prepareHighlight(paramInt);
    CrDb.d("viewer fragment", "setting to:" + paramInt);
    this.list.setSelection(paramInt);
    this.list.postDelayed(new Runnable()
    {
      public void run()
      {
        if ((GalleryListFragment.this.getActivity() == null) || (GalleryListFragment.this.list == null)) {
          return;
        }
        GalleryListFragment.this.list.setSelection(i);
      }
    }, 500L);
  }
  
  public void setUserVisibleHint(boolean paramBoolean)
  {
    super.setUserVisibleHint(paramBoolean);
    if (this.adapter != null)
    {
      if (paramBoolean) {
        this.adapter.notifyDataSetChanged();
      }
    }
    else {
      return;
    }
    this.adapter.cancelPicassoLoad();
  }
  
  public void showClutter() {}
  
  public void showError(DisplayError paramDisplayError, String paramString1, String paramString2)
  {
    if (this.errorView != null) {
      this.errorView.show(paramDisplayError, paramString1, paramString2);
    }
  }
  
  public void stopLoading()
  {
    this.adapter.stopLoading();
  }
  
  public boolean undo()
  {
    return (this.errorView != null) && (this.errorView.close());
  }
  
  public boolean willAllowPaging(MotionEvent paramMotionEvent)
  {
    return false;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/GalleryListFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */