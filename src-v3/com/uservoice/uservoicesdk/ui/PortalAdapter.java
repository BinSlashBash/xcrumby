package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.UserVoice;
import com.uservoice.uservoicesdk.activity.ContactActivity;
import com.uservoice.uservoicesdk.activity.ForumActivity;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.flow.InitManager;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.Forum;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.model.Topic;
import java.util.ArrayList;
import java.util.List;

public class PortalAdapter extends SearchAdapter<BaseModel> implements OnItemClickListener {
    private static int ARTICLE;
    private static int CONTACT;
    private static int FORUM;
    private static int KB_HEADER;
    private static int LOADING;
    private static int POWERED_BY;
    public static int SCOPE_ALL;
    public static int SCOPE_ARTICLES;
    public static int SCOPE_IDEAS;
    private static int TOPIC;
    private List<Article> articles;
    private boolean configLoaded;
    private final FragmentActivity context;
    private LayoutInflater inflater;
    private List<Integer> staticRows;

    /* renamed from: com.uservoice.uservoicesdk.ui.PortalAdapter.1 */
    class C06571 implements Runnable {
        C06571() {
        }

        public void run() {
            PortalAdapter.this.configLoaded = true;
            PortalAdapter.this.notifyDataSetChanged();
            PortalAdapter.this.loadForum();
            PortalAdapter.this.loadTopics();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.ui.PortalAdapter.2 */
    class C14532 extends DefaultCallback<Forum> {
        C14532(Context x0) {
            super(x0);
        }

        public void onModel(Forum model) {
            Session.getInstance().setForum(model);
            PortalAdapter.this.notifyDataSetChanged();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.ui.PortalAdapter.3 */
    class C14543 extends DefaultCallback<List<Article>> {
        C14543(Context x0) {
            super(x0);
        }

        public void onModel(List<Article> model) {
            Session.getInstance().setTopics(new ArrayList());
            PortalAdapter.this.articles = model;
            PortalAdapter.this.notifyDataSetChanged();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.ui.PortalAdapter.4 */
    class C14554 extends DefaultCallback<List<Topic>> {
        final /* synthetic */ DefaultCallback val$articlesCallback;

        C14554(Context x0, DefaultCallback defaultCallback) {
            this.val$articlesCallback = defaultCallback;
            super(x0);
        }

        public void onModel(List<Topic> model) {
            if (model.isEmpty()) {
                Session.getInstance().setTopics(model);
                Article.loadPage(1, this.val$articlesCallback);
                return;
            }
            ArrayList<Topic> topics = new ArrayList(model);
            topics.add(Topic.ALL_ARTICLES);
            Session.getInstance().setTopics(topics);
            PortalAdapter.this.notifyDataSetChanged();
        }
    }

    static {
        SCOPE_ALL = 0;
        SCOPE_ARTICLES = 1;
        SCOPE_IDEAS = 2;
        KB_HEADER = 0;
        FORUM = 1;
        TOPIC = 2;
        LOADING = 3;
        CONTACT = 4;
        ARTICLE = 5;
        POWERED_BY = 6;
    }

    public PortalAdapter(FragmentActivity context) {
        this.configLoaded = false;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        new InitManager(context, new C06571()).init();
    }

    private List<Topic> getTopics() {
        return Session.getInstance().getTopics();
    }

    private boolean shouldShowArticles() {
        return Session.getInstance().getConfig().getTopicId() != -1 || (getTopics() != null && getTopics().isEmpty());
    }

    private void loadForum() {
        Forum.loadForum(Session.getInstance().getConfig().getForumId(), new C14532(this.context));
    }

    private void loadTopics() {
        DefaultCallback<List<Article>> articlesCallback = new C14543(this.context);
        if (Session.getInstance().getConfig().getTopicId() != -1) {
            Article.loadPageForTopic(Session.getInstance().getConfig().getTopicId(), 1, articlesCallback);
        } else {
            Topic.loadTopics(new C14554(this.context, articlesCallback));
        }
    }

    private void computeStaticRows() {
        if (this.staticRows == null) {
            this.staticRows = new ArrayList();
            Config config = Session.getInstance().getConfig();
            if (config.shouldShowContactUs()) {
                this.staticRows.add(Integer.valueOf(CONTACT));
            }
            if (config.shouldShowForum()) {
                this.staticRows.add(Integer.valueOf(FORUM));
            }
            if (config.shouldShowKnowledgeBase()) {
                this.staticRows.add(Integer.valueOf(KB_HEADER));
            }
        }
    }

    public int getCount() {
        if (!this.configLoaded) {
            return 1;
        }
        computeStaticRows();
        int rows = this.staticRows.size();
        if (Session.getInstance().getConfig().shouldShowKnowledgeBase()) {
            if (getTopics() == null || (shouldShowArticles() && this.articles == null)) {
                rows++;
            } else {
                rows += shouldShowArticles() ? this.articles.size() : getTopics().size();
            }
        }
        if (Session.getInstance().getClientConfig().isWhiteLabel()) {
            return rows;
        }
        return rows + 1;
    }

    public List<BaseModel> getScopedSearchResults() {
        if (this.scope == SCOPE_ALL) {
            return this.searchResults;
        }
        if (this.scope == SCOPE_ARTICLES) {
            List<BaseModel> articles = new ArrayList();
            for (BaseModel model : this.searchResults) {
                if (model instanceof Article) {
                    articles.add(model);
                }
            }
            return articles;
        } else if (this.scope != SCOPE_IDEAS) {
            return null;
        } else {
            List<BaseModel> ideas = new ArrayList();
            for (BaseModel model2 : this.searchResults) {
                if (model2 instanceof Suggestion) {
                    ideas.add(model2);
                }
            }
            return ideas;
        }
    }

    public Object getItem(int position) {
        computeStaticRows();
        if (position < this.staticRows.size() && ((Integer) this.staticRows.get(position)).intValue() == FORUM) {
            return Session.getInstance().getForum();
        }
        if (getTopics() != null && !shouldShowArticles() && position >= this.staticRows.size() && position - this.staticRows.size() < getTopics().size()) {
            return getTopics().get(position - this.staticRows.size());
        }
        if (this.articles == null || !shouldShowArticles() || position < this.staticRows.size() || position - this.staticRows.size() >= this.articles.size()) {
            return null;
        }
        return this.articles.get(position - this.staticRows.size());
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public boolean isEnabled(int position) {
        if (!this.configLoaded) {
            return false;
        }
        computeStaticRows();
        if (position < this.staticRows.size()) {
            int type = ((Integer) this.staticRows.get(position)).intValue();
            if (type == KB_HEADER || type == LOADING) {
                return false;
            }
        }
        return true;
    }

    protected void searchResultsUpdated() {
        int articleResults = 0;
        int ideaResults = 0;
        for (BaseModel model : this.searchResults) {
            if (model instanceof Article) {
                articleResults++;
            } else {
                ideaResults++;
            }
        }
        ((SearchActivity) this.context).updateScopedSearch(this.searchResults.size(), articleResults, ideaResults);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int type = getItemViewType(position);
        if (view == null) {
            if (type == LOADING) {
                view = this.inflater.inflate(C0621R.layout.uv_loading_item, null);
            } else if (type == FORUM) {
                view = this.inflater.inflate(C0621R.layout.uv_text_item, null);
            } else if (type == KB_HEADER) {
                view = this.inflater.inflate(C0621R.layout.uv_header_item_light, null);
            } else if (type == TOPIC) {
                view = this.inflater.inflate(C0621R.layout.uv_text_item, null);
            } else if (type == CONTACT) {
                view = this.inflater.inflate(C0621R.layout.uv_text_item, null);
            } else if (type == ARTICLE) {
                view = this.inflater.inflate(C0621R.layout.uv_text_item, null);
            } else if (type == POWERED_BY) {
                view = this.inflater.inflate(C0621R.layout.uv_powered_by_item, null);
            }
        }
        if (type == FORUM) {
            ((TextView) view.findViewById(C0621R.id.uv_text)).setText(C0621R.string.uv_feedback_forum);
            TextView text2 = (TextView) view.findViewById(C0621R.id.uv_text2);
            text2.setText(Utils.getQuantityString(text2, C0621R.plurals.uv_ideas, Session.getInstance().getForum().getNumberOfOpenSuggestions()));
        } else if (type == KB_HEADER) {
            ((TextView) view.findViewById(C0621R.id.uv_header_text)).setText(C0621R.string.uv_knowledge_base);
        } else if (type == TOPIC) {
            Topic topic = (Topic) getItem(position);
            ((TextView) view.findViewById(C0621R.id.uv_text)).setText(topic.getName());
            TextView textView = (TextView) view.findViewById(C0621R.id.uv_text2);
            if (topic == Topic.ALL_ARTICLES) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
                textView.setText(String.format("%d %s", new Object[]{Integer.valueOf(topic.getNumberOfArticles()), this.context.getResources().getQuantityString(C0621R.plurals.uv_articles, topic.getNumberOfArticles())}));
            }
        } else if (type == CONTACT) {
            ((TextView) view.findViewById(C0621R.id.uv_text)).setText(C0621R.string.uv_contact_us);
            view.findViewById(C0621R.id.uv_text2).setVisibility(8);
        } else if (type == ARTICLE) {
            ((TextView) view.findViewById(C0621R.id.uv_text)).setText(((Article) getItem(position)).getTitle());
        } else if (type == POWERED_BY) {
            ((TextView) view.findViewById(C0621R.id.uv_version)).setText(this.context.getString(C0621R.string.uv_android_sdk) + " v" + UserVoice.getVersion());
        }
        View divider = view.findViewById(C0621R.id.uv_divider);
        if (divider != null) {
            int i = ((position == getCount() + -2 && getItemViewType(getCount() - 1) == POWERED_BY) || position == getCount() - 1) ? 8 : 0;
            divider.setVisibility(i);
        }
        if (type == FORUM) {
            divider.setVisibility(8);
        }
        return view;
    }

    public int getViewTypeCount() {
        return 7;
    }

    public int getItemViewType(int position) {
        if (!this.configLoaded) {
            return LOADING;
        }
        computeStaticRows();
        if (position < this.staticRows.size()) {
            int type = ((Integer) this.staticRows.get(position)).intValue();
            if (type == FORUM && Session.getInstance().getForum() == null) {
                return LOADING;
            }
            return type;
        }
        if (Session.getInstance().getConfig().shouldShowKnowledgeBase()) {
            if (getTopics() == null || (shouldShowArticles() && this.articles == null)) {
                if (position - this.staticRows.size() == 0) {
                    return LOADING;
                }
            } else if (shouldShowArticles() && position - this.staticRows.size() < this.articles.size()) {
                return ARTICLE;
            } else {
                if (!shouldShowArticles() && position - this.staticRows.size() < getTopics().size()) {
                    return TOPIC;
                }
            }
        }
        return POWERED_BY;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        int type = getItemViewType(position);
        if (type == CONTACT) {
            this.context.startActivity(new Intent(this.context, ContactActivity.class));
        } else if (type == FORUM) {
            this.context.startActivity(new Intent(this.context, ForumActivity.class));
        } else if (type == TOPIC || type == ARTICLE) {
            Utils.showModel(this.context, (BaseModel) getItem(position));
        }
    }
}
