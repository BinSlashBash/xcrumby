package com.crumby;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.AlertDialog.Builder;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.derpibooru.DerpibooruFragment;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.impl.furaffinity.FurAffinityFragment;
import com.crumby.lib.authentication.AuthenticatorActivity;
import com.crumby.lib.download.ImageDownloadManager;
import com.crumby.lib.events.PagerEvent;
import com.crumby.lib.events.ReloadFragmentEvent;
import com.crumby.lib.events.SilentUrlRedirectEvent;
import com.crumby.lib.events.StopLoadingFragmentEvent;
import com.crumby.lib.events.TerminateEvent;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.events.WebsiteSettingsChangedEvent;
import com.crumby.lib.fragment.GalleryPage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.universal.UniversalInterpreterManager;
import com.crumby.lib.widget.firstparty.main_menu.MainMenu;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelectBar;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.ExpandActionBarButton;
import com.crumby.lib.widget.firstparty.omnibar.OmnibarView;
import com.crumby.lib.widget.thirdparty.WebkitCookieManagerProxy;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.plus.PlusShare;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.tapstream.sdk.Config;
import com.tapstream.sdk.Tapstream;
import com.uservoice.uservoicesdk.UserVoice;
import it.gmariotti.changelibs.library.view.ChangeLogListView;
import it.sephiroth.android.library.tooltip.TooltipManager;
import it.sephiroth.android.library.tooltip.TooltipManager.ClosePolicy;
import it.sephiroth.android.library.tooltip.TooltipManager.Gravity;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Stack;
import net.rdrei.android.dirchooser.DirectoryChooserFragment.OnFragmentInteractionListener;

public class GalleryViewer extends Activity implements OnFragmentInteractionListener {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final String APP_VERSION = "45";
    public static final boolean BING_SEARCH = true;
    public static LinkedHashSet<String> BLACK_LISTED_TAGS = null;
    public static final STORE BUILD_FOR_STORE;
    public static final String CRUMBY_DOWNLOAD_DIRECTORY = "crumbyDownloadDirectory";
    public static final String DO_NOT_CREATE_ACCOUNT = "doNotCreateAccount";
    public static final String FORCE_RELOGIN = "forceRelogin";
    public static String PREVIOUS_VERSION = null;
    public static final float PROGRESS_COMPLETED = 1.0f;
    public static final float PROGRESS_STARTED = 0.05f;
    public static final String TAG_BLACKLIST_KEY = "crumbyTagBlacklistKey";
    public static boolean USER_JUST_INSTALLED;
    private static boolean sawFeedbackAlert;
    public static boolean shownGallerySearchTutorial;
    private boolean autoLoggingIn;
    private GalleryPage currentPage;
    private ExpandActionBarButton expandActionBarButton;
    private Runnable fadeProgress;
    private Handler feedback;
    private Runnable feeedbackRunnable;
    int fragmentCounter;
    private MainMenu mainMenu;
    private MultiSelectBar multiSelectBar;
    private boolean omnibarShowing;
    private OmnibarView omnibarView;
    private boolean onboarding;
    boolean optionStart;
    private int pageTag;
    private Handler progressHandler;
    private RelativeLayout progressIndicator;
    private Stack<GalleryPage> redo;
    private ImageButton redoButton;
    private boolean searchGalleriesShowing;
    private long startMegs;
    private ImageButton undoButton;
    private View undoRedoContainer;

    /* renamed from: com.crumby.GalleryViewer.12 */
    class AnonymousClass12 implements Runnable {
        final /* synthetic */ FragmentManager val$fm;

        AnonymousClass12(FragmentManager fragmentManager) {
            this.val$fm = fragmentManager;
        }

        public void run() {
            this.val$fm.popBackStackImmediate();
        }
    }

    /* renamed from: com.crumby.GalleryViewer.13 */
    class AnonymousClass13 implements AnimatorListener {
        final /* synthetic */ FragmentTransaction val$transaction;

        AnonymousClass13(FragmentTransaction fragmentTransaction) {
            this.val$transaction = fragmentTransaction;
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            View galleryView = GalleryViewer.this.findViewById(C0065R.id.gallery_view);
            galleryView.setAlpha(GalleryViewer.PROGRESS_COMPLETED);
            galleryView.animate().setListener(null);
            this.val$transaction.commit();
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    /* renamed from: com.crumby.GalleryViewer.1 */
    class C00531 implements Runnable {
        C00531() {
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000);
                    ((GalleryViewer) GalleryViewer.this.getActivity()).memCal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* renamed from: com.crumby.GalleryViewer.2 */
    class C00542 implements OnClickListener {
        C00542() {
        }

        public void onClick(View v) {
            GalleryViewer.this.showClutter();
        }
    }

    /* renamed from: com.crumby.GalleryViewer.3 */
    class C00553 implements Runnable {
        C00553() {
        }

        public void run() {
            GalleryViewer.this.hideProgress();
        }
    }

    /* renamed from: com.crumby.GalleryViewer.4 */
    class C00564 extends BaseAdapter {
        UserDefaultGallery[] choose;

        /* renamed from: com.crumby.GalleryViewer.4.UserDefaultGallery */
        class UserDefaultGallery {
            String imageUrl;
            String name;

            public UserDefaultGallery(String name, String imageUrl) {
                this.name = name;
                this.imageUrl = imageUrl;
            }
        }

        C00564() {
            this.choose = new UserDefaultGallery[]{new UserDefaultGallery("Viral", "http://www.moviehdwallpapers.com/wp-content/uploads/2014/10/google_images_-_photos_videos.jpg"), new UserDefaultGallery("Anime", "http://th06.deviantart.net/fs70/PRE/i/2011/280/8/3/minimal_black_rock_shooter_by_outre1-d4c3k55.png"), new UserDefaultGallery("Furry", "http://lounge.moviecodec.com/images/attachment/true-example-of-furry-art-4323.jpg"), new UserDefaultGallery("My Little Pony", "http://wallpoper.com/images/00/37/36/33/my-little_00373633.jpg")};
        }

        public int getCount() {
            return this.choose.length;
        }

        public Object getItem(int position) {
            return this.choose[position];
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = GalleryViewer.this.getLayoutInflater().inflate(C0065R.layout.onboard_user_gallery, null);
            }
            ((TextView) convertView.findViewById(C0065R.id.onboard_user_title)).setText(this.choose[position].name);
            Picasso.with(GalleryViewer.this.getActivity()).load(this.choose[position].imageUrl).into((ImageView) convertView.findViewById(C0065R.id.onboard_user_image));
            return convertView;
        }
    }

    /* renamed from: com.crumby.GalleryViewer.5 */
    class C00585 implements OnClickListener {

        /* renamed from: com.crumby.GalleryViewer.5.1 */
        class C00571 implements AnimatorListener {
            C00571() {
            }

            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                ((ViewGroup) GalleryViewer.this.findViewById(C0065R.id.onboard_new_user_page).getParent()).removeView(GalleryViewer.this.findViewById(C0065R.id.onboard_new_user_page));
                GalleryViewer.this.findViewById(C0065R.id.action_bar).setVisibility(0);
                GalleryViewer.this.loadNewGalleryPageFromUrl(UnsupportedUrlFragment.DISPLAY_NAME, null, null);
                GalleryViewer.this.onboarding = GalleryViewer.$assertionsDisabled;
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        }

        C00585() {
        }

        public void onClick(View v) {
            ((ViewGroup) GalleryViewer.this.findViewById(C0065R.id.onboard_new_user_page)).animate().x((float) (-GalleryViewer.this.getResources().getDisplayMetrics().widthPixels)).setListener(new C00571());
        }
    }

    /* renamed from: com.crumby.GalleryViewer.6 */
    class C00596 implements Runnable {
        final /* synthetic */ Handler val$handler;
        final /* synthetic */ int val$index;
        final /* synthetic */ String val$url;

        C00596(String str, int i, Handler handler) {
            this.val$url = str;
            this.val$index = i;
            this.val$handler = handler;
        }

        public void run() {
            List<String> settings = FragmentRouter.INSTANCE.getAllWebsiteUrls();
            boolean next = GalleryViewer.$assertionsDisabled;
            String url1 = this.val$url;
            for (String setting : settings) {
                if (next || this.val$url == null) {
                    url1 = setting;
                    next = GalleryViewer.$assertionsDisabled;
                    break;
                } else if (setting.equals(this.val$url)) {
                    next = GalleryViewer.BING_SEARCH;
                }
            }
            if (next) {
                url1 = (String) settings.get(0);
            }
            int index1 = this.val$index + 1;
            Log.e(index1 + UnsupportedUrlFragment.DISPLAY_NAME, url1);
            GalleryViewer.this.loadNewGalleryPageFromUrl(url1, null, null);
            GalleryViewer.this.testGalleries(this.val$handler, url1, index1);
        }
    }

    /* renamed from: com.crumby.GalleryViewer.7 */
    class C00607 implements DialogInterface.OnClickListener {
        C00607() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
        }
    }

    /* renamed from: com.crumby.GalleryViewer.8 */
    class C00618 implements AccountManagerCallback<Bundle> {
        final /* synthetic */ String val$accountType;
        final /* synthetic */ boolean val$showToast;

        C00618(String str, boolean z) {
            this.val$accountType = str;
            this.val$showToast = z;
        }

