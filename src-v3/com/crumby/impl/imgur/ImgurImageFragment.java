package com.crumby.impl.imgur;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
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

public class ImgurImageFragment extends GalleryImageFragment implements ScrollViewListener {
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

    class ImgurCommentDownloadTask extends AsyncTask<String, Void, List<ImageComment>> {
        Exception fetchingException;

        ImgurCommentDownloadTask() {
        }

        private ArrayList<ImageComment> recurseComments(JsonArray comments) {
            ArrayList<ImageComment> imageComments = new ArrayList();
            try {
                Iterator i$ = comments.iterator();
                while (i$.hasNext()) {
                    JsonObject comment = ((JsonElement) i$.next()).getAsJsonObject();
                    imageComments.add(new ImageComment(comment.get("author").getAsString(), comment.get("comment").getAsString(), recurseComments(comment.get("children").getAsJsonArray())));
                }
            } catch (NullPointerException e) {
            }
            return imageComments;
        }

        protected List<ImageComment> doInBackground(String... params) {
            try {
                return recurseComments(new JsonParser().parse(GalleryProducer.fetchUrl("https://imgur-apiv3.p.mashape.com/3/gallery/" + params[0] + "/comments/", ImgurProducer.API_CREDENTIALS, ImgurProducer.MASHAPE_AUTH)).getAsJsonObject().get("data").getAsJsonArray());
            } catch (Exception e) {
                this.fetchingException = e;
                return null;
            }
        }

        protected void onPostExecute(List<ImageComment> comments) {
            if (this.fetchingException != null) {
                ImgurImageFragment.this.indicateMetadataError("Error loading comments", this.fetchingException);
                return;
            }
            ImgurImageFragment.this.getImage().setComments(comments.subList(0, Math.min(comments.size(), 5)));
            if (ImgurImageFragment.this.getComments().isEmpty()) {
                ImgurImageFragment.this.indicateMetadataNotFound();
            } else {
                ImgurImageFragment.this.imgurComments.initialize(ImgurImageFragment.this.getComments());
            }
        }
    }

    static {
        SINGLE_IMAGE_REGEX = GalleryViewerFragment.zeroOrOneTimes("/gallery") + DeviceFragment.REGEX_BASE + GalleryViewerFragment.captureMinimumLength("[a-zA-Z0-9]", 2);
        SUBREDDIT_SINGLE_IMAGE_REGEX = ImgurSubredditFragment.SUBREDDIT_REGEX + DeviceFragment.REGEX_BASE + CAPTURE_ALPHANUMERIC_REPEATING;
        ALBUM_SINGLE_IMAGE_REGEX = ImgurAlbumFragment.ALBUM_REGEX + DeviceFragment.REGEX_BASE + CAPTURE_ALPHANUMERIC_REPEATING;
        REGEX_URL = ImgurFragment.REGEX_BASE + GalleryViewerFragment.matchOne(SINGLE_IMAGE_REGEX, SUBREDDIT_SINGLE_IMAGE_REGEX, ALBUM_SINGLE_IMAGE_REGEX) + "/?" + "\\??" + "[^/]*";
        BREADCRUMB_PARENT_CLASS = ImgurAlbumFragment.class;
    }

    private void alterBreadcrumbs(List<Breadcrumb> breadcrumbs, String url) {
        for (Breadcrumb breadcrumb : breadcrumbs) {
            FragmentIndex index = breadcrumb.getFragmentIndex();
            if (!index.isIndexOf(ImgurAlbumFragment.class)) {
                if (index.isIndexOf(ImgurSubredditFragment.class)) {
                }
            }
            breadcrumb.alter(url);
            return;
        }
    }

    protected ViewGroup inflateMetadataLayout(LayoutInflater inflater) {
        this.imgurComments = (ImageCommentsView) inflater.inflate(C0065R.layout.image_comments, null);
        return this.imgurComments;
    }

    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbList) {
        List<Breadcrumb> breadcrumbs = breadcrumbList.getChildren();
        String url = getImage().getLinkUrl();
        String subredditId = GalleryViewerFragment.matchIdFromUrl(ImgurSubredditFragment.REGEX_URL + DeviceFragment.REGEX_URL, url);
        if (subredditId != null) {
            alterBreadcrumbs(breadcrumbs, ImgurSubredditFragment.BASE_URL + subredditId);
        } else {
            String albumId = GalleryViewerFragment.matchIdFromUrl(ImgurAlbumFragment.REGEX_URL + DeviceFragment.REGEX_URL, url);
            if (albumId != null) {
                alterBreadcrumbs(breadcrumbs, ImgurAlbumFragment.BASE_URL + albumId);
            }
        }
        super.setBreadcrumbs(breadcrumbList);
    }

    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (scrollView.getChildAt(scrollView.getChildCount() - 1).getBottom() - (scrollView.getHeight() + scrollView.getScrollY()) < 50) {
            this.imgurComments.addComments(false);
        }
    }

    private List<ImageComment> getComments() {
        return getImage().getComments();
    }

    private boolean isSubredditImage() {
        return GalleryViewerFragment.matchIdFromUrl(new StringBuilder().append(ImgurSubredditFragment.REGEX_URL).append(DeviceFragment.REGEX_URL).toString(), getUrl()) != null ? BREADCRUMB_ALT_NAME : false;
    }

    private boolean isAlbumImage() {
        return GalleryViewerFragment.matchIdFromUrl(new StringBuilder().append(ImgurAlbumFragment.REGEX_URL).append(DeviceFragment.REGEX_URL).toString(), getUrl()) != null ? BREADCRUMB_ALT_NAME : false;
    }

    protected void fillMetadataView() {
        this.threshold = getImageScrollView().getScrollY();
        getImageScrollView().setScrollViewListener(this);
        if (getComments() == null) {
            new ImgurCommentDownloadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{GalleryViewerFragment.matchIdFromUrl(REGEX_URL, getUrl())});
            return;
        }
        this.imgurComments.initialize(getComments());
    }

    protected GalleryProducer createProducer() {
        return new ImgurImageProducer();
    }
}
