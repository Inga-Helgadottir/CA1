package facades;

import dtos.PersonDTO;
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

class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static PersonFacadeTest pft = new PersonFacadeTest();
    private int personsSize;
    Person p1, p2, p3, p4, p5;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
        pft.personsSize = 5;
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Person AUTO_INCREMENT = 1").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            p1 = new Person(1, "Missy", "Parker", "12345678", "myemail@something.com");
            p2 = new Person(2, "Max", "Masters", "14785239", "anemail@something.com");
            p3 = new Person(3, "Kelly", "Link", "12345555", "this@gmail.com");
            p4 = new Person(4, "Colin", "Lane", "12874678", "haha@mail.com");
            p5 = new Person(5, "Patty", "Spencer", "12365878", "hihi@email.com");
            p1.setHobby(em.find(Hobby.class, 1));
            p2.setHobby(em.find(Hobby.class, 20));
            p3.setHobby(em.find(Hobby.class, 55));
            p4.setHobby(em.find(Hobby.class, 21));
            p5.setHobby(em.find(Hobby.class, 12));
            p1.setCityinfo(em.find(Cityinfo.class, 55));
            p2.setCityinfo(em.find(Cityinfo.class, 6));
            p3.setCityinfo(em.find(Cityinfo.class, 10));
            p4.setCityinfo(em.find(Cityinfo.class, 66));
            p5.setCityinfo(em.find(Cityinfo.class, 84));

            em.persist(p1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllUsers() {
        int expected = pft.personsSize;
        int actual = facade.getAllUsers().size();
        assertEquals(expected, actual);
    }

    @Test
    void getUserById() {
        PersonDTO expected = new PersonDTO(p1);
        PersonDTO actual = facade.getUserById(1);
        assertEquals(expected, actual);
    }

    @Test
    void updateUser() {
        p1.setFirstName("TestName");
        p1.setLastName("Tester");
        PersonDTO expected = new PersonDTO(p1);
        PersonDTO actual = facade.updateUser(p1);
        assertEquals(expected, actual);
    }

    @Test
    void deleteUser() {
        facade.deleteUser(2);
        pft.personsSize = pft.personsSize - 1;
        int expected = pft.personsSize;
        int actual = facade.getAllUsers().size();
        assertEquals(expected, actual);
    }

}