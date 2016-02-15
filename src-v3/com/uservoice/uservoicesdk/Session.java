package com.uservoice.uservoicesdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.uservoice.uservoicesdk.model.AccessToken;
import com.uservoice.uservoicesdk.model.ClientConfig;
import com.uservoice.uservoicesdk.model.Forum;
import com.uservoice.uservoicesdk.model.RequestToken;
import com.uservoice.uservoicesdk.model.Topic;
import com.uservoice.uservoicesdk.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class Session {
    private static Session instance;
    private AccessToken accessToken;
    private ClientConfig clientConfig;
    private Config config;
    private Context context;
    private Map<String, String> externalIds;
    private Forum forum;
    private OAuthConsumer oauthConsumer;
    private RequestToken requestToken;
    private Runnable signinListener;
    private List<Topic> topics;
    private User user;

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    private Session() {
        this.externalIds = new HashMap();
    }

    public Context getContext() {
        return this.context;
    }

    public Config getConfig() {
        return this.config;
    }

    public void setConfig(Config config) {
        this.config = config;
        if (config.getEmail() != null) {
            persistIdentity(config.getName(), config.getEmail());
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void persistIdentity(String name, String email) {
        Editor edit = getSharedPreferences().edit();
        edit.putString("user_name", name);
        edit.putString("user_email", email);
        edit.commit();
    }

    public String getName() {
        if (this.user != null) {
            return this.user.getName();
        }
        return getSharedPreferences().getString("user_name", null);
    }

    public String getEmail() {
        if (this.user != null) {
            return this.user.getEmail();
        }
        return getSharedPreferences().getString("user_email", null);
    }

    public RequestToken getRequestToken() {
        return this.requestToken;
    }

    public void setRequestToken(RequestToken requestToken) {
        this.requestToken = requestToken;
    }

    public OAuthConsumer getOAuthConsumer() {
        if (this.oauthConsumer == null) {
            if (this.config.getKey() != null) {
                this.oauthConsumer = new CommonsHttpOAuthConsumer(this.config.getKey(), this.config.getSecret());
            } else if (this.clientConfig != null) {
                this.oauthConsumer = new CommonsHttpOAuthConsumer(this.clientConfig.getKey(), this.clientConfig.getSecret());
            }
        }
        return this.oauthConsumer;
    }

    public AccessToken getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(Context context, AccessToken accessToken) {
        this.accessToken = accessToken;
        accessToken.persist(getSharedPreferences(), "access_token", "access_token");
        if (this.signinListener != null) {
            this.signinListener.run();
        }
    }

    public SharedPreferences getSharedPreferences() {
        return this.context.getSharedPreferences("uv_" + this.config.getSite().replaceAll("\\W", "_"), 0);
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
        persistIdentity(user.getName(), user.getEmail());
    }

    public ClientConfig getClientConfig() {
        return this.clientConfig;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public void setExternalId(String scope, String id) {
        this.externalIds.put(scope, id);
    }

    public Map<String, String> getExternalIds() {
        return this.externalIds;
    }

    public Forum getForum() {
        return this.forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Topic> getTopics() {
        return this.topics;
    }

    public void setSignInListener(Runnable runnable) {
        this.signinListener = runnable;
    }
}
