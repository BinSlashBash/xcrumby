package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.crumby.BusProvider;
import com.crumby.lib.SearchForm;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.OmnibarView;
import com.squareup.otto.Bus;
import java.net.MalformedURLException;
import java.net.URL;

public class GalleryPanel
  extends LinearLayout
  implements View.OnTouchListener
{
  String DEBUG_TAG = "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDd";
  private boolean active;
  ViewPropertyAnimator animator = animate();
  private Breadcrumb breadcrumbListening;
  private ImageButton cancelButton;
  private float currentY;
  private ViewGroup gallerySearchFormView;
  private ViewGroup gallerySearchOptions;
  int lastY = 0;
  MoveY moveY = new MoveY();
  int moves = 0;
  private OmnibarView omnibarView;
  int originalHeight;
  private SearchForm searchForm;
  private int searchFormId;
  private boolean showing;
  private ImageButton submitButton;
  
  public GalleryPanel(Context paramContext)
  {
    super(paramContext);
  }
  
  public GalleryPanel(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public GalleryPanel(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void animateMove()
  {
    if (getY() != this.moveY.y)
    {
      this.animator.cancel();
      setY(this.moveY.y);
    }
  }
  
  private void clearSearchForm()
  {
    this.gallerySearchFormView.removeAllViews();
  }
  
  private String setSearchForm(int paramInt, String paramString)
  {
    View localView = ((LayoutInflater)getContext().getSystemService("layout_inflater")).inflate(paramInt, null);
    this.gallerySearchFormView = ((ViewGroup)findViewById(2131492996));
    clearSearchForm();
    this.gallerySearchFormView.addView(localView);
    this.gallerySearchFormView.setOnTouchListener(this);
    return setFormValuesFromSearchUrl(paramString);
  }
  
  public void hide()
  {
    if (this.showing)
    {
      animate().y(this.originalHeight);
      this.showing = false;
      if (this.breadcrumbListening != null)
      {
        this.breadcrumbListening.deselect();
        this.breadcrumbListening = null;
      }
    }
  }
  
  public boolean isShowing()
  {
    return this.showing;
  }
  
  public void onFinishInflate()
  {
    this.submitButton = ((ImageButton)findViewById(2131492997));
    this.submitButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        BusProvider.BUS.get().post(new UrlChangeEvent(GalleryPanel.this.breadcrumbListening.getFragmentIndex().getBaseUrl() + "?" + GalleryPanel.this.searchForm.encodeFormData()));
        GalleryPanel.this.hide();
      }
    });
    this.gallerySearchFormView = ((ViewGroup)findViewById(2131492996));
    bringToFront();
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    int i = 0;
    switch (MotionEventCompat.getActionMasked(paramMotionEvent))
    {
    default: 
      return super.onTouchEvent(paramMotionEvent);
    case 2: 
      if (this.lastY == 0) {}
      for (;;)
      {
        this.lastY = ((int)paramMotionEvent.getY());
        this.moveY.setY(i / 10 * 10);
        animateMove();
        this.moves += 1;
        return true;
        i = (int)paramMotionEvent.getY() - this.lastY;
      }
    }
    if ((this.moveY.y <= 65336) || ((this.moveY.y < 0) && (this.moves < 10)))
    {
      hide();
      this.omnibarView.dismissOmnibarModal();
    }
    for (;;)
    {
      this.moves = 0;
      this.moveY.y = 0;
      this.lastY = 0;
      return true;
      this.animator.y(0.0F).start();
    }
  }
  
  public void rebuildForm(int paramInt, Breadcrumb paramBreadcrumb)
  {
    if ((this.searchFormId == paramInt) && (this.showing)) {
      return;
    }
    hide();
    this.breadcrumbListening = paramBreadcrumb;
    update(paramBreadcrumb.getUrl(), paramInt);
    show();
  }
  
  public void setActive(boolean paramBoolean)
  {
    this.active = paramBoolean;
    if (paramBoolean) {
      bringToFront();
    }
  }
  
  public String setFormValuesFromSearchUrl(String paramString)
  {
    String str = null;
    if (!paramString.equals("")) {}
    try
    {
      str = this.searchForm.setFormData(new URL(paramString));
      return str;
    }
    catch (MalformedURLException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public void setOmnibarView(OmnibarView paramOmnibarView)
  {
    this.omnibarView = paramOmnibarView;
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
  }
  
  public void show()
  {
    if (this.active)
    {
      this.showing = true;
      if (getVisibility() == 4)
      {
        this.currentY = getY();
        this.originalHeight = (-getHeight());
        setY(this.originalHeight);
      }
      setVisibility(0);
      animate().y(this.currentY);
      bringToFront();
    }
  }
  
  public String update(String paramString, int paramInt)
  {
    clearSearchForm();
    if ((paramInt == -1) || (paramInt == 0))
    {
      setActive(false);
      return null;
    }
    this.searchFormId = paramInt;
    setActive(true);
    return setSearchForm(paramInt, paramString);
  }
  
  class MoveY
  {
    private int[] lastYs = new int['ߐ'];
    public int y = 0;
    
    MoveY() {}
    
    public void setY(int paramInt)
    {
      int i = this.y + paramInt;
      int j = Math.abs(i);
      if (this.lastYs[j] == 1)
      {
        this.lastYs[j] = 0;
        return;
      }
      paramInt = i;
      if (i > 0) {
        paramInt = 0;
      }
      this.y = paramInt;
      this.lastYs[j] = 1;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/GalleryPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */