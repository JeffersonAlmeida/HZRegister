package ranzo.hzregister.ui;

import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.QuickRule;

import ranzo.hzregister.json.Field;
import ranzo.hzregister.rules.IntervalRule;
import ranzo.hzregister.rules.RequiredRule;
import ranzo.hzregister.rules.RuleManager;

public class CustomizedUIComponent {

    private Context context;
    private LinearLayout linearLayout;
    private Field field;

    public CustomizedUIComponent (Context context, Field field){
        this.context = context;
        this.field = field;
        this.linearLayout = buildLinearLayout();
        TextView fieldDescription = buildLabel();
        this.linearLayout.addView(fieldDescription);
    }

    public void addRules(View uiComponent, QuickRule[] rules) {
        addGenericRules(uiComponent);
        for (QuickRule rule : rules)
            RuleManager.get().addRule(uiComponent, rule);
    }

    private void addGenericRules(View uiComponent) {
        RuleManager rm = RuleManager.get();
        int min = field.getMinSize();
        int max = field.getMaxSize();
        rm.addRule(uiComponent, new IntervalRule(min, max));
        if ( field.isMandatory() )
            rm.addRule(uiComponent, new RequiredRule());
    }

    private LinearLayout buildLinearLayout(){
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams
                (ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    private TextView buildLabel() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(context);
        textView.setText(field.getJsonName());
        textView.setTextSize(16);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    public View get(){
        return this.linearLayout;
    }

    public void addComponent(View component) {
        this.linearLayout.addView(component);
    }

    public View getComponent() {
        return this.linearLayout;
    }
}
