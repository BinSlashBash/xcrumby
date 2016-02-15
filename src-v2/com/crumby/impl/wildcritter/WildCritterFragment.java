/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.view.ViewGroup
 */
package com.crumby.impl.wildcritter;

import android.net.Uri;
import android.view.ViewGroup;
import com.crumby.GalleryViewer;
import com.crumby.impl.sankakuchan.SankakuChanFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class WildCritterFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://wildcritters.ws";
    public static final int BREADCRUMB_ICON = 2130837565;
    public static final String BREADCRUMB_NAME = "ws";
    public static final String DISPLAY_NAME = "ws";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("wildcritters.ws") + ")";
    public static final String REGEX_URL = REGEX_BASE + ".*";
    public static final String ROOT_NAME = "wildcritters.ws";
    public static final int SEARCH_FORM_ID = 2130903055;

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalProducer(){

            @Override
            protected String getBaseUrl() {
                return "http://wildcritters.ws";
            }

            @Override
            public String getHostUrl() {
                String string2 = .getQueryParameter(Uri.parse((String)super.getHostUrl()), super.getHostUrl(), "tags");
                String string3 = this.getBaseUrl() + "?tags=";
                String string4 = string2;
                if (string2 == null) {
                    string4 = string2;
                    if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(WildCritterFragment.class)) {
                        string4 = "rating:safe";
                    }
                }
                string2 = string3;
                if (string4 != null) {
                    string2 = string3;
                    if (!string4.equals("")) {
                        string2 = string3 + Uri.encode((String)string4);
                    }
                }
                return string2 + Uri.encode((String)GalleryViewer.getBlacklist());
            }

            @Override
            protected String getRegexForMatchingId() {
                return null;
            }

            @Override
            protected String getScriptName() {
                return SankakuChanFragment.class.getSimpleName();
            }
        };
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903105;
    }

    @Override
    public String getSearchArgumentName() {
        return "q";
    }

    @Override
    public String getSearchPrefix() {
        return "ws";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }

}

