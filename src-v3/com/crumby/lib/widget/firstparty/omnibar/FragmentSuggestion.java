package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.events.InsertFragmentLinkIntoDatabaseEvent;
import com.crumby.lib.events.RemoveFragmentLinkFromDatabaseEvent;
import com.crumby.lib.router.FragmentLink;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.squareup.picasso.Picasso;
import java.util.regex.Pattern;

public class FragmentSuggestion extends LinearLayout {
    private boolean clickable;
    private FragmentSuggestionFavourite favourite;
    FragmentLink fragmentLink;
    private ImageView image;
    private View link;
    private TextView name;
    private TextView url;

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.FragmentSuggestion.1 */
    class C01531 implements OnClickListener {
        C01531() {
        }

        public void onClick(View v) {
            FragmentSuggestion.this.favourite.toggle();
            if (FragmentSuggestion.this.favourite.isChecked()) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "add favorite", FragmentSuggestion.this.fragmentLink.getBaseUrl());
                BusProvider.BUS.get().post(new InsertFragmentLinkIntoDatabaseEvent(FragmentSuggestion.this.fragmentLink.getBaseUrl(), FragmentSuggestion.this.fragmentLink.getFaviconUrl()));
                return;
            }
            Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "remove favorite", FragmentSuggestion.this.fragmentLink.getBaseUrl());
            BusProvider.BUS.get().post(new RemoveFragmentLinkFromDatabaseEvent(FragmentSuggestion.this.fragmentLink.getBaseUrl()));
        }
    }

    private class HighlightBuilder extends SpannableStringBuilder implements HighlightText {
        private int highlightColor;
        private String text;

        private StyleSpan getBss() {
            return new StyleSpan(1);
        }

        private ForegroundColorSpan getFss() {
            return new ForegroundColorSpan(this.highlightColor);
        }

        public HighlightBuilder(String text, int color) {
            super(text);
            this.text = text;
            if (color == 0) {
                color = C0065R.color.SubtleBlue;
            }
            this.highlightColor = FragmentSuggestion.this.getResources().getColor(color);
        }

        public void set(int start, int end) {
            try {
                setSpan(getBss(), start, end, 18);
                setSpan(getFss(), start, end, 18);
            } catch (IndexOutOfBoundsException e) {
                CrDb.m0d("fragment link trie malfunctioning", e.getMessage() + " @ " + this.text);
            }
        }
    }

    public FragmentSuggestion(Context context) {
        super(context);
    }

    public FragmentSuggestion(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentSuggestion(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.name = (TextView) findViewById(C0065R.id.suggestion_name);
        this.url = (TextView) findViewById(C0065R.id.suggestion_url);
        this.image = (ImageView) findViewById(C0065R.id.suggestion_image);
        this.favourite = (FragmentSuggestionFavourite) findViewById(C0065R.id.suggestion_favorite);
        this.favourite.setOnClickListener(new C01531());
        this.link = findViewById(C0065R.id.fragment_suggestion_link);
    }

    public void setFragmentLink(FragmentLink fragmentLink) {
        this.fragmentLink = fragmentLink;
        if (fragmentLink.getDisplayName() == null) {
            this.name.setVisibility(8);
        } else {
            this.name.setVisibility(0);
        }
        this.name.setText(fragmentLink.getDisplayName());
        if (fragmentLink.baseUrlIsHidden() || fragmentLink.getBaseUrl() == null) {
            this.url.setVisibility(8);
        } else {
            this.url.setVisibility(0);
            this.url.setText(fragmentLink.getBaseUrl());
        }
        if (fragmentLink.isMandatory()) {
            this.favourite.setVisibility(8);
        } else {
            this.favourite.setVisibility(0);
        }
        setVisibility(0);
        setImage(fragmentLink.getFaviconUrl());
        setHideBackground(false);
        if (!this.favourite.isChecked()) {
            this.favourite.setChecked(true);
        }
    }

    public void notFavoritedYet() {
        this.favourite.setChecked(false);
    }

    public String getUrl() {
        return this.fragmentLink.getBaseUrl();
    }

    public void setImage(Drawable drawable) {
        this.image.setImageDrawable(drawable);
    }

    public void setImage(String url) {
        if (url != null && !url.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            Picasso.with(getContext()).load(url).into(this.image);
            this.image.isShown();
        }
    }

    private void setSpan() {
    }

    public void setHideBackground(boolean hideBackground) {
        this.clickable = !hideBackground;
        if (hideBackground) {
            this.link.getBackground().setAlpha(0);
            this.link.setEnabled(false);
            return;
        }
        this.link.getBackground().setAlpha(MotionEventCompat.ACTION_MASK);
        this.link.setEnabled(true);
    }

    public boolean isClickable() {
        return this.clickable;
    }

    private static Pattern buildRegexQuery(String query) {
        String newQuery = "(?i).*";
        for (String regexQuery : query.trim().split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
            newQuery = newQuery + "(" + Pattern.quote(regexQuery) + ")" + CrumbyGalleryFragment.REGEX_URL;
        }
        return Pattern.compile(newQuery);
    }

    public void highlight(String searchText) {
        if (this.fragmentLink.fuzzyMatchUrl(buildRegexQuery(searchText)) != -1) {
            this.url.setText(createHighlightedText(searchText, this.fragmentLink.getBaseUrl(), 0));
            this.name.setText(this.fragmentLink.getDisplayName());
            return;
        }
        this.name.setText(createHighlightedText(searchText, this.fragmentLink.getDisplayName(), C0065R.color.LessSubtleBlue));
        this.url.setText(this.fragmentLink.getBaseUrl());
    }

    private HighlightBuilder createHighlightedText(String searchText, String toBeHighlighted, int color) {
        HighlightBuilder sb = new HighlightBuilder(toBeHighlighted, color);
        TextHighlighter.highlight(sb, searchText, toBeHighlighted);
        return sb;
    }
}
