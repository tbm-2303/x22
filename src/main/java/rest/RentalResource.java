package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HouseDTO;
import dtos.RentalDTO;
import errorhandling.UsernameTakenException;
import facades.HouseFacade;
import facades.RentalFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rental")
public class RentalResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final RentalFacade rentalFacade =  RentalFacade.getFacadeExample(EMF);
    private static final HouseFacade houseFacade = HouseFacade.getHouseFacade(EMF);

    @GET
    @Path("/rentals/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRentalsFromUser(@PathParam("userId") int userId) {
        List<RentalDTO> rentalDTOS = rentalFacade.getByUserId(userId);;
        return Response.ok().entity(GSON.toJson(rentalDTOS)).build();
    }

    @GET
    @Path("/house/{rentalId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHouseByRentalID(@PathParam("rentalId") int rentalId) {
        HouseDTO houseDTO = houseFacade.getHouseFromRentalId(rentalId);
        return Response.ok().entity(GSON.toJson(houseDTO)).build();
    }
}