package ranzo.hzregister.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ranzo.hzregister.R;
import ranzo.hzregister.database.DataBase;
import ranzo.hzregister.json.Fields;
import ranzo.hzregister.model.User;


public class ListActivity
extends Activity implements OnItemClickListener{
	
	@Bind(R.id.list) protected ListView list;

	private Fields fields;
	private MyAdapter myAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		Bundle extras = this.getIntent().getExtras();
		this.fields = (Fields) extras.getSerializable("fields");

		ButterKnife.bind(this);
		
		DataBase dataBase = new DataBase();
		List<User> users = dataBase.getUserDao().listar();
		
		this.myAdapter = new MyAdapter(this, users);
		list.setAdapter(this.myAdapter);
		list.setOnItemClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		this.myAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		User user = (User) list.getItemAtPosition(position);
		startEditIntent(user);
	}

	private void startEditIntent(User user) {
		Intent intent = new Intent(ListActivity.this, EditActivity.class);
		Bundle args = new Bundle();
		args.putSerializable("fields", this.fields);
		args.putSerializable("user", user);
		intent.putExtras(args);
		startActivity(intent);
	}

}
