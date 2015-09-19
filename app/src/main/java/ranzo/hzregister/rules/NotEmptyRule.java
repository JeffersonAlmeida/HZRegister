package ranzo.hzregister.rules;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import ranzo.hzregister.R;
import com.mobsandgeeks.saripaar.QuickRule;

public class NotEmptyRule extends QuickRule<View> {

	@Override
	public boolean isValid(View view) {
		String input = "";
		if ( view instanceof EditText){
			input = ((EditText)view).getText().toString();
		}else if (view instanceof Spinner ){
			input = ((Spinner)view).getSelectedItem().toString();
		}
		return !input.trim().isEmpty();
	}

	@Override
	public String getMessage(Context context) {
		return context.getResources().getString(R.string.empty_input);
	}

}
