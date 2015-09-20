package ranzo.hzregister.screens;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import butterknife.Bind;
import butterknife.ButterKnife;
import ranzo.hzregister.R;
import ranzo.hzregister.factory.Factory;
import ranzo.hzregister.json.Field;
import ranzo.hzregister.json.Fields;
import ranzo.hzregister.model.User;
import ranzo.hzregister.ui.UIComponent;

public class FormFragment extends Fragment {

    @Bind(R.id.linear_layout_fragment) protected LinearLayout layout;

    private Fields fields;
    private User user;
    private Listener listener;

    public static FormFragment newInstance (Fields fields, User user){
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        args.putSerializable("fields", fields);
        args.putSerializable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fields = (Fields) getArguments().getSerializable("fields");
        this.user = (User) getArguments().getSerializable("user");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle saved) {
        View layout = inflater.inflate(R.layout.form_fragment, root, false);
        ButterKnife.bind(this, layout);
        buildUI();
        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (Listener) activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.listener.fragmentCreated();
    }

    private void buildUI(){
        Factory factory = new Factory(this.getActivity());
        for (Field field: fields.getList()){
            UIComponent element = factory.buildObject(field);
            if ( element != null ) build(element);
        }
    }

    public User getUserFromValues(){

        String name = getContent("name"); // FIXME: Constants names
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
        public void fragmentCreated();
    }
}
