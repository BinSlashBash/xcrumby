/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RuleChain
implements TestRule {
    private static final RuleChain EMPTY_CHAIN = new RuleChain(Collections.<TestRule>emptyList());
    private List<TestRule> rulesStartingWithInnerMost;

    private RuleChain(List<TestRule> list) {
        this.rulesStartingWithInnerMost = list;
    }

    public static RuleChain emptyRuleChain() {
        return EMPTY_CHAIN;
    }

    public static RuleChain outerRule(TestRule testRule) {
        return RuleChain.emptyRuleChain().around(testRule);
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        Iterator<TestRule> iterator = this.rulesStartingWithInnerMost.iterator();
        while (iterator.hasNext()) {
            statement = iterator.next().apply(statement, description);
        }
        return statement;
    }

    public RuleChain around(TestRule testRule) {
        ArrayList<TestRule> arrayList = new ArrayList<TestRule>();
        arrayList.add(testRule);
        arrayList.addAll(this.rulesStartingWithInnerMost);
        return new RuleChain(arrayList);
    }
}

