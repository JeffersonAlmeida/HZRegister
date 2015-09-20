package ranzo.hzregister.screens;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ranzo.hzregister.R;
import ranzo.hzregister.database.DataBase;
import ranzo.hzregister.database.UserDao;
import ranzo.hzregister.json.Fields;
import ranzo.hzregister.model.User;

public class EditActivity extends Activity
implements Validator.ValidationListener, FormFragment.Listener {

    private Fields fields;
    private User user;

    private Validator validator;
    private FormFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ButterKnife.bind(this);

        Bundle extras = this.getIntent().getExtras();
        this.fields = (Fields) extras.getSerializable("fields");
        this.user = (User) extras.getSerializable("user");
        validator = new Validator(this);
        validator.setValidationListener(this);

        this.fragment = FormFragment.newInstance(this.fields, user);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, fragment);
        fragmentTransaction.commit();

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
        // updateUser();
    }

    @Override
    public void fragmentCreated() {

    }

    @OnClick(R.id.button)
    public void submit(View view) {
        //validator.validate();
        updateData();
        showList();

    }

    private void updateData() {
        DataBase db = new DataBase();
        UserDao userDao = db.getUserDao();
        User updatedUser = this.fragment.getUserFromValues();
        updatedUser.setId(this.user.getId());
        userDao.editar(updatedUser);
    }


    private void showList() {
        finish();
    }
}
