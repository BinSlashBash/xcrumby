package org.junit.rules;

import java.io.File;
import java.io.IOException;

public class TemporaryFolder
  extends ExternalResource
{
  private File folder;
  private final File parentFolder;
  
  public TemporaryFolder()
  {
    this(null);
  }
  
  public TemporaryFolder(File paramFile)
  {
    this.parentFolder = paramFile;
  }
  
  private File createTemporaryFolderIn(File paramFile)
    throws IOException
  {
    paramFile = File.createTempFile("junit", "", paramFile);
    paramFile.delete();
    paramFile.mkdir();
    return paramFile;
  }
  
  private boolean isLastElementInArray(int paramInt, String[] paramArrayOfString)
  {
    return paramInt == paramArrayOfString.length - 1;
  }
  
  private void recursiveDelete(File paramFile)
  {
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile != null)
    {
      int j = arrayOfFile.length;
      int i = 0;
      while (i < j)
      {
        recursiveDelete(arrayOfFile[i]);
        i += 1;
      }
    }
    paramFile.delete();
  }
  
  protected void after()
  {
    delete();
  }
  
  protected void before()
    throws Throwable
  {
    create();
  }
  
  public void create()
    throws IOException
  {
    this.folder = createTemporaryFolderIn(this.parentFolder);
  }
  
  public void delete()
  {
    if (this.folder != null) {
      recursiveDelete(this.folder);
    }
  }
  
  public File getRoot()
  {
    if (this.folder == null) {
      throw new IllegalStateException("the temporary folder has not yet been created");
    }
    return this.folder;
  }
  
  public File newFile()
    throws IOException
  {
    return File.createTempFile("junit", null, getRoot());
  }
  
  public File newFile(String paramString)
    throws IOException
  {
    File localFile = new File(getRoot(), paramString);
    if (!localFile.createNewFile()) {
      throw new IOException("a file with the name '" + paramString + "' already exists in the test folder");
    }
    return localFile;
  }
  
  public File newFolder()
    throws IOException
  {
    return createTemporaryFolderIn(getRoot());
  }
  
  public File newFolder(String paramString)
    throws IOException
  {
    return newFolder(new String[] { paramString });
  }
  
  public File newFolder(String... paramVarArgs)
    throws IOException
  {
    File localFile = getRoot();
    int i = 0;
    while (i < paramVarArgs.length)
    {
      String str = paramVarArgs[i];
      localFile = new File(localFile, str);
      if ((!localFile.mkdir()) && (isLastElementInArray(i, paramVarArgs))) {
        throw new IOException("a folder with the name '" + str + "' already exists");
      }
      i += 1;
    }
    return localFile;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/TemporaryFolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */