/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.RenameMeDTO;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {


    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);
        fe.create(new RenameMeDTO(new RenameMe("First 1", "Last 1")));
        fe.create(new RenameMeDTO(new RenameMe("First 2", "Last 2")));
        fe.create(new RenameMeDTO(new RenameMe("First 3", "Last 3")));
    }
    public static void populate2(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    }

// mine relationer virker
    public static void populate3(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
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


    }
    
    public static void main(String[] args) {
        //populate();
       // populate2();
        populate3();
    }
}
