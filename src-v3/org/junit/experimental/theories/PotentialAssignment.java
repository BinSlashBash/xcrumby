package org.junit.experimental.theories;

public abstract class PotentialAssignment {

    public static class CouldNotGenerateValueException extends Exception {
        private static final long serialVersionUID = 1;
    }

    /* renamed from: org.junit.experimental.theories.PotentialAssignment.1 */
    static class C12491 extends PotentialAssignment {
        final /* synthetic */ String val$name;
        final /* synthetic */ Object val$value;

        C12491(Object obj, String str) {
            this.val$value = obj;
            this.val$name = str;
        }

        public Object getValue() throws CouldNotGenerateValueException {
            return this.val$value;
        }

        public String toString() {
            return String.format("[%s]", new Object[]{this.val$value});
        }

        public String getDescription() throws CouldNotGenerateValueException {
            return this.val$name;
        }
    }

    public abstract String getDescription() throws CouldNotGenerateValueException;

    public abstract Object getValue() throws CouldNotGenerateValueException;

    public static PotentialAssignment forValue(String name, Object value) {
        return new C12491(value, name);
    }
}
