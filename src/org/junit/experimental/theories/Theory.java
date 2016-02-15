package org.junit.experimental.theories;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Theory
{
  boolean nullsAccepted() default true;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/theories/Theory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */