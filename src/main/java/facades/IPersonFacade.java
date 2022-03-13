package facades;

import dtos.PersonDTO;
import entities.Person;
import java.util.List;

public interface IPersonFacade {
    List<PersonDTO> getAllUsers();
    PersonDTO getUserById(int id);
    PersonDTO updateUser(Person updatedPerson);
    void deleteUser(int id);
    PersonDTO getUserByZipcode(String zipcode);
    PersonDTO getUserByHobby(String hobby);
    PersonDTO addUser(PersonDTO newUser);
}
