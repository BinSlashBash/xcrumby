package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.lib.widget.firstparty.fragment_options.OverflowImageButton;
import com.uservoice.uservoicesdk.UserVoice;
import java.util.HashMap;

public class ErrorView
  extends RelativeLayout
{
  TextView detailsView;
  private Button dismiss;
  TextView mainView;
  TextView reasonView;
  private boolean shownError;
  
  public ErrorView(Context paramContext)
  {
    super(paramContext);
  }
  
  public ErrorView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ErrorView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void showError(DisplayError paramDisplayError, String paramString1, final String paramString2)
  {
    this.shownError = true;
    if (!paramDisplayError.showBackground) {
      setBackgroundDrawable(null);
    }
    if (paramDisplayError.showDismiss) {
      this.dismiss.setVisibility(0);
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("displayError", paramDisplayError);
    localHashMap.put("subMessage", paramString1);
    Analytics.INSTANCE.newError(paramDisplayError, paramString1 + " @" + paramString2);
    UserVoice.track("error", localHashMap);
    setVisibility(0);
    this.mainView.setText(paramDisplayError.main);
    this.reasonView.setText(paramString1);
    paramString1 = "";
    if (paramString2 == null) {
      findViewById(2131492964).setVisibility(8);
    }
    findViewById(2131492964).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        OverflowImageButton.openWebBrowser(paramString2, ErrorView.this.getContext());
      }
    });
    if (this.detailsView != null)
    {
      paramString1 = "" + "Suggestions:\n";
      paramString2 = paramDisplayError.details;
      int j = paramString2.length;
      int i = 0;
      paramDisplayError = paramString1;
      for (;;)
      {
        paramString1 = paramDisplayError;
        if (i >= j) {
          break;
        }
        paramString1 = paramString2[i];
        if (paramString1 == null) {
          return;
        }
        paramDisplayError = paramDisplayError + "• " + paramString1 + "\n";
        i += 1;
      }
    }
    this.detailsView.setText(paramString1);
  }
  
  public boolean close()
  {
    if ((this.dismiss.getVisibility() == 0) && (getVisibility() == 0))
    {
      setVisibility(8);
      return true;
    }
    return false;
  }
  
  public boolean isShowing()
  {
    return getVisibility() == 0;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mainView = ((TextView)findViewById(2131492960));
    this.reasonView = ((TextView)findViewById(2131492961));
    this.detailsView = ((TextView)findViewById(2131492962));
    this.dismiss = ((Button)findViewById(2131492965));
    findViewById(2131492963).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newNavigationEvent("uservoice", "contact");
        UserVoice.launchContactUs(ErrorView.this.getContext());
      }
    });
    this.dismiss.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ErrorView.this.setVisibility(8);
      }
    });
  }
  
  public void show(DisplayError paramDisplayError, String paramString1, String paramString2)
  {
    String str;
    if (paramString1 != null)
    {
      str = paramString1;
      if (!paramString1.equals("")) {}
    }
    else
    {
      str = paramDisplayError.reason;
    }
    showError(paramDisplayError, str, paramString2);
  }
  
  public boolean shownError()
  {
    return this.shownError;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/ErrorView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */