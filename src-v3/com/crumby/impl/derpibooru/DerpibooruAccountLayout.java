package com.crumby.impl.derpibooru;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.lib.authentication.UserData;
import com.crumby.lib.events.UrlChangeEvent;

public class DerpibooruAccountLayout extends LinearLayout implements UserData {

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruAccountLayout.1 */
    class C00811 implements OnClickListener {
        C00811() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", DerpibooruFavouritesFragment.BASE_URL);
            BusProvider.BUS.get().post(new UrlChangeEvent(DerpibooruFavouritesFragment.BASE_URL, true));
        }
    }

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruAccountLayout.2 */
    class C00822 implements OnClickListener {
        C00822() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", DerpibooruWatchedFragment.BASE_URL);
            BusProvider.BUS.get().post(new UrlChangeEvent(DerpibooruWatchedFragment.BASE_URL, true));
        }
    }

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruAccountLayout.3 */
    class C00833 implements OnClickListener {
        C00833() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", DerpibooruUpvotedFragment.BASE_URL);
            BusProvider.BUS.get().post(new UrlChangeEvent(DerpibooruUpvotedFragment.BASE_URL, true));
        }
    }

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruAccountLayout.4 */
    class C00844 implements OnClickListener {
        C00844() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", DerpibooruUploadedFragment.BASE_URL);
            BusProvider.BUS.get().post(new UrlChangeEvent(DerpibooruUploadedFragment.BASE_URL, true));
        }
    }

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruAccountLayout.5 */
    class C00855 implements OnClickListener {
        C00855() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "relogin", DerpibooruFragment.BREADCRUMB_NAME);
            ((GalleryViewer) DerpibooruAccountLayout.this.getContext()).autoLogin(true);
        }
    }

    public DerpibooruAccountLayout(Context context) {
        super(context);
    }

    public DerpibooruAccountLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DerpibooruAccountLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(C0065R.id.go_to_derpibooru_favorites).setOnClickListener(new C00811());
        findViewById(C0065R.id.go_to_derpibooru_watched).setOnClickListener(new C00822());
        findViewById(C0065R.id.go_to_derpibooru_upvoted).setOnClickListener(new C00833());
        findViewById(C0065R.id.go_to_derpibooru_uploaded).setOnClickListener(new C00844());
        findViewById(C0065R.id.login_trouble).setOnClickListener(new C00855());
    }

    public void fillWith(Object userData) {
        ((TextView) findViewById(C0065R.id.derpibooru_account_name)).setText("Logged in as " + ((String) userData));
    }
}
