/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package com.uservoice.uservoicesdk.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.activity.BaseListActivity;
import com.uservoice.uservoicesdk.activity.ContactActivity;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.ui.PortalAdapter;

public class PortalActivity
extends BaseListActivity
implements SearchActivity {
    public PortalAdapter getModelAdapter() {
        return (PortalAdapter)this.getListAdapter();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(R.string.uv_portal_title);
        this.getListView().setDivider(null);
        this.setListAdapter((ListAdapter)new PortalAdapter(this));
        this.getListView().setOnItemClickListener((AdapterView.OnItemClickListener)this.getModelAdapter());
        Babayaga.track(Babayaga.Event.VIEW_KB);
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(R.menu.uv_portal, menu2);
        this.setupScopedSearch(menu2);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int n2, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.uv_action_contact) {
            this.startActivity(new Intent((Context)this, (Class)ContactActivity.class));
            return true;
        }
        return super.onMenuItemSelected(n2, menuItem);
    }
}

