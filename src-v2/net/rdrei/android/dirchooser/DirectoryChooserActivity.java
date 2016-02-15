/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.MenuItem
 */
package net.rdrei.android.dirchooser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import net.rdrei.android.dirchooser.DirectoryChooserFragment;
import net.rdrei.android.dirchooser.R;

public class DirectoryChooserActivity
extends Activity
implements DirectoryChooserFragment.OnFragmentInteractionListener {
    public static final String EXTRA_INITIAL_DIRECTORY = "initial_directory";
    public static final String EXTRA_NEW_DIR_NAME = "directory_name";
    public static final int RESULT_CODE_DIR_SELECTED = 1;
    public static final String RESULT_SELECTED_DIR = "selected_dir";

    @Override
    public void onCancelChooser() {
        this.setResult(0);
        this.finish();
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.setupActionBar();
        this.setContentView(R.layout.directory_chooser_activity);
        Object object = this.getIntent().getStringExtra("directory_name");
        String string2 = this.getIntent().getStringExtra("initial_directory");
        if (object == null) {
            throw new IllegalArgumentException("You must provide EXTRA_NEW_DIR_NAME when starting the DirectoryChooserActivity.");
        }
        if (bundle == null) {
            bundle = this.getFragmentManager();
            object = DirectoryChooserFragment.newInstance((String)object, string2);
            bundle.beginTransaction().add(R.id.main, (Fragment)object).commit();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.setResult(0);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSelectDirectory(@NonNull String string2) {
        Intent intent = new Intent();
        intent.putExtra("selected_dir", string2);
        this.setResult(1, intent);
        this.finish();
    }

    void setupActionBar() {
        ActionBar actionBar = this.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}

