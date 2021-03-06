package facades;

import dtos.PersonDTO;
import entities.Cityinfo;
import entities.Hobby;
import entities.Person;

import javax.persistence.*;
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

    public PersonDTO updateUser(int id, PersonDTO updatedPerson) {
        EntityManager em = emf.createEntityManager();
        try {
            Person p = em.find(Person.class, id);
            p.setFirstName(updatedPerson.getFirstName());
            p.setLastName(updatedPerson.getLastName());
            Cityinfo c = em.find(Cityinfo.class, updatedPerson.getCityinfo().getId());
            Hobby h = em.find(Hobby.class, updatedPerson.getHobby().getId());
            if(c == null){
                c = new Cityinfo(updatedPerson.getCityinfo().getZipcode(), updatedPerson.getCityinfo().getCity());
            }
            if(h == null){
                h = new Hobby(updatedPerson.getHobby().getName(), updatedPerson.getHobby().getWikiLink(), updatedPerson.getHobby().getCategory(), updatedPerson.getHobby().getType());
            }
            c.addPeople(p);
            h.addPeople(p);
            p.setCityinfo(c);
            p.setHobby(h);
            em.getTransaction().begin();
            Person p2 = em.merge(p);
            em.getTransaction().commit();
            return new PersonDTO(p2);
        }finally {
            em.close();
        }
    }

    public PersonDTO deleteUser(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        try{
            Person p = em.find(Person.class, id);
            if (p == null)
                throw new EntityNotFoundException("Could not remove User with id: " + id);
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
            return new PersonDTO(p);
        }finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getUsersByZipcode(String zipcode) {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.cityinfo.zipcode = :zipcode", Person.class);
            query.setParameter("zipcode", zipcode);
            List<Person> rms = query.getResultList();
            return PersonDTO.getDtos(rms);
        }finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getUsersByHobby(String hobby) {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.hobby.name = :hobbyName", Person.class);
            query.setParameter("hobbyName", hobby);
            List<Person> rms = query.getResultList();
            return PersonDTO.getDtos(rms);
        }finally {
            em.close();
        }
    }

    @Override
    public PersonDTO addUser(PersonDTO newUser) {
        EntityManager em = emf.createEntityManager();
        try{
            Person p = new Person(newUser.getFirstName(), newUser.getLastName(), newUser.getPhoneNumber(), newUser.getEmail());

            Cityinfo c = em.find(Cityinfo.class, newUser.getCityinfo().getId());
            Hobby h = em.find(Hobby.class, newUser.getHobby().getId());
            if(c == null){
                c = new Cityinfo(newUser.getCityinfo().getZipcode(), newUser.getCityinfo().getCity());
            }
            if(h == null){
                h = new Hobby(newUser.getHobby().getName(), newUser.getHobby().getWikiLink(), newUser.getHobby().getCategory(), newUser.getHobby().getType());
            }
            c.addPeople(p);
            h.addPeople(p);
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return new PersonDTO(p);
        }finally {
            em.close();
        }
    }
}