        public void run(AccountManagerFuture<Bundle> future) {
            Exception e;
            Bundle bundle1;
            Account account = null;
            try {
                Bundle bnd = (Bundle) future.getResult();
                Account account2 = new Account(bnd.getString("authAccount"), this.val$accountType);
                try {
                    AuthenticatorActivity.getServerAuthenticate(this.val$accountType).userSignInAndGetAuth(bnd.getString("authAccount"), AccountManager.get(GalleryViewer.this.getActivity()).getPassword(account2), "BULLSHIT");
                    if (this.val$showToast) {
                        Toast toast = Toast.makeText(GalleryViewer.this.getApplicationContext(), UnsupportedUrlFragment.DISPLAY_NAME, 1);
                        toast.setGravity(48, 0, 0);
                        TextView view = (TextView) View.inflate(GalleryViewer.this.getApplicationContext(), C0065R.layout.toast_alert_success, null);
                        view.setText("You have relogged in to " + this.val$accountType + ".");
                        toast.setView(view);
                        toast.show();
                    }
                    account = account2;
                } catch (Exception e2) {
                    e = e2;
                    account = account2;
                    if (account != null) {
                        AccountManager.get(GalleryViewer.this.getActivity()).clearPassword(account);
                        bundle1 = new Bundle();
                        if (!(e instanceof IOException)) {
                            bundle1.putBoolean(AuthenticatorActivity.ARG_ERROR_WITH_ACCOUNT, GalleryViewer.BING_SEARCH);
                            AccountManager.get(GalleryViewer.this.getActivity()).getAuthToken(account, this.val$accountType, bundle1, GalleryViewer.this.getActivity(), null, null);
                            GalleryViewer.this.autoLoggingIn = GalleryViewer.$assertionsDisabled;
                        }
                        return;
                    }
                    return;
                }
            } catch (Exception e3) {
                e = e3;
                if (account != null) {
                    AccountManager.get(GalleryViewer.this.getActivity()).clearPassword(account);
                    bundle1 = new Bundle();
                    if (!(e instanceof IOException)) {
                        bundle1.putBoolean(AuthenticatorActivity.ARG_ERROR_WITH_ACCOUNT, GalleryViewer.BING_SEARCH);
                        AccountManager.get(GalleryViewer.this.getActivity()).getAuthToken(account, this.val$accountType, bundle1, GalleryViewer.this.getActivity(), null, null);
                        GalleryViewer.this.autoLoggingIn = GalleryViewer.$assertionsDisabled;
                    }
                    return;
                }
                return;
            }
            GalleryViewer.this.autoLoggingIn = GalleryViewer.$assertionsDisabled;
        }
    }

    /* renamed from: com.crumby.GalleryViewer.9 */
    class C00649 implements Runnable {

        /* renamed from: com.crumby.GalleryViewer.9.1 */
        class C00621 implements DialogInterface.OnClickListener {
            C00621() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "idea alert", "not interested");
            }
        }

        /* renamed from: com.crumby.GalleryViewer.9.2 */
        class C00632 implements DialogInterface.OnClickListener {
            C00632() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "idea alert", "interested");
                Analytics.INSTANCE.newNavigationEvent("uservoice", "help modal");
                UserVoice.launchForum(GalleryViewer.this.getActivity());
            }
        }

        C00649() {
        }

        public void run() {
            new Builder(GalleryViewer.this.getActivity()).setTitle("Any questions, concerns, or requests?").setMessage("You can post feedback and vote on cool ideas just by visiting the Crumby forum! It will only take a second, and it will definitely help to improve your browsing experience!").setPositiveButton("Visit the forum", new C00632()).setNegativeButton("Not right now", new C00621()).setCancelable(GalleryViewer.$assertionsDisabled).show();
            Editor editor = PreferenceManager.getDefaultSharedPreferences(GalleryViewer.this.getApplicationContext()).edit();
            GalleryViewer.sawFeedbackAlert = GalleryViewer.BING_SEARCH;
            editor.putBoolean("sawFeedbackAlert", GalleryViewer.BING_SEARCH);
            editor.commit();
        }
    }

    public enum STORE {
        GOOGLE_PLAY("http://i.getcrumby.com/play_update"),
        AMAZON("http://i.getcrumby.com/amazon_update");
        
        public String link;

        private STORE(String link) {
            this.link = link;
        }
    }

    static {
        $assertionsDisabled = !GalleryViewer.class.desiredAssertionStatus() ? BING_SEARCH : $assertionsDisabled;
        USER_JUST_INSTALLED = $assertionsDisabled;
        PREVIOUS_VERSION = null;
        BUILD_FOR_STORE = STORE.AMAZON;
    }

    public static boolean isUserNew() {
        return BING_SEARCH;
    }

    @Subscribe
    public void silentUrlRedirect(SilentUrlRedirectEvent redirectEvent) {
        this.currentPage.redirectFragment(redirectEvent.silentRedirectUrl, redirectEvent.keyImage);
    }

    @Subscribe
    public void onWebsiteSettingsChanged(WebsiteSettingsChangedEvent event) {
        if (this.currentPage != null) {
            GalleryViewerFragment fragment = this.currentPage.getActiveFragment();
            if (fragment != null && fragment.getImage() != null && UnsupportedUrlFragment.DISPLAY_NAME.equals(fragment.getImage().getLinkUrl())) {
                reloadCurrentFragment(null);
            }
        }
    }

    public static String getBlacklist() {
        return getBlacklist(9999);
    }

    public static String getBlacklist(int length) {
        String tagString = MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
        if (length <= 0) {
            return tagString;
        }
        if (BLACK_LISTED_TAGS.isEmpty()) {
            return tagString;
        }
        Iterator i$ = BLACK_LISTED_TAGS.iterator();
        while (i$.hasNext()) {
            String tag = (String) i$.next();
            if (!tag.equals("_")) {
                tagString = tagString + "-" + tag + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                length--;
                if (length == 0) {
                    break;
                }
            }
        }
        tagString = tagString.substring(0, tagString.length() - 1);
        CrDb.m0d("gallery viewer", "blacklist " + tagString);
        return tagString;
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getData() != null) {
            loadNewGalleryPageFromUrl(intent.getDataString(), null, null);
        }
    }

    @Subscribe
    public void launchPager(PagerEvent pagerEvent) {
    }

    @Subscribe
    public void reloadCurrentFragment(ReloadFragmentEvent reload) {
        if (canNavigate()) {
            this.currentPage.refresh();
        }
    }

    private void hideProgress() {
        this.progressIndicator.setAlpha(0.0f);
        this.omnibarView.pageNotLoading();
    }

    private void resetProgress(boolean instant) {
        if (this.progressIndicator != null) {
            this.progressIndicator.setPadding(0, 0, 0, 0);
            if (instant) {
                hideProgress();
            } else {
                this.progressHandler.postDelayed(this.fadeProgress, 1000);
            }
        }
    }

    public void pagingToNewFragment() {
        resetProgress(BING_SEARCH);
        this.multiSelectBar.clear();
    }

    public void progressChange(float progress) {
        if ($assertionsDisabled || (progress <= PROGRESS_COMPLETED && progress >= PROGRESS_STARTED)) {
            if (this.progressIndicator == null) {
                this.progressIndicator = (RelativeLayout) findViewById(C0065R.id.progress_indicator);
            }
            this.progressHandler.removeCallbacks(this.fadeProgress);
            if (progress != PROGRESS_COMPLETED) {
                this.progressIndicator.setAlpha(PROGRESS_COMPLETED);
                this.progressIndicator.setVisibility(0);
                this.progressIndicator.setPadding(0, 0, (int) (((float) this.progressIndicator.getWidth()) * (PROGRESS_COMPLETED - progress)), 0);
                this.omnibarView.pageLoading();
                return;
            }
            resetProgress($assertionsDisabled);
            return;
        }
        throw new AssertionError();
    }

    @Subscribe
    public void stopLoadingCurrentFragment(StopLoadingFragmentEvent stop) {
        if (this.progressIndicator != null) {
            this.progressIndicator.setAlpha(0.0f);
        }
        this.currentPage.stopLoading();
    }

    @Subscribe
    public void urlChange(UrlChangeEvent urlChangeEvent) {
        if (urlChangeEvent.clearPrevious) {
            this.omnibarView.clearBreadcrumbs();
        }
        if (canNavigate()) {
            loadNewGalleryPageFromUrl(urlChangeEvent.url, urlChangeEvent.bundle, urlChangeEvent.producer);
        }
    }

    private void loadNewGalleryPageFromUrl(String url, Bundle bundle, GalleryProducer producer) {
        hideOmniSearch();
        clearRedo();
        lockRedoButton();
        switchToPage(null, bundle, url, producer);
    }

    private void startCookieStores() {
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().setAcceptCookie(BING_SEARCH);
        CookieManager.setAcceptFileSchemeCookies(BING_SEARCH);
        WebkitCookieManagerProxy coreCookieManager = new WebkitCookieManagerProxy(null, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(coreCookieManager);
        CrumbyApp.getHttpClient().setCookieHandler(coreCookieManager);
    }

    private void copyScripts() {
        if (!USER_JUST_INSTALLED) {
            try {
            } catch (IOException e) {
                Analytics.INSTANCE.newException(new Exception("COULD NOT COPY SCRIPTS, " + e.getMessage(), e));
                e.printStackTrace();
                return;
            }
        }
        String namespace = "js/" + "production";
        String[] scripts = getAssets().list(namespace);
        CrDb.logTime("gallery viewer", "script copy", BING_SEARCH);
        for (String script : scripts) {
            InputStream in = getAssets().open(namespace + DeviceFragment.REGEX_BASE + script);
            OutputStream out = openFileOutput(script, 0);
            byte[] buf = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
            while (true) {
                int len = in.read(buf);
                if (len <= 0) {
                    break;
                }
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
        CrDb.logTime("gallery viewer", "script copy", $assertionsDisabled);
    }

    public void onCreate(Bundle savedInstanceState) {
        CrDb.newActivity();
        new Thread(new C00531()).start();
        super.onCreate(savedInstanceState);
        Tapstream.create(getApplication(), "crumby", "AbsCdo49R1ii5LFTcHoNKQ", new Config());
        startCookieStores();
        setContentView(C0065R.layout.gallery_viewer);
        setUpActionBar();
        USER_JUST_INSTALLED = checkUserJustInstalled();
        FragmentRouter.INSTANCE.initialize(getBaseContext());
        this.redo = new Stack();
        this.searchGalleriesShowing = $assertionsDisabled;
        this.expandActionBarButton = (ExpandActionBarButton) findViewById(C0065R.id.expand_action_bar);
        this.expandActionBarButton.setOnClickListener(new C00542());
        copyScripts();
        String home = UnsupportedUrlFragment.DISPLAY_NAME;
        if (IsInTest()) {
            home = "http://retard.com";
        }
        loadNewGalleryPageFromUrl(home, null, null);
        this.onboarding = BING_SEARCH;
        hideClutter();
        forceShowClutter();
        this.progressHandler = new Handler();
        this.fadeProgress = new C00553();
        launchPager(null);
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        lockUndoButton();
        autoLogin($assertionsDisabled);
        ImageDownloadManager.INSTANCE.setDownloadPath(PreferenceManager.getDefaultSharedPreferences(this).getString(CRUMBY_DOWNLOAD_DIRECTORY, null));
        BLACK_LISTED_TAGS = new LinkedHashSet();
        BLACK_LISTED_TAGS.addAll(PreferenceManager.getDefaultSharedPreferences(getActivity()).getStringSet(TAG_BLACKLIST_KEY, new LinkedHashSet()));
        UniversalInterpreterManager.INSTANCE.initialize(this);
        FragmentRouter.INSTANCE.getAllRegexUrls();
        if (IsInTest()) {
            testGalleries(new Handler(), null, 0);
        }
    }

    private boolean onboardNewUser() {
        if (!isUserNew() || this.onboarding) {
            return $assertionsDisabled;
        }
        this.onboarding = BING_SEARCH;
        findViewById(C0065R.id.action_bar).setVisibility(8);
        getLayoutInflater().inflate(C0065R.layout.onboard_new_user_page, (ViewGroup) findViewById(C0065R.id.browser_window).getParent());
        BaseAdapter onboard = new C00564();
        ((GridView) findViewById(C0065R.id.choose_content_galleries_grid)).setAdapter(onboard);
        onboard.notifyDataSetChanged();
        findViewById(C0065R.id.user_done_onboarding).setOnClickListener(new C00585());
        ((GridView) findViewById(C0065R.id.choose_content_galleries_grid)).setChoiceMode(2);
        return BING_SEARCH;
    }

    public static boolean IsInTest() {
        return $assertionsDisabled;
    }

    private void testGalleries(Handler handler, String url, int index) {
        handler.postDelayed(new C00596(url, index, handler), 5000);
    }

    private boolean checkUserJustInstalled() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            PREVIOUS_VERSION = preferences.getString("crumbyVersion", null);
            preferences.edit().putString("crumbyVersion", version).commit();
            if (PREVIOUS_VERSION == null || PREVIOUS_VERSION.equals(version)) {
                if (PREVIOUS_VERSION == null) {
                    return BING_SEARCH;
                }
                return $assertionsDisabled;
            }
            Analytics.INSTANCE.newEvent(AnalyticsCategories.MISCELLANEOUS, "show changelog", null);
            new Builder(getActivity()).setTitle("Recent Features & Fixes").setView((ChangeLogListView) ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(C0065R.layout.changelog, null)).setPositiveButton("Ok", new C00607()).create().show();
            return BING_SEARCH;
        } catch (NameNotFoundException e) {
            Analytics.INSTANCE.newException(e);
            e.printStackTrace();
        }
    }

    private void autoLoginToSite(boolean showToast, Bundle bundle, Handler handler, String accountType) {
        if (showToast) {
            Toast toast = Toast.makeText(getApplicationContext(), UnsupportedUrlFragment.DISPLAY_NAME, 1);
            toast.setGravity(48, 0, 0);
            TextView view = (TextView) View.inflate(getApplicationContext(), C0065R.layout.toast_alert_wait, null);
            view.setText("Re-logging in to  " + accountType + "...");
            toast.setView(view);
            toast.show();
        }
        AccountManager.get(this).getAuthTokenByFeatures(accountType, accountType, null, this, bundle, bundle, new C00618(accountType, showToast), handler);
    }

    public void autoLogin(boolean showToast) {
        if (!this.autoLoggingIn) {
            this.autoLoggingIn = BING_SEARCH;
            Bundle bundle = new Bundle();
            bundle.putBoolean(DO_NOT_CREATE_ACCOUNT, BING_SEARCH);
            bundle.putBoolean(FORCE_RELOGIN, BING_SEARCH);
            HandlerThread thread = new HandlerThread("MyHandlerThread");
            thread.start();
            Handler handler = new Handler(thread.getLooper());
            autoLoginToSite(showToast, bundle, handler, DerpibooruFragment.BREADCRUMB_NAME);
            autoLoginToSite(showToast, bundle, handler, FurAffinityFragment.ACCOUNT_TYPE);
        }
    }

    private void showFeedbackAlert() {
        if (sawFeedbackAlert) {
            CrDb.m0d("viewer", "saw feedback alert");
            return;
        }
        CrDb.m0d("viewer", "did not see feedback alert");
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("sawFeedbackAlert", $assertionsDisabled)) {
            sawFeedbackAlert = BING_SEARCH;
            return;
        }
        if (this.feedback == null) {
            this.feedback = new Handler();
            this.feeedbackRunnable = new C00649();
        }
        this.feedback.removeCallbacks(this.feeedbackRunnable);
        this.feedback.postDelayed(this.feeedbackRunnable, 300000);
    }

    protected void onResume() {
        if (this.currentPage != null) {
            this.currentPage.dispatchWaitResume();
        }
        super.onResume();
        BusProvider.BUS.get().register(this);
        showFeedbackAlert();
    }

    protected void onPause() {
        super.onPause();
        BusProvider.BUS.get().unregister(this);
        if (this.feedback != null) {
            this.feedback.removeCallbacks(this.feeedbackRunnable);
        }
    }

    protected void onDestroy() {
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        Analytics.INSTANCE.end();
        super.onDestroy();
        System.exit(0);
    }

    private void lockUndoButton() {
        this.undoButton.setAlpha(0.4f);
        this.undoButton.setEnabled($assertionsDisabled);
    }

    private void unlockUndoButton() {
        this.undoButton.setAlpha(PROGRESS_COMPLETED);
        this.undoButton.setEnabled(BING_SEARCH);
    }

    private void lockRedoButton() {
        this.redoButton.setAlpha(0.4f);
        this.redoButton.setEnabled($assertionsDisabled);
    }

    private void unlockRedoButton() {
        this.redoButton.setAlpha(PROGRESS_COMPLETED);
        this.redoButton.setEnabled(BING_SEARCH);
    }

    public Activity getActivity() {
        return this;
    }

    private void setUpActionBar() {
        ActionBar actionBar = getActionBar();
        View customActionBarView = findViewById(C0065R.id.action_bar);
        actionBar.hide();
        this.undoButton = (ImageButton) customActionBarView.findViewById(C0065R.id.undo);
        this.undoButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GalleryViewer.this.undo();
            }
        });
        this.redoButton = (ImageButton) customActionBarView.findViewById(C0065R.id.redo);
        this.redoButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GalleryViewer.this.redo();
            }
        });
        this.undoRedoContainer = customActionBarView.findViewById(C0065R.id.undo_redo_container);
        this.mainMenu = (MainMenu) findViewById(C0065R.id.main_menu);
        this.mainMenu.initialize(getActivity(), findViewById(C0065R.id.browser_window), customActionBarView, findViewById(C0065R.id.menu_modal));
        this.omnibarView = (OmnibarView) customActionBarView.findViewById(C0065R.id.omnibar_view);
        this.omnibarView.initialize(this);
        this.multiSelectBar = (MultiSelectBar) findViewById(C0065R.id.multi_select_bar);
        this.multiSelectBar.initialize(this);
        ImageDownloadManager.INSTANCE.initialize(this.mainMenu);
    }

    public void showOmniSearch() {
        this.searchGalleriesShowing = BING_SEARCH;
        if (this.currentPage != null) {
            this.currentPage.omniSearchIsShowing();
        }
        this.redoButton.setVisibility(8);
        this.undoButton.setVisibility(8);
        this.mainMenu.hideButton();
        this.omnibarView.showOmniSearchModal();
        if (!shownGallerySearchTutorial) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            if (prefs.getBoolean("shownGallerySearchTutorial", $assertionsDisabled)) {
                shownGallerySearchTutorial = BING_SEARCH;
                return;
            }
            ViewGroup tutorial = (ViewGroup) findViewById(C0065R.id.search_form);
            if (tutorial.getChildCount() != 0) {
                tutorial.performClick();
                tutorial.requestFocus();
                Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "omnibar gallery search", this.currentPage.getActiveUrl());
                TooltipManager.getInstance(this).create(10).anchor(findViewById(C0065R.id.search_form_submit), Gravity.BOTTOM).closePolicy(ClosePolicy.None, 5000).activateDelay(500).withStyleId(C0065R.style.tutorial_hint).text("To search the current image gallery, try using this form instead of the omnibar.").maxWidth(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE).show();
                shownGallerySearchTutorial = BING_SEARCH;
            }
            prefs.edit().putBoolean("shownGallerySearchTutorial", BING_SEARCH).commit();
        }
    }

    public void hideOmniSearch() {
        if (this.currentPage != null) {
            this.currentPage.omniSearchIsNotShowing();
            this.searchGalleriesShowing = $assertionsDisabled;
            this.redoButton.setVisibility(0);
            this.undoButton.setVisibility(0);
            this.mainMenu.showButton();
            this.omnibarView.hideModals();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0065R.menu.main, menu);
        return BING_SEARCH;
    }

    private boolean canNavigate() {
        return (this.currentPage == null || this.currentPage.isRemoving() || !this.currentPage.isVisible()) ? $assertionsDisabled : BING_SEARCH;
    }

    public void redo() {
        if (this.redo.size() != 0 && canNavigate()) {
            GalleryPage page = (GalleryPage) this.redo.pop();
            Analytics.INSTANCE.newNavigationEvent("redo", page.getTag() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + page.getActiveUrl());
            CrDb.m0d("viewer activity", "redo " + page.getTag());
            page.getArguments().putString(PlusShare.KEY_CALL_TO_ACTION_URL, page.getActiveUrl());
            page.resetFocusToSavedRedoPosition();
            switchToPage(page, page.getArguments(), page.getActiveUrl(), null);
            if (this.redo.size() == 0) {
                lockRedoButton();
            }
        }
    }

    private void addCurrentPageToRedoStack() {
        FragmentManager fm = getFragmentManager();
        String tag = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName();
        CrDb.m0d("viewer activity", "adding to redo stack:" + tag);
        GalleryPage fragment = (GalleryPage) fm.findFragmentByTag(tag);
        fragment.setSavedRedoFocus();
        fragment.onDestroyView();
        fragment.removeNestedFragments();
        this.redo.push(fragment);
        unlockRedoButton();
    }

    public boolean undo() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() <= 1 || !canNavigate()) {
            return $assertionsDisabled;
        }
        addCurrentPageToRedoStack();
        String tag = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 2).getName();
        this.currentPage.discard();
        this.currentPage = (GalleryPage) fm.findFragmentByTag(tag);
        GalleryViewerFragment fragment = FragmentRouter.INSTANCE.getGalleryFragmentInstance(this.currentPage.getActiveUrl());
        this.currentPage.setInitialFragment(fragment);
        updateBreadcrumbs(fragment);
        new Handler().postDelayed(new AnonymousClass12(fm), 25);
        if (fm.getBackStackEntryCount() <= 2) {
            lockUndoButton();
        }
        Analytics.INSTANCE.newNavigationEvent("undo", this.currentPage.getActiveUrl());
        forceShowClutter();
        return BING_SEARCH;
    }

    public void onBackPressed() {
        if (this.mainMenu.isShowing()) {
            this.mainMenu.hide();
        } else if (this.searchGalleriesShowing || this.omnibarView.galleryPanelShowing()) {
            hideOmniSearch();
        } else if (this.currentPage != null && this.currentPage.undo()) {
        } else {
            if (this.multiSelectBar.hasImages()) {
                this.multiSelectBar.clear();
            } else if (!undo()) {
                if (onboardNewUser()) {
                    getFragmentManager().popBackStack();
                    return;
                }
                BusProvider.BUS.get().post(new TerminateEvent());
                finish();
            }
        }
    }

    private void memCal() {
        MemoryInfo mi = new MemoryInfo();
        ((ActivityManager) getSystemService("activity")).getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576;
        if (this.startMegs == 0) {
            this.startMegs = availableMegs;
        }
        CrDb.m0d("MEMORY REMAINING", availableMegs + UnsupportedUrlFragment.DISPLAY_NAME);
        if (this.startMegs / 3 > availableMegs) {
            CrDb.m0d("MEMORY REMAINING", "DUMPING");
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
    }

    private void switchToPage(GalleryPage page, Bundle bundle, String url, GalleryProducer producer) {
        if (page == null) {
            page = new GalleryPage();
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (this.currentPage != null) {
            this.currentPage.discard();
        }
        if (this.multiSelectBar != null) {
            this.multiSelectBar.clear();
        }
        showOmnibar(BING_SEARCH);
        bundle.putString(PlusShare.KEY_CALL_TO_ACTION_URL, url);
        page.setArguments(bundle);
        GalleryViewerFragment fragment = page.getActiveFragment();
        if (producer != null) {
            page.setProducer(producer);
        }
        if (fragment == null) {
            fragment = FragmentRouter.INSTANCE.getGalleryFragmentInstance(url);
            page.setInitialFragment(fragment);
        }
        GalleryPage lastPage = this.currentPage;
        this.currentPage = page;
        updateBreadcrumbs(fragment);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (lastPage != null) {
            transaction.detach(lastPage);
        }
        String tag = UnsupportedUrlFragment.DISPLAY_NAME;
        if (page.getTag() != null) {
            tag = page.getTag();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            int i = this.fragmentCounter;
            this.fragmentCounter = i + 1;
            tag = stringBuilder.append(i).append(UnsupportedUrlFragment.DISPLAY_NAME).toString();
        }
        transaction.add(C0065R.id.gallery_view, page, tag);
        transaction.addToBackStack(tag);
        findViewById(C0065R.id.gallery_view).animate().alpha(0.0f).setDuration(10).setListener(new AnonymousClass13(transaction));
        if (lastPage != null) {
            lastPage.removeNestedFragments();
        }
        this.currentPage = page;
        showClutter();
        unlockUndoButton();
    }

    private void clearRedo() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Iterator i$ = this.redo.iterator();
        while (i$.hasNext()) {
            transaction.remove((GalleryPage) i$.next());
        }
        transaction.commit();
        this.redo.clear();
    }

    public void hideClutter() {
    }

    public void showClutter() {
        forceShowClutter();
    }

    public void forceShowClutter() {
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int screenSize = newConfig.screenLayout & 15;
        if (screenSize == 4 || screenSize == 3 || (screenSize == 2 && getResources().getConfiguration().orientation == 2)) {
            this.undoRedoContainer.setVisibility(0);
        } else {
            this.undoRedoContainer.setVisibility(8);
        }
    }

    public void alterBreadcrumbPath(GalleryViewerFragment fragment) {
        this.omnibarView.update(fragment, BING_SEARCH);
    }

    public void updateBreadcrumbs(GalleryViewerFragment fragment) {
        this.omnibarView.update(fragment, $assertionsDisabled);
    }

    public void overrideBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        this.omnibarView.override(breadcrumbs);
    }

    public MultiSelect getMultiSelect() {
        return this.multiSelectBar;
    }

    public void redrawPage() {
        if (this.currentPage != null) {
            this.currentPage.redraw();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 82) {
            return super.onKeyDown(keyCode, event);
        }
        this.mainMenu.toggleSettings();
        return BING_SEARCH;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return $assertionsDisabled;
    }

    public void authLoginPromptIfNeeded(String accountType, AccountManagerCallback<Bundle> callback) {
        AccountManager.get(getActivity()).getAuthTokenByFeatures(accountType, accountType, null, getActivity(), null, null, callback, null);
    }

    public void checkLogin(String accountType, AccountManagerCallback<Bundle> callback) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(DO_NOT_CREATE_ACCOUNT, BING_SEARCH);
        AccountManager.get(getActivity()).getAuthTokenByFeatures(accountType, accountType, null, getActivity(), bundle, bundle, callback, null);
    }

    public void onSelectDirectory(String path) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(CRUMBY_DOWNLOAD_DIRECTORY, path).commit();
        ImageDownloadManager.INSTANCE.setDownloadPath(path);
        Toast toast = Toast.makeText(getApplicationContext(), UnsupportedUrlFragment.DISPLAY_NAME, 1);
        toast.setGravity(5, 0, 0);
        TextView view = (TextView) View.inflate(getApplicationContext(), C0065R.layout.toast_alert_success, null);
        view.setText("Crumby will now save images into \"" + path + "/crumby\" ");
        toast.setView(view);
        toast.show();
    }

    public void onCancelChooser() {
        if (ImageDownloadManager.INSTANCE.clearDeferredImageDownloadAndCheckIfDownloadPathIsNull()) {
            Toast toast = Toast.makeText(getApplicationContext(), UnsupportedUrlFragment.DISPLAY_NAME, 1);
            toast.setGravity(5, 0, 0);
            TextView view = (TextView) View.inflate(getApplicationContext(), C0065R.layout.toast_alert_error, null);
            view.setText("You need to choose a download folder to save images!");
            toast.setView(view);
            toast.show();
        }
    }

    public void hideOmnibar() {
        if (this.currentPage != null) {
            this.currentPage.hideTitleStrip();
        }
        if (this.omnibarShowing) {
            toggleHideyBar();
            this.omnibarShowing = $assertionsDisabled;
            View view = findViewById(C0065R.id.action_bar);
            view.clearAnimation();
            view.animate().y((float) (-view.getMeasuredHeight()));
        }
    }

    private void showOmnibar(boolean instant) {
        if (!this.omnibarShowing) {
            toggleHideyBar();
            this.omnibarShowing = BING_SEARCH;
            View view = findViewById(C0065R.id.action_bar);
            if (instant) {
                view.setY(0.0f);
            } else {
                view.clearAnimation();
                view.animate().y(0.0f);
            }
            if (this.currentPage != null) {
                this.currentPage.showTitleStrip();
            }
        }
    }

    public void showOmnibar() {
        showOmnibar($assertionsDisabled);
    }

    public void toggleHideyBar() {
    }
}
