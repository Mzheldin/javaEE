package beans;

import persist.User;
import persist.UserRepository;
import services.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.jws.WebService;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@WebService(endpointInterface = "services.ws.UserServiceWs", serviceName = "UserService")
@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class UserBean implements UserService {
    @EJB
    private UserRepository userRepository;

    @Transactional
    public void merge(User user) {
        User merged = userRepository.merge(new User(user));
        user.setId(merged.getId());
    }

    @Transactional
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Transactional
    public User findById(int id) {
        return new User(userRepository.findById(id));
    }

    @Transactional
    public boolean existsById(int id) {
        return userRepository.findById(id) != null;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(User::new)
                .collect(Collectors.toList());
    }
}
