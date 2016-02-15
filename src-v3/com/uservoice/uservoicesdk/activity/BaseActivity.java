package com.uservoice.uservoicesdk.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ViewFlipper;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.ui.MixedSearchAdapter;
import com.uservoice.uservoicesdk.ui.PortalAdapter;
import com.uservoice.uservoicesdk.ui.SearchAdapter;
import com.uservoice.uservoicesdk.ui.SearchExpandListener;
import com.uservoice.uservoicesdk.ui.SearchQueryListener;

public class BaseActivity extends FragmentActivity {
    protected Tab allTab;
    protected Tab articlesTab;
    protected Tab ideasTab;
    private int originalNavigationMode;
    protected MixedSearchAdapter searchAdapter;

    /* renamed from: com.uservoice.uservoicesdk.activity.BaseActivity.1 */
    class C06251 implements TabListener {
        C06251() {
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            BaseActivity.this.searchAdapter.setScope(((Integer) tab.getTag()).intValue());
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
        }
    }

    public BaseActivity() {
        this.originalNavigationMode = -1;
    }

    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasActionBar()) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    public SearchAdapter<?> getSearchAdapter() {
        return this.searchAdapter;
    }

    @SuppressLint({"NewApi"})
    protected void setupScopedSearch(Menu menu) {
        if (hasActionBar()) {
            menu.findItem(C0621R.id.uv_action_search).setOnActionExpandListener(new SearchExpandListener((SearchActivity) this));
            ((SearchView) menu.findItem(C0621R.id.uv_action_search).getActionView()).setOnQueryTextListener(new SearchQueryListener((SearchActivity) this));
            this.searchAdapter = new MixedSearchAdapter(this);
            ListView searchView = new ListView(this);
            searchView.setAdapter(this.searchAdapter);
            searchView.setOnItemClickListener(this.searchAdapter);
            ((ViewFlipper) findViewById(C0621R.id.uv_view_flipper)).addView(searchView, 1);
            TabListener listener = new C06251();
            this.allTab = getActionBar().newTab().setText(getString(C0621R.string.uv_all_results_filter)).setTabListener(listener).setTag(Integer.valueOf(PortalAdapter.SCOPE_ALL));
            getActionBar().addTab(this.allTab);
            this.articlesTab = getActionBar().newTab().setText(getString(C0621R.string.uv_articles_filter)).setTabListener(listener).setTag(Integer.valueOf(PortalAdapter.SCOPE_ARTICLES));
            getActionBar().addTab(this.articlesTab);
            this.ideasTab = getActionBar().newTab().setText(getString(C0621R.string.uv_ideas_filter)).setTabListener(listener).setTag(Integer.valueOf(PortalAdapter.SCOPE_IDEAS));
            getActionBar().addTab(this.ideasTab);
            return;
        }
        menu.findItem(C0621R.id.uv_action_search).setVisible(false);
    }

    @SuppressLint({"NewApi"})
    public void updateScopedSearch(int results, int articleResults, int ideaResults) {
        if (hasActionBar()) {
            this.allTab.setText(String.format("%s (%d)", new Object[]{getString(C0621R.string.uv_all_results_filter), Integer.valueOf(results)}));
            this.articlesTab.setText(String.format("%s (%d)", new Object[]{getString(C0621R.string.uv_articles_filter), Integer.valueOf(articleResults)}));
            this.ideasTab.setText(String.format("%s (%d)", new Object[]{getString(C0621R.string.uv_ideas_filter), Integer.valueOf(ideaResults)}));
        }
    }

    @SuppressLint({"NewApi"})
    public void showSearch() {
        ((ViewFlipper) findViewById(C0621R.id.uv_view_flipper)).setDisplayedChild(1);
        if (hasActionBar()) {
            if (this.originalNavigationMode == -1) {
                this.originalNavigationMode = getActionBar().getNavigationMode();
            }
            getActionBar().setNavigationMode(2);
        }
    }

    @SuppressLint({"NewApi"})
    public void hideSearch() {
        int i = 0;
        ((ViewFlipper) findViewById(C0621R.id.uv_view_flipper)).setDisplayedChild(0);
        if (hasActionBar()) {
            ActionBar actionBar = getActionBar();
            if (this.originalNavigationMode != -1) {
                i = this.originalNavigationMode;
            }
            actionBar.setNavigationMode(i);
        }
    }

    @SuppressLint({"NewApi"})
    public boolean hasActionBar() {
        return VERSION.SDK_INT >= 11 && getActionBar() != null;
    }
}
