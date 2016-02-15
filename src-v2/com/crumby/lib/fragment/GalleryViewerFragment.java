/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.webkit.WebView
 *  android.widget.Adapter
 */
package com.crumby.lib.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.Adapter;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.GalleryImageClickChange;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.squareup.otto.Bus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class GalleryViewerFragment
extends Fragment
implements GalleryImageClickChange {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected static final String ALPHANUMERIC = "[a-zA-Z0-9]";
    protected static final String ALPHANUMERIC_REPEATING;
    protected static final String ALPHANUMERIC_WITH_DASH = "[a-zA-Z0-9_\\+\\-]";
    protected static final String ALPHANUMERIC_WITH_EXTRAS = "[a-zA-Z0-9_\\+]";
    protected static final String ALPHANUMERIC_WITH_EXTRAS_REPEATING;
    protected static final String CAPTURE_ALPHANUMERIC_REPEATING;
    protected static final String CAPTURE_ALPHANUMERIC_WITH_EXTRAS_REPEATING;
    protected static final String CAPTURE_NUMERIC_REPEATING;
    protected static final String GENERIC_REGEX_URL_PREFIX = "(?:http://www.|https://www.|https://|http://|www.)?";
    public static final String GENERIC_URL_PREFIX = "http://www.|https://www.|https://|http://|www.";
    protected static final String IGNORE_EVERYTHING_AFTER = "(?:/.*)?";
    protected static final IndexField INCLUDE_IN_HOME;
    protected static final IndexField INCLUDE_IN_HOME_FALSE;
    public static final String INCLUDE_IN_HOME_KEY = "include_in_home";
    protected static final String MUST_HAVE_QUERY_GIBBERISH_OR_NOTHING = "(?:\\?.*)?";
    protected static final String NUMERIC = "[0-9]";
    protected static final String NUMERIC_REPEATING;
    protected static final String OPTIONAL_QUERY_MARK = "\\??";
    protected static final String OPTIONAL_SLASH = "/?";
    private static final float PROGRESS_INIT = 0.025f;
    protected static final IndexField SAFE_IN_TOP_LEVEL;
    public static final String SAFE_IN_TOP_LEVEL_KEY = "safe_in_top_level";
    protected static final IndexField SHOW_IN_SEARCH;
    protected static final IndexField SHOW_IN_SEARCH_FALSE;
    public static final String SHOW_IN_SEARCH_KEY = "show_in_search";
    protected Adapter adapter;
    private List<Breadcrumb> breadcrumbs;
    private boolean doNotResume;
    private GalleryImage image;
    private float lastProgress;
    protected GalleryProducer producer;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !GalleryViewerFragment.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
        ALPHANUMERIC_REPEATING = GalleryViewerFragment.rRepeat("[a-zA-Z0-9]");
        ALPHANUMERIC_WITH_EXTRAS_REPEATING = GalleryViewerFragment.rRepeat("[a-zA-Z0-9_\\+]");
        CAPTURE_ALPHANUMERIC_REPEATING = GalleryViewerFragment.rCapture(ALPHANUMERIC_REPEATING);
        CAPTURE_ALPHANUMERIC_WITH_EXTRAS_REPEATING = GalleryViewerFragment.rCapture(ALPHANUMERIC_WITH_EXTRAS_REPEATING);
        NUMERIC_REPEATING = GalleryViewerFragment.rRepeat("[0-9]");
        CAPTURE_NUMERIC_REPEATING = GalleryViewerFragment.rCapture(NUMERIC_REPEATING);
        SAFE_IN_TOP_LEVEL = new IndexField<Boolean>("safe_in_top_level", "Attempt to show only 'Work-safe' images in the top-level gallery.", true);
        SHOW_IN_SEARCH = new IndexField<Boolean>("show_in_search", "Show in search results", true);
        INCLUDE_IN_HOME = new IndexField<Boolean>("include_in_home", "Include on Home Page", true);
        INCLUDE_IN_HOME_FALSE = INCLUDE_IN_HOME.differentDefaultValue(false);
        SHOW_IN_SEARCH_FALSE = SHOW_IN_SEARCH.differentDefaultValue(false);
    }

    protected static final String buildBasicRegexBase(String string2) {
        return "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote(string2);
    }

    protected static final String captureMinimumLength(String string2, int n2) {
        return "(" + string2 + "{" + n2 + ",})";
    }

    public static String matchIdFromUrl(String string2, String string3) {
        Matcher matcher = Pattern.compile(string2).matcher(string3);
        CrDb.d("GalleryViewerFragment", string3 + " " + string2);
        if (matcher.find()) {
            for (int i2 = matcher.groupCount(); i2 >= 0; --i2) {
                if (matcher.group(i2) == null) continue;
                return matcher.group(i2);
            }
        }
        return null;
    }

    protected static final /* varargs */ String matchOne(String ... arrstring) {
        String string2 = "(";
        int n2 = arrstring.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            String string3 = arrstring[i2];
            String string4 = string2;
            if (string3 != arrstring[0]) {
                string4 = string2 + "|";
            }
            string2 = string4 + "(" + string3 + ")";
        }
        return string2 + ")";
    }

    private static final String rCapture(String string2) {
        return "(" + string2 + ")";
    }

    private static final String rRepeat(String string2) {
        return string2 + "+";
    }

    public static String stripUrlPrefix(String string2) {
        Object object = Pattern.compile("http://www.|https://www.|https://|http://|www.").matcher(string2);
        String string3 = string2;
        if (object.find()) {
            object = object.group(0);
            string3 = string2;
            if (string2.indexOf((String)object) == 0) {
                string3 = string2.substring(object.length());
            }
        }
        return string3;
    }

    protected static final String zeroOrOneTimes(String string2) {
        return "(" + string2 + ")?";
    }

    protected boolean checkIfAllowedToResume() {
        boolean bl2 = false;
        if (this.doNotResume) {
            this.doNotResume = false;
            bl2 = true;
        }
        return bl2;
    }

    protected void cleanLinkUrl() {
        String string2 = "http://" + GalleryViewerFragment.stripUrlPrefix(this.getImage().getLinkUrl());
        this.getImage().setLinkUrl(string2);
    }

    public abstract View createFragmentView(LayoutInflater var1, ViewGroup var2, Bundle var3);

    protected abstract GalleryProducer createProducer();

    public abstract void deferSetDescription(String var1);

    public void destroy() {
        this.image.clearViews();
        this.detachFromProducer();
        if (this.breadcrumbs != null) {
            this.breadcrumbs.clear();
        }
    }

    public void detachFromProducer() {
        if (this.producer == null) {
            return;
        }
        this.producer.removeConsumer(this.getConsumer());
        this.producer = null;
    }

    public List<Breadcrumb> getBreadcrumbs() {
        return this.breadcrumbs;
    }

    public abstract GalleryConsumer getConsumer();

    public String getDisplayUrl() {
        return this.getUrl();
    }

    public GalleryImage getImage() {
        if (!$assertionsDisabled && this.image == null) {
            throw new AssertionError();
        }
        return this.image;
    }

    public float getLastProgress() {
        return this.lastProgress;
    }

    public GalleryProducer getProducer() {
        if (this.producer == null) {
            this.producer = this.createProducer();
        }
        return this.producer;
    }

    public GalleryProducer getProducerIfItExists() {
        return this.producer;
    }

    protected int getScreenWidth() {
        return this.getResources().getDisplayMetrics().widthPixels;
    }

    public String getUrl() {
        return this.image.getLinkUrl();
    }

    protected GalleryViewer getViewer() {
        return (GalleryViewer)this.getActivity();
    }

    public abstract void hideClutter();

    public void indicateLastProgressChange() {
        this.setUserVisibleHint(true);
        this.indicateProgressChange(this.lastProgress);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void indicateProgressChange(float f2) {
        if (this.getActivity() == null) {
            return;
        }
        this.lastProgress = f2;
        if (!this.getUserVisibleHint()) return;
        ((GalleryViewer)this.getActivity()).progressChange(f2);
    }

    protected void murderWebview(WebView webView) {
        if (webView != null) {
            if (webView.getParent() != null) {
                ((ViewGroup)webView.getParent()).removeView((View)webView);
            }
            webView.destroy();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.image == null) {
            this.image = new GalleryImage(this.getArguments().getString("url"));
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.getView() == null) {
            return this.createFragmentView(layoutInflater, viewGroup, bundle);
        }
        return this.getView();
    }

    public void onDestroy() {
        this.destroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    protected void overrideBreadcrumbs(List<Breadcrumb> list) {
        this.breadcrumbs = list;
        ((GalleryViewer)this.getActivity()).overrideBreadcrumbs(list);
    }

    protected void postUrlChangeToBus(String string2, Bundle bundle) {
        BusProvider.BUS.get().post(new UrlChangeEvent(string2, bundle));
    }

    protected void postUrlChangeToBusWithProducer(GalleryImage galleryImage) {
        this.producer.shareAndSetCurrentImageFocus(galleryImage.getPosition());
        BusProvider.BUS.get().post(new UrlChangeEvent(galleryImage.getLinkUrl(), this.producer));
    }

    public abstract void prepareForRefresh();

    public void redraw() {
        if (this.getView() != null) {
            this.getView().invalidate();
        }
    }

    public abstract void resume();

    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbListModifier) {
        if (!$assertionsDisabled && this.breadcrumbs != null) {
            throw new AssertionError();
        }
        this.setBreadcrumbs(breadcrumbListModifier.getChildren());
    }

    public void setBreadcrumbs(List<Breadcrumb> object) {
        if (object == null) {
            return;
        }
        this.breadcrumbs = object;
        ArrayList<String> arrayList = new ArrayList<String>();
        object = object.iterator();
        while (object.hasNext()) {
            Breadcrumb breadcrumb = (Breadcrumb)((Object)object.next());
            if (breadcrumb.getText().toString().equals("")) continue;
            arrayList.add(breadcrumb.getText().toString());
        }
        if (this instanceof GalleryImageFragment) {
            arrayList.remove(arrayList.size() - 1);
        }
        this.image.modifyPath(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setImage(GalleryImage galleryImage) {
        if (this.image == null) {
            this.image = galleryImage;
            this.cleanLinkUrl();
        } else {
            CrDb.d("viewer fragment", "copying gallery image " + galleryImage.getImageUrl());
            this.image.copy(galleryImage);
        }
        galleryImage.newPath();
    }

    public void setProducer(GalleryProducer galleryProducer) {
        this.producer = galleryProducer;
    }

    public abstract void showClutter();

    public abstract void stopLoading();

    public abstract boolean undo();

    public void waitOnResume() {
        this.doNotResume = true;
    }

    public abstract boolean willAllowPaging(MotionEvent var1);
}

