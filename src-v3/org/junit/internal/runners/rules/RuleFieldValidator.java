package org.junit.internal.runners.rules;

import java.lang.annotation.Annotation;
import java.util.List;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runners.model.FrameworkMember;
import org.junit.runners.model.TestClass;

public enum RuleFieldValidator {
    CLASS_RULE_VALIDATOR(ClassRule.class, false, true),
    RULE_VALIDATOR(Rule.class, false, false),
    CLASS_RULE_METHOD_VALIDATOR(ClassRule.class, true, true),
    RULE_METHOD_VALIDATOR(Rule.class, true, false);
    
    private final Class<? extends Annotation> fAnnotation;
    private final boolean fMethods;
    private final boolean fStaticMembers;

    private RuleFieldValidator(Class<? extends Annotation> annotation, boolean methods, boolean fStaticMembers) {
        this.fAnnotation = annotation;
        this.fStaticMembers = fStaticMembers;
        this.fMethods = methods;
    }

    public void validate(TestClass target, List<Throwable> errors) {
        for (FrameworkMember<?> each : this.fMethods ? target.getAnnotatedMethods(this.fAnnotation) : target.getAnnotatedFields(this.fAnnotation)) {
            validateMember(each, errors);
        }
    }

    private void validateMember(FrameworkMember<?> member, List<Throwable> errors) {
        validateStatic(member, errors);
        validatePublic(member, errors);
        validateTestRuleOrMethodRule(member, errors);
    }

    private void validateStatic(FrameworkMember<?> member, List<Throwable> errors) {
        if (this.fStaticMembers && !member.isStatic()) {
            addError(errors, member, "must be static.");
        }
        if (!this.fStaticMembers && member.isStatic()) {
            addError(errors, member, "must not be static.");
        }
    }

    private void validatePublic(FrameworkMember<?> member, List<Throwable> errors) {
        if (!member.isPublic()) {
            addError(errors, member, "must be public.");
        }
    }

    private void validateTestRuleOrMethodRule(FrameworkMember<?> member, List<Throwable> errors) {
        if (!isMethodRule(member) && !isTestRule(member)) {
            addError(errors, member, this.fMethods ? "must return an implementation of MethodRule or TestRule." : "must implement MethodRule or TestRule.");
        }
    }

    private boolean isTestRule(FrameworkMember<?> member) {
        return TestRule.class.isAssignableFrom(member.getType());
    }

    private boolean isMethodRule(FrameworkMember<?> member) {
        return MethodRule.class.isAssignableFrom(member.getType());
    }

    private void addError(List<Throwable> errors, FrameworkMember<?> member, String suffix) {
        errors.add(new Exception("The @" + this.fAnnotation.getSimpleName() + " '" + member.getName() + "' " + suffix));
    }
}
