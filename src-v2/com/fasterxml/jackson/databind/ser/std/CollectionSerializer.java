/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class CollectionSerializer
extends AsArraySerializerBase<Collection<?>> {
    public CollectionSerializer(JavaType javaType, boolean bl2, TypeSerializer typeSerializer, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer) {
        super(Collection.class, javaType, bl2, typeSerializer, beanProperty, jsonSerializer);
    }

    public CollectionSerializer(CollectionSerializer collectionSerializer, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        super(collectionSerializer, beanProperty, typeSerializer, jsonSerializer);
    }

    @Override
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new CollectionSerializer(this._elementType, this._staticTyping, typeSerializer, this._property, this._elementSerializer);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean hasSingleElement(Collection<?> iterator) {
        if (!(iterator = iterator.iterator()).hasNext()) {
            return false;
        }
        iterator.next();
        if (iterator.hasNext()) return false;
        return true;
    }

    @Override
    public boolean isEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    /*
     * Exception decompiling
     */
    @Override
    public void serializeContents(Collection<?> var1_1, JsonGenerator var2_2, SerializerProvider var3_4) throws IOException, JsonGenerationException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:394)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:446)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void serializeContentsUsing(Collection<?> collection, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<Object> jsonSerializer) throws IOException, JsonGenerationException {
        Iterator iterator = collection.iterator();
        if (!iterator.hasNext()) return;
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        int n2 = 0;
        do {
            Object obj;
            if ((obj = iterator.next()) == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
            } else if (typeSerializer == null) {
                jsonSerializer.serialize(obj, jsonGenerator, serializerProvider);
            } else {
                jsonSerializer.serializeWithType(obj, jsonGenerator, serializerProvider, typeSerializer);
            }
            ++n2;
            continue;
            catch (Exception exception) {
                this.wrapAndThrow(serializerProvider, (Throwable)exception, collection, n2);
            }
        } while (iterator.hasNext());
    }

    public CollectionSerializer withResolved(BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        return new CollectionSerializer(this, beanProperty, typeSerializer, jsonSerializer);
    }
}

