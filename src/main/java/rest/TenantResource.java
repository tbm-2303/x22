package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.TenantDTO;
import errorhandling.UsernameTakenException;
import facades.HouseFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Path("/tenant")
public class TenantResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final UserFacade userFacade = UserFacade.getUserFacade(EMF);
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response createUser(String content) throws UsernameTakenException {
        TenantDTO tenantDTO = GSON.fromJson(content, TenantDTO.class);
        System.out.println(tenantDTO);
        TenantDTO newTenantDTO = userFacade.createUser(tenantDTO);
        return Response.ok().entity(GSON.toJson(newTenantDTO)).build();
    }

}