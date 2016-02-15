package com.crumby.lib.widget.firstparty.main_menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.CrumbySettings;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.download.DownloadListAdapter;
import com.crumby.lib.download.ImageDownloadListener;
import com.crumby.lib.download.ImageDownloadManager;
import com.crumby.lib.fragment.tertiary.TagBlacklist;
import com.crumby.lib.widget.firstparty.omnibar.FormTabButton;
import com.crumby.lib.widget.firstparty.omnibar.TabManager;
import com.crumby.lib.widget.firstparty.omnibar.TabSwitchListener;
import com.google.android.gms.drive.DriveFile;
import com.uservoice.uservoicesdk.UserVoice;
import net.rdrei.android.dirchooser.DirectoryChooserFragment;

public class MainMenu extends LinearLayout implements TabSwitchListener {
    private static final String CATEGORY = "Main Menu";
    public static final String OPEN_IMAGES_IN_FULL_SCREEN_BY_DEFAULT = "crumbyUseBreadcrumbPathForDownload";
    public static final String PERSIST_GRID_COLUMNS = "crumbyPersistGridColumns";
    public static final String RUN_IN_BACKGROUND = "crumbyDoNotRunInBackground";
    public static final String SHOW_DOWNLOADS_ON_SAVE = "crumbyShowDownloadsOnSave";
    public static final String TRY_TO_OPEN_SWF = "crumbyTryToOpenSwf";
    public static final String USE_BREADCRUMB_PATH_FOR_DOWNLOAD = "crumbyUseBreadcrumbPathForDownload";
    private boolean animating;
    private View browserWindow;
    private Button clearDownloads;
    private DownloadIndicator downloadIndicator;
    private ListView downloadList;
    private FormTabButton downloadTabButton;
    private LayoutInflater inflater;
    private View menuButtonContainer;
    private View menuModal;
    private FormTabButton settingsTabButton;
    private boolean showing;
    private TabManager tabs;
    private boolean updating;

    /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.1 */
    class C01361 extends AnimatorListenerAdapter {
        C01361() {
        }

