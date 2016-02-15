package com.crumby.lib;

import android.graphics.drawable.LayerDrawable;
import com.crumby.Analytics;
import com.crumby.lib.widget.GalleryImageView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GalleryImage
{
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
  
  public GalleryImage() {}
  
  public GalleryImage(String paramString)
  {
    setLinkUrl(paramString);
  }
  
  public GalleryImage(String paramString1, String paramString2, String paramString3)
  {
    this(paramString2);
    this.thumbnailUrl = paramString1;
    this.title = paramString3;
  }
  
  public GalleryImage(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    this(paramString1, paramString2, paramString3);
    this.width = paramInt1;
    this.height = paramInt2;
  }
  
  public GalleryImage(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3)
  {
    this(paramString1, paramString2, paramString3, paramInt1, paramInt2);
    this.offset = paramInt3;
    this.split = true;
  }
  
  public GalleryImage(boolean paramBoolean)
  {
    this.unfilled = paramBoolean;
  }
  
  private void setAnimated(boolean paramBoolean)
  {
    this.animated = paramBoolean;
  }
  
  public void addView(GalleryImageView paramGalleryImageView)
  {
    if (this.views == null) {
      this.views = new HashSet();
    }
    this.views.add(paramGalleryImageView);
  }
  
  public ExtraAttributes attr()
  {
    return this.attributes;
  }
  
  public String buildPath()
  {
    String str1 = "";
    Iterator localIterator = this.path.iterator();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      str1 = str1 + str2 + "/";
    }
    return str1;
  }
  
  public void clearViews()
  {
    if (this.views == null) {
      return;
    }
    this.views.clear();
  }
  
  public void copy(GalleryImage paramGalleryImage)
  {
    if (paramGalleryImage.smallThumbnailUrl != null) {
      this.smallThumbnailUrl = paramGalleryImage.smallThumbnailUrl;
    }
    if (paramGalleryImage.thumbnailUrl != null) {
      this.thumbnailUrl = paramGalleryImage.thumbnailUrl;
    }
    if (paramGalleryImage.imageUrl != null) {
      this.imageUrl = paramGalleryImage.imageUrl;
    }
    if (paramGalleryImage.comments != null) {
      this.comments = paramGalleryImage.comments;
    }
    if (paramGalleryImage.animated) {
      this.animated = true;
    }
    if (paramGalleryImage.width != 0) {
      this.width = paramGalleryImage.width;
    }
    if (paramGalleryImage.height != 0) {
      this.height = paramGalleryImage.height;
    }
    if ((paramGalleryImage.title != null) && (!paramGalleryImage.title.equals(""))) {
      this.title = paramGalleryImage.title;
    }
    if ((paramGalleryImage.description != null) && (!paramGalleryImage.description.equals(""))) {
      this.description = paramGalleryImage.description;
    }
    if (paramGalleryImage.attributes != null) {
      this.attributes = paramGalleryImage.attributes;
    }
    if (paramGalleryImage.tags != null) {
      this.tags = paramGalleryImage.tags;
    }
    this.errorFlag = paramGalleryImage.errorFlag;
  }
  
  public List<ImageComment> getComments()
  {
    return this.comments;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public int getErrorFlag()
  {
    return this.errorFlag;
  }
  
  public int getHeight()
  {
    return this.height;
  }
  
  public int getIcon()
  {
    return this.icon;
  }
  
  public LayerDrawable getIconDrawable()
  {
    return this.iconDrawable;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getImageUrl()
  {
    return this.imageUrl;
  }
  
  public String getLinkUrl()
  {
    return this.linkUrl;
  }
  
  public int getOffset()
  {
    return this.offset;
  }
  
  public int getPage()
  {
    return this.page;
  }
  
  public List<String> getPath()
  {
    return this.path;
  }
  
  public int getPosition()
  {
    return this.position;
  }
  
  public String getRequestedFilename()
  {
    Object localObject1 = this.imageUrl.substring(this.imageUrl.lastIndexOf('/') + 1).replaceAll("\\s+", "_").replace(":", "_").replace("%20", "").replace("%", "");
    Object localObject2 = localObject1;
    if (((String)localObject1).contains("?")) {
      localObject2 = ((String)localObject1).substring(0, ((String)localObject1).lastIndexOf("?"));
    }
    localObject1 = localObject2;
    if (((String)localObject2).startsWith("Konachan"))
    {
      localObject1 = localObject2;
      if (((String)localObject2).length() > 50) {
        localObject1 = ((String)localObject2).substring(((String)localObject2).length() - 50);
      }
    }
    localObject2 = localObject1;
    if (this.imageUrl.contains("pururin.com"))
    {
      localObject2 = localObject1;
      if (((String)localObject1).contains("#"))
      {
        localObject2 = ((String)localObject1).substring(((String)localObject1).indexOf("#") + 1);
        localObject2 = (String)localObject2 + ((String)localObject1).substring(0, ((String)localObject1).indexOf("#") - 1);
      }
    }
    return (String)localObject2;
  }
  
  public String getSmallThumbnailUrl()
  {
    if (this.smallThumbnailUrl != null) {
      return this.smallThumbnailUrl;
    }
    return this.thumbnailUrl;
  }
  
  public String getSubtitle()
  {
    return this.subtitle;
  }
  
  public String[] getTags()
  {
    return this.tags;
  }
  
  public String getThumbnailUrl()
  {
    return this.thumbnailUrl;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public int getViewGridWidth()
  {
    return this.viewGridWidth;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public boolean hasError()
  {
    return this.errorFlag > 0;
  }
  
  public boolean hasIcon()
  {
    return ((this.icon != -1) && (this.icon != 0)) || (this.iconDrawable != null);
  }
  
  public boolean isALinkToGallery()
  {
    return this.linksToGallery;
  }
  
  public boolean isAnimated()
  {
    return this.animated;
  }
  
  public boolean isChecked()
  {
    return this.checked;
  }
  
  public boolean isSplit()
  {
    return this.split;
  }
  
  @JsonIgnore
  public boolean isUnfilled()
  {
    return this.unfilled;
  }
  
  public boolean isVisited()
  {
    return this.visited;
  }
  
  public boolean load()
  {
    return false;
  }
  
  public void modifyPath(List<String> paramList)
  {
    if (this.path == null)
    {
      setPath(paramList);
      return;
    }
    this.path.clear();
    this.path.addAll(paramList);
  }
  
  public boolean needsToBeReloadedInImageFragment()
  {
    return this.reload;
  }
  
  public void newPath()
  {
    if (this.path == null) {
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(this.path);
    this.path = localArrayList;
  }
  
  public String printError()
  {
    String str2;
    String str1;
    if (getLinkUrl() != null)
    {
      str2 = getLinkUrl();
      switch (this.errorFlag)
      {
      default: 
        str1 = "This image does not have an error";
      }
    }
    for (;;)
    {
      Analytics.INSTANCE.newException(new Exception(str2 + " " + str1));
      return str1;
      str2 = "null";
      break;
      str1 = "The website will not allow you to see this image. Try opening it in your web browser?";
      continue;
      str1 = "You can only see this image if you are logged into this images' website. If Crumby does not support logging in, contact the admin so he can add it!";
      continue;
      str1 = "This image was deleted and is no longer available.";
      continue;
      str1 = "This user has chosen to restrict access to their image to logged in users only!";
      continue;
      str1 = "This image could not be parsed for an unspecified reason";
    }
  }
  
  public void removeView(GalleryImageView paramGalleryImageView)
  {
    this.views.remove(paramGalleryImageView);
  }
  
  public void setAttributes(ExtraAttributes paramExtraAttributes)
  {
    this.attributes = paramExtraAttributes;
  }
  
  @JsonIgnore
  public void setChecked(boolean paramBoolean)
  {
    this.checked = paramBoolean;
  }
  
  public void setComments(List<ImageComment> paramList)
  {
    this.comments = paramList;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public void setErrorFlag(int paramInt)
  {
    this.errorFlag = paramInt;
  }
  
  public void setHeight(int paramInt)
  {
    this.height = paramInt;
  }
  
  @JsonIgnore
  public void setIcon(int paramInt)
  {
    this.icon = paramInt;
    this.iconDrawable = null;
  }
  
  @JsonIgnore
  public void setIcon(LayerDrawable paramLayerDrawable)
  {
    this.iconDrawable = paramLayerDrawable;
    this.icon = 0;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public void setImageUrl(String paramString)
  {
    this.imageUrl = paramString;
    if ((paramString != null) && (paramString.endsWith(".gif"))) {
      setAnimated(true);
    }
  }
  
  public void setLinkUrl(String paramString)
  {
    this.linkUrl = paramString;
  }
  
  public void setLinksToGallery(boolean paramBoolean)
  {
    this.linksToGallery = paramBoolean;
    if (paramBoolean)
    {
      this.icon = 2130837613;
      return;
    }
    this.icon = 0;
  }
  
  public void setPage(int paramInt)
  {
    this.page = paramInt;
  }
  
  @JsonIgnore
  public void setPath(List<String> paramList)
  {
    this.path = paramList;
  }
  
  @JsonIgnore
  public void setPosition(int paramInt)
  {
    this.position = paramInt;
  }
  
  public void setReload(boolean paramBoolean)
  {
    this.reload = paramBoolean;
  }
  
  public void setSmallThumbnailUrl(String paramString)
  {
    this.smallThumbnailUrl = paramString;
  }
  
  public void setSubtitle(String paramString)
  {
    this.subtitle = paramString;
  }
  
  public void setTags(String[] paramArrayOfString)
  {
    this.tags = paramArrayOfString;
  }
  
  public void setThumbnailUrl(String paramString)
  {
    this.thumbnailUrl = paramString;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  @JsonIgnore
  public void setViewGridWidth(int paramInt)
  {
    this.viewGridWidth = paramInt;
  }
  
  @JsonIgnore
  public void setVisited(boolean paramBoolean)
  {
    this.visited = paramBoolean;
  }
  
  public void setWidth(int paramInt)
  {
    this.width = paramInt;
  }
  
  public void updateViews()
  {
    if (this.views == null) {}
    for (;;)
    {
      return;
      Iterator localIterator = this.views.iterator();
      while (localIterator.hasNext()) {
        ((GalleryImageView)localIterator.next()).update();
      }
    }
  }
  
  public class ErrorFlag
  {
    public static final int COULD_NOT_PARSE = 5;
    public static final int DELETED = 1;
    public static final int NO_ERROR = 0;
    public static final int RESTRICTED = 4;
    public static final int RESTRICTED_BY_USER = 3;
    public static final int RESTRICTED_MUST_LOGIN = 2;
    
    public ErrorFlag() {}
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/GalleryImage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */