package ranzo.hzregister.ui;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.mobsandgeeks.saripaar.QuickRule;

import ranzo.hzregister.json.Field;
import ranzo.hzregister.model.User;
import ranzo.hzregister.rules.NameRule;

public class Name implements UIComponent {

    private EditTextComponent component;

    public Name(Context context, Field field) {
        QuickRule rule = new NameRule();
        int inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
        this.component = new EditTextComponent(context, field, inputType, rule);
    }

    @Override
    public View build() {
        return this.component.get();
    }

    @Override
    public View build(User user) {
        this.component.setText(user.getFullName());
        return build();
    }
}
