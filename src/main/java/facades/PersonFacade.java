package facades;

import dtos.PersonDTO;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    public PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<PersonDTO> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> rms = query.getResultList();
            return PersonDTO.getDtos(rms);
        }finally {
            em.close();
        }
    }

    public PersonDTO getUserById(int id) {
        EntityManager em = emf.createEntityManager();
        try{
            Person rm = em.find(Person.class, id);
            return new PersonDTO(rm);
        }finally {
            em.close();
        }
    }

    public PersonDTO updateUser(PersonDTO updatedPerson) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person rm = em.find(Person.class, updatedPerson.getIdPerson());
            em.persist(rm);
            em.getTransaction().commit();
            return updatedPerson;
        }finally {
            em.close();
        }
    }
}
