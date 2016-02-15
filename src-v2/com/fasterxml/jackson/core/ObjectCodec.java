/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Iterator;

public abstract class ObjectCodec
extends TreeCodec
implements Versioned {
    protected ObjectCodec() {
    }

    @Override
    public abstract TreeNode createArrayNode();

    @Override
    public abstract TreeNode createObjectNode();

    public JsonFactory getFactory() {
        return this.getJsonFactory();
    }

    @Deprecated
    public JsonFactory getJsonFactory() {
        return this.getFactory();
    }

    @Override
    public abstract <T extends TreeNode> T readTree(JsonParser var1) throws IOException, JsonProcessingException;

    public abstract <T> T readValue(JsonParser var1, ResolvedType var2) throws IOException, JsonProcessingException;

    public abstract <T> T readValue(JsonParser var1, TypeReference<?> var2) throws IOException, JsonProcessingException;

    public abstract <T> T readValue(JsonParser var1, Class<T> var2) throws IOException, JsonProcessingException;

    public abstract <T> Iterator<T> readValues(JsonParser var1, ResolvedType var2) throws IOException, JsonProcessingException;

    public abstract <T> Iterator<T> readValues(JsonParser var1, TypeReference<?> var2) throws IOException, JsonProcessingException;

    public abstract <T> Iterator<T> readValues(JsonParser var1, Class<T> var2) throws IOException, JsonProcessingException;

    @Override
    public abstract JsonParser treeAsTokens(TreeNode var1);

    public abstract <T> T treeToValue(TreeNode var1, Class<T> var2) throws JsonProcessingException;

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public abstract void writeTree(JsonGenerator var1, TreeNode var2) throws IOException, JsonProcessingException;

    public abstract void writeValue(JsonGenerator var1, Object var2) throws IOException, JsonProcessingException;
}

