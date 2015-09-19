package ranzo.hzregister.ui;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.mobsandgeeks.saripaar.QuickRule;

import ranzo.hzregister.json.Field;
import ranzo.hzregister.model.User;
import ranzo.hzregister.rules.BirthdayRule;


public class Birthday implements UIComponent {

	private EditTextComponent component;

	public Birthday(Context context, Field field) {
		QuickRule rule = new BirthdayRule();
		int inputType = InputType.TYPE_DATETIME_VARIATION_DATE;
		this.component = new EditTextComponent(context, field, inputType, rule);
	}

	@Override
	public View build() {
		return this.component.get();
	}

	@Override
	public View build(User user) {
		this.component.setText(user.getBirthday());
		return build();
	}
}
