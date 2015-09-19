package ranzo.hzregister.database;

import android.content.Context;

import ranzo.hzregister.application.MyApplication;


public class DataBase {

	private static final String DATABASE_NAME = "userdb";
	private static final int DATABASE_VERSION = 1;
	
	private UserDao userDao;
	private UserDBOpenHelper helper;
	
	public DataBase() {
		Context appContext = MyApplication.getAppContext();
		this.helper = new UserDBOpenHelper
				(DATABASE_NAME, DATABASE_VERSION, appContext);
	}
	
	public void closeConnection(){
		this.helper.close();
	}
	
	public synchronized UserDao getUserDao(){
		if ( userDao == null )
			userDao = new UserDaoSqlite(helper);
		return userDao;
	}
}
