/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class Parameterized
extends Suite {
    private static final List<Runner> NO_RUNNERS = Collections.emptyList();
    private final ArrayList<Runner> runners = new ArrayList();

    public Parameterized(Class<?> object) throws Throwable {
        super(object, NO_RUNNERS);
        object = (Parameters)this.getParametersMethod().getAnnotation(Parameters.class);
        this.createRunnersForParameters(this.allParameters(), object.name());
    }

    private Iterable<Object[]> allParameters() throws Throwable {
        Object object = this.getParametersMethod().invokeExplosively(null, new Object[0]);
        if (object instanceof Iterable) {
            return (Iterable)object;
        }
        throw this.parametersMethodReturnedWrongType();
    }

    private void createRunnersForParameters(Iterable<Object[]> object, String string2) throws InitializationError, Exception {
        int n2 = 0;
        try {
            object = object.iterator();
            while (object.hasNext()) {
                Object object2 = (Object[])object.next();
                String string3 = this.nameFor(string2, n2, (Object[])object2);
                object2 = new TestClassRunnerForParameters(this.getTestClass().getJavaClass(), (Object[])object2, string3);
                this.runners.add((Runner)object2);
                ++n2;
            }
        }
        catch (ClassCastException var1_2) {
            throw this.parametersMethodReturnedWrongType();
        }
    }

    private boolean fieldsAreAnnotated() {
        if (!this.getAnnotatedFieldsByParameter().isEmpty()) {
            return true;
        }
        return false;
    }

    private List<FrameworkField> getAnnotatedFieldsByParameter() {
        return this.getTestClass().getAnnotatedFields(Parameter.class);
    }

    private FrameworkMethod getParametersMethod() throws Exception {
        for (FrameworkMethod frameworkMethod : this.getTestClass().getAnnotatedMethods(Parameters.class)) {
            if (!frameworkMethod.isStatic() || !frameworkMethod.isPublic()) continue;
            return frameworkMethod;
        }
        throw new Exception("No public static parameters method on class " + this.getTestClass().getName());
    }

    private String nameFor(String string2, int n2, Object[] arrobject) {
        string2 = MessageFormat.format(string2.replaceAll("\\{index\\}", Integer.toString(n2)), arrobject);
        return "[" + string2 + "]";
    }

    private Exception parametersMethodReturnedWrongType() throws Exception {
        return new Exception(MessageFormat.format("{0}.{1}() must return an Iterable of arrays.", this.getTestClass().getName(), this.getParametersMethod().getName()));
    }

    @Override
    protected List<Runner> getChildren() {
        return this.runners;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public static @interface Parameter {
        public int value() default 0;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD})
    public static @interface Parameters {
        public String name() default "{index}";
    }

    private class TestClassRunnerForParameters
    extends BlockJUnit4ClassRunner {
        private final String fName;
        private final Object[] fParameters;

        TestClassRunnerForParameters(Class<?> class_, Object[] arrobject, String string2) throws InitializationError {
            super(class_);
            this.fParameters = arrobject;
            this.fName = string2;
        }

        private Object createTestUsingConstructorInjection() throws Exception {
            return this.getTestClass().getOnlyConstructor().newInstance(this.fParameters);
        }

        private Object createTestUsingFieldInjection() throws Exception {
            Object object = Parameterized.this.getAnnotatedFieldsByParameter();
            if (object.size() != this.fParameters.length) {
                throw new Exception("Wrong number of parameters and @Parameter fields. @Parameter fields counted: " + object.size() + ", available parameters: " + this.fParameters.length + ".");
            }
            Object obj = this.getTestClass().getJavaClass().newInstance();
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                object = ((FrameworkField)iterator.next()).getField();
                int n2 = ((Parameter)object.getAnnotation(Parameter.class)).value();
                try {
                    object.set(obj, this.fParameters[n2]);
                    continue;
                }
                catch (IllegalArgumentException var2_3) {
                    throw new Exception(this.getTestClass().getName() + ": Trying to set " + object.getName() + " with the value " + this.fParameters[n2] + " that is not the right type (" + this.fParameters[n2].getClass().getSimpleName() + " instead of " + object.getType().getSimpleName() + ").", var2_3);
                }
            }
            return obj;
        }

        @Override
        protected Statement classBlock(RunNotifier runNotifier) {
            return this.childrenInvoker(runNotifier);
        }

        @Override
        public Object createTest() throws Exception {
            if (Parameterized.this.fieldsAreAnnotated()) {
                return this.createTestUsingFieldInjection();
            }
            return this.createTestUsingConstructorInjection();
        }

        @Override
        protected String getName() {
            return this.fName;
        }

        @Override
        protected Annotation[] getRunnerAnnotations() {
            return new Annotation[0];
        }

        @Override
        protected String testName(FrameworkMethod frameworkMethod) {
            return frameworkMethod.getName() + this.getName();
        }

        @Override
        protected void validateConstructor(List<Throwable> list) {
            this.validateOnlyOneConstructor(list);
            if (Parameterized.this.fieldsAreAnnotated()) {
                this.validateZeroArgConstructor(list);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        protected void validateFields(List<Throwable> list) {
            int n2;
            super.validateFields(list);
            if (!Parameterized.this.fieldsAreAnnotated()) {
                return;
            }
            List list2 = Parameterized.this.getAnnotatedFieldsByParameter();
            int[] arrn = new int[list2.size()];
            Iterator iterator = list2.iterator();
            while (iterator.hasNext()) {
                n2 = ((Parameter)((FrameworkField)iterator.next()).getField().getAnnotation(Parameter.class)).value();
                if (n2 < 0 || n2 > list2.size() - 1) {
                    list.add(new Exception("Invalid @Parameter value: " + n2 + ". @Parameter fields counted: " + list2.size() + ". Please use an index between 0 and " + (list2.size() - 1) + "."));
                    continue;
                }
                arrn[n2] = arrn[n2] + 1;
            }
            n2 = 0;
            while (n2 < arrn.length) {
                int n3 = arrn[n2];
                if (n3 == 0) {
                    list.add(new Exception("@Parameter(" + n2 + ") is never used."));
                } else if (n3 > 1) {
                    list.add(new Exception("@Parameter(" + n2 + ") is used more than once (" + n3 + ")."));
                }
                ++n2;
            }
        }
    }

}

