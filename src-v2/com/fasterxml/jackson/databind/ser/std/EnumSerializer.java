/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.util.EnumValues;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@JacksonStdImpl
public class EnumSerializer
extends StdScalarSerializer<Enum<?>>
implements ContextualSerializer {
    protected final Boolean _serializeAsIndex;
    protected final EnumValues _values;

    @Deprecated
    public EnumSerializer(EnumValues enumValues) {
        this(enumValues, null);
    }

    public EnumSerializer(EnumValues enumValues, Boolean bl2) {
        super(Enum.class, false);
        this._values = enumValues;
        this._serializeAsIndex = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static Boolean _isShapeWrittenUsingIndex(Class<?> object, JsonFormat.Value object2, boolean bl2) {
        if (object2 == null) {
            return null;
        }
        if ((object2 = object2.getShape()) == null) {
            return null;
        }
        if (object2 == JsonFormat.Shape.ANY) return null;
        if (object2 == JsonFormat.Shape.SCALAR) return null;
        if (object2 == JsonFormat.Shape.STRING) {
            return Boolean.FALSE;
        }
        if (object2.isNumeric()) {
            return Boolean.TRUE;
        }
        object2 = new StringBuilder().append("Unsupported serialization shape (").append(object2).append(") for Enum ").append(object.getName()).append(", not supported as ");
        if (bl2) {
            object = "class";
            throw new IllegalArgumentException(object2.append((String)object).append(" annotation").toString());
        }
        object = "property";
        throw new IllegalArgumentException(object2.append((String)object).append(" annotation").toString());
    }

    public static EnumSerializer construct(Class<Enum<?>> class_, SerializationConfig serializationConfig, BeanDescription beanDescription, JsonFormat.Value value) {
        return new EnumSerializer(EnumValues.construct(serializationConfig, class_), EnumSerializer._isShapeWrittenUsingIndex(class_, value, true));
    }

    protected final boolean _serializeAsIndex(SerializerProvider serializerProvider) {
        if (this._serializeAsIndex != null) {
            return this._serializeAsIndex;
        }
        return serializerProvider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper object, JavaType javaType) throws JsonMappingException {
        if (object.getProvider().isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX)) {
            if ((object = object.expectIntegerFormat(javaType)) == null) return;
            {
                object.numberType(JsonParser.NumberType.INT);
                return;
            }
        } else {
            object = object.expectStringFormat(javaType);
            if (javaType == null || object == null || !javaType.isEnumType()) return;
            {
                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
                Iterator<SerializableString> iterator = this._values.values().iterator();
                do {
                    if (!iterator.hasNext()) {
                        object.enumTypes(linkedHashSet);
                        return;
                    }
                    linkedHashSet.add(iterator.next().getValue());
                } while (true);
            }
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider object, BeanProperty beanProperty) throws JsonMappingException {
        EnumSerializer enumSerializer = this;
        if (beanProperty != null) {
            object = object.getAnnotationIntrospector().findFormat(beanProperty.getMember());
            enumSerializer = this;
            if (object != null) {
                object = EnumSerializer._isShapeWrittenUsingIndex(beanProperty.getType().getRawClass(), (JsonFormat.Value)object, false);
                enumSerializer = this;
                if (object != this._serializeAsIndex) {
                    enumSerializer = new EnumSerializer(this._values, (Boolean)object);
                }
            }
        }
        return enumSerializer;
    }

    public EnumValues getEnumValues() {
        return this._values;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonNode getSchema(SerializerProvider object, Type iterator) {
        ObjectNode objectNode;
        if (this._serializeAsIndex((SerializerProvider)object)) {
            return this.createSchemaNode("integer", true);
        }
        ObjectNode objectNode2 = objectNode = this.createSchemaNode("string", true);
        if (iterator == null) return objectNode2;
        objectNode2 = objectNode;
        if (!object.constructType((Type)((Object)iterator)).isEnumType()) return objectNode2;
        object = objectNode.putArray("enum");
        iterator = this._values.values().iterator();
        do {
            objectNode2 = objectNode;
            if (!iterator.hasNext()) return objectNode2;
            object.add(iterator.next().getValue());
        } while (true);
    }

    @Override
    public final void serialize(Enum<?> enum_, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (this._serializeAsIndex(serializerProvider)) {
            jsonGenerator.writeNumber(enum_.ordinal());
            return;
        }
        jsonGenerator.writeString(this._values.serializedValueFor(enum_));
    }
}

