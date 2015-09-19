package ranzo.hzregister.factory;

import android.content.Context;
import ranzo.hzregister.json.Field;
import ranzo.hzregister.ui.Birthday;
import ranzo.hzregister.ui.Cpf;
import ranzo.hzregister.ui.Email;
import ranzo.hzregister.ui.Gender;
import ranzo.hzregister.ui.Name;
import ranzo.hzregister.ui.Password;
import ranzo.hzregister.ui.Phone;
import ranzo.hzregister.ui.State;
import ranzo.hzregister.ui.UIComponent;
import static ranzo.hzregister.factory.JsonConstantsNames.*;

public class Factory {
	
	private Context context;

	public Factory(Context context) {
		this.context = context;
	}
	
	public UIComponent buildObject(Field field){

		String jsonName = field.getJsonName();

		if ( jsonName.equals(NAME) )
			return new Name(context, field);
		
		else if ( field.getJsonName().equals(EMAIL) )
			return new Email(context, field);
		
		else if ( jsonName.equals(CPF) )
			return new Cpf(context,field);
		
		else if ( jsonName.equals(PHONE) )
			return new Phone(context, field);
		
		else if ( jsonName.equals(PASSWORD) )
			return new Password(context, field);
		
		else if ( jsonName.equals(GENDER) )
			return new Gender(context, field);
		
		else if ( jsonName.equals(BIRTHDAY) )
			return new Birthday(context, field);
		
		else if ( jsonName.equals(STATE) )
			return new State(context, field);
		
		return null;
	}
}
