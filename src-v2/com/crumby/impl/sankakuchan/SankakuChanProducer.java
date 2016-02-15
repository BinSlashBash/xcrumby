/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.sankakuchan;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.impl.sankakuchan.SankakuChanFragment;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.universal.UniversalProducer;

public class SankakuChanProducer
extends UniversalProducer {
    @Override
    protected String getBaseUrl() {
        return "https://chan.sankakucomplex.com";
    }

    @Override
    public String getHostUrl() {
        String string2 = SankakuChanProducer.getQueryParameter(Uri.parse((String)super.getHostUrl()), super.getHostUrl(), "tags");
        String string3 = this.getBaseUrl() + "?tags=";
        String string4 = string2;
        if (string2 == null) {
            string4 = string2;
            if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(SankakuChanFragment.class)) {
                string4 = "rating:safe";
            }
        }
        string2 = string3;
        if (string4 != null) {
            string2 = string3;
            if (!string4.equals("")) {
                string2 = string3 + Uri.encode((String)string4);
            }
        }
        return string2 + Uri.encode((String)GalleryViewer.getBlacklist());
    }

    @Override
    protected String getRegexForMatchingId() {
        return SankakuChanFragment.REGEX_URL;
    }

    @Override
    protected String getScriptName() {
        return SankakuChanFragment.class.getSimpleName();
    }
}

