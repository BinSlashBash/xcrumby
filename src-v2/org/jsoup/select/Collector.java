/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class Collector {
    private Collector() {
    }

    public static Elements collect(Evaluator evaluator, Element element) {
        Elements elements = new Elements();
        new NodeTraversor(new Accumulator(element, elements, evaluator)).traverse(element);
        return elements;
    }

    private static class Accumulator
    implements NodeVisitor {
        private final Elements elements;
        private final Evaluator eval;
        private final Element root;

        Accumulator(Element element, Elements elements, Evaluator evaluator) {
            this.root = element;
            this.elements = elements;
            this.eval = evaluator;
        }

        @Override
        public void head(Node node, int n2) {
            if (node instanceof Element && this.eval.matches(this.root, (Element)(node = (Element)node))) {
                this.elements.add((Element)node);
            }
        }

        @Override
        public void tail(Node node, int n2) {
        }
    }

}

