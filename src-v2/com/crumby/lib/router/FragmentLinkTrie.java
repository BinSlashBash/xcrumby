/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.router;

import com.crumby.lib.router.FileOpener;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FragmentLinkTrie {
    public static final boolean MATCH_NAME = false;
    public static final boolean MATCH_URL = true;
    private static int MAX_RESULT_SIZE = 5;
    JsonNode nameRootNode;
    FileOpener opener;
    private boolean terminateCurrentMatch;
    JsonNode urlRootNode;

    public FragmentLinkTrie(FileOpener fileOpener) {
    }

    private boolean find(String string2, JsonNode object, List<Integer> list, String object2) {
        if (list.size() > MAX_RESULT_SIZE) {
            return true;
        }
        int n2 = 0;
        JsonNode jsonNode = null;
        int n3 = string2.length();
        String string3 = object2;
        while (n2 < n3) {
            TreeNode treeNode;
            String string4;
            if (this.terminateCurrentMatch) {
                return false;
            }
            char c2 = string2.charAt(n2);
            int n4 = 0;
            char c3 = c2;
            if (c2 == ' ') {
                n4 = 0 + 1;
                c3 = string2.charAt(n2 + 1);
            }
            if ((treeNode = object.get(string4 = String.valueOf(c3))) != null) {
                jsonNode = object;
                object2 = treeNode;
                if (!treeNode.isContainerNode()) {
                    object2 = this.getNode(treeNode.asText());
                    ((ObjectNode)object).put(string4, (JsonNode)object2);
                }
                object = object2;
                string3 = string3 + c3;
                n2 = n2 + 1 + n4;
                continue;
            }
            if (jsonNode != null) {
                this.iterateOverLeaves(string2, jsonNode, list, string3);
            }
            object2 = string2;
            if (c2 == ' ') {
                object2 = string2.substring(n2);
            }
            this.iterateOverLeaves((String)object2, (JsonNode)object, list, string3);
            return false;
        }
        this.getValues((JsonNode)object, list);
        return true;
    }

    private JsonNode getNode(String object) {
        try {
            object = new ObjectMapper().readTree(this.opener.open("branches/" + (String)object + ".json"));
            return object;
        }
        catch (IOException var1_2) {
            var1_2.printStackTrace();
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void getValues(JsonNode iterator, List<Integer> list) {
        if (!this.terminateCurrentMatch && list.size() <= MAX_RESULT_SIZE) {
            TreeNode treeNode = iterator.get("_v");
            if (treeNode != null) {
                list.add(treeNode.asInt());
            }
            iterator = iterator.elements();
            while (iterator.hasNext() && !this.terminateCurrentMatch && list.size() <= MAX_RESULT_SIZE) {
                this.getValues(iterator.next(), list);
            }
        }
    }

    private void iterateOverLeaves(String string2, JsonNode jsonNode, List<Integer> list, String string3) {
        Iterator<String> iterator = jsonNode.fieldNames();
        while (iterator.hasNext() && !this.terminateCurrentMatch) {
            TreeNode treeNode;
            String string4 = iterator.next();
            if (string4.equals("_v")) continue;
            TreeNode treeNode2 = treeNode = jsonNode.get(string4);
            if (!treeNode.isContainerNode()) {
                treeNode2 = this.getNode(treeNode.asText());
                ((ObjectNode)jsonNode).put(string4, (JsonNode)treeNode2);
            }
            this.find(string2, (JsonNode)treeNode2, list, string3 + string4);
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    public List<Integer> search(String string2, boolean bl2) {
        JsonNode jsonNode = bl2 ? this.urlRootNode : this.nameRootNode;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        string2 = " " + string2.trim();
        if (!string2.equals(" ")) {
            this.terminateCurrentMatch = false;
            this.find(string2, jsonNode, arrayList, "");
        }
        return arrayList;
    }

    public void terminateSearch() {
        this.terminateCurrentMatch = true;
    }
}

