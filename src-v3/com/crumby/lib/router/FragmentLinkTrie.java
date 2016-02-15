package com.crumby.lib.router;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FragmentLinkTrie {
    public static final boolean MATCH_NAME = false;
    public static final boolean MATCH_URL = true;
    private static int MAX_RESULT_SIZE;
    JsonNode nameRootNode;
    FileOpener opener;
    private boolean terminateCurrentMatch;
    JsonNode urlRootNode;

    public FragmentLinkTrie(FileOpener opener) {
    }

    private JsonNode getNode(String resourceId) {
        JsonNode node = null;
        try {
            node = new ObjectMapper().readTree(this.opener.open("branches/" + resourceId + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    private void iterateOverLeaves(String search, JsonNode node, List<Integer> results, String root) {
        Iterator<String> it = node.fieldNames();
        while (it.hasNext() && !this.terminateCurrentMatch) {
            String key = (String) it.next();
            if (!key.equals("_v")) {
                JsonNode next_node = node.get(key);
                if (!next_node.isContainerNode()) {
                    next_node = getNode(next_node.asText());
                    ((ObjectNode) node).put(key, next_node);
                }
                find(search, next_node, results, root + key);
            }
        }
    }

    static {
        MAX_RESULT_SIZE = 5;
    }

    private boolean find(String search, JsonNode node, List<Integer> results, String root) {
        if (results.size() > MAX_RESULT_SIZE) {
            return MATCH_URL;
        }
        int i = 0;
        JsonNode found = null;
        int search_length = search.length();
        while (i < search_length) {
            if (this.terminateCurrentMatch) {
                return MATCH_NAME;
            }
            char character = search.charAt(i);
            int m = 0;
            char next_character = character;
            if (character == ' ') {
                m = 0 + 1;
                next_character = search.charAt(i + 1);
            }
            String index = String.valueOf(next_character);
            JsonNode next_node = node.get(index);
            if (next_node != null) {
                found = node;
                if (!next_node.isContainerNode()) {
                    next_node = getNode(next_node.asText());
                    ((ObjectNode) node).put(index, next_node);
                }
                node = next_node;
                root = root + next_character;
                i = (i + 1) + m;
            } else {
                if (found != null) {
                    iterateOverLeaves(search, found, results, root);
                }
                String next_search = search;
                if (character == ' ') {
                    next_search = next_search.substring(i);
                }
                iterateOverLeaves(next_search, node, results, root);
                return MATCH_NAME;
            }
        }
        getValues(node, results);
        return MATCH_URL;
    }

    private void getValues(JsonNode node, List<Integer> results) {
        if (!this.terminateCurrentMatch && results.size() <= MAX_RESULT_SIZE) {
            JsonNode id = node.get("_v");
            if (id != null) {
                results.add(Integer.valueOf(id.asInt()));
            }
            Iterator<JsonNode> it = node.elements();
            while (it.hasNext() && !this.terminateCurrentMatch && results.size() <= MAX_RESULT_SIZE) {
                getValues((JsonNode) it.next(), results);
            }
        }
    }

    public List<Integer> search(String query, boolean matchUrl) {
        JsonNode node = matchUrl ? this.urlRootNode : this.nameRootNode;
        List<Integer> results = new ArrayList();
        query = MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + query.trim();
        if (!query.equals(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
            this.terminateCurrentMatch = MATCH_NAME;
            find(query, node, results, UnsupportedUrlFragment.DISPLAY_NAME);
        }
        return results;
    }

    public void terminateSearch() {
        this.terminateCurrentMatch = MATCH_URL;
    }
}
