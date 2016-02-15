package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.TimeZone;

public class DateDeserializers
{
  private static final HashSet<String> _classNames = new HashSet();
  
  static
  {
    Class[] arrayOfClass = new Class[5];
    arrayOfClass[0] = Calendar.class;
    arrayOfClass[1] = GregorianCalendar.class;
    arrayOfClass[2] = java.sql.Date.class;
    arrayOfClass[3] = java.util.Date.class;
    arrayOfClass[4] = Timestamp.class;
    int j = arrayOfClass.length;
    int i = 0;
    while (i < j)
    {
      Class localClass = arrayOfClass[i];
      _classNames.add(localClass.getName());
      i += 1;
    }
  }
  
  public static JsonDeserializer<?> find(Class<?> paramClass, String paramString)
  {
    if (_classNames.contains(paramString))
    {
      if (paramClass == Calendar.class) {
        return new CalendarDeserializer();
      }
      if (paramClass == java.util.Date.class) {
        return DateDeserializer.instance;
      }
      if (paramClass == java.sql.Date.class) {
        return new SqlDateDeserializer();
      }
      if (paramClass == Timestamp.class) {
        return new TimestampDeserializer();
      }
      if (paramClass == GregorianCalendar.class) {
        return new CalendarDeserializer(GregorianCalendar.class);
      }
    }
    return null;
  }
  
  @JacksonStdImpl
  public static class CalendarDeserializer
    extends DateDeserializers.DateBasedDeserializer<Calendar>
  {
    protected final Class<? extends Calendar> _calendarClass;
    
    public CalendarDeserializer()
    {
      super();
      this._calendarClass = null;
    }
    
    public CalendarDeserializer(CalendarDeserializer paramCalendarDeserializer, DateFormat paramDateFormat, String paramString)
    {
      super(paramDateFormat, paramString);
      this._calendarClass = paramCalendarDeserializer._calendarClass;
    }
    
    public CalendarDeserializer(Class<? extends Calendar> paramClass)
    {
      super();
      this._calendarClass = paramClass;
    }
    
    public Calendar deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      paramJsonParser = _parseDate(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser == null) {
        paramJsonParser = null;
      }
      for (;;)
      {
        return paramJsonParser;
        if (this._calendarClass == null) {
          return paramDeserializationContext.constructCalendar(paramJsonParser);
        }
        try
        {
          Calendar localCalendar = (Calendar)this._calendarClass.newInstance();
          localCalendar.setTimeInMillis(paramJsonParser.getTime());
          TimeZone localTimeZone = paramDeserializationContext.getTimeZone();
          paramJsonParser = localCalendar;
          if (localTimeZone == null) {
            continue;
          }
          localCalendar.setTimeZone(localTimeZone);
          return localCalendar;
        }
        catch (Exception paramJsonParser)
        {
          throw paramDeserializationContext.instantiationException(this._calendarClass, paramJsonParser);
        }
      }
    }
    
