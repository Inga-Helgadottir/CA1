package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Person;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.List;
    /* TODO:
        getUsersByZipcode
        getUsersByHobby
        updateUser
        addUser
        deleteUser
    */

@Path("users")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(String jsonContext) {
        Person p = GSON.fromJson(jsonContext, Person.class);
        Person addP = new Person(p.getFirstName(), p.getLastName(), p.getPhoneNumber(), p.getEmail());
        return Response
                .ok("SUCCESS")
                .cookie(new NewCookie("test", p.getFirstName()))
                .entity(GSON.toJson(FACADE.addUser(addP)))
                .build();
    }

    @Path("zipcode/{zipcode}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PersonDTO> getUsersByZipcode(@PathParam("zipcode") String zipcode) throws EntityNotFoundException {
        List<PersonDTO> p = FACADE.getUsersByZipcode(zipcode);
        return p;
    }

}
