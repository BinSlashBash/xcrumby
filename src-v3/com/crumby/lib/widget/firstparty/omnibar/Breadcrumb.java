package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.media.TransportMediator;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils.TruncateAt;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import uk.co.senab.photoview.IPhotoView;

public class Breadcrumb extends Button {
    private boolean dropdownMode;
    private FragmentIndex fragmentIndex;
    private int index;
    private String url;

    private void setBreadcrumbDrawable() {
        int draw;
        if (this.index == 0) {
            draw = C0065R.drawable.breadcrumb_first;
        } else {
            draw = C0065R.drawable.breadcrumb;
            LayoutParams margins = new LayoutParams(-2, -1);
            margins.setMargins((int) CrumbyApp.convertDpToPx(getResources(), -15.0f), 0, 0, 0);
            setLayoutParams(margins);
        }
        setBackgroundDrawable(getResources().getDrawable(draw));
        getBackground().setAlpha(IPhotoView.DEFAULT_ZOOM_DURATION);
    }

    public void setBreadcrumbText(String text) {
        String breadcrumbText = null;
        if (this.fragmentIndex.hasAltName() && text == null) {
            try {
                breadcrumbText = GalleryViewerFragment.matchIdFromUrl(this.fragmentIndex.getRegexUrl(), this.url);
            } catch (NullPointerException e) {
                CrDb.m0d("breadcrumb", e.toString());
            }
        } else if (text == null) {
            breadcrumbText = this.fragmentIndex.getBreadcrumbName();
        } else {
            breadcrumbText = text;
        }
        setText(breadcrumbText);
        if (breadcrumbText == null || breadcrumbText.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            setVisibility(8);
        } else {
            setVisibility(0);
        }
    }

    public Breadcrumb(Context context, FragmentIndex fragmentIndex, int index, String url) {
        boolean z = true;
        super(context, null);
        setSingleLine(true);
        setHorizontallyScrolling(true);
        setSelected(false);
        setClickable(true);
        setEllipsize(TruncateAt.START);
        this.index = index;
        this.fragmentIndex = fragmentIndex;
        if (!fragmentIndex.isSuggestable() || fragmentIndex.getSearchFormId() == -1) {
            z = false;
        }
        this.dropdownMode = z;
        this.url = url;
        if (url == null) {
            this.url = fragmentIndex.getBaseUrl();
        }
        render();
    }

    public void render() {
        setBreadcrumbDrawable();
        setBreadcrumbText(null);
        setIcon();
    }

    private void setIcon() {
        if (this.fragmentIndex.hasBreadcrumbIcon()) {
            Drawable leftIcon = getResources().getDrawable(this.fragmentIndex.getBreadcrumbIcon()).mutate();
            if (this.fragmentIndex.getParent() != null || this.fragmentIndex.getBreadcrumbIcon() == C0065R.drawable.ic_action_search) {
                leftIcon.setAlpha(TransportMediator.KEYCODE_MEDIA_PAUSE);
            }
            setCompoundDrawablesWithIntrinsicBounds(leftIcon, null, null, null);
        }
    }

    public FragmentIndex getFragmentIndex() {
        return this.fragmentIndex;
    }

    public void setFragmentIndex(FragmentIndex fragmentIndex) {
        this.fragmentIndex = fragmentIndex;
    }

    public String getUrl() {
        return this.url;
    }

    public void focus() {
        if (VERSION.SDK_INT >= 16) {
            setBackground(null);
        } else {
            setBackgroundDrawable(null);
        }
        if (this.index == 0) {
            setPadding((int) CrumbyApp.convertDpToPx(getResources(), 5.0f), 0, 0, 0);
        } else {
            setPadding((int) CrumbyApp.convertDpToPx(getResources(), 15.0f), 0, 0, 0);
        }
    }

    private void setFilters(int maxLength) {
        int screenSize = getResources().getConfiguration().screenLayout & 15;
        if (screenSize != 4 && screenSize != 3) {
            setFilters(new InputFilter[]{new LengthFilter(maxLength)});
            setBreadcrumbText(null);
        }
    }

    public void defocus() {
        setPadding(0, 0, (int) CrumbyApp.convertDpToPx(getResources(), 0.0f), 0);
        setBreadcrumbDrawable();
    }

    public void deselect() {
        setSelected(false);
    }

    public boolean hasPanel() {
        return this.dropdownMode;
    }

    public void toggleSelected() {
        setSelected(!isSelected());
    }

    public boolean click() {
        return false;
    }

    public boolean hasUrl() {
        return this.url != null;
    }

    public boolean readyForGalleryPanel() {
        if (this.fragmentIndex.getSearchFormId() != -1) {
            toggleSelected();
        }
        return isSelected();
    }

    public void alter(String url) {
        this.fragmentIndex = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(url);
        this.url = url;
        setBreadcrumbText(null);
        invalidate();
    }

    public void restrict(int i) {
        setFilters(new InputFilter[]{new LengthFilter(i)});
        setBreadcrumbText(null);
    }
}
