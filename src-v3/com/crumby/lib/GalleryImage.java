package com.crumby.lib;

import android.graphics.drawable.LayerDrawable;
import com.crumby.Analytics;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.impl.pururin.PururinFragment;
import com.crumby.lib.widget.GalleryImageView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GalleryImage {
    public static final int NO_ICON = 0;
    private boolean animated;
    private ExtraAttributes attributes;
    private boolean checked;
    private List<ImageComment> comments;
    private String deferredThumbnail;
    private String description;
    private int errorFlag;
    private int height;
    private int icon;
    @JsonIgnore
    private LayerDrawable iconDrawable;
    private String id;
    private String imageUrl;
    private String linkUrl;
    private boolean linksToGallery;
    private CharSequence markdown;
    private int offset;
    private int page;
    private List<String> path;
    private int position;
    int pururin;
    private boolean reload;
    private String smallThumbnailUrl;
    private boolean split;
    private String subtitle;
    private String[] tags;
    private String thumbnailUrl;
    private String title;
    @JsonIgnore
    private boolean unfilled;
    private int viewGridWidth;
    private Set<GalleryImageView> views;
    private boolean visited;
    private int width;

    public class ErrorFlag {
        public static final int COULD_NOT_PARSE = 5;
        public static final int DELETED = 1;
        public static final int NO_ERROR = 0;
        public static final int RESTRICTED = 4;
        public static final int RESTRICTED_BY_USER = 3;
        public static final int RESTRICTED_MUST_LOGIN = 2;
    }

    public boolean hasError() {
        return this.errorFlag > 0;
    }

    public String printError() {
        String message;
        String linkUrl = getLinkUrl() != null ? getLinkUrl() : "null";
        switch (this.errorFlag) {
            case Std.STD_FILE /*1*/:
                message = "This image was deleted and is no longer available.";
                break;
            case Std.STD_URL /*2*/:
                message = "You can only see this image if you are logged into this images' website. If Crumby does not support logging in, contact the admin so he can add it!";
                break;
            case Std.STD_URI /*3*/:
                message = "This user has chosen to restrict access to their image to logged in users only!";
                break;
            case Std.STD_CLASS /*4*/:
                message = "The website will not allow you to see this image. Try opening it in your web browser?";
                break;
            case Std.STD_JAVA_TYPE /*5*/:
                message = "This image could not be parsed for an unspecified reason";
                break;
            default:
                message = "This image does not have an error";
                break;
        }
        Analytics.INSTANCE.newException(new Exception(linkUrl + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + message));
        return message;
    }

    public void setErrorFlag(int errorFlag) {
        this.errorFlag = errorFlag;
    }

    public int getErrorFlag() {
        return this.errorFlag;
    }

    public GalleryImage() {
        this.pururin = 0;
    }

    public GalleryImage(String linkUrl) {
        this.pururin = 0;
        setLinkUrl(linkUrl);
    }

    public GalleryImage(String thumbnailUrl, String linkUrl, String title) {
        this(linkUrl);
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
    }

    public GalleryImage(String thumbnailUrl, String linkUrl, String title, int width, int height) {
        this(thumbnailUrl, linkUrl, title);
        this.width = width;
        this.height = height;
    }

    public GalleryImage(String thumbnailUrl, String linkUrl, String title, int width, int height, int offset) {
        this(thumbnailUrl, linkUrl, title, width, height);
        this.offset = offset;
        this.split = true;
    }

    public GalleryImage(boolean unfilled) {
        this.pururin = 0;
        this.unfilled = unfilled;
    }

    public ExtraAttributes attr() {
        return this.attributes;
    }

    public void setAttributes(ExtraAttributes attributes) {
        this.attributes = attributes;
    }

    public int getViewGridWidth() {
        return this.viewGridWidth;
    }

    @JsonIgnore
    public void setViewGridWidth(int viewGridWidth) {
        this.viewGridWidth = viewGridWidth;
    }

    public void copy(GalleryImage other) {
        if (other.smallThumbnailUrl != null) {
            this.smallThumbnailUrl = other.smallThumbnailUrl;
        }
        if (other.thumbnailUrl != null) {
            this.thumbnailUrl = other.thumbnailUrl;
        }
        if (other.imageUrl != null) {
            this.imageUrl = other.imageUrl;
        }
        if (other.comments != null) {
            this.comments = other.comments;
        }
        if (other.animated) {
            this.animated = true;
        }
        if (other.width != 0) {
            this.width = other.width;
        }
        if (other.height != 0) {
            this.height = other.height;
        }
        if (!(other.title == null || other.title.equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
            this.title = other.title;
        }
        if (!(other.description == null || other.description.equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
            this.description = other.description;
        }
        if (other.attributes != null) {
            this.attributes = other.attributes;
        }
        if (other.tags != null) {
            this.tags = other.tags;
        }
        this.errorFlag = other.errorFlag;
    }

    public boolean isALinkToGallery() {
        return this.linksToGallery;
    }

    public void setLinksToGallery(boolean linksToGallery) {
        this.linksToGallery = linksToGallery;
        if (linksToGallery) {
            this.icon = C0065R.drawable.ic_action_collection;
        } else {
            this.icon = 0;
        }
    }

    public int getPosition() {
        return this.position;
    }

    @JsonIgnore
    public void setPosition(int position) {
        this.position = position;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getLinkUrl() {
        return this.linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOffset() {
        return this.offset;
    }

    public boolean isSplit() {
        return this.split;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisited() {
        return this.visited;
    }

    @JsonIgnore
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean load() {
        return false;
    }

    @JsonIgnore
    public boolean isUnfilled() {
        return this.unfilled;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAnimated() {
        return this.animated;
    }

    private void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        if (imageUrl != null && imageUrl.endsWith(".gif")) {
            setAnimated(true);
        }
    }

    public boolean isChecked() {
        return this.checked;
    }

    @JsonIgnore
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }

    public boolean needsToBeReloadedInImageFragment() {
        return this.reload;
    }

    public void addView(GalleryImageView view) {
        if (this.views == null) {
            this.views = new HashSet();
        }
        this.views.add(view);
    }

    public void removeView(GalleryImageView view) {
        this.views.remove(view);
    }

    public void clearViews() {
        if (this.views != null) {
            this.views.clear();
        }
    }

    public void updateViews() {
        if (this.views != null) {
            for (GalleryImageView view : this.views) {
                view.update();
            }
        }
    }

    public String getRequestedFilename() {
        String filename = this.imageUrl.substring(this.imageUrl.lastIndexOf(47) + 1).replaceAll("\\s+", "_").replace(":", "_").replace("%20", UnsupportedUrlFragment.DISPLAY_NAME).replace("%", UnsupportedUrlFragment.DISPLAY_NAME);
        if (filename.contains("?")) {
            filename = filename.substring(0, filename.lastIndexOf("?"));
        }
        if (filename.startsWith("Konachan") && filename.length() > 50) {
            filename = filename.substring(filename.length() - 50);
        }
        if (!this.imageUrl.contains(PururinFragment.ROOT_NAME) || !filename.contains("#")) {
            return filename;
        }
        return filename.substring(filename.indexOf("#") + 1) + filename.substring(0, filename.indexOf("#") - 1);
    }

    public List<String> getPath() {
        return this.path;
    }

    @JsonIgnore
    public void setPath(List<String> path) {
        this.path = path;
    }

    public void modifyPath(List<String> path) {
        if (this.path == null) {
            setPath(path);
            return;
        }
        this.path.clear();
        this.path.addAll(path);
    }

    public String buildPath() {
        String pathString = UnsupportedUrlFragment.DISPLAY_NAME;
        for (String directory : this.path) {
            pathString = pathString + directory + DeviceFragment.REGEX_BASE;
        }
        return pathString;
    }

    public String getSmallThumbnailUrl() {
        return this.smallThumbnailUrl != null ? this.smallThumbnailUrl : this.thumbnailUrl;
    }

    public void setSmallThumbnailUrl(String smallThumbnailUrl) {
        this.smallThumbnailUrl = smallThumbnailUrl;
    }

    public boolean hasIcon() {
        return ((this.icon == -1 || this.icon == 0) && this.iconDrawable == null) ? false : true;
    }

    public int getIcon() {
        return this.icon;
    }

    @JsonIgnore
    public void setIcon(LayerDrawable icon) {
        this.iconDrawable = icon;
        this.icon = 0;
    }

    @JsonIgnore
    public void setIcon(int icon) {
        this.icon = icon;
        this.iconDrawable = null;
    }

    public LayerDrawable getIconDrawable() {
        return this.iconDrawable;
    }

    public void newPath() {
        if (this.path != null) {
            List<String> path = new ArrayList();
            path.addAll(this.path);
            this.path = path;
        }
    }

    public List<ImageComment> getComments() {
        return this.comments;
    }

    public void setComments(List<ImageComment> comments) {
        this.comments = comments;
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
