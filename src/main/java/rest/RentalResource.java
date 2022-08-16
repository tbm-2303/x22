package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RentalDTO;
import errorhandling.UsernameTakenException;
import facades.RentalFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/driver")
public class RentalResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final RentalFacade rentalFacade =  RentalFacade.getFacadeExample(EMF);

    @GET
    @Path("/rentals/{userID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons(@PathParam("userID") int userID) {
        List<RentalDTO> rentalDTOS = rentalFacade.getByUserId(userID);;
        return Response.ok().entity(GSON.toJson(rentalDTOS)).build();
    }
}