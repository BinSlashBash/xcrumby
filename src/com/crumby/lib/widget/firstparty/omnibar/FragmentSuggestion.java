package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.lib.events.InsertFragmentLinkIntoDatabaseEvent;
import com.crumby.lib.events.RemoveFragmentLinkFromDatabaseEvent;
import com.crumby.lib.router.FragmentLink;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.regex.Pattern;

public class FragmentSuggestion
  extends LinearLayout
{
  private boolean clickable;
  private FragmentSuggestionFavourite favourite;
  FragmentLink fragmentLink;
  private ImageView image;
  private View link;
  private TextView name;
  private TextView url;
  
  public FragmentSuggestion(Context paramContext)
  {
    super(paramContext);
  }
  
  public FragmentSuggestion(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FragmentSuggestion(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private static Pattern buildRegexQuery(String paramString)
  {
    String[] arrayOfString = paramString.trim().split(" ");
    paramString = "(?i).*";
    int j = arrayOfString.length;
    int i = 0;
    while (i < j)
    {
      String str = arrayOfString[i];
      paramString = paramString + "(" + Pattern.quote(str) + ")" + ".*";
      i += 1;
    }
    return Pattern.compile(paramString);
  }
  
  private HighlightBuilder createHighlightedText(String paramString1, String paramString2, int paramInt)
  {
    HighlightBuilder localHighlightBuilder = new HighlightBuilder(paramString2, paramInt);
    TextHighlighter.highlight(localHighlightBuilder, paramString1, paramString2);
    return localHighlightBuilder;
  }
  
  private void setSpan() {}
  
  public String getUrl()
  {
    return this.fragmentLink.getBaseUrl();
  }
  
  public void highlight(String paramString)
  {
    Pattern localPattern = buildRegexQuery(paramString);
    if (this.fragmentLink.fuzzyMatchUrl(localPattern) != -1)
    {
      paramString = createHighlightedText(paramString, this.fragmentLink.getBaseUrl(), 0);
      this.url.setText(paramString);
      this.name.setText(this.fragmentLink.getDisplayName());
      return;
    }
    paramString = createHighlightedText(paramString, this.fragmentLink.getDisplayName(), 2131427528);
    this.name.setText(paramString);
    this.url.setText(this.fragmentLink.getBaseUrl());
  }
  
  public boolean isClickable()
  {
    return this.clickable;
  }
  
  public void notFavoritedYet()
  {
    this.favourite.setChecked(false);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.name = ((TextView)findViewById(2131492976));
    this.url = ((TextView)findViewById(2131492977));
    this.image = ((ImageView)findViewById(2131492975));
    this.favourite = ((FragmentSuggestionFavourite)findViewById(2131492978));
    this.favourite.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FragmentSuggestion.this.favourite.toggle();
        if (FragmentSuggestion.this.favourite.isChecked())
        {
          Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "add favorite", FragmentSuggestion.this.fragmentLink.getBaseUrl());
          BusProvider.BUS.get().post(new InsertFragmentLinkIntoDatabaseEvent(FragmentSuggestion.this.fragmentLink.getBaseUrl(), FragmentSuggestion.this.fragmentLink.getFaviconUrl()));
          return;
        }
        Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "remove favorite", FragmentSuggestion.this.fragmentLink.getBaseUrl());
        BusProvider.BUS.get().post(new RemoveFragmentLinkFromDatabaseEvent(FragmentSuggestion.this.fragmentLink.getBaseUrl()));
      }
    });
    this.link = findViewById(2131492974);
  }
  
  public void setFragmentLink(FragmentLink paramFragmentLink)
  {
    this.fragmentLink = paramFragmentLink;
    if (paramFragmentLink.getDisplayName() == null)
    {
      this.name.setVisibility(8);
      this.name.setText(paramFragmentLink.getDisplayName());
      if ((paramFragmentLink.baseUrlIsHidden()) || (paramFragmentLink.getBaseUrl() == null)) {
        break label129;
      }
      this.url.setVisibility(0);
      this.url.setText(paramFragmentLink.getBaseUrl());
      label65:
      if (!paramFragmentLink.isMandatory()) {
        break label141;
      }
      this.favourite.setVisibility(8);
    }
    for (;;)
    {
      setVisibility(0);
      setImage(paramFragmentLink.getFaviconUrl());
      setHideBackground(false);
      if (!this.favourite.isChecked()) {
        this.favourite.setChecked(true);
      }
      return;
      this.name.setVisibility(0);
      break;
      label129:
      this.url.setVisibility(8);
      break label65;
      label141:
      this.favourite.setVisibility(0);
    }
  }
  
  public void setHideBackground(boolean paramBoolean)
  {
    if (!paramBoolean) {}
    for (boolean bool = true;; bool = false)
    {
      this.clickable = bool;
      if (!paramBoolean) {
        break;
      }
      this.link.getBackground().setAlpha(0);
      this.link.setEnabled(false);
      return;
    }
    this.link.getBackground().setAlpha(255);
    this.link.setEnabled(true);
  }
  
  public void setImage(Drawable paramDrawable)
  {
    this.image.setImageDrawable(paramDrawable);
  }
  
  public void setImage(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      return;
    }
    Picasso.with(getContext()).load(paramString).into(this.image);
    this.image.isShown();
  }
  
  private class HighlightBuilder
    extends SpannableStringBuilder
    implements HighlightText
  {
    private int highlightColor;
    private String text;
    
    public HighlightBuilder(String paramString, int paramInt)
    {
      super();
      this.text = paramString;
      int i = paramInt;
      if (paramInt == 0) {
        i = 2131427515;
      }
      this.highlightColor = FragmentSuggestion.this.getResources().getColor(i);
    }
    
    private StyleSpan getBss()
    {
      return new StyleSpan(1);
    }
    
    private ForegroundColorSpan getFss()
    {
      return new ForegroundColorSpan(this.highlightColor);
    }
    
    public void set(int paramInt1, int paramInt2)
    {
      try
      {
        setSpan(getBss(), paramInt1, paramInt2, 18);
        setSpan(getFss(), paramInt1, paramInt2, 18);
        return;
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
        CrDb.d("fragment link trie malfunctioning", localIndexOutOfBoundsException.getMessage() + " @ " + this.text);
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/FragmentSuggestion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */