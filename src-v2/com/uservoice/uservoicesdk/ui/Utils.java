/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.Color
 *  android.net.Uri
 *  android.os.Parcelable
 *  android.util.TypedValue
 *  android.view.View
 *  android.webkit.WebChromeClient
 *  android.webkit.WebSettings
 *  android.webkit.WebSettings$PluginState
 *  android.webkit.WebView
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.uservoice.uservoicesdk.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.activity.TopicActivity;
import com.uservoice.uservoicesdk.dialog.ArticleDialogFragment;
import com.uservoice.uservoicesdk.dialog.SuggestionDialogFragment;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.model.Topic;
import java.util.Locale;

public class Utils {
    @SuppressLint(value={"SetJavaScriptEnabled"})
    public static void displayArticle(WebView webView, Article object, Context context) {
        String string2 = "iframe, img { width: 100%; }";
        if (Utils.isDarkTheme(context)) {
            webView.setBackgroundColor(-16777216);
            string2 = "iframe, img { width: 100%; }" + "body { background-color: #000000; color: #F6F6F6; } a { color: #0099FF; }";
        }
        object = String.format("<html><head><meta charset=\"utf-8\"><link rel=\"stylesheet\" type=\"text/css\" href=\"http://cdn.uservoice.com/stylesheets/vendor/typeset.css\"/><style>%s</style></head><body class=\"typeset\" style=\"font-family: sans-serif; margin: 1em\"><h3>%s</h3>%s</body></html>", string2, object.getTitle(), object.getHtml());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.loadUrl(String.format("data:text/html;charset=utf-8,%s", Uri.encode((String)object)));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void displayInstantAnswer(View view, BaseModel baseModel) {
        TextView textView = (TextView)view.findViewById(R.id.uv_title);
        TextView textView2 = (TextView)view.findViewById(R.id.uv_detail);
        View view2 = view.findViewById(R.id.uv_suggestion_details);
        view = (ImageView)view.findViewById(R.id.uv_icon);
        if (baseModel instanceof Article) {
            baseModel = (Article)baseModel;
            view.setImageResource(R.drawable.uv_article);
            textView.setText((CharSequence)baseModel.getTitle());
            if (baseModel.getTopicName() != null) {
                textView2.setVisibility(0);
                textView2.setText((CharSequence)baseModel.getTopicName());
            } else {
                textView2.setVisibility(8);
            }
            view2.setVisibility(8);
            return;
        }
        if (!(baseModel instanceof Suggestion)) return;
        {
            baseModel = (Suggestion)baseModel;
            view.setImageResource(R.drawable.uv_idea);
            textView.setText((CharSequence)baseModel.getTitle());
            textView2.setVisibility(0);
            textView2.setText((CharSequence)baseModel.getForumName());
            if (baseModel.getStatus() != null) {
                view = view2.findViewById(R.id.uv_suggestion_status_color);
                textView = (TextView)view2.findViewById(R.id.uv_suggestion_status);
                int n2 = Color.parseColor((String)baseModel.getStatusColor());
                view2.setVisibility(0);
                textView.setText((CharSequence)baseModel.getStatus().toUpperCase(Locale.getDefault()));
                textView.setTextColor(n2);
                view.setBackgroundColor(n2);
                return;
            }
        }
        view2.setVisibility(8);
    }

    @SuppressLint(value={"DefaultLocale"})
    public static String getQuantityString(View view, int n2, int n3) {
        return String.format("%,d %s", n3, view.getContext().getResources().getQuantityString(n2, n3));
    }

    public static boolean isDarkTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        float[] arrf = new float[3];
        context.getTheme().resolveAttribute(16842806, typedValue, true);
        Color.colorToHSV((int)context.getResources().getColor(typedValue.resourceId), (float[])arrf);
        if (arrf[2] > 0.5f) {
            return true;
        }
        return false;
    }

    public static void showModel(FragmentActivity fragmentActivity, BaseModel baseModel) {
        Utils.showModel(fragmentActivity, baseModel, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void showModel(FragmentActivity fragmentActivity, BaseModel baseModel, String string2) {
        if (baseModel instanceof Article) {
            new ArticleDialogFragment((Article)baseModel, string2).show(fragmentActivity.getSupportFragmentManager(), "ArticleDialogFragment");
            return;
        } else {
            if (baseModel instanceof Suggestion) {
                new SuggestionDialogFragment((Suggestion)baseModel, string2).show(fragmentActivity.getSupportFragmentManager(), "SuggestionDialogFragment");
                return;
            }
            if (!(baseModel instanceof Topic)) return;
            {
                string2 = new Intent((Context)fragmentActivity, (Class)TopicActivity.class);
                string2.putExtra("topic", (Parcelable)((Topic)baseModel));
                fragmentActivity.startActivity((Intent)string2);
                return;
            }
        }
    }
}

