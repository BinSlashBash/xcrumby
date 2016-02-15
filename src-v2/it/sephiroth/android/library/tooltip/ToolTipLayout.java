/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.ObjectAnimator
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Paint
 *  android.graphics.Point
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.Xfermode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.text.Html
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.widget.TextView
 */
package it.sephiroth.android.library.tooltip;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.TextView;
import it.sephiroth.android.library.tooltip.R;
import it.sephiroth.android.library.tooltip.ToolTipTextDrawable;
import it.sephiroth.android.library.tooltip.TooltipManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class ToolTipLayout
extends ViewGroup {
    static final boolean DBG = false;
    private static final String TAG = "ToolTipLayout";
    private final long activateDelay;
    Runnable activateRunnable;
    private OnCloseListener closeListener;
    private final TooltipManager.ClosePolicy closePolity;
    private final Rect drawRect;
    TooltipManager.Gravity gravity;
    private final boolean hideArrow;
    Runnable hideRunnable;
    private boolean mActivated;
    private boolean mAttached;
    private final ToolTipTextDrawable mDrawable;
    private boolean mInitialized;
    Animator mShowAnimation;
    boolean mShowing;
    private TextView mTextView;
    private View mView;
    private final int maxWidth;
    private final int padding;
    private final Point point;
    private final long showDelay;
    private final long showDuration;
    private final View targetView;
    private final Rect tempRect;
    private CharSequence text;
    private final int textResId;
    private final int toolTipId;
    private OnToolTipListener tooltipListener;
    private final int topRule;
    private final Rect viewRect;

    public ToolTipLayout(Context context, TooltipManager.Builder builder) {
        super(context);
        this.activateRunnable = new Runnable(){

            @Override
            public void run() {
                ToolTipLayout.this.mActivated = true;
            }
        };
        this.hideRunnable = new Runnable(){

            @Override
            public void run() {
                ToolTipLayout.this.onClose();
            }
        };
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(null, R.styleable.ToolTipLayout, builder.defStyleAttr, builder.defStyleRes);
        this.padding = typedArray.getDimensionPixelSize(0, 30);
        typedArray.recycle();
        this.toolTipId = builder.id;
        this.text = builder.text;
        this.gravity = builder.gravity;
        this.textResId = builder.textResId;
        this.maxWidth = builder.maxWidth;
        this.topRule = builder.actionbarSize;
        this.closePolity = builder.closePolicy;
        this.showDuration = builder.showDuration;
        this.showDelay = builder.showDelay;
        this.hideArrow = builder.hideArrow;
        this.activateDelay = builder.activateDelay;
        this.targetView = builder.view;
        this.point = builder.point;
        this.viewRect = new Rect();
        this.drawRect = new Rect();
        this.tempRect = new Rect();
        this.mDrawable = new ToolTipTextDrawable(context, builder);
        this.setVisibility(8);
        this.setHardwareAccelerated(true);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @TargetApi(value=11)
    private void calculatePositions(List<TooltipManager.Gravity> list) {
        if (!this.isAttached()) {
            return;
        }
        if (list.size() < 1) {
            if (this.tooltipListener != null) {
                this.tooltipListener.onShowFailed(this);
            }
            this.setVisibility(8);
            return;
        }
        TooltipManager.Gravity gravity = list.get(0);
        list.remove(0);
        Rect rect = new Rect();
        ((Activity)this.getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        rect.top += this.topRule;
        ToolTipTextDrawable toolTipTextDrawable = (ToolTipTextDrawable)this.mView.getBackground();
        if (this.targetView != null) {
            this.targetView.getGlobalVisibleRect(this.viewRect);
        } else {
            this.viewRect.set(this.point.x, this.point.y, this.point.x, this.point.y);
        }
        int n2 = this.mView.getWidth();
        int n3 = this.mView.getMeasuredHeight();
        Point point = new Point();
        if (gravity == TooltipManager.Gravity.BOTTOM) {
            this.drawRect.set(this.viewRect.centerX() - n2 / 2, this.viewRect.bottom, this.viewRect.centerX() + n2 / 2, this.viewRect.bottom + n3);
            point.x = this.viewRect.centerX();
            point.y = this.viewRect.bottom;
            if (!rect.contains(this.drawRect)) {
                if (this.drawRect.right > rect.right) {
                    this.drawRect.offset(rect.right - this.drawRect.right, 0);
                } else if (this.drawRect.left < rect.left) {
                    this.drawRect.offset(- this.drawRect.left, 0);
                }
                if (this.drawRect.bottom > rect.bottom) {
                    this.calculatePositions(list);
                    return;
                }
            }
        } else if (gravity == TooltipManager.Gravity.TOP) {
            this.drawRect.set(this.viewRect.centerX() - n2 / 2, this.viewRect.top - n3, this.viewRect.centerX() + n2 / 2, this.viewRect.top);
            point.x = this.viewRect.centerX();
            point.y = this.viewRect.top;
            if (!rect.contains(this.drawRect)) {
                if (this.drawRect.right > rect.right) {
                    this.drawRect.offset(rect.right - this.drawRect.right, 0);
                } else if (this.drawRect.left < rect.left) {
                    this.drawRect.offset(- this.drawRect.left, 0);
                }
                if (this.drawRect.top < rect.top) {
                    this.calculatePositions(list);
                    return;
                }
            }
        } else if (gravity == TooltipManager.Gravity.RIGHT) {
            this.drawRect.set(this.viewRect.right, this.viewRect.centerY() - n3 / 2, this.viewRect.right + n2, this.viewRect.centerY() + n3 / 2);
            point.x = this.viewRect.right;
            point.y = this.viewRect.centerY();
            if (!rect.contains(this.drawRect)) {
                if (this.drawRect.bottom > rect.bottom) {
                    this.drawRect.offset(0, rect.bottom - this.drawRect.bottom);
                } else if (this.drawRect.top < rect.top) {
                    this.drawRect.offset(0, rect.top - this.drawRect.top);
                }
                if (this.drawRect.right > rect.right) {
                    this.calculatePositions(list);
                    return;
                }
            }
        } else if (gravity == TooltipManager.Gravity.LEFT) {
            this.drawRect.set(this.viewRect.left - n2, this.viewRect.centerY() - n3 / 2, this.viewRect.left, this.viewRect.centerY() + n3 / 2);
            point.x = this.viewRect.left;
            point.y = this.viewRect.centerY();
            if (!rect.contains(this.drawRect)) {
                if (this.drawRect.bottom > rect.bottom) {
                    this.drawRect.offset(0, rect.bottom - this.drawRect.bottom);
                } else if (this.drawRect.top < rect.top) {
                    this.drawRect.offset(0, rect.top - this.drawRect.top);
                }
                if (this.drawRect.left < rect.left) {
                    this.gravity = TooltipManager.Gravity.RIGHT;
                    this.calculatePositions(list);
                    return;
                }
            }
        } else if (this.gravity == TooltipManager.Gravity.CENTER) {
            this.drawRect.set(this.viewRect.centerX() - n2 / 2, this.viewRect.centerY() - n3 / 2, this.viewRect.centerX() - n2 / 2, this.viewRect.centerY() + n3 / 2);
            point.x = this.viewRect.centerX();
            point.y = this.viewRect.centerY();
            if (!rect.contains(this.drawRect)) {
                if (this.drawRect.bottom > rect.bottom) {
                    this.drawRect.offset(0, rect.bottom - this.drawRect.bottom);
                } else if (this.drawRect.top < rect.top) {
                    this.drawRect.offset(0, rect.top - this.drawRect.top);
                }
                if (this.drawRect.right > rect.right) {
                    this.drawRect.offset(rect.right - this.drawRect.right, 0);
                } else if (this.drawRect.left < rect.left) {
                    this.drawRect.offset(rect.left - this.drawRect.left, 0);
                }
            }
        }
        this.mView.setTranslationX((float)this.drawRect.left);
        this.mView.setTranslationY((float)this.drawRect.top);
        this.mView.getGlobalVisibleRect(this.tempRect);
        point.x -= this.tempRect.left;
        point.y -= this.tempRect.top;
        if (gravity == TooltipManager.Gravity.LEFT || gravity == TooltipManager.Gravity.RIGHT) {
            point.y -= this.padding / 2;
        } else if (gravity == TooltipManager.Gravity.TOP || gravity == TooltipManager.Gravity.BOTTOM) {
            point.x -= this.padding / 2;
        }
        toolTipTextDrawable.setAnchor(gravity, this.padding / 2);
        if (this.hideArrow) return;
        toolTipTextDrawable.setDestinationPoint(point);
    }

    private void initializeView() {
        if (!this.isAttached() || this.mInitialized) {
            return;
        }
        this.mInitialized = true;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        this.mView = LayoutInflater.from((Context)this.getContext()).inflate(this.textResId, (ViewGroup)this, false);
        this.mView.setLayoutParams(layoutParams);
        this.mView.setBackgroundDrawable((Drawable)this.mDrawable);
        this.mView.setPadding(this.padding, this.padding, this.padding, this.padding);
        this.mTextView = (TextView)this.mView.findViewById(16908308);
        this.mTextView.setText((CharSequence)Html.fromHtml((String)((String)this.text)));
        if (this.maxWidth > -1) {
            this.mTextView.setMaxWidth(this.maxWidth);
        }
        this.addView(this.mView);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void onClose() {
        if (this.getHandler() == null) {
            return;
        }
        this.getHandler().removeCallbacks(this.hideRunnable);
        if (this.closeListener == null) return;
        this.closeListener.onClose(this);
    }

    private void postActivate(long l2) {
        if (l2 > 0) {
            if (this.isAttached()) {
                this.postDelayed(this.activateRunnable, l2);
            }
            return;
        }
        this.mActivated = true;
    }

    void doHide() {
        if (!this.isAttached()) {
            return;
        }
        this.fadeOut();
    }

    void doShow() {
        if (!this.isAttached()) {
            return;
        }
        this.initializeView();
        this.fadeIn();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @TargetApi(value=11)
    protected void fadeIn() {
        if (this.mShowAnimation != null) return;
        if (this.mShowing) {
            return;
        }
        this.mShowing = true;
        this.mShowAnimation = ObjectAnimator.ofFloat((Object)((Object)this), (String)"alpha", (float[])new float[]{0.0f, 1.0f});
        if (this.showDelay > 0) {
            this.mShowAnimation.setStartDelay(this.showDelay);
        }
        this.mShowAnimation.addListener(new Animator.AnimatorListener(){
            boolean cancelled;

            public void onAnimationCancel(Animator animator) {
                this.cancelled = true;
            }

            public void onAnimationEnd(Animator animator) {
                if (ToolTipLayout.this.tooltipListener != null && !this.cancelled) {
                    ToolTipLayout.this.tooltipListener.onShowCompleted(ToolTipLayout.this);
                    ToolTipLayout.this.postActivate(ToolTipLayout.this.activateDelay);
                }
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                ToolTipLayout.this.setVisibility(0);
                this.cancelled = false;
            }
        });
        this.mShowAnimation.start();
        if (this.showDuration <= 0) return;
        this.getHandler().removeCallbacks(this.hideRunnable);
        this.getHandler().postDelayed(this.hideRunnable, this.showDuration);
    }

    @TargetApi(value=11)
    protected void fadeOut() {
        if (!this.isAttached() || !this.mShowing) {
            return;
        }
        if (this.mShowAnimation != null) {
            this.mShowAnimation.cancel();
        }
        this.mShowing = false;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)((Object)this), (String)"alpha", (float[])new float[]{this.getAlpha(), 0.0f});
        objectAnimator.addListener(new Animator.AnimatorListener(){
            boolean cancelled;

            public void onAnimationCancel(Animator animator) {
                this.cancelled = true;
            }

            public void onAnimationEnd(Animator animator) {
                if (this.cancelled) {
                    return;
                }
                if (ToolTipLayout.this.tooltipListener != null) {
                    ToolTipLayout.this.tooltipListener.onHideCompleted(ToolTipLayout.this);
                }
                ToolTipLayout.this.mShowAnimation = null;
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                this.cancelled = false;
            }
        });
        objectAnimator.start();
    }

    int getTooltipId() {
        return this.toolTipId;
    }

    public boolean isAttached() {
        if (this.getParent() != null) {
            return true;
        }
        return false;
    }

    boolean isShowing() {
        return this.mShowing;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttached = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        Object object;
        n3 = this.getChildCount();
        for (n2 = 0; n2 < n3; ++n2) {
            object = this.getChildAt(n2);
            if (object.getVisibility() == 8) continue;
            object.getLayoutParams();
            object.layout(object.getLeft(), object.getTop(), object.getMeasuredWidth(), object.getMeasuredHeight());
        }
        object = new ArrayList<TooltipManager.Gravity>(Arrays.asList(new TooltipManager.Gravity[]{TooltipManager.Gravity.LEFT, TooltipManager.Gravity.RIGHT, TooltipManager.Gravity.TOP, TooltipManager.Gravity.BOTTOM, TooltipManager.Gravity.CENTER}));
        object.remove((Object)this.gravity);
        object.add(0, this.gravity);
        this.calculatePositions((List<TooltipManager.Gravity>)object);
    }

    protected void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        int n4 = -1;
        int n5 = -1;
        int n6 = View.MeasureSpec.getMode((int)n2);
        int n7 = View.MeasureSpec.getMode((int)n3);
        int n8 = View.MeasureSpec.getSize((int)n2);
        int n9 = View.MeasureSpec.getSize((int)n3);
        n2 = n4;
        if (n6 != 0) {
            n2 = n8;
        }
        n3 = n5;
        if (n7 != 0) {
            n3 = n9;
        }
        n9 = this.getChildCount();
        for (n5 = 0; n5 < n9; ++n5) {
            View view = this.getChildAt(n5);
            if (view.getVisibility() == 8) continue;
            view.measure(View.MeasureSpec.makeMeasureSpec((int)n2, (int)Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec((int)n3, (int)Integer.MIN_VALUE));
        }
        this.setMeasuredDimension(n2, n3);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && this.drawRect.contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
            this.onClose();
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @TargetApi(value=14)
    void removeFromParent() {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            if (this.getHandler() != null) {
                this.getHandler().removeCallbacks(this.hideRunnable);
            }
            ((ViewGroup)viewParent).removeView((View)this);
            if (this.mShowAnimation != null && this.mShowAnimation.isStarted()) {
                this.mShowAnimation.cancel();
            }
        }
    }

    @TargetApi(value=11)
    protected void setHardwareAccelerated(boolean bl2) {
        if (bl2) {
            if (Build.VERSION.SDK_INT >= 11) {
                if (this.isHardwareAccelerated()) {
                    Paint paint = new Paint();
                    paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
                    this.setLayerType(2, paint);
                    return;
                }
                this.setLayerType(1, null);
                return;
            }
            this.setDrawingCacheEnabled(true);
            return;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            this.setLayerType(1, null);
            return;
        }
        this.setDrawingCacheEnabled(true);
    }

    void setOnCloseListener(OnCloseListener onCloseListener) {
        this.closeListener = onCloseListener;
    }

    void setOnToolTipListener(OnToolTipListener onToolTipListener) {
        this.tooltipListener = onToolTipListener;
    }

    public void setText(CharSequence charSequence) {
        this.text = charSequence;
        if (this.mTextView != null) {
            this.mTextView.setText((CharSequence)Html.fromHtml((String)((String)charSequence)));
        }
    }

    static interface OnCloseListener {
        public void onClose(ToolTipLayout var1);
    }

    static interface OnToolTipListener {
        public void onHideCompleted(ToolTipLayout var1);

        public void onShowCompleted(ToolTipLayout var1);

        public void onShowFailed(ToolTipLayout var1);
    }

}

