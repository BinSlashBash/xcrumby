package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentLink;
import com.crumby.lib.router.FragmentRouter;
import com.squareup.otto.Bus;
import java.util.HashSet;
import java.util.Set;

public class FragmentSuggestions
  extends LinearLayout
  implements View.OnClickListener
{
  private BreadcrumbContainer breadcrumbContainer;
  private boolean searchGalleries;
  private Set<String> suggestionIds;
  int visible;
  
  public FragmentSuggestions(Context paramContext)
  {
    super(paramContext);
  }
  
  public FragmentSuggestions(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FragmentSuggestions(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void addSearchSuggestion(String paramString)
  {
    if (paramString.equals("")) {
      return;
    }
    if ((!paramString.matches("((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯][a-zA-Z0-9 -퟿豈-﷏ﷰ-￯\\-]{0,64}\\.)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnprwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agksyz]|v[aceginu]|w[fs]|(?:δοκιμή|испытание|рф|срб|טעסט|آزمایشی|إختبار|الاردن|الجزائر|السعودية|المغرب|امارات|بھارت|تونس|سورية|فلسطين|قطر|مصر|परीक्षा|भारत|ভারত|ਭਾਰਤ|ભારત|இந்தியா|இலங்கை|சிங்கப்பூர்|பரிட்சை|భారత్|ලංකා|ไทย|テスト|中国|中國|台湾|台灣|新加坡|测试|測試|香港|테스트|한국|xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-3e0b707e|xn\\-\\-45brj9c|xn\\-\\-80akhbyknj4f|xn\\-\\-90a3ac|xn\\-\\-9t4b11yi5a|xn\\-\\-clchc0ea0b2g2a9gcd|xn\\-\\-deba0ad|xn\\-\\-fiqs8s|xn\\-\\-fiqz9s|xn\\-\\-fpcrj9c3d|xn\\-\\-fzc2c9e2c|xn\\-\\-g6w251d|xn\\-\\-gecrj9c|xn\\-\\-h2brj9c|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-j6w193g|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-kprw13d|xn\\-\\-kpry57d|xn\\-\\-lgbbat1ad8j|xn\\-\\-mgbaam7a8h|xn\\-\\-mgbayh7gpa|xn\\-\\-mgbbh1a71e|xn\\-\\-mgbc0a9azcg|xn\\-\\-mgberp4a5d4ar|xn\\-\\-o3cw4h|xn\\-\\-ogbpf8fl|xn\\-\\-p1ai|xn\\-\\-pgbs0dh|xn\\-\\-s9brj9c|xn\\-\\-wgbh1c|xn\\-\\-wgbl6a|xn\\-\\-xkc2al3hye2a|xn\\-\\-xkc2dl3a5ee0h|xn\\-\\-yfro4i67o|xn\\-\\-ygbi2ammx|xn\\-\\-zckzah|xxx)|y[et]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)")) && (!paramString.contains("?")) && (!paramString.contains("crumby://")))
    {
      this.searchGalleries = true;
      addSuggestionWithNoBaseUrl("Search All Galleries for \"" + paramString + "\"", " " + paramString, getResources().getDrawable(2130837640));
      return;
    }
    this.searchGalleries = false;
  }
  
  private FragmentSuggestion addSuggestion(FragmentLink paramFragmentLink, Drawable paramDrawable)
  {
    if (this.visible >= getChildCount()) {
      return null;
    }
    int i = this.visible;
    this.visible = (i + 1);
    FragmentSuggestion localFragmentSuggestion = (FragmentSuggestion)getChildAt(i);
    localFragmentSuggestion.setImage(paramDrawable);
    localFragmentSuggestion.setFragmentLink(paramFragmentLink);
    return localFragmentSuggestion;
  }
  
  private FragmentSuggestion addSuggestion(String paramString1, String paramString2, String paramString3)
  {
    return addSuggestion(new FragmentLink(paramString1, paramString2, paramString3, 0), null);
  }
  
  private FragmentSuggestion addSuggestionWithNoBaseUrl(String paramString1, String paramString2, Drawable paramDrawable)
  {
    paramString1 = new FragmentLink(paramString1, paramString2, null, 0);
    paramString1.setHideBaseUrl(true);
    paramString1.setMandatory(true);
    return addSuggestion(paramString1, paramDrawable);
  }
  
  public void appendFinal(String paramString1, String paramString2, boolean paramBoolean)
  {
    FragmentIndex localFragmentIndex = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(paramString1);
    int j = 0;
    int i;
    if (localFragmentIndex != null)
    {
      if (localFragmentIndex.getFragmentClass().equals(UnsupportedUrlFragment.class)) {
        break label95;
      }
      if (!paramBoolean) {
        break label89;
      }
      i = j;
      if (this.suggestionIds.contains(paramString1)) {
        break label78;
      }
      paramString2 = addSuggestion("Favorite?", paramString1, paramString2);
      if (paramString2 != null) {
        break label65;
      }
    }
    for (;;)
    {
      return;
      label65:
      paramString2.setHideBackground(true);
      paramString2.notFavoritedYet();
      i = j;
      label78:
      while (i != 0)
      {
        appendTryParse(paramString1);
        return;
        label89:
        i = 1;
        continue;
        label95:
        i = 1;
      }
    }
  }
  
  /* Error */
  public void appendFragmentSuggestions(String paramString, java.util.List<FragmentLink> paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_2
    //   3: invokeinterface 173 1 0
    //   8: astore_2
    //   9: aload_2
    //   10: invokeinterface 179 1 0
    //   15: ifeq +42 -> 57
    //   18: aload_2
    //   19: invokeinterface 183 1 0
    //   24: checkcast 111	com/crumby/lib/router/FragmentLink
    //   27: astore_3
    //   28: aload_0
    //   29: getfield 148	com/crumby/lib/widget/firstparty/omnibar/FragmentSuggestions:suggestionIds	Ljava/util/Set;
    //   32: aload_3
    //   33: invokevirtual 186	com/crumby/lib/router/FragmentLink:getBaseUrl	()Ljava/lang/String;
    //   36: invokeinterface 152 2 0
    //   41: ifne -32 -> 9
    //   44: aload_0
    //   45: aload_3
    //   46: aconst_null
    //   47: invokespecial 116	com/crumby/lib/widget/firstparty/omnibar/FragmentSuggestions:addSuggestion	(Lcom/crumby/lib/router/FragmentLink;Landroid/graphics/drawable/Drawable;)Lcom/crumby/lib/widget/firstparty/omnibar/FragmentSuggestion;
    //   50: astore 4
    //   52: aload 4
    //   54: ifnonnull +6 -> 60
    //   57: aload_0
    //   58: monitorexit
    //   59: return
    //   60: aload 4
    //   62: aload_1
    //   63: invokevirtual 189	com/crumby/lib/widget/firstparty/omnibar/FragmentSuggestion:highlight	(Ljava/lang/String;)V
    //   66: aload_0
    //   67: getfield 148	com/crumby/lib/widget/firstparty/omnibar/FragmentSuggestions:suggestionIds	Ljava/util/Set;
    //   70: aload_3
    //   71: invokevirtual 186	com/crumby/lib/router/FragmentLink:getBaseUrl	()Ljava/lang/String;
    //   74: invokeinterface 192 2 0
    //   79: pop
    //   80: goto -71 -> 9
    //   83: astore_1
    //   84: aload_0
    //   85: monitorexit
    //   86: aload_1
    //   87: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	88	0	this	FragmentSuggestions
    //   0	88	1	paramString	String
    //   0	88	2	paramList	java.util.List<FragmentLink>
    //   27	44	3	localFragmentLink	FragmentLink
    //   50	11	4	localFragmentSuggestion	FragmentSuggestion
    // Exception table:
    //   from	to	target	type
    //   2	9	83	finally
    //   9	52	83	finally
    //   60	80	83	finally
  }
  
  public void appendTryParse(String paramString)
  {
    if ((this.suggestionIds.isEmpty()) && (!this.searchGalleries)) {
      addSuggestionWithNoBaseUrl("Try to parse \"" + paramString + "\"?", paramString, getResources().getDrawable(2130837645));
    }
  }
  
  public boolean canSearchGalleries()
  {
    return this.searchGalleries;
  }
  
  public int getVisible()
  {
    return this.visible;
  }
  
  public void hide()
  {
    setVisibility(8);
  }
  
  public void onClick(View paramView)
  {
    paramView = (FragmentSuggestion)paramView.getParent();
    if (!paramView.isClickable()) {
      return;
    }
    this.breadcrumbContainer.removeBreadcrumbs();
    paramView = paramView.getUrl();
    Analytics.INSTANCE.newNavigationEvent("fragment suggestion click", paramView);
    BusProvider.BUS.get().post(new UrlChangeEvent(paramView));
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    int i = getChildCount() - 1;
    while (i >= 0)
    {
      getChildAt(i).findViewById(2131492974).setOnClickListener(this);
      i -= 1;
    }
    this.suggestionIds = new HashSet();
  }
  
  public int remainingSpace()
  {
    return getChildCount() - this.visible - 2;
  }
  
  public void removeSuggestions(String paramString)
  {
    this.visible = 0;
    int i = getChildCount() - 1;
    while (i >= 0)
    {
      getChildAt(i).setVisibility(8);
      i -= 1;
    }
    this.suggestionIds.clear();
    addSearchSuggestion(paramString);
  }
  
  public void setBreadcrumbContainer(BreadcrumbContainer paramBreadcrumbContainer)
  {
    this.breadcrumbContainer = paramBreadcrumbContainer;
  }
  
  public void show()
  {
    setVisibility(0);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/FragmentSuggestions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */