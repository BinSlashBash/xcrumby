/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.os.AsyncTask
 *  android.os.Handler
 *  android.os.IBinder
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.router.FragmentLink;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbContainer;
import com.crumby.lib.widget.firstparty.omnibar.FragmentSuggestions;
import com.crumby.lib.widget.firstparty.omnibar.FragmentSuggestionsHolder;
import com.crumby.lib.widget.firstparty.omnibar.FragmentSuggestionsIndicator;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import com.squareup.otto.Bus;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;

public class OmnisearchField
extends EditText
implements TextView.OnEditorActionListener,
TextWatcher,
FragmentSuggestionsHolder {
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

    public OmnisearchField(Context context) {
        super(context);
    }

    public OmnisearchField(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OmnisearchField(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void clear(String string2) {
        this.fragmentSuggestions.removeSuggestions(string2);
        this.suggestUrlTask.cancel(true);
        this.suggestNameTask.cancel(true);
        if (this.getHandler() != null) {
            this.getHandler().removeCallbacks(this.timeoutSuggestions);
            this.getHandler().removeCallbacks(this.suggestWait);
        }
        this.timeout();
    }

    private void focus() {
        this.setVisibility(0);
        this.requestFocus();
        this.selectAll();
        ((InputMethodManager)this.getContext().getSystemService("input_method")).showSoftInput((View)this, 1);
    }

    private boolean prepareForSuggest(String string2) {
        this.clear(string2);
        if (string2.trim().length() < 1) {
            this.searching.showIntroText();
            return false;
        }
        this.searching.hideIntroText();
        this.suggestQuery = string2;
        return true;
    }

    private void startTimeout() {
        this.getHandler().removeCallbacks(this.timeoutSuggestions);
        this.getHandler().postDelayed(this.timeoutSuggestions, 6000);
    }

    private void suggestAfterWait() {
        if (this.suggestQuery == null || this.suggestQuery.trim().equals("")) {
            return;
        }
        this.suggestUrlTask = new SuggestUrlTask();
        this.startTimeout();
        this.suggestUrlTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new String[]{this.suggestQuery});
        this.omniformContainer.alter(this.suggestQuery);
        this.searching.show();
    }

    private void timeout() {
        FragmentRouter.INSTANCE.stopMatching();
        if (this.fragmentSuggestions.getVisible() > 0) {
            this.searching.hide();
            return;
        }
        this.searching.indicateNotFound();
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
    }

    public void defocus() {
        this.clearFocus();
        this.clearComposingText();
        ((InputMethodManager)this.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    public boolean hasSearchForm() {
        return this.omniformContainer.isActive();
    }

    public void hide() {
        this.clear("");
        this.setVisibility(8);
        this.defocus();
    }

    public boolean onEditorAction(TextView textView, int n2, KeyEvent keyEvent) {
        if (n2 == 2) {
            this.breadcrumbContainer.removeBreadcrumbs();
            Analytics.INSTANCE.newNavigationEvent("omnisearch go", textView.getText().toString());
            BusProvider.BUS.get().post(new UrlChangeEvent(textView.getText().toString()));
        }
        return true;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setOnEditorActionListener((TextView.OnEditorActionListener)this);
        this.suggestUrlTask = new SuggestUrlTask();
        this.suggestNameTask = new SuggestNameTask();
        this.suggestWait = new Runnable(){

            @Override
            public void run() {
                OmnisearchField.this.suggestAfterWait();
            }
        };
        this.timeoutSuggestions = new Runnable(){

            @Override
            public void run() {
                OmnisearchField.this.timeout();
            }
        };
    }

    protected void onFocusChanged(boolean bl2, int n2, Rect rect) {
        super.onFocusChanged(bl2, n2, rect);
        if (!bl2) {
            this.setAlpha(0.25f);
            return;
        }
        this.setAlpha(1.0f);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
        charSequence = charSequence.toString();
        if (this.fragmentSuggestions == null || !this.listenToTextChange || !this.prepareForSuggest((String)charSequence)) {
            return;
        }
        this.getHandler().postDelayed(this.suggestWait, 700);
    }

    public void setBreadcrumbContainer(BreadcrumbContainer breadcrumbContainer) {
        this.breadcrumbContainer = breadcrumbContainer;
    }

    @Override
    public void setFragmentSuggestions(FragmentSuggestions fragmentSuggestions) {
        this.fragmentSuggestions = fragmentSuggestions;
        this.fragmentSuggestions.setBreadcrumbContainer(this.breadcrumbContainer);
        this.addTextChangedListener((TextWatcher)this);
    }

    public void setLink(GalleryViewerFragment galleryViewerFragment) {
        this.setText(galleryViewerFragment.getDisplayUrl());
        this.linkUrl = this.savedText;
        if (galleryViewerFragment.getImage().getThumbnailUrl() != null) {
            this.linkThumbnailUrl = galleryViewerFragment.getImage().getThumbnailUrl();
        }
    }

    public void setOmniformContainer(OmniformContainer omniformContainer) {
        this.omniformContainer = omniformContainer;
    }

    public void setProgressIndicator(FragmentSuggestionsIndicator fragmentSuggestionsIndicator) {
        this.searching = fragmentSuggestionsIndicator;
        fragmentSuggestionsIndicator.hide();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setText(String string2) {
        String string3;
        this.listenToTextChange = false;
        try {
            new URL(string2);
            string3 = string2;
            if (!string2.startsWith("www.")) {
                string3 = "www." + GalleryViewerFragment.stripUrlPrefix(string2);
            }
        }
        catch (MalformedURLException var2_3) {
            string3 = string2;
        }
        super.setText((CharSequence)string3);
        this.savedText = string3;
        this.listenToTextChange = true;
    }

    public void show(List<Breadcrumb> list) {
        this.setText(this.savedText);
        this.focus();
        this.omniformContainer.alter(list);
        this.omniformContainer.backToSearchForm();
        if (this.prepareForSuggest(this.savedText)) {
            this.getHandler().postDelayed(this.suggestWait, 1000);
        }
    }

    public void showAccount() {
        this.defocus();
        Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "show account", this.savedText);
        this.omniformContainer.showAccount();
    }

    class SuggestNameTask
    extends AsyncTask<Integer, Integer, List<FragmentLink>> {
        SuggestNameTask() {
        }

        protected /* varargs */ List<FragmentLink> doInBackground(Integer ... arrinteger) {
            if (OmnisearchField.this.fragmentSuggestions.remainingSpace() <= 0) {
                return null;
            }
            return FragmentRouter.INSTANCE.findMatchingLinks(OmnisearchField.this.suggestQuery, false, OmnisearchField.this.fragmentSuggestions.remainingSpace());
        }

        protected void onPostExecute(List<FragmentLink> list) {
            super.onPostExecute(list);
            if (list == null) {
                return;
            }
            OmnisearchField.this.fragmentSuggestions.appendFragmentSuggestions(OmnisearchField.this.suggestQuery, list);
            OmnisearchField.this.fragmentSuggestions.appendFinal(OmnisearchField.this.suggestQuery, OmnisearchField.this.linkThumbnailUrl, OmnisearchField.this.linkUrl.equals(OmnisearchField.this.suggestQuery));
            OmnisearchField.this.timeout();
        }
    }

    class SuggestUrlTask
    extends AsyncTask<String, Integer, List<FragmentLink>> {
        SuggestUrlTask() {
        }

        protected /* varargs */ List<FragmentLink> doInBackground(String ... arrstring) {
            if (OmnisearchField.this.fragmentSuggestions.remainingSpace() <= 0) {
                return null;
            }
            return FragmentRouter.INSTANCE.findMatchingLinks(OmnisearchField.this.suggestQuery, true, OmnisearchField.this.fragmentSuggestions.remainingSpace());
        }

        protected void onPostExecute(List<FragmentLink> list) {
            super.onPostExecute(list);
            if (list == null) {
                return;
            }
            OmnisearchField.this.fragmentSuggestions.appendFragmentSuggestions(OmnisearchField.this.suggestQuery, list);
            if (list.size() > 0) {
                OmnisearchField.this.searching.hide();
            }
            if (list.size() < 10) {
                OmnisearchField.this.suggestNameTask = new SuggestNameTask();
                OmnisearchField.this.suggestNameTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Integer[]{list.size()});
                return;
            }
            OmnisearchField.this.timeout();
        }
    }

}

