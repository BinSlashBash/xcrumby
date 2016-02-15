/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.uservoice.uservoicesdk;

import android.content.Context;
import android.content.SharedPreferences;
import com.uservoice.uservoicesdk.Config;
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
    private Map<String, String> externalIds = new HashMap<String, String>();
    private Forum forum;
    private OAuthConsumer oauthConsumer;
    private RequestToken requestToken;
    private Runnable signinListener;
    private List<Topic> topics;
    private User user;

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public AccessToken getAccessToken() {
        return this.accessToken;
    }

    public ClientConfig getClientConfig() {
        return this.clientConfig;
    }

    public Config getConfig() {
        return this.config;
    }

    public Context getContext() {
        return this.context;
    }

    public String getEmail() {
        if (this.user != null) {
            return this.user.getEmail();
        }
        return this.getSharedPreferences().getString("user_email", null);
    }

    public Map<String, String> getExternalIds() {
        return this.externalIds;
    }

    public Forum getForum() {
        return this.forum;
    }

    public String getName() {
        if (this.user != null) {
            return this.user.getName();
        }
        return this.getSharedPreferences().getString("user_name", null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public OAuthConsumer getOAuthConsumer() {
        if (this.oauthConsumer != null) return this.oauthConsumer;
        if (this.config.getKey() != null) {
            this.oauthConsumer = new CommonsHttpOAuthConsumer(this.config.getKey(), this.config.getSecret());
            return this.oauthConsumer;
        }
        if (this.clientConfig == null) return this.oauthConsumer;
        this.oauthConsumer = new CommonsHttpOAuthConsumer(this.clientConfig.getKey(), this.clientConfig.getSecret());
        return this.oauthConsumer;
    }

    public RequestToken getRequestToken() {
        return this.requestToken;
    }

    public SharedPreferences getSharedPreferences() {
        return this.context.getSharedPreferences("uv_" + this.config.getSite().replaceAll("\\W", "_"), 0);
    }

    public List<Topic> getTopics() {
        return this.topics;
    }

    public User getUser() {
        return this.user;
    }

    public void persistIdentity(String string2, String string3) {
        SharedPreferences.Editor editor = this.getSharedPreferences().edit();
        editor.putString("user_name", string2);
        editor.putString("user_email", string3);
        editor.commit();
    }

    public void setAccessToken(Context context, AccessToken accessToken) {
        this.accessToken = accessToken;
        accessToken.persist(this.getSharedPreferences(), "access_token", "access_token");
        if (this.signinListener != null) {
            this.signinListener.run();
        }
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public void setConfig(Config config) {
        this.config = config;
        if (config.getEmail() != null) {
            this.persistIdentity(config.getName(), config.getEmail());
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setExternalId(String string2, String string3) {
        this.externalIds.put(string2, string3);
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public void setRequestToken(RequestToken requestToken) {
        this.requestToken = requestToken;
    }

    public void setSignInListener(Runnable runnable) {
        this.signinListener = runnable;
    }

    public void setTopics(List<Topic> list) {
        this.topics = list;
    }

    public void setUser(User user) {
        this.user = user;
        this.persistIdentity(user.getName(), user.getEmail());
    }
}

