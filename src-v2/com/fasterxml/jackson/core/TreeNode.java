/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import java.util.Iterator;

public interface TreeNode {
    public JsonToken asToken();

    public TreeNode at(JsonPointer var1);

    public TreeNode at(String var1) throws IllegalArgumentException;

    public Iterator<String> fieldNames();

    public TreeNode get(int var1);

    public TreeNode get(String var1);

    public boolean isArray();

    public boolean isContainerNode();

    public boolean isMissingNode();

    public boolean isObject();

    public boolean isValueNode();

    public JsonParser.NumberType numberType();

    public TreeNode path(int var1);

    public TreeNode path(String var1);

    public int size();

    public JsonParser traverse();

    public JsonParser traverse(ObjectCodec var1);
}

