package ranzo.hzregister.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import ranzo.hzregister.R;
import com.mobsandgeeks.saripaar.QuickRule;

public class EmailRule extends QuickRule<View> {

	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	public boolean isValid(View view) {
		String input = "";
		if ( view instanceof EditText)
			input = ((EditText)view).getText().toString();
		return validate(input);
	}
	
	public boolean validate(final String hex) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();

	}

	@Override
	public String getMessage(Context context) {
		return context.getResources().getString(R.string.invalid);
	}

}
