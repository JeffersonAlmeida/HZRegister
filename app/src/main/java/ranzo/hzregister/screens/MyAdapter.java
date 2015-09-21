package ranzo.hzregister.screens;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import ranzo.hzregister.model.User;

public class MyAdapter extends ArrayAdapter<User> {
	
	private List<User> users;
	private Context context;

	public MyAdapter(Context context, List<User> users) {
		super (context,android.R.layout.simple_list_item_1, android.R.id.text1, users);
		this.context = context;
		this.users = users;
	}
	
	public void swapItems(List<User> users) {
		this.users.clear();
		this.users.addAll(users);
		this.notifyDataSetChanged();
	}
}
