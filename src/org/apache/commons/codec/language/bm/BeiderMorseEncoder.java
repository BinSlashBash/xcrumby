package org.apache.commons.codec.language.bm;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class BeiderMorseEncoder
  implements StringEncoder
{
  private PhoneticEngine engine = new PhoneticEngine(NameType.GENERIC, RuleType.APPROX, true);
  
  public Object encode(Object paramObject)
    throws EncoderException
  {
    if (!(paramObject instanceof String)) {
      throw new EncoderException("BeiderMorseEncoder encode parameter is not of type String");
    }
    return encode((String)paramObject);
  }
  
  public String encode(String paramString)
    throws EncoderException
  {
    if (paramString == null) {
      return null;
    }
    return this.engine.encode(paramString);
  }
  
  public NameType getNameType()
  {
    return this.engine.getNameType();
  }
  
  public RuleType getRuleType()
  {
    return this.engine.getRuleType();
  }
  
  public boolean isConcat()
  {
    return this.engine.isConcat();
  }
  
  public void setConcat(boolean paramBoolean)
  {
    this.engine = new PhoneticEngine(this.engine.getNameType(), this.engine.getRuleType(), paramBoolean, this.engine.getMaxPhonemes());
  }
  
  public void setMaxPhonemes(int paramInt)
  {
    this.engine = new PhoneticEngine(this.engine.getNameType(), this.engine.getRuleType(), this.engine.isConcat(), paramInt);
  }
  
  public void setNameType(NameType paramNameType)
  {
    this.engine = new PhoneticEngine(paramNameType, this.engine.getRuleType(), this.engine.isConcat(), this.engine.getMaxPhonemes());
  }
  
  public void setRuleType(RuleType paramRuleType)
  {
    this.engine = new PhoneticEngine(this.engine.getNameType(), paramRuleType, this.engine.isConcat(), this.engine.getMaxPhonemes());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/language/bm/BeiderMorseEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */