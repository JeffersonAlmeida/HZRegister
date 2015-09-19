package ranzo.hzregister.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ranzo.hzregister.model.User;


public class UserDaoSqlite implements UserDao {

	private UserDBOpenHelper helper;
	
	public static final String TABLE_USER = "user";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FULL_NAME = "fullname";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_CPF = "cpf";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_GENDER = "gender";
	public static final String COLUMN_BIRTHDAY = "birthday";
	public static final String COLUMN_STATE = "state";
	
	public static final String CREATE_TABLE_USER =
			"CREATE TABLE "
			+ TABLE_USER + "("
			+ COLUMN_ID  + " integer primary key autoincrement, "
			+ COLUMN_FULL_NAME	+ " text, "
			+ COLUMN_EMAIL	+ " text, "
			+ COLUMN_CPF    + " text, "
			+ COLUMN_PHONE	+ " text, "
			+ COLUMN_PASSWORD	+ " text, "
			+ COLUMN_GENDER	+ " text, "
			+ COLUMN_BIRTHDAY + " text, "
			+ COLUMN_STATE	+ " text );";
	
	public UserDaoSqlite(UserDBOpenHelper helper) {
		this.helper = helper;
	}
	
	@Override
	public long inserir(User user) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, user.getId()); 
		values.put(COLUMN_FULL_NAME, user.getFullName());
		values.put(COLUMN_EMAIL, user.getEmail());
		values.put(COLUMN_CPF, user.getCpf());
		values.put(COLUMN_PHONE, user.getPhone());
		values.put(COLUMN_PASSWORD, user.getPassword());
		values.put(COLUMN_GENDER, user.getGender());
		values.put(COLUMN_BIRTHDAY, user.getBirthday());
		values.put(COLUMN_STATE, user.getState());
		SQLiteDatabase db = this.helper.getWritableDatabase();
		return db.insertOrThrow(TABLE_USER, null, values);
	}

	@Override
	public void editar(User user) {
		if ( user != null && user.getId() != null && user.getId() > 0 ) {
			ContentValues newValues = new ContentValues();
			newValues.put(COLUMN_ID, user.getId()); 
			newValues.put(COLUMN_FULL_NAME, user.getFullName());
			newValues.put(COLUMN_EMAIL, user.getEmail());
			newValues.put(COLUMN_CPF, user.getCpf());
			newValues.put(COLUMN_PHONE, user.getPhone());
			newValues.put(COLUMN_PASSWORD, user.getPassword());
			newValues.put(COLUMN_GENDER, user.getGender());
			newValues.put(COLUMN_BIRTHDAY, user.getBirthday());
			newValues.put(COLUMN_STATE, user.getState());
			SQLiteDatabase db = this.helper.getWritableDatabase();
			db.update(TABLE_USER, newValues, COLUMN_ID + "=" + user.getId(), null);		
		}
	}

	@Override
	public void remover(User user) {
		if ( user != null && user.getId() != null ) {
			SQLiteDatabase db = helper.getWritableDatabase();
			db.delete(TABLE_USER, COLUMN_ID + "=" + user.getId(), null);
		}
	}

	@Override
	public List<User> listar() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USER, null, null, null, null, null, null);
		return buildUsers(cursor);
	}
	
	private List<User> buildUsers(Cursor cursor) {
		List<User> users = new ArrayList<User>();
		if ( cursor != null && cursor.moveToFirst() ){
			do {
				users.add(buildUser(cursor));
			}while( cursor.moveToNext() );
		}
		((ArrayList<User>) users).trimToSize();
		return users;
	}
	
	private User buildUser(Cursor cursor) {
		if (cursor!=null){
			Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
			String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME)); 
			String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
			String cpf = cursor.getString(cursor.getColumnIndex(COLUMN_CPF));
			String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
			String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
			String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
			String birthday = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY));
			String state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE));
			return new User.Builder().id(id).fullName(fullName).email(email).cpf(cpf).
					phone(phone).password(password).gender(gender).birthday(birthday).
					state(state).build();
		}
		return null;
	}

	@Override
	public User carregar(Long id) {
		if ( id != null ) {
			SQLiteDatabase db = helper.getReadableDatabase();
			Cursor cursor = db.query(TABLE_USER, null ,
					COLUMN_ID + " = " + id, null, null, null, null);
			if(cursor != null){
				cursor.moveToFirst();
				return buildUser(cursor);
			}
		}
		return null;
	}
}
