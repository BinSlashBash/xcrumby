package net.rdrei.android.dirchooser;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
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

public class DirectoryChooserFragment extends DialogFragment {
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

    /* renamed from: net.rdrei.android.dirchooser.DirectoryChooserFragment.1 */
    class C06671 implements OnClickListener {
        C06671() {
        }

        public void onClick(View v) {
            if (DirectoryChooserFragment.this.isValidFile(DirectoryChooserFragment.this.mSelectedDir)) {
                DirectoryChooserFragment.this.returnSelectedFolder();
            }
        }
    }

    /* renamed from: net.rdrei.android.dirchooser.DirectoryChooserFragment.2 */
    class C06682 implements OnClickListener {
        C06682() {
        }

        public void onClick(View v) {
            DirectoryChooserFragment.this.mListener.onCancelChooser();
            DirectoryChooserFragment.this.getDialog().cancel();
        }
    }

    /* renamed from: net.rdrei.android.dirchooser.DirectoryChooserFragment.3 */
    class C06693 implements OnItemClickListener {
        C06693() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            DirectoryChooserFragment.this.debug("Selected index: %d", Integer.valueOf(position));
            if (DirectoryChooserFragment.this.mFilesInDir != null && position >= 0 && position < DirectoryChooserFragment.this.mFilesInDir.length) {
                DirectoryChooserFragment.this.changeDirectory(DirectoryChooserFragment.this.mFilesInDir[position]);
            }
        }
    }

    /* renamed from: net.rdrei.android.dirchooser.DirectoryChooserFragment.4 */
    class C06704 implements OnClickListener {
        C06704() {
        }

        public void onClick(View v) {
            if (DirectoryChooserFragment.this.mSelectedDir != null) {
                File parent = DirectoryChooserFragment.this.mSelectedDir.getParentFile();
                if (parent != null) {
                    DirectoryChooserFragment.this.changeDirectory(parent);
                }
            }
        }
    }

    /* renamed from: net.rdrei.android.dirchooser.DirectoryChooserFragment.5 */
    class C06715 implements OnClickListener {
        C06715() {
        }

        public void onClick(View v) {
            DirectoryChooserFragment.this.openNewFolderDialog();
        }
    }

    /* renamed from: net.rdrei.android.dirchooser.DirectoryChooserFragment.6 */
    class C06726 implements DialogInterface.OnClickListener {
        C06726() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            Toast.makeText(DirectoryChooserFragment.this.getActivity(), DirectoryChooserFragment.this.createFolder(), 0).show();
        }
    }

    /* renamed from: net.rdrei.android.dirchooser.DirectoryChooserFragment.7 */
    class C06737 implements DialogInterface.OnClickListener {
        C06737() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    /* renamed from: net.rdrei.android.dirchooser.DirectoryChooserFragment.8 */
    class C06758 extends FileObserver {

        /* renamed from: net.rdrei.android.dirchooser.DirectoryChooserFragment.8.1 */
        class C06741 implements Runnable {
            C06741() {
            }

            public void run() {
                DirectoryChooserFragment.this.refreshDirectory();
            }
        }

        C06758(String x0, int x1) {
            super(x0, x1);
        }

        public void onEvent(int event, String path) {
            DirectoryChooserFragment.this.debug("FileObserver received event %d", Integer.valueOf(event));
            Activity activity = DirectoryChooserFragment.this.getActivity();
            if (activity != null) {
                activity.runOnUiThread(new C06741());
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onCancelChooser();

        void onSelectDirectory(@NonNull String str);
    }

    static {
        $assertionsDisabled = !DirectoryChooserFragment.class.desiredAssertionStatus() ? true : $assertionsDisabled;
        TAG = DirectoryChooserFragment.class.getSimpleName();
    }

    public static DirectoryChooserFragment newInstance(@NonNull String newDirectoryName, @Nullable String initialDirectory) {
        DirectoryChooserFragment fragment = new DirectoryChooserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NEW_DIRECTORY_NAME, newDirectoryName);
        args.putString(ARG_INITIAL_DIRECTORY, initialDirectory);
        fragment.setArguments(args);
        return fragment;
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.mSelectedDir != null) {
            outState.putString(KEY_CURRENT_DIRECTORY, this.mSelectedDir.getAbsolutePath());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            throw new IllegalArgumentException("You must create DirectoryChooserFragment via newInstance().");
        }
        this.mNewDirectoryName = getArguments().getString(ARG_NEW_DIRECTORY_NAME);
        this.mInitialDirectory = getArguments().getString(ARG_INITIAL_DIRECTORY);
        if (savedInstanceState != null) {
            this.mInitialDirectory = savedInstanceState.getString(KEY_CURRENT_DIRECTORY);
        }
        if (getShowsDialog()) {
            setStyle(1, 0);
        } else {
            setHasOptionsMenu(true);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if ($assertionsDisabled || getActivity() != null) {
            File initialDir;
            View view = inflater.inflate(C0676R.layout.directory_chooser, container, $assertionsDisabled);
            this.mBtnConfirm = (Button) view.findViewById(C0676R.id.btnConfirm);
            this.mBtnCancel = (Button) view.findViewById(C0676R.id.btnCancel);
            this.mBtnNavUp = (ImageButton) view.findViewById(C0676R.id.btnNavUp);
            this.mBtnCreateFolder = (ImageButton) view.findViewById(C0676R.id.btnCreateFolder);
            this.mTxtvSelectedFolder = (TextView) view.findViewById(C0676R.id.txtvSelectedFolder);
            this.mListDirectories = (ListView) view.findViewById(C0676R.id.directoryList);
            this.mBtnConfirm.setOnClickListener(new C06671());
            this.mBtnCancel.setOnClickListener(new C06682());
            this.mListDirectories.setOnItemClickListener(new C06693());
            this.mBtnNavUp.setOnClickListener(new C06704());
            this.mBtnCreateFolder.setOnClickListener(new C06715());
            this.mBtnCreateFolder.setVisibility(8);
            adjustResourceLightness();
            this.mFilenames = new ArrayList();
            this.mListDirectoriesAdapter = new ArrayAdapter(getActivity(), 17367043, this.mFilenames);
            this.mListDirectories.setAdapter(this.mListDirectoriesAdapter);
            if (this.mInitialDirectory == null || !isValidFile(new File(this.mInitialDirectory))) {
                initialDir = Environment.getExternalStorageDirectory();
            } else {
                initialDir = new File(this.mInitialDirectory);
            }
            changeDirectory(initialDir);
            return view;
        }
        throw new AssertionError();
    }

    private void adjustResourceLightness() {
        int color = ViewCompat.MEASURED_SIZE_MASK;
        Theme theme = getActivity().getTheme();
        if (theme != null) {
            TypedArray backgroundAttributes = theme.obtainStyledAttributes(new int[]{16842801});
            if (backgroundAttributes != null) {
                color = backgroundAttributes.getColor(0, ViewCompat.MEASURED_SIZE_MASK);
                backgroundAttributes.recycle();
            }
        }
        if (color != ViewCompat.MEASURED_SIZE_MASK && ((0.21d * ((double) Color.red(color))) + (0.72d * ((double) Color.green(color)))) + (0.07d * ((double) Color.blue(color))) < 128.0d) {
            this.mBtnNavUp.setImageResource(C0676R.drawable.navigation_up_light);
            this.mBtnCreateFolder.setImageResource(C0676R.drawable.ic_action_create_light);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(C0676R.menu.directory_chooser, menu);
        MenuItem menuItem = menu.findItem(C0676R.id.new_folder_item);
        if (menuItem != null) {
            boolean z = (!isValidFile(this.mSelectedDir) || this.mNewDirectoryName == null) ? $assertionsDisabled : true;
            menuItem.setVisible(z);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != C0676R.id.new_folder_item) {
            return super.onOptionsItemSelected(item);
        }
        openNewFolderDialog();
        return true;
    }

    private void openNewFolderDialog() {
        new Builder(getActivity()).setTitle(C0676R.string.create_folder_label).setMessage(String.format(getString(C0676R.string.create_folder_msg), new Object[]{this.mNewDirectoryName})).setNegativeButton(C0676R.string.cancel_label, new C06737()).setPositiveButton(C0676R.string.confirm_label, new C06726()).create().show();
    }

    private void debug(String message, Object... args) {
        Log.d(TAG, String.format(message, args));
    }

    private void changeDirectory(File dir) {
        if (dir == null) {
            debug("Could not change folder: dir was null", new Object[0]);
        } else if (dir.isDirectory()) {
            File[] contents = dir.listFiles();
            if (contents != null) {
                int numDirectories = 0;
                for (File f : contents) {
                    if (f.isDirectory()) {
                        numDirectories++;
                    }
                }
                this.mFilesInDir = new File[numDirectories];
                this.mFilenames.clear();
                int i = 0;
                int counter = 0;
                while (i < numDirectories) {
                    if (contents[counter].isDirectory()) {
                        this.mFilesInDir[i] = contents[counter];
                        this.mFilenames.add(contents[counter].getName());
                        i++;
                    }
                    counter++;
                }
                Arrays.sort(this.mFilesInDir);
                Collections.sort(this.mFilenames);
                this.mSelectedDir = dir;
                this.mTxtvSelectedFolder.setText(dir.getAbsolutePath());
                this.mListDirectoriesAdapter.notifyDataSetChanged();
                this.mFileObserver = createFileObserver(dir.getAbsolutePath());
                this.mFileObserver.startWatching();
                debug("Changed directory to %s", dir.getAbsolutePath());
            } else {
                debug("Could not change folder: contents of dir were null", new Object[0]);
            }
        } else {
            debug("Could not change folder: dir is no directory", new Object[0]);
        }
        refreshButtonState();
    }

    private void refreshButtonState() {
        if (getActivity() != null && this.mSelectedDir != null) {
            this.mBtnConfirm.setEnabled(isValidFile(this.mSelectedDir));
            getActivity().invalidateOptionsMenu();
        }
    }

    private void refreshDirectory() {
        if (this.mSelectedDir != null) {
            changeDirectory(this.mSelectedDir);
        }
    }

    private FileObserver createFileObserver(String path) {
        return new C06758(path, 960);
    }

    private void returnSelectedFolder() {
        if (this.mSelectedDir != null) {
            debug("Returning %s as result", this.mSelectedDir.getAbsolutePath());
            this.mListener.onSelectDirectory(this.mSelectedDir.getAbsolutePath());
            getDialog().dismiss();
            return;
        }
        this.mListener.onCancelChooser();
        getDialog().cancel();
    }

    private int createFolder() {
        if (this.mNewDirectoryName != null && this.mSelectedDir != null && this.mSelectedDir.canWrite()) {
            File newDir = new File(this.mSelectedDir, this.mNewDirectoryName);
            if (newDir.exists()) {
                return C0676R.string.create_folder_error_already_exists;
            }
            if (newDir.mkdir()) {
                return C0676R.string.create_folder_success;
            }
            return C0676R.string.create_folder_error;
        } else if (this.mSelectedDir == null || this.mSelectedDir.canWrite()) {
            return C0676R.string.create_folder_error;
        } else {
            return C0676R.string.create_folder_error_no_write_access;
        }
    }

    private boolean isValidFile(File file) {
        return (file != null && file.isDirectory() && file.canRead() && file.canWrite()) ? true : $assertionsDisabled;
    }
}
