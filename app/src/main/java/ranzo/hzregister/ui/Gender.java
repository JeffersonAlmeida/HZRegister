package ranzo.hzregister.ui;

import android.content.Context;
import android.view.View;

import com.mobsandgeeks.saripaar.QuickRule;

import ranzo.hzregister.R;
import ranzo.hzregister.json.Field;
import ranzo.hzregister.model.User;
import ranzo.hzregister.rules.GenderRule;


public class Gender implements UIComponent{

	private SpinnerComponent component;

	public Gender(Context context, Field field) {
		QuickRule rule = new GenderRule();
		String[] items =
				context.getResources().getStringArray(R.array.gender);
		this.component = new SpinnerComponent(context,field,items,rule);
	}

	@Override
	public View build() {
		return this.component.get();
	}

	@Override
	public View build(User user) {
		this.component.setText(user.getGender());
		return build();
	}
}
