/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners.rules;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMember;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public enum RuleFieldValidator {
    CLASS_RULE_VALIDATOR(ClassRule.class, false, true),
    RULE_VALIDATOR(Rule.class, false, false),
    CLASS_RULE_METHOD_VALIDATOR(ClassRule.class, true, true),
    RULE_METHOD_VALIDATOR(Rule.class, true, false);
    
    private final Class<? extends Annotation> fAnnotation;
    private final boolean fMethods;
    private final boolean fStaticMembers;

    private RuleFieldValidator(Class<? extends Annotation> class_, boolean bl2, boolean bl3) {
        this.fAnnotation = class_;
        this.fStaticMembers = bl3;
        this.fMethods = bl2;
    }

    private void addError(List<Throwable> list, FrameworkMember<?> frameworkMember, String string2) {
        list.add(new Exception("The @" + this.fAnnotation.getSimpleName() + " '" + frameworkMember.getName() + "' " + string2));
    }

    private boolean isMethodRule(FrameworkMember<?> frameworkMember) {
        return MethodRule.class.isAssignableFrom(frameworkMember.getType());
    }

    private boolean isTestRule(FrameworkMember<?> frameworkMember) {
        return TestRule.class.isAssignableFrom(frameworkMember.getType());
    }

    private void validateMember(FrameworkMember<?> frameworkMember, List<Throwable> list) {
        this.validateStatic(frameworkMember, list);
        this.validatePublic(frameworkMember, list);
        this.validateTestRuleOrMethodRule(frameworkMember, list);
    }

    private void validatePublic(FrameworkMember<?> frameworkMember, List<Throwable> list) {
        if (!frameworkMember.isPublic()) {
            this.addError(list, frameworkMember, "must be public.");
        }
    }

    private void validateStatic(FrameworkMember<?> frameworkMember, List<Throwable> list) {
        if (this.fStaticMembers && !frameworkMember.isStatic()) {
            this.addError(list, frameworkMember, "must be static.");
        }
        if (!this.fStaticMembers && frameworkMember.isStatic()) {
            this.addError(list, frameworkMember, "must not be static.");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void validateTestRuleOrMethodRule(FrameworkMember<?> frameworkMember, List<Throwable> list) {
        if (!this.isMethodRule(frameworkMember) && !this.isTestRule(frameworkMember)) {
            String string2 = this.fMethods ? "must return an implementation of MethodRule or TestRule." : "must implement MethodRule or TestRule.";
            this.addError(list, frameworkMember, string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void validate(TestClass list, List<Throwable> list2) {
        list = this.fMethods ? list.getAnnotatedMethods(this.fAnnotation) : list.getAnnotatedFields(this.fAnnotation);
        list = list.iterator();
        while (list.hasNext()) {
            this.validateMember((FrameworkMember)list.next(), list2);
        }
        return;
    }
}

