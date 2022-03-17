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
    Cityinfo c1, c2, c3;
    Hobby h1, h2, h3;

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
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Hobby AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Person AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("Cityinfo.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Cityinfo AUTO_INCREMENT = 1").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            h1 = new Hobby("hName", "hWiki", "hCate", "hType");
            h2 = new Hobby("hName2", "hWiki2", "hCate3", "hType3");
            h3 = new Hobby("hName3", "hWiki2", "hCate3", "hType3");

            c1 = new Cityinfo("1234", "city1");
            c2 = new Cityinfo("4567", "city2");
            c3 = new Cityinfo("8912", "city3");

            p1 = new Person("Missy", "Parker", "12345678", "myemail@something.com");
            p2 = new Person("Max", "Masters", "14785239", "anemail@something.com");
            p3 = new Person("Kelly", "Link", "12345555", "this@gmail.com");
            p4 = new Person("Colin", "Lane", "12874678", "haha@mail.com");
            p5 = new Person("Patty", "Spencer", "12365878", "hihi@email.com");
            p1.setHobby(h1);
            p2.setHobby(h2);
            p3.setHobby(h3);
            p4.setHobby(h1);
            p5.setHobby(h1);
            p1.setCityinfo(c1);
            p2.setCityinfo(c2);
            p3.setCityinfo(c3);
            p4.setCityinfo(c1);
            p5.setCityinfo(c2);
            h1.addPeople(p1);
            h1.addPeople(p3);
            h1.addPeople(p5);
            h2.addPeople(p2);
            h2.addPeople(p4);
            c1.addPeople(p1);
            c1.addPeople(p3);
            c1.addPeople(p5);
            c2.addPeople(p2);
            c2.addPeople(p4);
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(p1);
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
        PersonDTO actual = facade.updateUser(p1.getIdPerson(), expected);
        assertEquals(expected, actual);
    }

    @Test
    void addUser() {
        EntityManager em = emf.createEntityManager();
        System.out.println("Add user");
        Person p = new Person("Kelly", "Parkers", "12345678", "myEmailIsHere@somewhere.com");
        p.setHobby(h1);
        p.setCityinfo(c1);
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
        int expected = 3;
        int actual = facade.getUsersByHobby("hName").size();
        assertEquals(expected, actual);
    }

    @Test
    void getUsersByZipcode() {
        System.out.println("Get users by zipcode");
        int expected = 2;
        int actual = facade.getUsersByZipcode("1234").size();
        assertEquals(expected, actual);
    }

}