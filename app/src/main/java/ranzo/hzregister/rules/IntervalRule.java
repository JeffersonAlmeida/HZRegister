package ranzo.hzregister.rules;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobsandgeeks.saripaar.QuickRule;

public class IntervalRule extends QuickRule<View> {
	
	private int min;
	private int max;

	public IntervalRule(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean isValid(View view) {
		String input = "";
		if ( view instanceof EditText){
			input = ((EditText)view).getText().toString();
		}else if (view instanceof Spinner ){
			input = ((Spinner)view).getSelectedItem().toString();
		}
		
		if ( min == max)
			return input.length() == min;
		return input.length() >= min && input.length() <= max;
	}

	@Override
	public String getMessage(Context context) {
		String msg = "";
		if ( min == max )
			return msg = "Must have " + min + " caracters";
		return "Between [ " + min + " - " + max + " ] caracteres" ;
	}

}
