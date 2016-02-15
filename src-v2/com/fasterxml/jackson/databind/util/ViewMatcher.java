/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import java.io.Serializable;

public class ViewMatcher
implements Serializable {
    protected static final ViewMatcher EMPTY = new ViewMatcher();
    private static final long serialVersionUID = 1;

    public static ViewMatcher construct(Class<?>[] arrclass) {
        if (arrclass == null) {
            return EMPTY;
        }
        switch (arrclass.length) {
            default: {
                return new Multi(arrclass);
            }
            case 0: {
                return EMPTY;
            }
            case 1: 
        }
        return new Single(arrclass[0]);
    }

    public boolean isVisibleForView(Class<?> class_) {
        return false;
    }

    private static final class Multi
    extends ViewMatcher
    implements Serializable {
        private static final long serialVersionUID = 1;
        private final Class<?>[] _views;

        public Multi(Class<?>[] arrclass) {
            this._views = arrclass;
        }

        @Override
        public boolean isVisibleForView(Class<?> class_) {
            for (Class class_2 : this._views) {
                if (class_ != class_2 && !class_2.isAssignableFrom(class_)) continue;
                return true;
            }
            return false;
        }
    }

    private static final class Single
    extends ViewMatcher {
        private static final long serialVersionUID = 1;
        private final Class<?> _view;

        public Single(Class<?> class_) {
            this._view = class_;
        }

        @Override
        public boolean isVisibleForView(Class<?> class_) {
            if (class_ == this._view || this._view.isAssignableFrom(class_)) {
                return true;
            }
            return false;
        }
    }

}

