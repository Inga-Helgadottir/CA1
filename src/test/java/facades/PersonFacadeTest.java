package facades;

import dtos.PersonDTO;
import entities.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static int personsSize;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
        personsSize = 100;//when adding to or removing from Person table
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllUsers() {
        int expected = personsSize;
        int actual = facade.getAllUsers().size();
        assertEquals(expected, actual);
    }

    @Test
    void getUserById() {
        int expected = 1;
        String expected2 = "Leo";
        String expected3 = "Lopez";
        String expected4 = "23271721";
        String expected5 = "et.malesuada@icloud.com";
        PersonDTO actual = facade.getUserById(1);
        assertEquals(expected, actual.getIdPerson());
        assertEquals(expected2, actual.getFirstName());
        assertEquals(expected3, actual.getLastName());
        assertEquals(expected4, actual.getPhoneNumber());
        assertEquals(expected5, actual.getEmail());
    }
}