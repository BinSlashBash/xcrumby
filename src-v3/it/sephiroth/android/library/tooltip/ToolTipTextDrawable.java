package it.sephiroth.android.library.tooltip;

import android.content.Context;
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
import it.sephiroth.android.library.tooltip.TooltipManager.Builder;
import it.sephiroth.android.library.tooltip.TooltipManager.Gravity;

class ToolTipTextDrawable extends Drawable {
    static final boolean DBG = false;
    static final String TAG = "ToolTipTextDrawable";
    private final int backgroundColor;
    private final Paint bgPaint;
    private final float ellipseSize;
    private Gravity gravity;
    private int padding;
    private final Path path;
    private Point point;
    private final RectF rectF;
    private final Paint stPaint;
    private final int strokeColor;
    private final int strokeWidth;

    public ToolTipTextDrawable(Context context, Builder builder) {
        this.padding = 0;
        TypedArray theme = context.getTheme().obtainStyledAttributes(null, C0661R.styleable.ToolTipLayout, builder.defStyleAttr, builder.defStyleRes);
        this.ellipseSize = (float) theme.getDimensionPixelSize(4, 4);
        this.strokeWidth = theme.getDimensionPixelSize(3, 30);
        this.backgroundColor = theme.getColor(2, 0);
        this.strokeColor = theme.getColor(1, 0);
        theme.recycle();
        this.rectF = new RectF();
        this.bgPaint = new Paint(1);
        this.bgPaint.setColor(this.backgroundColor);
        this.bgPaint.setStyle(Style.FILL);
        this.stPaint = new Paint(1);
        this.stPaint.setColor(this.strokeColor);
        this.stPaint.setStyle(Style.STROKE);
        this.stPaint.setStrokeWidth((float) this.strokeWidth);
        this.path = new Path();
    }

    private void calculatePath(Rect outBounds) {
        int left = outBounds.left + this.padding;
        int top = outBounds.top + this.padding;
        int right = outBounds.right - this.padding;
        int bottom = outBounds.bottom - this.padding;
        int arrowWeight = (int) (((float) this.padding) / 1.2f);
        if (this.point == null || this.gravity == null) {
            this.rectF.set((float) left, (float) top, (float) right, (float) bottom);
            this.path.addRoundRect(this.rectF, this.ellipseSize, this.ellipseSize, Direction.CW);
            return;
        }
        this.path.reset();
        if (this.point.y < top) {
            this.point.y = top;
        } else if (this.point.y > bottom) {
            this.point.y = bottom;
        }
        if (this.point.x < left) {
            this.point.x = left;
        }
        if (this.point.x > right) {
            this.point.x = right;
        }
        this.path.moveTo(((float) left) + this.ellipseSize, (float) top);
        if (this.gravity == Gravity.BOTTOM) {
            this.path.lineTo((float) ((this.point.x + left) - arrowWeight), (float) top);
            this.path.lineTo((float) (this.point.x + left), (float) outBounds.top);
            this.path.lineTo((float) ((this.point.x + left) + arrowWeight), (float) top);
        }
        this.path.lineTo(((float) right) - this.ellipseSize, (float) top);
        this.path.quadTo((float) right, (float) top, (float) right, ((float) top) + this.ellipseSize);
        if (this.gravity == Gravity.LEFT) {
            this.path.lineTo((float) right, (float) ((this.point.y + top) - arrowWeight));
            this.path.lineTo((float) outBounds.right, (float) (this.point.y + top));
            this.path.lineTo((float) right, (float) ((this.point.y + top) + arrowWeight));
        }
        this.path.lineTo((float) right, ((float) bottom) - this.ellipseSize);
        this.path.quadTo((float) right, (float) bottom, ((float) right) - this.ellipseSize, (float) bottom);
        if (this.gravity == Gravity.TOP) {
            this.path.lineTo((float) ((this.point.x + left) + arrowWeight), (float) bottom);
            this.path.lineTo((float) (this.point.x + left), (float) outBounds.bottom);
            this.path.lineTo((float) ((this.point.x + left) - arrowWeight), (float) bottom);
        }
        this.path.lineTo(((float) left) + this.ellipseSize, (float) bottom);
        this.path.quadTo((float) left, (float) bottom, (float) left, ((float) bottom) - this.ellipseSize);
        if (this.gravity == Gravity.RIGHT && this.point.y > top) {
            this.path.lineTo((float) left, (float) ((this.point.y + top) + arrowWeight));
            this.path.lineTo((float) outBounds.left, (float) (this.point.y + top));
            this.path.lineTo((float) left, (float) ((this.point.y + top) - arrowWeight));
        }
        this.path.lineTo((float) left, ((float) top) + this.ellipseSize);
        this.path.quadTo((float) left, (float) top, ((float) left) + this.ellipseSize, (float) top);
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(this.path, this.bgPaint);
        canvas.drawPath(this.path, this.stPaint);
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        calculatePath(bounds);
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return 0;
    }

    public void setDestinationPoint(Point point) {
        this.point = point;
    }

    public void setAnchor(Gravity gravity, int padding) {
        this.gravity = gravity;
        this.padding = padding;
    }
}
