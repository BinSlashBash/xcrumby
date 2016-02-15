package org.junit.experimental.theories;

public abstract class PotentialAssignment
{
  public static PotentialAssignment forValue(final String paramString, Object paramObject)
  {
    new PotentialAssignment()
    {
      public String getDescription()
        throws PotentialAssignment.CouldNotGenerateValueException
      {
        return paramString;
      }
      
      public Object getValue()
        throws PotentialAssignment.CouldNotGenerateValueException
      {
        return this.val$value;
      }
      
      public String toString()
      {
        return String.format("[%s]", new Object[] { this.val$value });
      }
    };
  }
  
  public abstract String getDescription()
    throws PotentialAssignment.CouldNotGenerateValueException;
  
  public abstract Object getValue()
    throws PotentialAssignment.CouldNotGenerateValueException;
  
  public static class CouldNotGenerateValueException
    extends Exception
  {
    private static final long serialVersionUID = 1L;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/theories/PotentialAssignment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */