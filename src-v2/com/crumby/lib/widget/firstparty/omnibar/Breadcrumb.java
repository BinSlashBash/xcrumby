/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.InputFilter
 *  android.text.InputFilter$LengthFilter
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;

public class Breadcrumb
extends Button {
    private boolean dropdownMode;
    private FragmentIndex fragmentIndex;
    private int index;
    private String url;

    /*
     * Enabled aggressive block sorting
     */
    public Breadcrumb(Context context, FragmentIndex fragmentIndex, int n2, String string2) {
        boolean bl2 = true;
        super(context, null);
        this.setSingleLine(true);
        this.setHorizontallyScrolling(true);
        this.setSelected(false);
        this.setClickable(true);
        this.setEllipsize(TextUtils.TruncateAt.START);
        this.index = n2;
        this.fragmentIndex = fragmentIndex;
        if (!fragmentIndex.isSuggestable() || fragmentIndex.getSearchFormId() == -1) {
            bl2 = false;
        }
        this.dropdownMode = bl2;
        this.url = string2;
        if (string2 == null) {
            this.url = fragmentIndex.getBaseUrl();
        }
        this.render();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setBreadcrumbDrawable() {
        int n2;
        if (this.index == 0) {
            n2 = 2130837525;
        } else {
            n2 = 2130837513;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
            layoutParams.setMargins((int)CrumbyApp.convertDpToPx(this.getResources(), -15.0f), 0, 0, 0);
            this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
        this.setBackgroundDrawable(this.getResources().getDrawable(n2));
        this.getBackground().setAlpha(200);
    }

    private void setFilters(int n2) {
        int n3 = this.getResources().getConfiguration().screenLayout & 15;
        if (n3 == 4 || n3 == 3) {
            return;
        }
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(n2)});
        this.setBreadcrumbText(null);
    }

    private void setIcon() {
        if (this.fragmentIndex.hasBreadcrumbIcon()) {
            Drawable drawable2 = this.getResources().getDrawable(this.fragmentIndex.getBreadcrumbIcon()).mutate();
            if (this.fragmentIndex.getParent() != null || this.fragmentIndex.getBreadcrumbIcon() == 2130837640) {
                drawable2.setAlpha(127);
            }
            this.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
        }
    }

    public void alter(String string2) {
        this.fragmentIndex = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(string2);
        this.url = string2;
        this.setBreadcrumbText(null);
        this.invalidate();
    }

    public boolean click() {
        return false;
    }

    public void defocus() {
        this.setPadding(0, 0, (int)CrumbyApp.convertDpToPx(this.getResources(), 0.0f), 0);
        this.setBreadcrumbDrawable();
    }

    public void deselect() {
        this.setSelected(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void focus() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setBackground(null);
        } else {
            this.setBackgroundDrawable(null);
        }
        if (this.index == 0) {
            this.setPadding((int)CrumbyApp.convertDpToPx(this.getResources(), 5.0f), 0, 0, 0);
            return;
        }
        this.setPadding((int)CrumbyApp.convertDpToPx(this.getResources(), 15.0f), 0, 0, 0);
    }

    public FragmentIndex getFragmentIndex() {
        return this.fragmentIndex;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean hasPanel() {
        return this.dropdownMode;
    }

    public boolean hasUrl() {
        if (this.url != null) {
            return true;
        }
        return false;
    }

    public boolean readyForGalleryPanel() {
        if (this.fragmentIndex.getSearchFormId() != -1) {
            this.toggleSelected();
        }
        return this.isSelected();
    }

    public void render() {
        this.setBreadcrumbDrawable();
        this.setBreadcrumbText(null);
        this.setIcon();
    }

    public void restrict(int n2) {
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(n2)});
        this.setBreadcrumbText(null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setBreadcrumbText(String string2) {
        Object var2_3 = null;
        if (this.fragmentIndex.hasAltName() && string2 == null) {
            try {
                string2 = GalleryViewerFragment.matchIdFromUrl(this.fragmentIndex.getRegexUrl(), this.url);
            }
            catch (NullPointerException var1_2) {
                CrDb.d("breadcrumb", var1_2.toString());
                string2 = var2_3;
            }
        } else if (string2 == null) {
            string2 = this.fragmentIndex.getBreadcrumbName();
        }
        this.setText((CharSequence)string2);
        if (string2 != null && !string2.equals("")) {
            this.setVisibility(0);
            return;
        }
        this.setVisibility(8);
    }

    public void setFragmentIndex(FragmentIndex fragmentIndex) {
        this.fragmentIndex = fragmentIndex;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void toggleSelected() {
        boolean bl2 = !this.isSelected();
        this.setSelected(bl2);
    }
}

