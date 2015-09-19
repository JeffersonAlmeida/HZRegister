package ranzo.hzregister.ui;

import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.mobsandgeeks.saripaar.QuickRule;

import ranzo.hzregister.json.Field;

public class SpinnerComponent {

    private ArrayAdapter<String> adapter;
    private Spinner spinner;
    private CustomizedUIComponent mCustomizedUIComponent;

    public SpinnerComponent
    (Context context, Field field, String[] items, QuickRule... rules){

        this.mCustomizedUIComponent = new CustomizedUIComponent(context, field);

        this.spinner = buildField(context, field, items);
        this.spinner.setTag(field.getJsonName());

        mCustomizedUIComponent.addRules(this.spinner, rules);
        mCustomizedUIComponent.addComponent(this.spinner);

    }

    private Spinner buildField(Context context, Field field, String[] items) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

        this.adapter = new ArrayAdapter
                <String>(context, android.R.layout.simple_spinner_dropdown_item, items);

        Spinner spinner = new Spinner(context);
        spinner.setAdapter(adapter);
        return spinner;
    }

    public View get(){
        return this.mCustomizedUIComponent.getComponent();
    }

    public void setText(String text){
        int position = this.adapter.getPosition(text);
        this.spinner.setSelection(position);
    }
}
