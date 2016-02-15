/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.LayerDrawable
 */
package com.crumby.lib;

import android.graphics.drawable.LayerDrawable;
import com.crumby.Analytics;
import com.crumby.lib.ExtraAttributes;
import com.crumby.lib.ImageComment;
import com.crumby.lib.widget.GalleryImageView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown=1)
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
    int pururin = 0;
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

    public GalleryImage() {
    }

    public GalleryImage(String string2) {
        this.setLinkUrl(string2);
    }

    public GalleryImage(String string2, String string3, String string4) {
        this(string3);
        this.thumbnailUrl = string2;
        this.title = string4;
    }

    public GalleryImage(String string2, String string3, String string4, int n2, int n3) {
        this(string2, string3, string4);
        this.width = n2;
        this.height = n3;
    }

    public GalleryImage(String string2, String string3, String string4, int n2, int n3, int n4) {
        this(string2, string3, string4, n2, n3);
        this.offset = n4;
        this.split = true;
    }

    public GalleryImage(boolean bl2) {
        this.unfilled = bl2;
    }

    private void setAnimated(boolean bl2) {
        this.animated = bl2;
    }

    public void addView(GalleryImageView galleryImageView) {
        if (this.views == null) {
            this.views = new HashSet<GalleryImageView>();
        }
        this.views.add(galleryImageView);
    }

    public ExtraAttributes attr() {
        return this.attributes;
    }

    public String buildPath() {
        String string2 = "";
        for (String string3 : this.path) {
            string2 = string2 + string3 + "/";
        }
        return string2;
    }

    public void clearViews() {
        if (this.views == null) {
            return;
        }
        this.views.clear();
    }

    public void copy(GalleryImage galleryImage) {
        if (galleryImage.smallThumbnailUrl != null) {
            this.smallThumbnailUrl = galleryImage.smallThumbnailUrl;
        }
        if (galleryImage.thumbnailUrl != null) {
            this.thumbnailUrl = galleryImage.thumbnailUrl;
        }
        if (galleryImage.imageUrl != null) {
            this.imageUrl = galleryImage.imageUrl;
        }
        if (galleryImage.comments != null) {
            this.comments = galleryImage.comments;
        }
        if (galleryImage.animated) {
            this.animated = true;
        }
        if (galleryImage.width != 0) {
            this.width = galleryImage.width;
        }
        if (galleryImage.height != 0) {
            this.height = galleryImage.height;
        }
        if (galleryImage.title != null && !galleryImage.title.equals("")) {
            this.title = galleryImage.title;
        }
        if (galleryImage.description != null && !galleryImage.description.equals("")) {
            this.description = galleryImage.description;
        }
        if (galleryImage.attributes != null) {
            this.attributes = galleryImage.attributes;
        }
        if (galleryImage.tags != null) {
            this.tags = galleryImage.tags;
        }
        this.errorFlag = galleryImage.errorFlag;
    }

    public List<ImageComment> getComments() {
        return this.comments;
    }

    public String getDescription() {
        return this.description;
    }

    public int getErrorFlag() {
        return this.errorFlag;
    }

    public int getHeight() {
        return this.height;
    }

    public int getIcon() {
        return this.icon;
    }

    public LayerDrawable getIconDrawable() {
        return this.iconDrawable;
    }

    public String getId() {
        return this.id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getLinkUrl() {
        return this.linkUrl;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getPage() {
        return this.page;
    }

    public List<String> getPath() {
        return this.path;
    }

    public int getPosition() {
        return this.position;
    }

    public String getRequestedFilename() {
        String string2;
        String string3 = string2 = this.imageUrl.substring(this.imageUrl.lastIndexOf(47) + 1).replaceAll("\\s+", "_").replace(":", "_").replace("%20", "").replace("%", "");
        if (string2.contains("?")) {
            string3 = string2.substring(0, string2.lastIndexOf("?"));
        }
        string2 = string3;
        if (string3.startsWith("Konachan")) {
            string2 = string3;
            if (string3.length() > 50) {
                string2 = string3.substring(string3.length() - 50);
            }
        }
        string3 = string2;
        if (this.imageUrl.contains("pururin.com")) {
            string3 = string2;
            if (string2.contains("#")) {
                string3 = string2.substring(string2.indexOf("#") + 1);
                string3 = string3 + string2.substring(0, string2.indexOf("#") - 1);
            }
        }
        return string3;
    }

    public String getSmallThumbnailUrl() {
        if (this.smallThumbnailUrl != null) {
            return this.smallThumbnailUrl;
        }
        return this.thumbnailUrl;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public String[] getTags() {
        return this.tags;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public int getViewGridWidth() {
        return this.viewGridWidth;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean hasError() {
        if (this.errorFlag > 0) {
            return true;
        }
        return false;
    }

    public boolean hasIcon() {
        if (this.icon != -1 && this.icon != 0 || this.iconDrawable != null) {
            return true;
        }
        return false;
    }

    public boolean isALinkToGallery() {
        return this.linksToGallery;
    }

    public boolean isAnimated() {
        return this.animated;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public boolean isSplit() {
        return this.split;
    }

    @JsonIgnore
    public boolean isUnfilled() {
        return this.unfilled;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public boolean load() {
        return false;
    }

    public void modifyPath(List<String> list) {
        if (this.path == null) {
            this.setPath(list);
            return;
        }
        this.path.clear();
        this.path.addAll(list);
    }

    public boolean needsToBeReloadedInImageFragment() {
        return this.reload;
    }

    public void newPath() {
        if (this.path == null) {
            return;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.addAll(this.path);
        this.path = arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String printError() {
        String string2;
        String string3 = this.getLinkUrl() != null ? this.getLinkUrl() : "null";
        switch (this.errorFlag) {
            default: {
                string2 = "This image does not have an error";
                break;
            }
            case 4: {
                string2 = "The website will not allow you to see this image. Try opening it in your web browser?";
                break;
            }
            case 2: {
                string2 = "You can only see this image if you are logged into this images' website. If Crumby does not support logging in, contact the admin so he can add it!";
                break;
            }
            case 1: {
                string2 = "This image was deleted and is no longer available.";
                break;
            }
            case 3: {
                string2 = "This user has chosen to restrict access to their image to logged in users only!";
                break;
            }
            case 5: {
                string2 = "This image could not be parsed for an unspecified reason";
            }
        }
        Analytics.INSTANCE.newException(new Exception(string3 + " " + string2));
        return string2;
    }

    public void removeView(GalleryImageView galleryImageView) {
        this.views.remove(galleryImageView);
    }

    public void setAttributes(ExtraAttributes extraAttributes) {
        this.attributes = extraAttributes;
    }

    @JsonIgnore
    public void setChecked(boolean bl2) {
        this.checked = bl2;
    }

    public void setComments(List<ImageComment> list) {
        this.comments = list;
    }

    public void setDescription(String string2) {
        this.description = string2;
    }

    public void setErrorFlag(int n2) {
        this.errorFlag = n2;
    }

    public void setHeight(int n2) {
        this.height = n2;
    }

    @JsonIgnore
    public void setIcon(int n2) {
        this.icon = n2;
        this.iconDrawable = null;
    }

    @JsonIgnore
    public void setIcon(LayerDrawable layerDrawable) {
        this.iconDrawable = layerDrawable;
        this.icon = 0;
    }

    public void setId(String string2) {
        this.id = string2;
    }

    public void setImageUrl(String string2) {
        this.imageUrl = string2;
        if (string2 != null && string2.endsWith(".gif")) {
            this.setAnimated(true);
        }
    }

    public void setLinkUrl(String string2) {
        this.linkUrl = string2;
    }

    public void setLinksToGallery(boolean bl2) {
        this.linksToGallery = bl2;
        if (bl2) {
            this.icon = 2130837613;
            return;
        }
        this.icon = 0;
    }

    public void setPage(int n2) {
        this.page = n2;
    }

    @JsonIgnore
    public void setPath(List<String> list) {
        this.path = list;
    }

    @JsonIgnore
    public void setPosition(int n2) {
        this.position = n2;
    }

    public void setReload(boolean bl2) {
        this.reload = bl2;
    }

    public void setSmallThumbnailUrl(String string2) {
        this.smallThumbnailUrl = string2;
    }

    public void setSubtitle(String string2) {
        this.subtitle = string2;
    }

    public void setTags(String[] arrstring) {
        this.tags = arrstring;
    }

    public void setThumbnailUrl(String string2) {
        this.thumbnailUrl = string2;
    }

    public void setTitle(String string2) {
        this.title = string2;
    }

    @JsonIgnore
    public void setViewGridWidth(int n2) {
        this.viewGridWidth = n2;
    }

    @JsonIgnore
    public void setVisited(boolean bl2) {
        this.visited = bl2;
    }

    public void setWidth(int n2) {
        this.width = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void updateViews() {
        if (this.views != null) {
            Iterator<GalleryImageView> iterator = this.views.iterator();
            while (iterator.hasNext()) {
                iterator.next().update();
            }
        }
    }

    public class ErrorFlag {
        public static final int COULD_NOT_PARSE = 5;
        public static final int DELETED = 1;
        public static final int NO_ERROR = 0;
        public static final int RESTRICTED = 4;
        public static final int RESTRICTED_BY_USER = 3;
        public static final int RESTRICTED_MUST_LOGIN = 2;
    }

}

