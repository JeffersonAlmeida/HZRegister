package ranzo.hzregister.application;

import android.content.Context;

import ranzo.hzregister.json.Field;
import ranzo.hzregister.ui.UIElement;
import ranzo.hzregister.ui.*;

public class Factory {
	
	private Context context;

	public Factory(Context context) {
		this.context = context;
	}
	
	public UIElement buildObject(Field field){
		String jsonName = field.getJsonName();
		if ( jsonName.equals("name") )
			return new FullName(context, field);
		
		else if ( field.getJsonName().equals("email") )
			return new Email(context, field);
		
		else if ( jsonName.equals("cpf") )
			return new Cpf(context,field);
		
		else if ( jsonName.equals("phone") )
			return new Phone(context, field);
		
		else if ( jsonName.equals("password") )
			return new Password(context, field);
		
		else if ( jsonName.equals("gender") )
			return new Gender(context, field);
		
		else if ( jsonName.equals("birthday") )
			return new Birthday(context, field);
		
		else if ( jsonName.equals("state") )
			return new State(context, field);
		
		return null;
	}
}
