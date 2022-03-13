package facades;

import dtos.PersonDTO;
import entities.Person;

import javax.persistence.EntityManager;
import java.util.List;

public interface IPersonFacade {
    List<PersonDTO> getAllUsers();
    PersonDTO getUserById(int id);
    PersonDTO updateUser(Person updatedPerson);
    void deleteUser(int id);
    PersonDTO getUserByZipcode(String zipcode);
    PersonDTO getUserByHobby(String hobby);
}
