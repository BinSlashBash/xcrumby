/*
 * Decompiled with CFR 0_110.
 */
package com.google.ads.mediation;

import com.google.android.gms.internal.dw;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Deprecated
public abstract class MediationServerParameters {
    protected void a() {
    }

    public void load(Map<String, String> object) throws MappingException {
        HashMap<String, Field> hashMap = new HashMap<String, Field>();
        for (Field field2 : this.getClass().getFields()) {
            Parameter parameter = (Parameter)field2.getAnnotation(Parameter.class);
            if (parameter == null) continue;
            hashMap.put(parameter.name(), field2);
        }
        if (hashMap.isEmpty()) {
            dw.z("No server options fields detected. To suppress this message either add a field with the @Parameter annotation, or override the load() method.");
        }
        for (Map.Entry entry : object.entrySet()) {
            Field field2;
            field2 = (Field)hashMap.remove(entry.getKey());
            if (field2 != null) {
                try {
                    field2.set(this, entry.getValue());
                }
                catch (IllegalAccessException var6_11) {
                    dw.z("Server option \"" + (String)entry.getKey() + "\" could not be set: Illegal Access");
                }
                catch (IllegalArgumentException var6_12) {
                    dw.z("Server option \"" + (String)entry.getKey() + "\" could not be set: Bad Type");
                }
                continue;
            }
            dw.v("Unexpected server option: " + (String)entry.getKey() + " = \"" + (String)entry.getValue() + "\"");
        }
        object = new StringBuilder();
        for (Field field3 : hashMap.values()) {
            if (!((Parameter)field3.getAnnotation(Parameter.class)).required()) continue;
            dw.z("Required server option missing: " + ((Parameter)field3.getAnnotation(Parameter.class)).name());
            if (object.length() > 0) {
                object.append(", ");
            }
            object.append(((Parameter)field3.getAnnotation(Parameter.class)).name());
        }
        if (object.length() > 0) {
            throw new MappingException("Required server option(s) missing: " + object.toString());
        }
        this.a();
    }

    public static final class MappingException
    extends Exception {
        public MappingException(String string2) {
            super(string2);
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    protected static @interface Parameter {
        public String name();

        public boolean required() default 1;
    }

}

