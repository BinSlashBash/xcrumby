package com.crumby.impl.furaffinity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.C0065R;
import com.crumby.lib.authentication.UserData;
import com.uservoice.uservoicesdk.UserVoice;

public class FurAffinityAccountLayout extends LinearLayout implements UserData {

    /* renamed from: com.crumby.impl.furaffinity.FurAffinityAccountLayout.1 */
    class C00891 implements OnClickListener {
        C00891() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newNavigationEvent("uservoice", "furaffinity click");
            UserVoice.launchForum(FurAffinityAccountLayout.this.getContext());
        }
    }

    public FurAffinityAccountLayout(Context context) {
        super(context);
    }

    public FurAffinityAccountLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FurAffinityAccountLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(C0065R.id.user_forum).setOnClickListener(new C00891());
    }

    public void fillWith(Object userData) {
        ((TextView) findViewById(C0065R.id.furaffinity_username)).setText("Logged in as: " + ((String) userData));
    }
}
