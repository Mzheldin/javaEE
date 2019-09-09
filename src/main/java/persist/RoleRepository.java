package persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.List;

@ApplicationScoped
@Named
public class RoleRepository {

    private Logger logger = LoggerFactory.getLogger(RoleRepository.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    @PostConstruct
    public void init() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        logger.info("Role table is empty. Initializing.");

        Long roleCount = em.createQuery("select count(*) from Role", Long.class).getSingleResult();
        if (roleCount == 0L) {
            userTransaction.begin();
            em.merge(new Role("ADMIN"));
            em.merge(new Role("GUEST"));
            userTransaction.commit();
        }
    }

    @Transactional
    public Role merge(Role role) {
        return em.merge(role);
    }

    @Transactional
    public Role findById(int id) {
        return em.find(Role.class, id);
    }

    @Transactional
    public List<Role> getAllRoles() {
        return em.createQuery("from Role ", Role.class).getResultList();
    }
}