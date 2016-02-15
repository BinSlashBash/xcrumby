/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.FragmentManager
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.res.Configuration
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.Button
 *  android.widget.ScrollView
 *  android.widget.TextView
 *  android.widget.ToggleButton
 */
package com.crumby.impl.crumby;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.CrumbySettings;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.router.IndexSetting;
import com.crumby.lib.widget.HomeGalleryList;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
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
import java.util.concurrent.Executor;

public class HomeFragment
extends GalleryViewerFragment
implements ScrollViewListener {
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

    private void addList(ViewGroup viewGroup, LayoutInflater object, FragmentIndex fragmentIndex) {
        object = (HomeGalleryList)object.inflate(2130903075, null);
        GalleryImage galleryImage = new GalleryImage("", fragmentIndex.getBaseUrl(), fragmentIndex.getDisplayName());
        galleryImage.setIcon(fragmentIndex.getBreadcrumbIcon());
        viewGroup.addView((View)object);
        object.initialize(galleryImage, fragmentIndex.getProducer(), this.getViewer().getMultiSelect(), false);
        this.queuedLists.add((HomeGalleryList)object);
    }

    private void hideWebsiteSettingPrompt() {
        if (this.getActivity() == null) {
            return;
        }
        PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putBoolean("wantsSettingPrompt", false).commit();
        this.settingsPrompt.setVisibility(8);
        this.goToWebSettings.setVisibility(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setUpAsHomeView(View view, LayoutInflater layoutInflater) {
        this.queuedLists = new LinkedList<HomeGalleryList>();
        this.startedLists = new HashSet<HomeGalleryList>();
        ViewGroup viewGroup = (ViewGroup)view.findViewById(2131493015);
        ((VerticalScrollView)view).setScrollViewListener(this);
        for (IndexSetting indexSetting : FragmentRouter.INSTANCE.getAllIndexSettings()) {
            if (!indexSetting.getAttributeBoolean("include_in_home")) continue;
            this.addList(viewGroup, layoutInflater, indexSetting.getIndex());
        }
        view.findViewById(2131492980).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newNavigationEvent("uservoice", "home click");
                UserVoice.launchForum((Context)HomeFragment.this.getActivity());
            }
        });
        view.getMeasuredHeight();
        ((VerticalScrollView)view).loadInitial(new Runnable(){

            @Override
            public void run() {
                while (HomeFragment.this.shouldListBeLoaded(0, (ScrollView)HomeFragment.this.getView())) {
                }
            }
        });
        this.startMessage = (TextView)view.findViewById(2131493011);
        this.updateCrumby = view.findViewById(2131493012);
        this.updateCrumby.setOnClickListener(new View.OnClickListener(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onClick(View object) {
                try {
                    object = HomeFragment.this.getActivity().getPackageManager().getPackageInfo((String)HomeFragment.this.getActivity().getPackageName(), (int)0).versionName;
                    Analytics.INSTANCE.newEvent(AnalyticsCategories.MISCELLANEOUS, "update button click", (String)object);
                }
                catch (Exception var1_2) {
                    Analytics.INSTANCE.newException(var1_2);
                }
                object = new Intent("android.intent.action.VIEW", Uri.parse((String)GalleryViewer.BUILD_FOR_STORE.link));
                HomeFragment.this.startActivity((Intent)object);
            }
        });
        layoutInflater = PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity());
        if (!layoutInflater.getBoolean("newToCrumby", true)) {
            new AsyncTask<Void, Void, String>(){

                protected /* varargs */ String doInBackground(Void ... object) {
                    try {
                        object = GalleryProducer.fetchUrl("http://timschambers.com/crumby_start_message.txt");
                        return object;
                    }
                    catch (IOException var1_2) {
                        return null;
                    }
                }

                protected void onPostExecute(String object) {
                    if (object == null || HomeFragment.this.getActivity() == null || HomeFragment.this.getView() == null) {
                        return;
                    }
                    try {
                        String string2 = HomeFragment.this.getActivity().getPackageManager().getPackageInfo((String)HomeFragment.this.getActivity().getPackageName(), (int)0).versionName;
                        object = new JsonParser().parse((String)object).getAsJsonObject();
                        String string3 = object.get("latest_version").getAsString();
                        if (!string2.equals(string3) && Float.parseFloat(string3) > Float.parseFloat(string2)) {
                            HomeFragment.this.startMessage.setText((CharSequence)"It looks like your version of Crumby is out of date.\n\nDownload the latest version on the play store to get the latest features and bug fixes!\n");
                            ((Button)HomeFragment.this.updateCrumby).setText((CharSequence)("Update to Version " + string3));
                            HomeFragment.this.updateCrumby.setVisibility(0);
                            return;
                        }
                        object = object.get("message").getAsString();
                        HomeFragment.this.startMessage.setText((CharSequence)object);
                        return;
                    }
                    catch (Exception var1_2) {
                        return;
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
        } else {
            layoutInflater.edit().putBoolean("newToCrumby", false).commit();
        }
        this.goToWebSettings = view.findViewById(2131493014);
        this.goToWebSettings.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, "home button click", null);
                new CrumbySettings().show(HomeFragment.this.getActivity().getFragmentManager(), "dialog");
            }
        });
        view.findViewById(2131493019).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, "home button click 1", null);
                new CrumbySettings().show(HomeFragment.this.getActivity().getFragmentManager(), "dialog");
            }
        });
        this.settingsPrompt = view.findViewById(2131493018);
        if (layoutInflater.getBoolean("wantsSettingPrompt", true)) {
            this.settingsPrompt.setVisibility(0);
            view.findViewById(2131493020).setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    HomeFragment.this.hideWebsiteSettingPrompt();
                }
            });
            return;
        }
        this.hideWebsiteSettingPrompt();
    }

    private void setUpAsNewUserView(View view, LayoutInflater object) {
        view.findViewById(2131493013).setVisibility(8);
        ((TextView)view.findViewById(2131493011)).setText((CharSequence)"Thanks for downloading, Crumby!\n To start off, would you like to change the image galleries that show up on your home page? \n");
        view = (ViewGroup)view.findViewById(2131493016);
        view.setVisibility(0);
        for (IndexSetting indexSetting : FragmentRouter.INSTANCE.getAllIndexSettings()) {
            ToggleButton toggleButton = new ToggleButton((Context)this.getActivity());
            toggleButton.setText((CharSequence)indexSetting.getDisplayName());
            toggleButton.setCompoundDrawablesWithIntrinsicBounds(indexSetting.getIndex().getRootBreadcrumbIcon(), 0, 0, 0);
            toggleButton.setTextOn((CharSequence)indexSetting.getDisplayName());
            toggleButton.setTextOff((CharSequence)indexSetting.getDisplayName());
            view.addView((View)toggleButton);
        }
    }

    private boolean shouldListBeLoaded(int n2, ScrollView object) {
        if (!this.queuedLists.isEmpty()) {
            int n3 = ((View)this.queuedLists.peek().getParent()).getTop();
            if (this.queuedLists.peek().getTop() + n3 < object.getHeight() + n2) {
                CrDb.d(this.getClass().getName(), "" + n2 + "");
                object = this.queuedLists.remove();
                object.start();
                this.startedLists.add((HomeGalleryList)object);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void cleanLinkUrl() {
    }

    @Override
    public View createFragmentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        viewGroup = layoutInflater.inflate(2130903084, null);
        this.indicateProgressChange(1.0f);
        this.setUpAsHomeView((View)viewGroup, layoutInflater);
        return viewGroup;
    }

    @Override
    protected GalleryProducer createProducer() {
        return null;
    }

    @Override
    public void deferSetDescription(String string2) {
    }

    @Override
    public GalleryConsumer getConsumer() {
        return null;
    }

    @Override
    public void goToImage(View view, GalleryImage galleryImage, int n2) {
    }

    @Override
    public void hideClutter() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.getView() != null && this.queuedLists != null) {
            while (this.shouldListBeLoaded(this.getView().getMeasuredHeight(), (ScrollView)this.getView())) {
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (this.queuedLists != null) {
            this.queuedLists.clear();
        }
        if (this.startedLists != null) {
            Iterator<HomeGalleryList> iterator = this.startedLists.iterator();
            while (iterator.hasNext()) {
                iterator.next().cancel();
            }
            this.startedLists.clear();
        }
        super.onDestroyView();
    }

    @Override
    public void onScrollChanged(ObservableScrollView observableScrollView, int n2, int n3, int n4, int n5) {
        this.shouldListBeLoaded(n3, observableScrollView);
    }

    @Override
    public void prepareForRefresh() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbListModifier) {
        List<Breadcrumb> list = breadcrumbListModifier.getChildren();
        list.get(0).setTextColor(-3355444);
        list.get(0).invalidate();
        super.setBreadcrumbs(breadcrumbListModifier);
    }

    @Override
    public void showClutter() {
    }

    @Override
    public void stopLoading() {
    }

    @Override
    public boolean undo() {
        return false;
    }

    @Override
    public boolean willAllowPaging(MotionEvent motionEvent) {
        return false;
    }

}

