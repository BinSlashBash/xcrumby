package com.uservoice.uservoicesdk.activity;

import android.os.Bundle;
import com.uservoice.uservoicesdk.R.string;
import com.uservoice.uservoicesdk.ui.ContactAdapter;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;

public class ContactActivity
  extends InstantAnswersActivity
{
  protected InstantAnswersAdapter createAdapter()
  {
    return new ContactAdapter(this);
  }
  
  protected int getDiscardDialogMessage()
  {
    return R.string.uv_msg_confirm_discard_message;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTitle(R.string.uv_contact_us);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/activity/ContactActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */