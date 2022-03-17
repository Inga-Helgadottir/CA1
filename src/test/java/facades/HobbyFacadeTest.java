package facades;

import dtos.HobbyDTO;
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

class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;
    private static Hobby h1, h2, h3;

    public HobbyFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HobbyFacade.getHobbyFacade(emf);
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
            h1 = new Hobby("hName", "hWiki", "hCate", "hType");
            h2 = new Hobby("hName2", "hWiki2", "hCate3", "hType3");
            h3 = new Hobby("hName3", "hWiki2", "hCate3", "hType3");
            h1.addPeople(em.find(Person.class, 1));
            h2.addPeople(em.find(Person.class, 1));
            h3.addPeople(em.find(Person.class, 1));
            em.persist(h1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(h2);
            em.persist(h3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getHobbyById() {
        int expected = 1;
        String expected2 = "hName";
        String expected3 = "hWiki";
        String expected4 = "hCate";
        String expected5 = "hType";
        HobbyDTO actual = facade.getHobbyById(1);
        assertEquals(expected, actual.getId());
        assertEquals(expected2, actual.getName());
        assertEquals(expected3, actual.getWikiLink());
        assertEquals(expected4, actual.getCategory());
        assertEquals(expected5, actual.getType());
    }

    @Test
    void getAllUsers() {
        System.out.println("Get all users with hobby");
        int expected = 3;
        int actual = facade.getAllHobbies().size();
        assertEquals(expected, actual);
    }
}