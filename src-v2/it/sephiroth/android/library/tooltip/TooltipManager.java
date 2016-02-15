/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.graphics.Point
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 */
package it.sephiroth.android.library.tooltip;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import it.sephiroth.android.library.tooltip.R;
import it.sephiroth.android.library.tooltip.ToolTipLayout;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TooltipManager {
    static final boolean DBG = false;
    private static final String TAG = "TooltipManager";
    private static ConcurrentHashMap<Activity, TooltipManager> instances = new ConcurrentHashMap();
    final Object lock = new Object();
    final Activity mActivity;
    private ToolTipLayout.OnCloseListener mCloseListener;
    private ToolTipLayout.OnToolTipListener mTooltipListener;
    final HashMap<Integer, ToolTipLayout> mTooltips = new HashMap();

    public TooltipManager(Activity activity) {
        this.mCloseListener = new ToolTipLayout.OnCloseListener(){

            @Override
            public void onClose(ToolTipLayout toolTipLayout) {
                TooltipManager.this.hide(toolTipLayout.getTooltipId());
            }
        };
        this.mTooltipListener = new ToolTipLayout.OnToolTipListener(){

            @Override
            public void onHideCompleted(ToolTipLayout toolTipLayout) {
                toolTipLayout.removeFromParent();
                TooltipManager.this.printStats();
            }

            @Override
            public void onShowCompleted(ToolTipLayout toolTipLayout) {
            }

            @Override
            public void onShowFailed(ToolTipLayout toolTipLayout) {
                TooltipManager.this.remove(toolTipLayout.getTooltipId());
            }
        };
        this.mActivity = activity;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void destroy() {
        Object object = this.lock;
        synchronized (object) {
            Iterator<Integer> iterator = this.mTooltips.keySet().iterator();
            do {
                if (!iterator.hasNext()) {
                    // MONITOREXIT [2, 3, 4] lbl6 : MonitorExitStatement: MONITOREXIT : var1_1
                    this.printStats();
                    return;
                }
                this.remove(iterator.next());
            } while (true);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static TooltipManager getInstance(Activity activity) {
        TooltipManager tooltipManager;
        TooltipManager tooltipManager2 = tooltipManager = instances.get((Object)activity);
        if (tooltipManager != null) return tooltipManager2;
        // MONITORENTER : it.sephiroth.android.library.tooltip.TooltipManager.class
        tooltipManager2 = tooltipManager = instances.get((Object)activity);
        if (tooltipManager == null) {
            // MONITORENTER : it.sephiroth.android.library.tooltip.TooltipManager.class
            tooltipManager2 = new TooltipManager(activity);
            instances.putIfAbsent(activity, tooltipManager2);
            // MONITOREXIT : it.sephiroth.android.library.tooltip.TooltipManager.class
        }
        // MONITOREXIT : it.sephiroth.android.library.tooltip.TooltipManager.class
        return tooltipManager2;
        catch (Throwable throwable) {
            throw throwable;
        }
    }

    private void printStats() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void removeInstance(Activity object) {
        if ((object = instances.remove(object)) != null) {
            synchronized (TooltipManager.class) {
                object.destroy();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean show(Builder builder) {
        Object object = this.lock;
        synchronized (object) {
            if (this.mTooltips.containsKey(builder.id)) {
                Log.w((String)"TooltipManager", (String)"A Tooltip with the same id was walready specified");
                return false;
            }
            ToolTipLayout toolTipLayout = new ToolTipLayout((Context)this.mActivity, builder);
            toolTipLayout.setOnCloseListener(this.mCloseListener);
            toolTipLayout.setOnToolTipListener(this.mTooltipListener);
            this.mTooltips.put(builder.id, toolTipLayout);
            this.showInternal(toolTipLayout);
        }
        this.printStats();
        return true;
    }

    private void showInternal(ToolTipLayout toolTipLayout) {
        ViewGroup viewGroup;
        if (this.mActivity == null || (viewGroup = (ViewGroup)this.mActivity.getWindow().getDecorView()) == null) {
            return;
        }
        if (toolTipLayout.getParent() == null) {
            viewGroup.addView((View)toolTipLayout, new ViewGroup.LayoutParams(-1, -1));
        }
        toolTipLayout.doShow();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean active(int n2) {
        Object object = this.lock;
        synchronized (object) {
            return this.mTooltips.containsKey(n2);
        }
    }

    public Builder create(int n2) {
        return new Builder(this, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void hide(int n2) {
        Object object = this.lock;
        // MONITORENTER : object
        ToolTipLayout toolTipLayout = this.mTooltips.remove(n2);
        // MONITOREXIT : object
        if (toolTipLayout == null) return;
        toolTipLayout.setOnCloseListener(null);
        toolTipLayout.doHide();
        this.printStats();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void remove(int n2) {
        Object object = this.lock;
        // MONITORENTER : object
        ToolTipLayout toolTipLayout = this.mTooltips.remove(n2);
        // MONITOREXIT : object
        if (toolTipLayout != null) {
            toolTipLayout.setOnCloseListener(null);
            toolTipLayout.setOnToolTipListener(null);
            toolTipLayout.removeFromParent();
        }
        this.printStats();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setText(int n2, CharSequence charSequence) {
        Object object = this.lock;
        // MONITORENTER : object
        ToolTipLayout toolTipLayout = this.mTooltips.get(n2);
        // MONITOREXIT : object
        if (toolTipLayout == null) return;
        toolTipLayout.setText(charSequence);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void update(int n2) {
        Object object = this.lock;
        // MONITORENTER : object
        ToolTipLayout toolTipLayout = this.mTooltips.get(n2);
        // MONITOREXIT : object
        if (toolTipLayout == null) return;
        toolTipLayout.layout(toolTipLayout.getLeft(), toolTipLayout.getTop(), toolTipLayout.getRight(), toolTipLayout.getBottom());
        toolTipLayout.requestLayout();
    }

    public static final class Builder {
        int actionbarSize = 0;
        long activateDelay = 0;
        ClosePolicy closePolicy;
        int defStyleAttr = R.attr.ttlm_defaultStyle;
        int defStyleRes = R.style.ToolTipLayoutDefaultStyle;
        Gravity gravity;
        boolean hideArrow;
        int id;
        WeakReference<TooltipManager> manager;
        int maxWidth = -1;
        Point point;
        long showDelay = 0;
        long showDuration;
        CharSequence text;
        int textResId = R.layout.tooltip_textview;
        View view;

        Builder(TooltipManager tooltipManager, int n2) {
            this.manager = new WeakReference<TooltipManager>(tooltipManager);
            this.id = n2;
        }

        public Builder actionBarSize(int n2) {
            this.actionbarSize = n2;
            return this;
        }

        public Builder activateDelay(long l2) {
            this.activateDelay = l2;
            return this;
        }

        public Builder anchor(Point point, Gravity gravity) {
            this.view = null;
            this.point = new Point(point);
            this.gravity = gravity;
            return this;
        }

        public Builder anchor(View view, Gravity gravity) {
            this.point = null;
            this.view = view;
            this.gravity = gravity;
            return this;
        }

        public Builder closePolicy(ClosePolicy closePolicy, long l2) {
            this.closePolicy = closePolicy;
            this.showDuration = l2;
            return this;
        }

        public Builder maxWidth(int n2) {
            this.maxWidth = n2;
            return this;
        }

        public boolean show() {
            TooltipManager tooltipManager;
            if (this.closePolicy == null) {
                throw new IllegalStateException("ClosePolicy cannot be null");
            }
            if (this.point == null && this.view == null) {
                throw new IllegalStateException("Target point or target view must be specified");
            }
            if (this.gravity == Gravity.CENTER) {
                this.hideArrow = true;
            }
            if ((tooltipManager = this.manager.get()) != null) {
                return tooltipManager.show(this);
            }
            return false;
        }

        public Builder showDelay(long l2) {
            this.showDelay = l2;
            return this;
        }

        public Builder text(Context context, int n2) {
            return this.text(context.getString(n2));
        }

        public Builder text(CharSequence charSequence) {
            this.text = charSequence;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder toggleArrow(boolean bl2) {
            bl2 = !bl2;
            this.hideArrow = bl2;
            return this;
        }

        public Builder withCustomView(int n2) {
            this.textResId = n2;
            return this;
        }

        public Builder withStyleId(int n2) {
            this.defStyleAttr = 0;
            this.defStyleRes = n2;
            return this;
        }
    }

    public static enum ClosePolicy {
        TouchInside,
        TouchOutside,
        None;
        

        private ClosePolicy() {
        }
    }

    public static enum Gravity {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        CENTER;
        

        private Gravity() {
        }
    }

}

