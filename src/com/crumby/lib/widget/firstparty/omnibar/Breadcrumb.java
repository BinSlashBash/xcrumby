package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils.TruncateAt;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;

public class Breadcrumb
  extends Button
{
  private boolean dropdownMode;
  private FragmentIndex fragmentIndex;
  private int index;
  private String url;
  
  public Breadcrumb(Context paramContext, FragmentIndex paramFragmentIndex, int paramInt, String paramString)
  {
    super(paramContext, null);
    setSingleLine(true);
    setHorizontallyScrolling(true);
    setSelected(false);
    setClickable(true);
    setEllipsize(TextUtils.TruncateAt.START);
    this.index = paramInt;
    this.fragmentIndex = paramFragmentIndex;
    if ((paramFragmentIndex.isSuggestable()) && (paramFragmentIndex.getSearchFormId() != -1)) {}
    for (;;)
    {
      this.dropdownMode = bool;
      this.url = paramString;
      if (paramString == null) {
        this.url = paramFragmentIndex.getBaseUrl();
      }
      render();
      return;
      bool = false;
    }
  }
  
  private void setBreadcrumbDrawable()
  {
    int i;
    if (this.index == 0) {
      i = 2130837525;
    }
    for (;;)
    {
      setBackgroundDrawable(getResources().getDrawable(i));
      getBackground().setAlpha(200);
      return;
      i = 2130837513;
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
      localLayoutParams.setMargins((int)CrumbyApp.convertDpToPx(getResources(), -15.0F), 0, 0, 0);
      setLayoutParams(localLayoutParams);
    }
  }
  
  private void setFilters(int paramInt)
  {
    int i = getResources().getConfiguration().screenLayout & 0xF;
    if ((i == 4) || (i == 3)) {
      return;
    }
    setFilters(new InputFilter[] { new InputFilter.LengthFilter(paramInt) });
    setBreadcrumbText(null);
  }
  
  private void setIcon()
  {
    if (this.fragmentIndex.hasBreadcrumbIcon())
    {
      Drawable localDrawable = getResources().getDrawable(this.fragmentIndex.getBreadcrumbIcon()).mutate();
      if ((this.fragmentIndex.getParent() != null) || (this.fragmentIndex.getBreadcrumbIcon() == 2130837640)) {
        localDrawable.setAlpha(127);
      }
      setCompoundDrawablesWithIntrinsicBounds(localDrawable, null, null, null);
    }
  }
  
  public void alter(String paramString)
  {
    this.fragmentIndex = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(paramString);
    this.url = paramString;
    setBreadcrumbText(null);
    invalidate();
  }
  
  public boolean click()
  {
    return false;
  }
  
  public void defocus()
  {
    setPadding(0, 0, (int)CrumbyApp.convertDpToPx(getResources(), 0.0F), 0);
    setBreadcrumbDrawable();
  }
  
  public void deselect()
  {
    setSelected(false);
  }
  
  public void focus()
  {
    if (Build.VERSION.SDK_INT >= 16) {
      setBackground(null);
    }
    while (this.index == 0)
    {
      setPadding((int)CrumbyApp.convertDpToPx(getResources(), 5.0F), 0, 0, 0);
      return;
      setBackgroundDrawable(null);
    }
    setPadding((int)CrumbyApp.convertDpToPx(getResources(), 15.0F), 0, 0, 0);
  }
  
  public FragmentIndex getFragmentIndex()
  {
    return this.fragmentIndex;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public boolean hasPanel()
  {
    return this.dropdownMode;
  }
  
  public boolean hasUrl()
  {
    return this.url != null;
  }
  
  public boolean readyForGalleryPanel()
  {
    if (this.fragmentIndex.getSearchFormId() != -1) {
      toggleSelected();
    }
    return isSelected();
  }
  
  public void render()
  {
    setBreadcrumbDrawable();
    setBreadcrumbText(null);
    setIcon();
  }
  
  public void restrict(int paramInt)
  {
    setFilters(new InputFilter[] { new InputFilter.LengthFilter(paramInt) });
    setBreadcrumbText(null);
  }
  
  public void setBreadcrumbText(String paramString)
  {
    Object localObject = null;
    if ((this.fragmentIndex.hasAltName()) && (paramString == null)) {}
    for (;;)
    {
      try
      {
        paramString = GalleryViewerFragment.matchIdFromUrl(this.fragmentIndex.getRegexUrl(), this.url);
        setText(paramString);
        if ((paramString != null) && (!paramString.equals(""))) {
          break;
        }
        setVisibility(8);
        return;
      }
      catch (NullPointerException paramString)
      {
        CrDb.d("breadcrumb", paramString.toString());
        paramString = (String)localObject;
        continue;
      }
      if (paramString == null) {
        paramString = this.fragmentIndex.getBreadcrumbName();
      }
    }
    setVisibility(0);
  }
  
  public void setFragmentIndex(FragmentIndex paramFragmentIndex)
  {
    this.fragmentIndex = paramFragmentIndex;
  }
  
  public void toggleSelected()
  {
    if (!isSelected()) {}
    for (boolean bool = true;; bool = false)
    {
      setSelected(bool);
      return;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/Breadcrumb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */