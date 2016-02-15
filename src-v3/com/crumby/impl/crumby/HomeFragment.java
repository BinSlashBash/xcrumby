package com.crumby.impl.crumby;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uservoice.uservoicesdk.UserVoice;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class HomeFragment extends GalleryViewerFragment implements ScrollViewListener {
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

    /* renamed from: com.crumby.impl.crumby.HomeFragment.1 */
    class C00741 implements OnClickListener {
        C00741() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newNavigationEvent("uservoice", "home click");
            UserVoice.launchForum(HomeFragment.this.getActivity());
        }
    }

    /* renamed from: com.crumby.impl.crumby.HomeFragment.2 */
    class C00752 implements Runnable {
        C00752() {
        }

        public void run() {
            do {
            } while (HomeFragment.this.shouldListBeLoaded(0, (ScrollView) HomeFragment.this.getView()));
        }
    }

    /* renamed from: com.crumby.impl.crumby.HomeFragment.3 */
    class C00763 implements OnClickListener {
        C00763() {
        }

        public void onClick(View v) {
            try {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.MISCELLANEOUS, "update button click", HomeFragment.this.getActivity().getPackageManager().getPackageInfo(HomeFragment.this.getActivity().getPackageName(), 0).versionName);
            } catch (Exception e) {
                Analytics.INSTANCE.newException(e);
            }
            HomeFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(GalleryViewer.BUILD_FOR_STORE.link)));
        }
    }

    /* renamed from: com.crumby.impl.crumby.HomeFragment.4 */
    class C00774 extends AsyncTask<Void, Void, String> {
        C00774() {
        }

        protected String doInBackground(Void... params) {
            String result = null;
            try {
                result = GalleryProducer.fetchUrl("http://timschambers.com/crumby_start_message.txt");
            } catch (IOException e) {
            }
            return result;
        }

        protected void onPostExecute(String s) {
            if (s != null && HomeFragment.this.getActivity() != null && HomeFragment.this.getView() != null) {
                try {
                    String version = HomeFragment.this.getActivity().getPackageManager().getPackageInfo(HomeFragment.this.getActivity().getPackageName(), 0).versionName;
                    JsonObject response = new JsonParser().parse(s).getAsJsonObject();
                    String latestVersion = response.get("latest_version").getAsString();
                    if (version.equals(latestVersion) || Float.parseFloat(latestVersion) <= Float.parseFloat(version)) {
                        HomeFragment.this.startMessage.setText(response.get("message").getAsString());
                        return;
                    }
                    HomeFragment.this.startMessage.setText("It looks like your version of Crumby is out of date.\n\nDownload the latest version on the play store to get the latest features and bug fixes!\n");
                    ((Button) HomeFragment.this.updateCrumby).setText("Update to Version " + latestVersion);
                    HomeFragment.this.updateCrumby.setVisibility(0);
                } catch (Exception e) {
                }
            }
        }
    }

    /* renamed from: com.crumby.impl.crumby.HomeFragment.5 */
    class C00785 implements OnClickListener {
        C00785() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, "home button click", null);
            new CrumbySettings().show(HomeFragment.this.getActivity().getFragmentManager(), "dialog");
        }
    }

    /* renamed from: com.crumby.impl.crumby.HomeFragment.6 */
    class C00796 implements OnClickListener {
        C00796() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.SETTINGS, "home button click 1", null);
            new CrumbySettings().show(HomeFragment.this.getActivity().getFragmentManager(), "dialog");
        }
    }

    /* renamed from: com.crumby.impl.crumby.HomeFragment.7 */
    class C00807 implements OnClickListener {
        C00807() {
        }

        public void onClick(View v) {
            HomeFragment.this.hideWebsiteSettingPrompt();
        }
    }

    protected GalleryProducer createProducer() {
        return null;
    }

    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbList) {
        List<Breadcrumb> breadcrumbs = breadcrumbList.getChildren();
        ((Breadcrumb) breadcrumbs.get(0)).setTextColor(-3355444);
        ((Breadcrumb) breadcrumbs.get(0)).invalidate();
        super.setBreadcrumbs(breadcrumbList);
    }

    protected void cleanLinkUrl() {
    }

    private void addList(ViewGroup parent, LayoutInflater inflater, FragmentIndex index) {
        HomeGalleryList homeGalleryList = (HomeGalleryList) inflater.inflate(C0065R.layout.gallery_horizontal_list_container, null);
        GalleryImage image = new GalleryImage(REGEX_URL, index.getBaseUrl(), index.getDisplayName());
        image.setIcon(index.getBreadcrumbIcon());
        parent.addView(homeGalleryList);
        homeGalleryList.initialize(image, index.getProducer(), getViewer().getMultiSelect(), false);
        this.queuedLists.add(homeGalleryList);
    }

    private void setUpAsHomeView(View view, LayoutInflater inflater) {
        this.queuedLists = new LinkedList();
        this.startedLists = new HashSet();
        ViewGroup listParent = (ViewGroup) view.findViewById(C0065R.id.list_holder);
        ((VerticalScrollView) view).setScrollViewListener(this);
        for (IndexSetting setting : FragmentRouter.INSTANCE.getAllIndexSettings()) {
            if (setting.getAttributeBoolean(GalleryViewerFragment.INCLUDE_IN_HOME_KEY)) {
                addList(listParent, inflater, setting.getIndex());
            }
        }
        view.findViewById(C0065R.id.user_forum).setOnClickListener(new C00741());
        int height = view.getMeasuredHeight();
        ((VerticalScrollView) view).loadInitial(new C00752());
        this.startMessage = (TextView) view.findViewById(C0065R.id.start_message);
        this.updateCrumby = view.findViewById(C0065R.id.update_crumby);
        this.updateCrumby.setOnClickListener(new C00763());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (prefs.getBoolean("newToCrumby", true)) {
            prefs.edit().putBoolean("newToCrumby", false).commit();
        } else {
            new C00774().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
        this.goToWebSettings = view.findViewById(C0065R.id.go_to_manage_websites);
        this.goToWebSettings.setOnClickListener(new C00785());
        view.findViewById(C0065R.id.go_to_manage_websites_1).setOnClickListener(new C00796());
        this.settingsPrompt = view.findViewById(C0065R.id.home_settings_prompt);
        if (prefs.getBoolean(WANTS_SETTING_PROMPT, true)) {
            this.settingsPrompt.setVisibility(0);
            view.findViewById(C0065R.id.hide_website_settings_prompt).setOnClickListener(new C00807());
            return;
        }
        hideWebsiteSettingPrompt();
    }

    private void hideWebsiteSettingPrompt() {
        if (getActivity() != null) {
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(WANTS_SETTING_PROMPT, false).commit();
            this.settingsPrompt.setVisibility(8);
            this.goToWebSettings.setVisibility(0);
        }
    }

    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C0065R.layout.home, null);
        indicateProgressChange(GalleryViewer.PROGRESS_COMPLETED);
        setUpAsHomeView(view, inflater);
        return view;
    }

    private void setUpAsNewUserView(View view, LayoutInflater inflater) {
        view.findViewById(C0065R.id.default_home).setVisibility(8);
        ((TextView) view.findViewById(C0065R.id.start_message)).setText("Thanks for downloading, Crumby!\n To start off, would you like to change the image galleries that show up on your home page? \n");
        ViewGroup chooseWebsites = (ViewGroup) view.findViewById(C0065R.id.website_chooser_new_user);
        chooseWebsites.setVisibility(0);
        for (IndexSetting setting : FragmentRouter.INSTANCE.getAllIndexSettings()) {
            ToggleButton button = new ToggleButton(getActivity());
            button.setText(setting.getDisplayName());
            button.setCompoundDrawablesWithIntrinsicBounds(setting.getIndex().getRootBreadcrumbIcon(), 0, 0, 0);
            button.setTextOn(setting.getDisplayName());
            button.setTextOff(setting.getDisplayName());
            chooseWebsites.addView(button);
        }
    }

    public void stopLoading() {
    }

    public void resume() {
    }

    public void showClutter() {
    }

    public void hideClutter() {
    }

    public boolean undo() {
        return false;
    }

    public void deferSetDescription(String update) {
    }

    public boolean willAllowPaging(MotionEvent ev) {
        return false;
    }

    public void prepareForRefresh() {
    }

    public GalleryConsumer getConsumer() {
        return null;
    }

    public void goToImage(View v, GalleryImage image, int position) {
    }

    private boolean shouldListBeLoaded(int y, ScrollView scrollView) {
        if (!this.queuedLists.isEmpty()) {
            if (((HomeGalleryList) this.queuedLists.peek()).getTop() + ((View) ((HomeGalleryList) this.queuedLists.peek()).getParent()).getTop() < scrollView.getHeight() + y) {
                CrDb.m0d(getClass().getName(), y + REGEX_URL);
                HomeGalleryList list = (HomeGalleryList) this.queuedLists.remove();
                list.start();
                this.startedLists.add(list);
                return true;
            }
        }
        return false;
    }

    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        shouldListBeLoaded(y, scrollView);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getView() != null && this.queuedLists != null) {
            while (shouldListBeLoaded(getView().getMeasuredHeight(), (ScrollView) getView())) {
            }
        }
    }

    public void onDestroyView() {
        if (this.queuedLists != null) {
            this.queuedLists.clear();
        }
        if (this.startedLists != null) {
            for (HomeGalleryList list : this.startedLists) {
                list.cancel();
            }
            this.startedLists.clear();
        }
        super.onDestroyView();
    }
}
