package facades;

import dtos.HobbyDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;

    public HobbyFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HobbyFacade.getHobbyFacade(emf);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getHobbyById() {
        int expected = 1;
        String expected2 = "3D-udskrivning";
        String expected3 = "https://en.wikipedia.org/wiki/3D_printing";
        String expected4 = "Generel";
        String expected5 = "Indendørs";
        HobbyDTO actual = facade.getHobbyById(1);
        assertEquals(expected, actual.getId());
        assertEquals(expected2, actual.getName());
        assertEquals(expected3, actual.getWikiLink());
        assertEquals(expected4, actual.getCategory());
        assertEquals(expected5, actual.getType());
    }
}