package ranzo.hzregister.screens;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import ranzo.hzregister.R;
import ranzo.hzregister.database.DataBase;
import ranzo.hzregister.database.UserDao;
import ranzo.hzregister.json.Fields;
import ranzo.hzregister.model.User;

public class EditActivity
extends FragmentActivity implements FormFragment.Listener {

    private Fields fields;
    private User user;
    private FormFragment fragment;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_edit);
        Bundle extras = this.getIntent().getExtras();
        this.fields = (Fields) extras.getSerializable("fields");
        Bundle args = ( saved != null ) ? saved : extras;
        this.user = (User) args.getSerializable("user");
        this.fragment = buildFragment(saved);
    }

    private FormFragment buildFragment(Bundle saved) {
        return ( saved != null ) ? getSavedFragment(saved) : createFragment();
    }

    private FormFragment createFragment() {
        FormFragment formFragment = FormFragment.newInstance(this.fields, this.user);
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, formFragment);
        fragmentTransaction.commit();
        return formFragment;
    }

    private FormFragment getSavedFragment(Bundle saved) {
        return (FormFragment)
                getSupportFragmentManager().getFragment(saved, "fragment");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        User updatedUser = this.fragment.getUserFromValues();
        outState.putSerializable("user", updatedUser);
        getSupportFragmentManager().putFragment(outState, "fragment", fragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onValidationSucceeded(User user) {
        updateUser(user);
        back();
    }

    private void updateUser(User user) {
        UserDao userDao =  DataBase.getInstance().getUserDao();
        User updatedUser = this.fragment.getUserFromValues();
        updatedUser.setId(this.user.getId());
        userDao.edit(updatedUser);
    }

    private void back() {
        finish();
    }
}
