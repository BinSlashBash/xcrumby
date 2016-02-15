package com.crumby.impl.tumblr;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.sankakuchan.SankakuChanImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.List;

public class TumblrImageFragment extends SankakuChanImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    /* renamed from: com.crumby.impl.tumblr.TumblrImageFragment.1 */
    class C14681 extends UniversalImageProducer {
        C14681() {
        }

        protected String getScriptName() {
            return TumblrImageFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return "http://api.tumblr.com";
        }

        protected String getRegexForMatchingId() {
            return TumblrImageFragment.REGEX_URL;
        }
    }

    static {
        REGEX_URL = TumblrArtistFragment.REGEX_BASE + "/post/([0-9]+)" + "/?" + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = TumblrArtistFragment.class;
    }

    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.createFragmentView(inflater, container, savedInstanceState);
        view.findViewById(C0065R.id.website_button).setVisibility(8);
        return view;
    }

    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbList) {
        alterBreadcrumbs(breadcrumbList.getChildren());
        super.setBreadcrumbs(breadcrumbList);
    }

    private void alterBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        if (breadcrumbs != null && breadcrumbs.size() >= 2 && getImage() != null && getImage().getLinkUrl() != null) {
            ((Breadcrumb) breadcrumbs.get(1)).alter("http://" + GalleryViewerFragment.matchIdFromUrl(TumblrArtistFragment.REGEX_URL, getImage().getLinkUrl()) + "." + TumblrFragment.ROOT_NAME);
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    protected GalleryProducer createProducer() {
        return new C14681();
    }

    protected void alternateFillMetadata() {
        super.alternateFillMetadata();
        this.homeGalleryList.findViewById(C0065R.id.website_button).setVisibility(8);
    }

    protected String getTagUrl(String tag) {
        return "http://tumblr.com?tagged=" + Uri.encode(tag);
    }
}
