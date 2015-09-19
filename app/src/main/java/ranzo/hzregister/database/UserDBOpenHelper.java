package ranzo.hzregister.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBOpenHelper extends SQLiteOpenHelper {

	public UserDBOpenHelper(String name, int version, Context context) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(UserDaoSqlite.CREATE_TABLE_USER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "	+ UserDaoSqlite.TABLE_USER);
		onCreate(db);
	}
}
