package com.uservoice.uservoicesdk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private Map<String, String> customFields;
    private String email;
    private int forumId;
    private String guid;
    private String key;
    private String name;
    private String secret;
    private boolean showContactUs;
    private boolean showForum;
    private boolean showKnowledgeBase;
    private boolean showPostIdea;
    private String site;
    private int topicId;
    private Map<String, Object> userTraits;

    public Config(String site) {
        this.customFields = new HashMap();
        this.topicId = -1;
        this.forumId = -1;
        this.showForum = true;
        this.showPostIdea = true;
        this.showContactUs = true;
        this.showKnowledgeBase = true;
        this.userTraits = new HashMap();
        this.site = site;
    }

    public Config(String site, String key, String secret) {
        this.customFields = new HashMap();
        this.topicId = -1;
        this.forumId = -1;
        this.showForum = true;
        this.showPostIdea = true;
        this.showContactUs = true;
        this.showKnowledgeBase = true;
        this.userTraits = new HashMap();
        this.site = site;
        this.key = key;
        this.secret = secret;
    }

    public String getSite() {
        return this.site;
    }

    public String getKey() {
        return this.key;
    }

    public String getSecret() {
        return this.secret;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public String getGuid() {
        return this.guid;
    }

    public Map<String, String> getCustomFields() {
        return this.customFields;
    }

    public void setCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
    }

    public int getTopicId() {
        return this.topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getForumId() {
        if (this.forumId != -1 || Session.getInstance().getClientConfig() == null) {
            return this.forumId;
        }
        return Session.getInstance().getClientConfig().getDefaultForumId();
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public boolean shouldShowForum() {
        if (Session.getInstance().getClientConfig() == null || Session.getInstance().getClientConfig().isFeedbackEnabled()) {
            return this.showForum;
        }
        return false;
    }

    public void setShowForum(boolean showForum) {
        this.showForum = showForum;
    }

    public boolean shouldShowPostIdea() {
        if (Session.getInstance().getClientConfig() == null || Session.getInstance().getClientConfig().isFeedbackEnabled()) {
            return this.showPostIdea;
        }
        return false;
    }

    public void setShowPostIdea(boolean showPostIdea) {
        this.showPostIdea = showPostIdea;
    }

    public boolean shouldShowContactUs() {
        if (Session.getInstance().getClientConfig() == null || Session.getInstance().getClientConfig().isTicketSystemEnabled()) {
            return this.showContactUs;
        }
        return false;
    }

    public void setShowContactUs(boolean showContactUs) {
        this.showContactUs = showContactUs;
    }

    public boolean shouldShowKnowledgeBase() {
        if (Session.getInstance().getClientConfig() == null || Session.getInstance().getClientConfig().isTicketSystemEnabled()) {
            return this.showKnowledgeBase;
        }
        return false;
    }

    public void setShowKnowledgeBase(boolean showKnowledgeBase) {
        this.showKnowledgeBase = showKnowledgeBase;
    }

    public void identifyUser(String id, String name, String email) {
        this.guid = id;
        this.name = name;
        this.email = email;
        putUserTrait("id", id);
        putUserTrait("name", name);
        putUserTrait("email", email);
    }

    public void putUserTrait(String key, String value) {
        this.userTraits.put(key, value);
    }

    public void putUserTrait(String key, int value) {
        this.userTraits.put(key, Integer.valueOf(value));
    }

    public void putUserTrait(String key, boolean value) {
        this.userTraits.put(key, Boolean.valueOf(value));
    }

    public void putUserTrait(String key, float value) {
        this.userTraits.put(key, Float.valueOf(value));
    }

    public void putUserTrait(String key, Date value) {
        this.userTraits.put(key, Long.valueOf(value.getTime() / 1000));
    }

    public void putAccountTrait(String key, String value) {
        putUserTrait("account_" + key, value);
    }

    public void putAccountTrait(String key, int value) {
        putUserTrait("account_" + key, value);
    }

    public void putAccountTrait(String key, boolean value) {
        putUserTrait("account_" + key, value);
    }

    public void putAccountTrait(String key, float value) {
        putUserTrait("account_" + key, value);
    }

    public void putAccountTrait(String key, Date value) {
        putUserTrait("account_" + key, value);
    }

    public Map<String, Object> getUserTraits() {
        return this.userTraits;
    }
}
