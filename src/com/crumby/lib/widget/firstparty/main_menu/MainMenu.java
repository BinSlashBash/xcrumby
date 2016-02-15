package com.crumby.lib.widget.firstparty.main_menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.crumby.lib.widget.firstparty.omnibar.FormTabButton;
import com.crumby.lib.widget.firstparty.omnibar.TabManager;
import com.crumby.lib.widget.firstparty.omnibar.TabSwitchListener;
import com.uservoice.uservoicesdk.UserVoice;
import java.io.File;
import net.rdrei.android.dirchooser.DirectoryChooserFragment;

public class MainMenu
  extends LinearLayout
  implements TabSwitchListener
{
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
  
  public MainMenu(Context paramContext)
  {
    super(paramContext);
  }
  
  public MainMenu(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public MainMenu(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void openSaveImageDirectory()
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setType("image/*");
    Analytics.INSTANCE.newEvent(AnalyticsCategories.MAIN_MENU, "open photo app", null);
    getContext().startActivity(localIntent);
  }
  
  private void toggleMenu()
  {
    if (!this.showing)
    {
      show();
      return;
    }
    hide();
  }
  
  public void changeDownloadDirectory()
  {
    Activity localActivity = (Activity)getContext();
    DirectoryChooserFragment.newInstance("DialogSample", PreferenceManager.getDefaultSharedPreferences(localActivity).getString("crumbyDownloadDirectory", Environment.getExternalStorageDirectory().getPath())).show(localActivity.getFragmentManager(), null);
  }
  
  public void hide()
  {
    if ((!this.showing) || (this.animating)) {
      return;
    }
    this.showing = false;
    this.animating = true;
    this.browserWindow.animate().xBy(getMeasuredWidth()).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        MainMenu.this.setVisibility(8);
        MainMenu.this.menuModal.setVisibility(8);
        MainMenu.access$002(MainMenu.this, false);
      }
    });
    this.menuButtonContainer.setPressed(false);
    this.downloadList.smoothScrollBy(0, 0);
    int i = this.downloadList.getChildCount() - 1;
    while (i >= 0)
    {
      ((DownloadMenuItem)this.downloadList.getChildAt(i)).cancel();
      i -= 1;
    }
    ((DownloadListAdapter)this.downloadList.getAdapter()).pause();
  }
  
  public void hideButton()
  {
    this.menuButtonContainer.setVisibility(8);
  }
  
  public void initialize(final Activity paramActivity, View paramView1, View paramView2, View paramView3)
  {
    this.inflater = paramActivity.getLayoutInflater();
    this.browserWindow = paramView1;
    this.menuButtonContainer = paramView2.findViewById(2131493056);
    this.menuModal = paramView3;
    paramView3.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        MainMenu.this.hide();
        return true;
      }
    });
    this.menuButtonContainer.findViewById(2131493057).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MainMenu.this.toggleMenu();
      }
    });
    this.downloadIndicator = ((DownloadIndicator)paramView2.findViewById(2131493058));
    this.downloadList = ((ListView)findViewById(2131493053));
    this.downloadList.setAdapter(new DownloadListAdapter(this.downloadList, this.inflater, (TextView)findViewById(2131493052)));
    this.downloadList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        paramAnonymousAdapterView = (DownloadMenuItem)paramAnonymousView;
        if (paramAnonymousAdapterView.canBeRestarted())
        {
          paramAnonymousAdapterView.restartDownload();
          return;
        }
        if (!paramAnonymousAdapterView.hasDownloaded())
        {
          paramAnonymousAdapterView.stopDownload();
          return;
        }
        Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "open", paramAnonymousAdapterView.getURI());
        paramAnonymousView = new Intent();
        paramAnonymousView.setAction("android.intent.action.VIEW");
        paramAnonymousView.setDataAndType(Uri.parse("file://" + paramAnonymousAdapterView.getURI()), "image/*");
        paramActivity.startActivity(paramAnonymousView);
      }
    });
    this.clearDownloads = ((Button)findViewById(2131493054));
    this.clearDownloads.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ImageDownloadManager.INSTANCE.terminateDownloads();
      }
    });
    ((Button)findViewById(2131493102)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newNavigationEvent("uservoice", "help main menu");
        UserVoice.launchUserVoice(MainMenu.this.getContext());
      }
    });
    findViewById(2131493111).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new Intent(MainMenu.this.getContext(), GalleryViewer.class);
        paramAnonymousView = PendingIntent.getActivity(MainMenu.this.getContext(), 123456, paramAnonymousView, 268435456);
        ((AlarmManager)MainMenu.this.getContext().getSystemService("alarm")).set(1, System.currentTimeMillis() + 400L, paramAnonymousView);
        System.exit(0);
      }
    });
    this.downloadTabButton = ((FormTabButton)findViewById(2131493049));
    this.downloadTabButton.setTag("downloads");
    this.settingsTabButton = ((FormTabButton)findViewById(2131493050));
    this.settingsTabButton.setTag("settings");
    this.downloadTabButton.setView(findViewById(2131493051));
    this.settingsTabButton.setView(findViewById(2131493101));
    this.tabs = new TabManager(this, new FormTabButton[] { this.downloadTabButton, this.settingsTabButton });
    this.tabs.onClick(this.downloadTabButton);
    findViewById(2131493055).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        int i = 0;
        paramAnonymousView = PreferenceManager.getDefaultSharedPreferences(MainMenu.this.getContext());
        if (!paramAnonymousView.getBoolean("crumbyClickedDownloadDirectory", false)) {
          i = 1;
        }
        if (i != 0)
        {
          paramAnonymousView.edit().putBoolean("crumbyClickedDownloadDirectory", true).commit();
          paramAnonymousView = new AlertDialog.Builder(paramActivity);
          paramAnonymousView.setMessage("").setTitle("Warning!");
          paramAnonymousView.setPositiveButton("Ok", new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {}
          });
          paramAnonymousView.setMessage("To see the images you have saved, you will need a proper image browser or a file explorer. If you do not have one, this feature will NOT work!");
          paramAnonymousView = paramAnonymousView.create();
          paramAnonymousView.setOnDismissListener(new DialogInterface.OnDismissListener()
          {
            public void onDismiss(DialogInterface paramAnonymous2DialogInterface)
            {
              MainMenu.this.openSaveImageDirectory();
            }
          });
          paramAnonymousView.show();
          return;
        }
        MainMenu.this.openSaveImageDirectory();
      }
    });
    findViewById(2131493105).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MainMenu.this.changeDownloadDirectory();
      }
    });
    findViewById(2131493103).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        new CrumbySettings().show(((Activity)MainMenu.this.getContext()).getFragmentManager(), "dialog");
      }
    });
    findViewById(2131493104).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        new TagBlacklist().show(((Activity)MainMenu.this.getContext()).getFragmentManager(), "dialog");
      }
    });
    this.clearDownloads.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ImageDownloadManager.INSTANCE.terminateDownloads();
      }
    });
    ((SettingsCheckBox)findViewById(2131493107)).setPreferenceKey("crumbyPersistGridColumns", true);
    ((SettingsCheckBox)findViewById(2131493106)).setPreferenceKey("crumbyShowDownloadsOnSave", true);
    ((SettingsCheckBox)findViewById(2131493108)).setPreferenceKey("crumbyUseBreadcrumbPathForDownload", false);
    ((SettingsCheckBox)findViewById(2131493110)).setPreferenceKey("crumbyUseBreadcrumbPathForDownload", false);
    ImageDownloadManager.INSTANCE.addDefaultListener(this.downloadIndicator);
    ImageDownloadManager.INSTANCE.addDefaultListener((ImageDownloadListener)this.downloadList.getAdapter());
  }
  
  public boolean isShowing()
  {
    return this.showing;
  }
  
  public void onTabSwitch(FormTabButton paramFormTabButton)
  {
    Analytics.INSTANCE.newEvent(AnalyticsCategories.MAIN_MENU, "tab", (String)paramFormTabButton.getTag());
  }
  
  public void show()
  {
    if ((this.showing) || (this.animating)) {
      return;
    }
    ((DownloadListAdapter)this.downloadList.getAdapter()).unpause();
    this.showing = true;
    setVisibility(0);
    this.menuModal.setVisibility(0);
    this.animating = true;
    this.browserWindow.animate().xBy(-getMeasuredWidth()).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        MainMenu.access$002(MainMenu.this, false);
      }
    });
    this.menuButtonContainer.setPressed(true);
  }
  
  public void showButton()
  {
    this.menuButtonContainer.setVisibility(0);
  }
  
  public void showDownloads()
  {
    this.downloadList.setSelection(0);
    this.tabs.onClick(this.downloadTabButton);
    if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("crumbyShowDownloadsOnSave", true)) {
      return;
    }
    show();
  }
  
  public void toggleSettings()
  {
    toggleMenu();
    this.tabs.onClick(this.settingsTabButton);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/main_menu/MainMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */