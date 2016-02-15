package org.json.zip;

import org.json.Kim;

class TrieKeep
  extends Keep
{
  private int[] froms = new int[this.capacity];
  private Kim[] kims = new Kim[this.capacity];
  private Node root = new Node();
  private int[] thrus = new int[this.capacity];
  
  public TrieKeep(int paramInt)
  {
    super(paramInt);
  }
  
  public Kim kim(int paramInt)
  {
    Kim localKim2 = this.kims[paramInt];
    int i = this.froms[paramInt];
    int j = this.thrus[paramInt];
    Kim localKim1;
    if (i == 0)
    {
      localKim1 = localKim2;
      if (j == localKim2.length) {}
    }
    else
    {
      localKim1 = new Kim(localKim2, i, j);
      this.froms[paramInt] = 0;
      this.thrus[paramInt] = localKim1.length;
      this.kims[paramInt] = localKim1;
    }
    return localKim1;
  }
  
  public int length(int paramInt)
  {
    return this.thrus[paramInt] - this.froms[paramInt];
  }
  
  public int match(Kim paramKim, int paramInt1, int paramInt2)
  {
    Node localNode = this.root;
    int j = -1;
    int i = paramInt1;
    for (;;)
    {
      if (i < paramInt2)
      {
        localNode = localNode.get(paramKim.get(i));
        if (localNode != null) {}
      }
      else
      {
        return j;
      }
      if (localNode.integer != -1) {
        j = localNode.integer;
      }
      paramInt1 += 1;
      i += 1;
    }
  }
  
  public boolean postMortem(PostMortem paramPostMortem)
  {
    int j = 1;
    paramPostMortem = (TrieKeep)paramPostMortem;
    if (this.length != paramPostMortem.length) {
      JSONzip.log("\nLength " + this.length + " <> " + paramPostMortem.length);
    }
    do
    {
      return false;
      if (this.capacity != paramPostMortem.capacity)
      {
        JSONzip.log("\nCapacity " + this.capacity + " <> " + paramPostMortem.capacity);
        return false;
      }
      int i = 0;
      while (i < this.length)
      {
        Kim localKim1 = kim(i);
        Kim localKim2 = paramPostMortem.kim(i);
        if (!localKim1.equals(localKim2))
        {
          JSONzip.log("\n[" + i + "] " + localKim1 + " <> " + localKim2);
          j = 0;
        }
        i += 1;
      }
    } while ((j == 0) || (!this.root.postMortem(paramPostMortem.root)));
    return true;
  }
  
  public void registerMany(Kim paramKim)
  {
    int i1 = paramKim.length;
    int j = this.capacity - this.length;
    int i = j;
    if (j > 40) {
      i = 40;
    }
    j = 0;
    int k = i;
    i = j;
    for (;;)
    {
      int m;
      Node localNode;
      int n;
      if (i < i1 - 2)
      {
        m = i1 - i;
        j = m;
        if (m > 10) {
          j = 10;
        }
        localNode = this.root;
        n = i;
      }
      while (n < j + i)
      {
        localNode = localNode.vet(paramKim.get(n));
        m = k;
        if (localNode.integer == -1)
        {
          m = k;
          if (n - i >= 2)
          {
            Node.access$002(localNode, this.length);
            this.uses[this.length] = 1L;
            this.kims[this.length] = paramKim;
            this.froms[this.length] = i;
            this.thrus[this.length] = (n + 1);
            this.length += 1;
            k -= 1;
            m = k;
            if (k <= 0) {
              return;
            }
          }
        }
        n += 1;
        k = m;
      }
      i += 1;
    }
  }
  
  public int registerOne(Kim paramKim, int paramInt1, int paramInt2)
  {
    int j = -1;
    int i = j;
    if (this.length < this.capacity)
    {
      Node localNode = this.root;
      i = paramInt1;
      while (i < paramInt2)
      {
        localNode = localNode.vet(paramKim.get(i));
        i += 1;
      }
      i = j;
      if (localNode.integer == -1)
      {
        i = this.length;
        Node.access$002(localNode, i);
        this.uses[i] = 1L;
        this.kims[i] = paramKim;
        this.froms[i] = paramInt1;
        this.thrus[i] = paramInt2;
        this.length += 1;
      }
    }
    return i;
  }
  
  public void registerOne(Kim paramKim)
  {
    int i = registerOne(paramKim, 0, paramKim.length);
    if (i != -1) {
      this.kims[i] = paramKim;
    }
  }
  
  public void reserve()
  {
    if (this.capacity - this.length < 40)
    {
      int j = 0;
      int i = 0;
      this.root = new Node();
      while (j < this.capacity)
      {
        int k = i;
        if (this.uses[j] > 1L)
        {
          Kim localKim = this.kims[j];
          int m = this.thrus[j];
          Node localNode = this.root;
          k = this.froms[j];
          while (k < m)
          {
            localNode = localNode.vet(localKim.get(k));
            k += 1;
          }
          Node.access$002(localNode, i);
          this.uses[i] = age(this.uses[j]);
          this.froms[i] = this.froms[j];
          this.thrus[i] = m;
          this.kims[i] = localKim;
          k = i + 1;
        }
        j += 1;
        i = k;
      }
      j = i;
      if (this.capacity - i < 40)
      {
        this.power = 0;
        this.root = new Node();
        j = 0;
      }
      this.length = j;
      while (j < this.capacity)
      {
        this.uses[j] = 0L;
        this.kims[j] = null;
        this.froms[j] = 0;
        this.thrus[j] = 0;
        j += 1;
      }
    }
  }
  
  public Object value(int paramInt)
  {
    return kim(paramInt);
  }
  
  class Node
    implements PostMortem
  {
    private int integer = -1;
    private Node[] next = null;
    
    public Node() {}
    
    public Node get(byte paramByte)
    {
      return get(paramByte & 0xFF);
    }
    
    public Node get(int paramInt)
    {
      if (this.next == null) {
        return null;
      }
      return this.next[paramInt];
    }
    
    public boolean postMortem(PostMortem paramPostMortem)
    {
      paramPostMortem = (Node)paramPostMortem;
      if (paramPostMortem == null) {
        JSONzip.log("\nMisalign");
      }
      int i;
      Node localNode;
      do
      {
        return false;
        if (this.integer != paramPostMortem.integer)
        {
          JSONzip.log("\nInteger " + this.integer + " <> " + paramPostMortem.integer);
          return false;
        }
        if (this.next == null)
        {
          if (paramPostMortem.next == null) {
            return true;
          }
          JSONzip.log("\nNext is null " + this.integer);
          return false;
        }
        i = 0;
        if (i >= 256) {
          break label182;
        }
        localNode = this.next[i];
        if (localNode == null) {
          break;
        }
      } while (!localNode.postMortem(paramPostMortem.next[i]));
      while (paramPostMortem.next[i] == null)
      {
        i += 1;
        break;
      }
      JSONzip.log("\nMisalign " + i);
      return false;
      label182:
      return true;
    }
    
    public void set(byte paramByte, Node paramNode)
    {
      set(paramByte & 0xFF, paramNode);
    }
    
    public void set(int paramInt, Node paramNode)
    {
      if (this.next == null) {
        this.next = new Node['Ā'];
      }
      this.next[paramInt] = paramNode;
    }
    
    public Node vet(byte paramByte)
    {
      return vet(paramByte & 0xFF);
    }
    
    public Node vet(int paramInt)
    {
      Node localNode2 = get(paramInt);
      Node localNode1 = localNode2;
      if (localNode2 == null)
      {
        localNode1 = new Node(TrieKeep.this);
        set(paramInt, localNode1);
      }
      return localNode1;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/TrieKeep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */