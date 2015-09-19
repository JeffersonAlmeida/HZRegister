package ranzo.hzregister.ui;


import android.content.Context;
import android.view.View;

import com.mobsandgeeks.saripaar.QuickRule;

import java.util.List;

import ranzo.hzregister.json.Field;
import ranzo.hzregister.model.User;
import ranzo.hzregister.rules.StateRule;


public class State implements UIComponent {

	private SpinnerComponent component;

	public State(Context context, Field field) {
		QuickRule rule = new StateRule();
		List<String> list = field.getCombo();
		String[] items = list.toArray(new String[list.size()]);;
		this.component = new SpinnerComponent(context,field, items, rule);
	}

	@Override
	public View build() {
		return this.component.get();
	}

	@Override
	public View build(User user) {
		this.component.setText(user.getState());
		return build();
	}
}
