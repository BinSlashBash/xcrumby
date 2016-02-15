package org.junit.experimental.categories;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class Categories
  extends Suite
{
  public Categories(Class<?> paramClass, RunnerBuilder paramRunnerBuilder)
    throws InitializationError
  {
    super(paramClass, paramRunnerBuilder);
    try
    {
      filter(new CategoryFilter(getIncludedCategory(paramClass), getExcludedCategory(paramClass)));
      assertNoCategorizedDescendentsOfUncategorizeableParents(getDescription());
      return;
    }
    catch (NoTestsRemainException paramClass)
    {
      throw new InitializationError(paramClass);
    }
  }
  
  private void assertNoCategorizedDescendentsOfUncategorizeableParents(Description paramDescription)
    throws InitializationError
  {
    if (!canHaveCategorizedChildren(paramDescription)) {
      assertNoDescendantsHaveCategoryAnnotations(paramDescription);
    }
    paramDescription = paramDescription.getChildren().iterator();
    while (paramDescription.hasNext()) {
      assertNoCategorizedDescendentsOfUncategorizeableParents((Description)paramDescription.next());
    }
  }
  
  private void assertNoDescendantsHaveCategoryAnnotations(Description paramDescription)
    throws InitializationError
  {
    paramDescription = paramDescription.getChildren().iterator();
    while (paramDescription.hasNext())
    {
      Description localDescription = (Description)paramDescription.next();
      if (localDescription.getAnnotation(Category.class) != null) {
        throw new InitializationError("Category annotations on Parameterized classes are not supported on individual methods.");
      }
      assertNoDescendantsHaveCategoryAnnotations(localDescription);
    }
  }
  
  private static boolean canHaveCategorizedChildren(Description paramDescription)
  {
    paramDescription = paramDescription.getChildren().iterator();
    while (paramDescription.hasNext()) {
      if (((Description)paramDescription.next()).getTestClass() == null) {
        return false;
      }
    }
    return true;
  }
  
  private Class<?> getExcludedCategory(Class<?> paramClass)
  {
    paramClass = (ExcludeCategory)paramClass.getAnnotation(ExcludeCategory.class);
    if (paramClass == null) {
      return null;
    }
    return paramClass.value();
  }
  
  private Class<?> getIncludedCategory(Class<?> paramClass)
  {
    paramClass = (IncludeCategory)paramClass.getAnnotation(IncludeCategory.class);
    if (paramClass == null) {
      return null;
    }
    return paramClass.value();
  }
  
  public static class CategoryFilter
    extends Filter
  {
    private final Class<?> fExcluded;
    private final Class<?> fIncluded;
    
    public CategoryFilter(Class<?> paramClass1, Class<?> paramClass2)
    {
      this.fIncluded = paramClass1;
      this.fExcluded = paramClass2;
    }
    
    private List<Class<?>> categories(Description paramDescription)
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.addAll(Arrays.asList(directCategories(paramDescription)));
      localArrayList.addAll(Arrays.asList(directCategories(parentDescription(paramDescription))));
      return localArrayList;
    }
    
    private Class<?>[] directCategories(Description paramDescription)
    {
      if (paramDescription == null) {
        return new Class[0];
      }
      paramDescription = (Category)paramDescription.getAnnotation(Category.class);
      if (paramDescription == null) {
        return new Class[0];
      }
      return paramDescription.value();
    }
    
    private boolean hasCorrectCategoryAnnotation(Description paramDescription)
    {
      paramDescription = categories(paramDescription);
      if (paramDescription.isEmpty()) {
        return this.fIncluded == null;
      }
      Object localObject = paramDescription.iterator();
      while (((Iterator)localObject).hasNext())
      {
        Class localClass = (Class)((Iterator)localObject).next();
        if ((this.fExcluded != null) && (this.fExcluded.isAssignableFrom(localClass))) {
          return false;
        }
      }
      paramDescription = paramDescription.iterator();
      for (;;)
      {
        if (paramDescription.hasNext())
        {
          localObject = (Class)paramDescription.next();
          if (this.fIncluded == null) {
            break;
          }
          if (this.fIncluded.isAssignableFrom((Class)localObject)) {
            return true;
          }
        }
      }
      return false;
    }
    
    public static CategoryFilter include(Class<?> paramClass)
    {
      return new CategoryFilter(paramClass, null);
    }
    
    private Description parentDescription(Description paramDescription)
    {
      paramDescription = paramDescription.getTestClass();
      if (paramDescription == null) {
        return null;
      }
      return Description.createSuiteDescription(paramDescription);
    }
    
    public String describe()
    {
      return "category " + this.fIncluded;
    }
    
    public boolean shouldRun(Description paramDescription)
    {
      if (hasCorrectCategoryAnnotation(paramDescription)) {
        return true;
      }
      paramDescription = paramDescription.getChildren().iterator();
      while (paramDescription.hasNext()) {
        if (shouldRun((Description)paramDescription.next())) {
          return true;
        }
      }
      return false;
    }
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface ExcludeCategory
  {
    Class<?> value();
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface IncludeCategory
  {
    Class<?> value();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/categories/Categories.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */