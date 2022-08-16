package rest;

import com.google.gson.Gson;
import entities.*;

import java.io.IOException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setup")
    public String setup() {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            User user = new User("user", "user123");
            User admin = new User("admin", "admin123");

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");

            user.setRole(userRole);
            admin.setRole(adminRole);

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);

            em.getTransaction().commit();
            return "ok! setup";
        } finally {
            em.close();
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setup2")
    public String setup2() {
        EntityManager em = EMF.createEntityManager();
        try {
            User user1 = new User("user", "test123");
            User user2 = new User("user2", "test123");
            User user3 = new User("user2", "test123");
            User admin = new User("admin", "test123");
            Rental rental1 = new Rental("01/08/2022", "01/01/2023", 85000, 10000, "boss");
            Rental rental2 = new Rental("01/08/2022", "01/01/2023", 85000, 10000, "boss");
            Rental rental3 = new Rental("01/08/2022", "01/01/2023", 85000, 10009, "boss ");
            Tenant tenant1 = new Tenant("jim jimsen", "23232323", "Selvstændig");
            Tenant tenant2 = new Tenant("anders andersen", "323233232", "diskodanser");
            Tenant tenant3 = new Tenant("kim kimsen", "22222", "astronaut");
            House house1 = new House("blichersvej 2", "Helsingør", 2);
            House house2 = new House("esrumvej 2", "snekkersten", 3);
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
//relations:
// add roles to user (bi, 12M/M21)
            user1.setRole(userRole);
            user2.setRole(userRole);
            user3.setRole(userRole);
            admin.setRole(adminRole);
// add user to tenant (bi, 121)
            tenant1.setUser(user1);
            tenant2.setUser(user2);
            tenant3.setUser(user3);
//add rental to house/add house to rental (bi, 12M/M21)
            house1.addRental(rental1);
            house1.addRental(rental2);
            house2.addRental(rental3);
//add tenant to rental
            rental1.addTenant(tenant1);
            rental2.addTenant(tenant2);
            rental3.addTenant(tenant3);
            rental3.addTenant(tenant1);
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(house1);
            em.persist(house2);
            em.persist(rental1);
            em.persist(rental2);
            em.persist(rental3);
            em.persist(tenant1);
            em.persist(tenant2);
            em.persist(tenant3);
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.getTransaction().commit();
            return "ok! setup";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
}