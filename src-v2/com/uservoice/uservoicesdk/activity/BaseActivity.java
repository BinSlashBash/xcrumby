/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.ActionBar
 *  android.app.ActionBar$Tab
 *  android.app.ActionBar$TabListener
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.SearchView
 *  android.widget.SearchView$OnQueryTextListener
 *  android.widget.ViewFlipper
 */
package com.uservoice.uservoicesdk.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ViewFlipper;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.ui.MixedSearchAdapter;
import com.uservoice.uservoicesdk.ui.PortalAdapter;
import com.uservoice.uservoicesdk.ui.SearchAdapter;
import com.uservoice.uservoicesdk.ui.SearchExpandListener;
import com.uservoice.uservoicesdk.ui.SearchQueryListener;

public class BaseActivity
extends FragmentActivity {
    protected ActionBar.Tab allTab;
    protected ActionBar.Tab articlesTab;
    protected ActionBar.Tab ideasTab;
    private int originalNavigationMode = -1;
    protected MixedSearchAdapter searchAdapter;

    public SearchAdapter<?> getSearchAdapter() {
        return this.searchAdapter;
    }

    @SuppressLint(value={"NewApi"})
    public boolean hasActionBar() {
        if (Build.VERSION.SDK_INT >= 11 && this.getActionBar() != null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"NewApi"})
    public void hideSearch() {
        int n2 = 0;
        ((ViewFlipper)this.findViewById(R.id.uv_view_flipper)).setDisplayedChild(0);
        if (this.hasActionBar()) {
            ActionBar actionBar = this.getActionBar();
            if (this.originalNavigationMode != -1) {
                n2 = this.originalNavigationMode;
            }
            actionBar.setNavigationMode(n2);
        }
    }

    @SuppressLint(value={"NewApi"})
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.hasActionBar()) {
            this.getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @SuppressLint(value={"NewApi"})
    protected void setupScopedSearch(Menu object) {
        if (this.hasActionBar()) {
            object.findItem(R.id.uv_action_search).setOnActionExpandListener((MenuItem.OnActionExpandListener)new SearchExpandListener((SearchActivity)((Object)this)));
            ((SearchView)object.findItem(R.id.uv_action_search).getActionView()).setOnQueryTextListener((SearchView.OnQueryTextListener)new SearchQueryListener((SearchActivity)((Object)this)));
            this.searchAdapter = new MixedSearchAdapter(this);
            object = new ListView((Context)this);
            object.setAdapter((ListAdapter)this.searchAdapter);
            object.setOnItemClickListener((AdapterView.OnItemClickListener)this.searchAdapter);
            ((ViewFlipper)this.findViewById(R.id.uv_view_flipper)).addView((View)object, 1);
            object = new ActionBar.TabListener(){

                public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                }

                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    BaseActivity.this.searchAdapter.setScope((Integer)tab.getTag());
                }

                public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                }
            };
            this.allTab = this.getActionBar().newTab().setText((CharSequence)this.getString(R.string.uv_all_results_filter)).setTabListener((ActionBar.TabListener)object).setTag((Object)PortalAdapter.SCOPE_ALL);
            this.getActionBar().addTab(this.allTab);
            this.articlesTab = this.getActionBar().newTab().setText((CharSequence)this.getString(R.string.uv_articles_filter)).setTabListener((ActionBar.TabListener)object).setTag((Object)PortalAdapter.SCOPE_ARTICLES);
            this.getActionBar().addTab(this.articlesTab);
            this.ideasTab = this.getActionBar().newTab().setText((CharSequence)this.getString(R.string.uv_ideas_filter)).setTabListener((ActionBar.TabListener)object).setTag((Object)PortalAdapter.SCOPE_IDEAS);
            this.getActionBar().addTab(this.ideasTab);
            return;
        }
        object.findItem(R.id.uv_action_search).setVisible(false);
    }

    @SuppressLint(value={"NewApi"})
    public void showSearch() {
        ((ViewFlipper)this.findViewById(R.id.uv_view_flipper)).setDisplayedChild(1);
        if (this.hasActionBar()) {
            if (this.originalNavigationMode == -1) {
                this.originalNavigationMode = this.getActionBar().getNavigationMode();
            }
            this.getActionBar().setNavigationMode(2);
        }
    }

    @SuppressLint(value={"NewApi"})
    public void updateScopedSearch(int n2, int n3, int n4) {
        if (this.hasActionBar()) {
            this.allTab.setText((CharSequence)String.format("%s (%d)", this.getString(R.string.uv_all_results_filter), n2));
            this.articlesTab.setText((CharSequence)String.format("%s (%d)", this.getString(R.string.uv_articles_filter), n3));
            this.ideasTab.setText((CharSequence)String.format("%s (%d)", this.getString(R.string.uv_ideas_filter), n4));
        }
    }

}

