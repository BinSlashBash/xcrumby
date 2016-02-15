package net.rdrei.android.dirchooser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DirectoryChooserFragment
  extends DialogFragment
{
  private static final String ARG_INITIAL_DIRECTORY = "INITIAL_DIRECTORY";
  private static final String ARG_NEW_DIRECTORY_NAME = "NEW_DIRECTORY_NAME";
  public static final String KEY_CURRENT_DIRECTORY = "CURRENT_DIRECTORY";
  private static final String TAG;
  private Button mBtnCancel;
  private Button mBtnConfirm;
  private ImageButton mBtnCreateFolder;
  private ImageButton mBtnNavUp;
  private FileObserver mFileObserver;
  private ArrayList<String> mFilenames;
  private File[] mFilesInDir;
  private String mInitialDirectory;
  private ListView mListDirectories;
  private ArrayAdapter<String> mListDirectoriesAdapter;
  private OnFragmentInteractionListener mListener;
  private String mNewDirectoryName;
  private File mSelectedDir;
  private TextView mTxtvSelectedFolder;
  
  static
  {
    if (!DirectoryChooserFragment.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      TAG = DirectoryChooserFragment.class.getSimpleName();
      return;
    }
  }
  
  private void adjustResourceLightness()
  {
    int j = 16777215;
    Object localObject = getActivity().getTheme();
    int i = j;
    if (localObject != null)
    {
      localObject = ((Resources.Theme)localObject).obtainStyledAttributes(new int[] { 16842801 });
      i = j;
      if (localObject != null)
      {
        i = ((TypedArray)localObject).getColor(0, 16777215);
        ((TypedArray)localObject).recycle();
      }
    }
    if ((i != 16777215) && (0.21D * Color.red(i) + 0.72D * Color.green(i) + 0.07D * Color.blue(i) < 128.0D))
    {
      this.mBtnNavUp.setImageResource(R.drawable.navigation_up_light);
      this.mBtnCreateFolder.setImageResource(R.drawable.ic_action_create_light);
    }
  }
  
  private void changeDirectory(File paramFile)
  {
    if (paramFile == null) {
      debug("Could not change folder: dir was null", new Object[0]);
    }
    for (;;)
    {
      refreshButtonState();
      return;
      if (!paramFile.isDirectory())
      {
        debug("Could not change folder: dir is no directory", new Object[0]);
      }
      else
      {
        File[] arrayOfFile = paramFile.listFiles();
        if (arrayOfFile != null)
        {
          int i = 0;
          int m = arrayOfFile.length;
          int j = 0;
          while (j < m)
          {
            k = i;
            if (arrayOfFile[j].isDirectory()) {
              k = i + 1;
            }
            j += 1;
            i = k;
          }
          this.mFilesInDir = new File[i];
          this.mFilenames.clear();
          int k = 0;
          j = 0;
          while (k < i)
          {
            m = k;
            if (arrayOfFile[j].isDirectory())
            {
              this.mFilesInDir[k] = arrayOfFile[j];
              this.mFilenames.add(arrayOfFile[j].getName());
              m = k + 1;
            }
            j += 1;
            k = m;
          }
          Arrays.sort(this.mFilesInDir);
          Collections.sort(this.mFilenames);
          this.mSelectedDir = paramFile;
          this.mTxtvSelectedFolder.setText(paramFile.getAbsolutePath());
          this.mListDirectoriesAdapter.notifyDataSetChanged();
          this.mFileObserver = createFileObserver(paramFile.getAbsolutePath());
          this.mFileObserver.startWatching();
          debug("Changed directory to %s", new Object[] { paramFile.getAbsolutePath() });
        }
        else
        {
          debug("Could not change folder: contents of dir were null", new Object[0]);
        }
      }
    }
  }
  
  private FileObserver createFileObserver(String paramString)
  {
    new FileObserver(paramString, 960)
    {
      public void onEvent(int paramAnonymousInt, String paramAnonymousString)
      {
        DirectoryChooserFragment.this.debug("FileObserver received event %d", new Object[] { Integer.valueOf(paramAnonymousInt) });
        paramAnonymousString = DirectoryChooserFragment.this.getActivity();
        if (paramAnonymousString != null) {
          paramAnonymousString.runOnUiThread(new Runnable()
          {
            public void run()
            {
              DirectoryChooserFragment.this.refreshDirectory();
            }
          });
        }
      }
    };
  }
  
  private int createFolder()
  {
    if ((this.mNewDirectoryName != null) && (this.mSelectedDir != null) && (this.mSelectedDir.canWrite()))
    {
      File localFile = new File(this.mSelectedDir, this.mNewDirectoryName);
      if (!localFile.exists())
      {
        if (localFile.mkdir()) {
          return R.string.create_folder_success;
        }
        return R.string.create_folder_error;
      }
      return R.string.create_folder_error_already_exists;
    }
    if ((this.mSelectedDir != null) && (!this.mSelectedDir.canWrite())) {
      return R.string.create_folder_error_no_write_access;
    }
    return R.string.create_folder_error;
  }
  
  private void debug(String paramString, Object... paramVarArgs)
  {
    Log.d(TAG, String.format(paramString, paramVarArgs));
  }
  
  private boolean isValidFile(File paramFile)
  {
    return (paramFile != null) && (paramFile.isDirectory()) && (paramFile.canRead()) && (paramFile.canWrite());
  }
  
  public static DirectoryChooserFragment newInstance(@NonNull String paramString1, @Nullable String paramString2)
  {
    DirectoryChooserFragment localDirectoryChooserFragment = new DirectoryChooserFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("NEW_DIRECTORY_NAME", paramString1);
    localBundle.putString("INITIAL_DIRECTORY", paramString2);
    localDirectoryChooserFragment.setArguments(localBundle);
    return localDirectoryChooserFragment;
  }
  
  private void openNewFolderDialog()
  {
    new AlertDialog.Builder(getActivity()).setTitle(R.string.create_folder_label).setMessage(String.format(getString(R.string.create_folder_msg), new Object[] { this.mNewDirectoryName })).setNegativeButton(R.string.cancel_label, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    }).setPositiveButton(R.string.confirm_label, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
        paramAnonymousInt = DirectoryChooserFragment.this.createFolder();
        Toast.makeText(DirectoryChooserFragment.this.getActivity(), paramAnonymousInt, 0).show();
      }
    }).create().show();
  }
  
  private void refreshButtonState()
  {
    if ((getActivity() != null) && (this.mSelectedDir != null))
    {
      this.mBtnConfirm.setEnabled(isValidFile(this.mSelectedDir));
      getActivity().invalidateOptionsMenu();
    }
  }
  
  private void refreshDirectory()
  {
    if (this.mSelectedDir != null) {
      changeDirectory(this.mSelectedDir);
    }
  }
  
  private void returnSelectedFolder()
  {
    if (this.mSelectedDir != null)
    {
      debug("Returning %s as result", new Object[] { this.mSelectedDir.getAbsolutePath() });
      this.mListener.onSelectDirectory(this.mSelectedDir.getAbsolutePath());
      getDialog().dismiss();
      return;
    }
    this.mListener.onCancelChooser();
    getDialog().cancel();
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    try
    {
      this.mListener = ((OnFragmentInteractionListener)paramActivity);
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new ClassCastException(paramActivity.toString() + " must implement OnFragmentInteractionListener");
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (getArguments() == null) {
      throw new IllegalArgumentException("You must create DirectoryChooserFragment via newInstance().");
    }
    this.mNewDirectoryName = getArguments().getString("NEW_DIRECTORY_NAME");
    this.mInitialDirectory = getArguments().getString("INITIAL_DIRECTORY");
    if (paramBundle != null) {
      this.mInitialDirectory = paramBundle.getString("CURRENT_DIRECTORY");
    }
    if (getShowsDialog())
    {
      setStyle(1, 0);
      return;
    }
    setHasOptionsMenu(true);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenuInflater.inflate(R.menu.directory_chooser, paramMenu);
    paramMenu = paramMenu.findItem(R.id.new_folder_item);
    if (paramMenu == null) {
      return;
    }
    if ((isValidFile(this.mSelectedDir)) && (this.mNewDirectoryName != null)) {}
    for (boolean bool = true;; bool = false)
    {
      paramMenu.setVisible(bool);
      return;
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    assert (getActivity() != null);
    paramViewGroup = paramLayoutInflater.inflate(R.layout.directory_chooser, paramViewGroup, false);
    this.mBtnConfirm = ((Button)paramViewGroup.findViewById(R.id.btnConfirm));
    this.mBtnCancel = ((Button)paramViewGroup.findViewById(R.id.btnCancel));
    this.mBtnNavUp = ((ImageButton)paramViewGroup.findViewById(R.id.btnNavUp));
    this.mBtnCreateFolder = ((ImageButton)paramViewGroup.findViewById(R.id.btnCreateFolder));
    this.mTxtvSelectedFolder = ((TextView)paramViewGroup.findViewById(R.id.txtvSelectedFolder));
    this.mListDirectories = ((ListView)paramViewGroup.findViewById(R.id.directoryList));
    this.mBtnConfirm.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (DirectoryChooserFragment.this.isValidFile(DirectoryChooserFragment.this.mSelectedDir)) {
          DirectoryChooserFragment.this.returnSelectedFolder();
        }
      }
    });
    this.mBtnCancel.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DirectoryChooserFragment.this.mListener.onCancelChooser();
        DirectoryChooserFragment.this.getDialog().cancel();
      }
    });
    this.mListDirectories.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        DirectoryChooserFragment.this.debug("Selected index: %d", new Object[] { Integer.valueOf(paramAnonymousInt) });
        if ((DirectoryChooserFragment.this.mFilesInDir != null) && (paramAnonymousInt >= 0) && (paramAnonymousInt < DirectoryChooserFragment.this.mFilesInDir.length)) {
          DirectoryChooserFragment.this.changeDirectory(DirectoryChooserFragment.this.mFilesInDir[paramAnonymousInt]);
        }
      }
    });
    this.mBtnNavUp.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (DirectoryChooserFragment.this.mSelectedDir != null)
        {
          paramAnonymousView = DirectoryChooserFragment.this.mSelectedDir.getParentFile();
          if (paramAnonymousView != null) {
            DirectoryChooserFragment.this.changeDirectory(paramAnonymousView);
          }
        }
      }
    });
    this.mBtnCreateFolder.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DirectoryChooserFragment.this.openNewFolderDialog();
      }
    });
    this.mBtnCreateFolder.setVisibility(8);
    adjustResourceLightness();
    this.mFilenames = new ArrayList();
    this.mListDirectoriesAdapter = new ArrayAdapter(getActivity(), 17367043, this.mFilenames);
    this.mListDirectories.setAdapter(this.mListDirectoriesAdapter);
    if ((this.mInitialDirectory != null) && (isValidFile(new File(this.mInitialDirectory)))) {}
    for (paramLayoutInflater = new File(this.mInitialDirectory);; paramLayoutInflater = Environment.getExternalStorageDirectory())
    {
      changeDirectory(paramLayoutInflater);
      return paramViewGroup;
    }
  }
  
  public void onDetach()
  {
    super.onDetach();
    this.mListener = null;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == R.id.new_folder_item)
    {
      openNewFolderDialog();
      return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public void onPause()
  {
    super.onPause();
    if (this.mFileObserver != null) {
      this.mFileObserver.stopWatching();
    }
  }
  
  public void onResume()
  {
    super.onResume();
    if (this.mFileObserver != null) {
      this.mFileObserver.startWatching();
    }
  }
  
  public void onSaveInstanceState(@NonNull Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mSelectedDir != null) {
      paramBundle.putString("CURRENT_DIRECTORY", this.mSelectedDir.getAbsolutePath());
    }
  }
  
  public static abstract interface OnFragmentInteractionListener
  {
    public abstract void onCancelChooser();
    
    public abstract void onSelectDirectory(@NonNull String paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/net/rdrei/android/dirchooser/DirectoryChooserFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */