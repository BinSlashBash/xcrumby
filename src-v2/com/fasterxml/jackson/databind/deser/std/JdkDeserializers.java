/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.AtomicBooleanDeserializer;
import com.fasterxml.jackson.databind.deser.std.ByteBufferDeserializer;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.fasterxml.jackson.databind.deser.std.StackTraceElementDeserializer;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class JdkDeserializers {
    private static final HashSet<String> _classNames = new HashSet();

    static {
        for (Class class_2 : new Class[]{UUID.class, AtomicBoolean.class, StackTraceElement.class, ByteBuffer.class}) {
            _classNames.add(class_2.getName());
        }
        for (Class class_ : FromStringDeserializer.types()) {
            _classNames.add(class_.getName());
        }
    }

    public static JsonDeserializer<?> find(Class<?> class_, String object) {
        if (_classNames.contains(object)) {
            object = FromStringDeserializer.findDeserializer(class_);
            if (object != null) {
                return object;
            }
            if (class_ == UUID.class) {
                return new UUIDDeserializer();
            }
            if (class_ == StackTraceElement.class) {
                return new StackTraceElementDeserializer();
            }
            if (class_ == AtomicBoolean.class) {
                return new AtomicBooleanDeserializer();
            }
            if (class_ == ByteBuffer.class) {
                return new ByteBufferDeserializer();
            }
        }
        return null;
    }
}

