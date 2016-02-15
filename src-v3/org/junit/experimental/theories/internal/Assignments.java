package org.junit.experimental.theories.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.PotentialAssignment.CouldNotGenerateValueException;
import org.junit.runners.model.TestClass;

public class Assignments {
    private List<PotentialAssignment> fAssigned;
    private final TestClass fClass;
    private final List<ParameterSignature> fUnassigned;

    private Assignments(List<PotentialAssignment> assigned, List<ParameterSignature> unassigned, TestClass testClass) {
        this.fUnassigned = unassigned;
        this.fAssigned = assigned;
        this.fClass = testClass;
    }

    public static Assignments allUnassigned(Method testMethod, TestClass testClass) throws Exception {
        List<ParameterSignature> signatures = ParameterSignature.signatures(testClass.getOnlyConstructor());
        signatures.addAll(ParameterSignature.signatures(testMethod));
        return new Assignments(new ArrayList(), signatures, testClass);
    }

    public boolean isComplete() {
        return this.fUnassigned.size() == 0;
    }

    public ParameterSignature nextUnassigned() {
        return (ParameterSignature) this.fUnassigned.get(0);
    }

    public Assignments assignNext(PotentialAssignment source) {
        List<PotentialAssignment> assigned = new ArrayList(this.fAssigned);
        assigned.add(source);
        return new Assignments(assigned, this.fUnassigned.subList(1, this.fUnassigned.size()), this.fClass);
    }

    public Object[] getActualValues(int start, int stop, boolean nullsOk) throws CouldNotGenerateValueException {
        Object[] values = new Object[(stop - start)];
        int i = start;
        while (i < stop) {
            Object value = ((PotentialAssignment) this.fAssigned.get(i)).getValue();
            if (value != null || nullsOk) {
                values[i - start] = value;
                i++;
            } else {
                throw new CouldNotGenerateValueException();
            }
        }
        return values;
    }

    public List<PotentialAssignment> potentialsForNextUnassigned() throws InstantiationException, IllegalAccessException {
        ParameterSignature unassigned = nextUnassigned();
        return getSupplier(unassigned).getValueSources(unassigned);
    }

    public ParameterSupplier getSupplier(ParameterSignature unassigned) throws InstantiationException, IllegalAccessException {
        ParameterSupplier supplier = getAnnotatedSupplier(unassigned);
        return supplier != null ? supplier : new AllMembersSupplier(this.fClass);
    }

    public ParameterSupplier getAnnotatedSupplier(ParameterSignature unassigned) throws InstantiationException, IllegalAccessException {
        ParametersSuppliedBy annotation = (ParametersSuppliedBy) unassigned.findDeepAnnotation(ParametersSuppliedBy.class);
        if (annotation == null) {
            return null;
        }
        return (ParameterSupplier) annotation.value().newInstance();
    }

    public Object[] getConstructorArguments(boolean nullsOk) throws CouldNotGenerateValueException {
        return getActualValues(0, getConstructorParameterCount(), nullsOk);
    }

    public Object[] getMethodArguments(boolean nullsOk) throws CouldNotGenerateValueException {
        return getActualValues(getConstructorParameterCount(), this.fAssigned.size(), nullsOk);
    }

    public Object[] getAllArguments(boolean nullsOk) throws CouldNotGenerateValueException {
        return getActualValues(0, this.fAssigned.size(), nullsOk);
    }

    private int getConstructorParameterCount() {
        return ParameterSignature.signatures(this.fClass.getOnlyConstructor()).size();
    }

    public Object[] getArgumentStrings(boolean nullsOk) throws CouldNotGenerateValueException {
        Object[] values = new Object[this.fAssigned.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = ((PotentialAssignment) this.fAssigned.get(i)).getDescription();
        }
        return values;
    }
}
