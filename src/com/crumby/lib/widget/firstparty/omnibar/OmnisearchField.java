package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.router.FragmentLink;
import com.crumby.lib.router.FragmentRouter;
import com.squareup.otto.Bus;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class OmnisearchField
  extends EditText
  implements TextView.OnEditorActionListener, TextWatcher, FragmentSuggestionsHolder
{
  private BreadcrumbContainer breadcrumbContainer;
  private FragmentSuggestions fragmentSuggestions;
  private int icon = -1;
  String linkThumbnailUrl;
  String linkUrl;
  private boolean listenToTextChange;
  private OmniformContainer omniformContainer;
  private String savedText;
  private FragmentSuggestionsIndicator searching;
  private SuggestNameTask suggestNameTask;
  private String suggestQuery;
  private SuggestUrlTask suggestUrlTask;
  private Runnable suggestWait;
  private Runnable timeoutSuggestions;
  
  public OmnisearchField(Context paramContext)
  {
    super(paramContext);
  }
  
  public OmnisearchField(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public OmnisearchField(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void clear(String paramString)
  {
    this.fragmentSuggestions.removeSuggestions(paramString);
    this.suggestUrlTask.cancel(true);
    this.suggestNameTask.cancel(true);
    if (getHandler() != null)
    {
      getHandler().removeCallbacks(this.timeoutSuggestions);
      getHandler().removeCallbacks(this.suggestWait);
    }
    timeout();
  }
  
  private void focus()
  {
    setVisibility(0);
    requestFocus();
    selectAll();
    ((InputMethodManager)getContext().getSystemService("input_method")).showSoftInput(this, 1);
  }
  
  private boolean prepareForSuggest(String paramString)
  {
    clear(paramString);
    if (paramString.trim().length() < 1)
    {
      this.searching.showIntroText();
      return false;
    }
    this.searching.hideIntroText();
    this.suggestQuery = paramString;
    return true;
  }
  
  private void startTimeout()
  {
    getHandler().removeCallbacks(this.timeoutSuggestions);
    getHandler().postDelayed(this.timeoutSuggestions, 6000L);
  }
  
  private void suggestAfterWait()
  {
    if ((this.suggestQuery == null) || (this.suggestQuery.trim().equals(""))) {
      return;
    }
    this.suggestUrlTask = new SuggestUrlTask();
    startTimeout();
    this.suggestUrlTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[] { this.suggestQuery });
    this.omniformContainer.alter(this.suggestQuery);
    this.searching.show();
  }
  
  private void timeout()
  {
    FragmentRouter.INSTANCE.stopMatching();
    if (this.fragmentSuggestions.getVisible() > 0)
    {
      this.searching.hide();
      return;
    }
    this.searching.indicateNotFound();
  }
  
  public void afterTextChanged(Editable paramEditable) {}
  
  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void defocus()
  {
    clearFocus();
    clearComposingText();
    ((InputMethodManager)getContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindowToken(), 0);
  }
  
  public boolean hasSearchForm()
  {
    return this.omniformContainer.isActive();
  }
  
  public void hide()
  {
    clear("");
    setVisibility(8);
    defocus();
  }
  
  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 2)
    {
      this.breadcrumbContainer.removeBreadcrumbs();
      Analytics.INSTANCE.newNavigationEvent("omnisearch go", paramTextView.getText().toString());
      BusProvider.BUS.get().post(new UrlChangeEvent(paramTextView.getText().toString()));
    }
    return true;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    setOnEditorActionListener(this);
    this.suggestUrlTask = new SuggestUrlTask();
    this.suggestNameTask = new SuggestNameTask();
    this.suggestWait = new Runnable()
    {
      public void run()
      {
        OmnisearchField.this.suggestAfterWait();
      }
    };
    this.timeoutSuggestions = new Runnable()
    {
      public void run()
      {
        OmnisearchField.this.timeout();
      }
    };
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    if (!paramBoolean)
    {
      setAlpha(0.25F);
      return;
    }
    setAlpha(1.0F);
  }
  
  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    paramCharSequence = paramCharSequence.toString();
    if ((this.fragmentSuggestions == null) || (!this.listenToTextChange)) {}
    while (!prepareForSuggest(paramCharSequence)) {
      return;
    }
    getHandler().postDelayed(this.suggestWait, 700L);
  }
  
  public void setBreadcrumbContainer(BreadcrumbContainer paramBreadcrumbContainer)
  {
    this.breadcrumbContainer = paramBreadcrumbContainer;
  }
  
  public void setFragmentSuggestions(FragmentSuggestions paramFragmentSuggestions)
  {
    this.fragmentSuggestions = paramFragmentSuggestions;
    this.fragmentSuggestions.setBreadcrumbContainer(this.breadcrumbContainer);
    addTextChangedListener(this);
  }
  
  public void setLink(GalleryViewerFragment paramGalleryViewerFragment)
  {
    setText(paramGalleryViewerFragment.getDisplayUrl());
    this.linkUrl = this.savedText;
    if (paramGalleryViewerFragment.getImage().getThumbnailUrl() != null) {
      this.linkThumbnailUrl = paramGalleryViewerFragment.getImage().getThumbnailUrl();
    }
  }
  
  public void setOmniformContainer(OmniformContainer paramOmniformContainer)
  {
    this.omniformContainer = paramOmniformContainer;
  }
  
  public void setProgressIndicator(FragmentSuggestionsIndicator paramFragmentSuggestionsIndicator)
  {
    this.searching = paramFragmentSuggestionsIndicator;
    paramFragmentSuggestionsIndicator.hide();
  }
  
  public void setText(String paramString)
  {
    this.listenToTextChange = false;
    try
    {
      new URL(paramString);
      str1 = paramString;
      if (!paramString.startsWith("www.")) {
        str1 = "www." + GalleryViewerFragment.stripUrlPrefix(paramString);
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      for (;;)
      {
        String str1;
        String str2 = paramString;
      }
    }
    super.setText(str1);
    this.savedText = str1;
    this.listenToTextChange = true;
  }
  
  public void show(List<Breadcrumb> paramList)
  {
    setText(this.savedText);
    focus();
    this.omniformContainer.alter(paramList);
    this.omniformContainer.backToSearchForm();
    if (prepareForSuggest(this.savedText)) {
      getHandler().postDelayed(this.suggestWait, 1000L);
    }
  }
  
  public void showAccount()
  {
    defocus();
    Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "show account", this.savedText);
    this.omniformContainer.showAccount();
  }
  
  class SuggestNameTask
    extends AsyncTask<Integer, Integer, List<FragmentLink>>
  {
    SuggestNameTask() {}
    
    protected List<FragmentLink> doInBackground(Integer... paramVarArgs)
    {
      if (OmnisearchField.this.fragmentSuggestions.remainingSpace() <= 0) {
        return null;
      }
      return FragmentRouter.INSTANCE.findMatchingLinks(OmnisearchField.this.suggestQuery, false, OmnisearchField.this.fragmentSuggestions.remainingSpace());
    }
    
    protected void onPostExecute(List<FragmentLink> paramList)
    {
      super.onPostExecute(paramList);
      if (paramList == null) {
        return;
      }
      OmnisearchField.this.fragmentSuggestions.appendFragmentSuggestions(OmnisearchField.this.suggestQuery, paramList);
      OmnisearchField.this.fragmentSuggestions.appendFinal(OmnisearchField.this.suggestQuery, OmnisearchField.this.linkThumbnailUrl, OmnisearchField.this.linkUrl.equals(OmnisearchField.this.suggestQuery));
      OmnisearchField.this.timeout();
    }
  }
  
  class SuggestUrlTask
    extends AsyncTask<String, Integer, List<FragmentLink>>
  {
    SuggestUrlTask() {}
    
    protected List<FragmentLink> doInBackground(String... paramVarArgs)
    {
      if (OmnisearchField.this.fragmentSuggestions.remainingSpace() <= 0) {
        return null;
      }
      return FragmentRouter.INSTANCE.findMatchingLinks(OmnisearchField.this.suggestQuery, true, OmnisearchField.this.fragmentSuggestions.remainingSpace());
    }
    
    protected void onPostExecute(List<FragmentLink> paramList)
    {
      super.onPostExecute(paramList);
      if (paramList == null) {
        return;
      }
      OmnisearchField.this.fragmentSuggestions.appendFragmentSuggestions(OmnisearchField.this.suggestQuery, paramList);
      if (paramList.size() > 0) {
        OmnisearchField.this.searching.hide();
      }
      if (paramList.size() < 10)
      {
        OmnisearchField.access$502(OmnisearchField.this, new OmnisearchField.SuggestNameTask(OmnisearchField.this));
        OmnisearchField.this.suggestNameTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Integer[] { Integer.valueOf(paramList.size()) });
        return;
      }
      OmnisearchField.this.timeout();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/OmnisearchField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */