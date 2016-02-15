/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.view.Display
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.Window
 */
package com.google.android.gms.games.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.internal.GamesLog;
import com.google.android.gms.internal.gr;
import java.lang.ref.WeakReference;

public class PopupManager {
    protected GamesClientImpl JK;
    protected PopupLocationInfo JL;

    private PopupManager(GamesClientImpl gamesClientImpl, int n2) {
        this.JK = gamesClientImpl;
        this.bc(n2);
    }

    public static PopupManager a(GamesClientImpl gamesClientImpl, int n2) {
        if (gr.fv()) {
            return new PopupManagerHCMR1(gamesClientImpl, n2);
        }
        return new PopupManager(gamesClientImpl, n2);
    }

    protected void bc(int n2) {
        this.JL = new PopupLocationInfo(n2, (IBinder)new Binder());
    }

    public void g(View view) {
    }

    public void gS() {
        this.JK.a(this.JL.JM, this.JL.gV());
    }

    public Bundle gT() {
        return this.JL.gV();
    }

    public IBinder gU() {
        return this.JL.JM;
    }

    public void setGravity(int n2) {
        this.JL.gravity = n2;
    }

    public static final class PopupLocationInfo {
        public IBinder JM;
        public int JN = -1;
        public int bottom = 0;
        public int gravity;
        public int left = 0;
        public int right = 0;
        public int top = 0;

        private PopupLocationInfo(int n2, IBinder iBinder) {
            this.gravity = n2;
            this.JM = iBinder;
        }

        public Bundle gV() {
            Bundle bundle = new Bundle();
            bundle.putInt("popupLocationInfo.gravity", this.gravity);
            bundle.putInt("popupLocationInfo.displayId", this.JN);
            bundle.putInt("popupLocationInfo.left", this.left);
            bundle.putInt("popupLocationInfo.top", this.top);
            bundle.putInt("popupLocationInfo.right", this.right);
            bundle.putInt("popupLocationInfo.bottom", this.bottom);
            return bundle;
        }
    }

    private static final class PopupManagerHCMR1
    extends PopupManager
    implements View.OnAttachStateChangeListener,
    ViewTreeObserver.OnGlobalLayoutListener {
        private boolean Iz = false;
        private WeakReference<View> JO;

        protected PopupManagerHCMR1(GamesClientImpl gamesClientImpl, int n2) {
            super(gamesClientImpl, n2);
        }

        private void h(View view) {
            int n2;
            Display display;
            int n3 = n2 = -1;
            if (gr.fz()) {
                display = view.getDisplay();
                n3 = n2;
                if (display != null) {
                    n3 = display.getDisplayId();
                }
            }
            display = view.getWindowToken();
            int[] arrn = new int[2];
            view.getLocationInWindow(arrn);
            n2 = view.getWidth();
            int n4 = view.getHeight();
            this.JL.JN = n3;
            this.JL.JM = display;
            this.JL.left = arrn[0];
            this.JL.top = arrn[1];
            this.JL.right = arrn[0] + n2;
            this.JL.bottom = arrn[1] + n4;
            if (this.Iz) {
                this.gS();
                this.Iz = false;
            }
        }

        @Override
        protected void bc(int n2) {
            this.JL = new PopupLocationInfo(n2, null);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void g(View view) {
            View view2;
            View view3;
            this.JK.gF();
            if (this.JO != null) {
                view2 = this.JO.get();
                Context context = this.JK.getContext();
                view3 = view2;
                if (view2 == null) {
                    view3 = view2;
                    if (context instanceof Activity) {
                        view3 = ((Activity)context).getWindow().getDecorView();
                    }
                }
                if (view3 != null) {
                    view3.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
                    view3 = view3.getViewTreeObserver();
                    if (gr.fy()) {
                        view3.removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                    } else {
                        view3.removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                    }
                }
            }
            this.JO = null;
            view2 = this.JK.getContext();
            view3 = view;
            if (view == null) {
                view3 = view;
                if (view2 instanceof Activity) {
                    view = view3 = ((Activity)view2).findViewById(16908290);
                    if (view3 == null) {
                        view = ((Activity)view2).getWindow().getDecorView();
                    }
                    GamesLog.g("PopupManager", "You have not specified a View to use as content view for popups. Falling back to the Activity content view which may not work properly in future versions of the API. Use setViewForPopups() to set your content view.");
                    view3 = view;
                }
            }
            if (view3 != null) {
                this.h(view3);
                this.JO = new WeakReference<View>(view3);
                view3.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
                view3.getViewTreeObserver().addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                return;
            }
            GamesLog.h("PopupManager", "No content view usable to display popups. Popups will not be displayed in response to this client's calls. Use setViewForPopups() to set your content view.");
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void gS() {
            if (this.JL.JM != null) {
                super.gS();
                return;
            }
            boolean bl2 = this.JO != null;
            this.Iz = bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onGlobalLayout() {
            View view;
            if (this.JO == null || (view = this.JO.get()) == null) {
                return;
            }
            this.h(view);
        }

        public void onViewAttachedToWindow(View view) {
            this.h(view);
        }

        public void onViewDetachedFromWindow(View view) {
            this.JK.gF();
            view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
        }
    }

}

