/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.util.LRUMap;
import java.io.Serializable;

public class RootNameLookup
implements Serializable {
    private static final long serialVersionUID = 1;
    protected transient LRUMap<ClassKey, PropertyName> _rootNames = new LRUMap(20, 200);

    public PropertyName findRootName(JavaType javaType, MapperConfig<?> mapperConfig) {
        return this.findRootName(javaType.getRawClass(), mapperConfig);
    }

    public PropertyName findRootName(Class<?> class_, MapperConfig<?> object) {
        ClassKey classKey;
        block3 : {
            classKey = new ClassKey(class_);
            Object object2 = this._rootNames.get(classKey);
            if (object2 != null) {
                return object2;
            }
            object2 = object.introspectClassAnnotations(class_);
            object2 = object.getAnnotationIntrospector().findRootName(object2.getClassInfo());
            if (object2 != null) {
                object = object2;
                if (object2.hasSimpleName()) break block3;
            }
            object = new PropertyName(class_.getSimpleName());
        }
        this._rootNames.put(classKey, (PropertyName)object);
        return object;
    }

    protected Object readResolve() {
        return new RootNameLookup();
    }
}

