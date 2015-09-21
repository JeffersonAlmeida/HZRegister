package ranzo.hzregister.database;

import java.util.List;
import ranzo.hzregister.model.User;


public interface UserDao {
	
	public User load(Long id);
	
	public long insert(User user);

	public void edit(User user);
	
	public void remove(User user);
	
	public List<User> list();

}
