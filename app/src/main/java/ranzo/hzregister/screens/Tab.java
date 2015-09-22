package ranzo.hzregister.screens;

public enum Tab {

    REGISTER_FORM, USER_LIST;

    public static Tab get(int position){
        return values()[position];
    }
}
