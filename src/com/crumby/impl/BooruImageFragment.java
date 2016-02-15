package com.crumby.impl;

import android.content.res.Resources;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.widget.firstparty.ImageCommentsView;

public abstract class BooruImageFragment
  extends GalleryImageFragment
  implements View.OnClickListener
{
  private ImageCommentsView comments;
  private TextView description;
  private ViewGroup mainTags;
  private View title;
  
  private View.OnClickListener getThis()
  {
    return this;
  }
  
  protected void addTags()
  {
    addTags(this.title, this.mainTags, getImage().getTags());
  }
  
  protected void addTags(View paramView, ViewGroup paramViewGroup, String[] paramArrayOfString)
  {
    CrDb.logTime("booru image fragment", "tags", true);
    int j = 0;
    int m = (int)getResources().getDimension(2131165194);
    int n = paramArrayOfString.length;
    int i = 0;
    if (i < n)
    {
      String str = paramArrayOfString[i];
      int k = j;
      if (str != null) {
        if (!str.trim().equals("")) {
          break label82;
        }
      }
      for (k = j;; k = j + 1)
      {
        i += 1;
        j = k;
        break;
        label82:
        Button localButton = new Button(paramViewGroup.getContext());
        localButton.setMaxWidth(m);
        paramViewGroup.addView(localButton);
        localButton.setText(str);
        localButton.setOnClickListener(getThis());
      }
    }
    if (j == 0)
    {
      paramView.setVisibility(8);
      paramViewGroup.setVisibility(8);
    }
    CrDb.logTime("booru image fragment", "tags", false);
  }
  
  protected void alternateFillMetadata() {}
  
  protected void fillMetadataView()
  {
    this.mainTags.postDelayed(new Runnable()
    {
      public void run()
      {
        try
        {
          if (BooruImageFragment.this.getActivity() == null) {
            return;
          }
          if (BooruImageFragment.this.getImage().getDescription() != null)
          {
            BooruImageFragment.this.description.setText(BooruImageFragment.this.getImage().getDescription());
            BooruImageFragment.this.description.setVisibility(0);
          }
          BooruImageFragment.this.comments.initialize(BooruImageFragment.this.getImage().getComments());
          BooruImageFragment.this.addTags();
          BooruImageFragment.this.alternateFillMetadata();
          return;
        }
        catch (ClassCastException localClassCastException)
        {
          BooruImageFragment.this.indicateMetadataError("Could not load tags: ", localClassCastException);
          return;
        }
        catch (NullPointerException localNullPointerException)
        {
          BooruImageFragment.this.indicateMetadataError("Could not load tags: ", localNullPointerException);
        }
      }
    }, 400L);
  }
  
  protected int getBooruLayout()
  {
    return 2130903043;
  }
  
  protected abstract String getTagUrl(String paramString);
  
  protected ViewGroup inflateMetadataLayout(LayoutInflater paramLayoutInflater)
  {
    paramLayoutInflater = (ViewGroup)paramLayoutInflater.inflate(getBooruLayout(), null);
    this.mainTags = ((ViewGroup)paramLayoutInflater.findViewById(2131492906));
    this.title = paramLayoutInflater.findViewById(2131492905);
    this.comments = ((ImageCommentsView)paramLayoutInflater.findViewById(2131493021));
    this.description = ((TextView)paramLayoutInflater.findViewById(2131492904));
    this.description.setMovementMethod(LinkMovementMethod.getInstance());
    return paramLayoutInflater;
  }
  
  public void onClick(View paramView)
  {
    paramView = ((Button)paramView).getText().toString();
    Analytics.INSTANCE.newNavigationEvent("booru metadata click", paramView);
    postUrlChangeToBus(getTagUrl(paramView), null);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/BooruImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */