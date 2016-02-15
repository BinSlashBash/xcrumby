package com.crumby.lib.universal;

import com.crumby.lib.GalleryImage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;

public abstract class UniversalImageProducer extends UniversalProducer {
    protected ArrayList<GalleryImage> getImagesFromJson(JsonNode node) throws Exception {
        ((ArrayNode) node.get("images")).insert(0, node.get("hostImage"));
        return convertJsonNodeToGalleryImages(node.get("images"));
    }
}
