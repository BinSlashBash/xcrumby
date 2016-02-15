package com.crumby.impl.e621;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.C0065R;
import com.crumby.lib.authentication.UserData;
import com.crumby.lib.events.UrlChangeEvent;

public class e621AccountLayout extends LinearLayout implements UserData {
    String user;

    /* renamed from: com.crumby.impl.e621.e621AccountLayout.1 */
    class C00881 implements OnClickListener {
        C00881() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newNavigationEvent("account", "e621 favorites");
            BusProvider.BUS.get().post(new UrlChangeEvent("https://e621.net/post?tags=" + Uri.encode("fav:" + e621AccountLayout.this.user)));
        }
    }

    public e621AccountLayout(Context context) {
        super(context);
    }

    public e621AccountLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public e621AccountLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(C0065R.id.go_to_e621_favorites).setOnClickListener(new C00881());
    }

    public void fillWith(Object userData) {
        this.user = (String) userData;
        ((TextView) findViewById(C0065R.id.username)).setText("Logged in as: " + this.user);
    }
}
