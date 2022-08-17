package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HouseDTO;
import facades.HouseFacade;
import facades.RentalFacade;
import utils.EMF_Creator;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/house")
public class HouseResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final RentalFacade rentalFacade =  RentalFacade.getFacadeExample(EMF);
    private static final HouseFacade houseFacade = HouseFacade.getHouseFacade(EMF);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response createHouse(String content){
        HouseDTO houseDTO = GSON.fromJson(content, HouseDTO.class);
        HouseDTO newHouseDTO = houseFacade.createHouse(houseDTO);
        return Response.ok().entity(GSON.toJson(newHouseDTO)).build();
    }

}