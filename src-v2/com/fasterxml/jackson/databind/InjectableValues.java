/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class InjectableValues {
    public abstract Object findInjectableValue(Object var1, DeserializationContext var2, BeanProperty var3, Object var4);

    public static class Std
    extends InjectableValues
    implements Serializable {
        private static final long serialVersionUID = 1;
        protected final Map<String, Object> _values;

        public Std() {
            this(new HashMap<String, Object>());
        }

        public Std(Map<String, Object> map) {
            this._values = map;
        }

        public Std addValue(Class<?> class_, Object object) {
            this._values.put(class_.getName(), object);
            return this;
        }

        public Std addValue(String string2, Object object) {
            this._values.put(string2, object);
            return this;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public Object findInjectableValue(Object object, DeserializationContext object2, BeanProperty beanProperty, Object object3) {
            if (!(object instanceof String)) {
                if (object == null) {
                    object = "[null]";
                    do {
                        throw new IllegalArgumentException("Unrecognized inject value id type (" + (String)object + "), expecting String");
                        break;
                    } while (true);
                }
                object = object.getClass().getName();
                throw new IllegalArgumentException("Unrecognized inject value id type (" + (String)object + "), expecting String");
            }
            object2 = this._values.get(object = (String)object);
            if (object2 != null || this._values.containsKey(object)) return object2;
            throw new IllegalArgumentException("No injectable id with value '" + (String)object + "' found (for property '" + beanProperty.getName() + "')");
        }
    }

}

