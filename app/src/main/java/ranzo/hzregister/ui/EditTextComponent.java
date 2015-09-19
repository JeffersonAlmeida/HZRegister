package ranzo.hzregister.ui;

import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mobsandgeeks.saripaar.QuickRule;

import ranzo.hzregister.json.Field;

public class EditTextComponent {

    private EditText editTextField;
    private CustomizedUIComponent mCustomizedUIComponent;

    public EditTextComponent
            (Context context, Field field, int inputType, QuickRule... rules){

        this.mCustomizedUIComponent = new CustomizedUIComponent(context, field);

        this.editTextField = buildField(context);
        this.editTextField.setInputType(inputType);
        this.editTextField.setTag(field.getJsonName());

        mCustomizedUIComponent.addRules(this.editTextField, rules);
        mCustomizedUIComponent.addComponent(this.editTextField);

    }

    private EditText buildField(Context context) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        EditText editText = new EditText(context);
        editText.setLayoutParams(layoutParams);
        return editText;
    }

    public View get(){
        return this.mCustomizedUIComponent.getComponent();
    }

    public void setText(String text){
        this.editTextField.setText(text);
    }
}
