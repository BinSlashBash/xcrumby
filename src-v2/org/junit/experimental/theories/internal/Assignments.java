/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.internal.AllMembersSupplier;
import org.junit.runners.model.TestClass;

public class Assignments {
    private List<PotentialAssignment> fAssigned;
    private final TestClass fClass;
    private final List<ParameterSignature> fUnassigned;

    private Assignments(List<PotentialAssignment> list, List<ParameterSignature> list2, TestClass testClass) {
        this.fUnassigned = list2;
        this.fAssigned = list;
        this.fClass = testClass;
    }

    public static Assignments allUnassigned(Method method, TestClass testClass) throws Exception {
        List<ParameterSignature> list = ParameterSignature.signatures(testClass.getOnlyConstructor());
        list.addAll(ParameterSignature.signatures(method));
        return new Assignments(new ArrayList<PotentialAssignment>(), list, testClass);
    }

    private int getConstructorParameterCount() {
        return ParameterSignature.signatures(this.fClass.getOnlyConstructor()).size();
    }

    public Assignments assignNext(PotentialAssignment potentialAssignment) {
        ArrayList<PotentialAssignment> arrayList = new ArrayList<PotentialAssignment>(this.fAssigned);
        arrayList.add(potentialAssignment);
        return new Assignments(arrayList, this.fUnassigned.subList(1, this.fUnassigned.size()), this.fClass);
    }

    public Object[] getActualValues(int n2, int n3, boolean bl2) throws PotentialAssignment.CouldNotGenerateValueException {
        Object[] arrobject = new Object[n3 - n2];
        for (int i2 = n2; i2 < n3; ++i2) {
            Object object = this.fAssigned.get(i2).getValue();
            if (object == null && !bl2) {
                throw new PotentialAssignment.CouldNotGenerateValueException();
            }
            arrobject[i2 - n2] = object;
        }
        return arrobject;
    }

    public Object[] getAllArguments(boolean bl2) throws PotentialAssignment.CouldNotGenerateValueException {
        return this.getActualValues(0, this.fAssigned.size(), bl2);
    }

    public ParameterSupplier getAnnotatedSupplier(ParameterSignature object) throws InstantiationException, IllegalAccessException {
        if ((object = (ParametersSuppliedBy)object.findDeepAnnotation(ParametersSuppliedBy.class)) == null) {
            return null;
        }
        return object.value().newInstance();
    }

    public Object[] getArgumentStrings(boolean bl2) throws PotentialAssignment.CouldNotGenerateValueException {
        Object[] arrobject = new Object[this.fAssigned.size()];
        for (int i2 = 0; i2 < arrobject.length; ++i2) {
            arrobject[i2] = this.fAssigned.get(i2).getDescription();
        }
        return arrobject;
    }

    public Object[] getConstructorArguments(boolean bl2) throws PotentialAssignment.CouldNotGenerateValueException {
        return this.getActualValues(0, this.getConstructorParameterCount(), bl2);
    }

    public Object[] getMethodArguments(boolean bl2) throws PotentialAssignment.CouldNotGenerateValueException {
        return this.getActualValues(this.getConstructorParameterCount(), this.fAssigned.size(), bl2);
    }

    public ParameterSupplier getSupplier(ParameterSignature object) throws InstantiationException, IllegalAccessException {
        if ((object = this.getAnnotatedSupplier((ParameterSignature)object)) != null) {
            return object;
        }
        return new AllMembersSupplier(this.fClass);
    }

    public boolean isComplete() {
        if (this.fUnassigned.size() == 0) {
            return true;
        }
        return false;
    }

    public ParameterSignature nextUnassigned() {
        return this.fUnassigned.get(0);
    }

    public List<PotentialAssignment> potentialsForNextUnassigned() throws InstantiationException, IllegalAccessException {
        ParameterSignature parameterSignature = this.nextUnassigned();
        return this.getSupplier(parameterSignature).getValueSources(parameterSignature);
    }
}

