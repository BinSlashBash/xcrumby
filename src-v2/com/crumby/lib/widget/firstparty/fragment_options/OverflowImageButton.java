/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.util.AttributeSet
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageButton
 *  android.widget.PopupMenu
 *  android.widget.PopupMenu$OnMenuItemClickListener
 */
package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.InsertFragmentLinkIntoDatabaseEvent;
import com.squareup.otto.Bus;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class OverflowImageButton
extends ImageButton
implements View.OnClickListener {
    private GalleryImage image;
    private String url;

    public OverflowImageButton(Context context) {
        super(context);
    }

    public OverflowImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OverflowImageButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void favoriteImage() {
        String string2 = null;
        if (this.image != null) {
            string2 = this.image.getThumbnailUrl();
        }
        BusProvider.BUS.get().post(new InsertFragmentLinkIntoDatabaseEvent(this.url, string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void openImageInWeb() {
        String string2 = this.image != null && this.image.getLinkUrl() != null ? this.image.getLinkUrl() : this.url;
        OverflowImageButton.openWebBrowser(string2, this.getContext());
    }

    public static void openWebBrowser(String string2, Context context) {
        if (string2 == null) {
            return;
        }
        Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "open in web", string2);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse((String)string2));
        context.startActivity(intent);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void share() {
        String string2;
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        String string3 = "Image found with crumby!";
        if (this.image != null && this.image.getTitle() != null) {
            string2 = this.image.getLinkUrl();
            string3 = this.image.getTitle();
            this.url = this.image.getLinkUrl();
        } else {
            if (this.url == null) return;
            if (this.url.equals("")) return;
            string2 = this.url;
        }
        string2 = string2 + ". Found with Crumby, Universal Image Browser [http://tinyurl.com/getcrumby]";
        intent.putExtra("android.intent.extra.SUBJECT", string3);
        intent.putExtra("android.intent.extra.TEXT", string2);
        Analytics.INSTANCE.newEvent(AnalyticsCategories.SHARING, "image share", this.url);
        this.getContext().startActivity(Intent.createChooser((Intent)intent, (CharSequence)("Share \"" + this.url + "\" via")));
    }

    public void initialize(GalleryImage galleryImage) {
        this.image = galleryImage;
        this.setOnClickListener((View.OnClickListener)this);
    }

    public void initialize(String string2) {
        this.url = string2;
        this.setOnClickListener((View.OnClickListener)this);
        this.setVisibility(0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onClick(View view) {
        view = new PopupMenu(this.getContext(), (View)this);
        view.getMenuInflater().inflate(2131689474, view.getMenu());
        for (Object object : view.getClass().getDeclaredFields()) {
            try {
                if (!"mPopup".equals(object.getName())) continue;
                object.setAccessible(true);
                object = object.get((Object)view);
                Class.forName(object.getClass().getName()).getMethod("setForceShowIcon", Boolean.TYPE).invoke(object, true);
                break;
            }
            catch (Exception var5_6) {
                // empty catch block
            }
        }
        view.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == 2131493173) {
                    OverflowImageButton.this.share();
                    return true;
                }
                if (menuItem.getItemId() != 2131493174) return true;
                OverflowImageButton.this.openImageInWeb();
                return true;
            }
        });
        view.show();
    }

}

