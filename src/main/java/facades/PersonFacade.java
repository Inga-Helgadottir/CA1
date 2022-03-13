package facades;

import dtos.PersonDTO;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonFacade implements IPersonFacade{

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

    public PersonDTO updateUser(Person updatedPerson) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person p = em.merge(updatedPerson);
            em.getTransaction().commit();
            return new PersonDTO(p);
        }finally {
            em.close();
        }
    }

    public void deleteUser(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null)
            throw new EntityNotFoundException("Could not remove User with id: " + id);
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    @Override
    public PersonDTO getUserByZipcode(String zipcode) {
        return null;
    }

    @Override
    public PersonDTO getUserByHobby(String hobby) {
        return null;
    }

    @Override
    public PersonDTO addUser(PersonDTO newUser) {
        return null;
    }
}
