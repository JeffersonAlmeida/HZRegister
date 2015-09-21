package ranzo.hzregister.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import ranzo.hzregister.R;
import ranzo.hzregister.database.DataBase;
import ranzo.hzregister.database.UserDao;
import ranzo.hzregister.json.Fields;
import ranzo.hzregister.json.JsonDownloader;
import ranzo.hzregister.model.User;

public class TabActivity extends ActionBarActivity
        implements ActionBar.TabListener,
        ListFragment.OnFragmentInteractionListener,
        JsonDownloader.OnTaskCompleted, FormFragment.Listener {

    private Fields fields;
    private ListFragment listFragment;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_tab);
        Context context = TabActivity.this;
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        String msg = context.getResources().getString(R.string.dialog_message);
        progressDialog.setMessage(msg);

        if ( saved != null ){
            this.fields = (Fields) saved.getSerializable("fields");
            buildUserInterface();
        }else {
            JsonDownloader download = new JsonDownloader(progressDialog, this);
            download.execute();
        }
    }

    @Override
    public void onTaskCompleted(final String json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createUserInterfaceFrom(json);
            }
        });
    }

    private void createUserInterfaceFrom(String json) {
        this.fields = new Fields(json);
        buildUserInterface();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("onSaveInstance", "TabActivity");
        outState.putSerializable("fields", this.fields);
        super.onSaveInstanceState(outState);
    }

    private void buildUserInterface(){
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) { }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) { }

    @Override
    public void onValidationSucceeded(User user) {
        save(user);
        Toast.makeText(this, "User Saved!", Toast.LENGTH_LONG).show();
    }

    private void save(User user) {
        DataBase db = new DataBase();
        UserDao userDao = db.getUserDao();
        userDao.inserir(user);
        this.listFragment.dataChanged();
    }

    @Override
    public void onClickItem(User user) {
        Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
        startEditIntent(user);

    }

    private void startEditIntent(User user) {
        Intent intent = new Intent(this, EditActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("fields", this.fields);
        args.putSerializable("user", user);
        intent.putExtras(args);
        startActivity(intent);
    }


    public void removeUser (User user){
        DataBase db = new DataBase();
        UserDao userDao = db.getUserDao();
        listFragment.removeUser(user);
        userDao.remover(user);
        Toast.makeText(this, "Removed!", Toast.LENGTH_LONG).show();
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final int PAGE_NUMBER = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if ( position == 0 )
                return FormFragment.newInstance(fields,null);

            return listFragment = ListFragment.newInstance(fields);
        }

        @Override
        public int getCount() {
            return PAGE_NUMBER;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.register_user).toUpperCase(l);
                case 1:
                    return getString(R.string.users_list).toUpperCase(l);
            }
            return null;
        }
    }
}
