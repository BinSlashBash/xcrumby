package org.junit.runners.model;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

class NoGenericTypeParametersValidator
{
  private final Method fMethod;
  
  NoGenericTypeParametersValidator(Method paramMethod)
  {
    this.fMethod = paramMethod;
  }
  
  private void validateNoTypeParameterOnGenericArrayType(GenericArrayType paramGenericArrayType, List<Throwable> paramList)
  {
    validateNoTypeParameterOnType(paramGenericArrayType.getGenericComponentType(), paramList);
  }
  
  private void validateNoTypeParameterOnParameterizedType(ParameterizedType paramParameterizedType, List<Throwable> paramList)
  {
    paramParameterizedType = paramParameterizedType.getActualTypeArguments();
    int j = paramParameterizedType.length;
    int i = 0;
    while (i < j)
    {
      validateNoTypeParameterOnType(paramParameterizedType[i], paramList);
      i += 1;
    }
  }
  
  private void validateNoTypeParameterOnType(Type paramType, List<Throwable> paramList)
  {
    if ((paramType instanceof TypeVariable)) {
      paramList.add(new Exception("Method " + this.fMethod.getName() + "() contains unresolved type variable " + paramType));
    }
    do
    {
      return;
      if ((paramType instanceof ParameterizedType))
      {
        validateNoTypeParameterOnParameterizedType((ParameterizedType)paramType, paramList);
        return;
      }
      if ((paramType instanceof WildcardType))
      {
        validateNoTypeParameterOnWildcardType((WildcardType)paramType, paramList);
        return;
      }
    } while (!(paramType instanceof GenericArrayType));
    validateNoTypeParameterOnGenericArrayType((GenericArrayType)paramType, paramList);
  }
  
  private void validateNoTypeParameterOnWildcardType(WildcardType paramWildcardType, List<Throwable> paramList)
  {
    Type[] arrayOfType = paramWildcardType.getUpperBounds();
    int j = arrayOfType.length;
    int i = 0;
    while (i < j)
    {
      validateNoTypeParameterOnType(arrayOfType[i], paramList);
      i += 1;
    }
    paramWildcardType = paramWildcardType.getLowerBounds();
    j = paramWildcardType.length;
    i = 0;
    while (i < j)
    {
      validateNoTypeParameterOnType(paramWildcardType[i], paramList);
      i += 1;
    }
  }
  
  void validate(List<Throwable> paramList)
  {
    Type[] arrayOfType = this.fMethod.getGenericParameterTypes();
    int j = arrayOfType.length;
    int i = 0;
    while (i < j)
    {
      validateNoTypeParameterOnType(arrayOfType[i], paramList);
      i += 1;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/model/NoGenericTypeParametersValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */