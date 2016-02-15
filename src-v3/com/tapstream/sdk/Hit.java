package com.tapstream.sdk;

import android.support.v4.view.MotionEventCompat;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.commons.codec.binary.Hex;

public class Hit {
    private String encodedTrackerName;
    private StringBuilder tags;
    private String trackerName;

    public interface CompletionHandler {
        void complete(Response response);
    }

    public Hit(String str) {
        this.tags = null;
        this.trackerName = str;
        try {
            this.encodedTrackerName = URLEncoder.encode(str, Hex.DEFAULT_CHARSET_NAME).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            Logging.log(4, "Tapstream Error: Could not encode hit tracker name, exception=%s", e.getMessage());
        }
    }

    public void addTag(String str) {
        if (str.length() > MotionEventCompat.ACTION_MASK) {
            Logging.log(5, "Tapstream Warning: Hit tag exceeds 255 characters, it will not be included in the post (tag=%s)", str);
            return;
        }
        try {
            String replace = URLEncoder.encode(str, Hex.DEFAULT_CHARSET_NAME).replace("+", "%20");
            if (this.tags == null) {
                this.tags = new StringBuilder("__ts=");
            } else {
                this.tags.append(",");
            }
            this.tags.append(replace);
        } catch (UnsupportedEncodingException e) {
            Logging.log(4, "Tapstream Error: Could not encode hit tracker tag %s, exception=%s", str, e.getMessage());
        }
    }

    public String getEncodedTrackerName() {
        return this.encodedTrackerName;
    }

    public String getPostData() {
        return this.tags == null ? UnsupportedUrlFragment.DISPLAY_NAME : this.tags.toString();
    }

    public String getTrackerName() {
        return this.trackerName;
    }
}
