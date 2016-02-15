/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.examples;

import java.io.IOException;
import java.io.PrintStream;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class HtmlToPlainText {
    /*
     * Enabled aggressive block sorting
     */
    public static /* varargs */ void main(String ... object) throws IOException {
        boolean bl2 = true;
        if (object.length != 1) {
            bl2 = false;
        }
        Validate.isTrue(bl2, "usage: supply url to fetch");
        object = Jsoup.connect((String)object[0]).get();
        object = new HtmlToPlainText().getPlainText((Element)object);
        System.out.println((String)object);
    }

    public String getPlainText(Element element) {
        FormattingVisitor formattingVisitor = new FormattingVisitor();
        new NodeTraversor(formattingVisitor).traverse(element);
        return formattingVisitor.toString();
    }

    private class FormattingVisitor
    implements NodeVisitor {
        private static final int maxWidth = 80;
        private StringBuilder accum;
        private int width;

        private FormattingVisitor() {
            this.width = 0;
            this.accum = new StringBuilder();
        }

        /*
         * Enabled aggressive block sorting
         */
        private void append(String string2) {
            if (string2.startsWith("\n")) {
                this.width = 0;
            }
            if (string2.equals(" ") && (this.accum.length() == 0 || StringUtil.in(this.accum.substring(this.accum.length() - 1), " ", "\n"))) {
                return;
            }
            if (string2.length() + this.width <= 80) {
                this.accum.append(string2);
                this.width += string2.length();
                return;
            }
            String[] arrstring = string2.split("\\s+");
            int n2 = 0;
            while (n2 < arrstring.length) {
                String string3 = arrstring[n2];
                boolean bl2 = n2 == arrstring.length - 1;
                string2 = string3;
                if (!bl2) {
                    string2 = string3 + " ";
                }
                if (string2.length() + this.width > 80) {
                    this.accum.append("\n").append(string2);
                    this.width = string2.length();
                } else {
                    this.accum.append(string2);
                    this.width += string2.length();
                }
                ++n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void head(Node node, int n2) {
            String string2 = node.nodeName();
            if (node instanceof TextNode) {
                this.append(((TextNode)node).text());
                return;
            } else {
                if (!string2.equals("li")) return;
                {
                    this.append("\n * ");
                    return;
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void tail(Node node, int n2) {
            String string2 = node.nodeName();
            if (string2.equals("br")) {
                this.append("\n");
                return;
            } else {
                if (StringUtil.in(string2, "p", "h1", "h2", "h3", "h4", "h5")) {
                    this.append("\n\n");
                    return;
                }
                if (!string2.equals("a")) return;
                {
                    this.append(String.format(" <%s>", node.absUrl("href")));
                    return;
                }
            }
        }

        public String toString() {
            return this.accum.toString();
        }
    }

}

