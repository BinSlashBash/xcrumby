/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.crumby.impl.danbooru;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.danbooru.DanbooruAttributes;
import com.crumby.impl.danbooru.DanbooruGalleryFragment;
import com.crumby.impl.danbooru.DanbooruImageProducer;
import com.crumby.lib.ExtraAttributes;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.crumby.lib.widget.firstparty.omnibar.UrlCrumb;

public class DanbooruImageFragment
extends BooruImageFragment {
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
        REGEX_URL = REGEX_BASE + "\\??" + ".*";
        MATCH_POOL_ID = REGEX_BASE + "\\?.*" + "pool_id=([0-9]+)" + ".*";
        BREADCRUMB_PARENT_CLASS = DanbooruGalleryFragment.class;
    }

    @Override
    protected void addTags() throws ClassCastException {
        super.addTags();
        this.addTags(this.getView().findViewById(2131492931), this.characterTags, ((DanbooruAttributes)this.getImage().attr()).getCharacterTags());
        this.addTags(this.getView().findViewById(2131492929), this.artistTags, ((DanbooruAttributes)this.getImage().attr()).getArtistTags());
        this.addTags(this.getView().findViewById(2131492927), this.copyrightTags, ((DanbooruAttributes)this.getImage().attr()).getCopyrightTags());
    }

    @Override
    protected GalleryProducer createProducer() {
        return new DanbooruImageProducer();
    }

    @Override
    protected int getBooruLayout() {
        return 2130903052;
    }

    @Override
    protected String getTagUrl(String string2) {
        return "http://danbooru.donmai.us?tags=" + Uri.encode((String)string2);
    }

    @Override
    protected ViewGroup inflateMetadataLayout(LayoutInflater layoutInflater) {
        layoutInflater = super.inflateMetadataLayout(layoutInflater);
        this.characterTags = (ViewGroup)layoutInflater.findViewById(2131492932);
        this.artistTags = (ViewGroup)layoutInflater.findViewById(2131492930);
        this.copyrightTags = (ViewGroup)layoutInflater.findViewById(2131492928);
        ((TextView)layoutInflater.findViewById(2131492905)).setText((CharSequence)"General Tags");
        return layoutInflater;
    }

    @Override
    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbListModifier) {
        if (this.getUrl().matches(MATCH_POOL_ID)) {
            String string2 = DanbooruImageFragment.matchIdFromUrl(MATCH_POOL_ID, this.getUrl());
            breadcrumbListModifier.addNew(new UrlCrumb(1, "http://danbooru.donmai.us/pools/"), new UrlCrumb(2, "http://danbooru.donmai.us/pools/" + string2));
        }
        super.setBreadcrumbs(breadcrumbListModifier);
    }
}

