package ranzo.hzregister.rules;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

import ranzo.hzregister.R;

public class CPFRule extends QuickRule<View> {

	private String cpf;

	@Override
	public boolean isValid(View view) {
		cpf = ((EditText)view).getText().toString();
		return checkCPF();
	}

	private boolean checkCPF(){
		removeCharacters();
		return isSizeOk() && !allTheSame() && isNumbersComparisonOk(); 
	}

	private void removeCharacters(){    
		this.cpf = this.cpf.replace("-","");
		this.cpf = this.cpf.replace(".","");    
	}

	private boolean isSizeOk(){   
		return cpf.length() == 11;
	}

	private boolean allTheSame(){    
		char first = cpf.charAt(0);
		char [] sequence = cpf.toCharArray();    
		for( char c: sequence  )
			if ( c != first )
				return false;        
		return true;
	}
	
	private boolean isNumbersComparisonOk(){
		String generatedCPF = this.cpf.substring(0, 9);
		generatedCPF = generatedCPF.concat(calculate(generatedCPF));
		generatedCPF = generatedCPF.concat(calculate(generatedCPF));
		return generatedCPF.equals(this.cpf);
	}

	private String calculate(String cpf){    
		int generated = 0;
		int mult = cpf.length()+1;
		char [] sequence = cpf.toCharArray();
		for ( int i = 0; i < cpf.length(); i++ )
			generated += (sequence[i]-48)* mult--;
		if ( generated % 11 < 2)
			generated = 0;
		else
			generated = 11 - generated % 11;
		return  String.valueOf(generated); 
	}

	@Override
	public String getMessage(Context context) {
		return context.getResources().getString(R.string.invalid);
	}

}
