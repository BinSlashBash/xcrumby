package com.crumby.lib.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Adapter;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.GalleryImageClickChange;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class GalleryViewerFragment extends Fragment implements GalleryImageClickChange {
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

    public abstract View createFragmentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle);

    protected abstract GalleryProducer createProducer();

    public abstract void deferSetDescription(String str);

    public abstract GalleryConsumer getConsumer();

    public abstract void hideClutter();

    public abstract void prepareForRefresh();

    public abstract void resume();

    public abstract void showClutter();

    public abstract void stopLoading();

    public abstract boolean undo();

    public abstract boolean willAllowPaging(MotionEvent motionEvent);

    static {
        $assertionsDisabled = !GalleryViewerFragment.class.desiredAssertionStatus() ? true : $assertionsDisabled;
        ALPHANUMERIC_REPEATING = rRepeat(ALPHANUMERIC);
        ALPHANUMERIC_WITH_EXTRAS_REPEATING = rRepeat(ALPHANUMERIC_WITH_EXTRAS);
        CAPTURE_ALPHANUMERIC_REPEATING = rCapture(ALPHANUMERIC_REPEATING);
        CAPTURE_ALPHANUMERIC_WITH_EXTRAS_REPEATING = rCapture(ALPHANUMERIC_WITH_EXTRAS_REPEATING);
        NUMERIC_REPEATING = rRepeat(NUMERIC);
        CAPTURE_NUMERIC_REPEATING = rCapture(NUMERIC_REPEATING);
        SAFE_IN_TOP_LEVEL = new IndexField(SAFE_IN_TOP_LEVEL_KEY, "Attempt to show only 'Work-safe' images in the top-level gallery.", Boolean.valueOf(true));
        SHOW_IN_SEARCH = new IndexField(SHOW_IN_SEARCH_KEY, "Show in search results", Boolean.valueOf(true));
        INCLUDE_IN_HOME = new IndexField(INCLUDE_IN_HOME_KEY, "Include on Home Page", Boolean.valueOf(true));
        INCLUDE_IN_HOME_FALSE = INCLUDE_IN_HOME.differentDefaultValue(Boolean.valueOf($assertionsDisabled));
        SHOW_IN_SEARCH_FALSE = SHOW_IN_SEARCH.differentDefaultValue(Boolean.valueOf($assertionsDisabled));
    }

    private static final String rRepeat(String s) {
        return s + "+";
    }

    private static final String rCapture(String s) {
        return "(" + s + ")";
    }

    protected static final String buildBasicRegexBase(String rootName) {
        return GENERIC_REGEX_URL_PREFIX + Pattern.quote(rootName);
    }

    protected static final String zeroOrOneTimes(String s) {
        return "(" + s + ")?";
    }

    protected static final String captureMinimumLength(String s, int x) {
        return "(" + s + "{" + x + ",})";
    }

    protected static final String matchOne(String... strings) {
        String base = "(";
        for (String string : strings) {
            if (string != strings[0]) {
                base = base + "|";
            }
            base = base + "(" + string + ")";
        }
        return base + ")";
    }

    public static String matchIdFromUrl(String pattern, String url) {
        Matcher m = Pattern.compile(pattern).matcher(url);
        CrDb.m0d("GalleryViewerFragment", url + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + pattern);
        if (m.find()) {
            for (int i = m.groupCount(); i >= 0; i--) {
                if (m.group(i) != null) {
                    return m.group(i);
                }
            }
        }
        return null;
    }

    protected GalleryViewer getViewer() {
        return (GalleryViewer) getActivity();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.image == null) {
            this.image = new GalleryImage(getArguments().getString(PlusShare.KEY_CALL_TO_ACTION_URL));
        }
    }

    public List<Breadcrumb> getBreadcrumbs() {
        return this.breadcrumbs;
    }

    protected void murderWebview(WebView webView) {
        if (webView != null) {
            if (webView.getParent() != null) {
                ((ViewGroup) webView.getParent()).removeView(webView);
            }
            webView.destroy();
        }
    }

    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbList) {
        if ($assertionsDisabled || this.breadcrumbs == null) {
            setBreadcrumbs(breadcrumbList.getChildren());
            return;
        }
        throw new AssertionError();
    }

    public GalleryProducer getProducerIfItExists() {
        return this.producer;
    }

    public GalleryProducer getProducer() {
        if (this.producer == null) {
            this.producer = createProducer();
        }
        return this.producer;
    }

    protected void postUrlChangeToBus(String url, Bundle bundle) {
        BusProvider.BUS.get().post(new UrlChangeEvent(url, bundle));
    }

    protected int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    protected void postUrlChangeToBusWithProducer(GalleryImage image) {
        this.producer.shareAndSetCurrentImageFocus(image.getPosition());
        BusProvider.BUS.get().post(new UrlChangeEvent(image.getLinkUrl(), this.producer));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getView() == null) {
            return createFragmentView(inflater, container, savedInstanceState);
        }
        return getView();
    }

    protected void overrideBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
        ((GalleryViewer) getActivity()).overrideBreadcrumbs(breadcrumbs);
    }

    public void setProducer(GalleryProducer producer) {
        this.producer = producer;
    }

    public void setImage(GalleryImage image) {
        if (this.image == null) {
            this.image = image;
            cleanLinkUrl();
        } else {
            CrDb.m0d("viewer fragment", "copying gallery image " + image.getImageUrl());
            this.image.copy(image);
        }
        image.newPath();
    }

    public static String stripUrlPrefix(String url) {
        String linkUrl = url;
        Matcher m = Pattern.compile(GENERIC_URL_PREFIX).matcher(linkUrl);
        if (!m.find()) {
            return linkUrl;
        }
        String found = m.group(0);
        if (linkUrl.indexOf(found) == 0) {
            return linkUrl.substring(found.length());
        }
        return linkUrl;
    }

    protected void cleanLinkUrl() {
        getImage().setLinkUrl("http://" + stripUrlPrefix(getImage().getLinkUrl()));
    }

    public GalleryImage getImage() {
        if ($assertionsDisabled || this.image != null) {
            return this.image;
        }
        throw new AssertionError();
    }

    public String getUrl() {
        return this.image.getLinkUrl();
    }

    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void redraw() {
        if (getView() != null) {
            getView().invalidate();
        }
    }

    public void detachFromProducer() {
        if (this.producer != null) {
            this.producer.removeConsumer(getConsumer());
            this.producer = null;
        }
    }

    public void destroy() {
        this.image.clearViews();
        detachFromProducer();
        if (this.breadcrumbs != null) {
            this.breadcrumbs.clear();
        }
    }

    public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        if (breadcrumbs != null) {
            this.breadcrumbs = breadcrumbs;
            List<String> path = new ArrayList();
            for (Breadcrumb breadcrumb : breadcrumbs) {
                if (!breadcrumb.getText().toString().equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                    path.add(breadcrumb.getText().toString());
                }
            }
            if (this instanceof GalleryImageFragment) {
                path.remove(path.size() - 1);
            }
            this.image.modifyPath(path);
        }
    }

    public void indicateProgressChange(float progress) {
        if (getActivity() != null) {
            this.lastProgress = progress;
            if (getUserVisibleHint()) {
                ((GalleryViewer) getActivity()).progressChange(progress);
            }
        }
    }

    public void indicateLastProgressChange() {
        setUserVisibleHint(true);
        indicateProgressChange(this.lastProgress);
    }

    public float getLastProgress() {
        return this.lastProgress;
    }

    protected boolean checkIfAllowedToResume() {
        if (!this.doNotResume) {
            return $assertionsDisabled;
        }
        this.doNotResume = $assertionsDisabled;
        return true;
    }

    public void waitOnResume() {
        this.doNotResume = true;
    }

    public String getDisplayUrl() {
        return getUrl();
    }
}
