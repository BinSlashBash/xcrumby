/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class AllMembersSupplier
extends ParameterSupplier {
    private final TestClass fClass;

    public AllMembersSupplier(TestClass testClass) {
        this.fClass = testClass;
    }

    private void addArrayValues(String string2, List<PotentialAssignment> list, Object object) {
        for (int i2 = 0; i2 < Array.getLength(object); ++i2) {
            list.add(PotentialAssignment.forValue(string2 + "[" + i2 + "]", Array.get(object, i2)));
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void addFields(ParameterSignature var1_1, List<PotentialAssignment> var2_2) {
        var5_3 = this.fClass.getJavaClass().getFields();
        var4_4 = var5_3.length;
        var3_5 = 0;
        while (var3_5 < var4_4) {
            block7 : {
                var6_6 = var5_3[var3_5];
                if (Modifier.isStatic(var6_6.getModifiers())) {
                    var7_8 = var6_6.getType();
                    if (var1_1.canAcceptArrayType(var7_8) && var6_6.getAnnotation(DataPoints.class) != null) {
                        this.addArrayValues(var6_6.getName(), var2_2, this.getStaticFieldValue(var6_6));
                        ** GOTO lbl16
                    } else if (var1_1.canAcceptType(var7_8) && var6_6.getAnnotation(DataPoint.class) != null) {
                        var2_2.add(PotentialAssignment.forValue(var6_6.getName(), this.getStaticFieldValue(var6_6)));
                    }
                    break block7;
                    catch (Throwable var6_7) {}
                }
            }
            ++var3_5;
        }
    }

    private void addMultiPointArrayValues(ParameterSignature parameterSignature, String string2, List<PotentialAssignment> list, Object object) throws Throwable {
        int n2 = 0;
        while (n2 < Array.getLength(object) && this.isCorrectlyTyped(parameterSignature, Array.get(object, n2).getClass())) {
            list.add(PotentialAssignment.forValue(string2 + "[" + n2 + "]", Array.get(object, n2)));
            ++n2;
        }
        return;
    }

    private void addMultiPointMethods(ParameterSignature parameterSignature, List<PotentialAssignment> list) {
        for (FrameworkMethod frameworkMethod : this.fClass.getAnnotatedMethods(DataPoints.class)) {
            try {
                this.addMultiPointArrayValues(parameterSignature, frameworkMethod.getName(), list, frameworkMethod.invokeExplosively(null, new Object[0]));
            }
            catch (Throwable var4_5) {}
        }
    }

    private void addSinglePointMethods(ParameterSignature parameterSignature, List<PotentialAssignment> list) {
        for (FrameworkMethod frameworkMethod : this.fClass.getAnnotatedMethods(DataPoint.class)) {
            if (!this.isCorrectlyTyped(parameterSignature, frameworkMethod.getType())) continue;
            list.add(new MethodParameterValue(frameworkMethod));
        }
    }

    private Object getStaticFieldValue(Field object) {
        try {
            object = object.get(null);
            return object;
        }
        catch (IllegalArgumentException var1_2) {
            throw new RuntimeException("unexpected: field from getClass doesn't exist on object");
        }
        catch (IllegalAccessException var1_3) {
            throw new RuntimeException("unexpected: getFields returned an inaccessible field");
        }
    }

    private boolean isCorrectlyTyped(ParameterSignature parameterSignature, Class<?> class_) {
        return parameterSignature.canAcceptType(class_);
    }

    @Override
    public List<PotentialAssignment> getValueSources(ParameterSignature parameterSignature) {
        ArrayList<PotentialAssignment> arrayList = new ArrayList<PotentialAssignment>();
        this.addFields(parameterSignature, arrayList);
        this.addSinglePointMethods(parameterSignature, arrayList);
        this.addMultiPointMethods(parameterSignature, arrayList);
        return arrayList;
    }

    static class MethodParameterValue
    extends PotentialAssignment {
        private final FrameworkMethod fMethod;

        private MethodParameterValue(FrameworkMethod frameworkMethod) {
            this.fMethod = frameworkMethod;
        }

        @Override
        public String getDescription() throws PotentialAssignment.CouldNotGenerateValueException {
            return this.fMethod.getName();
        }

        @Override
        public Object getValue() throws PotentialAssignment.CouldNotGenerateValueException {
            try {
                Object object = this.fMethod.invokeExplosively(null, new Object[0]);
                return object;
            }
            catch (IllegalArgumentException var1_2) {
                throw new RuntimeException("unexpected: argument length is checked");
            }
            catch (IllegalAccessException var1_3) {
                throw new RuntimeException("unexpected: getMethods returned an inaccessible method");
            }
            catch (Throwable var1_4) {
                throw new PotentialAssignment.CouldNotGenerateValueException();
            }
        }
    }

}

