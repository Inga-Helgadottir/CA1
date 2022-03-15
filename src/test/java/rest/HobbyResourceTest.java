package rest;

import dtos.HobbyDTO;
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
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HobbyResourceTest {
    private static EntityManagerFactory emf;
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api/";
    private static Hobby h1, h2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;

    static HttpServer startServer() {
        System.out.println("Start server");
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        System.out.println("Set up class");
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
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Hobby AUTO_INCREMENT = 1").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            h1 = new Hobby("testHobby name", "1testWiki Link", "1testCategory", "1testType");
            h2 = new Hobby("2testHobby name", "2testWiki Link", "2testCategory", "2testType");
            em.persist(h1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(h2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void getAllHobbies() {
        System.out.println("Testing get all hobbies");
        List<HobbyDTO> hobbyDTOs;

        hobbyDTOs = given()
                .contentType("application/json")
                .when()
                .get("hobby/all")
                .then()
                .extract().body().jsonPath().getList("", HobbyDTO.class);

        HobbyDTO h1DTO = new HobbyDTO(h1);
        HobbyDTO h2DTO = new HobbyDTO(h2);
        assertEquals(hobbyDTOs.get(0), h1DTO);
        assertEquals(hobbyDTOs.get(1), h2DTO);
    }

    @Test
    void getHobbyById() {
        System.out.println("Testing get hobbies by id");
        given()
                .contentType(ContentType.JSON)
                .get("/hobby/{id}",h1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(h1.getId()))
                .body("name", equalTo(h1.getName()))
                .body("wikiLink", equalTo(h1.getWikiLink()))
                .body("category", equalTo(h1.getCategory()))
                .body("type", equalTo(h1.getType()));
    }
}