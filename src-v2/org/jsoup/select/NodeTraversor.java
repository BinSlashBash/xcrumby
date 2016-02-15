/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.select;

import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

public class NodeTraversor {
    private NodeVisitor visitor;

    public NodeTraversor(NodeVisitor nodeVisitor) {
        this.visitor = nodeVisitor;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void traverse(Node node) {
        Node node2 = node;
        int n2 = 0;
        while (node2 != null) {
            int n3;
            this.visitor.head(node2, n2);
            Node node3 = node2;
            if (node2.childNodeSize() > 0) {
                node2 = node2.childNode(0);
                continue;
            }
            for (n3 = n2++; node3.nextSibling() == null && n3 > 0; --n3) {
                this.visitor.tail(node3, n3);
                node3 = node3.parentNode();
            }
            this.visitor.tail(node3, n3);
            if (node3 == node) {
                return;
            }
            node2 = node3.nextSibling();
            n2 = n3;
        }
    }
}

