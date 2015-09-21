package ranzo.hzregister.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ranzo.hzregister.R;
import ranzo.hzregister.database.DataBase;
import ranzo.hzregister.database.UserDao;
import ranzo.hzregister.json.Fields;
import ranzo.hzregister.json.JsonDownloader;
import ranzo.hzregister.model.User;
import ranzo.hzregister.rules.RuleManager;

public class MainActivity extends 
Activity implements JsonDownloader.OnTaskCompleted,
        ValidationListener, FormFragment.Listener {

	@Bind(R.id.linear_layout) protected RelativeLayout layout;
    @Bind(R.id.button) protected Button button;

	private Fields fields;
	
	private Validator validator;
	private FormFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);
		validator = new Validator(this);
		validator.setValidationListener(this);
		RuleManager.initialize(validator);
		/*
		JsonDownloader download = new JsonDownloader(MainActivity.this, this);
		download.execute();
		*/
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
		this.fields = new Fields(json);

		/*
		this.fragment = FormFragment.newInstance(this.fields, null);
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content_fragment, fragment);
		fragmentTransaction.commit();
		*/

	}

    @OnClick(R.id.button)
    public void submit(View view) {
        //validator.validate();
        saveData();
        showList();

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
		Intent intent = new Intent(this, ListActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("fields", this.fields);
        intent.putExtras(args);
		startActivity(intent);
	}

	private void saveData() {
		User user = this.fragment.getUserFromValues();
		DataBase db = new DataBase();
		UserDao userDao = db.getUserDao();
		userDao.inserir(user);
	}


	@Override
	public void onValidationSucceeded(User user) {

	}
}
