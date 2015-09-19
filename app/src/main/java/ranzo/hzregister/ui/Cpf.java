package ranzo.hzregister.ui;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.mobsandgeeks.saripaar.QuickRule;

import ranzo.hzregister.json.Field;
import ranzo.hzregister.model.User;
import ranzo.hzregister.rules.CPFRule;


public class Cpf implements UIComponent {

	private EditTextComponent component;

	public Cpf(Context context, Field field) {
		QuickRule rule = new CPFRule();
		int inputType = InputType.TYPE_CLASS_TEXT;
		this.component = new EditTextComponent(context, field, inputType, rule);
	}

	@Override
	public View build() {
		return this.component.get();
	}

	@Override
	public View build(User user) {
		this.component.setText(user.getCpf());
		return build();
	}
	
}
