package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import java.io.Serializable;
import org.json.zip.JSONzip;

public class ViewMatcher implements Serializable {
    protected static final ViewMatcher EMPTY;
    private static final long serialVersionUID = 1;

    private static final class Multi extends ViewMatcher implements Serializable {
        private static final long serialVersionUID = 1;
        private final Class<?>[] _views;

        public Multi(Class<?>[] v) {
            this._views = v;
        }

        public boolean isVisibleForView(Class<?> activeView) {
            for (Class<?> view : this._views) {
                if (activeView == view || view.isAssignableFrom(activeView)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final class Single extends ViewMatcher {
        private static final long serialVersionUID = 1;
        private final Class<?> _view;

        public Single(Class<?> v) {
            this._view = v;
        }

        public boolean isVisibleForView(Class<?> activeView) {
            return activeView == this._view || this._view.isAssignableFrom(activeView);
        }
    }

    static {
        EMPTY = new ViewMatcher();
    }

    public boolean isVisibleForView(Class<?> cls) {
        return false;
    }

    public static ViewMatcher construct(Class<?>[] views) {
        if (views == null) {
            return EMPTY;
        }
        switch (views.length) {
            case JSONzip.zipEmptyObject /*0*/:
                return EMPTY;
            case Std.STD_FILE /*1*/:
                return new Single(views[0]);
            default:
                return new Multi(views);
        }
    }
}
