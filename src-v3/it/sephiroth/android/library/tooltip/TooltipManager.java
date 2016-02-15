package it.sephiroth.android.library.tooltip;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TooltipManager {
    static final boolean DBG = false;
    private static final String TAG = "TooltipManager";
    private static ConcurrentHashMap<Activity, TooltipManager> instances;
    final Object lock;
    final Activity mActivity;
    private OnCloseListener mCloseListener;
    private OnToolTipListener mTooltipListener;
    final HashMap<Integer, ToolTipLayout> mTooltips;

    public static final class Builder {
        int actionbarSize;
        long activateDelay;
        ClosePolicy closePolicy;
        int defStyleAttr;
        int defStyleRes;
        Gravity gravity;
        boolean hideArrow;
        int id;
        WeakReference<TooltipManager> manager;
        int maxWidth;
        Point point;
        long showDelay;
        long showDuration;
        CharSequence text;
        int textResId;
        View view;

        Builder(TooltipManager manager, int id) {
            this.actionbarSize = 0;
            this.textResId = C0661R.layout.tooltip_textview;
            this.showDelay = 0;
            this.maxWidth = -1;
            this.defStyleRes = C0661R.style.ToolTipLayoutDefaultStyle;
            this.defStyleAttr = C0661R.attr.ttlm_defaultStyle;
            this.activateDelay = 0;
            this.manager = new WeakReference(manager);
            this.id = id;
        }

        public Builder withCustomView(int resId) {
            this.textResId = resId;
            return this;
        }

        public Builder withStyleId(int styleId) {
            this.defStyleAttr = 0;
            this.defStyleRes = styleId;
            return this;
        }

        public Builder text(Context context, int resid) {
            return text(context.getString(resid));
        }

        public Builder text(CharSequence text) {
            this.text = text;
            return this;
        }

        public Builder maxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public Builder anchor(View view, Gravity gravity) {
            this.point = null;
            this.view = view;
            this.gravity = gravity;
            return this;
        }

        public Builder anchor(Point point, Gravity gravity) {
            this.view = null;
            this.point = new Point(point);
            this.gravity = gravity;
            return this;
        }

        public Builder toggleArrow(boolean show) {
            this.hideArrow = !show ? true : TooltipManager.DBG;
            return this;
        }

        public Builder actionBarSize(int actionBarSize) {
            this.actionbarSize = actionBarSize;
            return this;
        }

        public Builder closePolicy(ClosePolicy policy, long milliseconds) {
            this.closePolicy = policy;
            this.showDuration = milliseconds;
            return this;
        }

        public Builder activateDelay(long ms) {
            this.activateDelay = ms;
            return this;
        }

        public Builder showDelay(long ms) {
            this.showDelay = ms;
            return this;
        }

        public boolean show() {
            if (this.closePolicy == null) {
                throw new IllegalStateException("ClosePolicy cannot be null");
            } else if (this.point == null && this.view == null) {
                throw new IllegalStateException("Target point or target view must be specified");
            } else {
                if (this.gravity == Gravity.CENTER) {
                    this.hideArrow = true;
                }
                TooltipManager tmanager = (TooltipManager) this.manager.get();
                if (tmanager != null) {
                    return tmanager.show(this);
                }
                return TooltipManager.DBG;
            }
        }
    }

    public enum ClosePolicy {
        TouchInside,
        TouchOutside,
        None
    }

    public enum Gravity {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        CENTER
    }

    /* renamed from: it.sephiroth.android.library.tooltip.TooltipManager.1 */
    class C12031 implements OnCloseListener {
        C12031() {
        }

        public void onClose(ToolTipLayout layout) {
            TooltipManager.this.hide(layout.getTooltipId());
        }
    }

    /* renamed from: it.sephiroth.android.library.tooltip.TooltipManager.2 */
    class C12042 implements OnToolTipListener {
        C12042() {
        }

        public void onHideCompleted(ToolTipLayout layout) {
            layout.removeFromParent();
            TooltipManager.this.printStats();
        }

        public void onShowCompleted(ToolTipLayout layout) {
        }

        public void onShowFailed(ToolTipLayout layout) {
            TooltipManager.this.remove(layout.getTooltipId());
        }
    }

    static {
        instances = new ConcurrentHashMap();
    }

    public TooltipManager(Activity activity) {
        this.lock = new Object();
        this.mTooltips = new HashMap();
        this.mCloseListener = new C12031();
        this.mTooltipListener = new C12042();
        this.mActivity = activity;
    }

    public Builder create(int id) {
        return new Builder(this, id);
    }

    private boolean show(Builder builder) {
        synchronized (this.lock) {
            if (this.mTooltips.containsKey(Integer.valueOf(builder.id))) {
                Log.w(TAG, "A Tooltip with the same id was walready specified");
                return DBG;
            }
            ToolTipLayout layout = new ToolTipLayout(this.mActivity, builder);
            layout.setOnCloseListener(this.mCloseListener);
            layout.setOnToolTipListener(this.mTooltipListener);
            this.mTooltips.put(Integer.valueOf(builder.id), layout);
            showInternal(layout);
            printStats();
            return true;
        }
    }

    public void hide(int id) {
        synchronized (this.lock) {
            ToolTipLayout layout = (ToolTipLayout) this.mTooltips.remove(Integer.valueOf(id));
        }
        if (layout != null) {
            layout.setOnCloseListener(null);
            layout.doHide();
            printStats();
        }
    }

    public void update(int id) {
        synchronized (this.lock) {
            ToolTipLayout layout = (ToolTipLayout) this.mTooltips.get(Integer.valueOf(id));
        }
        if (layout != null) {
            layout.layout(layout.getLeft(), layout.getTop(), layout.getRight(), layout.getBottom());
            layout.requestLayout();
        }
    }

    public boolean active(int id) {
        boolean containsKey;
        synchronized (this.lock) {
            containsKey = this.mTooltips.containsKey(Integer.valueOf(id));
        }
        return containsKey;
    }

    public void remove(int id) {
        synchronized (this.lock) {
            ToolTipLayout layout = (ToolTipLayout) this.mTooltips.remove(Integer.valueOf(id));
        }
        if (layout != null) {
            layout.setOnCloseListener(null);
            layout.setOnToolTipListener(null);
            layout.removeFromParent();
        }
        printStats();
    }

    public void setText(int id, CharSequence text) {
        synchronized (this.lock) {
            ToolTipLayout layout = (ToolTipLayout) this.mTooltips.get(Integer.valueOf(id));
        }
        if (layout != null) {
            layout.setText(text);
        }
    }

    private void printStats() {
    }

    private void destroy() {
        synchronized (this.lock) {
            for (Integer intValue : this.mTooltips.keySet()) {
                remove(intValue.intValue());
            }
        }
        printStats();
    }

    private void showInternal(ToolTipLayout layout) {
        if (this.mActivity != null) {
            ViewGroup decor = (ViewGroup) this.mActivity.getWindow().getDecorView();
            if (decor != null) {
                if (layout.getParent() == null) {
                    decor.addView(layout, new LayoutParams(-1, -1));
                }
                layout.doShow();
            }
        }
    }

    public static void removeInstance(Activity activity) {
        TooltipManager sInstance = (TooltipManager) instances.remove(activity);
        if (sInstance != null) {
            synchronized (TooltipManager.class) {
                sInstance.destroy();
            }
        }
    }

    public static TooltipManager getInstance(Activity activity) {
        Throwable th;
        TooltipManager sInstance = (TooltipManager) instances.get(activity);
        if (sInstance == null) {
            synchronized (TooltipManager.class) {
                sInstance = (TooltipManager) instances.get(activity);
                if (sInstance == null) {
                    synchronized (TooltipManager.class) {
                        try {
                            TooltipManager sInstance2 = new TooltipManager(activity);
                            try {
                                instances.putIfAbsent(activity, sInstance2);
                                sInstance = sInstance2;
                            } catch (Throwable th2) {
                                th = th2;
                                sInstance = sInstance2;
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            throw th;
                        }
                    }
                }
            }
        }
        return sInstance;
    }
}
