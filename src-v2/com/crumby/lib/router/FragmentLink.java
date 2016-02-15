/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.router;

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

    public FragmentLink() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public FragmentLink(String string2, String string3, String string4, int n2) {
        boolean bl2 = true;
        this.displayName = string2;
        this.baseUrl = string3;
        if (string4 == null) {
            this.setFavicon();
        } else {
            this.faviconUrl = string4;
        }
        if (n2 != 1) {
            bl2 = false;
        }
        this.mandatory = bl2;
    }

    private int fuzzyMatch(Pattern object, String string2) {
        if ((object = object.matcher(string2)).find()) {
            return object.start(1);
        }
        return -1;
    }

    public boolean baseUrlIsHidden() {
        return this.hideBaseUrl;
    }

    public int fuzzyMatchDisplayName(Pattern pattern) {
        if (this.displayName == null) {
            return -1;
        }
        return this.fuzzyMatch(pattern, this.displayName);
    }

    public int fuzzyMatchUrl(Pattern pattern) {
        return this.fuzzyMatch(pattern, this.baseUrl);
    }

    public String[] getAsArray() {
        return new String[]{this.displayName, this.baseUrl};
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getFaviconUrl() {
        return this.faviconUrl;
    }

    public String getRegexUrl() {
        return null;
    }

    public boolean isMandatory() {
        return this.mandatory;
    }

    public boolean isSuggestable() {
        return this.suggestable;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setFavicon() {
        block6 : {
            block7 : {
                try {
                    var2_1 = this.baseUrl;
                    if (var2_1 != null && !var2_1.contains("crumby://")) {
                        var4_2 = "http://";
                        var5_3 = "/favicon.ico";
                        var1_4 = var2_1;
                        if (var2_1.startsWith("www.")) {
                            var1_4 = "http://" + var2_1.substring("www.".length());
                        }
                        var2_1 = var4_2;
                        var3_6 = var5_3;
                        if (var1_4 == null) break block6;
                        if (!(var1_4.contains("derpibooru.org") || var1_4.contains("e621.net") || var1_4.contains("sankakucomplex"))) {
                            var2_1 = var4_2;
                            var3_6 = var5_3;
                            if (var1_4.contains("gelbooru.com")) {
                                var3_6 = "/favicon.png";
                                var2_1 = var4_2;
                                ** GOTO lbl28
                            } else {
                                ** GOTO lbl20
                            }
                        }
                        break block7;
lbl20: // 2 sources:
                        break block6;
                    }
                    this.faviconUrl = "https://lh4.ggpht.com/nCBI6wC4Liyg4bKN2qe7UrPEDndmHHRUtomx1fHK2jfxr1ccmkktk6eJ2ThnoDqoD7Y=w300-rw";
                    return;
                }
                catch (MalformedURLException var1_5) {
                    return;
                }
            }
            var2_1 = "https://";
            var3_6 = var5_3;
        }
        this.faviconUrl = var2_1 + new URL(var1_4).getHost() + var3_6;
    }

    public void setHideBaseUrl(boolean bl2) {
        this.hideBaseUrl = bl2;
    }

    public void setMandatory(boolean bl2) {
        this.mandatory = bl2;
    }

    public void setSuggestable(boolean bl2) {
        this.suggestable = bl2;
    }
}

