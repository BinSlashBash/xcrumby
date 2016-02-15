package com.crumby.impl.derpibooru;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.gson.JsonArray;

public abstract class DerpibooruLoggedInUserProducer extends DerpibooruProducer {
    protected abstract String getSuffix();

    protected JsonArray getDerpImages(int pageNumber) throws Exception {
        String pageArg;
        if (pageNumber == 0) {
            pageArg = UnsupportedUrlFragment.DISPLAY_NAME;
        } else {
            pageArg = "?page=" + (pageNumber + 1);
        }
        return getDerpImages(DerpibooruFragment.BASE_URL + getSuffix() + ".json" + pageArg, "images");
    }
}
