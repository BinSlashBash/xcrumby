package net.rdrei.android.dirchooser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import net.rdrei.android.dirchooser.DirectoryChooserFragment.OnFragmentInteractionListener;

public class DirectoryChooserActivity extends Activity implements OnFragmentInteractionListener {
    public static final String EXTRA_INITIAL_DIRECTORY = "initial_directory";
    public static final String EXTRA_NEW_DIR_NAME = "directory_name";
    public static final int RESULT_CODE_DIR_SELECTED = 1;
    public static final String RESULT_SELECTED_DIR = "selected_dir";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        setContentView(C0676R.layout.directory_chooser_activity);
        String newDirName = getIntent().getStringExtra(EXTRA_NEW_DIR_NAME);
        String initialDir = getIntent().getStringExtra(EXTRA_INITIAL_DIRECTORY);
        if (newDirName == null) {
            throw new IllegalArgumentException("You must provide EXTRA_NEW_DIR_NAME when starting the DirectoryChooserActivity.");
        } else if (savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().add(C0676R.id.main, DirectoryChooserFragment.newInstance(newDirName, initialDir)).commit();
        }
    }

    void setupActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        setResult(0);
        finish();
        return true;
    }

    public void onSelectDirectory(@NonNull String path) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_SELECTED_DIR, path);
        setResult(RESULT_CODE_DIR_SELECTED, intent);
        finish();
    }

    public void onCancelChooser() {
        setResult(0);
        finish();
    }
}
