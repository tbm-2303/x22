package facades;

import dtos.HouseDTO;
import entities.House;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class HouseFacade {
    private static HouseFacade instance;
    private static EntityManagerFactory emf;


    public static HouseFacade getHouseFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HouseFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public HouseDTO createHouse(HouseDTO houseDTO) {
        EntityManager em = getEntityManager();
        House house = new House(houseDTO.getAddress(), houseDTO.getCity(), houseDTO.getRooms());
        try{
            em.getTransaction().begin();
            em.persist(house);
            em.getTransaction().commit();
            return new HouseDTO(house);
        } finally {
            em.close();
        }
    }


}
