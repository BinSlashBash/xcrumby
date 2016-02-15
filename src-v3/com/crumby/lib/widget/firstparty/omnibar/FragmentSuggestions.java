package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentLink;
import com.crumby.lib.router.FragmentRouter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FragmentSuggestions extends LinearLayout implements OnClickListener {
    private BreadcrumbContainer breadcrumbContainer;
    private boolean searchGalleries;
    private Set<String> suggestionIds;
    int visible;

    public FragmentSuggestions(Context context) {
        super(context);
    }

    public FragmentSuggestions(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentSuggestions(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void show() {
        setVisibility(0);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).findViewById(C0065R.id.fragment_suggestion_link).setOnClickListener(this);
        }
        this.suggestionIds = new HashSet();
    }

    public void hide() {
        setVisibility(8);
    }

    private FragmentSuggestion addSuggestion(FragmentLink link, Drawable drawable) {
        if (this.visible >= getChildCount()) {
            return null;
        }
        int i = this.visible;
        this.visible = i + 1;
        FragmentSuggestion suggestion = (FragmentSuggestion) getChildAt(i);
        suggestion.setImage(drawable);
        suggestion.setFragmentLink(link);
        return suggestion;
    }

    private FragmentSuggestion addSuggestion(String title, String linksTo, String thumbnailUrl) {
        return addSuggestion(new FragmentLink(title, linksTo, thumbnailUrl, 0), null);
    }

    private FragmentSuggestion addSuggestionWithNoBaseUrl(String title, String linksTo, Drawable drawable) {
        FragmentLink link = new FragmentLink(title, linksTo, null, 0);
        link.setHideBaseUrl(true);
        link.setMandatory(true);
        return addSuggestion(link, drawable);
    }

    private void addSearchSuggestion(String searchText) {
        if (!searchText.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            if (searchText.matches(UnsupportedUrlFragment.REGEX_URL) || searchText.contains("?") || searchText.contains("crumby://")) {
                this.searchGalleries = false;
                return;
            }
            this.searchGalleries = true;
            addSuggestionWithNoBaseUrl("Search All Galleries for \"" + searchText + "\"", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + searchText, getResources().getDrawable(C0065R.drawable.ic_action_search));
        }
    }

    public void removeSuggestions(String query) {
        this.visible = 0;
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).setVisibility(8);
        }
        this.suggestionIds.clear();
        addSearchSuggestion(query);
    }

    public void onClick(View v) {
        FragmentSuggestion suggestion = (FragmentSuggestion) v.getParent();
        if (suggestion.isClickable()) {
            this.breadcrumbContainer.removeBreadcrumbs();
            String url = suggestion.getUrl();
            Analytics.INSTANCE.newNavigationEvent("fragment suggestion click", url);
            BusProvider.BUS.get().post(new UrlChangeEvent(url));
        }
    }

    public synchronized void appendFragmentSuggestions(String searchText, List<FragmentLink> matchedFragments) {
        for (FragmentLink matchedFragment : matchedFragments) {
            if (!this.suggestionIds.contains(matchedFragment.getBaseUrl())) {
                FragmentSuggestion suggestion = addSuggestion(matchedFragment, null);
                if (suggestion == null) {
                    break;
                }
                suggestion.highlight(searchText);
                this.suggestionIds.add(matchedFragment.getBaseUrl());
            }
        }
    }

    public boolean canSearchGalleries() {
        return this.searchGalleries;
    }

    public void setBreadcrumbContainer(BreadcrumbContainer breadcrumbContainer) {
        this.breadcrumbContainer = breadcrumbContainer;
    }

    public int getVisible() {
        return this.visible;
    }

    public void appendTryParse(String linkUrl) {
        if (this.suggestionIds.isEmpty() && !this.searchGalleries) {
            addSuggestionWithNoBaseUrl("Try to parse \"" + linkUrl + "\"?", linkUrl, getResources().getDrawable(C0065R.drawable.ic_action_warning));
        }
    }

    public int remainingSpace() {
        return (getChildCount() - this.visible) - 2;
    }

    public void appendFinal(String query, String linkThumbnailUrl, boolean linkUrlIsQuery) {
        FragmentIndex findMatch = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(query);
        boolean showTryParse = false;
        if (findMatch != null) {
            if (findMatch.getFragmentClass().equals(UnsupportedUrlFragment.class)) {
                showTryParse = true;
            } else if (!linkUrlIsQuery) {
                showTryParse = true;
            } else if (!this.suggestionIds.contains(query)) {
                FragmentSuggestion suggestion = addSuggestion("Favorite?", query, linkThumbnailUrl);
                if (suggestion != null) {
                    suggestion.setHideBackground(true);
                    suggestion.notFavoritedYet();
                } else {
                    return;
                }
            }
            if (showTryParse) {
                appendTryParse(query);
            }
        }
    }
}
