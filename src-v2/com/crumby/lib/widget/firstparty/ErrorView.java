/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.fragment_options.OverflowImageButton;
import com.uservoice.uservoicesdk.UserVoice;
import java.util.HashMap;

public class ErrorView
extends RelativeLayout {
    TextView detailsView;
    private Button dismiss;
    TextView mainView;
    TextView reasonView;
    private boolean shownError;

    public ErrorView(Context context) {
        super(context);
    }

    public ErrorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ErrorView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void showError(DisplayError object, String object2, String arrstring) {
        this.shownError = true;
        if (!object.showBackground) {
            this.setBackgroundDrawable(null);
        }
        if (object.showDismiss) {
            this.dismiss.setVisibility(0);
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("displayError", (DisplayError)object);
        hashMap.put("subMessage", (String)object2);
        Analytics.INSTANCE.newError((DisplayError)object, (String)object2 + " @" + (String)arrstring);
        UserVoice.track("error", hashMap);
        this.setVisibility(0);
        this.mainView.setText((CharSequence)object.main);
        this.reasonView.setText((CharSequence)object2);
        object2 = "";
        if (arrstring == null) {
            this.findViewById(2131492964).setVisibility(8);
        }
        this.findViewById(2131492964).setOnClickListener(new View.OnClickListener((String)arrstring){
            final /* synthetic */ String val$url;

            public void onClick(View view) {
                OverflowImageButton.openWebBrowser(this.val$url, ErrorView.this.getContext());
            }
        });
        if (this.detailsView != null) {
            object2 = "" + "Suggestions:\n";
            arrstring = object.details;
            int n2 = arrstring.length;
            int n3 = 0;
            object = object2;
            do {
                object2 = object;
                if (n3 >= n2) break;
                object2 = arrstring[n3];
                if (object2 == null) {
                    return;
                }
                object = (String)object + "\u2022 " + (String)object2 + "\n";
                ++n3;
            } while (true);
        }
        this.detailsView.setText((CharSequence)object2);
    }

    public boolean close() {
        if (this.dismiss.getVisibility() == 0 && this.getVisibility() == 0) {
            this.setVisibility(8);
            return true;
        }
        return false;
    }

    public boolean isShowing() {
        if (this.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mainView = (TextView)this.findViewById(2131492960);
        this.reasonView = (TextView)this.findViewById(2131492961);
        this.detailsView = (TextView)this.findViewById(2131492962);
        this.dismiss = (Button)this.findViewById(2131492965);
        this.findViewById(2131492963).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newNavigationEvent("uservoice", "contact");
                UserVoice.launchContactUs(ErrorView.this.getContext());
            }
        });
        this.dismiss.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ErrorView.this.setVisibility(8);
            }
        });
    }

    public void show(DisplayError displayError, String string2, String string3) {
        String string4;
        block2 : {
            if (string2 != null) {
                string4 = string2;
                if (!string2.equals("")) break block2;
            }
            string4 = displayError.reason;
        }
        this.showError(displayError, string4, string3);
    }

    public boolean shownError() {
        return this.shownError;
    }

}

