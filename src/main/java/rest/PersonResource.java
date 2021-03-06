package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Person;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("users")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok()
                .entity(GSON.toJson(FACADE.getAllUsers()))
                .build();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") int id) throws EntityNotFoundException {
        PersonDTO p = FACADE.getUserById(id);
        return Response.ok().entity(GSON.toJson(p)).build();
    }

    @Path("zipcode/{zipcode}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUsersByZipcode(@PathParam("zipcode") String zipcode) throws EntityNotFoundException {
        List<PersonDTO> p = FACADE.getUsersByZipcode(zipcode);
        return Response.ok().entity(GSON.toJson(p)).build();
    }

    @Path("hobby/{hobby}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByHobby(@PathParam("hobby") String hobby) throws EntityNotFoundException {
        List<PersonDTO> p = FACADE.getUsersByHobby(hobby);
        return Response.ok().entity(GSON.toJson(p)).build();
    }

    @POST
    @Path("/newPerson")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(String jsonContext) {
        PersonDTO pdto = GSON.fromJson(jsonContext, PersonDTO.class);
        PersonDTO newPdto = FACADE.addUser(pdto);
        return Response.ok().entity(GSON.toJson(newPdto)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, String jsonContext)  throws EntityNotFoundException {
        PersonDTO pdto = GSON.fromJson(jsonContext, PersonDTO.class);
        PersonDTO updated = FACADE.updateUser(id, pdto);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id) throws EntityNotFoundException {
        PersonDTO deleted = FACADE.deleteUser(id);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }
}
