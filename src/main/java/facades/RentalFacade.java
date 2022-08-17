package facades;

import dtos.HouseDTO;
import dtos.RentalDTO;
import dtos.TenantDTO;
import edu.emory.mathcs.backport.java.util.Arrays;
import entities.House;
import entities.Rental;
import entities.Tenant;

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
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
//(us3)

    public List<TenantDTO> getAllTenantsFromHouse(int houseID)  {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r where r.house.id=:houseID", Rental.class);
            query.setParameter("houseID", houseID);
            List<Rental> rentals = query.getResultList();

            int tempid = 0;
            for (Rental rental : rentals) {
                tempid = rental.getId();
            }
            TypedQuery<TenantDTO> tq = em.createQuery("SELECT new dtos.TenantDTO(t) FROM Tenant t join Rental r where t.rentals = r and r.id=:rentalID", TenantDTO.class);
            tq.setParameter("rentalID", tempid);
            return tq.getResultList();
        } finally {
            em.close();
        }
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
    public HouseDTO getHouseByID(int houseID) {
        EntityManager em = getEntityManager();
        try{
            return new HouseDTO(em.find(House.class,houseID));
        } finally {
            em.close();
        }
    }



    public RentalDTO deleteRental(int rentalID) {
        EntityManager em = getEntityManager();
        Rental rental = em.find(Rental.class, rentalID);
        try {
            em.getTransaction().begin();
            em.remove(rental);
            em.getTransaction().commit();
            return new RentalDTO(rental);
        } finally {
            em.close();
        }
    }


    public RentalDTO createRental(RentalDTO rentalDTO)  {
        EntityManager em = getEntityManager();
        Rental newRental = new Rental(rentalDTO.getStartDate(), rentalDTO.getEndDate(), rentalDTO.getPriceAnnual(), rentalDTO.getDeposit(), rentalDTO.getContactPerson());
        try{
            House house = em.find(House.class,rentalDTO.getHouseId());
            Tenant tenant = em.find(Tenant.class,rentalDTO.getTenantId());
            newRental.setHouse(house);
            newRental.addTenant(tenant);
            em.getTransaction().begin();
            em.persist(newRental);
            em.getTransaction().commit();
            return new RentalDTO(newRental);
        } finally {
            em.close();
        }
    }
    public RentalDTO updateRentalInfo(RentalDTO rentalDTO) {
        EntityManager em = getEntityManager();
        Rental rental = em.find(Rental.class, rentalDTO.getId());
        rental.setStartDate(rentalDTO.getStartDate());
        rental.setEndDate(rentalDTO.getEndDate());
        rental.setPriceAnnual(rentalDTO.getPriceAnnual());
        rental.setContactPerson(rentalDTO.getContactPerson());

        rental.setDeposit(rentalDTO.getDeposit());
        try {
            em.getTransaction().begin();
            em.merge(rental);
            em.getTransaction().commit();
            return new RentalDTO(rental);
        } finally {
            em.close();
        }
    }

    public RentalDTO setHouse(int rentalID, int houseID) {
        EntityManager em = getEntityManager();
        Rental rental = em.find(Rental.class, rentalID);
        House house = em.find(House.class, houseID);
        house.addRental(rental);
        try {
            em.getTransaction().begin();
            em.merge(house);
            em.getTransaction().commit();
            return new RentalDTO(rental);
        } finally {
            em.close();
        }
    }

    public RentalDTO addTenantToRental(int rentalID, int tenantID) {
        EntityManager em = getEntityManager();
        Rental rental = em.find(Rental.class, rentalID);
        Tenant tenant = em.find(Tenant.class, tenantID);
        rental.addTenant(tenant);
        try {
            em.getTransaction().begin();
            em.merge(rental);
            em.getTransaction().commit();
            return new RentalDTO(rental);
        } finally {
            em.close();
        }
    }

    public RentalDTO removeTenantFromRental(int rentalID, int tenantID) {
        EntityManager em = getEntityManager();
        Rental rental = em.find(Rental.class, rentalID);
        Tenant tenant = em.find(Tenant.class, tenantID);
        rental.removeTenant(tenant);
        try {
            em.getTransaction().begin();
            em.merge(rental);
            em.getTransaction().commit();
            return new RentalDTO(rental);
        } finally {
            em.close();
        }
    }

}