/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.imgur;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.imgur.ImgurAlbumFragment;
import com.crumby.impl.imgur.ImgurFragment;
import com.crumby.impl.imgur.ImgurImageProducer;
import com.crumby.impl.imgur.ImgurSubredditFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.widget.ImageScrollView;
import com.crumby.lib.widget.firstparty.ImageCommentsView;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.crumby.lib.widget.thirdparty.ObservableScrollView;
import com.crumby.lib.widget.thirdparty.ScrollViewListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

public class ImgurImageFragment
extends GalleryImageFragment
implements ScrollViewListener {
    public static final String ALBUM_SINGLE_IMAGE_REGEX;
    public static final String BASE_URL = "http://imgur.com/gallery/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final String BREADCRUMB_NAME = "i/";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "imgur.com/gallery/";
    public static final String SINGLE_IMAGE_REGEX;
    public static final String SUBREDDIT_SINGLE_IMAGE_REGEX;
    public static final String SUFFIX = "/gallery/";
    int comment;
    private ImageCommentsView imgurComments;
    private long last;
    private View loading;
    private int threshold;
    private View title;

    static {
        SINGLE_IMAGE_REGEX = ImgurImageFragment.zeroOrOneTimes("/gallery") + "/" + ImgurImageFragment.captureMinimumLength("[a-zA-Z0-9]", 2);
        SUBREDDIT_SINGLE_IMAGE_REGEX = ImgurSubredditFragment.SUBREDDIT_REGEX + "/" + CAPTURE_ALPHANUMERIC_REPEATING;
        ALBUM_SINGLE_IMAGE_REGEX = ImgurAlbumFragment.ALBUM_REGEX + "/" + CAPTURE_ALPHANUMERIC_REPEATING;
        REGEX_URL = ImgurFragment.REGEX_BASE + ImgurImageFragment.matchOne(SINGLE_IMAGE_REGEX, SUBREDDIT_SINGLE_IMAGE_REGEX, ALBUM_SINGLE_IMAGE_REGEX) + "/?" + "\\??" + "[^/]*";
        BREADCRUMB_PARENT_CLASS = ImgurAlbumFragment.class;
    }

    private void alterBreadcrumbs(List<Breadcrumb> object, String string2) {
        object = object.iterator();
        while (object.hasNext()) {
            Breadcrumb breadcrumb = (Breadcrumb)((Object)object.next());
            FragmentIndex fragmentIndex = breadcrumb.getFragmentIndex();
            if (!fragmentIndex.isIndexOf(ImgurAlbumFragment.class) && !fragmentIndex.isIndexOf(ImgurSubredditFragment.class)) continue;
            breadcrumb.alter(string2);
            break;
        }
    }

    private List<ImageComment> getComments() {
        return this.getImage().getComments();
    }

    private boolean isAlbumImage() {
        if (ImgurImageFragment.matchIdFromUrl(ImgurAlbumFragment.REGEX_URL + "/.*", this.getUrl()) != null) {
            return true;
        }
        return false;
    }

    private boolean isSubredditImage() {
        if (ImgurImageFragment.matchIdFromUrl(ImgurSubredditFragment.REGEX_URL + "/.*", this.getUrl()) != null) {
            return true;
        }
        return false;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new ImgurImageProducer();
    }

    @Override
    protected void fillMetadataView() {
        this.threshold = this.getImageScrollView().getScrollY();
        this.getImageScrollView().setScrollViewListener(this);
        if (this.getComments() == null) {
            new ImgurCommentDownloadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new String[]{GalleryViewerFragment.matchIdFromUrl(REGEX_URL, this.getUrl())});
            return;
        }
        this.imgurComments.initialize(this.getComments());
    }

    @Override
    protected ViewGroup inflateMetadataLayout(LayoutInflater layoutInflater) {
        this.imgurComments = (ImageCommentsView)layoutInflater.inflate(2130903088, null);
        return this.imgurComments;
    }

    @Override
    public void onScrollChanged(ObservableScrollView observableScrollView, int n2, int n3, int n4, int n5) {
        if (observableScrollView.getChildAt(observableScrollView.getChildCount() - 1).getBottom() - (observableScrollView.getHeight() + observableScrollView.getScrollY()) < 50) {
            this.imgurComments.addComments(false);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbListModifier) {
        List<Breadcrumb> list = breadcrumbListModifier.getChildren();
        String string2 = this.getImage().getLinkUrl();
        String string3 = ImgurImageFragment.matchIdFromUrl(ImgurSubredditFragment.REGEX_URL + "/.*", string2);
        if (string3 != null) {
            this.alterBreadcrumbs(list, "http://imgur.com/r/" + string3);
        } else {
            string2 = ImgurImageFragment.matchIdFromUrl(ImgurAlbumFragment.REGEX_URL + "/.*", string2);
            if (string2 != null) {
                this.alterBreadcrumbs(list, "http://imgur.com/a/" + string2);
            }
        }
        super.setBreadcrumbs(breadcrumbListModifier);
    }

    class ImgurCommentDownloadTask
    extends AsyncTask<String, Void, List<ImageComment>> {
        Exception fetchingException;

        ImgurCommentDownloadTask() {
        }

        private ArrayList<ImageComment> recurseComments(JsonArray object) {
            ArrayList<ImageComment> arrayList = new ArrayList<ImageComment>();
            try {
                object = object.iterator();
                while (object.hasNext()) {
                    JsonObject jsonObject = ((JsonElement)object.next()).getAsJsonObject();
                    arrayList.add(new ImageComment(jsonObject.get("author").getAsString(), jsonObject.get("comment").getAsString(), this.recurseComments(jsonObject.get("children").getAsJsonArray())));
                }
            }
            catch (NullPointerException var1_2) {
                // empty catch block
            }
            return arrayList;
        }

        protected /* varargs */ List<ImageComment> doInBackground(String ... arrayList) {
            try {
                arrayList = GalleryProducer.fetchUrl("https://imgur-apiv3.p.mashape.com/3/gallery/" + arrayList[0] + "/comments/", "Client-ID ac562464e4b98f8", "F7x7cTikVsmshJJ4wubKUnkmJkIyp19IUJKjsnJegrKYFdu0OP");
                arrayList = this.recurseComments(new JsonParser().parse((String)((Object)arrayList)).getAsJsonObject().get("data").getAsJsonArray());
                return arrayList;
            }
            catch (Exception var1_2) {
                this.fetchingException = var1_2;
                return null;
            }
        }

        protected void onPostExecute(List<ImageComment> list) {
            if (this.fetchingException != null) {
                ImgurImageFragment.this.indicateMetadataError("Error loading comments", this.fetchingException);
                return;
            }
            ImgurImageFragment.this.getImage().setComments(list.subList(0, Math.min(list.size(), 5)));
            if (ImgurImageFragment.this.getComments().isEmpty()) {
                ImgurImageFragment.this.indicateMetadataNotFound();
                return;
            }
            ImgurImageFragment.this.imgurComments.initialize(ImgurImageFragment.this.getComments());
        }
    }

}

