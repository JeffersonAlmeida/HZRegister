package ranzo.hzregister.ui;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import ranzo.hzregister.json.Field;
import ranzo.hzregister.rules.CPFRule;
import ranzo.hzregister.rules.RuleManager;


public class Birthday extends UIElement {

	public Birthday(Context context, Field field) {
		super(context, field);
	}

	@Override
	public View build() {
		EditText editText = buidEditText();
		addRules(editText);
		configure(editText);
		addComponent(editText);
		return getLayout();
	}
	
	@Override
	protected void addRules(View view){
		super.addRules(view);
	}

	private EditText configure(EditText editText) {
		super.configure(editText);
		editText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
		return editText;
	}

	private EditText buidEditText() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
				(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		EditText editText = new EditText(context);
		editText.setLayoutParams(layoutParams);
		return editText;
	}
	
}
