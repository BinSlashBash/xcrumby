/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.annotation;

import java.io.Serializable;

public abstract class ObjectIdGenerator<T>
implements Serializable {
    public abstract boolean canUseFor(ObjectIdGenerator<?> var1);

    public abstract ObjectIdGenerator<T> forScope(Class<?> var1);

    public abstract T generateId(Object var1);

    public abstract Class<?> getScope();

    public abstract IdKey key(Object var1);

    public abstract ObjectIdGenerator<T> newForSerialization(Object var1);

    public static final class IdKey
    implements Serializable {
        private static final long serialVersionUID = 1;
        private final int hashCode;
        public final Object key;
        public final Class<?> scope;
        public final Class<?> type;

        public IdKey(Class<?> class_, Class<?> class_2, Object object) {
            int n2;
            this.type = class_;
            this.scope = class_2;
            this.key = object;
            int n3 = n2 = object.hashCode() + class_.getName().hashCode();
            if (class_2 != null) {
                n3 = n2 ^ class_2.getName().hashCode();
            }
            this.hashCode = n3;
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (object.getClass() != this.getClass()) {
                return false;
            }
            object = (IdKey)object;
            if (!object.key.equals(this.key)) return false;
            if (object.type != this.type) return false;
            if (object.scope == this.scope) return true;
            return false;
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

}