        public void onAnimationEnd(Animator animation) {
            MainMenu.this.animating = false;
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.2 */
    class C01372 extends AnimatorListenerAdapter {
        C01372() {
        }

        public void onAnimationEnd(Animator animation) {
            MainMenu.this.setVisibility(8);
            MainMenu.this.menuModal.setVisibility(8);
            MainMenu.this.animating = false;
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.3 */
    class C01383 implements OnTouchListener {
        C01383() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            MainMenu.this.hide();
            return true;
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.4 */
    class C01394 implements OnClickListener {
        C01394() {
        }

        public void onClick(View view) {
            MainMenu.this.toggleMenu();
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.5 */
    class C01405 implements OnItemClickListener {
        final /* synthetic */ Activity val$activity;

        C01405(Activity activity) {
            this.val$activity = activity;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            DownloadMenuItem item = (DownloadMenuItem) view;
            if (item.canBeRestarted()) {
                item.restartDownload();
            } else if (item.hasDownloaded()) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "open", item.getURI());
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setDataAndType(Uri.parse("file://" + item.getURI()), "image/*");
                this.val$activity.startActivity(intent);
            } else {
                item.stopDownload();
            }
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.6 */
    class C01416 implements OnClickListener {
        C01416() {
        }

        public void onClick(View v) {
            ImageDownloadManager.INSTANCE.terminateDownloads();
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.7 */
    class C01427 implements OnClickListener {
        C01427() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newNavigationEvent("uservoice", "help main menu");
            UserVoice.launchUserVoice(MainMenu.this.getContext());
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.8 */
    class C01438 implements OnClickListener {
        C01438() {
        }

        public void onClick(View v) {
            ((AlarmManager) MainMenu.this.getContext().getSystemService("alarm")).set(1, System.currentTimeMillis() + 400, PendingIntent.getActivity(MainMenu.this.getContext(), 123456, new Intent(MainMenu.this.getContext(), GalleryViewer.class), DriveFile.MODE_READ_ONLY));
            System.exit(0);
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.9 */
    class C01469 implements OnClickListener {
        final /* synthetic */ Activity val$activity;

        /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.9.1 */
        class C01441 implements DialogInterface.OnClickListener {
            C01441() {
            }

            public void onClick(DialogInterface dialog, int id) {
            }
        }

        /* renamed from: com.crumby.lib.widget.firstparty.main_menu.MainMenu.9.2 */
        class C01452 implements OnDismissListener {
            C01452() {
            }

            public void onDismiss(DialogInterface dialog) {
                MainMenu.this.openSaveImageDirectory();
            }
        }

        C01469(Activity activity) {
            this.val$activity = activity;
        }

        public void onClick(View v) {
            boolean needsToSeeAlert = false;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainMenu.this.getContext());
            if (!preferences.getBoolean("crumbyClickedDownloadDirectory", false)) {
                needsToSeeAlert = true;
            }
            if (needsToSeeAlert) {
                preferences.edit().putBoolean("crumbyClickedDownloadDirectory", true).commit();
                Builder builder = new Builder(this.val$activity);
                builder.setMessage(UnsupportedUrlFragment.DISPLAY_NAME).setTitle("Warning!");
                builder.setPositiveButton("Ok", new C01441());
                builder.setMessage("To see the images you have saved, you will need a proper image browser or a file explorer. If you do not have one, this feature will NOT work!");
                AlertDialog dialog = builder.create();
                dialog.setOnDismissListener(new C01452());
                dialog.show();
                return;
            }
            MainMenu.this.openSaveImageDirectory();
        }
    }

    public MainMenu(Context context) {
        super(context);
    }

    public MainMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showDownloads() {
        this.downloadList.setSelection(0);
        this.tabs.onClick(this.downloadTabButton);
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(SHOW_DOWNLOADS_ON_SAVE, true)) {
            show();
        }
    }

    public void show() {
        if (!this.showing && !this.animating) {
            ((DownloadListAdapter) this.downloadList.getAdapter()).unpause();
            this.showing = true;
            setVisibility(0);
            this.menuModal.setVisibility(0);
            this.animating = true;
            this.browserWindow.animate().xBy((float) (-getMeasuredWidth())).setListener(new C01361());
            this.menuButtonContainer.setPressed(true);
        }
    }

    public void hide() {
        if (this.showing && !this.animating) {
            this.showing = false;
            this.animating = true;
            this.browserWindow.animate().xBy((float) getMeasuredWidth()).setListener(new C01372());
            this.menuButtonContainer.setPressed(false);
            this.downloadList.smoothScrollBy(0, 0);
            for (int i = this.downloadList.getChildCount() - 1; i >= 0; i--) {
                ((DownloadMenuItem) this.downloadList.getChildAt(i)).cancel();
            }
            ((DownloadListAdapter) this.downloadList.getAdapter()).pause();
        }
    }

    public void hideButton() {
        this.menuButtonContainer.setVisibility(8);
    }

    public void showButton() {
        this.menuButtonContainer.setVisibility(0);
    }

    private void toggleMenu() {
        if (this.showing) {
            hide();
        } else {
            show();
        }
    }

    public void initialize(Activity activity, View browserWindow, View actionBarView, View menuModal) {
        this.inflater = activity.getLayoutInflater();
        this.browserWindow = browserWindow;
        this.menuButtonContainer = actionBarView.findViewById(C0065R.id.main_menu_button_container);
        this.menuModal = menuModal;
        menuModal.setOnTouchListener(new C01383());
        this.menuButtonContainer.findViewById(C0065R.id.main_menu_button).setOnClickListener(new C01394());
        this.downloadIndicator = (DownloadIndicator) actionBarView.findViewById(C0065R.id.download_indicator);
        this.downloadList = (ListView) findViewById(C0065R.id.menu_downloads);
        this.downloadList.setAdapter(new DownloadListAdapter(this.downloadList, this.inflater, (TextView) findViewById(C0065R.id.downloads_header)));
        this.downloadList.setOnItemClickListener(new C01405(activity));
        this.clearDownloads = (Button) findViewById(C0065R.id.clear_downloads);
        this.clearDownloads.setOnClickListener(new C01416());
        ((Button) findViewById(C0065R.id.get_help)).setOnClickListener(new C01427());
        findViewById(C0065R.id.restart_app).setOnClickListener(new C01438());
        this.downloadTabButton = (FormTabButton) findViewById(C0065R.id.download_tab_button);
        this.downloadTabButton.setTag("downloads");
        this.settingsTabButton = (FormTabButton) findViewById(C0065R.id.settings_tab_button);
        this.settingsTabButton.setTag("settings");
        this.downloadTabButton.setView(findViewById(C0065R.id.downloads_tab));
        this.settingsTabButton.setView(findViewById(C0065R.id.settings_tab));
        this.tabs = new TabManager(this, this.downloadTabButton, this.settingsTabButton);
        this.tabs.onClick(this.downloadTabButton);
        findViewById(C0065R.id.view_download_directory).setOnClickListener(new C01469(activity));
        findViewById(C0065R.id.change_download_directory).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainMenu.this.changeDownloadDirectory();
            }
        });
        findViewById(C0065R.id.change_website_settings).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new CrumbySettings().show(((Activity) MainMenu.this.getContext()).getFragmentManager(), "dialog");
            }
        });
        findViewById(C0065R.id.change_tag_blacklist).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new TagBlacklist().show(((Activity) MainMenu.this.getContext()).getFragmentManager(), "dialog");
            }
        });
        this.clearDownloads.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ImageDownloadManager.INSTANCE.terminateDownloads();
            }
        });
        ((SettingsCheckBox) findViewById(C0065R.id.setting_persist_grid_columns)).setPreferenceKey(PERSIST_GRID_COLUMNS, true);
        ((SettingsCheckBox) findViewById(C0065R.id.setting_show_downloads_on_save)).setPreferenceKey(SHOW_DOWNLOADS_ON_SAVE, true);
        ((SettingsCheckBox) findViewById(C0065R.id.setting_use_folders)).setPreferenceKey(USE_BREADCRUMB_PATH_FOR_DOWNLOAD, false);
        ((SettingsCheckBox) findViewById(C0065R.id.open_images_in_full_screen_by_default)).setPreferenceKey(USE_BREADCRUMB_PATH_FOR_DOWNLOAD, false);
        ImageDownloadManager.INSTANCE.addDefaultListener(this.downloadIndicator);
        ImageDownloadManager.INSTANCE.addDefaultListener((ImageDownloadListener) this.downloadList.getAdapter());
    }

    private void openSaveImageDirectory() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setType("image/*");
        Analytics.INSTANCE.newEvent(AnalyticsCategories.MAIN_MENU, "open photo app", null);
        getContext().startActivity(intent);
    }

    public void changeDownloadDirectory() {
        Activity activity = (Activity) getContext();
        DirectoryChooserFragment.newInstance("DialogSample", PreferenceManager.getDefaultSharedPreferences(activity).getString(GalleryViewer.CRUMBY_DOWNLOAD_DIRECTORY, Environment.getExternalStorageDirectory().getPath())).show(activity.getFragmentManager(), null);
    }

    public boolean isShowing() {
        return this.showing;
    }

    public void onTabSwitch(FormTabButton button) {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.MAIN_MENU, "tab", (String) button.getTag());
    }

    public void toggleSettings() {
        toggleMenu();
        this.tabs.onClick(this.settingsTabButton);
    }
}
