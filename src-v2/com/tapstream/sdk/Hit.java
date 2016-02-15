/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

import com.tapstream.sdk.Logging;
import com.tapstream.sdk.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Hit {
    private String encodedTrackerName;
    private StringBuilder tags = null;
    private String trackerName;

    public Hit(String string2) {
        this.trackerName = string2;
        try {
            this.encodedTrackerName = URLEncoder.encode(string2, "UTF-8").replace("+", "%20");
            return;
        }
        catch (UnsupportedEncodingException var1_2) {
            Logging.log(4, "Tapstream Error: Could not encode hit tracker name, exception=%s", var1_2.getMessage());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addTag(String string2) {
        String string3;
        block4 : {
            block3 : {
                if (string2.length() > 255) {
                    Logging.log(5, "Tapstream Warning: Hit tag exceeds 255 characters, it will not be included in the post (tag=%s)", string2);
                    return;
                }
                try {
                    string3 = URLEncoder.encode(string2, "UTF-8").replace("+", "%20");
                    if (this.tags != null) break block3;
                    this.tags = new StringBuilder("__ts=");
                    break block4;
                }
                catch (UnsupportedEncodingException var2_3) {
                    Logging.log(4, "Tapstream Error: Could not encode hit tracker tag %s, exception=%s", string2, var2_3.getMessage());
                    return;
                }
            }
            this.tags.append(",");
        }
        this.tags.append(string3);
    }

    public String getEncodedTrackerName() {
        return this.encodedTrackerName;
    }

    public String getPostData() {
        if (this.tags == null) {
            return "";
        }
        return this.tags.toString();
    }

    public String getTrackerName() {
        return this.trackerName;
    }

    public static interface CompletionHandler {
        public void complete(Response var1);
    }

}

