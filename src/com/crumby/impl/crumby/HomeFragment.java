package com.crumby.impl.crumby;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.GalleryViewer.STORE;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.router.IndexSetting;
import com.crumby.lib.widget.HomeGalleryList;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.crumby.lib.widget.thirdparty.ObservableScrollView;
import com.crumby.lib.widget.thirdparty.ScrollViewListener;
import com.crumby.lib.widget.thirdparty.VerticalScrollView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uservoice.uservoicesdk.UserVoice;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class HomeFragment
  extends GalleryViewerFragment
  implements ScrollViewListener
{
  public static final int BREADCRUMB_ICON = 2130837640;
  public static final String BREADCRUMB_NAME = "search or type url";
  public static final String DISPLAY_NAME = "home";
  public static final String REGEX_URL = "";
  public static final String WANTS_SETTING_PROMPT = "wantsSettingPrompt";
  View goToWebSettings;
  Queue<HomeGalleryList> queuedLists;
  View settingsPrompt;
  TextView startMessage;
  Set<HomeGalleryList> startedLists;
  private View updateCrumby;
  
  private void addList(ViewGroup paramViewGroup, LayoutInflater paramLayoutInflater, FragmentIndex paramFragmentIndex)
  {
    paramLayoutInflater = (HomeGalleryList)paramLayoutInflater.inflate(2130903075, null);
    GalleryImage localGalleryImage = new GalleryImage("", paramFragmentIndex.getBaseUrl(), paramFragmentIndex.getDisplayName());
    localGalleryImage.setIcon(paramFragmentIndex.getBreadcrumbIcon());
    paramViewGroup.addView(paramLayoutInflater);
    paramLayoutInflater.initialize(localGalleryImage, paramFragmentIndex.getProducer(), getViewer().getMultiSelect(), false);
    this.queuedLists.add(paramLayoutInflater);
  }
  
  private void hideWebsiteSettingPrompt()
  {
    if (getActivity() == null) {
      return;
    }
    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean("wantsSettingPrompt", false).commit();
    this.settingsPrompt.setVisibility(8);
    this.goToWebSettings.setVisibility(0);
  }
  
  private void setUpAsHomeView(View paramView, LayoutInflater paramLayoutInflater)
  {
    this.queuedLists = new LinkedList();
    this.startedLists = new HashSet();
    ViewGroup localViewGroup = (ViewGroup)paramView.findViewById(2131493015);
    ((VerticalScrollView)paramView).setScrollViewListener(this);
    Iterator localIterator = FragmentRouter.INSTANCE.getAllIndexSettings().iterator();
    while (localIterator.hasNext())
    {
      IndexSetting localIndexSetting = (IndexSetting)localIterator.next();
      if (localIndexSetting.getAttributeBoolean("include_in_home")) {
        addList(localViewGroup, paramLayoutInflater, localIndexSetting.getIndex());
      }
    }
    paramView.findViewById(2131492980).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newNavigationEvent("uservoice", "home click");
        UserVoice.launchForum(HomeFragment.this.getActivity());
      }
    });
    paramView.getMeasuredHeight();
    ((VerticalScrollView)paramView).loadInitial(new Runnable()
    {
      public void run()
      {
        while (HomeFragment.this.shouldListBeLoaded(0, (ScrollView)HomeFragment.this.getView())) {}
      }
    });
    this.startMessage = ((TextView)paramView.findViewById(2131493011));
    this.updateCrumby = paramView.findViewById(2131493012);
    this.updateCrumby.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          paramAnonymousView = HomeFragment.this.getActivity().getPackageManager().getPackageInfo(HomeFragment.this.getActivity().getPackageName(), 0).versionName;
          Analytics.INSTANCE.newEvent(AnalyticsCategories.MISCELLANEOUS, "update button click", paramAnonymousView);
          paramAnonymousView = new Intent("android.intent.action.VIEW", Uri.parse(GalleryViewer.BUILD_FOR_STORE.link));
          HomeFragment.this.startActivity(paramAnonymousView);
          return;
        }
        catch (Exception paramAnonymousView)
        {
          for (;;)
          {
            Analytics.INSTANCE.newException(paramAnonymousView);
          }
        }
      }
    });
    paramLayoutInflater = PreferenceManager.getDefaultSharedPreferences(getActivity());
    if (!paramLayoutInflater.getBoolean("newToCrumby", true)) {
      new AsyncTask()
      {
        protected String doInBackground(Void... paramAnonymousVarArgs)
        {
          try
          {
            paramAnonymousVarArgs = GalleryProducer.fetchUrl("http://timschambers.com/crumby_start_message.txt");
            return paramAnonymousVarArgs;
          }
          catch (IOException paramAnonymousVarArgs) {}
          return null;
        }
        
        protected void onPostExecute(String paramAnonymousString)
        {
          if ((paramAnonymousString == null) || (HomeFragment.this.getActivity() == null) || (HomeFragment.this.getView() == null)) {
            return;
          }
          try
          {
            String str1 = HomeFragment.this.getActivity().getPackageManager().getPackageInfo(HomeFragment.this.getActivity().getPackageName(), 0).versionName;
            paramAnonymousString = new JsonParser().parse(paramAnonymousString).getAsJsonObject();
            String str2 = paramAnonymousString.get("latest_version").getAsString();
            if ((!str1.equals(str2)) && (Float.parseFloat(str2) > Float.parseFloat(str1)))
            {
              HomeFragment.this.startMessage.setText("It looks like your version of Crumby is out of date.\n\nDownload the latest version on the play store to get the latest features and bug fixes!\n");
              ((Button)HomeFragment.this.updateCrumby).setText("Update to Version " + str2);
              HomeFragment.this.updateCrumby.setVisibility(0);
              return;
            }
            paramAnonymousString = paramAnonymousString.get("message").getAsString();
            HomeFragment.this.startMessage.setText(paramAnonymousString);
            return;
          }
          catch (Exception paramAnonymousString) {}
        }
      }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }
    for (;;)
    {
      this.goToWebSettings = paramView.findViewById(2131493014);
      this.goToWebSettings.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, "home button click", null);
          new CrumbySettings().show(HomeFragment.this.getActivity().getFragmentManager(), "dialog");
        }
      });
      paramView.findViewById(2131493019).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, "home button click 1", null);
          new CrumbySettings().show(HomeFragment.this.getActivity().getFragmentManager(), "dialog");
        }
      });
      this.settingsPrompt = paramView.findViewById(2131493018);
      if (!paramLayoutInflater.getBoolean("wantsSettingPrompt", true)) {
        break;
      }
      this.settingsPrompt.setVisibility(0);
      paramView.findViewById(2131493020).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          HomeFragment.this.hideWebsiteSettingPrompt();
        }
      });
      return;
      paramLayoutInflater.edit().putBoolean("newToCrumby", false).commit();
    }
    hideWebsiteSettingPrompt();
  }
  
  private void setUpAsNewUserView(View paramView, LayoutInflater paramLayoutInflater)
  {
    paramView.findViewById(2131493013).setVisibility(8);
    ((TextView)paramView.findViewById(2131493011)).setText("Thanks for downloading, Crumby!\n To start off, would you like to change the image galleries that show up on your home page? \n");
    paramView = (ViewGroup)paramView.findViewById(2131493016);
    paramView.setVisibility(0);
    paramLayoutInflater = FragmentRouter.INSTANCE.getAllIndexSettings().iterator();
    while (paramLayoutInflater.hasNext())
    {
      IndexSetting localIndexSetting = (IndexSetting)paramLayoutInflater.next();
      ToggleButton localToggleButton = new ToggleButton(getActivity());
      localToggleButton.setText(localIndexSetting.getDisplayName());
      localToggleButton.setCompoundDrawablesWithIntrinsicBounds(localIndexSetting.getIndex().getRootBreadcrumbIcon(), 0, 0, 0);
      localToggleButton.setTextOn(localIndexSetting.getDisplayName());
      localToggleButton.setTextOff(localIndexSetting.getDisplayName());
      paramView.addView(localToggleButton);
    }
  }
  
  private boolean shouldListBeLoaded(int paramInt, ScrollView paramScrollView)
  {
    if (!this.queuedLists.isEmpty())
    {
      int i = ((View)((HomeGalleryList)this.queuedLists.peek()).getParent()).getTop();
      if (((HomeGalleryList)this.queuedLists.peek()).getTop() + i < paramScrollView.getHeight() + paramInt)
      {
        CrDb.d(getClass().getName(), paramInt + "");
        paramScrollView = (HomeGalleryList)this.queuedLists.remove();
        paramScrollView.start();
        this.startedLists.add(paramScrollView);
        return true;
      }
    }
    return false;
  }
  
  protected void cleanLinkUrl() {}
  
  public View createFragmentView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramViewGroup = paramLayoutInflater.inflate(2130903084, null);
    indicateProgressChange(1.0F);
    setUpAsHomeView(paramViewGroup, paramLayoutInflater);
    return paramViewGroup;
  }
  
  protected GalleryProducer createProducer()
  {
    return null;
  }
  
  public void deferSetDescription(String paramString) {}
  
  public GalleryConsumer getConsumer()
  {
    return null;
  }
  
  public void goToImage(View paramView, GalleryImage paramGalleryImage, int paramInt) {}
  
  public void hideClutter() {}
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if ((getView() == null) || (this.queuedLists == null)) {}
    for (;;)
    {
      return;
      while (shouldListBeLoaded(getView().getMeasuredHeight(), (ScrollView)getView())) {}
    }
  }
  
  public void onDestroyView()
  {
    if (this.queuedLists != null) {
      this.queuedLists.clear();
    }
    if (this.startedLists != null)
    {
      Iterator localIterator = this.startedLists.iterator();
      while (localIterator.hasNext()) {
        ((HomeGalleryList)localIterator.next()).cancel();
      }
      this.startedLists.clear();
    }
    super.onDestroyView();
  }
  
  public void onScrollChanged(ObservableScrollView paramObservableScrollView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    shouldListBeLoaded(paramInt2, paramObservableScrollView);
  }
  
  public void prepareForRefresh() {}
  
  public void resume() {}
  
  public void setBreadcrumbs(BreadcrumbListModifier paramBreadcrumbListModifier)
  {
    List localList = paramBreadcrumbListModifier.getChildren();
    ((Breadcrumb)localList.get(0)).setTextColor(-3355444);
    ((Breadcrumb)localList.get(0)).invalidate();
    super.setBreadcrumbs(paramBreadcrumbListModifier);
  }
  
  public void showClutter() {}
  
  public void stopLoading() {}
  
  public boolean undo()
  {
    return false;
  }
  
  public boolean willAllowPaging(MotionEvent paramMotionEvent)
  {
    return false;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/crumby/HomeFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */