/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.method.LinkMovementMethod
 *  android.text.method.MovementMethod
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.TextView
 */
package com.crumby.impl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.widget.firstparty.ImageCommentsView;
import java.util.List;

public abstract class BooruImageFragment
extends GalleryImageFragment
implements View.OnClickListener {
    private ImageCommentsView comments;
    private TextView description;
    private ViewGroup mainTags;
    private View title;

    private View.OnClickListener getThis() {
        return this;
    }

    protected void addTags() {
        this.addTags(this.title, this.mainTags, this.getImage().getTags());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void addTags(View view, ViewGroup viewGroup, String[] arrstring) {
        CrDb.logTime("booru image fragment", "tags", true);
        int n2 = 0;
        int n3 = (int)this.getResources().getDimension(2131165194);
        for (String string2 : arrstring) {
            int n4 = n2;
            if (string2 != null) {
                if (string2.trim().equals("")) {
                    n4 = n2;
                } else {
                    Button button = new Button(viewGroup.getContext());
                    button.setMaxWidth(n3);
                    viewGroup.addView((View)button);
                    button.setText((CharSequence)string2);
                    button.setOnClickListener(this.getThis());
                    n4 = n2 + 1;
                }
            }
            n2 = n4;
        }
        if (n2 == 0) {
            view.setVisibility(8);
            viewGroup.setVisibility(8);
        }
        CrDb.logTime("booru image fragment", "tags", false);
    }

    protected void alternateFillMetadata() {
    }

    @Override
    protected void fillMetadataView() {
        this.mainTags.postDelayed(new Runnable(){

            @Override
            public void run() {
                try {
                    if (BooruImageFragment.this.getActivity() == null) {
                        return;
                    }
                    if (BooruImageFragment.this.getImage().getDescription() != null) {
                        BooruImageFragment.this.description.setText((CharSequence)BooruImageFragment.this.getImage().getDescription());
                        BooruImageFragment.this.description.setVisibility(0);
                    }
                    BooruImageFragment.this.comments.initialize(BooruImageFragment.this.getImage().getComments());
                    BooruImageFragment.this.addTags();
                    BooruImageFragment.this.alternateFillMetadata();
                    return;
                }
                catch (ClassCastException var1_1) {
                    BooruImageFragment.this.indicateMetadataError("Could not load tags: ", var1_1);
                    return;
                }
                catch (NullPointerException var1_2) {
                    BooruImageFragment.this.indicateMetadataError("Could not load tags: ", var1_2);
                    return;
                }
            }
        }, 400);
    }

    protected int getBooruLayout() {
        return 2130903043;
    }

    protected abstract String getTagUrl(String var1);

    @Override
    protected ViewGroup inflateMetadataLayout(LayoutInflater layoutInflater) {
        layoutInflater = (ViewGroup)layoutInflater.inflate(this.getBooruLayout(), null);
        this.mainTags = (ViewGroup)layoutInflater.findViewById(2131492906);
        this.title = layoutInflater.findViewById(2131492905);
        this.comments = (ImageCommentsView)layoutInflater.findViewById(2131493021);
        this.description = (TextView)layoutInflater.findViewById(2131492904);
        this.description.setMovementMethod(LinkMovementMethod.getInstance());
        return layoutInflater;
    }

    public void onClick(View object) {
        object = ((Button)object).getText().toString();
        Analytics.INSTANCE.newNavigationEvent("booru metadata click", (String)object);
        this.postUrlChangeToBus(this.getTagUrl((String)object), null);
    }

}

