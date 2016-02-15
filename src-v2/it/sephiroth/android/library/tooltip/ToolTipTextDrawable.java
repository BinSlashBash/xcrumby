/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 */
package it.sephiroth.android.library.tooltip;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import it.sephiroth.android.library.tooltip.R;
import it.sephiroth.android.library.tooltip.TooltipManager;

class ToolTipTextDrawable
extends Drawable {
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

    public ToolTipTextDrawable(Context context, TooltipManager.Builder builder) {
        context = context.getTheme().obtainStyledAttributes(null, R.styleable.ToolTipLayout, builder.defStyleAttr, builder.defStyleRes);
        this.ellipseSize = context.getDimensionPixelSize(4, 4);
        this.strokeWidth = context.getDimensionPixelSize(3, 30);
        this.backgroundColor = context.getColor(2, 0);
        this.strokeColor = context.getColor(1, 0);
        context.recycle();
        this.rectF = new RectF();
        this.bgPaint = new Paint(1);
        this.bgPaint.setColor(this.backgroundColor);
        this.bgPaint.setStyle(Paint.Style.FILL);
        this.stPaint = new Paint(1);
        this.stPaint.setColor(this.strokeColor);
        this.stPaint.setStyle(Paint.Style.STROKE);
        this.stPaint.setStrokeWidth((float)this.strokeWidth);
        this.path = new Path();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculatePath(Rect rect) {
        int n2 = rect.left + this.padding;
        int n3 = rect.top + this.padding;
        int n4 = rect.right - this.padding;
        int n5 = rect.bottom - this.padding;
        int n6 = (int)((float)this.padding / 1.2f);
        if (this.point != null && this.gravity != null) {
            this.path.reset();
            if (this.point.y < n3) {
                this.point.y = n3;
            } else if (this.point.y > n5) {
                this.point.y = n5;
            }
            if (this.point.x < n2) {
                this.point.x = n2;
            }
            if (this.point.x > n4) {
                this.point.x = n4;
            }
            this.path.moveTo((float)n2 + this.ellipseSize, (float)n3);
            if (this.gravity == TooltipManager.Gravity.BOTTOM) {
                this.path.lineTo((float)(this.point.x + n2 - n6), (float)n3);
                this.path.lineTo((float)(this.point.x + n2), (float)rect.top);
                this.path.lineTo((float)(this.point.x + n2 + n6), (float)n3);
            }
            this.path.lineTo((float)n4 - this.ellipseSize, (float)n3);
            this.path.quadTo((float)n4, (float)n3, (float)n4, (float)n3 + this.ellipseSize);
            if (this.gravity == TooltipManager.Gravity.LEFT) {
                this.path.lineTo((float)n4, (float)(this.point.y + n3 - n6));
                this.path.lineTo((float)rect.right, (float)(this.point.y + n3));
                this.path.lineTo((float)n4, (float)(this.point.y + n3 + n6));
            }
            this.path.lineTo((float)n4, (float)n5 - this.ellipseSize);
            this.path.quadTo((float)n4, (float)n5, (float)n4 - this.ellipseSize, (float)n5);
            if (this.gravity == TooltipManager.Gravity.TOP) {
                this.path.lineTo((float)(this.point.x + n2 + n6), (float)n5);
                this.path.lineTo((float)(this.point.x + n2), (float)rect.bottom);
                this.path.lineTo((float)(this.point.x + n2 - n6), (float)n5);
            }
            this.path.lineTo((float)n2 + this.ellipseSize, (float)n5);
            this.path.quadTo((float)n2, (float)n5, (float)n2, (float)n5 - this.ellipseSize);
            if (this.gravity == TooltipManager.Gravity.RIGHT && this.point.y > n3) {
                this.path.lineTo((float)n2, (float)(this.point.y + n3 + n6));
                this.path.lineTo((float)rect.left, (float)(this.point.y + n3));
                this.path.lineTo((float)n2, (float)(this.point.y + n3 - n6));
            }
            this.path.lineTo((float)n2, (float)n3 + this.ellipseSize);
            this.path.quadTo((float)n2, (float)n3, (float)n2 + this.ellipseSize, (float)n3);
            return;
        }
        this.rectF.set((float)n2, (float)n3, (float)n4, (float)n5);
        this.path.addRoundRect(this.rectF, this.ellipseSize, this.ellipseSize, Path.Direction.CW);
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(this.path, this.bgPaint);
        canvas.drawPath(this.path, this.stPaint);
    }

    public int getOpacity() {
        return 0;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.calculatePath(rect);
    }

    public void setAlpha(int n2) {
    }

    public void setAnchor(TooltipManager.Gravity gravity, int n2) {
        this.gravity = gravity;
        this.padding = n2;
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setDestinationPoint(Point point) {
        this.point = point;
    }
}

