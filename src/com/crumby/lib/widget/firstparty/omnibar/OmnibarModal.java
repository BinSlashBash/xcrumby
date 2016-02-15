package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

public class OmnibarModal
  extends LinearLayout
{
  private FragmentSuggestions fragmentSuggestions;
  private ListView listView;
  
  public OmnibarModal(Context paramContext)
  {
    super(paramContext);
  }
  
  public OmnibarModal(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public OmnibarModal(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void hide()
  {
    this.fragmentSuggestions.hide();
    setVisibility(8);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.fragmentSuggestions = ((FragmentSuggestions)findViewById(2131493073));
  }
  
  public void shareSuggestionsViewWith(FragmentSuggestionsHolder paramFragmentSuggestionsHolder)
  {
    paramFragmentSuggestionsHolder.setFragmentSuggestions(this.fragmentSuggestions);
  }
  
  public void show(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.fragmentSuggestions.show();
    }
    setVisibility(0);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/OmnibarModal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */