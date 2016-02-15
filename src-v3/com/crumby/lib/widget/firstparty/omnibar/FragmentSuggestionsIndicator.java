package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;

public class FragmentSuggestionsIndicator extends LinearLayout {
    private View introText;
    private View loading;
    private View notFound;

    public FragmentSuggestionsIndicator(Context context) {
        super(context);
    }

    public FragmentSuggestionsIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentSuggestionsIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.loading = findViewById(C0065R.id.loading_suggestions);
        this.notFound = findViewById(C0065R.id.no_suggestions_found);
        this.introText = findViewById(C0065R.id.suggestions_intro);
        this.introText.setVisibility(0);
    }

    public void hide() {
        this.notFound.setVisibility(8);
        this.loading.setVisibility(8);
        clearAnimation();
    }

    public void showIntroText() {
        this.introText.setVisibility(0);
        hide();
    }

    public void indicateNotFound() {
        clearAnimation();
        this.loading.setVisibility(8);
        this.notFound.setVisibility(0);
        this.introText.setVisibility(8);
    }

    public void show() {
        setVisibility(0);
        this.loading.setVisibility(0);
        this.notFound.setVisibility(8);
        setAlpha(0.0f);
        animate().alpha(GalleryViewer.PROGRESS_COMPLETED).setStartDelay(500);
    }

    public void hideIntroText() {
        this.introText.setVisibility(8);
    }
}
