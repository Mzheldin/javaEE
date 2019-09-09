package services;

import persist.User;

import java.util.List;

public interface UserService {
    public void merge(User user);
    public void delete(int id);
    public User findById(int id);
    public boolean existsById(int id);
    public List<User> getAllUsers();
}