    protected CalendarDeserializer withDateFormat(DateFormat paramDateFormat, String paramString)
    {
      return new CalendarDeserializer(this, paramDateFormat, paramString);
    }
  }
  
  protected static abstract class DateBasedDeserializer<T>
    extends StdScalarDeserializer<T>
    implements ContextualDeserializer
  {
    protected final DateFormat _customFormat;
    protected final String _formatString;
    
    protected DateBasedDeserializer(DateBasedDeserializer<T> paramDateBasedDeserializer, DateFormat paramDateFormat, String paramString)
    {
      super();
      this._customFormat = paramDateFormat;
      this._formatString = paramString;
    }
    
    protected DateBasedDeserializer(Class<?> paramClass)
    {
      super();
      this._customFormat = null;
      this._formatString = null;
    }
    
    protected java.util.Date _parseDate(JsonParser arg1, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      if (this._customFormat != null)
      {
        Object localObject = ???.getCurrentToken();
        if (localObject == JsonToken.VALUE_STRING)
        {
          paramDeserializationContext = ???.getText().trim();
          if (paramDeserializationContext.length() == 0) {
            return (java.util.Date)getEmptyValue();
          }
          try
          {
            synchronized (this._customFormat)
            {
              localObject = this._customFormat.parse(paramDeserializationContext);
              return (java.util.Date)localObject;
            }
            if (localParseException != JsonToken.START_ARRAY) {
              break label171;
            }
          }
          catch (ParseException localParseException)
          {
            throw new IllegalArgumentException("Failed to parse Date value '" + paramDeserializationContext + "' (format: \"" + this._formatString + "\"): " + localParseException.getMessage());
          }
        }
        else if (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS))
        {
          ???.nextToken();
          java.util.Date localDate = _parseDate(???, paramDeserializationContext);
          if (???.nextToken() != JsonToken.END_ARRAY) {
            throw paramDeserializationContext.wrongTokenException(???, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'java.util.Date' value but there was more than a single value in the array");
          }
          return localDate;
        }
      }
      label171:
      return super._parseDate(???, paramDeserializationContext);
    }
    
    public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
      throws JsonMappingException
    {
      Object localObject1 = this;
      Object localObject2;
      TimeZone localTimeZone;
      if (paramBeanProperty != null)
      {
        localObject2 = paramDeserializationContext.getAnnotationIntrospector().findFormat(paramBeanProperty.getMember());
        localObject1 = this;
        if (localObject2 != null)
        {
          localTimeZone = ((JsonFormat.Value)localObject2).getTimeZone();
          if (!((JsonFormat.Value)localObject2).hasPattern()) {
            break label111;
          }
          localObject1 = ((JsonFormat.Value)localObject2).getPattern();
          if (!((JsonFormat.Value)localObject2).hasLocale()) {
            break label103;
          }
          paramBeanProperty = ((JsonFormat.Value)localObject2).getLocale();
          localObject2 = new SimpleDateFormat((String)localObject1, paramBeanProperty);
          paramBeanProperty = localTimeZone;
          if (localTimeZone == null) {
            paramBeanProperty = paramDeserializationContext.getTimeZone();
          }
          ((SimpleDateFormat)localObject2).setTimeZone(paramBeanProperty);
          localObject1 = withDateFormat((DateFormat)localObject2, (String)localObject1);
        }
      }
      label103:
      label111:
      do
      {
        return (JsonDeserializer<?>)localObject1;
        paramBeanProperty = paramDeserializationContext.getLocale();
        break;
        localObject1 = this;
      } while (localTimeZone == null);
      paramBeanProperty = paramDeserializationContext.getConfig().getDateFormat();
      if (paramBeanProperty.getClass() == StdDateFormat.class) {
        if (((JsonFormat.Value)localObject2).hasLocale())
        {
          paramDeserializationContext = ((JsonFormat.Value)localObject2).getLocale();
          paramDeserializationContext = ((StdDateFormat)paramBeanProperty).withTimeZone(localTimeZone).withLocale(paramDeserializationContext);
        }
      }
      for (;;)
      {
        return withDateFormat(paramDeserializationContext, this._formatString);
        paramDeserializationContext = paramDeserializationContext.getLocale();
        break;
        paramDeserializationContext = (DateFormat)paramBeanProperty.clone();
        paramDeserializationContext.setTimeZone(localTimeZone);
      }
    }
    
    protected abstract DateBasedDeserializer<T> withDateFormat(DateFormat paramDateFormat, String paramString);
  }
  
  public static class DateDeserializer
    extends DateDeserializers.DateBasedDeserializer<java.util.Date>
  {
    public static final DateDeserializer instance = new DateDeserializer();
    
    public DateDeserializer()
    {
      super();
    }
    
    public DateDeserializer(DateDeserializer paramDateDeserializer, DateFormat paramDateFormat, String paramString)
    {
      super(paramDateFormat, paramString);
    }
    
    public java.util.Date deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      return _parseDate(paramJsonParser, paramDeserializationContext);
    }
    
    protected DateDeserializer withDateFormat(DateFormat paramDateFormat, String paramString)
    {
      return new DateDeserializer(this, paramDateFormat, paramString);
    }
  }
  
  public static class SqlDateDeserializer
    extends DateDeserializers.DateBasedDeserializer<java.sql.Date>
  {
    public SqlDateDeserializer()
    {
      super();
    }
    
    public SqlDateDeserializer(SqlDateDeserializer paramSqlDateDeserializer, DateFormat paramDateFormat, String paramString)
    {
      super(paramDateFormat, paramString);
    }
    
    public java.sql.Date deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      paramJsonParser = _parseDate(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser == null) {
        return null;
      }
      return new java.sql.Date(paramJsonParser.getTime());
    }
    
    protected SqlDateDeserializer withDateFormat(DateFormat paramDateFormat, String paramString)
    {
      return new SqlDateDeserializer(this, paramDateFormat, paramString);
    }
  }
  
  public static class TimestampDeserializer
    extends DateDeserializers.DateBasedDeserializer<Timestamp>
  {
    public TimestampDeserializer()
    {
      super();
    }
    
    public TimestampDeserializer(TimestampDeserializer paramTimestampDeserializer, DateFormat paramDateFormat, String paramString)
    {
      super(paramDateFormat, paramString);
    }
    
    public Timestamp deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      return new Timestamp(_parseDate(paramJsonParser, paramDeserializationContext).getTime());
    }
    
    protected TimestampDeserializer withDateFormat(DateFormat paramDateFormat, String paramString)
    {
      return new TimestampDeserializer(this, paramDateFormat, paramString);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/DateDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */