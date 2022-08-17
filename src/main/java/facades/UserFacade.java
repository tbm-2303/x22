package facades;

import dtos.TenantDTO;
import entities.Role;
import entities.Tenant;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import errorhandling.UsernameTakenException;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.userName=:username", User.class);
            query.setParameter("username", username);
            user = query.getSingleResult();


            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }
    //also tenant
    public TenantDTO createUser(TenantDTO tenant) throws UsernameTakenException {
        EntityManager em = emf.createEntityManager();

        if (usernameTaken(tenant.getUsername())) {
            throw new UsernameTakenException("Username is taken");
        }

        User user = new User(tenant.getUsername(),tenant.getPassword());
        Role userRole = em.find(Role.class, "user");
        user.setRole(userRole);
        Tenant newTenant = new Tenant(tenant.getName(),tenant.getPhone(),tenant.getJob());
        newTenant.setUser(user);
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.persist(newTenant);
            em.getTransaction().commit();
            return new TenantDTO(newTenant);
        } finally {
            em.close();
        }
    }

    static boolean usernameTaken(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u from User u where u.userName =:username", User.class);
            query.setParameter("username", username);
            query.setMaxResults(1);
            try {
                User userFound = query.getSingleResult();
            } catch (NoResultException e) {
                return false;
            }
        } finally {
            em.close();
        }
        return true;
    }

}
