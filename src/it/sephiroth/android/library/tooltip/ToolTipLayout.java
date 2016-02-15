package it.sephiroth.android.library.tooltip;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ToolTipLayout
  extends ViewGroup
{
  static final boolean DBG = false;
  private static final String TAG = "ToolTipLayout";
  private final long activateDelay;
  Runnable activateRunnable = new Runnable()
  {
    public void run()
    {
      ToolTipLayout.access$302(ToolTipLayout.this, true);
    }
  };
  private OnCloseListener closeListener;
  private final TooltipManager.ClosePolicy closePolity;
  private final Rect drawRect;
  TooltipManager.Gravity gravity;
  private final boolean hideArrow;
  Runnable hideRunnable = new Runnable()
  {
    public void run()
    {
      ToolTipLayout.this.onClose();
    }
  };
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
  
  public ToolTipLayout(Context paramContext, TooltipManager.Builder paramBuilder)
  {
    super(paramContext);
    TypedArray localTypedArray = paramContext.getTheme().obtainStyledAttributes(null, R.styleable.ToolTipLayout, paramBuilder.defStyleAttr, paramBuilder.defStyleRes);
    this.padding = localTypedArray.getDimensionPixelSize(0, 30);
    localTypedArray.recycle();
    this.toolTipId = paramBuilder.id;
    this.text = paramBuilder.text;
    this.gravity = paramBuilder.gravity;
    this.textResId = paramBuilder.textResId;
    this.maxWidth = paramBuilder.maxWidth;
    this.topRule = paramBuilder.actionbarSize;
    this.closePolity = paramBuilder.closePolicy;
    this.showDuration = paramBuilder.showDuration;
    this.showDelay = paramBuilder.showDelay;
    this.hideArrow = paramBuilder.hideArrow;
    this.activateDelay = paramBuilder.activateDelay;
    this.targetView = paramBuilder.view;
    this.point = paramBuilder.point;
    this.viewRect = new Rect();
    this.drawRect = new Rect();
    this.tempRect = new Rect();
    this.mDrawable = new ToolTipTextDrawable(paramContext, paramBuilder);
    setVisibility(8);
    setHardwareAccelerated(true);
  }
  
  @TargetApi(11)
  private void calculatePositions(List<TooltipManager.Gravity> paramList)
  {
    if (!isAttached()) {
      return;
    }
    if (paramList.size() < 1)
    {
      if (this.tooltipListener != null) {
        this.tooltipListener.onShowFailed(this);
      }
      setVisibility(8);
      return;
    }
    TooltipManager.Gravity localGravity = (TooltipManager.Gravity)paramList.get(0);
    paramList.remove(0);
    Rect localRect = new Rect();
    ((Activity)getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
    localRect.top += this.topRule;
    ToolTipTextDrawable localToolTipTextDrawable = (ToolTipTextDrawable)this.mView.getBackground();
    int i;
    int j;
    Point localPoint;
    if (this.targetView != null)
    {
      this.targetView.getGlobalVisibleRect(this.viewRect);
      i = this.mView.getWidth();
      j = this.mView.getMeasuredHeight();
      localPoint = new Point();
      if (localGravity != TooltipManager.Gravity.BOTTOM) {
        break label378;
      }
      this.drawRect.set(this.viewRect.centerX() - i / 2, this.viewRect.bottom, this.viewRect.centerX() + i / 2, this.viewRect.bottom + j);
      localPoint.x = this.viewRect.centerX();
      localPoint.y = this.viewRect.bottom;
      if (localRect.contains(this.drawRect)) {
        break label1104;
      }
      if (this.drawRect.right <= localRect.right) {
        break label344;
      }
      this.drawRect.offset(localRect.right - this.drawRect.right, 0);
    }
    for (;;)
    {
      if (this.drawRect.bottom <= localRect.bottom) {
        break label1104;
      }
      calculatePositions(paramList);
      return;
      this.viewRect.set(this.point.x, this.point.y, this.point.x, this.point.y);
      break;
      label344:
      if (this.drawRect.left < localRect.left) {
        this.drawRect.offset(-this.drawRect.left, 0);
      }
    }
    label378:
    if (localGravity == TooltipManager.Gravity.TOP)
    {
      this.drawRect.set(this.viewRect.centerX() - i / 2, this.viewRect.top - j, this.viewRect.centerX() + i / 2, this.viewRect.top);
      localPoint.x = this.viewRect.centerX();
      localPoint.y = this.viewRect.top;
      if (!localRect.contains(this.drawRect))
      {
        if (this.drawRect.right > localRect.right) {
          this.drawRect.offset(localRect.right - this.drawRect.right, 0);
        }
        while (this.drawRect.top < localRect.top)
        {
          calculatePositions(paramList);
          return;
          if (this.drawRect.left < localRect.left) {
            this.drawRect.offset(-this.drawRect.left, 0);
          }
        }
      }
    }
    else if (localGravity == TooltipManager.Gravity.RIGHT)
    {
      this.drawRect.set(this.viewRect.right, this.viewRect.centerY() - j / 2, this.viewRect.right + i, this.viewRect.centerY() + j / 2);
      localPoint.x = this.viewRect.right;
      localPoint.y = this.viewRect.centerY();
      if (!localRect.contains(this.drawRect))
      {
        if (this.drawRect.bottom > localRect.bottom) {
          this.drawRect.offset(0, localRect.bottom - this.drawRect.bottom);
        }
        while (this.drawRect.right > localRect.right)
        {
          calculatePositions(paramList);
          return;
          if (this.drawRect.top < localRect.top) {
            this.drawRect.offset(0, localRect.top - this.drawRect.top);
          }
        }
      }
    }
    else if (localGravity == TooltipManager.Gravity.LEFT)
    {
      this.drawRect.set(this.viewRect.left - i, this.viewRect.centerY() - j / 2, this.viewRect.left, this.viewRect.centerY() + j / 2);
      localPoint.x = this.viewRect.left;
      localPoint.y = this.viewRect.centerY();
      if (!localRect.contains(this.drawRect))
      {
        if (this.drawRect.bottom > localRect.bottom) {
          this.drawRect.offset(0, localRect.bottom - this.drawRect.bottom);
        }
        while (this.drawRect.left < localRect.left)
        {
          this.gravity = TooltipManager.Gravity.RIGHT;
          calculatePositions(paramList);
          return;
          if (this.drawRect.top < localRect.top) {
            this.drawRect.offset(0, localRect.top - this.drawRect.top);
          }
        }
      }
    }
    else if (this.gravity == TooltipManager.Gravity.CENTER)
    {
      this.drawRect.set(this.viewRect.centerX() - i / 2, this.viewRect.centerY() - j / 2, this.viewRect.centerX() - i / 2, this.viewRect.centerY() + j / 2);
      localPoint.x = this.viewRect.centerX();
      localPoint.y = this.viewRect.centerY();
      if (!localRect.contains(this.drawRect))
      {
        if (this.drawRect.bottom <= localRect.bottom) {
          break label1243;
        }
        this.drawRect.offset(0, localRect.bottom - this.drawRect.bottom);
        label1068:
        if (this.drawRect.right <= localRect.right) {
          break label1282;
        }
        this.drawRect.offset(localRect.right - this.drawRect.right, 0);
      }
    }
    label1104:
    this.mView.setTranslationX(this.drawRect.left);
    this.mView.setTranslationY(this.drawRect.top);
    this.mView.getGlobalVisibleRect(this.tempRect);
    localPoint.x -= this.tempRect.left;
    localPoint.y -= this.tempRect.top;
    if ((localGravity == TooltipManager.Gravity.LEFT) || (localGravity == TooltipManager.Gravity.RIGHT)) {
      localPoint.y -= this.padding / 2;
    }
    for (;;)
    {
      localToolTipTextDrawable.setAnchor(localGravity, this.padding / 2);
      if (this.hideArrow) {
        break;
      }
      localToolTipTextDrawable.setDestinationPoint(localPoint);
      return;
      label1243:
      if (this.drawRect.top >= localRect.top) {
        break label1068;
      }
      this.drawRect.offset(0, localRect.top - this.drawRect.top);
      break label1068;
      label1282:
      if (this.drawRect.left >= localRect.left) {
        break label1104;
      }
      this.drawRect.offset(localRect.left - this.drawRect.left, 0);
      break label1104;
      if ((localGravity == TooltipManager.Gravity.TOP) || (localGravity == TooltipManager.Gravity.BOTTOM)) {
        localPoint.x -= this.padding / 2;
      }
    }
  }
  
  private void initializeView()
  {
    if ((!isAttached()) || (this.mInitialized)) {
      return;
    }
    this.mInitialized = true;
    ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-2, -2);
    this.mView = LayoutInflater.from(getContext()).inflate(this.textResId, this, false);
    this.mView.setLayoutParams(localLayoutParams);
    this.mView.setBackgroundDrawable(this.mDrawable);
    this.mView.setPadding(this.padding, this.padding, this.padding, this.padding);
    this.mTextView = ((TextView)this.mView.findViewById(16908308));
    this.mTextView.setText(Html.fromHtml((String)this.text));
    if (this.maxWidth > -1) {
      this.mTextView.setMaxWidth(this.maxWidth);
    }
    addView(this.mView);
  }
  
  private void onClose()
  {
    if (getHandler() == null) {}
    do
    {
      return;
      getHandler().removeCallbacks(this.hideRunnable);
    } while (this.closeListener == null);
    this.closeListener.onClose(this);
  }
  
  private void postActivate(long paramLong)
  {
    if (paramLong > 0L)
    {
      if (isAttached()) {
        postDelayed(this.activateRunnable, paramLong);
      }
      return;
    }
    this.mActivated = true;
  }
  
  void doHide()
  {
    if (!isAttached()) {
      return;
    }
    fadeOut();
  }
  
  void doShow()
  {
    if (!isAttached()) {
      return;
    }
    initializeView();
    fadeIn();
  }
  
  @TargetApi(11)
  protected void fadeIn()
  {
    if ((this.mShowAnimation != null) || (this.mShowing)) {}
    do
    {
      return;
      this.mShowing = true;
      this.mShowAnimation = ObjectAnimator.ofFloat(this, "alpha", new float[] { 0.0F, 1.0F });
      if (this.showDelay > 0L) {
        this.mShowAnimation.setStartDelay(this.showDelay);
      }
      this.mShowAnimation.addListener(new Animator.AnimatorListener()
      {
        boolean cancelled;
        
        public void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          this.cancelled = true;
        }
        
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          if ((ToolTipLayout.this.tooltipListener != null) && (!this.cancelled))
          {
            ToolTipLayout.this.tooltipListener.onShowCompleted(ToolTipLayout.this);
            ToolTipLayout.this.postActivate(ToolTipLayout.this.activateDelay);
          }
        }
        
        public void onAnimationRepeat(Animator paramAnonymousAnimator) {}
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          ToolTipLayout.this.setVisibility(0);
          this.cancelled = false;
        }
      });
      this.mShowAnimation.start();
    } while (this.showDuration <= 0L);
    getHandler().removeCallbacks(this.hideRunnable);
    getHandler().postDelayed(this.hideRunnable, this.showDuration);
  }
  
  @TargetApi(11)
  protected void fadeOut()
  {
    if ((!isAttached()) || (!this.mShowing)) {
      return;
    }
    if (this.mShowAnimation != null) {
      this.mShowAnimation.cancel();
    }
    this.mShowing = false;
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this, "alpha", new float[] { getAlpha(), 0.0F });
    localObjectAnimator.addListener(new Animator.AnimatorListener()
    {
      boolean cancelled;
      
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        this.cancelled = true;
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        if (this.cancelled) {
          return;
        }
        if (ToolTipLayout.this.tooltipListener != null) {
          ToolTipLayout.this.tooltipListener.onHideCompleted(ToolTipLayout.this);
        }
        ToolTipLayout.this.mShowAnimation = null;
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator) {}
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        this.cancelled = false;
      }
    });
    localObjectAnimator.start();
  }
  
  int getTooltipId()
  {
    return this.toolTipId;
  }
  
  public boolean isAttached()
  {
    return getParent() != null;
  }
  
  boolean isShowing()
  {
    return this.mShowing;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mAttached = true;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mAttached = false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt2 = getChildCount();
    paramInt1 = 0;
    while (paramInt1 < paramInt2)
    {
      localObject = getChildAt(paramInt1);
      if (((View)localObject).getVisibility() != 8)
      {
        ((View)localObject).getLayoutParams();
        ((View)localObject).layout(((View)localObject).getLeft(), ((View)localObject).getTop(), ((View)localObject).getMeasuredWidth(), ((View)localObject).getMeasuredHeight());
      }
      paramInt1 += 1;
    }
    Object localObject = new ArrayList(Arrays.asList(new TooltipManager.Gravity[] { TooltipManager.Gravity.LEFT, TooltipManager.Gravity.RIGHT, TooltipManager.Gravity.TOP, TooltipManager.Gravity.BOTTOM, TooltipManager.Gravity.CENTER }));
    ((List)localObject).remove(this.gravity);
    ((List)localObject).add(0, this.gravity);
    calculatePositions((List)localObject);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int k = -1;
    int i = -1;
    int i1 = View.MeasureSpec.getMode(paramInt1);
    int n = View.MeasureSpec.getMode(paramInt2);
    int m = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    paramInt1 = k;
    if (i1 != 0) {
      paramInt1 = m;
    }
    paramInt2 = i;
    if (n != 0) {
      paramInt2 = j;
    }
    j = getChildCount();
    i = 0;
    while (i < j)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() != 8) {
        localView.measure(View.MeasureSpec.makeMeasureSpec(paramInt1, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(paramInt2, Integer.MIN_VALUE));
      }
      i += 1;
    }
    setMeasuredDimension(paramInt1, paramInt2);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() == 0) && (this.drawRect.contains((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())))
    {
      onClose();
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  @TargetApi(14)
  void removeFromParent()
  {
    ViewParent localViewParent = getParent();
    if (localViewParent != null)
    {
      if (getHandler() != null) {
        getHandler().removeCallbacks(this.hideRunnable);
      }
      ((ViewGroup)localViewParent).removeView(this);
      if ((this.mShowAnimation != null) && (this.mShowAnimation.isStarted())) {
        this.mShowAnimation.cancel();
      }
    }
  }
  
  @TargetApi(11)
  protected void setHardwareAccelerated(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (Build.VERSION.SDK_INT >= 11)
      {
        if (isHardwareAccelerated())
        {
          Paint localPaint = new Paint();
          localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
          setLayerType(2, localPaint);
          return;
        }
        setLayerType(1, null);
        return;
      }
      setDrawingCacheEnabled(true);
      return;
    }
    if (Build.VERSION.SDK_INT >= 11)
    {
      setLayerType(1, null);
      return;
    }
    setDrawingCacheEnabled(true);
  }
  
  void setOnCloseListener(OnCloseListener paramOnCloseListener)
  {
    this.closeListener = paramOnCloseListener;
  }
  
  void setOnToolTipListener(OnToolTipListener paramOnToolTipListener)
  {
    this.tooltipListener = paramOnToolTipListener;
  }
  
  public void setText(CharSequence paramCharSequence)
  {
    this.text = paramCharSequence;
    if (this.mTextView != null) {
      this.mTextView.setText(Html.fromHtml((String)paramCharSequence));
    }
  }
  
  static abstract interface OnCloseListener
  {
    public abstract void onClose(ToolTipLayout paramToolTipLayout);
  }
  
  static abstract interface OnToolTipListener
  {
    public abstract void onHideCompleted(ToolTipLayout paramToolTipLayout);
    
    public abstract void onShowCompleted(ToolTipLayout paramToolTipLayout);
    
    public abstract void onShowFailed(ToolTipLayout paramToolTipLayout);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/it/sephiroth/android/library/tooltip/ToolTipLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */