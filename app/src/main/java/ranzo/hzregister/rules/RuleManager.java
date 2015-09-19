package ranzo.hzregister.rules;

import android.view.View;

import com.mobsandgeeks.saripaar.QuickRule;
import com.mobsandgeeks.saripaar.Validator;

import ranzo.hzregister.json.Field;

public class RuleManager {
	
	private static RuleManager instace;
	
	private RuleManager (){}
	
	private static Validator validator;
	
	public static void initialize ( Validator v ) {
		validator = v;
	}
	
	public synchronized static RuleManager get(){
		if ( instace == null)
			instace = new RuleManager();
		return instace;
	}

	public void addGenericRules(View view, Field field) {
		validator.put(view, new IntervalRule(field.getMinSize(), field.getMaxSize()));
		if ( field.isMandatory() )
			validator.put(view, new NotEmptyRule());
	} 

	public void addRule (View view, QuickRule rule){
		validator.put(view, rule);
	}

}
