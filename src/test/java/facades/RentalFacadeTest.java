package facades;

import dtos.HouseDTO;
import entities.House;
import entities.Rental;
import entities.Tenant;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class RentalFacadeTest {
    private static EntityManagerFactory emf;
    private static RentalFacade facade;

    House house1, house2;
    Tenant tenant1, tenant2, tenant3;
    Rental rental1, rental2, rental3;
    User user1, user2, user3;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RentalFacade.getFacadeExample(emf);
    }

    @AfterEach
    void tearDown() {
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        user1 = new User("user1", "test123");
        user2 = new User("user2", "test123");
        user3 = new User("user3", "test123");
        rental1 = new Rental("01/08/2022", "01/01/2023", 85000, 10000, "boss");
        rental2 = new Rental("01/08/2022", "01/01/2023", 85000, 10000, "boss");
        rental3 = new Rental("01/08/2022", "01/01/2023", 85000, 10009, "boss ");
        tenant1 = new Tenant("jim jimsen", "23232323", "Selvstændig");
        tenant2 = new Tenant("anders andersen", "323233232", "diskodanser");
        tenant3 = new Tenant("kim kimsen", "22222", "astronaut");
        house1 = new House("blichersvej 2", "Helsingør", 2);
        house2 = new House("esrumvej 2", "snekkersten", 3);

//add user to tenant
        tenant1.setUser(user1);
        tenant2.setUser(user2);
        tenant3.setUser(user3);
//add rental to house/add house to rental
        house1.addRental(rental1);
        house1.addRental(rental2);
        house2.addRental(rental3);
//add tenant to rental
        rental1.addTenant(tenant1);
        rental2.addTenant(tenant2);
        rental3.addTenant(tenant3);
        rental3.addTenant(tenant1);

        em.getTransaction().begin();
        em.createNamedQuery("Role.deleteAllRows").executeUpdate();
        em.createNamedQuery("User.deleteAllRows").executeUpdate();
        em.createNamedQuery("Tenant.deleteAllRows").executeUpdate();
        em.createNamedQuery("House.deleteAllRows").executeUpdate();
        em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
        em.getTransaction().commit();

        try {
            em.getTransaction().begin();
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
        } finally {
            em.close();
        }
    }

    @Test
    void getAllRentalsFromUserTest() {
        System.out.println("Get all rental agreements from a user");
        assertEquals(2, facade.getAllRentalsFromUser(tenant1.getUser().getId()).size());
    }


    @Test
    void getHouseByRentalIDTest(){
        System.out.println("Get house by rental id test!");
        HouseDTO houseDTO = new HouseDTO(house1);
        assertEquals(houseDTO,facade.getHouseFromRentalId(rental1.getId()));
    }

}