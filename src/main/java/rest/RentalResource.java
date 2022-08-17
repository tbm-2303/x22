package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HouseDTO;
import dtos.RentalDTO;
import dtos.TenantDTO;
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
    @Path("/getAll")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllRentals() {
        List<RentalDTO> rentalDTOS = rentalFacade.getAllRentals();
        return Response.ok().entity(GSON.toJson(rentalDTOS)).build();
    }
    @GET
    @Path("/getAllHouses")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllHouses() {
        List<HouseDTO> houseDTOS = rentalFacade.getAllHouses();
        return Response.ok().entity(GSON.toJson(houseDTOS)).build();
    }
    //(us1)
    @GET
    @Path("/getAllRentals/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRentalsFromUser(@PathParam("userId") int userId) {
        List<RentalDTO> rentalDTOS = rentalFacade.getAllRentalsFromUser(userId);;
        return Response.ok().entity(GSON.toJson(rentalDTOS)).build();
    }
    //(us2)
    @GET
    @Path("/getHouse/{rentalId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHouseByRentalID(@PathParam("rentalId") int rentalId) {
        HouseDTO houseDTO = rentalFacade.getHouseFromRentalId(rentalId);
        return Response.ok().entity(GSON.toJson(houseDTO)).build();
    }

    @GET
    @Path("/getTenants/{houseID}")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCurrentTenants(@PathParam("houseID") int houseID)  {
        List<TenantDTO> tenantDTOs = rentalFacade.getAllTenantsFromHouse(houseID);
        return Response.ok().entity(GSON.toJson(tenantDTOs)).build();
    }

    @POST
    @Path("/createRental")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response createRental(String content)  {
        RentalDTO rentalDTO = GSON.fromJson(content, RentalDTO.class);
        RentalDTO newRentalDTO = rentalFacade.createRental(rentalDTO);
        return Response.ok().entity(GSON.toJson(newRentalDTO)).build();
    }

    @PUT
    @Path("/updateinfo")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response updateRentalInfo(String content){
        RentalDTO rentalDTO = GSON.fromJson(content, RentalDTO.class);
        RentalDTO newRentalDTO = rentalFacade.updateRentalInfo(rentalDTO);
        return Response.ok().entity(GSON.toJson(newRentalDTO)).build();
    }


    @PUT
    @Path("/changehouse/{rentalId}/{houseId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response setHouse(@PathParam("rentalId") int rentalId,@PathParam("houseId") int houseId){
        RentalDTO newRentalDTO = rentalFacade.setHouse(rentalId,houseId);
        return Response.ok().entity(GSON.toJson(newRentalDTO)).build();
    }

    @PUT
    @Path("/addtenant/{rentalId}/{tenantId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response AddTenantToRental(@PathParam("rentalId") int rentalId,@PathParam("tenantId") int tenantId){
        RentalDTO newRentalDTO = rentalFacade.addTenantToRental(rentalId,tenantId);
        return Response.ok().entity(GSON.toJson(newRentalDTO)).build();
    }

    @PUT
    @Path("/removetenant/{rentalId}/{tenantId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response removeTenantFromRental(@PathParam("rentalId") int rentalId,@PathParam("tenantId") int tenantId){
        RentalDTO newRentalDTO = rentalFacade.removeTenantFromRental(rentalId,tenantId);
        return Response.ok().entity(GSON.toJson(newRentalDTO)).build();
    }

    @DELETE
    @Path("/delete/{rentalID}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response deleteRentalByID(@PathParam("rentalID") int rentalID) {
        RentalDTO deleted = rentalFacade.deleteRental(rentalID);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }

}