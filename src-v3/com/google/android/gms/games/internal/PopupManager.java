package com.google.android.gms.games.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Display;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.google.android.gms.internal.gr;
import java.lang.ref.WeakReference;

public class PopupManager {
    protected GamesClientImpl JK;
    protected PopupLocationInfo JL;

    public static final class PopupLocationInfo {
        public IBinder JM;
        public int JN;
        public int bottom;
        public int gravity;
        public int left;
        public int right;
        public int top;

        private PopupLocationInfo(int gravity, IBinder windowToken) {
            this.JN = -1;
            this.left = 0;
            this.top = 0;
            this.right = 0;
            this.bottom = 0;
            this.gravity = gravity;
            this.JM = windowToken;
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

    private static final class PopupManagerHCMR1 extends PopupManager implements OnAttachStateChangeListener, OnGlobalLayoutListener {
        private boolean Iz;
        private WeakReference<View> JO;

        protected PopupManagerHCMR1(GamesClientImpl gamesClientImpl, int gravity) {
            super(gravity, null);
            this.Iz = false;
        }

        private void m1988h(View view) {
            int i = -1;
            if (gr.fz()) {
                Display display = view.getDisplay();
                if (display != null) {
                    i = display.getDisplayId();
                }
            }
            IBinder windowToken = view.getWindowToken();
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int width = view.getWidth();
            int height = view.getHeight();
            this.JL.JN = i;
            this.JL.JM = windowToken;
            this.JL.left = iArr[0];
            this.JL.top = iArr[1];
            this.JL.right = iArr[0] + width;
            this.JL.bottom = iArr[1] + height;
            if (this.Iz) {
                gS();
                this.Iz = false;
            }
        }

        protected void bc(int i) {
            this.JL = new PopupLocationInfo(null, null);
        }

        public void m1989g(View view) {
            View view2;
            Context context;
            this.JK.gF();
            if (this.JO != null) {
                view2 = (View) this.JO.get();
                context = this.JK.getContext();
                if (view2 == null && (context instanceof Activity)) {
                    view2 = ((Activity) context).getWindow().getDecorView();
                }
                if (view2 != null) {
                    view2.removeOnAttachStateChangeListener(this);
                    ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
                    if (gr.fy()) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this);
                    } else {
                        viewTreeObserver.removeGlobalOnLayoutListener(this);
                    }
                }
            }
            this.JO = null;
            context = this.JK.getContext();
            if (view == null && (context instanceof Activity)) {
                view2 = ((Activity) context).findViewById(16908290);
                if (view2 == null) {
                    view2 = ((Activity) context).getWindow().getDecorView();
                }
                GamesLog.m366g("PopupManager", "You have not specified a View to use as content view for popups. Falling back to the Activity content view which may not work properly in future versions of the API. Use setViewForPopups() to set your content view.");
                view = view2;
            }
            if (view != null) {
                m1988h(view);
                this.JO = new WeakReference(view);
                view.addOnAttachStateChangeListener(this);
                view.getViewTreeObserver().addOnGlobalLayoutListener(this);
                return;
            }
            GamesLog.m367h("PopupManager", "No content view usable to display popups. Popups will not be displayed in response to this client's calls. Use setViewForPopups() to set your content view.");
        }

        public void gS() {
            if (this.JL.JM != null) {
                super.gS();
            } else {
                this.Iz = this.JO != null;
            }
        }

        public void onGlobalLayout() {
            if (this.JO != null) {
                View view = (View) this.JO.get();
                if (view != null) {
                    m1988h(view);
                }
            }
        }

        public void onViewAttachedToWindow(View v) {
            m1988h(v);
        }

        public void onViewDetachedFromWindow(View v) {
            this.JK.gF();
            v.removeOnAttachStateChangeListener(this);
        }
    }

    private PopupManager(GamesClientImpl gamesClientImpl, int gravity) {
        this.JK = gamesClientImpl;
        bc(gravity);
    }

    public static PopupManager m566a(GamesClientImpl gamesClientImpl, int i) {
        return gr.fv() ? new PopupManagerHCMR1(gamesClientImpl, i) : new PopupManager(gamesClientImpl, i);
    }

    protected void bc(int i) {
        this.JL = new PopupLocationInfo(new Binder(), null);
    }

    public void m567g(View view) {
    }

    public void gS() {
        this.JK.m2791a(this.JL.JM, this.JL.gV());
    }

    public Bundle gT() {
        return this.JL.gV();
    }

    public IBinder gU() {
        return this.JL.JM;
    }

    public void setGravity(int gravity) {
        this.JL.gravity = gravity;
    }
}
