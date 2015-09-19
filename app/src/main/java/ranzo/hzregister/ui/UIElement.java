package ranzo.hzregister.ui;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ranzo.hzregister.json.Field;
import ranzo.hzregister.rules.RuleManager;


public abstract class UIElement {

	protected Context context;
	protected Field field;
	protected LinearLayout layout;

	public UIElement(Context context, Field field) {
		this.context = context;
		this.field = field;
		this.layout = (LinearLayout) buildLinearLayout();
	}

	public abstract View build();

	protected void addComponent(View view){
		this.layout.addView(view);
	}
	
	protected View getLayout(){
		return this.layout;
	}

	protected void addRules(View view){
		RuleManager ruleManager = RuleManager.get();
		ruleManager.addGenericRules(view, field);
	}

	protected void configure(View view) {
		view.setTag(field.getJsonName());
	}

	protected View buildLinearLayout(){
		LinearLayout linearLayout = new LinearLayout(context);

		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);

		TextView textView = buildTextView();

		linearLayout.addView(textView);
		return linearLayout;
	}

	protected TextView buildTextView() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		TextView textView = new TextView(context);
		textView.setText(field.getJsonName());
		textView.setTextSize(16);
		textView.setLayoutParams(layoutParams);
		return textView;
	}

}
