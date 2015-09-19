package ranzo.hzregister.ui;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.mobsandgeeks.saripaar.QuickRule;

import ranzo.hzregister.json.Field;
import ranzo.hzregister.model.User;
import ranzo.hzregister.rules.PhoneRule;


public class Phone implements UIComponent {

	private EditTextComponent component;

	public Phone(Context context, Field field) {
		QuickRule rule = new PhoneRule();
		int inputType = InputType.TYPE_CLASS_PHONE;
		this.component = new EditTextComponent(context, field, inputType, rule);
	}

	@Override
	public View build() {
		return this.component.get();
	}

	@Override
	public View build(User user) {
		this.component.setText(user.getPhone());
		return build();
	}
}