package ranzo.hzregister.database;

import java.util.List;

import ranzo.hzregister.model.User;


public interface UserDao {
	
	public User carregar(Long id);
	
	public long inserir (User user);

	public void editar (User user);
	
	public void remover(User user);
	
	public List<User> listar();

}
