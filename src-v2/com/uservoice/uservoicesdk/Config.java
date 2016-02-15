/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk;

import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.model.ClientConfig;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private Map<String, String> customFields = new HashMap<String, String>();
    private String email;
    private int forumId = -1;
    private String guid;
    private String key;
    private String name;
    private String secret;
    private boolean showContactUs = true;
    private boolean showForum = true;
    private boolean showKnowledgeBase = true;
    private boolean showPostIdea = true;
    private String site;
    private int topicId = -1;
    private Map<String, Object> userTraits = new HashMap<String, Object>();

    public Config(String string2) {
        this.site = string2;
    }

    public Config(String string2, String string3, String string4) {
        this.site = string2;
        this.key = string3;
        this.secret = string4;
    }

    public Map<String, String> getCustomFields() {
        return this.customFields;
    }

    public String getEmail() {
        return this.email;
    }

    public int getForumId() {
        if (this.forumId == -1 && Session.getInstance().getClientConfig() != null) {
            return Session.getInstance().getClientConfig().getDefaultForumId();
        }
        return this.forumId;
    }

    public String getGuid() {
        return this.guid;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public String getSecret() {
        return this.secret;
    }

    public String getSite() {
        return this.site;
    }

    public int getTopicId() {
        return this.topicId;
    }

    public Map<String, Object> getUserTraits() {
        return this.userTraits;
    }

    public void identifyUser(String string2, String string3, String string4) {
        this.guid = string2;
        this.name = string3;
        this.email = string4;
        this.putUserTrait("id", string2);
        this.putUserTrait("name", string3);
        this.putUserTrait("email", string4);
    }

    public void putAccountTrait(String string2, float f2) {
        this.putUserTrait("account_" + string2, f2);
    }

    public void putAccountTrait(String string2, int n2) {
        this.putUserTrait("account_" + string2, n2);
    }

    public void putAccountTrait(String string2, String string3) {
        this.putUserTrait("account_" + string2, string3);
    }

    public void putAccountTrait(String string2, Date date) {
        this.putUserTrait("account_" + string2, date);
    }

    public void putAccountTrait(String string2, boolean bl2) {
        this.putUserTrait("account_" + string2, bl2);
    }

    public void putUserTrait(String string2, float f2) {
        this.userTraits.put(string2, Float.valueOf(f2));
    }

    public void putUserTrait(String string2, int n2) {
        this.userTraits.put(string2, n2);
    }

    public void putUserTrait(String string2, String string3) {
        this.userTraits.put(string2, string3);
    }

    public void putUserTrait(String string2, Date date) {
        this.userTraits.put(string2, date.getTime() / 1000);
    }

    public void putUserTrait(String string2, boolean bl2) {
        this.userTraits.put(string2, bl2);
    }

    public void setCustomFields(Map<String, String> map) {
        this.customFields = map;
    }

    public void setForumId(int n2) {
        this.forumId = n2;
    }

    public void setShowContactUs(boolean bl2) {
        this.showContactUs = bl2;
    }

    public void setShowForum(boolean bl2) {
        this.showForum = bl2;
    }

    public void setShowKnowledgeBase(boolean bl2) {
        this.showKnowledgeBase = bl2;
    }

    public void setShowPostIdea(boolean bl2) {
        this.showPostIdea = bl2;
    }

    public void setTopicId(int n2) {
        this.topicId = n2;
    }

    public boolean shouldShowContactUs() {
        if (Session.getInstance().getClientConfig() != null && !Session.getInstance().getClientConfig().isTicketSystemEnabled()) {
            return false;
        }
        return this.showContactUs;
    }

    public boolean shouldShowForum() {
        if (Session.getInstance().getClientConfig() != null && !Session.getInstance().getClientConfig().isFeedbackEnabled()) {
            return false;
        }
        return this.showForum;
    }

    public boolean shouldShowKnowledgeBase() {
        if (Session.getInstance().getClientConfig() != null && !Session.getInstance().getClientConfig().isTicketSystemEnabled()) {
            return false;
        }
        return this.showKnowledgeBase;
    }

    public boolean shouldShowPostIdea() {
        if (Session.getInstance().getClientConfig() != null && !Session.getInstance().getClientConfig().isFeedbackEnabled()) {
            return false;
        }
        return this.showPostIdea;
    }
}

