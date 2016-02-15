package com.uservoice.uservoicesdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.ui.PortalAdapter;

public class PortalActivity extends BaseListActivity implements SearchActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(C0621R.string.uv_portal_title);
        getListView().setDivider(null);
        setListAdapter(new PortalAdapter(this));
        getListView().setOnItemClickListener(getModelAdapter());
        Babayaga.track(Event.VIEW_KB);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0621R.menu.uv_portal, menu);
        setupScopedSearch(menu);
        return true;
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() != C0621R.id.uv_action_contact) {
            return super.onMenuItemSelected(featureId, item);
        }
        startActivity(new Intent(this, ContactActivity.class));
        return true;
    }

    public PortalAdapter getModelAdapter() {
        return (PortalAdapter) getListAdapter();
    }
}
