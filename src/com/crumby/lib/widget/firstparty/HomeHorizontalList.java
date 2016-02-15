package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.crumby.BusProvider;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.GalleryListFragment;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.fragment.adapter.ImageWrapper;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.List;

public class HomeHorizontalList
  extends LinearLayout
  implements GalleryConsumer, View.OnClickListener
{
  private GalleryImage image;
  private GalleryProducer producer;
  
  public HomeHorizontalList(Context paramContext)
  {
    super(paramContext);
  }
  
  public HomeHorizontalList(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public HomeHorizontalList(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void clearLoading() {}
  
  public boolean addImages(List<GalleryImage> paramList)
  {
    clearLoading();
    int i = 0;
    while (i < Math.min(paramList.size(), 7))
    {
      View localView = getChildAt(i + 1);
      GridImageWrapper localGridImageWrapper = new GridImageWrapper(localView, false);
      localGridImageWrapper.clear();
      localGridImageWrapper.set((GalleryImage)paramList.get(i));
      localView.setTag(localGridImageWrapper);
      localView.setOnClickListener(this);
      int j = GalleryListFragment.THUMBNAIL_HEIGHT;
      Picasso.with(getContext()).load(localGridImageWrapper.getImage().getThumbnailUrl()).resize(j, j).centerCrop().into(localGridImageWrapper.getImageView());
      addViewGrow(localView, i * 400);
      i += 1;
    }
    paramList.add(this.image);
    paramList = new Button(getContext());
    paramList.setText("See more...");
    paramList.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        BusProvider.BUS.get().post(new UrlChangeEvent(HomeHorizontalList.this.image.getLinkUrl()));
      }
    });
    addView(paramList);
    return false;
  }
  
  public void addViewGrow(View paramView, long paramLong)
  {
    paramView.setVisibility(0);
    paramView.setScaleX(0.0F);
    paramView.setScaleY(0.0F);
    paramView.animate().scaleX(1.0F).scaleY(1.0F).setStartDelay(paramLong).setDuration(400L);
  }
  
  public void finishLoading() {}
  
  public void notifyDataSetChanged() {}
  
  public void onClick(View paramView)
  {
    paramView = ((ImageWrapper)paramView.getTag()).getImage();
    this.producer.shareAndSetCurrentImageFocus(paramView.getPosition());
    BusProvider.BUS.get().post(new UrlChangeEvent(paramView.getLinkUrl(), this.producer));
  }
  
  public void setProducer(String paramString, GalleryProducer paramGalleryProducer)
  {
    this.producer = paramGalleryProducer;
    this.image = new GalleryImage(paramString);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(paramString).getBreadcrumbName());
    this.image.setPath(localArrayList);
    paramGalleryProducer.initialize(this, this.image, null, true);
  }
  
  public void showError(Exception paramException)
  {
    clearLoading();
    View localView = View.inflate(getContext(), 2130903085, null);
    super.addView(localView);
    addViewGrow(localView, 0L);
    ((TextView)findViewById(2131493017)).setText(paramException.getMessage());
    int i = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
    localView.measure(0, 0);
    if (localView.getMeasuredWidth() > i) {
      localView.setLayoutParams(new LinearLayout.LayoutParams(i, -2));
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/HomeHorizontalList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */