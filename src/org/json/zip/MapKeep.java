package org.json.zip;

import java.util.HashMap;
import org.json.Kim;

class MapKeep
  extends Keep
{
  private Object[] list = new Object[this.capacity];
  private HashMap map = new HashMap(this.capacity);
  
  public MapKeep(int paramInt)
  {
    super(paramInt);
  }
  
  private void compact()
  {
    int i = 0;
    int j = 0;
    if (i < this.capacity)
    {
      Object localObject = this.list[i];
      long l = age(this.uses[i]);
      if (l > 0L)
      {
        this.uses[j] = l;
        this.list[j] = localObject;
        this.map.put(localObject, new Integer(j));
        j += 1;
      }
      for (;;)
      {
        i += 1;
        break;
        this.map.remove(localObject);
      }
    }
    if (j < this.capacity) {}
    for (this.length = j;; this.length = 0)
    {
      this.power = 0;
      return;
      this.map.clear();
    }
  }
  
  public int find(Object paramObject)
  {
    paramObject = this.map.get(paramObject);
    if ((paramObject instanceof Integer)) {
      return ((Integer)paramObject).intValue();
    }
    return -1;
  }
  
  public boolean postMortem(PostMortem paramPostMortem)
  {
    MapKeep localMapKeep = (MapKeep)paramPostMortem;
    if (this.length != localMapKeep.length)
    {
      JSONzip.log(this.length + " <> " + localMapKeep.length);
      return false;
    }
    int i = 0;
    while (i < this.length)
    {
      if ((this.list[i] instanceof Kim)) {}
      Object localObject1;
      for (boolean bool = ((Kim)this.list[i]).equals(localMapKeep.list[i]); !bool; bool = paramPostMortem.equals(localObject1))
      {
        JSONzip.log("\n[" + i + "]\n " + this.list[i] + "\n " + localMapKeep.list[i] + "\n " + this.uses[i] + "\n " + localMapKeep.uses[i]);
        return false;
        localObject1 = this.list[i];
        Object localObject2 = localMapKeep.list[i];
        paramPostMortem = (PostMortem)localObject1;
        if ((localObject1 instanceof Number)) {
          paramPostMortem = localObject1.toString();
        }
        localObject1 = localObject2;
        if ((localObject2 instanceof Number)) {
          localObject1 = localObject2.toString();
        }
      }
      i += 1;
    }
    return true;
  }
  
  public void register(Object paramObject)
  {
    if (this.length >= this.capacity) {
      compact();
    }
    this.list[this.length] = paramObject;
    this.map.put(paramObject, new Integer(this.length));
    this.uses[this.length] = 1L;
    this.length += 1;
  }
  
  public Object value(int paramInt)
  {
    return this.list[paramInt];
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/MapKeep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */