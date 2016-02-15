/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewPropertyAnimator
 *  android.widget.ImageButton
 *  android.widget.LinearLayout
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.crumby.BusProvider;
import com.crumby.lib.SearchForm;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.OmnibarView;
import com.squareup.otto.Bus;
import java.net.MalformedURLException;
import java.net.URL;

public class GalleryPanel
extends LinearLayout
implements View.OnTouchListener {
    String DEBUG_TAG = "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDd";
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

    public GalleryPanel(Context context) {
        super(context);
        this.animator = this.animate();
        this.moves = 0;
        this.moveY = new MoveY();
        this.lastY = 0;
    }

    public GalleryPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.animator = this.animate();
        this.moves = 0;
        this.moveY = new MoveY();
        this.lastY = 0;
    }

    public GalleryPanel(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.animator = this.animate();
        this.moves = 0;
        this.moveY = new MoveY();
        this.lastY = 0;
    }

    private void animateMove() {
        if (this.getY() != (float)this.moveY.y) {
            this.animator.cancel();
            this.setY((float)this.moveY.y);
        }
    }

    private void clearSearchForm() {
        this.gallerySearchFormView.removeAllViews();
    }

    private String setSearchForm(int n2, String string2) {
        View view = ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(n2, null);
        this.gallerySearchFormView = (ViewGroup)this.findViewById(2131492996);
        this.clearSearchForm();
        this.gallerySearchFormView.addView(view);
        this.gallerySearchFormView.setOnTouchListener((View.OnTouchListener)this);
        return this.setFormValuesFromSearchUrl(string2);
    }

    public void hide() {
        if (this.showing) {
            this.animate().y((float)this.originalHeight);
            this.showing = false;
            if (this.breadcrumbListening != null) {
                this.breadcrumbListening.deselect();
                this.breadcrumbListening = null;
            }
        }
    }

    public boolean isShowing() {
        return this.showing;
    }

    public void onFinishInflate() {
        this.submitButton = (ImageButton)this.findViewById(2131492997);
        this.submitButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                BusProvider.BUS.get().post(new UrlChangeEvent(GalleryPanel.this.breadcrumbListening.getFragmentIndex().getBaseUrl() + "?" + GalleryPanel.this.searchForm.encodeFormData()));
                GalleryPanel.this.hide();
            }
        });
        this.gallerySearchFormView = (ViewGroup)this.findViewById(2131492996);
        this.bringToFront();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int n2 = 0;
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            default: {
                return super.onTouchEvent(motionEvent);
            }
            case 2: {
                if (this.lastY != 0) {
                    n2 = (int)motionEvent.getY() - this.lastY;
                }
                this.lastY = (int)motionEvent.getY();
                this.moveY.setY(n2 / 10 * 10);
                this.animateMove();
                ++this.moves;
                return true;
            }
            case 1: 
        }
        if (this.moveY.y <= -200 || this.moveY.y < 0 && this.moves < 10) {
            this.hide();
            this.omnibarView.dismissOmnibarModal();
        } else {
            this.animator.y(0.0f).start();
        }
        this.moves = 0;
        this.moveY.y = 0;
        this.lastY = 0;
        return true;
    }

    public void rebuildForm(int n2, Breadcrumb breadcrumb) {
        if (this.searchFormId == n2 && this.showing) {
            return;
        }
        this.hide();
        this.breadcrumbListening = breadcrumb;
        this.update(breadcrumb.getUrl(), n2);
        this.show();
    }

    public void setActive(boolean bl2) {
        this.active = bl2;
        if (bl2) {
            this.bringToFront();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String setFormValuesFromSearchUrl(String string2) {
        String string3 = null;
        if (string2.equals("")) return string3;
        try {
            return this.searchForm.setFormData(new URL(string2));
        }
        catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
            return null;
        }
    }

    public void setOmnibarView(OmnibarView omnibarView) {
        this.omnibarView = omnibarView;
    }

    public void setVisibility(int n2) {
        super.setVisibility(n2);
    }

    public void show() {
        if (this.active) {
            this.showing = true;
            if (this.getVisibility() == 4) {
                this.currentY = this.getY();
                this.originalHeight = - this.getHeight();
                this.setY((float)this.originalHeight);
            }
            this.setVisibility(0);
            this.animate().y(this.currentY);
            this.bringToFront();
        }
    }

    public String update(String string2, int n2) {
        this.clearSearchForm();
        if (n2 == -1 || n2 == 0) {
            this.setActive(false);
            return null;
        }
        this.searchFormId = n2;
        this.setActive(true);
        return this.setSearchForm(n2, string2);
    }

    class MoveY {
        private int[] lastYs;
        public int y;

        MoveY() {
            this.y = 0;
            this.lastYs = new int[2000];
        }

        public void setY(int n2) {
            int n3 = this.y + n2;
            int n4 = Math.abs(n3);
            if (this.lastYs[n4] == 1) {
                this.lastYs[n4] = 0;
                return;
            }
            n2 = n3;
            if (n3 > 0) {
                n2 = 0;
            }
            this.y = n2;
            this.lastYs[n4] = 1;
        }
    }

}

