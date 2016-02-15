package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import com.crumby.CrDb;
import com.crumby.lib.ImageComment;
import java.util.List;

public class ImageCommentsView
  extends LinearLayout
{
  private LinearLayout commentContainer;
  private List<ImageComment> comments;
  int index;
  private View loading;
  private View title;
  
  public ImageCommentsView(Context paramContext)
  {
    super(paramContext);
  }
  
  public ImageCommentsView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ImageCommentsView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void addComments(boolean paramBoolean)
  {
    CrDb.logTime("imgur image", "comments", true);
    if (this.comments == null) {
      return;
    }
    this.title.setVisibility(0);
    this.loading.setVisibility(8);
    int i = getResources().getDisplayMetrics().heightPixels;
    getResources().getDimension(2131165195);
    if (!paramBoolean) {
      i = 1;
    }
    while ((i > 0) && (this.index < this.comments.size()) && (this.index < this.commentContainer.getChildCount()))
    {
      ImageCommentView localImageCommentView = (ImageCommentView)this.commentContainer.getChildAt(this.index);
      localImageCommentView.setVisibility(0);
      localImageCommentView.fillWith((ImageComment)this.comments.get(this.index));
      this.index += 1;
      i -= localImageCommentView.getMeasuredHeight();
    }
    CrDb.logTime("imgur image", "comments", false);
  }
  
  public void initialize(List<ImageComment> paramList)
  {
    if ((paramList == null) || (paramList.isEmpty()))
    {
      setVisibility(8);
      return;
    }
    this.comments = paramList;
    addComments(true);
  }
  
  protected void onFinishInflate()
  {
    this.title = findViewById(2131493023);
    this.loading = findViewById(2131493022);
    this.commentContainer = ((LinearLayout)findViewById(2131493024));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/ImageCommentsView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */