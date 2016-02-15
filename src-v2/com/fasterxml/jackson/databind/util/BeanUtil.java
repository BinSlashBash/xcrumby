/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import java.io.Serializable;

public class BeanUtil {
    /*
     * Enabled aggressive block sorting
     */
    protected static boolean isCglibGetCallbacks(AnnotatedMethod object) {
        if ((object = object.getRawType()) == null || !object.isArray() || (object = object.getComponentType().getPackage()) == null || !(object = object.getName()).startsWith("net.sf.cglib") && !object.startsWith("org.hibernate.repackage.cglib")) {
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static boolean isGroovyMetaClassGetter(AnnotatedMethod class_) {
        if ((class_ = class_.getRawType()) == null || class_.isArray() || (class_ = class_.getPackage()) == null || !class_.getName().startsWith("groovy.lang")) {
            return false;
        }
        return true;
    }

    protected static boolean isGroovyMetaClassSetter(AnnotatedMethod object) {
        boolean bl2 = false;
        object = object.getRawParameterType(0).getPackage();
        boolean bl3 = bl2;
        if (object != null) {
            bl3 = bl2;
            if (object.getName().startsWith("groovy.lang")) {
                bl3 = true;
            }
        }
        return bl3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static String manglePropertyName(String string2) {
        int n2 = string2.length();
        if (n2 == 0) {
            return null;
        }
        StringBuilder stringBuilder = null;
        int n3 = 0;
        while (n3 < n2) {
            char c2;
            char c3 = string2.charAt(n3);
            if (c3 == (c2 = Character.toLowerCase(c3))) {
                if (stringBuilder == null) return string2;
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder2 = stringBuilder;
            if (stringBuilder == null) {
                stringBuilder2 = new StringBuilder(string2);
            }
            stringBuilder2.setCharAt(n3, c2);
            ++n3;
            stringBuilder = stringBuilder2;
        }
        return string2;
    }

    public static String okNameForGetter(AnnotatedMethod annotatedMethod) {
        String string2;
        String string3 = annotatedMethod.getName();
        String string4 = string2 = BeanUtil.okNameForIsGetter(annotatedMethod, string3);
        if (string2 == null) {
            string4 = BeanUtil.okNameForRegularGetter(annotatedMethod, string3);
        }
        return string4;
    }

    public static String okNameForIsGetter(AnnotatedMethod serializable, String string2) {
        if (!string2.startsWith("is") || (serializable = serializable.getRawType()) != Boolean.class && serializable != Boolean.TYPE) {
            return null;
        }
        return BeanUtil.manglePropertyName(string2.substring(2));
    }

    public static String okNameForMutator(AnnotatedMethod object, String string2) {
        if ((object = object.getName()).startsWith(string2)) {
            return BeanUtil.manglePropertyName(object.substring(string2.length()));
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String okNameForRegularGetter(AnnotatedMethod annotatedMethod, String string2) {
        if (!string2.startsWith("get") || ("getCallbacks".equals(string2) ? BeanUtil.isCglibGetCallbacks(annotatedMethod) : "getMetaClass".equals(string2) && BeanUtil.isGroovyMetaClassGetter(annotatedMethod))) {
            return null;
        }
        return BeanUtil.manglePropertyName(string2.substring(3));
    }

    public static String okNameForSetter(AnnotatedMethod annotatedMethod) {
        String string2 = BeanUtil.okNameForMutator(annotatedMethod, "set");
        if (string2 != null) {
            String string3 = string2;
            if ("metaClass".equals(string2)) {
                string3 = string2;
                if (BeanUtil.isGroovyMetaClassSetter(annotatedMethod)) {
                    string3 = null;
                }
            }
            return string3;
        }
        return null;
    }
}

