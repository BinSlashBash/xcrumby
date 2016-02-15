/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.GridView
 *  android.widget.ListAdapter
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.router.IndexSetting;
import java.util.List;

public class ChooseYourWebsites
extends GridView {
    public ChooseYourWebsites(Context context) {
        super(context);
    }

    public ChooseYourWebsites(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ChooseYourWebsites(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setAdapter((ListAdapter)new BaseAdapter(FragmentRouter.INSTANCE.getAllIndexSettings()){
            final /* synthetic */ List val$indexSettingList;

            public int getCount() {
                return this.val$indexSettingList.size();
            }

            public Object getItem(int n2) {
                return null;
            }

            public long getItemId(int n2) {
                return 0;
            }

            public View getView(int n2, View view, ViewGroup viewGroup) {
                if (view == null) {
                    View.inflate((Context)ChooseYourWebsites.this.getContext(), (int)2130903048, (ViewGroup)null);
                }
                return null;
            }
        });
    }

}

