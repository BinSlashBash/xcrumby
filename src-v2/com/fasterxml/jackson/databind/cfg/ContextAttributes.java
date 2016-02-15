/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.cfg;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class ContextAttributes {
    public static ContextAttributes getEmpty() {
        return Impl.getEmpty();
    }

    public abstract Object getAttribute(Object var1);

    public abstract ContextAttributes withPerCallAttribute(Object var1, Object var2);

    public abstract ContextAttributes withSharedAttribute(Object var1, Object var2);

    public abstract ContextAttributes withSharedAttributes(Map<Object, Object> var1);

    public abstract ContextAttributes withoutSharedAttribute(Object var1);

    public static class Impl
    extends ContextAttributes
    implements Serializable {
        protected static final Impl EMPTY = new Impl(Collections.<Object, Object>emptyMap());
        protected static final Object NULL_SURROGATE = new Object();
        private static final long serialVersionUID = 1;
        protected transient Map<Object, Object> _nonShared;
        protected final Map<Object, Object> _shared;

        protected Impl(Map<Object, Object> map) {
            this._shared = map;
            this._nonShared = null;
        }

        protected Impl(Map<Object, Object> map, Map<Object, Object> map2) {
            this._shared = map;
            this._nonShared = map2;
        }

        private Map<Object, Object> _copy(Map<Object, Object> map) {
            return new HashMap<Object, Object>(map);
        }

        public static ContextAttributes getEmpty() {
            return EMPTY;
        }

        @Override
        public Object getAttribute(Object object) {
            Object object2;
            if (this._nonShared != null && (object2 = this._nonShared.get(object)) != null) {
                object = object2;
                if (object2 == NULL_SURROGATE) {
                    object = null;
                }
                return object;
            }
            return this._shared.get(object);
        }

        protected ContextAttributes nonSharedInstance(Object object, Object object2) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            Object object3 = object2;
            if (object2 == null) {
                object3 = NULL_SURROGATE;
            }
            hashMap.put(object, object3);
            return new Impl(this._shared, hashMap);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public ContextAttributes withPerCallAttribute(Object object, Object object2) {
            Object object3 = object2;
            if (object2 == null) {
                object2 = this;
                if (!this._shared.containsKey(object)) return object2;
                object3 = NULL_SURROGATE;
            }
            if (this._nonShared == null) {
                return this.nonSharedInstance(object, object3);
            }
            this._nonShared.put(object, object3);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public ContextAttributes withSharedAttribute(Object object, Object object2) {
            Map map = this == EMPTY ? new HashMap<Object, Object>(8) : this._copy(this._shared);
            map.put(object, object2);
            return new Impl(map);
        }

        @Override
        public ContextAttributes withSharedAttributes(Map<Object, Object> map) {
            return new Impl(map);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public ContextAttributes withoutSharedAttribute(Object object) {
            if (this._shared.isEmpty() || !this._shared.containsKey(object)) {
                return this;
            }
            if (this._shared.size() == 1) {
                return EMPTY;
            }
            Map<Object, Object> map = this._copy(this._shared);
            map.remove(object);
            return new Impl(map);
        }
    }

}

