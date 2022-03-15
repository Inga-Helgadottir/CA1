package rest;

import dtos.PersonDTO;
import entities.Cityinfo;
import entities.Hobby;
import entities.Person;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonResourceTest {
    private static EntityManagerFactory emf;
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api/";
    private static Person dbp1, dbp2, dbp3, dbp4, dbp5;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    /* TODO:
        getUserById
        getUsersByZipcode
        getUsersByHobby
        updateUser
        addUser
        deleteUser
    */

    static HttpServer startServer() {
        System.out.println("Start server");
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        System.out.println("set up class");
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        System.out.println("Closing test server");
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    void setUp() {
        System.out.println("Setting up the test database");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Person AUTO_INCREMENT = 1").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            dbp1 = new Person("Missy", "Parker", "12345678", "myemail@something.com");
            dbp2 = new Person("Max", "Masters", "14785239", "anemail@something.com");
            dbp3 = new Person("Kelly", "Link", "12345555", "this@gmail.com");
            dbp4 = new Person("Colin", "Lane", "12874678", "haha@mail.com");
            dbp5 = new Person("Patty", "Spencer", "12365878", "hihi@email.com");
            dbp1.setHobby(em.find(Hobby.class, 1));
            dbp2.setHobby(em.find(Hobby.class, 2));
            dbp3.setHobby(em.find(Hobby.class, 55));
            dbp4.setHobby(em.find(Hobby.class, 2));
            dbp5.setHobby(em.find(Hobby.class, 12));
            dbp1.setCityinfo(em.find(Cityinfo.class, 55));
            dbp2.setCityinfo(em.find(Cityinfo.class, 55));
            dbp3.setCityinfo(em.find(Cityinfo.class, 6));
            dbp4.setCityinfo(em.find(Cityinfo.class, 66));
            dbp5.setCityinfo(em.find(Cityinfo.class, 84));

            em.persist(dbp1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(dbp2);
            em.persist(dbp3);
            em.persist(dbp4);
            em.persist(dbp5);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/xxx").then().statusCode(200);
    }

    @Test
    void getAllUsers() {
        System.out.println("Testing to get all persons");
        List<PersonDTO> personDTOs;

        personDTOs = given()
                .contentType("application/json")
                .when()
                .get("users")
                .then()
                .extract().body().jsonPath().getList("", PersonDTO.class);

        PersonDTO p1DTO = new PersonDTO(dbp1);
        PersonDTO p2DTO = new PersonDTO(dbp2);
        assertEquals(personDTOs.get(0), p1DTO);
        assertEquals(personDTOs.get(1), p2DTO);
    }

    @Test
    public void getById()  {
        System.out.println("Testing to get person by id");
       given()
                .contentType(ContentType.JSON)
                .get("/users/{id}",dbp1.getIdPerson())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("idPerson", equalTo(dbp1.getIdPerson()))
                .body("firstName", equalTo(dbp1.getFirstName()))
                .body("lastName", equalTo(dbp1.getLastName()));
    }
    @Test
    void getUsersByZipcode() {
        System.out.println("Testing to get persons by zipcode");
        PersonDTO p = new PersonDTO(dbp1);
        PersonDTO p2 = new PersonDTO(dbp2);
        List<PersonDTO> actualPersonsDTOs = given()
                .contentType("application/json")
                .when()
                .get("users/zipcode/{zipcode}", dbp1.getCityinfo().getZipcode())
                .then()
                .extract().body().jsonPath().getList("", PersonDTO.class);
        assertEquals(actualPersonsDTOs.get(0), p);
        assertEquals(actualPersonsDTOs.get(1), p2);
    }

}