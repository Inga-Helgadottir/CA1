package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityinfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import facades.CityinfoFacade;
import facades.HobbyFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("cityinfo")
public class CityinfoResource {
    /* TODO:
        getAllZipcodes
        getZipcodeById
    */

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final CityinfoFacade FACADE =  CityinfoFacade.getCityinfoFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllZipcodes() {
        return Response.ok()
                .entity(GSON.toJson(FACADE.getAllZipcodes()))
                .build();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") int id) throws EntityNotFoundException {
        CityinfoDTO p = FACADE.getZipcodeById(id);
        return Response.ok().entity(GSON.toJson(p)).build();
    }
}
