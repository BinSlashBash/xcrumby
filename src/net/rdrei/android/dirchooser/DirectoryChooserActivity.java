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

public class DirectoryChooserActivity
  extends Activity
  implements DirectoryChooserFragment.OnFragmentInteractionListener
{
  public static final String EXTRA_INITIAL_DIRECTORY = "initial_directory";
  public static final String EXTRA_NEW_DIR_NAME = "directory_name";
  public static final int RESULT_CODE_DIR_SELECTED = 1;
  public static final String RESULT_SELECTED_DIR = "selected_dir";
  
  public void onCancelChooser()
  {
    setResult(0);
    finish();
  }
  
  public void onCreate(@Nullable Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setupActionBar();
    setContentView(R.layout.directory_chooser_activity);
    Object localObject = getIntent().getStringExtra("directory_name");
    String str = getIntent().getStringExtra("initial_directory");
    if (localObject == null) {
      throw new IllegalArgumentException("You must provide EXTRA_NEW_DIR_NAME when starting the DirectoryChooserActivity.");
    }
    if (paramBundle == null)
    {
      paramBundle = getFragmentManager();
      localObject = DirectoryChooserFragment.newInstance((String)localObject, str);
      paramBundle.beginTransaction().add(R.id.main, (Fragment)localObject).commit();
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 16908332)
    {
      setResult(0);
      finish();
      return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public void onSelectDirectory(@NonNull String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("selected_dir", paramString);
    setResult(1, localIntent);
    finish();
  }
  
  void setupActionBar()
  {
    ActionBar localActionBar = getActionBar();
    if (localActionBar != null) {
      localActionBar.setDisplayHomeAsUpEnabled(true);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/net/rdrei/android/dirchooser/DirectoryChooserActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */