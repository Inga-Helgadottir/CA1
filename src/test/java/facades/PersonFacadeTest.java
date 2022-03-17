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
    }

    @BeforeEach
    void setUp() {
        pft.personsSize = 5;
        System.out.println("Setting up the test database");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Person AUTO_INCREMENT = 1").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            p1 = new Person("Missy", "Parker", "12345678", "myemail@something.com");
            p2 = new Person("Max", "Masters", "14785239", "anemail@something.com");
            p3 = new Person("Kelly", "Link", "12345555", "this@gmail.com");
            p4 = new Person("Colin", "Lane", "12874678", "haha@mail.com");
            p5 = new Person("Patty", "Spencer", "12365878", "hihi@email.com");
            p1.setHobby(em.find(Hobby.class, 1));
            p2.setHobby(em.find(Hobby.class, 2));
            p3.setHobby(em.find(Hobby.class, 1));
            p4.setHobby(em.find(Hobby.class, 2));
            p5.setHobby(em.find(Hobby.class, 1));
            p1.setCityinfo(em.find(Cityinfo.class, 1));
            p2.setCityinfo(em.find(Cityinfo.class, 2));
            p3.setCityinfo(em.find(Cityinfo.class, 1));
            p4.setCityinfo(em.find(Cityinfo.class, 2));
            p5.setCityinfo(em.find(Cityinfo.class, 1));

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
        System.out.println("Get all users");
        int expected = pft.personsSize;
        int actual = facade.getAllUsers().size();
        assertEquals(expected, actual);
    }

    @Test
    void getUserById() {
        System.out.println("Get user by id");
        PersonDTO expected = new PersonDTO(p1);
        PersonDTO actual = facade.getUserById(1);
        assertEquals(expected, actual);
    }

    @Test
    void updateUser() {
        System.out.println("Update user");
        p1.setFirstName("TestName");
        p1.setLastName("Tester");
        PersonDTO expected = new PersonDTO(p1);
        PersonDTO actual = facade.updateUser(p1);
        assertEquals(expected, actual);
    }

    @Test
    void addUser() {
        EntityManager em = emf.createEntityManager();
        System.out.println("Add user");
        Person p = new Person("Kelly", "Parkers", "12345678", "myEmailIsHere@somewhere.com");
        p.setHobby(em.find(Hobby.class, 1));
        p.setCityinfo(em.find(Cityinfo.class, 1));
        pft.personsSize = pft.personsSize + 1;
        p.setIdPerson(pft.personsSize);
        PersonDTO expected = new PersonDTO(p);
        PersonDTO actual = facade.addUser(expected);
        assertEquals(expected, actual);
    }

    @Test
    void deleteUser() {
        System.out.println("Delete user");
        facade.deleteUser(2);
        pft.personsSize = pft.personsSize - 1;
        int expected = pft.personsSize;
        int actual = facade.getAllUsers().size();
        assertEquals(expected, actual);
    }

    @Test
    void getUsersByHobby() {
        System.out.println("Get users by hobby");
        int expected = 2;
        int actual = facade.getUsersByHobby("Akrobatik").size();
        assertEquals(expected, actual);
    }

    @Test
    void getUsersByZipcode() {
        System.out.println("Get users by zipcode");
        int expected = 2;
        int actual = facade.getUsersByZipcode("1060").size();
        assertEquals(expected, actual);
    }

}