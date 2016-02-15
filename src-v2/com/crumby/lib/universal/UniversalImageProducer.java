/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.universal;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.universal.UniversalProducer;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;

public abstract class UniversalImageProducer
extends UniversalProducer {
    @Override
    protected ArrayList<GalleryImage> getImagesFromJson(JsonNode jsonNode) throws Exception {
        ((ArrayNode)jsonNode.get("images")).insert(0, (JsonNode)jsonNode.get("hostImage"));
        return this.convertJsonNodeToGalleryImages((JsonNode)jsonNode.get("images"));
    }
}

