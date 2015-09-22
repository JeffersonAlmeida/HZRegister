package ranzo.hzregister.screens;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ranzo.hzregister.R;
import ranzo.hzregister.factory.Factory;
import ranzo.hzregister.json.Field;
import ranzo.hzregister.json.Fields;
import ranzo.hzregister.model.User;
import ranzo.hzregister.rules.RuleManager;
import ranzo.hzregister.ui.UIComponent;

import static ranzo.hzregister.util.JsonConstantsNames.*;

public class FormFragment extends Fragment
        implements Validator.ValidationListener{

    @Bind(R.id.linear_layout_fragment) protected LinearLayout layout;

    private Fields fields;
    private User user;
    private Listener listener;
    private Validator validator;

    public static FormFragment newInstance (Fields fields, User user){
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        args.putSerializable("fields", fields);
        args.putSerializable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (Listener) activity;
    }

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        this.fields = (Fields) getArguments().getSerializable("fields");
        Bundle args = ( saved != null ) ? saved : getArguments();
        this.user = (User) args.getSerializable("user");
        validator = new Validator(this);
        validator.setValidationListener(this);
        RuleManager.initialize(validator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle saved) {
        View layout = inflater.inflate(R.layout.form_fragment, root, false);
        ButterKnife.bind(this, layout);
        buildUI();
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        User userFromValues = this.getUserFromValues();
        outState.putSerializable("user", userFromValues);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void buildUI(){
        Factory factory = new Factory(this.getActivity());
        for (Field field: fields.getList()){
            UIComponent element = factory.buildObject(field);
            if ( element != null ) build(element);
        }
        buildSubmitButton();
    }

    private void buildSubmitButton() {
        Button button = new Button(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(layoutParams);
        button.setText("Submit");
        button.setOnClickListener(new SubmitListener());
        layout.addView(button);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            if (view instanceof EditText)
                ((EditText) view).setError(message);
            else Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onValidationSucceeded() {
        User user = getUserFromValues();
        this.listener.onValidationSucceeded(user);
    }

    private class SubmitListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
           // validator.validate();
            onValidationSucceeded();
        }
    }

    public User getUserFromValues(){

        String name = getContent(NAME);
        String email = getContent(EMAIL);
        String cpf = getContent(CPF);
        String phone = getContent(PHONE);
        String password = getContent(PASSWORD);
        String gender = getContent(GENDER);
        String birthday = getContent(BIRTHDAY);
        String state = getContent(STATE);

        return new User.Builder().id(this.user.getId()).
                fullName(name).email(email).cpf(cpf).
                phone(phone).password(password).
                gender(gender).birthday(birthday).state(state).build();

    }

    private String getContent(String tag) {
        String content = "";
        View view = layout.findViewWithTag(tag);
        if (view instanceof EditText)
            content = ((EditText)view).getText().toString();
        else if (view instanceof Spinner)
            content = ((Spinner)view).getSelectedItem().toString();
        return content;
    }

    private void build(UIComponent element) {
        View view = ( user != null ) ? element.build(user) : element.build();
        layout.addView(view);
    }

    public interface Listener {
        public void onValidationSucceeded(User user);
    }
}
