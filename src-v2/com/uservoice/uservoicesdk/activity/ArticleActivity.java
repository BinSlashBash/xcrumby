/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.Toast
 */
package com.uservoice.uservoicesdk.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.activity.BaseActivity;
import com.uservoice.uservoicesdk.activity.ContactActivity;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.dialog.UnhelpfulDialogFragment;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.ui.Utils;

public class ArticleActivity
extends BaseActivity
implements SearchActivity {
    public void finish() {
        ((WebView)this.findViewById(R.id.uv_webview)).loadData("", "text/html", "utf-8");
        super.finish();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.setContentView(R.layout.uv_article_layout);
        object = (Article)this.getIntent().getParcelableExtra("article");
        this.setTitle((CharSequence)object.getTitle());
        WebView webView = (WebView)this.findViewById(R.id.uv_webview);
        Utils.displayArticle(webView, (Article)object, (Context)this);
        View view = this.findViewById(R.id.uv_container);
        int n2 = Utils.isDarkTheme((Context)this) ? -16777216 : -1;
        view.setBackgroundColor(n2);
        webView.setWebViewClient(new WebViewClient(){

            public void onPageFinished(WebView webView, String string2) {
                super.onPageFinished(webView, string2);
                ArticleActivity.this.findViewById(R.id.uv_helpful_section).setVisibility(0);
            }
        });
        this.findViewById(R.id.uv_helpful_button).setOnClickListener(new View.OnClickListener((Article)object){
            final /* synthetic */ Article val$article;

            public void onClick(View view) {
                Babayaga.track(Babayaga.Event.VOTE_ARTICLE, this.val$article.getId());
                Toast.makeText((Context)ArticleActivity.this, (int)R.string.uv_thanks, (int)0).show();
            }
        });
        this.findViewById(R.id.uv_unhelpful_button).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                new UnhelpfulDialogFragment().show(ArticleActivity.this.getSupportFragmentManager(), "UnhelpfulDialogFragment");
            }
        });
        Babayaga.track(Babayaga.Event.VIEW_ARTICLE, object.getId());
    }

    @SuppressLint(value={"NewApi"})
    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(R.menu.uv_portal, menu2);
        this.setupScopedSearch(menu2);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int n2, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.uv_action_contact) {
            this.startActivity(new Intent((Context)this, (Class)ContactActivity.class));
            return true;
        }
        return super.onMenuItemSelected(n2, menuItem);
    }

}

