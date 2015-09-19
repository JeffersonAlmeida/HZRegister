package ranzo.hzregister.rules;

import android.content.Context;
import android.view.View;

import com.mobsandgeeks.saripaar.QuickRule;

import ranzo.hzregister.R;

public class PhoneRule extends QuickRule<View> {

	@Override
	public boolean isValid(View view) {
		return true;
	}

	@Override
	public String getMessage(Context context) {
		return context.getResources().getString(R.string.invalid);
	}

}
