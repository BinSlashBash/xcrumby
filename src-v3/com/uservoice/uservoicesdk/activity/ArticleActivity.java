package com.uservoice.uservoicesdk.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.dialog.UnhelpfulDialogFragment;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.ui.Utils;

public class ArticleActivity extends BaseActivity implements SearchActivity {

    /* renamed from: com.uservoice.uservoicesdk.activity.ArticleActivity.1 */
    class C06221 extends WebViewClient {
        C06221() {
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            ArticleActivity.this.findViewById(C0621R.id.uv_helpful_section).setVisibility(0);
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.activity.ArticleActivity.2 */
    class C06232 implements OnClickListener {
        final /* synthetic */ Article val$article;

        C06232(Article article) {
            this.val$article = article;
        }

        public void onClick(View v) {
            Babayaga.track(Event.VOTE_ARTICLE, this.val$article.getId());
            Toast.makeText(ArticleActivity.this, C0621R.string.uv_thanks, 0).show();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.activity.ArticleActivity.3 */
    class C06243 implements OnClickListener {
        C06243() {
        }

        public void onClick(View v) {
            new UnhelpfulDialogFragment().show(ArticleActivity.this.getSupportFragmentManager(), "UnhelpfulDialogFragment");
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0621R.layout.uv_article_layout);
        Article article = (Article) getIntent().getParcelableExtra("article");
        setTitle(article.getTitle());
        WebView webView = (WebView) findViewById(C0621R.id.uv_webview);
        Utils.displayArticle(webView, article, this);
        findViewById(C0621R.id.uv_container).setBackgroundColor(Utils.isDarkTheme(this) ? ViewCompat.MEASURED_STATE_MASK : -1);
        webView.setWebViewClient(new C06221());
        findViewById(C0621R.id.uv_helpful_button).setOnClickListener(new C06232(article));
        findViewById(C0621R.id.uv_unhelpful_button).setOnClickListener(new C06243());
        Babayaga.track(Event.VIEW_ARTICLE, article.getId());
    }

    @SuppressLint({"NewApi"})
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0621R.menu.uv_portal, menu);
        setupScopedSearch(menu);
        return true;
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() != C0621R.id.uv_action_contact) {
            return super.onMenuItemSelected(featureId, item);
        }
        startActivity(new Intent(this, ContactActivity.class));
        return true;
    }

    public void finish() {
        ((WebView) findViewById(C0621R.id.uv_webview)).loadData(UnsupportedUrlFragment.DISPLAY_NAME, "text/html", "utf-8");
        super.finish();
    }
}
