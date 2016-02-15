/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.TextView
 */
package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.UserVoice;
import com.uservoice.uservoicesdk.activity.ContactActivity;
import com.uservoice.uservoicesdk.activity.ForumActivity;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.flow.InitManager;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.ClientConfig;
import com.uservoice.uservoicesdk.model.Forum;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.model.Topic;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.SearchAdapter;
import com.uservoice.uservoicesdk.ui.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PortalAdapter
extends SearchAdapter<BaseModel>
implements AdapterView.OnItemClickListener {
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
    private boolean configLoaded = false;
    private final FragmentActivity context;
    private LayoutInflater inflater;
    private List<Integer> staticRows;

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

    public PortalAdapter(FragmentActivity fragmentActivity) {
        this.context = fragmentActivity;
        this.inflater = (LayoutInflater)fragmentActivity.getSystemService("layout_inflater");
        new InitManager((Context)fragmentActivity, new Runnable(){

            @Override
            public void run() {
                PortalAdapter.this.configLoaded = true;
                PortalAdapter.this.notifyDataSetChanged();
                PortalAdapter.this.loadForum();
                PortalAdapter.this.loadTopics();
            }
        }).init();
    }

    private void computeStaticRows() {
        if (this.staticRows == null) {
            this.staticRows = new ArrayList<Integer>();
            Config config = Session.getInstance().getConfig();
            if (config.shouldShowContactUs()) {
                this.staticRows.add(CONTACT);
            }
            if (config.shouldShowForum()) {
                this.staticRows.add(FORUM);
            }
            if (config.shouldShowKnowledgeBase()) {
                this.staticRows.add(KB_HEADER);
            }
        }
    }

    private List<Topic> getTopics() {
        return Session.getInstance().getTopics();
    }

    private void loadForum() {
        Forum.loadForum(Session.getInstance().getConfig().getForumId(), new DefaultCallback<Forum>((Context)this.context){

            @Override
            public void onModel(Forum forum) {
                Session.getInstance().setForum(forum);
                PortalAdapter.this.notifyDataSetChanged();
            }
        });
    }

    private void loadTopics() {
        final DefaultCallback<List<Article>> defaultCallback = new DefaultCallback<List<Article>>((Context)this.context){

            @Override
            public void onModel(List<Article> list) {
                Session.getInstance().setTopics(new ArrayList<Topic>());
                PortalAdapter.this.articles = list;
                PortalAdapter.this.notifyDataSetChanged();
            }
        };
        if (Session.getInstance().getConfig().getTopicId() != -1) {
            Article.loadPageForTopic(Session.getInstance().getConfig().getTopicId(), 1, defaultCallback);
            return;
        }
        Topic.loadTopics(new DefaultCallback<List<Topic>>((Context)this.context){

            @Override
            public void onModel(List<Topic> list) {
                if (list.isEmpty()) {
                    Session.getInstance().setTopics(list);
                    Article.loadPage(1, defaultCallback);
                    return;
                }
                list = new ArrayList<Topic>(list);
                list.add(Topic.ALL_ARTICLES);
                Session.getInstance().setTopics(list);
                PortalAdapter.this.notifyDataSetChanged();
            }
        });
    }

    private boolean shouldShowArticles() {
        if (Session.getInstance().getConfig().getTopicId() != -1 || this.getTopics() != null && this.getTopics().isEmpty()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getCount() {
        int n2;
        if (!this.configLoaded) {
            return 1;
        }
        this.computeStaticRows();
        int n3 = n2 = this.staticRows.size();
        if (Session.getInstance().getConfig().shouldShowKnowledgeBase()) {
            if (this.getTopics() == null || this.shouldShowArticles() && this.articles == null) {
                n3 = n2 + 1;
            } else {
                n3 = this.shouldShowArticles() ? this.articles.size() : this.getTopics().size();
                n3 = n2 + n3;
            }
        }
        n2 = n3;
        if (Session.getInstance().getClientConfig().isWhiteLabel()) return n2;
        return n3 + 1;
    }

    public Object getItem(int n2) {
        this.computeStaticRows();
        if (n2 < this.staticRows.size() && this.staticRows.get(n2) == FORUM) {
            return Session.getInstance().getForum();
        }
        if (this.getTopics() != null && !this.shouldShowArticles() && n2 >= this.staticRows.size() && n2 - this.staticRows.size() < this.getTopics().size()) {
            return this.getTopics().get(n2 - this.staticRows.size());
        }
        if (this.articles != null && this.shouldShowArticles() && n2 >= this.staticRows.size() && n2 - this.staticRows.size() < this.articles.size()) {
            return this.articles.get(n2 - this.staticRows.size());
        }
        return null;
    }

    public long getItemId(int n2) {
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getItemViewType(int n2) {
        if (!this.configLoaded) {
            return LOADING;
        }
        this.computeStaticRows();
        if (n2 < this.staticRows.size()) {
            int n3;
            n2 = n3 = this.staticRows.get(n2).intValue();
            if (n3 != FORUM) return n2;
            n2 = n3;
            if (Session.getInstance().getForum() != null) return n2;
            return LOADING;
        }
        if (!Session.getInstance().getConfig().shouldShowKnowledgeBase()) return POWERED_BY;
        if (this.getTopics() == null || this.shouldShowArticles() && this.articles == null) {
            if (n2 - this.staticRows.size() != 0) return POWERED_BY;
            return LOADING;
        }
        if (this.shouldShowArticles() && n2 - this.staticRows.size() < this.articles.size()) {
            return ARTICLE;
        }
        if (this.shouldShowArticles()) return POWERED_BY;
        if (n2 - this.staticRows.size() >= this.getTopics().size()) return POWERED_BY;
        return TOPIC;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<BaseModel> getScopedSearchResults() {
        if (this.scope == SCOPE_ALL) {
            return this.searchResults;
        }
        if (this.scope == SCOPE_ARTICLES) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            Iterator iterator = this.searchResults.iterator();
            do {
                Object object = arrayList;
                if (!iterator.hasNext()) return object;
                object = (BaseModel)iterator.next();
                if (!(object instanceof Article)) continue;
                arrayList.add(object);
            } while (true);
        }
        if (this.scope != SCOPE_IDEAS) return null;
        ArrayList<BaseModel> arrayList = new ArrayList<BaseModel>();
        Iterator iterator = this.searchResults.iterator();
        while (iterator.hasNext()) {
            BaseModel baseModel = (BaseModel)iterator.next();
            if (!(baseModel instanceof Suggestion)) continue;
            arrayList.add(baseModel);
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View getView(int n2, View object, ViewGroup object2) {
        object2 = object;
        int n3 = this.getItemViewType(n2);
        object = object2;
        if (object2 == null) {
            if (n3 == LOADING) {
                object = this.inflater.inflate(R.layout.uv_loading_item, null);
            } else if (n3 == FORUM) {
                object = this.inflater.inflate(R.layout.uv_text_item, null);
            } else if (n3 == KB_HEADER) {
                object = this.inflater.inflate(R.layout.uv_header_item_light, null);
            } else if (n3 == TOPIC) {
                object = this.inflater.inflate(R.layout.uv_text_item, null);
            } else if (n3 == CONTACT) {
                object = this.inflater.inflate(R.layout.uv_text_item, null);
            } else if (n3 == ARTICLE) {
                object = this.inflater.inflate(R.layout.uv_text_item, null);
            } else {
                object = object2;
                if (n3 == POWERED_BY) {
                    object = this.inflater.inflate(R.layout.uv_powered_by_item, null);
                }
            }
        }
        if (n3 == FORUM) {
            ((TextView)object.findViewById(R.id.uv_text)).setText(R.string.uv_feedback_forum);
            object2 = (TextView)object.findViewById(R.id.uv_text2);
            object2.setText((CharSequence)Utils.getQuantityString((View)object2, R.plurals.uv_ideas, Session.getInstance().getForum().getNumberOfOpenSuggestions()));
        } else if (n3 == KB_HEADER) {
            ((TextView)object.findViewById(R.id.uv_header_text)).setText(R.string.uv_knowledge_base);
        } else if (n3 == TOPIC) {
            object2 = (Topic)this.getItem(n2);
            ((TextView)object.findViewById(R.id.uv_text)).setText((CharSequence)object2.getName());
            TextView textView = (TextView)object.findViewById(R.id.uv_text2);
            if (object2 == Topic.ALL_ARTICLES) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
                textView.setText((CharSequence)String.format("%d %s", object2.getNumberOfArticles(), this.context.getResources().getQuantityString(R.plurals.uv_articles, object2.getNumberOfArticles())));
            }
        } else if (n3 == CONTACT) {
            ((TextView)object.findViewById(R.id.uv_text)).setText(R.string.uv_contact_us);
            object.findViewById(R.id.uv_text2).setVisibility(8);
        } else if (n3 == ARTICLE) {
            ((TextView)object.findViewById(R.id.uv_text)).setText((CharSequence)((Article)this.getItem(n2)).getTitle());
        } else if (n3 == POWERED_BY) {
            ((TextView)object.findViewById(R.id.uv_version)).setText((CharSequence)(this.context.getString(R.string.uv_android_sdk) + " v" + UserVoice.getVersion()));
        }
        if ((object2 = object.findViewById(R.id.uv_divider)) != null) {
            n2 = n2 == this.getCount() - 2 && this.getItemViewType(this.getCount() - 1) == POWERED_BY || n2 == this.getCount() - 1 ? 8 : 0;
            object2.setVisibility(n2);
        }
        if (n3 == FORUM) {
            object2.setVisibility(8);
        }
        return object;
    }

    public int getViewTypeCount() {
        return 7;
    }

    public boolean isEnabled(int n2) {
        if (!this.configLoaded) {
            return false;
        }
        this.computeStaticRows();
        if (n2 < this.staticRows.size() && ((n2 = this.staticRows.get(n2).intValue()) == KB_HEADER || n2 == LOADING)) {
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
        int n3 = this.getItemViewType(n2);
        if (n3 == CONTACT) {
            this.context.startActivity(new Intent((Context)this.context, (Class)ContactActivity.class));
            return;
        } else {
            if (n3 == FORUM) {
                this.context.startActivity(new Intent((Context)this.context, (Class)ForumActivity.class));
                return;
            }
            if (n3 != TOPIC && n3 != ARTICLE) return;
            {
                Utils.showModel(this.context, (BaseModel)this.getItem(n2));
                return;
            }
        }
    }

    @Override
    protected void searchResultsUpdated() {
        int n2 = 0;
        int n3 = 0;
        Iterator iterator = this.searchResults.iterator();
        while (iterator.hasNext()) {
            if ((BaseModel)iterator.next() instanceof Article) {
                ++n2;
                continue;
            }
            ++n3;
        }
        ((SearchActivity)((Object)this.context)).updateScopedSearch(this.searchResults.size(), n2, n3);
    }

}

