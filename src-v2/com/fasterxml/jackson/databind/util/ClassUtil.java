/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.annotation.NoClass;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ClassUtil {
    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void _addSuperTypes(Class<?> class_, Class<?> class_2, Collection<Class<?>> collection, boolean bl2) {
        if (class_ == class_2) return;
        if (class_ == null) return;
        if (class_ == Object.class) {
            return;
        }
        if (bl2) {
            if (collection.contains(class_)) return;
            collection.add(class_);
        }
        Class<?>[] arrclass = class_.getInterfaces();
        int n2 = arrclass.length;
        int n3 = 0;
        do {
            if (n3 >= n2) {
                ClassUtil._addSuperTypes(class_.getSuperclass(), class_2, collection, true);
                return;
            }
            ClassUtil._addSuperTypes(arrclass[n3], class_2, collection, true);
            ++n3;
        } while (true);
    }

    public static String canBeABeanType(Class<?> class_) {
        if (class_.isAnnotation()) {
            return "annotation";
        }
        if (class_.isArray()) {
            return "array";
        }
        if (class_.isEnum()) {
            return "enum";
        }
        if (class_.isPrimitive()) {
            return "primitive";
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void checkAndFixAccess(Member member) {
        AccessibleObject accessibleObject = (AccessibleObject)((Object)member);
        try {
            accessibleObject.setAccessible(true);
            return;
        }
        catch (SecurityException var1_3) {
            if (accessibleObject.isAccessible()) return;
            Class class_ = member.getDeclaringClass();
            throw new IllegalArgumentException("Can not access " + member + " (from class " + class_.getName() + "; failed to set access: " + var1_3.getMessage());
        }
    }

    public static <T> T createInstance(Class<T> class_, boolean bl2) throws IllegalArgumentException {
        Constructor constructor = ClassUtil.findConstructor(class_, bl2);
        if (constructor == null) {
            throw new IllegalArgumentException("Class " + class_.getName() + " has no default (no arg) constructor");
        }
        try {
            constructor = constructor.newInstance(new Object[0]);
        }
        catch (Exception var2_3) {
            ClassUtil.unwrapAndThrowAsIAE(var2_3, "Failed to instantiate class " + class_.getName() + ", problem: " + var2_3.getMessage());
            return null;
        }
        return (T)constructor;
    }

    public static Object defaultValue(Class<?> class_) {
        if (class_ == Integer.TYPE) {
            return 0;
        }
        if (class_ == Long.TYPE) {
            return 0;
        }
        if (class_ == Boolean.TYPE) {
            return Boolean.FALSE;
        }
        if (class_ == Double.TYPE) {
            return 0.0;
        }
        if (class_ == Float.TYPE) {
            return Float.valueOf(0.0f);
        }
        if (class_ == Byte.TYPE) {
            return Byte.valueOf(0);
        }
        if (class_ == Short.TYPE) {
            return 0;
        }
        if (class_ == Character.TYPE) {
            return Character.valueOf('\u0000');
        }
        throw new IllegalArgumentException("Class " + class_.getName() + " is not a primitive type");
    }

    public static Class<?> findClass(String object) throws ClassNotFoundException {
        if (object.indexOf(46) < 0) {
            if ("int".equals(object)) {
                return Integer.TYPE;
            }
            if ("long".equals(object)) {
                return Long.TYPE;
            }
            if ("float".equals(object)) {
                return Float.TYPE;
            }
            if ("double".equals(object)) {
                return Double.TYPE;
            }
            if ("boolean".equals(object)) {
                return Boolean.TYPE;
            }
            if ("byte".equals(object)) {
                return Byte.TYPE;
            }
            if ("char".equals(object)) {
                return Character.TYPE;
            }
            if ("short".equals(object)) {
                return Short.TYPE;
            }
            if ("void".equals(object)) {
                return Void.TYPE;
            }
        }
        Serializable serializable = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            try {
                serializable = Class.forName((String)object, true, classLoader);
                return serializable;
            }
            catch (Exception var1_2) {
                serializable = ClassUtil.getRootCause(var1_2);
            }
        }
        try {
            object = Class.forName((String)object);
            return object;
        }
        catch (Exception var2_4) {
            object = serializable;
            if (serializable == null) {
                object = ClassUtil.getRootCause(var2_4);
            }
            if (object instanceof RuntimeException) {
                throw (RuntimeException)object;
            }
            throw new ClassNotFoundException(object.getMessage(), (Throwable)object);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <T> Constructor<T> findConstructor(Class<T> class_, boolean bl2) throws IllegalArgumentException {
        Constructor<T> constructor;
        block6 : {
            constructor = class_.getDeclaredConstructor(new Class[0]);
            if (!bl2) break block6;
            ClassUtil.checkAndFixAccess(constructor);
            return constructor;
        }
        try {
            if (Modifier.isPublic(constructor.getModifiers())) return constructor;
            throw new IllegalArgumentException("Default constructor for " + class_.getName() + " is not accessible (non-public?): not allowed to try modify access via Reflection: can not instantiate type");
        }
        catch (NoSuchMethodException var0_1) {
            do {
                return null;
                break;
            } while (true);
        }
        catch (Exception var2_4) {
            ClassUtil.unwrapAndThrowAsIAE(var2_4, "Failed to find default constructor of class " + class_.getName() + ", problem: " + var2_4.getMessage());
            return null;
        }
    }

    public static Class<? extends Enum<?>> findEnumType(Class<?> class_) {
        Class class_2 = class_;
        if (class_.getSuperclass() != Enum.class) {
            class_2 = class_.getSuperclass();
        }
        return class_2;
    }

    public static Class<? extends Enum<?>> findEnumType(Enum<?> class_) {
        Class class_2;
        class_ = class_2 = class_.getClass();
        if (class_2.getSuperclass() != Enum.class) {
            class_ = class_2.getSuperclass();
        }
        return class_;
    }

    public static Class<? extends Enum<?>> findEnumType(EnumMap<?, ?> enumMap) {
        if (!enumMap.isEmpty()) {
            return ClassUtil.findEnumType((Enum)enumMap.keySet().iterator().next());
        }
        return EnumTypeLocator.instance.enumTypeFor(enumMap);
    }

    public static Class<? extends Enum<?>> findEnumType(EnumSet<?> enumSet) {
        if (!enumSet.isEmpty()) {
            return ClassUtil.findEnumType((Enum)enumSet.iterator().next());
        }
        return EnumTypeLocator.instance.enumTypeFor(enumSet);
    }

    public static List<Class<?>> findSuperTypes(Class<?> class_, Class<?> class_2) {
        return ClassUtil.findSuperTypes(class_, class_2, new ArrayList(8));
    }

    public static List<Class<?>> findSuperTypes(Class<?> class_, Class<?> class_2, List<Class<?>> list) {
        ClassUtil._addSuperTypes(class_, class_2, list, false);
        return list;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getClassDescription(Object class_) {
        if (class_ == null) {
            return "unknown";
        }
        if (class_ instanceof Class) {
            do {
                return class_.getName();
                break;
            } while (true);
        }
        class_ = class_.getClass();
        return class_.getName();
    }

    public static Class<?> getOuterClass(Class<?> class_) {
        block5 : {
            if (class_.getEnclosingMethod() == null) break block5;
            return null;
        }
        try {
            if (!Modifier.isStatic(class_.getModifiers())) {
                class_ = class_.getEnclosingClass();
                return class_;
            }
        }
        catch (NullPointerException var0_1) {
            return null;
        }
        catch (SecurityException var0_2) {
            // empty catch block
        }
        return null;
    }

    public static Throwable getRootCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean hasGetterSignature(Method method) {
        Class<?>[] arrclass;
        if (Modifier.isStatic(method.getModifiers()) || (arrclass = method.getParameterTypes()) != null && arrclass.length != 0 || Void.TYPE == method.getReturnType()) {
            return false;
        }
        return true;
    }

    public static final boolean isBogusClass(Class<?> class_) {
        if (class_ == Void.class || class_ == Void.TYPE || class_ == NoClass.class) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isCollectionMapOrArray(Class<?> class_) {
        if (class_.isArray() || Collection.class.isAssignableFrom(class_) || Map.class.isAssignableFrom(class_)) {
            return true;
        }
        return false;
    }

    public static boolean isConcrete(Class<?> class_) {
        if ((class_.getModifiers() & 1536) == 0) {
            return true;
        }
        return false;
    }

    public static boolean isConcrete(Member member) {
        if ((member.getModifiers() & 1536) == 0) {
            return true;
        }
        return false;
    }

    public static boolean isJacksonStdImpl(Class<?> class_) {
        if (class_.getAnnotation(JacksonStdImpl.class) != null) {
            return true;
        }
        return false;
    }

    public static boolean isJacksonStdImpl(Object object) {
        if (object != null && ClassUtil.isJacksonStdImpl(object.getClass())) {
            return true;
        }
        return false;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String isLocalType(Class<?> class_, boolean bl2) {
        try {
            if (class_.getEnclosingMethod() != null) {
                return "local/anonymous";
            }
            if (bl2) return null;
            if (class_.getEnclosingClass() == null) return null;
            if (Modifier.isStatic(class_.getModifiers())) return null;
            return "non-static member class";
        }
        catch (NullPointerException var0_1) {
            // empty catch block
        }
        return null;
        catch (SecurityException securityException) {
            return null;
        }
    }

    public static boolean isProxyType(Class<?> object) {
        if ((object = object.getName()).startsWith("net.sf.cglib.proxy.") || object.startsWith("org.hibernate.proxy.")) {
            return true;
        }
        return false;
    }

    public static void throwAsIAE(Throwable throwable) {
        ClassUtil.throwAsIAE(throwable, throwable.getMessage());
    }

    public static void throwAsIAE(Throwable throwable, String string2) {
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        throw new IllegalArgumentException(string2, throwable);
    }

    public static void throwRootCause(Throwable throwable) throws Exception {
        if ((throwable = ClassUtil.getRootCause(throwable)) instanceof Exception) {
            throw (Exception)throwable;
        }
        throw (Error)throwable;
    }

    public static void unwrapAndThrowAsIAE(Throwable throwable) {
        ClassUtil.throwAsIAE(ClassUtil.getRootCause(throwable));
    }

    public static void unwrapAndThrowAsIAE(Throwable throwable, String string2) {
        ClassUtil.throwAsIAE(ClassUtil.getRootCause(throwable), string2);
    }

    public static Class<?> wrapperType(Class<?> class_) {
        if (class_ == Integer.TYPE) {
            return Integer.class;
        }
        if (class_ == Long.TYPE) {
            return Long.class;
        }
        if (class_ == Boolean.TYPE) {
            return Boolean.class;
        }
        if (class_ == Double.TYPE) {
            return Double.class;
        }
        if (class_ == Float.TYPE) {
            return Float.class;
        }
        if (class_ == Byte.TYPE) {
            return Byte.class;
        }
        if (class_ == Short.TYPE) {
            return Short.class;
        }
        if (class_ == Character.TYPE) {
            return Character.class;
        }
        throw new IllegalArgumentException("Class " + class_.getName() + " is not a primitive type");
    }

    private static class EnumTypeLocator {
        static final EnumTypeLocator instance = new EnumTypeLocator();
        private final Field enumMapTypeField = EnumTypeLocator.locateField(EnumMap.class, "elementType", Class.class);
        private final Field enumSetTypeField = EnumTypeLocator.locateField(EnumSet.class, "elementType", Class.class);

        private EnumTypeLocator() {
        }

        private Object get(Object object, Field field) {
            try {
                object = field.get(object);
                return object;
            }
            catch (Exception var1_2) {
                throw new IllegalArgumentException(var1_2);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private static Field locateField(Class<?> class_, String class_2, Class<?> class_3) {
            void var1_9;
            block8 : {
                void var2_10;
                Field field = null;
                Field[] arrfield = class_.getDeclaredFields();
                int n2 = arrfield.length;
                int n3 = 0;
                do {
                    class_ = field;
                    if (n3 >= n2 || class_2.equals((class_ = arrfield[n3]).getName()) && class_.getType() == var2_10) {
                        Class class_4 = class_;
                        if (class_ == null) {
                            break;
                        }
                        break block8;
                    }
                    ++n3;
                } while (true);
                n2 = arrfield.length;
                n3 = 0;
                do {
                    void var1_8;
                    Class class_5 = class_;
                    if (n3 >= n2) break;
                    field = arrfield[n3];
                    Class class_6 = class_;
                    if (field.getType() == var2_10) {
                        if (class_ != null) {
                            return null;
                        }
                        Field field2 = field;
                    }
                    ++n3;
                    class_ = var1_8;
                } while (true);
            }
            if (var1_9 == null) return var1_9;
            try {
                var1_9.setAccessible(true);
                return var1_9;
            }
            catch (Throwable var0_1) {
                return var1_9;
            }
        }

        public Class<? extends Enum<?>> enumTypeFor(EnumMap<?, ?> enumMap) {
            if (this.enumMapTypeField != null) {
                return (Class)this.get(enumMap, this.enumMapTypeField);
            }
            throw new IllegalStateException("Can not figure out type for EnumMap (odd JDK platform?)");
        }

        public Class<? extends Enum<?>> enumTypeFor(EnumSet<?> enumSet) {
            if (this.enumSetTypeField != null) {
                return (Class)this.get(enumSet, this.enumSetTypeField);
            }
            throw new IllegalStateException("Can not figure out type for EnumSet (odd JDK platform?)");
        }
    }

}

