/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import java.util.UUID;

public class ObjectIdGenerators {

    private static abstract class Base<T>
    extends ObjectIdGenerator<T> {
        protected final Class<?> _scope;

        protected Base(Class<?> class_) {
            this._scope = class_;
        }

        @Override
        public boolean canUseFor(ObjectIdGenerator<?> objectIdGenerator) {
            if (objectIdGenerator.getClass() == this.getClass() && objectIdGenerator.getScope() == this._scope) {
                return true;
            }
            return false;
        }

        @Override
        public abstract T generateId(Object var1);

        @Override
        public final Class<?> getScope() {
            return this._scope;
        }
    }

    public static final class IntSequenceGenerator
    extends Base<Integer> {
        private static final long serialVersionUID = 1;
        protected transient int _nextValue;

        public IntSequenceGenerator() {
            this(Object.class, -1);
        }

        public IntSequenceGenerator(Class<?> class_, int n2) {
            super(class_);
            this._nextValue = n2;
        }

        @Override
        public ObjectIdGenerator<Integer> forScope(Class<?> class_) {
            if (this._scope == class_) {
                return this;
            }
            return new IntSequenceGenerator(class_, this._nextValue);
        }

        @Override
        public Integer generateId(Object object) {
            int n2 = this._nextValue++;
            return n2;
        }

        protected int initialValue() {
            return 1;
        }

        @Override
        public ObjectIdGenerator.IdKey key(Object object) {
            return new ObjectIdGenerator.IdKey(this.getClass(), this._scope, object);
        }

        @Override
        public ObjectIdGenerator<Integer> newForSerialization(Object object) {
            return new IntSequenceGenerator(this._scope, this.initialValue());
        }
    }

    public static abstract class None
    extends ObjectIdGenerator<Object> {
    }

    public static abstract class PropertyGenerator
    extends Base<Object> {
        private static final long serialVersionUID = 1;

        protected PropertyGenerator(Class<?> class_) {
            super(class_);
        }
    }

    public static final class UUIDGenerator
    extends Base<UUID> {
        private static final long serialVersionUID = 1;

        public UUIDGenerator() {
            this(Object.class);
        }

        private UUIDGenerator(Class<?> class_) {
            super(Object.class);
        }

        @Override
        public boolean canUseFor(ObjectIdGenerator<?> objectIdGenerator) {
            if (objectIdGenerator.getClass() == this.getClass()) {
                return true;
            }
            return false;
        }

        @Override
        public ObjectIdGenerator<UUID> forScope(Class<?> class_) {
            return this;
        }

        @Override
        public UUID generateId(Object object) {
            return UUID.randomUUID();
        }

        @Override
        public ObjectIdGenerator.IdKey key(Object object) {
            return new ObjectIdGenerator.IdKey(this.getClass(), null, object);
        }

        @Override
        public ObjectIdGenerator<UUID> newForSerialization(Object object) {
            return this;
        }
    }

}

