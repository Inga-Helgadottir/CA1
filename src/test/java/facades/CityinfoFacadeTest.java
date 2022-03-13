package facades;

import dtos.CityinfoDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class CityinfoFacadeTest {

    private static EntityManagerFactory emf;
    private static CityinfoFacade facade;


    public CityinfoFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CityinfoFacade.getCityinfoFacade(emf);
    }
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllZipcodes() {
        int expected = 1352;
        int actual = facade.getAllZipcodes().size();
        assertEquals(expected, actual);
    }

    @Test
    void getCityinfoById() {
        int expected = 1;
        String expected2 = "0555";
        String expected3 = "Scanning";
        CityinfoDTO actual = facade.getZipcodeById(1);
        assertEquals(expected, actual.getId());
        assertEquals(expected2, actual.getZipcode());
        assertEquals(expected3, actual.getCity());
    }
}