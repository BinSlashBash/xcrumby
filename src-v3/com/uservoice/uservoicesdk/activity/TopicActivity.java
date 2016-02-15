package com.uservoice.uservoicesdk.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.Topic;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.ui.PaginatedAdapter;
import com.uservoice.uservoicesdk.ui.PaginationScrollListener;
import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends BaseListActivity implements SearchActivity {

    /* renamed from: com.uservoice.uservoicesdk.activity.TopicActivity.1 */
    class C06311 implements OnNavigationListener {
        C06311() {
        }

        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            TopicActivity.this.getIntent().putExtra("topic", (Topic) Session.getInstance().getTopics().get(itemPosition));
            TopicActivity.this.getModelAdapter().reload();
            return true;
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.activity.TopicActivity.3 */
    class C06323 implements OnItemClickListener {
        C06323() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Article article = (Article) TopicActivity.this.getListAdapter().getItem(position);
            Intent intent = new Intent(TopicActivity.this, ArticleActivity.class);
            intent.putExtra("article", article);
            TopicActivity.this.startActivity(intent);
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.activity.TopicActivity.2 */
    class C15082 extends PaginatedAdapter<Article> {
        C15082(Context x0, int x1, List x2) {
            super(x0, x1, x2);
        }

        protected void loadPage(int page, Callback<List<Article>> callback) {
            Topic topic = (Topic) TopicActivity.this.getIntent().getParcelableExtra("topic");
            if (topic.getId() == -1) {
                Article.loadPage(page, callback);
            } else {
                Article.loadPageForTopic(topic.getId(), page, callback);
            }
        }

        public int getTotalNumberOfObjects() {
            Topic topic = (Topic) TopicActivity.this.getIntent().getParcelableExtra("topic");
            if (topic.getId() == -1) {
                return -1;
            }
            return topic.getNumberOfArticles();
        }

        protected void customizeLayout(View view, Article model) {
            Topic topic = (Topic) TopicActivity.this.getIntent().getParcelableExtra("topic");
            TextView text2 = (TextView) view.findViewById(C0621R.id.uv_text2);
            ((TextView) view.findViewById(C0621R.id.uv_text)).setText(model.getTitle());
            if (topic.getId() != -1 || model.getTopicName() == null) {
                text2.setVisibility(8);
                return;
            }
            text2.setVisibility(0);
            text2.setText(model.getTopicName());
        }
    }

    @SuppressLint({"InlinedApi", "NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Topic topic = (Topic) getIntent().getParcelableExtra("topic");
        if (hasActionBar()) {
            ActionBar actionBar = getActionBar();
            actionBar.setNavigationMode(1);
            actionBar.setListNavigationCallbacks(new ArrayAdapter(actionBar.getThemedContext(), 17367049, Session.getInstance().getTopics()), new C06311());
            actionBar.setSelectedNavigationItem(Session.getInstance().getTopics().indexOf(topic));
        }
        setTitle(null);
        getListView().setDivider(null);
        setListAdapter(new C15082(this, C0621R.layout.uv_text_item, new ArrayList()));
        getListView().setOnScrollListener(new PaginationScrollListener(getModelAdapter()));
        getListView().setOnItemClickListener(new C06323());
        Babayaga.track(Event.VIEW_TOPIC, topic.getId());
    }

    public void hideSearch() {
        super.hideSearch();
        getActionBar().setNavigationMode(1);
    }

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

    public PaginatedAdapter<Article> getModelAdapter() {
        return (PaginatedAdapter) getListAdapter();
    }
}
