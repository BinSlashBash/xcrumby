/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class UnsafeAllocator {
    public static UnsafeAllocator create() {
        try {
            Object object = Class.forName("sun.misc.Unsafe");
            Object object2 = object.getDeclaredField("theUnsafe");
            object2.setAccessible(true);
            object2 = object2.get(null);
            object = new UnsafeAllocator(object.getMethod("allocateInstance", Class.class), object2){
                final /* synthetic */ Method val$allocateInstance;
                final /* synthetic */ Object val$unsafe;

                @Override
                public <T> T newInstance(Class<T> class_) throws Exception {
                    return (T)this.val$allocateInstance.invoke(this.val$unsafe, class_);
                }
            };
            return object;
        }
        catch (Exception var1_1) {
            try {
                Object object = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
                object.setAccessible(true);
                object = new UnsafeAllocator((Method)object){
                    final /* synthetic */ Method val$newInstance;

                    @Override
                    public <T> T newInstance(Class<T> class_) throws Exception {
                        return (T)this.val$newInstance.invoke(null, class_, Object.class);
                    }
                };
                return object;
            }
            catch (Exception var1_3) {
                try {
                    Object object = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
                    object.setAccessible(true);
                    int n2 = (Integer)object.invoke(null, Object.class);
                    object = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, Integer.TYPE);
                    object.setAccessible(true);
                    object = new UnsafeAllocator((Method)object, n2){
                        final /* synthetic */ int val$constructorId;
                        final /* synthetic */ Method val$newInstance;

                        @Override
                        public <T> T newInstance(Class<T> class_) throws Exception {
                            return (T)this.val$newInstance.invoke(null, class_, this.val$constructorId);
                        }
                    };
                    return object;
                }
                catch (Exception var1_5) {
                    return new UnsafeAllocator(){

                        @Override
                        public <T> T newInstance(Class<T> class_) {
                            throw new UnsupportedOperationException("Cannot allocate " + class_);
                        }
                    };
                }
            }
        }
    }

    public abstract <T> T newInstance(Class<T> var1) throws Exception;

}

