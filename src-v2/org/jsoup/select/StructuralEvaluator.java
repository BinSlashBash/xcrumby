/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

abstract class StructuralEvaluator
extends Evaluator {
    Evaluator evaluator;

    StructuralEvaluator() {
    }

    static class Has
    extends StructuralEvaluator {
        public Has(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        @Override
        public boolean matches(Element element, Element element2) {
            for (Element element3 : element2.getAllElements()) {
                if (element3 == element2 || !this.evaluator.matches(element, element3)) continue;
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format(":has(%s)", this.evaluator);
        }
    }

    static class ImmediateParent
    extends StructuralEvaluator {
        public ImmediateParent(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean matches(Element element, Element element2) {
            if (element == element2 || (element2 = element2.parent()) == null || !this.evaluator.matches(element, element2)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return String.format(":ImmediateParent%s", this.evaluator);
        }
    }

    static class ImmediatePreviousSibling
    extends StructuralEvaluator {
        public ImmediatePreviousSibling(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean matches(Element element, Element element2) {
            if (element == element2 || (element2 = element2.previousElementSibling()) == null || !this.evaluator.matches(element, element2)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return String.format(":prev%s", this.evaluator);
        }
    }

    static class Not
    extends StructuralEvaluator {
        public Not(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (!this.evaluator.matches(element, element2)) {
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format(":not%s", this.evaluator);
        }
    }

    static class Parent
    extends StructuralEvaluator {
        public Parent(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean matches(Element element, Element element2) {
            if (element != element2) {
                for (element2 = element2.parent(); element2 != element; element2 = element2.parent()) {
                    if (!this.evaluator.matches(element, element2)) continue;
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return String.format(":parent%s", this.evaluator);
        }
    }

    static class PreviousSibling
    extends StructuralEvaluator {
        public PreviousSibling(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean matches(Element element, Element element2) {
            if (element != element2) {
                for (element2 = element2.previousElementSibling(); element2 != null; element2 = element2.previousElementSibling()) {
                    if (!this.evaluator.matches(element, element2)) continue;
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return String.format(":prev*%s", this.evaluator);
        }
    }

    static class Root
    extends Evaluator {
        Root() {
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (element == element2) {
                return true;
            }
            return false;
        }
    }

}

