package com.crumby.impl.danbooru;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.crumby.C0065R;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.crumby.lib.widget.firstparty.omnibar.UrlCrumb;

public class DanbooruImageFragment extends BooruImageFragment {
    public static final String BASE_URL = "http://danbooru.donmai.us/posts/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final String BREADCRUMB_NAME = "s";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String MATCH_POOL_ID;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    private ViewGroup artistTags;
    private ViewGroup characterTags;
    private ViewGroup copyrightTags;

    static {
        REGEX_BASE = DanbooruGalleryFragment.REGEX_BASE + "/posts/" + CAPTURE_NUMERIC_REPEATING + "/?";
        REGEX_URL = REGEX_BASE + "\\??" + CrumbyGalleryFragment.REGEX_URL;
        MATCH_POOL_ID = REGEX_BASE + "\\?.*" + "pool_id=([0-9]+)" + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = DanbooruGalleryFragment.class;
    }

    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbList) {
        if (getUrl().matches(MATCH_POOL_ID)) {
            String poolId = GalleryViewerFragment.matchIdFromUrl(MATCH_POOL_ID, getUrl());
            String url = "http://danbooru.donmai.us/pools/";
            breadcrumbList.addNew(new UrlCrumb(1, url), new UrlCrumb(2, url + poolId));
        }
        super.setBreadcrumbs(breadcrumbList);
    }

    protected int getBooruLayout() {
        return C0065R.layout.danbooru_image_metadata;
    }

    protected ViewGroup inflateMetadataLayout(LayoutInflater inflater) {
        ViewGroup view = super.inflateMetadataLayout(inflater);
        this.characterTags = (ViewGroup) view.findViewById(C0065R.id.danbooru_character_tags);
        this.artistTags = (ViewGroup) view.findViewById(C0065R.id.danbooru_artist_tags);
        this.copyrightTags = (ViewGroup) view.findViewById(C0065R.id.danbooru_copyright_tags);
        ((TextView) view.findViewById(C0065R.id.booru_tags_title)).setText("General Tags");
        return view;
    }

    protected void addTags() throws ClassCastException {
        super.addTags();
        addTags(getView().findViewById(C0065R.id.danbooru_character_tags_title), this.characterTags, ((DanbooruAttributes) getImage().attr()).getCharacterTags());
        addTags(getView().findViewById(C0065R.id.danbooru_artist_tags_title), this.artistTags, ((DanbooruAttributes) getImage().attr()).getArtistTags());
        addTags(getView().findViewById(C0065R.id.danbooru_copyright_tags_title), this.copyrightTags, ((DanbooruAttributes) getImage().attr()).getCopyrightTags());
    }

    protected GalleryProducer createProducer() {
        return new DanbooruImageProducer();
    }

    protected String getTagUrl(String tag) {
        return "http://danbooru.donmai.us?tags=" + Uri.encode(tag);
    }
}
