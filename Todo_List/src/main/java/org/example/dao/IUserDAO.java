package org.example.dao;
import org.example.entity.User;
import java.util.List;
public interface IUserDAO {
    public boolean addUser(User user);
    boolean getUserById(Long userId);
    public List<User> getAllUsers();
    public void deleteUser(Long userId);
}
