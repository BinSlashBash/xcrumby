package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.SearchForm;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.OmnibarView;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.games.GamesStatusCodes;
import java.net.MalformedURLException;
import java.net.URL;

public class GalleryPanel extends LinearLayout implements OnTouchListener {
    String DEBUG_TAG;
    private boolean active;
    ViewPropertyAnimator animator;
    private Breadcrumb breadcrumbListening;
    private ImageButton cancelButton;
    private float currentY;
    private ViewGroup gallerySearchFormView;
    private ViewGroup gallerySearchOptions;
    int lastY;
    MoveY moveY;
    int moves;
    private OmnibarView omnibarView;
    int originalHeight;
    private SearchForm searchForm;
    private int searchFormId;
    private boolean showing;
    private ImageButton submitButton;

    /* renamed from: com.crumby.lib.widget.firstparty.GalleryPanel.1 */
    class C01271 implements OnClickListener {
        C01271() {
        }

        public void onClick(View v) {
            BusProvider.BUS.get().post(new UrlChangeEvent(GalleryPanel.this.breadcrumbListening.getFragmentIndex().getBaseUrl() + "?" + GalleryPanel.this.searchForm.encodeFormData()));
            GalleryPanel.this.hide();
        }
    }

    class MoveY {
        private int[] lastYs;
        public int f3y;

        MoveY() {
            this.f3y = 0;
            this.lastYs = new int[GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS];
        }

        public void setY(int y) {
            int newY = this.f3y + y;
            int newYIndex = Math.abs(newY);
            if (this.lastYs[newYIndex] == 1) {
                this.lastYs[newYIndex] = 0;
                return;
            }
            if (newY > 0) {
                newY = 0;
            }
            this.f3y = newY;
            this.lastYs[newYIndex] = 1;
        }
    }

    public GalleryPanel(Context context) {
        super(context);
        this.DEBUG_TAG = "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDd";
        this.animator = animate();
        this.moves = 0;
        this.moveY = new MoveY();
        this.lastY = 0;
    }

    public GalleryPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.DEBUG_TAG = "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDd";
        this.animator = animate();
        this.moves = 0;
        this.moveY = new MoveY();
        this.lastY = 0;
    }

    public GalleryPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.DEBUG_TAG = "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDd";
        this.animator = animate();
        this.moves = 0;
        this.moveY = new MoveY();
        this.lastY = 0;
    }

    public void onFinishInflate() {
        this.submitButton = (ImageButton) findViewById(C0065R.id.gallery_search_submit);
        this.submitButton.setOnClickListener(new C01271());
        this.gallerySearchFormView = (ViewGroup) findViewById(C0065R.id.gallery_search_form);
        bringToFront();
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    public String setFormValuesFromSearchUrl(String currentUrl) {
        String searchMessage = null;
        if (!currentUrl.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            try {
                searchMessage = this.searchForm.setFormData(new URL(currentUrl));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return searchMessage;
    }

    public void show() {
        if (this.active) {
            this.showing = true;
            if (getVisibility() == 4) {
                this.currentY = getY();
                this.originalHeight = -getHeight();
                setY((float) this.originalHeight);
            }
            setVisibility(0);
            animate().y(this.currentY);
            bringToFront();
        }
    }

    public void hide() {
        if (this.showing) {
            animate().y((float) this.originalHeight);
            this.showing = false;
            if (this.breadcrumbListening != null) {
                this.breadcrumbListening.deselect();
                this.breadcrumbListening = null;
            }
        }
    }

    public void setActive(boolean active) {
        this.active = active;
        if (active) {
            bringToFront();
        }
    }

    private void clearSearchForm() {
        this.gallerySearchFormView.removeAllViews();
    }

    private String setSearchForm(int searchFormId, String url) {
        View view = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(searchFormId, null);
        this.gallerySearchFormView = (ViewGroup) findViewById(C0065R.id.gallery_search_form);
        clearSearchForm();
        this.gallerySearchFormView.addView(view);
        this.gallerySearchFormView.setOnTouchListener(this);
        return setFormValuesFromSearchUrl(url);
    }

    public String update(String url, int searchFormId) {
        clearSearchForm();
        if (searchFormId == -1 || searchFormId == 0) {
            setActive(false);
            return null;
        }
        this.searchFormId = searchFormId;
        setActive(true);
        return setSearchForm(searchFormId, url);
    }

    public void rebuildForm(int searchFormId, Breadcrumb breadcrumbListening) {
        if (this.searchFormId != searchFormId || !this.showing) {
            hide();
            this.breadcrumbListening = breadcrumbListening;
            update(breadcrumbListening.getUrl(), searchFormId);
            show();
        }
    }

    public boolean isShowing() {
        return this.showing;
    }

    public void setOmnibarView(OmnibarView urlHandler) {
        this.omnibarView = urlHandler;
    }

    private void animateMove() {
        if (getY() != ((float) this.moveY.f3y)) {
            this.animator.cancel();
            setY((float) this.moveY.f3y);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        int diff = 0;
        switch (MotionEventCompat.getActionMasked(event)) {
            case Std.STD_FILE /*1*/:
                if (this.moveY.f3y <= -200 || (this.moveY.f3y < 0 && this.moves < 10)) {
                    hide();
                    this.omnibarView.dismissOmnibarModal();
                } else {
                    this.animator.y(0.0f).start();
                }
                this.moves = 0;
                this.moveY.f3y = 0;
                this.lastY = 0;
                return true;
            case Std.STD_URL /*2*/:
                if (this.lastY != 0) {
                    diff = ((int) event.getY()) - this.lastY;
                }
                this.lastY = (int) event.getY();
                this.moveY.setY((diff / 10) * 10);
                animateMove();
                this.moves++;
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
