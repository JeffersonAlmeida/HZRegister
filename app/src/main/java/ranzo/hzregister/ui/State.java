package ranzo.hzregister.ui;


import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import ranzo.hzregister.json.Field;
import ranzo.hzregister.rules.CPFRule;
import ranzo.hzregister.rules.RuleManager;


public class State extends UIElement {

	public State(Context context, Field field) {
		super(context, field);
	}

	@Override
	public View build() {
		Spinner spinner = configure(buildSpinner());
		addRules(spinner);
		super.layout.addView(spinner);
		return super.layout;
	}
	
	@Override
	protected void addRules(View view){
		super.addRules(view);
	}

	private Spinner configure(Spinner spinner) {
		super.configure(spinner);
		spinner.setSelection(1);
		return spinner;
	}

	private Spinner buildSpinner() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
				(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		List<String> states = field.getCombo();
		ArrayAdapter<String> adapter = new ArrayAdapter
				<String>(context, android.R.layout.simple_spinner_dropdown_item, states);
		
		Spinner spinner = new Spinner(context);
		spinner.setAdapter(adapter);
		return spinner;
	}

}
