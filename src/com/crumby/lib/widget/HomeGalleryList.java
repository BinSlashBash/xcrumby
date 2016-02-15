package com.crumby.lib.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.GalleryClickHandler;
import com.crumby.lib.fragment.ImageClickListener;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.ErrorView;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import com.squareup.otto.Bus;
import twowayview.TwoWayView;
import twowayview.TwoWayView.Orientation;

public class HomeGalleryList
  extends LinearLayout
  implements GalleryList, GalleryClickHandler
{
  private GalleryListAdapter adapter;
  private Button button;
  private boolean clicked;
  private boolean doNotStart;
  private ErrorView errorView;
  private boolean fetchOnVisible;
  private GalleryImage image;
  private TwoWayView list;
  private MultiSelect multiselect;
  private GalleryProducer producer;
  private View progress;
  
  public HomeGalleryList(Context paramContext)
  {
    super(paramContext);
  }
  
  public HomeGalleryList(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public HomeGalleryList(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void cancel()
  {
    this.doNotStart = true;
    if (this.producer != null) {
      this.producer.haltDownload();
    }
  }
  
  public Bundle getArguments()
  {
    return null;
  }
  
  public GalleryImage getImage()
  {
    return this.image;
  }
  
  public AdapterView getList()
  {
    return this.list;
  }
  
  public GalleryProducer getProducer()
  {
    return this.producer;
  }
  
  public boolean getUserVisibleHint()
  {
    return isShown();
  }
  
  public void goToImage(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    if (this.clicked) {
      return;
    }
    this.clicked = true;
    this.producer.shareAndSetCurrentImageFocus(paramGalleryImage.getPosition());
    BusProvider.BUS.get().post(new UrlChangeEvent(paramGalleryImage.getLinkUrl(), this.producer));
  }
  
  public void indicateProgressChange(float paramFloat)
  {
    if (paramFloat == 1.0F)
    {
      this.progress.setVisibility(8);
      return;
    }
    this.progress.setVisibility(0);
  }
  
  public void initialize(final GalleryImage paramGalleryImage, GalleryProducer paramGalleryProducer, MultiSelect paramMultiSelect, boolean paramBoolean)
  {
    if (this.doNotStart) {
      return;
    }
    this.list = ((TwoWayView)findViewById(2131492986));
    this.list.setOrientation(TwoWayView.Orientation.HORIZONTAL);
    this.button = ((Button)findViewById(2131492989));
    if (paramGalleryImage.hasIcon()) {
      this.button.setCompoundDrawablesWithIntrinsicBounds(paramGalleryImage.getIcon(), 0, 0, 0);
    }
    if (paramGalleryImage.getLinkUrl() == null)
    {
      this.button.setEnabled(false);
      this.button.setBackgroundDrawable(null);
    }
    for (;;)
    {
      this.button.setText(paramGalleryImage.getTitle());
      this.button.setVisibility(0);
      this.errorView = ((ErrorView)findViewById(2131492959));
      this.progress = findViewById(2131492990);
      this.image = paramGalleryImage;
      this.producer = paramGalleryProducer;
      this.adapter = new GalleryListAdapter();
      this.adapter.initialize(this);
      if (paramBoolean) {
        this.adapter.startFetchingAndThenFinish();
      }
      this.list.setAdapter(this.adapter);
      this.list.setOnItemClickListener(new ImageClickListener(this, paramMultiSelect, "horizontal"));
      return;
      this.button.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Analytics.INSTANCE.newEvent(AnalyticsCategories.MISCELLANEOUS, "horizontal list image click", paramGalleryImage.getLinkUrl());
          BusProvider.BUS.get().post(new UrlChangeEvent(paramGalleryImage.getLinkUrl()));
        }
      });
    }
  }
  
  protected void onDetachedFromWindow()
  {
    if (this.producer != null)
    {
      this.producer.removeConsumer(this.adapter);
      if (this.list != null) {
        this.list.setAdapter(null);
      }
    }
    super.onDetachedFromWindow();
  }
  
  protected void onVisibilityChanged(View paramView, int paramInt)
  {
    super.onVisibilityChanged(paramView, paramInt);
  }
  
  public void showError(DisplayError paramDisplayError, String paramString1, String paramString2)
  {
    if (this.errorView != null) {
      this.errorView.show(paramDisplayError, paramString1, paramString2);
    }
  }
  
  public void start()
  {
    this.adapter.startFetchingAndThenFinish();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/HomeGalleryList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */