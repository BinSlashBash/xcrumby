/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.accounts.AccountManager
 *  android.accounts.AccountManagerCallback
 *  android.accounts.AccountManagerFuture
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.app.ActivityManager
 *  android.app.ActivityManager$MemoryInfo
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Application
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentManager$BackStackEntry
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.preference.PreferenceManager
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.ViewPropertyAnimator
 *  android.webkit.CookieManager
 *  android.webkit.CookieSyncManager
 *  android.widget.BaseAdapter
 *  android.widget.GridView
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 *  android.widget.Toast
 */
package com.crumby;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.animation.Animator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.lib.GalleryImage;
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
import com.google.android.gms.analytics.GoogleAnalytics;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.tapstream.sdk.Config;
import com.tapstream.sdk.Tapstream;
import com.uservoice.uservoicesdk.UserVoice;
import it.gmariotti.changelibs.library.view.ChangeLogListView;
import it.sephiroth.android.library.tooltip.TooltipManager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import net.rdrei.android.dirchooser.DirectoryChooserFragment;

public class GalleryViewer
extends Activity
implements DirectoryChooserFragment.OnFragmentInteractionListener {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final String APP_VERSION = "45";
    public static final boolean BING_SEARCH = true;
    public static LinkedHashSet<String> BLACK_LISTED_TAGS;
    public static final STORE BUILD_FOR_STORE;
    public static final String CRUMBY_DOWNLOAD_DIRECTORY = "crumbyDownloadDirectory";
    public static final String DO_NOT_CREATE_ACCOUNT = "doNotCreateAccount";
    public static final String FORCE_RELOGIN = "forceRelogin";
    public static String PREVIOUS_VERSION;
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

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !GalleryViewer.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
        USER_JUST_INSTALLED = false;
        PREVIOUS_VERSION = null;
        BUILD_FOR_STORE = STORE.AMAZON;
    }

    public static boolean IsInTest() {
        return false;
    }

    static /* synthetic */ void access$400(GalleryViewer galleryViewer, Handler handler, String string2, int n2) {
        galleryViewer.testGalleries(handler, string2, n2);
    }

    private void addCurrentPageToRedoStack() {
        Object object = this.getFragmentManager();
        String string2 = object.getBackStackEntryAt(object.getBackStackEntryCount() - 1).getName();
        CrDb.d("viewer activity", "adding to redo stack:" + string2);
        object = (GalleryPage)object.findFragmentByTag(string2);
        object.setSavedRedoFocus();
        object.onDestroyView();
        object.removeNestedFragments();
        this.redo.push((GalleryPage)object);
        this.unlockRedoButton();
    }

    private void autoLoginToSite(final boolean bl2, Bundle bundle, Handler handler, final String string2) {
        if (bl2) {
            Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"", (int)1);
            toast.setGravity(48, 0, 0);
            TextView textView = (TextView)View.inflate((Context)this.getApplicationContext(), (int)2130903120, (ViewGroup)null);
            textView.setText((CharSequence)("Re-logging in to  " + string2 + "..."));
            toast.setView((View)textView);
            toast.show();
        }
        AccountManager.get((Context)this).getAuthTokenByFeatures(string2, string2, null, (Activity)this, bundle, bundle, (AccountManagerCallback)new AccountManagerCallback<Bundle>(){

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            public void run(AccountManagerFuture<Bundle> account) {
                block5 : {
                    Object object = null;
                    Bundle bundle = (Bundle)account.getResult();
                    account = new Account(bundle.getString("authAccount"), string2);
                    object = AccountManager.get((Context)GalleryViewer.this.getActivity()).getPassword(account);
                    AuthenticatorActivity.getServerAuthenticate(string2).userSignInAndGetAuth(bundle.getString("authAccount"), (String)object, "BULLSHIT");
                    if (bl2) {
                        bundle = Toast.makeText((Context)GalleryViewer.this.getApplicationContext(), (CharSequence)"", (int)1);
                        bundle.setGravity(48, 0, 0);
                        object = (TextView)View.inflate((Context)GalleryViewer.this.getApplicationContext(), (int)2130903119, (ViewGroup)null);
                        object.setText((CharSequence)("You have relogged in to " + string2 + "."));
                        bundle.setView((View)object);
                        bundle.show();
                    }
                    break block5;
                    {
                        catch (Exception exception) {}
                    }
                    catch (Exception exception) {
                        void var2_5;
                        account = object;
                        if (account == null) return;
                        AccountManager.get((Context)GalleryViewer.this.getActivity()).clearPassword(account);
                        object = new Bundle();
                        if (var2_5 instanceof IOException) return;
                        object.putBoolean("ERROR_WITH_ACCOUNT", true);
                        AccountManager.get((Context)GalleryViewer.this.getActivity()).getAuthToken(account, string2, (Bundle)object, GalleryViewer.this.getActivity(), null, null);
                    }
                }
                GalleryViewer.this.autoLoggingIn = false;
            }
        }, handler);
    }

    private boolean canNavigate() {
        if (this.currentPage != null && !this.currentPage.isRemoving() && this.currentPage.isVisible()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean checkUserJustInstalled() {
        Object object;
        block4 : {
            object = PreferenceManager.getDefaultSharedPreferences((Context)this.getApplicationContext());
            try {
                String string2 = this.getPackageManager().getPackageInfo((String)this.getPackageName(), (int)0).versionName;
                PREVIOUS_VERSION = object.getString("crumbyVersion", null);
                object.edit().putString("crumbyVersion", string2).commit();
                if (PREVIOUS_VERSION == null || PREVIOUS_VERSION.equals(string2)) break block4;
                Analytics.INSTANCE.newEvent(AnalyticsCategories.MISCELLANEOUS, "show changelog", null);
                object = (ChangeLogListView)((LayoutInflater)this.getActivity().getSystemService("layout_inflater")).inflate(2130903045, null);
                new AlertDialog.Builder((Context)this.getActivity()).setTitle((CharSequence)"Recent Features & Fixes").setView((View)object).setPositiveButton((CharSequence)"Ok", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n2) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
                return true;
            }
            catch (PackageManager.NameNotFoundException var1_2) {
                Analytics.INSTANCE.newException((Exception)var1_2);
                var1_2.printStackTrace();
                return false;
            }
        }
        object = PREVIOUS_VERSION;
        if (object == null) return true;
        do {
            return false;
            break;
        } while (true);
    }

    private void clearRedo() {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        Iterator<GalleryPage> iterator = this.redo.iterator();
        while (iterator.hasNext()) {
            fragmentTransaction.remove((Fragment)iterator.next());
        }
        fragmentTransaction.commit();
        this.redo.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void copyScripts() {
        if (!USER_JUST_INSTALLED) {
            // empty if block
        }
        String string2 = "js/" + "production";
        String[] arrstring = this.getAssets().list(string2);
        CrDb.logTime("gallery viewer", "script copy", true);
        for (Object object : arrstring) {
            int n2;
            InputStream inputStream = this.getAssets().open(string2 + "/" + (String)object);
            object = this.openFileOutput((String)object, 0);
            byte[] arrby = new byte[1024];
            while ((n2 = inputStream.read(arrby)) > 0) {
                object.write(arrby, 0, n2);
            }
            inputStream.close();
            object.close();
            continue;
        }
        try {
            CrDb.logTime("gallery viewer", "script copy", false);
            return;
        }
        catch (IOException var4_2) {
            Analytics.INSTANCE.newException(new Exception("COULD NOT COPY SCRIPTS, " + var4_2.getMessage(), var4_2));
            var4_2.printStackTrace();
            return;
        }
    }

    public static String getBlacklist() {
        return GalleryViewer.getBlacklist(9999);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static String getBlacklist(int var0) {
        var2_1 = " ";
        if (var0 <= 0) {
            return " ";
        }
        if (GalleryViewer.BLACK_LISTED_TAGS.isEmpty()) {
            return " ";
        }
        var4_2 = GalleryViewer.BLACK_LISTED_TAGS.iterator();
        do lbl-1000: // 3 sources:
        {
            var3_4 = var2_1;
            if (!var4_2.hasNext()) break;
            var3_4 = var4_2.next();
            if (var3_4.equals("_")) ** GOTO lbl-1000
            var3_4 = var2_1 + "-" + var3_4 + " ";
            var1_3 = var0 - 1;
            var2_1 = var3_4;
            var0 = var1_3;
        } while (var1_3 != 0);
        var2_1 = var3_4.substring(0, var3_4.length() - 1);
        CrDb.d("gallery viewer", "blacklist " + var2_1);
        return var2_1;
    }

    private void hideProgress() {
        this.progressIndicator.setAlpha(0.0f);
        this.omnibarView.pageNotLoading();
    }

    public static boolean isUserNew() {
        return true;
    }

    private void loadNewGalleryPageFromUrl(String string2, Bundle bundle, GalleryProducer galleryProducer) {
        this.hideOmniSearch();
        this.clearRedo();
        this.lockRedoButton();
        this.switchToPage(null, bundle, string2, galleryProducer);
    }

    private void lockRedoButton() {
        this.redoButton.setAlpha(0.4f);
        this.redoButton.setEnabled(false);
    }

    private void lockUndoButton() {
        this.undoButton.setAlpha(0.4f);
        this.undoButton.setEnabled(false);
    }

    private void memCal() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager)this.getSystemService("activity")).getMemoryInfo(memoryInfo);
        long l2 = memoryInfo.availMem / 0x100000;
        if (this.startMegs == 0) {
            this.startMegs = l2;
        }
        CrDb.d("MEMORY REMAINING", "" + l2 + "");
        if (this.startMegs / 3 > l2) {
            CrDb.d("MEMORY REMAINING", "DUMPING");
        }
    }

    private boolean onboardNewUser() {
        if (!GalleryViewer.isUserNew() || this.onboarding) {
            return false;
        }
        this.onboarding = true;
        this.findViewById(2131492894).setVisibility(8);
        this.getLayoutInflater().inflate(2130903106, (ViewGroup)this.findViewById(2131493000).getParent());
        BaseAdapter baseAdapter = new BaseAdapter(){
            UserDefaultGallery[] choose;

            public int getCount() {
                return this.choose.length;
            }

            public Object getItem(int n2) {
                return this.choose[n2];
            }

            public long getItemId(int n2) {
                return 0;
            }

            public View getView(int n2, View view, ViewGroup viewGroup) {
                viewGroup = view;
                if (view == null) {
                    viewGroup = GalleryViewer.this.getLayoutInflater().inflate(2130903107, null);
                }
                ((TextView)viewGroup.findViewById(2131493098)).setText((CharSequence)this.choose[n2].name);
                Picasso.with((Context)GalleryViewer.this.getActivity()).load(this.choose[n2].imageUrl).into((ImageView)viewGroup.findViewById(2131493097));
                return viewGroup;
            }

            class UserDefaultGallery {
                String imageUrl;
                String name;

                public UserDefaultGallery(String string2, String string3) {
                    this.name = string2;
                    this.imageUrl = string3;
                }
            }

        };
        ((GridView)this.findViewById(2131493095)).setAdapter((ListAdapter)baseAdapter);
        baseAdapter.notifyDataSetChanged();
        this.findViewById(2131493096).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ((ViewGroup)GalleryViewer.this.findViewById(2131493094)).animate().x((float)(- GalleryViewer.this.getResources().getDisplayMetrics().widthPixels)).setListener(new Animator.AnimatorListener(){

                    public void onAnimationCancel(Animator animator) {
                    }

                    public void onAnimationEnd(Animator animator) {
                        ((ViewGroup)GalleryViewer.this.findViewById(2131493094).getParent()).removeView(GalleryViewer.this.findViewById(2131493094));
                        GalleryViewer.this.findViewById(2131492894).setVisibility(0);
                        GalleryViewer.this.loadNewGalleryPageFromUrl("", null, null);
                        GalleryViewer.this.onboarding = false;
                    }

                    public void onAnimationRepeat(Animator animator) {
                    }

                    public void onAnimationStart(Animator animator) {
                    }
                });
            }

        });
        ((GridView)this.findViewById(2131493095)).setChoiceMode(2);
        return true;
    }

    private void resetProgress(boolean bl2) {
        if (this.progressIndicator == null) {
            return;
        }
        this.progressIndicator.setPadding(0, 0, 0, 0);
        if (!bl2) {
            this.progressHandler.postDelayed(this.fadeProgress, 1000);
            return;
        }
        this.hideProgress();
    }

    private void setUpActionBar() {
        ActionBar actionBar = this.getActionBar();
        View view = this.findViewById(2131492894);
        actionBar.hide();
        this.undoButton = (ImageButton)view.findViewById(2131493116);
        this.undoButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                GalleryViewer.this.undo();
            }
        });
        this.redoButton = (ImageButton)view.findViewById(2131493117);
        this.redoButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                GalleryViewer.this.redo();
            }
        });
        this.undoRedoContainer = view.findViewById(2131493118);
        this.mainMenu = (MainMenu)this.findViewById(2131493047);
        this.mainMenu.initialize(this.getActivity(), this.findViewById(2131493000), view, this.findViewById(2131493004));
        this.omnibarView = (OmnibarView)view.findViewById(2131492896);
        this.omnibarView.initialize(this);
        this.multiSelectBar = (MultiSelectBar)this.findViewById(2131493065);
        this.multiSelectBar.initialize(this);
        ImageDownloadManager.INSTANCE.initialize(this.mainMenu);
    }

    private void showFeedbackAlert() {
        if (!sawFeedbackAlert) {
            CrDb.d("viewer", "did not see feedback alert");
            if (PreferenceManager.getDefaultSharedPreferences((Context)this.getApplicationContext()).getBoolean("sawFeedbackAlert", false)) {
                sawFeedbackAlert = true;
                return;
            }
            if (this.feedback == null) {
                this.feedback = new Handler();
                this.feeedbackRunnable = new Runnable(){

                    @Override
                    public void run() {
                        new AlertDialog.Builder((Context)GalleryViewer.this.getActivity()).setTitle((CharSequence)"Any questions, concerns, or requests?").setMessage((CharSequence)"You can post feedback and vote on cool ideas just by visiting the Crumby forum! It will only take a second, and it will definitely help to improve your browsing experience!").setPositiveButton((CharSequence)"Visit the forum", new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialogInterface, int n2) {
                                Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "idea alert", "interested");
                                Analytics.INSTANCE.newNavigationEvent("uservoice", "help modal");
                                UserVoice.launchForum((Context)GalleryViewer.this.getActivity());
                            }
                        }).setNegativeButton((CharSequence)"Not right now", new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialogInterface, int n2) {
                                Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "idea alert", "not interested");
                            }
                        }).setCancelable(false).show();
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)GalleryViewer.this.getApplicationContext()).edit();
                        sawFeedbackAlert = true;
                        editor.putBoolean("sawFeedbackAlert", true);
                        editor.commit();
                    }

                };
            }
            this.feedback.removeCallbacks(this.feeedbackRunnable);
            this.feedback.postDelayed(this.feeedbackRunnable, 300000);
            return;
        }
        CrDb.d("viewer", "saw feedback alert");
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void showOmnibar(boolean bl2) {
        if (this.omnibarShowing) {
            return;
        }
        this.toggleHideyBar();
        this.omnibarShowing = true;
        View view = this.findViewById(2131492894);
        if (!bl2) {
            view.clearAnimation();
            view.animate().y(0.0f);
        } else {
            view.setY(0.0f);
        }
        if (this.currentPage == null) return;
        this.currentPage.showTitleStrip();
    }

    private void startCookieStores() {
        CookieSyncManager.createInstance((Context)this);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.setAcceptFileSchemeCookies((boolean)true);
        WebkitCookieManagerProxy webkitCookieManagerProxy = new WebkitCookieManagerProxy(null, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(webkitCookieManagerProxy);
        CrumbyApp.getHttpClient().setCookieHandler(webkitCookieManagerProxy);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void switchToPage(GalleryPage object, Bundle object2, String string2, GalleryProducer galleryProducer) {
        Object object3 = object;
        if (object == null) {
            object3 = new GalleryPage();
        }
        object = object2;
        if (object2 == null) {
            object = new Bundle();
        }
        if (this.currentPage != null) {
            this.currentPage.discard();
        }
        if (this.multiSelectBar != null) {
            this.multiSelectBar.clear();
        }
        this.showOmnibar(true);
        object.putString("url", string2);
        object3.setArguments((Bundle)object);
        object2 = object3.getActiveFragment();
        if (galleryProducer != null) {
            object3.setProducer(galleryProducer);
        }
        object = object2;
        if (object2 == null) {
            object = FragmentRouter.INSTANCE.getGalleryFragmentInstance(string2);
            object3.setInitialFragment((GalleryViewerFragment)object);
        }
        object2 = this.currentPage;
        this.currentPage = object3;
        this.updateBreadcrumbs((GalleryViewerFragment)object);
        string2 = this.getFragmentManager().beginTransaction();
        if (object2 != null) {
            string2.detach((Fragment)object2);
        }
        if (object3.getTag() != null) {
            object = object3.getTag();
        } else {
            object = new StringBuilder();
            int n2 = this.fragmentCounter;
            this.fragmentCounter = n2 + 1;
            object = object.append(n2).append("").toString();
        }
        string2.add(2131493001, (Fragment)object3, (String)object);
        string2.addToBackStack((String)object);
        this.findViewById(2131493001).animate().alpha(0.0f).setDuration(10).setListener(new Animator.AnimatorListener((FragmentTransaction)string2){
            final /* synthetic */ FragmentTransaction val$transaction;

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                animator = GalleryViewer.this.findViewById(2131493001);
                animator.setAlpha(1.0f);
                animator.animate().setListener(null);
                this.val$transaction.commit();
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        if (object2 != null) {
            object2.removeNestedFragments();
        }
        this.currentPage = object3;
        this.showClutter();
        this.unlockUndoButton();
    }

    private void testGalleries(final Handler handler, final String string2, final int n2) {
        handler.postDelayed(new Runnable(){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            @Override
            public void run() {
                var5_1 = FragmentRouter.INSTANCE.getAllWebsiteUrls();
                var1_2 = 0;
                var4_3 = string2;
                var6_4 = var5_1.iterator();
                do {
                    var2_5 = var1_2;
                    var3_6 = var4_3;
                    if (!var6_4.hasNext()) ** GOTO lbl12
                    var3_6 = var6_4.next();
                    if (var1_2 != 0 || string2 == null) {
                        var2_5 = 0;
lbl12: // 2 sources:
                        if (var2_5 != 0) {
                            var3_6 = var5_1.get(0);
                        }
                        var1_2 = n2 + 1;
                        Log.e((String)("" + var1_2 + ""), (String)var3_6);
                        GalleryViewer.access$200(GalleryViewer.this, var3_6, null, null);
                        GalleryViewer.access$400(GalleryViewer.this, handler, var3_6, var1_2);
                        return;
                    }
                    if (!var3_6.equals(string2)) continue;
                    var1_2 = 1;
                } while (true);
            }
        }, 5000);
    }

    private void unlockRedoButton() {
        this.redoButton.setAlpha(1.0f);
        this.redoButton.setEnabled(true);
    }

    private void unlockUndoButton() {
        this.undoButton.setAlpha(1.0f);
        this.undoButton.setEnabled(true);
    }

    public void alterBreadcrumbPath(GalleryViewerFragment galleryViewerFragment) {
        this.omnibarView.update(galleryViewerFragment, true);
    }

    public void authLoginPromptIfNeeded(String string2, AccountManagerCallback<Bundle> accountManagerCallback) {
        AccountManager.get((Context)this.getActivity()).getAuthTokenByFeatures(string2, string2, null, this.getActivity(), null, null, accountManagerCallback, null);
    }

    public void autoLogin(boolean bl2) {
        if (this.autoLoggingIn) {
            return;
        }
        this.autoLoggingIn = true;
        Bundle bundle = new Bundle();
        bundle.putBoolean("doNotCreateAccount", true);
        bundle.putBoolean("forceRelogin", true);
        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        handlerThread = new Handler(handlerThread.getLooper());
        this.autoLoginToSite(bl2, bundle, (Handler)handlerThread, "derpibooru");
        this.autoLoginToSite(bl2, bundle, (Handler)handlerThread, "furaffinity");
    }

    public void checkLogin(String string2, AccountManagerCallback<Bundle> accountManagerCallback) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("doNotCreateAccount", true);
        AccountManager.get((Context)this.getActivity()).getAuthTokenByFeatures(string2, string2, null, this.getActivity(), bundle, bundle, accountManagerCallback, null);
    }

    public void forceShowClutter() {
    }

    public Activity getActivity() {
        return this;
    }

    public MultiSelect getMultiSelect() {
        return this.multiSelectBar;
    }

    public void hideClutter() {
    }

    public void hideOmniSearch() {
        if (this.currentPage == null) {
            return;
        }
        this.currentPage.omniSearchIsNotShowing();
        this.searchGalleriesShowing = false;
        this.redoButton.setVisibility(0);
        this.undoButton.setVisibility(0);
        this.mainMenu.showButton();
        this.omnibarView.hideModals();
    }

    public void hideOmnibar() {
        if (this.currentPage != null) {
            this.currentPage.hideTitleStrip();
        }
        if (!this.omnibarShowing) {
            return;
        }
        this.toggleHideyBar();
        this.omnibarShowing = false;
        View view = this.findViewById(2131492894);
        view.clearAnimation();
        view.animate().y((float)(- view.getMeasuredHeight()));
    }

    @Subscribe
    public void launchPager(PagerEvent pagerEvent) {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onBackPressed() {
        if (this.mainMenu.isShowing()) {
            this.mainMenu.hide();
            return;
        }
        if (this.searchGalleriesShowing || this.omnibarView.galleryPanelShowing()) {
            this.hideOmniSearch();
            return;
        }
        if (this.currentPage != null && this.currentPage.undo()) return;
        if (this.multiSelectBar.hasImages()) {
            this.multiSelectBar.clear();
            return;
        }
        if (this.undo()) return;
        {
            if (this.onboardNewUser()) {
                this.getFragmentManager().popBackStack();
                return;
            }
        }
        BusProvider.BUS.get().post(new TerminateEvent());
        this.finish();
    }

    @Override
    public void onCancelChooser() {
        if (ImageDownloadManager.INSTANCE.clearDeferredImageDownloadAndCheckIfDownloadPathIsNull()) {
            Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"", (int)1);
            toast.setGravity(5, 0, 0);
            TextView textView = (TextView)View.inflate((Context)this.getApplicationContext(), (int)2130903118, (ViewGroup)null);
            textView.setText((CharSequence)"You need to choose a download folder to save images!");
            toast.setView((View)textView);
            toast.show();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int n2 = configuration.screenLayout & 15;
        if (n2 == 4 || n2 == 3 || n2 == 2 && this.getResources().getConfiguration().orientation == 2) {
            this.undoRedoContainer.setVisibility(0);
            return;
        }
        this.undoRedoContainer.setVisibility(8);
    }

    public void onCreate(Bundle object) {
        CrDb.newActivity();
        new Thread(new Runnable(){

            @Override
            public void run() {
                do {
                    try {
                        do {
                            Thread.sleep(10000);
                            ((GalleryViewer)GalleryViewer.this.getActivity()).memCal();
                        } while (true);
                    }
                    catch (InterruptedException var1_1) {
                        var1_1.printStackTrace();
                        continue;
                    }
                    break;
                } while (true);
            }
        }).start();
        super.onCreate((Bundle)object);
        object = new Config();
        Tapstream.create(this.getApplication(), "crumby", "AbsCdo49R1ii5LFTcHoNKQ", (Config)object);
        this.startCookieStores();
        this.setContentView(2130903081);
        this.setUpActionBar();
        USER_JUST_INSTALLED = this.checkUserJustInstalled();
        FragmentRouter.INSTANCE.initialize(this.getBaseContext());
        this.redo = new Stack();
        this.searchGalleriesShowing = false;
        this.expandActionBarButton = (ExpandActionBarButton)this.findViewById(2131493003);
        this.expandActionBarButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                GalleryViewer.this.showClutter();
            }
        });
        this.copyScripts();
        object = "";
        if (GalleryViewer.IsInTest()) {
            object = "http://retard.com";
        }
        this.loadNewGalleryPageFromUrl((String)object, null, null);
        this.onboarding = true;
        this.hideClutter();
        this.forceShowClutter();
        this.progressHandler = new Handler();
        this.fadeProgress = new Runnable(){

            @Override
            public void run() {
                GalleryViewer.this.hideProgress();
            }
        };
        this.launchPager(null);
        GoogleAnalytics.getInstance((Context)this).reportActivityStart(this);
        this.lockUndoButton();
        this.autoLogin(false);
        ImageDownloadManager.INSTANCE.setDownloadPath(PreferenceManager.getDefaultSharedPreferences((Context)this).getString("crumbyDownloadDirectory", null));
        BLACK_LISTED_TAGS = new LinkedHashSet();
        BLACK_LISTED_TAGS.addAll(PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).getStringSet("crumbyTagBlacklistKey", new LinkedHashSet()));
        UniversalInterpreterManager.INSTANCE.initialize((Context)this);
        FragmentRouter.INSTANCE.getAllRegexUrls();
        if (GalleryViewer.IsInTest()) {
            this.testGalleries(new Handler(), null, 0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131689473, menu2);
        return true;
    }

    protected void onDestroy() {
        GoogleAnalytics.getInstance((Context)this).reportActivityStop(this);
        Analytics.INSTANCE.end();
        super.onDestroy();
        System.exit(0);
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        if (n2 == 82) {
            this.mainMenu.toggleSettings();
            return true;
        }
        return super.onKeyDown(n2, keyEvent);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getData() != null) {
            this.loadNewGalleryPageFromUrl(intent.getDataString(), null, null);
        }
    }

    protected void onPause() {
        super.onPause();
        BusProvider.BUS.get().unregister(this);
        if (this.feedback != null) {
            this.feedback.removeCallbacks(this.feeedbackRunnable);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu2) {
        return false;
    }

    protected void onResume() {
        if (this.currentPage != null) {
            this.currentPage.dispatchWaitResume();
        }
        super.onResume();
        BusProvider.BUS.get().register(this);
        this.showFeedbackAlert();
    }

    protected void onSaveInstanceState(Bundle bundle) {
    }

    @Override
    public void onSelectDirectory(String string2) {
        PreferenceManager.getDefaultSharedPreferences((Context)this).edit().putString("crumbyDownloadDirectory", string2).commit();
        ImageDownloadManager.INSTANCE.setDownloadPath(string2);
        Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"", (int)1);
        toast.setGravity(5, 0, 0);
        TextView textView = (TextView)View.inflate((Context)this.getApplicationContext(), (int)2130903119, (ViewGroup)null);
        textView.setText((CharSequence)("Crumby will now save images into \"" + string2 + "/crumby\" "));
        toast.setView((View)textView);
        toast.show();
    }

    @Subscribe
    public void onWebsiteSettingsChanged(WebsiteSettingsChangedEvent object) {
        if (this.currentPage != null && (object = this.currentPage.getActiveFragment()) != null && object.getImage() != null && "".equals(object.getImage().getLinkUrl())) {
            this.reloadCurrentFragment(null);
        }
    }

    public void overrideBreadcrumbs(List<Breadcrumb> list) {
        this.omnibarView.override(list);
    }

    public void pagingToNewFragment() {
        this.resetProgress(true);
        this.multiSelectBar.clear();
    }

    public void progressChange(float f2) {
        if (!($assertionsDisabled || f2 <= 1.0f && f2 >= 0.05f)) {
            throw new AssertionError();
        }
        if (this.progressIndicator == null) {
            this.progressIndicator = (RelativeLayout)this.findViewById(2131493166);
        }
        this.progressHandler.removeCallbacks(this.fadeProgress);
        if (f2 != 1.0f) {
            this.progressIndicator.setAlpha(1.0f);
            this.progressIndicator.setVisibility(0);
            this.progressIndicator.setPadding(0, 0, (int)((float)this.progressIndicator.getWidth() * (1.0f - f2)), 0);
            this.omnibarView.pageLoading();
            return;
        }
        this.resetProgress(false);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void redo() {
        if (this.redo.size() == 0) return;
        if (!this.canNavigate()) {
            return;
        }
        GalleryPage galleryPage = this.redo.pop();
        Analytics.INSTANCE.newNavigationEvent("redo", galleryPage.getTag() + " " + galleryPage.getActiveUrl());
        CrDb.d("viewer activity", "redo " + galleryPage.getTag());
        galleryPage.getArguments().putString("url", galleryPage.getActiveUrl());
        galleryPage.resetFocusToSavedRedoPosition();
        this.switchToPage(galleryPage, galleryPage.getArguments(), galleryPage.getActiveUrl(), null);
        if (this.redo.size() != 0) return;
        this.lockRedoButton();
    }

    public void redrawPage() {
        if (this.currentPage != null) {
            this.currentPage.redraw();
        }
    }

    @Subscribe
    public void reloadCurrentFragment(ReloadFragmentEvent reloadFragmentEvent) {
        if (this.canNavigate()) {
            this.currentPage.refresh();
        }
    }

    public void showClutter() {
        this.forceShowClutter();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void showOmniSearch() {
        this.searchGalleriesShowing = true;
        if (this.currentPage != null) {
            this.currentPage.omniSearchIsShowing();
        }
        this.redoButton.setVisibility(8);
        this.undoButton.setVisibility(8);
        this.mainMenu.hideButton();
        this.omnibarView.showOmniSearchModal();
        if (shownGallerySearchTutorial) return;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this.getApplicationContext());
        if (sharedPreferences.getBoolean("shownGallerySearchTutorial", false)) {
            shownGallerySearchTutorial = true;
            return;
        }
        ViewGroup viewGroup = (ViewGroup)this.findViewById(2131493086);
        if (viewGroup.getChildCount() != 0) {
            viewGroup.performClick();
            viewGroup.requestFocus();
            Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "omnibar gallery search", this.currentPage.getActiveUrl());
            TooltipManager.getInstance(this).create(10).anchor(this.findViewById(2131493093), TooltipManager.Gravity.BOTTOM).closePolicy(TooltipManager.ClosePolicy.None, 5000).activateDelay(500).withStyleId(2131361823).text("To search the current image gallery, try using this form instead of the omnibar.").maxWidth(1000).show();
            shownGallerySearchTutorial = true;
        }
        sharedPreferences.edit().putBoolean("shownGallerySearchTutorial", true).commit();
    }

    public void showOmnibar() {
        this.showOmnibar(false);
    }

    @Subscribe
    public void silentUrlRedirect(SilentUrlRedirectEvent silentUrlRedirectEvent) {
        this.currentPage.redirectFragment(silentUrlRedirectEvent.silentRedirectUrl, silentUrlRedirectEvent.keyImage);
    }

    @Subscribe
    public void stopLoadingCurrentFragment(StopLoadingFragmentEvent stopLoadingFragmentEvent) {
        if (this.progressIndicator != null) {
            this.progressIndicator.setAlpha(0.0f);
        }
        this.currentPage.stopLoading();
    }

    public void toggleHideyBar() {
    }

    public boolean undo() {
        final FragmentManager fragmentManager = this.getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() <= 1 || !this.canNavigate()) {
            return false;
        }
        this.addCurrentPageToRedoStack();
        Object object = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 2).getName();
        this.currentPage.discard();
        this.currentPage = (GalleryPage)fragmentManager.findFragmentByTag((String)object);
        object = FragmentRouter.INSTANCE.getGalleryFragmentInstance(this.currentPage.getActiveUrl());
        this.currentPage.setInitialFragment((GalleryViewerFragment)object);
        this.updateBreadcrumbs((GalleryViewerFragment)object);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                fragmentManager.popBackStackImmediate();
            }
        }, 25);
        if (fragmentManager.getBackStackEntryCount() <= 2) {
            this.lockUndoButton();
        }
        Analytics.INSTANCE.newNavigationEvent("undo", this.currentPage.getActiveUrl());
        this.forceShowClutter();
        return true;
    }

    public void updateBreadcrumbs(GalleryViewerFragment galleryViewerFragment) {
        this.omnibarView.update(galleryViewerFragment, false);
    }

    @Subscribe
    public void urlChange(UrlChangeEvent urlChangeEvent) {
        if (urlChangeEvent.clearPrevious) {
            this.omnibarView.clearBreadcrumbs();
        }
        if (!this.canNavigate()) {
            return;
        }
        this.loadNewGalleryPageFromUrl(urlChangeEvent.url, urlChangeEvent.bundle, urlChangeEvent.producer);
    }

    public static enum STORE {
        GOOGLE_PLAY("http://i.getcrumby.com/play_update"),
        AMAZON("http://i.getcrumby.com/amazon_update");
        
        public String link;

        private STORE(String string3) {
            this.link = string3;
        }
    }

}

