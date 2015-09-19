package ranzo.hzregister.rules;

import android.view.View;

import com.mobsandgeeks.saripaar.QuickRule;
import com.mobsandgeeks.saripaar.Validator;

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

	public void addRule (View view, QuickRule rule){
		validator.put(view, rule);
	}

}
