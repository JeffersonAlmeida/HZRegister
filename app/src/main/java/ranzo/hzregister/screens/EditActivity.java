package ranzo.hzregister.screens;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;
import ranzo.hzregister.R;
import ranzo.hzregister.database.DataBase;
import ranzo.hzregister.database.UserDao;
import ranzo.hzregister.json.Fields;
import ranzo.hzregister.model.User;

public class EditActivity extends FragmentActivity implements
         FormFragment.Listener {

    private Fields fields;
    private User user;
    private FormFragment fragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_edit);

        ButterKnife.bind(this);

        Bundle extras = this.getIntent().getExtras();
        this.fields = (Fields) extras.getSerializable("fields");

        Bundle args = ( saved != null ) ? saved : extras;
        this.user = (User) args.getSerializable("user");

        if ( saved != null ) {
            this.fragment = (FormFragment)
                    getSupportFragmentManager().getFragment(saved, "fragment");
        }else {
            this.fragment = FormFragment.newInstance(this.fields, this.user);
            this.fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_fragment, this.fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("onSaveInstance", "EditActivity");
        getSupportFragmentManager().putFragment(outState, "fragment", fragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onValidationSucceeded(User user) {
        updateUser(user);
        Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        showList();
    }

    private void updateUser(User user) {
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
