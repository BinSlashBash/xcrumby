package com.crumby.lib.router;

import com.crumby.impl.derpibooru.DerpibooruFragment;
import com.crumby.impl.e621.e621Fragment;
import com.crumby.impl.gelbooru.GelbooruFragment;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentLink {
    public static final int INVALID = -1;
    protected String baseUrl;
    protected String displayName;
    private boolean doNotMatchBaseUrl;
    private String faviconUrl;
    private boolean hideBaseUrl;
    private boolean mandatory;
    protected boolean suggestable;

    public FragmentLink(String displayName, String baseUrl, String faviconUrl, int mandatory) {
        boolean z = true;
        this.displayName = displayName;
        this.baseUrl = baseUrl;
        if (faviconUrl == null) {
            setFavicon();
        } else {
            this.faviconUrl = faviconUrl;
        }
        if (mandatory != 1) {
            z = false;
        }
        this.mandatory = z;
    }

    public void setFavicon() {
        try {
            String baseUrl = this.baseUrl;
            if (baseUrl == null || baseUrl.contains("crumby://")) {
                this.faviconUrl = "https://lh4.ggpht.com/nCBI6wC4Liyg4bKN2qe7UrPEDndmHHRUtomx1fHK2jfxr1ccmkktk6eJ2ThnoDqoD7Y=w300-rw";
                return;
            }
            String prefix = "http://";
            String suffix = "/favicon.ico";
            if (baseUrl.startsWith("www.")) {
                baseUrl = "http://" + baseUrl.substring("www.".length());
            }
            if (baseUrl != null) {
                if (baseUrl.contains(DerpibooruFragment.ROOT_NAME) || baseUrl.contains(e621Fragment.ROOT_NAME) || baseUrl.contains("sankakucomplex")) {
                    prefix = "https://";
                } else if (baseUrl.contains(GelbooruFragment.ROOT_NAME)) {
                    suffix = "/favicon.png";
                }
            }
            this.faviconUrl = prefix + new URL(baseUrl).getHost() + suffix;
        } catch (MalformedURLException e) {
        }
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getFaviconUrl() {
        return this.faviconUrl;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public boolean isSuggestable() {
        return this.suggestable;
    }

    public String getRegexUrl() {
        return null;
    }

    public int fuzzyMatchUrl(Pattern regexPattern) {
        return fuzzyMatch(regexPattern, this.baseUrl);
    }

    private int fuzzyMatch(Pattern pattern, String capture) {
        Matcher m = pattern.matcher(capture);
        if (m.find()) {
            return m.start(1);
        }
        return INVALID;
    }

    public int fuzzyMatchDisplayName(Pattern regexPattern) {
        if (this.displayName == null) {
            return INVALID;
        }
        return fuzzyMatch(regexPattern, this.displayName);
    }

    public boolean baseUrlIsHidden() {
        return this.hideBaseUrl;
    }

    public void setHideBaseUrl(boolean b) {
        this.hideBaseUrl = b;
    }

    public void setSuggestable(boolean suggestable) {
        this.suggestable = suggestable;
    }

    public String[] getAsArray() {
        return new String[]{this.displayName, this.baseUrl};
    }

    public boolean isMandatory() {
        return this.mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}
