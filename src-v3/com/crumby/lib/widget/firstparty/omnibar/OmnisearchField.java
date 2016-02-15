package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
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
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.router.FragmentLink;
import com.crumby.lib.router.FragmentRouter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class OmnisearchField extends EditText implements OnEditorActionListener, TextWatcher, FragmentSuggestionsHolder {
    private BreadcrumbContainer breadcrumbContainer;
    private FragmentSuggestions fragmentSuggestions;
    private int icon;
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

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.OmnisearchField.1 */
    class C01611 implements Runnable {
        C01611() {
        }

        public void run() {
            OmnisearchField.this.suggestAfterWait();
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.OmnisearchField.2 */
    class C01622 implements Runnable {
        C01622() {
        }

        public void run() {
            OmnisearchField.this.timeout();
        }
    }

    class SuggestNameTask extends AsyncTask<Integer, Integer, List<FragmentLink>> {
        SuggestNameTask() {
        }

        protected List<FragmentLink> doInBackground(Integer... size) {
            if (OmnisearchField.this.fragmentSuggestions.remainingSpace() <= 0) {
                return null;
            }
            return FragmentRouter.INSTANCE.findMatchingLinks(OmnisearchField.this.suggestQuery, false, OmnisearchField.this.fragmentSuggestions.remainingSpace());
        }

        protected void onPostExecute(List<FragmentLink> fragmentLinks) {
            super.onPostExecute(fragmentLinks);
            if (fragmentLinks != null) {
                OmnisearchField.this.fragmentSuggestions.appendFragmentSuggestions(OmnisearchField.this.suggestQuery, fragmentLinks);
                OmnisearchField.this.fragmentSuggestions.appendFinal(OmnisearchField.this.suggestQuery, OmnisearchField.this.linkThumbnailUrl, OmnisearchField.this.linkUrl.equals(OmnisearchField.this.suggestQuery));
                OmnisearchField.this.timeout();
            }
        }
    }

    class SuggestUrlTask extends AsyncTask<String, Integer, List<FragmentLink>> {
        SuggestUrlTask() {
        }

        protected List<FragmentLink> doInBackground(String... strings) {
            if (OmnisearchField.this.fragmentSuggestions.remainingSpace() <= 0) {
                return null;
            }
            return FragmentRouter.INSTANCE.findMatchingLinks(OmnisearchField.this.suggestQuery, true, OmnisearchField.this.fragmentSuggestions.remainingSpace());
        }

        protected void onPostExecute(List<FragmentLink> fragmentLinks) {
            super.onPostExecute(fragmentLinks);
            if (fragmentLinks != null) {
                OmnisearchField.this.fragmentSuggestions.appendFragmentSuggestions(OmnisearchField.this.suggestQuery, fragmentLinks);
                if (fragmentLinks.size() > 0) {
                    OmnisearchField.this.searching.hide();
                }
                if (fragmentLinks.size() < 10) {
                    OmnisearchField.this.suggestNameTask = new SuggestNameTask();
                    OmnisearchField.this.suggestNameTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Integer[]{Integer.valueOf(fragmentLinks.size())});
                    return;
                }
                OmnisearchField.this.timeout();
            }
        }
    }

    public OmnisearchField(Context context) {
        super(context);
        this.icon = -1;
    }

    public OmnisearchField(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.icon = -1;
    }

    public OmnisearchField(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.icon = -1;
    }

    private void startTimeout() {
        getHandler().removeCallbacks(this.timeoutSuggestions);
        getHandler().postDelayed(this.timeoutSuggestions, 6000);
    }

    private void suggestAfterWait() {
        if (this.suggestQuery != null && !this.suggestQuery.trim().equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            this.suggestUrlTask = new SuggestUrlTask();
            startTimeout();
            this.suggestUrlTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{this.suggestQuery});
            this.omniformContainer.alter(this.suggestQuery);
            this.searching.show();
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnEditorActionListener(this);
        this.suggestUrlTask = new SuggestUrlTask();
        this.suggestNameTask = new SuggestNameTask();
        this.suggestWait = new C01611();
        this.timeoutSuggestions = new C01622();
    }

    public void setFragmentSuggestions(FragmentSuggestions suggestionsView) {
        this.fragmentSuggestions = suggestionsView;
        this.fragmentSuggestions.setBreadcrumbContainer(this.breadcrumbContainer);
        addTextChangedListener(this);
    }

    private void focus() {
        setVisibility(0);
        requestFocus();
        selectAll();
        ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this, 1);
    }

    public void defocus() {
        clearFocus();
        clearComposingText();
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindowToken(), 0);
    }

    public void hide() {
        clear(UnsupportedUrlFragment.DISPLAY_NAME);
        setVisibility(8);
        defocus();
    }

    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            setAlpha(GalleryViewer.PROGRESS_COMPLETED);
        } else {
            setAlpha(0.25f);
        }
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == 2) {
            this.breadcrumbContainer.removeBreadcrumbs();
            Analytics.INSTANCE.newNavigationEvent("omnisearch go", textView.getText().toString());
            BusProvider.BUS.get().post(new UrlChangeEvent(textView.getText().toString()));
        }
        return true;
    }

    public void show(List<Breadcrumb> breadcrumbs) {
        setText(this.savedText);
        focus();
        this.omniformContainer.alter((List) breadcrumbs);
        this.omniformContainer.backToSearchForm();
        if (prepareForSuggest(this.savedText)) {
            getHandler().postDelayed(this.suggestWait, 1000);
        }
    }

    public void setBreadcrumbContainer(BreadcrumbContainer breadcrumbContainer) {
        this.breadcrumbContainer = breadcrumbContainer;
    }

    public void setText(String url) {
        this.listenToTextChange = false;
        try {
            URL url2 = new URL(url);
            if (!url.startsWith("www.")) {
                url = "www." + GalleryViewerFragment.stripUrlPrefix(url);
            }
        } catch (MalformedURLException e) {
        }
        super.setText(url);
        this.savedText = url;
        this.listenToTextChange = true;
    }

    public void setProgressIndicator(FragmentSuggestionsIndicator searching) {
        this.searching = searching;
        searching.hide();
    }

    public void setOmniformContainer(OmniformContainer omniformContainer) {
        this.omniformContainer = omniformContainer;
    }

    public boolean hasSearchForm() {
        return this.omniformContainer.isActive();
    }

    public void showAccount() {
        defocus();
        Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "show account", this.savedText);
        this.omniformContainer.showAccount();
    }

    public void setLink(GalleryViewerFragment fragment) {
        setText(fragment.getDisplayUrl());
        this.linkUrl = this.savedText;
        if (fragment.getImage().getThumbnailUrl() != null) {
            this.linkThumbnailUrl = fragment.getImage().getThumbnailUrl();
        }
    }

    private void timeout() {
        FragmentRouter.INSTANCE.stopMatching();
        if (this.fragmentSuggestions.getVisible() > 0) {
            this.searching.hide();
        } else {
            this.searching.indicateNotFound();
        }
    }

    private void clear(String query) {
        this.fragmentSuggestions.removeSuggestions(query);
        this.suggestUrlTask.cancel(true);
        this.suggestNameTask.cancel(true);
        if (getHandler() != null) {
            getHandler().removeCallbacks(this.timeoutSuggestions);
            getHandler().removeCallbacks(this.suggestWait);
        }
        timeout();
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String query = charSequence.toString();
        if (this.fragmentSuggestions != null && this.listenToTextChange && prepareForSuggest(query)) {
            getHandler().postDelayed(this.suggestWait, 700);
        }
    }

    private boolean prepareForSuggest(String query) {
        clear(query);
        if (query.trim().length() < 1) {
            this.searching.showIntroText();
            return false;
        }
        this.searching.hideIntroText();
        this.suggestQuery = query;
        return true;
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
