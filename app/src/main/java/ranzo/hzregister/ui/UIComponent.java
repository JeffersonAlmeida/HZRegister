package ranzo.hzregister.ui;

import android.view.View;

import ranzo.hzregister.model.User;

public interface UIComponent {

    public View build();
    public View build (User user);

}
