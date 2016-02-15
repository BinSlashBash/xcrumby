package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;

public class FragmentSuggestionsIndicator
  extends LinearLayout
{
  private View introText;
  private View loading;
  private View notFound;
  
  public FragmentSuggestionsIndicator(Context paramContext)
  {
    super(paramContext);
  }
  
  public FragmentSuggestionsIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FragmentSuggestionsIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void hide()
  {
    this.notFound.setVisibility(8);
    this.loading.setVisibility(8);
    clearAnimation();
  }
  
  public void hideIntroText()
  {
    this.introText.setVisibility(8);
  }
  
  public void indicateNotFound()
  {
    clearAnimation();
    this.loading.setVisibility(8);
    this.notFound.setVisibility(0);
    this.introText.setVisibility(8);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.loading = findViewById(2131493076);
    this.notFound = findViewById(2131493077);
    this.introText = findViewById(2131493078);
    this.introText.setVisibility(0);
  }
  
  public void show()
  {
    setVisibility(0);
    this.loading.setVisibility(0);
    this.notFound.setVisibility(8);
    setAlpha(0.0F);
    animate().alpha(1.0F).setStartDelay(500L);
  }
  
  public void showIntroText()
  {
    this.introText.setVisibility(0);
    hide();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/FragmentSuggestionsIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */