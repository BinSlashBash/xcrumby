/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$OnHierarchyChangeListener
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.BaseAdapter
 *  android.widget.EditText
 */
package com.uservoice.uservoicesdk.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.deflection.Deflection;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.ClientConfig;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class InstantAnswersAdapter
extends BaseAdapter
implements ViewGroup.OnHierarchyChangeListener,
AdapterView.OnItemClickListener {
    protected int BUTTON = 1;
    protected int EMAIL_FIELD = 5;
    protected int HEADING = 2;
    protected int INSTANT_ANSWER = 4;
    protected int LOADING = 3;
    protected int NAME_FIELD = 6;
    protected int SPACE = 7;
    protected int TEXT = 0;
    protected FragmentActivity context;
    protected int continueButtonMessage;
    protected String deflectingType;
    protected EditText emailField;
    protected LayoutInflater inflater;
    protected List<BaseModel> instantAnswers;
    protected boolean isPosting;
    protected EditText nameField;
    protected State state = State.INIT;
    protected EditText textField;

    public InstantAnswersAdapter(FragmentActivity fragmentActivity) {
        this.context = fragmentActivity;
        this.inflater = (LayoutInflater)fragmentActivity.getSystemService("layout_inflater");
    }

    protected abstract void doSubmit();

    public int getCount() {
        if (this.isLoading()) {
            return 1;
        }
        return this.getRows().size();
    }

    protected abstract List<Integer> getDetailRows();

    public Object getItem(int n2) {
        if (this.getItemViewType(n2) == this.INSTANT_ANSWER) {
            return this.instantAnswers.get(n2 - this.getRows().indexOf(this.INSTANT_ANSWER));
        }
        return null;
    }

    public long getItemId(int n2) {
        return n2;
    }

    public int getItemViewType(int n2) {
        if (this.isLoading()) {
            return this.LOADING;
        }
        return this.getRows().get(n2);
    }

    protected List<Integer> getRows() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(this.TEXT);
        if (this.state != State.INIT && this.state != State.INIT_LOADING && !this.instantAnswers.isEmpty()) {
            arrayList.add(this.SPACE);
            arrayList.add(this.HEADING);
        }
        if (this.state == State.INSTANT_ANSWERS || this.state == State.DETAILS) {
            if (this.instantAnswers.size() > 0) {
                arrayList.add(this.INSTANT_ANSWER);
            }
            if (this.instantAnswers.size() > 1) {
                arrayList.add(this.INSTANT_ANSWER);
            }
            if (this.instantAnswers.size() > 2) {
                arrayList.add(this.INSTANT_ANSWER);
            }
        }
        if (this.state == State.DETAILS) {
            arrayList.add(this.SPACE);
            arrayList.addAll(this.getDetailRows());
        }
        arrayList.add(this.BUTTON);
        return arrayList;
    }

    protected abstract String getSubmitString();

    /*
     * Exception decompiling
     */
    @SuppressLint(value={"CutPasteId"})
    public View getView(int var1_1, View var2_2, ViewGroup var3_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:422)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    public int getViewTypeCount() {
        return 8;
    }

    public boolean hasText() {
        if (this.textField != null && this.textField.getText().toString().length() != 0) {
            return true;
        }
        return false;
    }

    public boolean isEnabled(int n2) {
        if (this.getItemViewType(n2) == this.INSTANT_ANSWER) {
            return true;
        }
        return false;
    }

    protected boolean isLoading() {
        if (Session.getInstance().getClientConfig() == null) {
            return true;
        }
        return false;
    }

    public void notHelpful() {
        if (this.state == State.INSTANT_ANSWERS) {
            this.state = State.DETAILS;
            this.notifyDataSetChanged();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onButtonTapped() {
        if (this.state == State.INIT) {
            String string2 = this.textField.getText().toString().trim();
            if (string2.length() == 0) return;
            {
                this.state = State.INIT_LOADING;
                this.notifyDataSetChanged();
                Deflection.setSearchText(string2);
                ((InputMethodManager)this.context.getSystemService("input_method")).toggleSoftInput(1, 0);
                Article.loadInstantAnswers(string2, new DefaultCallback<List<BaseModel>>((Context)this.context){

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void onModel(List<BaseModel> list) {
                        Deflection.trackSearchDeflection(list.subList(0, Math.min(list.size(), 3)), InstantAnswersAdapter.this.deflectingType);
                        InstantAnswersAdapter.this.instantAnswers = list;
                        InstantAnswersAdapter.this.state = InstantAnswersAdapter.this.instantAnswers.isEmpty() ? State.DETAILS : State.INSTANT_ANSWERS;
                        InstantAnswersAdapter.this.notifyDataSetChanged();
                    }
                });
                return;
            }
        }
        if (this.state == State.INSTANT_ANSWERS) {
            this.state = State.DETAILS;
            this.notifyDataSetChanged();
            return;
        }
        if (this.state != State.DETAILS) return;
        String string3 = this.nameField.getText().toString();
        String string4 = this.emailField.getText().toString();
        if (string4.length() == 0) {
            string3 = new AlertDialog.Builder((Context)this.context);
            string3.setTitle(R.string.uv_error);
            string3.setMessage(R.string.uv_msg_user_identity_validation);
            string3.create().show();
            return;
        }
        if (this.isPosting) {
            return;
        }
        this.isPosting = true;
        Session.getInstance().persistIdentity(string3, string4);
        this.doSubmit();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onChildViewAdded(View view, View view2) {
        if (this.state == State.DETAILS && this.emailField != null) {
            this.emailField.requestFocus();
            return;
        } else {
            if (this.textField == null) return;
            {
                this.textField.requestFocus();
                return;
            }
        }
    }

    public void onChildViewRemoved(View view, View view2) {
        this.onChildViewAdded(null, null);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
        if (this.getItemViewType(n2) == this.INSTANT_ANSWER) {
            Deflection.trackDeflection("show", this.deflectingType, (BaseModel)this.getItem(n2));
            Utils.showModel(this.context, (BaseModel)this.getItem(n2), this.deflectingType);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void restoreEnteredText(EditText object, EditText editText, String object2) {
        if (object == null) {
            editText.setText((CharSequence)object2);
            editText.setSelection(editText.getText().length());
            return;
        }
        if (!TextUtils.isEmpty((CharSequence)(object = object.getText().toString()))) {
            object2 = object;
        }
        editText.setText((CharSequence)object2);
        editText.setSelection(editText.getText().length());
    }

    protected static enum State {
        INIT,
        INIT_LOADING,
        INSTANT_ANSWERS,
        DETAILS;
        

        private State() {
        }
    }

}

