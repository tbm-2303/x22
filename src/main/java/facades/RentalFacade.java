package facades;

import dtos.HouseDTO;
import dtos.RentalDTO;
import dtos.TenantDTO;
import entities.House;
import entities.Rental;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class RentalFacade {
    private static RentalFacade instance;
    private static EntityManagerFactory emf;


    public static RentalFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RentalFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


//(us1)
    public List<RentalDTO> getAllRentalsFromUser(int userId) {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<RentalDTO> query = em.createQuery("SELECT new dtos.RentalDTO(r) FROM Rental r join Tenant t where t.rentals = r and t.user.id =:userId", RentalDTO.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


    //(us2)
    public HouseDTO getHouseFromRentalId(int rentalId) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<HouseDTO> query = em.createQuery("SELECT new dtos.HouseDTO(h) FROM House h join Rental r where h.rentals = r and r.id=:rentalId", HouseDTO.class);
            query.setParameter("rentalId", rentalId);
            query.setMaxResults(1);
            HouseDTO houseDTO = query.getSingleResult();
            return houseDTO;
        } finally {
            em.close();
        }
    }
//(us3)







    public List<HouseDTO> getAllHouses() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<HouseDTO> query = em.createQuery("SELECT new dtos.HouseDTO(h) FROM House h", HouseDTO.class);
            List<HouseDTO> houseDTOs = query.getResultList();
            return houseDTOs;
        } finally {
            em.close();
        }
    }
    public HouseDTO getHouseByID(int houseID) {
        EntityManager em = getEntityManager();
        try{
            return new HouseDTO(em.find(House.class,houseID));
        } finally {
            em.close();
        }
    }

}