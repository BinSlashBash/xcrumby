package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.widget.firstparty.fragment_options.OverflowImageButton;
import com.uservoice.uservoicesdk.UserVoice;
import java.util.HashMap;

public class ErrorView extends RelativeLayout {
    TextView detailsView;
    private Button dismiss;
    TextView mainView;
    TextView reasonView;
    private boolean shownError;

    /* renamed from: com.crumby.lib.widget.firstparty.ErrorView.1 */
    class C01221 implements OnClickListener {
        C01221() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newNavigationEvent("uservoice", "contact");
            UserVoice.launchContactUs(ErrorView.this.getContext());
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.ErrorView.2 */
    class C01232 implements OnClickListener {
        C01232() {
        }

        public void onClick(View v) {
            ErrorView.this.setVisibility(8);
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.ErrorView.3 */
    class C01243 implements OnClickListener {
        final /* synthetic */ String val$url;

        C01243(String str) {
            this.val$url = str;
        }

        public void onClick(View v) {
            OverflowImageButton.openWebBrowser(this.val$url, ErrorView.this.getContext());
        }
    }

    public ErrorView(Context context) {
        super(context);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mainView = (TextView) findViewById(C0065R.id.error_main_message);
        this.reasonView = (TextView) findViewById(C0065R.id.error_sub_message);
        this.detailsView = (TextView) findViewById(C0065R.id.error_details);
        this.dismiss = (Button) findViewById(C0065R.id.error_dismiss);
        findViewById(C0065R.id.error_report).setOnClickListener(new C01221());
        this.dismiss.setOnClickListener(new C01232());
    }

    private void showError(DisplayError err, String reason, String url) {
        this.shownError = true;
        if (!err.showBackground) {
            setBackgroundDrawable(null);
        }
        if (err.showDismiss) {
            this.dismiss.setVisibility(0);
        }
        HashMap<String, Object> event = new HashMap();
        event.put("displayError", err);
        event.put("subMessage", reason);
        Analytics.INSTANCE.newError(err, reason + " @" + url);
        UserVoice.track("error", event);
        setVisibility(0);
        this.mainView.setText(err.main);
        this.reasonView.setText(reason);
        String detailsText = UnsupportedUrlFragment.DISPLAY_NAME;
        if (url == null) {
            findViewById(C0065R.id.error_try_opening_in_web_browser).setVisibility(8);
        }
        findViewById(C0065R.id.error_try_opening_in_web_browser).setOnClickListener(new C01243(url));
        if (this.detailsView != null) {
            detailsText = detailsText + "Suggestions:\n";
            String[] arr$ = err.details;
            int len$ = arr$.length;
            int i$ = 0;
            while (i$ < len$) {
                String detail = arr$[i$];
                if (detail != null) {
                    detailsText = detailsText + "\u2022 " + detail + "\n";
                    i$++;
                } else {
                    return;
                }
            }
        }
        this.detailsView.setText(detailsText);
    }

    public boolean close() {
        if (this.dismiss.getVisibility() != 0 || getVisibility() != 0) {
            return false;
        }
        setVisibility(8);
        return true;
    }

    public void show(DisplayError error, String reason, String url) {
        if (reason == null || reason.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            reason = error.reason;
        }
        showError(error, reason, url);
    }

    public boolean isShowing() {
        return getVisibility() == 0;
    }

    public boolean shownError() {
        return this.shownError;
    }
}
