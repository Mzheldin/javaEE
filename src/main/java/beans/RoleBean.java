package beans;

import persist.Role;
import persist.RoleRepository;
import services.RoleService;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class RoleBean implements Serializable, RoleService {

    @Inject
    private RoleRepository roleRepository;

    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.getAllRoles().stream()
                .map(Role::new)
                .collect(Collectors.toList());
    }
}
