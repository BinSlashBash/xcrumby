/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

abstract class CombiningEvaluator
extends Evaluator {
    final ArrayList<Evaluator> evaluators = new ArrayList();
    int num = 0;

    CombiningEvaluator() {
    }

    CombiningEvaluator(Collection<Evaluator> collection) {
        this();
        this.evaluators.addAll(collection);
        this.updateNumEvaluators();
    }

    void replaceRightMostEvaluator(Evaluator evaluator) {
        this.evaluators.set(this.num - 1, evaluator);
    }

    Evaluator rightMostEvaluator() {
        if (this.num > 0) {
            return this.evaluators.get(this.num - 1);
        }
        return null;
    }

    void updateNumEvaluators() {
        this.num = this.evaluators.size();
    }

    static final class And
    extends CombiningEvaluator {
        And(Collection<Evaluator> collection) {
            super(collection);
        }

        /* varargs */ And(Evaluator ... arrevaluator) {
            this(Arrays.asList(arrevaluator));
        }

        @Override
        public boolean matches(Element element, Element element2) {
            for (int i2 = 0; i2 < this.num; ++i2) {
                if (((Evaluator)this.evaluators.get(i2)).matches(element, element2)) continue;
                return false;
            }
            return true;
        }

        public String toString() {
            return StringUtil.join(this.evaluators, " ");
        }
    }

    static final class Or
    extends CombiningEvaluator {
        Or() {
        }

        /*
         * Enabled aggressive block sorting
         */
        Or(Collection<Evaluator> collection) {
            if (this.num > 1) {
                this.evaluators.add(new And(collection));
            } else {
                this.evaluators.addAll(collection);
            }
            this.updateNumEvaluators();
        }

        public void add(Evaluator evaluator) {
            this.evaluators.add(evaluator);
            this.updateNumEvaluators();
        }

        @Override
        public boolean matches(Element element, Element element2) {
            for (int i2 = 0; i2 < this.num; ++i2) {
                if (!((Evaluator)this.evaluators.get(i2)).matches(element, element2)) continue;
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format(":or%s", this.evaluators);
        }
    }

}

