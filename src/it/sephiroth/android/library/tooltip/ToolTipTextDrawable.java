package it.sephiroth.android.library.tooltip;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

class ToolTipTextDrawable
  extends Drawable
{
  static final boolean DBG = false;
  static final String TAG = "ToolTipTextDrawable";
  private final int backgroundColor;
  private final Paint bgPaint;
  private final float ellipseSize;
  private TooltipManager.Gravity gravity;
  private int padding = 0;
  private final Path path;
  private Point point;
  private final RectF rectF;
  private final Paint stPaint;
  private final int strokeColor;
  private final int strokeWidth;
  
  public ToolTipTextDrawable(Context paramContext, TooltipManager.Builder paramBuilder)
  {
    paramContext = paramContext.getTheme().obtainStyledAttributes(null, R.styleable.ToolTipLayout, paramBuilder.defStyleAttr, paramBuilder.defStyleRes);
    this.ellipseSize = paramContext.getDimensionPixelSize(4, 4);
    this.strokeWidth = paramContext.getDimensionPixelSize(3, 30);
    this.backgroundColor = paramContext.getColor(2, 0);
    this.strokeColor = paramContext.getColor(1, 0);
    paramContext.recycle();
    this.rectF = new RectF();
    this.bgPaint = new Paint(1);
    this.bgPaint.setColor(this.backgroundColor);
    this.bgPaint.setStyle(Paint.Style.FILL);
    this.stPaint = new Paint(1);
    this.stPaint.setColor(this.strokeColor);
    this.stPaint.setStyle(Paint.Style.STROKE);
    this.stPaint.setStrokeWidth(this.strokeWidth);
    this.path = new Path();
  }
  
  private void calculatePath(Rect paramRect)
  {
    int i = paramRect.left + this.padding;
    int j = paramRect.top + this.padding;
    int k = paramRect.right - this.padding;
    int m = paramRect.bottom - this.padding;
    int n = (int)(this.padding / 1.2F);
    if ((this.point != null) && (this.gravity != null))
    {
      this.path.reset();
      if (this.point.y < j) {
        this.point.y = j;
      }
      for (;;)
      {
        if (this.point.x < i) {
          this.point.x = i;
        }
        if (this.point.x > k) {
          this.point.x = k;
        }
        this.path.moveTo(i + this.ellipseSize, j);
        if (this.gravity == TooltipManager.Gravity.BOTTOM)
        {
          this.path.lineTo(this.point.x + i - n, j);
          this.path.lineTo(this.point.x + i, paramRect.top);
          this.path.lineTo(this.point.x + i + n, j);
        }
        this.path.lineTo(k - this.ellipseSize, j);
        this.path.quadTo(k, j, k, j + this.ellipseSize);
        if (this.gravity == TooltipManager.Gravity.LEFT)
        {
          this.path.lineTo(k, this.point.y + j - n);
          this.path.lineTo(paramRect.right, this.point.y + j);
          this.path.lineTo(k, this.point.y + j + n);
        }
        this.path.lineTo(k, m - this.ellipseSize);
        this.path.quadTo(k, m, k - this.ellipseSize, m);
        if (this.gravity == TooltipManager.Gravity.TOP)
        {
          this.path.lineTo(this.point.x + i + n, m);
          this.path.lineTo(this.point.x + i, paramRect.bottom);
          this.path.lineTo(this.point.x + i - n, m);
        }
        this.path.lineTo(i + this.ellipseSize, m);
        this.path.quadTo(i, m, i, m - this.ellipseSize);
        if ((this.gravity == TooltipManager.Gravity.RIGHT) && (this.point.y > j))
        {
          this.path.lineTo(i, this.point.y + j + n);
          this.path.lineTo(paramRect.left, this.point.y + j);
          this.path.lineTo(i, this.point.y + j - n);
        }
        this.path.lineTo(i, j + this.ellipseSize);
        this.path.quadTo(i, j, i + this.ellipseSize, j);
        return;
        if (this.point.y > m) {
          this.point.y = m;
        }
      }
    }
    this.rectF.set(i, j, k, m);
    this.path.addRoundRect(this.rectF, this.ellipseSize, this.ellipseSize, Path.Direction.CW);
  }
  
  public void draw(Canvas paramCanvas)
  {
    paramCanvas.drawPath(this.path, this.bgPaint);
    paramCanvas.drawPath(this.path, this.stPaint);
  }
  
  public int getOpacity()
  {
    return 0;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    calculatePath(paramRect);
  }
  
  public void setAlpha(int paramInt) {}
  
  public void setAnchor(TooltipManager.Gravity paramGravity, int paramInt)
  {
    this.gravity = paramGravity;
    this.padding = paramInt;
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
  
  public void setDestinationPoint(Point paramPoint)
  {
    this.point = paramPoint;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/it/sephiroth/android/library/tooltip/ToolTipTextDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */