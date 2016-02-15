/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.text.SpannableStringBuilder
 *  android.text.style.ForegroundColorSpan
 *  android.text.style.StyleSpan
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.lib.events.InsertFragmentLinkIntoDatabaseEvent;
import com.crumby.lib.events.RemoveFragmentLinkFromDatabaseEvent;
import com.crumby.lib.router.FragmentLink;
import com.crumby.lib.widget.firstparty.omnibar.FragmentSuggestionFavourite;
import com.crumby.lib.widget.firstparty.omnibar.HighlightText;
import com.crumby.lib.widget.firstparty.omnibar.TextHighlighter;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.regex.Pattern;

public class FragmentSuggestion
extends LinearLayout {
    private boolean clickable;
    private FragmentSuggestionFavourite favourite;
    FragmentLink fragmentLink;
    private ImageView image;
    private View link;
    private TextView name;
    private TextView url;

    public FragmentSuggestion(Context context) {
        super(context);
    }

    public FragmentSuggestion(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FragmentSuggestion(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private static Pattern buildRegexQuery(String string2) {
        String[] arrstring = string2.trim().split(" ");
        string2 = "(?i).*";
        for (String string3 : arrstring) {
            string2 = string2 + "(" + Pattern.quote(string3) + ")" + ".*";
        }
        return Pattern.compile(string2);
    }

    private HighlightBuilder createHighlightedText(String string2, String string3, int n2) {
        HighlightBuilder highlightBuilder = new HighlightBuilder(string3, n2);
        TextHighlighter.highlight(highlightBuilder, string2, string3);
        return highlightBuilder;
    }

    private void setSpan() {
    }

    public String getUrl() {
        return this.fragmentLink.getBaseUrl();
    }

    public void highlight(String object) {
        Pattern pattern = FragmentSuggestion.buildRegexQuery((String)object);
        if (this.fragmentLink.fuzzyMatchUrl(pattern) != -1) {
            object = this.createHighlightedText((String)object, this.fragmentLink.getBaseUrl(), 0);
            this.url.setText((CharSequence)object);
            this.name.setText((CharSequence)this.fragmentLink.getDisplayName());
            return;
        }
        object = this.createHighlightedText((String)object, this.fragmentLink.getDisplayName(), 2131427528);
        this.name.setText((CharSequence)object);
        this.url.setText((CharSequence)this.fragmentLink.getBaseUrl());
    }

    public boolean isClickable() {
        return this.clickable;
    }

    public void notFavoritedYet() {
        this.favourite.setChecked(false);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.name = (TextView)this.findViewById(2131492976);
        this.url = (TextView)this.findViewById(2131492977);
        this.image = (ImageView)this.findViewById(2131492975);
        this.favourite = (FragmentSuggestionFavourite)this.findViewById(2131492978);
        this.favourite.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                FragmentSuggestion.this.favourite.toggle();
                if (FragmentSuggestion.this.favourite.isChecked()) {
                    Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "add favorite", FragmentSuggestion.this.fragmentLink.getBaseUrl());
                    BusProvider.BUS.get().post(new InsertFragmentLinkIntoDatabaseEvent(FragmentSuggestion.this.fragmentLink.getBaseUrl(), FragmentSuggestion.this.fragmentLink.getFaviconUrl()));
                    return;
                }
                Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "remove favorite", FragmentSuggestion.this.fragmentLink.getBaseUrl());
                BusProvider.BUS.get().post(new RemoveFragmentLinkFromDatabaseEvent(FragmentSuggestion.this.fragmentLink.getBaseUrl()));
            }
        });
        this.link = this.findViewById(2131492974);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setFragmentLink(FragmentLink fragmentLink) {
        this.fragmentLink = fragmentLink;
        if (fragmentLink.getDisplayName() == null) {
            this.name.setVisibility(8);
        } else {
            this.name.setVisibility(0);
        }
        this.name.setText((CharSequence)fragmentLink.getDisplayName());
        if (!fragmentLink.baseUrlIsHidden() && fragmentLink.getBaseUrl() != null) {
            this.url.setVisibility(0);
            this.url.setText((CharSequence)fragmentLink.getBaseUrl());
        } else {
            this.url.setVisibility(8);
        }
        if (fragmentLink.isMandatory()) {
            this.favourite.setVisibility(8);
        } else {
            this.favourite.setVisibility(0);
        }
        this.setVisibility(0);
        this.setImage(fragmentLink.getFaviconUrl());
        this.setHideBackground(false);
        if (!this.favourite.isChecked()) {
            this.favourite.setChecked(true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setHideBackground(boolean bl2) {
        boolean bl3 = !bl2;
        this.clickable = bl3;
        if (bl2) {
            this.link.getBackground().setAlpha(0);
            this.link.setEnabled(false);
            return;
        }
        this.link.getBackground().setAlpha(255);
        this.link.setEnabled(true);
    }

    public void setImage(Drawable drawable2) {
        this.image.setImageDrawable(drawable2);
    }

    public void setImage(String string2) {
        if (string2 == null || string2.equals("")) {
            return;
        }
        Picasso.with(this.getContext()).load(string2).into(this.image);
        this.image.isShown();
    }

    private class HighlightBuilder
    extends SpannableStringBuilder
    implements HighlightText {
        private int highlightColor;
        private String text;

        public HighlightBuilder(String string2, int n2) {
            super((CharSequence)string2);
            this.text = string2;
            int n3 = n2;
            if (n2 == 0) {
                n3 = 2131427515;
            }
            this.highlightColor = FragmentSuggestion.this.getResources().getColor(n3);
        }

        private StyleSpan getBss() {
            return new StyleSpan(1);
        }

        private ForegroundColorSpan getFss() {
            return new ForegroundColorSpan(this.highlightColor);
        }

        @Override
        public void set(int n2, int n3) {
            try {
                this.setSpan((Object)this.getBss(), n2, n3, 18);
                this.setSpan((Object)this.getFss(), n2, n3, 18);
                return;
            }
            catch (IndexOutOfBoundsException var3_3) {
                CrDb.d("fragment link trie malfunctioning", var3_3.getMessage() + " @ " + this.text);
                return;
            }
        }
    }

}

