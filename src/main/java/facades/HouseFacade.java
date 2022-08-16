package facades;

import dtos.HouseDTO;

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

}
