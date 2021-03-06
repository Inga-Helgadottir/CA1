package facades;

import dtos.CityinfoDTO;
import entities.Cityinfo;
import entities.Hobby;
import entities.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class CityinfoFacadeTest {

    private static EntityManagerFactory emf;
    private static CityinfoFacade facade;
    private static Cityinfo c1, c2, c3;


    public CityinfoFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CityinfoFacade.getCityinfoFacade(emf);
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
            c1 = new Cityinfo("1234", "city1");
            c2 = new Cityinfo("4567", "city2");
            c3 = new Cityinfo("8912", "city3");
            c1.addPeople(em.find(Person.class, 1));
            c2.addPeople(em.find(Person.class, 1));
            c3.addPeople(em.find(Person.class, 1));
            em.persist(c1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(c2);
            em.persist(c3);
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
        int expected = 3;
        int actual = facade.getAllZipcodes().size();
        assertEquals(expected, actual);
    }

    @Test
    void getCityinfoById() {
        int expected = 1;
        String expected2 = "1234";
        String expected3 = "city1";
        CityinfoDTO actual = facade.getZipcodeById(1);
        assertEquals(expected, actual.getId());
        assertEquals(expected2, actual.getZipcode());
        assertEquals(expected3, actual.getCity());
    }
}