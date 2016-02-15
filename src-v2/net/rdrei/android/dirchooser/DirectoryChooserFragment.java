/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Color
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.FileObserver
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.ImageButton
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  android.widget.Toast
 */
package net.rdrei.android.dirchooser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.rdrei.android.dirchooser.R;

public class DirectoryChooserFragment
extends DialogFragment {
    static final /* synthetic */ boolean $assertionsDisabled;
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

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !DirectoryChooserFragment.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
        TAG = DirectoryChooserFragment.class.getSimpleName();
    }

    private void adjustResourceLightness() {
        int n2 = 16777215;
        Resources.Theme theme = this.getActivity().getTheme();
        int n3 = n2;
        if (theme != null) {
            theme = theme.obtainStyledAttributes(new int[]{16842801});
            n3 = n2;
            if (theme != null) {
                n3 = theme.getColor(0, 16777215);
                theme.recycle();
            }
        }
        if (n3 != 16777215 && 0.21 * (double)Color.red((int)n3) + 0.72 * (double)Color.green((int)n3) + 0.07 * (double)Color.blue((int)n3) < 128.0) {
            this.mBtnNavUp.setImageResource(R.drawable.navigation_up_light);
            this.mBtnCreateFolder.setImageResource(R.drawable.ic_action_create_light);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void changeDirectory(File file) {
        if (file == null) {
            this.debug("Could not change folder: dir was null", new Object[0]);
        } else if (!file.isDirectory()) {
            this.debug("Could not change folder: dir is no directory", new Object[0]);
        } else {
            File[] arrfile = file.listFiles();
            if (arrfile != null) {
                int n2;
                int n3;
                int n4 = 0;
                int n5 = arrfile.length;
                for (n3 = 0; n3 < n5; ++n3) {
                    n2 = n4;
                    if (arrfile[n3].isDirectory()) {
                        n2 = n4 + 1;
                    }
                    n4 = n2;
                }
                this.mFilesInDir = new File[n4];
                this.mFilenames.clear();
                n2 = 0;
                n3 = 0;
                while (n2 < n4) {
                    n5 = n2;
                    if (arrfile[n3].isDirectory()) {
                        this.mFilesInDir[n2] = arrfile[n3];
                        this.mFilenames.add(arrfile[n3].getName());
                        n5 = n2 + 1;
                    }
                    ++n3;
                    n2 = n5;
                }
                Arrays.sort(this.mFilesInDir);
                Collections.sort(this.mFilenames);
                this.mSelectedDir = file;
                this.mTxtvSelectedFolder.setText((CharSequence)file.getAbsolutePath());
                this.mListDirectoriesAdapter.notifyDataSetChanged();
                this.mFileObserver = this.createFileObserver(file.getAbsolutePath());
                this.mFileObserver.startWatching();
                this.debug("Changed directory to %s", file.getAbsolutePath());
            } else {
                this.debug("Could not change folder: contents of dir were null", new Object[0]);
            }
        }
        this.refreshButtonState();
    }

    private FileObserver createFileObserver(String string2) {
        return new FileObserver(string2, 960){

            public void onEvent(int n2, String string2) {
                DirectoryChooserFragment.this.debug("FileObserver received event %d", new Object[]{n2});
                string2 = DirectoryChooserFragment.this.getActivity();
                if (string2 != null) {
                    string2.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            DirectoryChooserFragment.this.refreshDirectory();
                        }
                    });
                }
            }

        };
    }

    private int createFolder() {
        if (this.mNewDirectoryName != null && this.mSelectedDir != null && this.mSelectedDir.canWrite()) {
            File file = new File(this.mSelectedDir, this.mNewDirectoryName);
            if (!file.exists()) {
                if (file.mkdir()) {
                    return R.string.create_folder_success;
                }
                return R.string.create_folder_error;
            }
            return R.string.create_folder_error_already_exists;
        }
        if (this.mSelectedDir != null && !this.mSelectedDir.canWrite()) {
            return R.string.create_folder_error_no_write_access;
        }
        return R.string.create_folder_error;
    }

    private /* varargs */ void debug(String string2, Object ... arrobject) {
        Log.d((String)TAG, (String)String.format(string2, arrobject));
    }

    private boolean isValidFile(File file) {
        if (file != null && file.isDirectory() && file.canRead() && file.canWrite()) {
            return true;
        }
        return false;
    }

    public static DirectoryChooserFragment newInstance(@NonNull String string2, @Nullable String string3) {
        DirectoryChooserFragment directoryChooserFragment = new DirectoryChooserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("NEW_DIRECTORY_NAME", string2);
        bundle.putString("INITIAL_DIRECTORY", string3);
        directoryChooserFragment.setArguments(bundle);
        return directoryChooserFragment;
    }

    private void openNewFolderDialog() {
        new AlertDialog.Builder((Context)this.getActivity()).setTitle(R.string.create_folder_label).setMessage((CharSequence)String.format(this.getString(R.string.create_folder_msg), this.mNewDirectoryName)).setNegativeButton(R.string.cancel_label, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton(R.string.confirm_label, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                dialogInterface.dismiss();
                n2 = DirectoryChooserFragment.this.createFolder();
                Toast.makeText((Context)DirectoryChooserFragment.this.getActivity(), (int)n2, (int)0).show();
            }
        }).create().show();
    }

    private void refreshButtonState() {
        if (this.getActivity() != null && this.mSelectedDir != null) {
            this.mBtnConfirm.setEnabled(this.isValidFile(this.mSelectedDir));
            this.getActivity().invalidateOptionsMenu();
        }
    }

    private void refreshDirectory() {
        if (this.mSelectedDir != null) {
            this.changeDirectory(this.mSelectedDir);
        }
    }

    private void returnSelectedFolder() {
        if (this.mSelectedDir != null) {
            this.debug("Returning %s as result", this.mSelectedDir.getAbsolutePath());
            this.mListener.onSelectDirectory(this.mSelectedDir.getAbsolutePath());
            this.getDialog().dismiss();
            return;
        }
        this.mListener.onCancelChooser();
        this.getDialog().cancel();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnFragmentInteractionListener)activity;
            return;
        }
        catch (ClassCastException var2_2) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.getArguments() == null) {
            throw new IllegalArgumentException("You must create DirectoryChooserFragment via newInstance().");
        }
        this.mNewDirectoryName = this.getArguments().getString("NEW_DIRECTORY_NAME");
        this.mInitialDirectory = this.getArguments().getString("INITIAL_DIRECTORY");
        if (bundle != null) {
            this.mInitialDirectory = bundle.getString("CURRENT_DIRECTORY");
        }
        if (this.getShowsDialog()) {
            this.setStyle(1, 0);
            return;
        }
        this.setHasOptionsMenu(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.directory_chooser, menu2);
        menu2 = menu2.findItem(R.id.new_folder_item);
        if (menu2 == null) {
            return;
        }
        boolean bl2 = this.isValidFile(this.mSelectedDir) && this.mNewDirectoryName != null;
        menu2.setVisible(bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public View onCreateView(LayoutInflater object, ViewGroup viewGroup, Bundle bundle) {
        if (!$assertionsDisabled && this.getActivity() == null) {
            throw new AssertionError();
        }
        viewGroup = object.inflate(R.layout.directory_chooser, viewGroup, false);
        this.mBtnConfirm = (Button)viewGroup.findViewById(R.id.btnConfirm);
        this.mBtnCancel = (Button)viewGroup.findViewById(R.id.btnCancel);
        this.mBtnNavUp = (ImageButton)viewGroup.findViewById(R.id.btnNavUp);
        this.mBtnCreateFolder = (ImageButton)viewGroup.findViewById(R.id.btnCreateFolder);
        this.mTxtvSelectedFolder = (TextView)viewGroup.findViewById(R.id.txtvSelectedFolder);
        this.mListDirectories = (ListView)viewGroup.findViewById(R.id.directoryList);
        this.mBtnConfirm.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (DirectoryChooserFragment.this.isValidFile(DirectoryChooserFragment.this.mSelectedDir)) {
                    DirectoryChooserFragment.this.returnSelectedFolder();
                }
            }
        });
        this.mBtnCancel.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                DirectoryChooserFragment.this.mListener.onCancelChooser();
                DirectoryChooserFragment.this.getDialog().cancel();
            }
        });
        this.mListDirectories.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
                DirectoryChooserFragment.this.debug("Selected index: %d", new Object[]{n2});
                if (DirectoryChooserFragment.this.mFilesInDir != null && n2 >= 0 && n2 < DirectoryChooserFragment.this.mFilesInDir.length) {
                    DirectoryChooserFragment.this.changeDirectory(DirectoryChooserFragment.this.mFilesInDir[n2]);
                }
            }
        });
        this.mBtnNavUp.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (DirectoryChooserFragment.this.mSelectedDir != null && (object = DirectoryChooserFragment.this.mSelectedDir.getParentFile()) != null) {
                    DirectoryChooserFragment.this.changeDirectory((File)object);
                }
            }
        });
        this.mBtnCreateFolder.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                DirectoryChooserFragment.this.openNewFolderDialog();
            }
        });
        this.mBtnCreateFolder.setVisibility(8);
        this.adjustResourceLightness();
        this.mFilenames = new ArrayList();
        this.mListDirectoriesAdapter = new ArrayAdapter((Context)this.getActivity(), 17367043, this.mFilenames);
        this.mListDirectories.setAdapter(this.mListDirectoriesAdapter);
        object = this.mInitialDirectory != null && this.isValidFile(new File(this.mInitialDirectory)) ? new File(this.mInitialDirectory) : Environment.getExternalStorageDirectory();
        this.changeDirectory((File)object);
        return viewGroup;
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.new_folder_item) {
            this.openNewFolderDialog();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onPause() {
        super.onPause();
        if (this.mFileObserver != null) {
            this.mFileObserver.stopWatching();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.mFileObserver != null) {
            this.mFileObserver.startWatching();
        }
    }

    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mSelectedDir != null) {
            bundle.putString("CURRENT_DIRECTORY", this.mSelectedDir.getAbsolutePath());
        }
    }

    public static interface OnFragmentInteractionListener {
        public void onCancelChooser();

        public void onSelectDirectory(@NonNull String var1);
    }

}

