package ranzo.hzregister.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ranzo.hzregister.R;
import ranzo.hzregister.database.DataBase;
import ranzo.hzregister.model.User;


public class ListActivity
extends Activity implements OnItemClickListener{
	
	@Bind(R.id.list) protected ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		ButterKnife.bind(this);
		
		DataBase dataBase = new DataBase();
		List<User> users = dataBase.getUserDao().listar();
		
		MyAdapter adapter = new MyAdapter(this, users);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		User user = (User) list.getItemAtPosition(position);
		Toast.makeText(this, user.toStr(), Toast.LENGTH_LONG).show();
	}

}
