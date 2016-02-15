/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import com.fasterxml.jackson.databind.PropertyName;

public class ObjectIdInfo {
    protected final boolean _alwaysAsId;
    protected final Class<? extends ObjectIdGenerator<?>> _generator;
    protected final PropertyName _propertyName;
    private final Class<? extends ObjectIdResolver> _resolver;
    protected final Class<?> _scope;

    @Deprecated
    public ObjectIdInfo(PropertyName propertyName, Class<?> class_, Class<? extends ObjectIdGenerator<?>> class_2) {
        this(propertyName, class_, class_2, false);
    }

    public ObjectIdInfo(PropertyName propertyName, Class<?> class_, Class<? extends ObjectIdGenerator<?>> class_2, Class<? extends ObjectIdResolver> class_3) {
        this(propertyName, class_, class_2, false, class_3);
    }

    protected ObjectIdInfo(PropertyName propertyName, Class<?> class_, Class<? extends ObjectIdGenerator<?>> class_2, boolean bl2) {
        this(propertyName, class_, class_2, bl2, SimpleObjectIdResolver.class);
    }

    protected ObjectIdInfo(PropertyName var1_1, Class<?> class_, Class<? extends ObjectIdGenerator<?>> class_2, boolean bl2, Class<? extends ObjectIdResolver> class_3) {
        void var2_5;
        void var3_6;
        void var5_8;
        void var4_7;
        void var1_4;
        this._propertyName = var1_1;
        this._scope = var2_5;
        this._generator = var3_6;
        this._alwaysAsId = var4_7;
        void var1_2 = var5_8;
        if (var5_8 == null) {
            reference var1_3 = SimpleObjectIdResolver.class;
        }
        this._resolver = var1_4;
    }

    @Deprecated
    public ObjectIdInfo(String string2, Class<?> class_, Class<? extends ObjectIdGenerator<?>> class_2) {
        this(new PropertyName(string2), class_, class_2, false);
    }

    public boolean getAlwaysAsId() {
        return this._alwaysAsId;
    }

    public Class<? extends ObjectIdGenerator<?>> getGeneratorType() {
        return this._generator;
    }

    public PropertyName getPropertyName() {
        return this._propertyName;
    }

    public Class<? extends ObjectIdResolver> getResolverType() {
        return this._resolver;
    }

    public Class<?> getScope() {
        return this._scope;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append("ObjectIdInfo: propName=").append(this._propertyName).append(", scope=");
        String string2 = this._scope == null ? "null" : this._scope.getName();
        stringBuilder = stringBuilder.append(string2).append(", generatorType=");
        if (this._generator == null) {
            string2 = "null";
            return stringBuilder.append(string2).append(", alwaysAsId=").append(this._alwaysAsId).toString();
        }
        string2 = this._generator.getName();
        return stringBuilder.append(string2).append(", alwaysAsId=").append(this._alwaysAsId).toString();
    }

    public ObjectIdInfo withAlwaysAsId(boolean bl2) {
        if (this._alwaysAsId == bl2) {
            return this;
        }
        return new ObjectIdInfo(this._propertyName, this._scope, this._generator, bl2);
    }
}

