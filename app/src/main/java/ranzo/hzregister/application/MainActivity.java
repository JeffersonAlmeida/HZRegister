package ranzo.hzregister.application;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ranzo.hzregister.R;
import ranzo.hzregister.database.DataBase;
import ranzo.hzregister.database.UserDao;
import ranzo.hzregister.json.Field;
import ranzo.hzregister.json.FromJson;
import ranzo.hzregister.json.JsonDownloader;
import ranzo.hzregister.list.ListActivity;
import ranzo.hzregister.model.User;
import ranzo.hzregister.rules.RuleManager;
import ranzo.hzregister.ui.UIElement;

public class MainActivity extends 
Activity implements JsonDownloader.OnTaskCompleted, ValidationListener {

	@Bind(R.id.linear_layout) protected LinearLayout linearLayout;
	private List<Field> fields;
	
	private Validator validator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		JsonDownloader download = new JsonDownloader(MainActivity.this, this);
		download.execute();
		validator = new Validator(this);
		validator.setValidationListener(this);
		RuleManager.initialize(validator);
	}

	@Override
	public void onTaskCompleted(final String json) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				convertJson(json);	
			}
		});
	}

	private void convertJson(String json) {
		this.fields = new FromJson().toFields(json);
		BuildUI();
	}

	private void BuildUI(){
		Factory factory = new Factory(this);
		for (Field field: fields){
			UIElement element = factory.buildObject(field);
			if ( element != null){
				View view = element.build();
				linearLayout.addView(view);
			}
		}
		buildSubmitButton();
	}
	
	private void buildSubmitButton() {
		Button button = new Button(this);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
				(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		button.setLayoutParams(layoutParams);
		button.setText("Submit");
		button.setOnClickListener(new SubmitListener());
		linearLayout.addView(button);
	}

	private class SubmitListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			validator.validate();
		}
	}

	@Override
	public void onValidationFailed(List<ValidationError> errors) {
		for (ValidationError error : errors) {
			View view = error.getView();
			String message = error.getCollatedErrorMessage(this);
			if (view instanceof EditText) 
				((EditText) view).setError(message);
			else 
				Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onValidationSucceeded() {
		saveData();
		showList();
	}
	
	private void showList() {
		Intent i = new Intent(this, ListActivity.class);
		startActivity(i);
	}

	private void saveData() {
		User user = getUserFromValues();
		DataBase db = new DataBase();
		UserDao userDao = db.getUserDao();
		userDao.inserir(user);
	}

	private User getUserFromValues(){
		
		String name = getContent("name");
		String email = getContent("email");
		String cpf = getContent("cpf");
		String phone = getContent("phone");
		String password = getContent("password");
		String gender = getContent("gender");
		String birthday = getContent("birthday");
		String state = getContent("state");
		
		return new User.Builder().fullName(name).email(email).cpf(cpf).
		phone(phone).password(password).gender(gender).birthday(birthday).state(state).build();
		
	}

	private String getContent(String tag) {
		String content = "";
		View view = linearLayout.findViewWithTag(tag);
		if (view instanceof EditText)
			content = ((EditText)view).getText().toString();
		else if (view instanceof Spinner)
			content = ((Spinner)view).getSelectedItem().toString();
		return content;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle arg0) {
		super.onSaveInstanceState(arg0);
		/*TODO: save form values */
	}
}
