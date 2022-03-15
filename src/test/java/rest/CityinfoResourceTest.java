package rest;

import dtos.CityinfoDTO;
import dtos.PersonDTO;
import entities.Cityinfo;
import entities.Hobby;
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
import static org.junit.jupiter.api.Assertions.*;

class CityinfoResourceTest {
    private static EntityManagerFactory emf;
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api/";
    private static Cityinfo c1, c2;

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
            em.createNamedQuery("Cityinfo.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Cityinfo AUTO_INCREMENT = 1").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            c1 = new Cityinfo("1234", "1testCity");
            c2 = new Cityinfo("5678", "2testCity");
            em.persist(c1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(c2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllZipcodes() {
        System.out.println("Testing get all zipcodes");
        List<CityinfoDTO> cityinfoDTOS;

        cityinfoDTOS = given()
                .contentType("application/json")
                .when()
                .get("cityinfo/all")
                .then()
                .extract().body().jsonPath().getList("", CityinfoDTO.class);

        CityinfoDTO c1DTO = new CityinfoDTO(c1);
        CityinfoDTO c2DTO = new CityinfoDTO(c2);
        assertEquals(cityinfoDTOS.get(0), c1DTO);
        assertEquals(cityinfoDTOS.get(1), c2DTO);
    }
    @Test
    public void getById()  {
        System.out.println("Testing get zipcode by id");
        given()
                .contentType(ContentType.JSON)
                .get("/cityinfo/{id}",c1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(c1.getId()))
                .body("zipcode", equalTo(c1.getZipcode()))
                .body("city", equalTo(c1.getCity()));
    }
}