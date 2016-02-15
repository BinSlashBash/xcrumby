package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.InsertFragmentLinkIntoDatabaseEvent;
import com.squareup.otto.Bus;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class OverflowImageButton
  extends ImageButton
  implements View.OnClickListener
{
  private GalleryImage image;
  private String url;
  
  public OverflowImageButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public OverflowImageButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public OverflowImageButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void favoriteImage()
  {
    String str = null;
    if (this.image != null) {
      str = this.image.getThumbnailUrl();
    }
    BusProvider.BUS.get().post(new InsertFragmentLinkIntoDatabaseEvent(this.url, str));
  }
  
  private void openImageInWeb()
  {
    if ((this.image != null) && (this.image.getLinkUrl() != null)) {}
    for (String str = this.image.getLinkUrl();; str = this.url)
    {
      openWebBrowser(str, getContext());
      return;
    }
  }
  
  public static void openWebBrowser(String paramString, Context paramContext)
  {
    if (paramString == null) {
      return;
    }
    Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "open in web", paramString);
    Intent localIntent = new Intent();
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.setData(Uri.parse(paramString));
    paramContext.startActivity(localIntent);
  }
  
  private void share()
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.setType("text/plain");
    String str1 = "Image found with crumby!";
    String str2;
    if ((this.image != null) && (this.image.getTitle() != null))
    {
      str2 = this.image.getLinkUrl();
      str1 = this.image.getTitle();
      this.url = this.image.getLinkUrl();
    }
    for (;;)
    {
      str2 = str2 + ". Found with Crumby, Universal Image Browser [http://tinyurl.com/getcrumby]";
      localIntent.putExtra("android.intent.extra.SUBJECT", str1);
      localIntent.putExtra("android.intent.extra.TEXT", str2);
      Analytics.INSTANCE.newEvent(AnalyticsCategories.SHARING, "image share", this.url);
      getContext().startActivity(Intent.createChooser(localIntent, "Share \"" + this.url + "\" via"));
      do
      {
        return;
      } while ((this.url == null) || (this.url.equals("")));
      str2 = this.url;
    }
  }
  
  public void initialize(GalleryImage paramGalleryImage)
  {
    this.image = paramGalleryImage;
    setOnClickListener(this);
  }
  
  public void initialize(String paramString)
  {
    this.url = paramString;
    setOnClickListener(this);
    setVisibility(0);
  }
  
  public void onClick(View paramView)
  {
    paramView = new PopupMenu(getContext(), this);
    paramView.getMenuInflater().inflate(2131689474, paramView.getMenu());
    Field[] arrayOfField = paramView.getClass().getDeclaredFields();
    int j = arrayOfField.length;
    int i = 0;
    for (;;)
    {
      Object localObject;
      if (i < j) {
        localObject = arrayOfField[i];
      }
      try
      {
        if ("mPopup".equals(((Field)localObject).getName()))
        {
          ((Field)localObject).setAccessible(true);
          localObject = ((Field)localObject).get(paramView);
          Class.forName(localObject.getClass().getName()).getMethod("setForceShowIcon", new Class[] { Boolean.TYPE }).invoke(localObject, new Object[] { Boolean.valueOf(true) });
          paramView.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
          {
            public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
            {
              if (paramAnonymousMenuItem.getItemId() == 2131493173) {
                OverflowImageButton.this.share();
              }
              for (;;)
              {
                return true;
                if (paramAnonymousMenuItem.getItemId() == 2131493174) {
                  OverflowImageButton.this.openImageInWeb();
                }
              }
            }
          });
          paramView.show();
          return;
        }
      }
      catch (Exception localException)
      {
        i += 1;
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/fragment_options/OverflowImageButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */