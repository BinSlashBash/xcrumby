/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.app.Activity
 *  android.app.AlarmManager
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.FragmentManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.net.Uri
 *  android.os.Environment
 *  android.preference.PreferenceManager
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewPropertyAnimator
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty.main_menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.CrumbySettings;
import com.crumby.lib.download.DownloadListAdapter;
import com.crumby.lib.download.ImageDownloadListener;
import com.crumby.lib.download.ImageDownloadManager;
import com.crumby.lib.fragment.tertiary.TagBlacklist;
import com.crumby.lib.widget.firstparty.main_menu.DownloadIndicator;
import com.crumby.lib.widget.firstparty.main_menu.DownloadMenuItem;
import com.crumby.lib.widget.firstparty.main_menu.SettingsCheckBox;
import com.crumby.lib.widget.firstparty.omnibar.FormTabButton;
import com.crumby.lib.widget.firstparty.omnibar.TabManager;
import com.crumby.lib.widget.firstparty.omnibar.TabSwitchListener;
import com.uservoice.uservoicesdk.UserVoice;
import net.rdrei.android.dirchooser.DirectoryChooserFragment;

public class MainMenu
extends LinearLayout
implements TabSwitchListener {
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

    public MainMenu(Context context) {
        super(context);
    }

    public MainMenu(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MainMenu(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void openSaveImageDirectory() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setType("image/*");
        Analytics.INSTANCE.newEvent(AnalyticsCategories.MAIN_MENU, "open photo app", null);
        this.getContext().startActivity(intent);
    }

    private void toggleMenu() {
        if (!this.showing) {
            this.show();
            return;
        }
        this.hide();
    }

    public void changeDownloadDirectory() {
        Activity activity = (Activity)this.getContext();
        DirectoryChooserFragment.newInstance("DialogSample", PreferenceManager.getDefaultSharedPreferences((Context)activity).getString("crumbyDownloadDirectory", Environment.getExternalStorageDirectory().getPath())).show(activity.getFragmentManager(), null);
    }

    public void hide() {
        if (!this.showing || this.animating) {
            return;
        }
        this.showing = false;
        this.animating = true;
        this.browserWindow.animate().xBy((float)this.getMeasuredWidth()).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator) {
                MainMenu.this.setVisibility(8);
                MainMenu.this.menuModal.setVisibility(8);
                MainMenu.this.animating = false;
            }
        });
        this.menuButtonContainer.setPressed(false);
        this.downloadList.smoothScrollBy(0, 0);
        for (int i2 = this.downloadList.getChildCount() - 1; i2 >= 0; --i2) {
            ((DownloadMenuItem)this.downloadList.getChildAt(i2)).cancel();
        }
        ((DownloadListAdapter)this.downloadList.getAdapter()).pause();
    }

    public void hideButton() {
        this.menuButtonContainer.setVisibility(8);
    }

    public void initialize(final Activity activity, View view, View view2, View view3) {
        this.inflater = activity.getLayoutInflater();
        this.browserWindow = view;
        this.menuButtonContainer = view2.findViewById(2131493056);
        this.menuModal = view3;
        view3.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                MainMenu.this.hide();
                return true;
            }
        });
        this.menuButtonContainer.findViewById(2131493057).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainMenu.this.toggleMenu();
            }
        });
        this.downloadIndicator = (DownloadIndicator)view2.findViewById(2131493058);
        this.downloadList = (ListView)this.findViewById(2131493053);
        this.downloadList.setAdapter((ListAdapter)new DownloadListAdapter(this.downloadList, this.inflater, (TextView)this.findViewById(2131493052)));
        this.downloadList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> object, View view, int n2, long l2) {
                object = (DownloadMenuItem)view;
                if (object.canBeRestarted()) {
                    object.restartDownload();
                    return;
                }
                if (!object.hasDownloaded()) {
                    object.stopDownload();
                    return;
                }
                Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "open", object.getURI());
                view = new Intent();
                view.setAction("android.intent.action.VIEW");
                view.setDataAndType(Uri.parse((String)("file://" + object.getURI())), "image/*");
                activity.startActivity((Intent)view);
            }
        });
        this.clearDownloads = (Button)this.findViewById(2131493054);
        this.clearDownloads.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ImageDownloadManager.INSTANCE.terminateDownloads();
            }
        });
        ((Button)this.findViewById(2131493102)).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newNavigationEvent("uservoice", "help main menu");
                UserVoice.launchUserVoice(MainMenu.this.getContext());
            }
        });
        this.findViewById(2131493111).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                view = new Intent(MainMenu.this.getContext(), (Class)GalleryViewer.class);
                view = PendingIntent.getActivity((Context)MainMenu.this.getContext(), (int)123456, (Intent)view, (int)268435456);
                ((AlarmManager)MainMenu.this.getContext().getSystemService("alarm")).set(1, System.currentTimeMillis() + 400, (PendingIntent)view);
                System.exit(0);
            }
        });
        this.downloadTabButton = (FormTabButton)this.findViewById(2131493049);
        this.downloadTabButton.setTag((Object)"downloads");
        this.settingsTabButton = (FormTabButton)this.findViewById(2131493050);
        this.settingsTabButton.setTag((Object)"settings");
        this.downloadTabButton.setView(this.findViewById(2131493051));
        this.settingsTabButton.setView(this.findViewById(2131493101));
        this.tabs = new TabManager(this, this.downloadTabButton, this.settingsTabButton);
        this.tabs.onClick((View)this.downloadTabButton);
        this.findViewById(2131493055).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                boolean bl2 = false;
                view = PreferenceManager.getDefaultSharedPreferences((Context)MainMenu.this.getContext());
                if (!view.getBoolean("crumbyClickedDownloadDirectory", false)) {
                    bl2 = true;
                }
                if (bl2) {
                    view.edit().putBoolean("crumbyClickedDownloadDirectory", true).commit();
                    view = new AlertDialog.Builder((Context)activity);
                    view.setMessage((CharSequence)"").setTitle((CharSequence)"Warning!");
                    view.setPositiveButton((CharSequence)"Ok", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n2) {
                        }
                    });
                    view.setMessage((CharSequence)"To see the images you have saved, you will need a proper image browser or a file explorer. If you do not have one, this feature will NOT work!");
                    view = view.create();
                    view.setOnDismissListener(new DialogInterface.OnDismissListener(){

                        public void onDismiss(DialogInterface dialogInterface) {
                            MainMenu.this.openSaveImageDirectory();
                        }
                    });
                    view.show();
                    return;
                }
                MainMenu.this.openSaveImageDirectory();
            }

        });
        this.findViewById(2131493105).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainMenu.this.changeDownloadDirectory();
            }
        });
        this.findViewById(2131493103).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                new CrumbySettings().show(((Activity)MainMenu.this.getContext()).getFragmentManager(), "dialog");
            }
        });
        this.findViewById(2131493104).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                new TagBlacklist().show(((Activity)MainMenu.this.getContext()).getFragmentManager(), "dialog");
            }
        });
        this.clearDownloads.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ImageDownloadManager.INSTANCE.terminateDownloads();
            }
        });
        ((SettingsCheckBox)this.findViewById(2131493107)).setPreferenceKey("crumbyPersistGridColumns", true);
        ((SettingsCheckBox)this.findViewById(2131493106)).setPreferenceKey("crumbyShowDownloadsOnSave", true);
        ((SettingsCheckBox)this.findViewById(2131493108)).setPreferenceKey("crumbyUseBreadcrumbPathForDownload", false);
        ((SettingsCheckBox)this.findViewById(2131493110)).setPreferenceKey("crumbyUseBreadcrumbPathForDownload", false);
        ImageDownloadManager.INSTANCE.addDefaultListener(this.downloadIndicator);
        ImageDownloadManager.INSTANCE.addDefaultListener((ImageDownloadListener)this.downloadList.getAdapter());
    }

    public boolean isShowing() {
        return this.showing;
    }

    @Override
    public void onTabSwitch(FormTabButton formTabButton) {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.MAIN_MENU, "tab", (String)formTabButton.getTag());
    }

    public void show() {
        if (this.showing || this.animating) {
            return;
        }
        ((DownloadListAdapter)this.downloadList.getAdapter()).unpause();
        this.showing = true;
        this.setVisibility(0);
        this.menuModal.setVisibility(0);
        this.animating = true;
        this.browserWindow.animate().xBy((float)(- this.getMeasuredWidth())).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator) {
                MainMenu.this.animating = false;
            }
        });
        this.menuButtonContainer.setPressed(true);
    }

    public void showButton() {
        this.menuButtonContainer.setVisibility(0);
    }

    public void showDownloads() {
        this.downloadList.setSelection(0);
        this.tabs.onClick((View)this.downloadTabButton);
        if (!PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).getBoolean("crumbyShowDownloadsOnSave", true)) {
            return;
        }
        this.show();
    }

    public void toggleSettings() {
        this.toggleMenu();
        this.tabs.onClick((View)this.settingsTabButton);
    }

}

