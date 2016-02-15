/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.boorus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.CrDb;
import com.crumby.impl.gelbooru.GelbooruProducer;
import com.crumby.lib.FormSearchHandler;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.SearchForm;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class BoorusFragment
extends GalleryGridFragment {
    public static final String API_ROOT_SUFFIX = "/index.php?page=dapi&s=post&q=index";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837565;
    public static final String BREADCRUMB_NAME = "BOORU";
    public static final boolean DEFAULT_TO_HIDDEN = true;
    public static final FormSearchHandler FORM_SEARCH_HANDLER;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "booru.org";
    public static final String SAFE_API_ROOT_SUFFIX = "&tags=rating%3Asafe";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final boolean SUGGESTABLE = true;
    private boolean mustUseUniversal;

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + BoorusFragment.captureMinimumLength("[a-zA-Z0-9]", 4) + "\\." + Pattern.quote("booru.org");
        REGEX_URL = REGEX_BASE + ".*";
        FORM_SEARCH_HANDLER = new FormSearchHandler(){

            @Override
            public int getIcon(FragmentIndex fragmentIndex, String string2) {
                return 0;
            }

            @Override
            public String getTitle(FragmentIndex fragmentIndex, String string2) {
                return "BOORUS " + BoorusFragment.getSubdomain(string2).toUpperCase();
            }

            @Override
            public String getUrlForSearch(String string2, SearchForm searchForm) {
                return BoorusFragment.getBaseUrl(string2) + "/index.php?page=post&s=list&" + searchForm.encodeFormData();
            }
        };
    }

    public static String getBaseUrl(String string2) {
        return "http://" + BoorusFragment.getSubdomain(string2) + ".booru.org";
    }

    private static String getSubdomain(String string2) {
        String string3;
        string2 = string3 = BoorusFragment.matchIdFromUrl(REGEX_URL, string2);
        if (string3 == null) {
            string2 = "invalid";
        }
        return string2;
    }

    @Override
    public View createFragmentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.createFragmentView(layoutInflater, viewGroup, bundle);
    }

    @Override
    protected GalleryProducer createProducer() {
        String string2 = BoorusFragment.getBaseUrl(this.getUrl());
        if (!this.mustUseUniversal) {
            return new GelbooruProducer(string2 + "/", string2 + "/index.php?page=dapi&s=post&q=index", string2 + "/index.php?page=dapi&s=post&q=index" + "&tags=rating%3Asafe", null);
        }
        CrDb.d("boorus fragment", "spinning up universal producer");
        return new UniversalProducer(){

            @Override
            protected String getBaseUrl() {
                return BoorusFragment.getBaseUrl(BoorusFragment.this.getUrl());
            }

            @Override
            protected String getRegexForMatchingId() {
                return null;
            }

            @Override
            protected String getScriptName() {
                return BoorusFragment.class.getSimpleName();
            }
        };
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903105;
    }

    @Override
    public String getSearchArgumentName() {
        return "tags";
    }

    @Override
    public String getSearchPrefix() {
        return BoorusFragment.getSubdomain(this.getUrl());
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }

    @Override
    public void showError(DisplayError displayError, String string2, String string3) {
        if (displayError.equals(DisplayError.EMPTY_GALLERY) && !this.mustUseUniversal) {
            this.mustUseUniversal = true;
            this.producer = this.createProducer();
            this.adapter.initialize(this);
            this.adapter.startFetching();
            return;
        }
        super.showError(displayError, string2, string3);
    }

}

