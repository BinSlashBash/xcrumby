package it.sephiroth.android.library.tooltip;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.widget.ExploreByTouchHelper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.TextView;
import com.crumby.GalleryViewer;
import it.sephiroth.android.library.tooltip.TooltipManager.Builder;
import it.sephiroth.android.library.tooltip.TooltipManager.ClosePolicy;
import it.sephiroth.android.library.tooltip.TooltipManager.Gravity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ToolTipLayout extends ViewGroup {
    static final boolean DBG = false;
    private static final String TAG = "ToolTipLayout";
    private final long activateDelay;
    Runnable activateRunnable;
    private OnCloseListener closeListener;
    private final ClosePolicy closePolity;
    private final Rect drawRect;
    Gravity gravity;
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

    /* renamed from: it.sephiroth.android.library.tooltip.ToolTipLayout.1 */
    class C06621 implements AnimatorListener {
        boolean cancelled;

        C06621() {
        }

        public void onAnimationStart(Animator animation) {
            ToolTipLayout.this.setVisibility(0);
            this.cancelled = ToolTipLayout.DBG;
        }

        public void onAnimationEnd(Animator animation) {
            if (ToolTipLayout.this.tooltipListener != null && !this.cancelled) {
                ToolTipLayout.this.tooltipListener.onShowCompleted(ToolTipLayout.this);
                ToolTipLayout.this.postActivate(ToolTipLayout.this.activateDelay);
            }
        }

        public void onAnimationCancel(Animator animation) {
            this.cancelled = true;
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    /* renamed from: it.sephiroth.android.library.tooltip.ToolTipLayout.2 */
    class C06632 implements Runnable {
        C06632() {
        }

        public void run() {
            ToolTipLayout.this.mActivated = true;
        }
    }

    /* renamed from: it.sephiroth.android.library.tooltip.ToolTipLayout.3 */
    class C06643 implements Runnable {
        C06643() {
        }

        public void run() {
            ToolTipLayout.this.onClose();
        }
    }

    /* renamed from: it.sephiroth.android.library.tooltip.ToolTipLayout.4 */
    class C06654 implements AnimatorListener {
        boolean cancelled;

        C06654() {
        }

        public void onAnimationStart(Animator animation) {
            this.cancelled = ToolTipLayout.DBG;
        }

        public void onAnimationEnd(Animator animation) {
            if (!this.cancelled) {
                if (ToolTipLayout.this.tooltipListener != null) {
                    ToolTipLayout.this.tooltipListener.onHideCompleted(ToolTipLayout.this);
                }
                ToolTipLayout.this.mShowAnimation = null;
            }
        }

        public void onAnimationCancel(Animator animation) {
            this.cancelled = true;
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    interface OnCloseListener {
        void onClose(ToolTipLayout toolTipLayout);
    }

    interface OnToolTipListener {
        void onHideCompleted(ToolTipLayout toolTipLayout);

        void onShowCompleted(ToolTipLayout toolTipLayout);

        void onShowFailed(ToolTipLayout toolTipLayout);
    }

    public ToolTipLayout(Context context, Builder builder) {
        super(context);
        this.activateRunnable = new C06632();
        this.hideRunnable = new C06643();
        TypedArray theme = context.getTheme().obtainStyledAttributes(null, C0661R.styleable.ToolTipLayout, builder.defStyleAttr, builder.defStyleRes);
        this.padding = theme.getDimensionPixelSize(0, 30);
        theme.recycle();
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
        setVisibility(8);
        setHardwareAccelerated(true);
    }

    int getTooltipId() {
        return this.toolTipId;
    }

    @TargetApi(11)
    protected void setHardwareAccelerated(boolean accelerated) {
        if (accelerated) {
            if (VERSION.SDK_INT < 11) {
                setDrawingCacheEnabled(true);
            } else if (isHardwareAccelerated()) {
                Paint hardwarePaint = new Paint();
                hardwarePaint.setXfermode(new PorterDuffXfermode(Mode.OVERLAY));
                setLayerType(2, hardwarePaint);
            } else {
                setLayerType(1, null);
            }
        } else if (VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        } else {
            setDrawingCacheEnabled(true);
        }
    }

    void doShow() {
        if (isAttached()) {
            initializeView();
            fadeIn();
        }
    }

    void doHide() {
        if (isAttached()) {
            fadeOut();
        }
    }

    @TargetApi(11)
    protected void fadeIn() {
        if (this.mShowAnimation == null && !this.mShowing) {
            this.mShowing = true;
            this.mShowAnimation = ObjectAnimator.ofFloat(this, "alpha", new float[]{0.0f, GalleryViewer.PROGRESS_COMPLETED});
            if (this.showDelay > 0) {
                this.mShowAnimation.setStartDelay(this.showDelay);
            }
            this.mShowAnimation.addListener(new C06621());
            this.mShowAnimation.start();
            if (this.showDuration > 0) {
                getHandler().removeCallbacks(this.hideRunnable);
                getHandler().postDelayed(this.hideRunnable, this.showDuration);
            }
        }
    }

    boolean isShowing() {
        return this.mShowing;
    }

    private void postActivate(long ms) {
        if (ms <= 0) {
            this.mActivated = true;
        } else if (isAttached()) {
            postDelayed(this.activateRunnable, ms);
        }
    }

    @TargetApi(14)
    void removeFromParent() {
        ViewParent parent = getParent();
        if (parent != null) {
            if (getHandler() != null) {
                getHandler().removeCallbacks(this.hideRunnable);
            }
            ((ViewGroup) parent).removeView(this);
            if (this.mShowAnimation != null && this.mShowAnimation.isStarted()) {
                this.mShowAnimation.cancel();
            }
        }
    }

    @TargetApi(11)
    protected void fadeOut() {
        if (isAttached() && this.mShowing) {
            if (this.mShowAnimation != null) {
                this.mShowAnimation.cancel();
            }
            this.mShowing = DBG;
            Animator animation = ObjectAnimator.ofFloat(this, "alpha", new float[]{getAlpha(), 0.0f});
            animation.addListener(new C06654());
            animation.start();
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams st = child.getLayoutParams();
                child.layout(child.getLeft(), child.getTop(), child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }
        List<Gravity> gravities = new ArrayList(Arrays.asList(new Gravity[]{Gravity.LEFT, Gravity.RIGHT, Gravity.TOP, Gravity.BOTTOM, Gravity.CENTER}));
        gravities.remove(this.gravity);
        gravities.add(0, this.gravity);
        calculatePositions(gravities);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int myWidth = -1;
        int myHeight = -1;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode != 0) {
            myWidth = widthSize;
        }
        if (heightMode != 0) {
            myHeight = heightSize;
        }
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                child.measure(MeasureSpec.makeMeasureSpec(myWidth, ExploreByTouchHelper.INVALID_ID), MeasureSpec.makeMeasureSpec(myHeight, ExploreByTouchHelper.INVALID_ID));
            }
        }
        setMeasuredDimension(myWidth, myHeight);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttached = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = DBG;
    }

    private void initializeView() {
        if (isAttached() && !this.mInitialized) {
            this.mInitialized = true;
            LayoutParams params = new LayoutParams(-2, -2);
            this.mView = LayoutInflater.from(getContext()).inflate(this.textResId, this, DBG);
            this.mView.setLayoutParams(params);
            this.mView.setBackgroundDrawable(this.mDrawable);
            this.mView.setPadding(this.padding, this.padding, this.padding, this.padding);
            this.mTextView = (TextView) this.mView.findViewById(16908308);
            this.mTextView.setText(Html.fromHtml((String) this.text));
            if (this.maxWidth > -1) {
                this.mTextView.setMaxWidth(this.maxWidth);
            }
            addView(this.mView);
        }
    }

    @TargetApi(11)
    private void calculatePositions(List<Gravity> gravities) {
        if (!isAttached()) {
            return;
        }
        if (gravities.size() < 1) {
            if (this.tooltipListener != null) {
                this.tooltipListener.onShowFailed(this);
            }
            setVisibility(8);
            return;
        }
        Gravity gravity = (Gravity) gravities.get(0);
        gravities.remove(0);
        Rect screenRect = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(screenRect);
        screenRect.top += this.topRule;
        ToolTipTextDrawable drawable = (ToolTipTextDrawable) this.mView.getBackground();
        if (this.targetView != null) {
            this.targetView.getGlobalVisibleRect(this.viewRect);
        } else {
            this.viewRect.set(this.point.x, this.point.y, this.point.x, this.point.y);
        }
        int width = this.mView.getWidth();
        int height = this.mView.getMeasuredHeight();
        Point point = new Point();
        if (gravity == Gravity.BOTTOM) {
            this.drawRect.set(this.viewRect.centerX() - (width / 2), this.viewRect.bottom, this.viewRect.centerX() + (width / 2), this.viewRect.bottom + height);
            point.x = this.viewRect.centerX();
            point.y = this.viewRect.bottom;
            if (!screenRect.contains(this.drawRect)) {
                if (this.drawRect.right > screenRect.right) {
                    this.drawRect.offset(screenRect.right - this.drawRect.right, 0);
                } else if (this.drawRect.left < screenRect.left) {
                    this.drawRect.offset(-this.drawRect.left, 0);
                }
                if (this.drawRect.bottom > screenRect.bottom) {
                    calculatePositions(gravities);
                    return;
                }
            }
        } else if (gravity == Gravity.TOP) {
            this.drawRect.set(this.viewRect.centerX() - (width / 2), this.viewRect.top - height, this.viewRect.centerX() + (width / 2), this.viewRect.top);
            point.x = this.viewRect.centerX();
            point.y = this.viewRect.top;
            if (!screenRect.contains(this.drawRect)) {
                if (this.drawRect.right > screenRect.right) {
                    this.drawRect.offset(screenRect.right - this.drawRect.right, 0);
                } else if (this.drawRect.left < screenRect.left) {
                    this.drawRect.offset(-this.drawRect.left, 0);
                }
                if (this.drawRect.top < screenRect.top) {
                    calculatePositions(gravities);
                    return;
                }
            }
        } else if (gravity == Gravity.RIGHT) {
            this.drawRect.set(this.viewRect.right, this.viewRect.centerY() - (height / 2), this.viewRect.right + width, this.viewRect.centerY() + (height / 2));
            point.x = this.viewRect.right;
            point.y = this.viewRect.centerY();
            if (!screenRect.contains(this.drawRect)) {
                if (this.drawRect.bottom > screenRect.bottom) {
                    this.drawRect.offset(0, screenRect.bottom - this.drawRect.bottom);
                } else if (this.drawRect.top < screenRect.top) {
                    this.drawRect.offset(0, screenRect.top - this.drawRect.top);
                }
                if (this.drawRect.right > screenRect.right) {
                    calculatePositions(gravities);
                    return;
                }
            }
        } else if (gravity == Gravity.LEFT) {
            this.drawRect.set(this.viewRect.left - width, this.viewRect.centerY() - (height / 2), this.viewRect.left, this.viewRect.centerY() + (height / 2));
            point.x = this.viewRect.left;
            point.y = this.viewRect.centerY();
            if (!screenRect.contains(this.drawRect)) {
                if (this.drawRect.bottom > screenRect.bottom) {
                    this.drawRect.offset(0, screenRect.bottom - this.drawRect.bottom);
                } else if (this.drawRect.top < screenRect.top) {
                    this.drawRect.offset(0, screenRect.top - this.drawRect.top);
                }
                if (this.drawRect.left < screenRect.left) {
                    this.gravity = Gravity.RIGHT;
                    calculatePositions(gravities);
                    return;
                }
            }
        } else if (this.gravity == Gravity.CENTER) {
            this.drawRect.set(this.viewRect.centerX() - (width / 2), this.viewRect.centerY() - (height / 2), this.viewRect.centerX() - (width / 2), this.viewRect.centerY() + (height / 2));
            point.x = this.viewRect.centerX();
            point.y = this.viewRect.centerY();
            if (!screenRect.contains(this.drawRect)) {
                if (this.drawRect.bottom > screenRect.bottom) {
                    this.drawRect.offset(0, screenRect.bottom - this.drawRect.bottom);
                } else if (this.drawRect.top < screenRect.top) {
                    this.drawRect.offset(0, screenRect.top - this.drawRect.top);
                }
                if (this.drawRect.right > screenRect.right) {
                    this.drawRect.offset(screenRect.right - this.drawRect.right, 0);
                } else if (this.drawRect.left < screenRect.left) {
                    this.drawRect.offset(screenRect.left - this.drawRect.left, 0);
                }
            }
        }
        this.mView.setTranslationX((float) this.drawRect.left);
        this.mView.setTranslationY((float) this.drawRect.top);
        this.mView.getGlobalVisibleRect(this.tempRect);
        point.x -= this.tempRect.left;
        point.y -= this.tempRect.top;
        if (gravity == Gravity.LEFT || gravity == Gravity.RIGHT) {
            point.y -= this.padding / 2;
        } else if (gravity == Gravity.TOP || gravity == Gravity.BOTTOM) {
            point.x -= this.padding / 2;
        }
        drawable.setAnchor(gravity, this.padding / 2);
        if (!this.hideArrow) {
            drawable.setDestinationPoint(point);
        }
    }

    public boolean isAttached() {
        return getParent() != null ? true : DBG;
    }

    public void setText(CharSequence text) {
        this.text = text;
        if (this.mTextView != null) {
            this.mTextView.setText(Html.fromHtml((String) text));
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != 0 || !this.drawRect.contains((int) event.getX(), (int) event.getY())) {
            return super.onTouchEvent(event);
        }
        onClose();
        return true;
    }

    private void onClose() {
        if (getHandler() != null) {
            getHandler().removeCallbacks(this.hideRunnable);
            if (this.closeListener != null) {
                this.closeListener.onClose(this);
            }
        }
    }

    void setOnCloseListener(OnCloseListener listener) {
        this.closeListener = listener;
    }

    void setOnToolTipListener(OnToolTipListener listener) {
        this.tooltipListener = listener;
    }
}
