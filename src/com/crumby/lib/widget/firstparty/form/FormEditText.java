package com.crumby.lib.widget.firstparty.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;
import com.crumby.R.styleable;
import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public class FormEditText
  extends EditText
  implements FormField
{
  private String argumentName;
  private String niceName;
  private Watcher watcher;
  
  public FormEditText(Context paramContext)
  {
    super(paramContext);
  }
  
  public FormEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 16842862);
  }
  
  public FormEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.FormField, paramInt, 0);
    if ((getHint() == null) || (getHint().equals(""))) {
      setHint("Enter Keywords");
    }
    this.argumentName = paramContext.getString(0);
    this.niceName = paramContext.getString(1);
    setFieldValue(paramContext.getString(2));
    paramContext.recycle();
  }
  
  public String getArgumentName()
  {
    return this.argumentName;
  }
  
  public String getFieldValue()
  {
    return getText().toString();
  }
  
  public String getNiceName()
  {
    return this.niceName;
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    if (this.watcher != null) {
      this.watcher.onValueChanged();
    }
  }
  
  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    super.onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
    if (this.watcher != null) {
      this.watcher.onValueChanged();
    }
  }
  
  public void setFieldValue(String paramString)
  {
    setText(paramString);
  }
  
  public void setWatcher(Watcher paramWatcher)
  {
    this.watcher = paramWatcher;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/form/FormEditText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */