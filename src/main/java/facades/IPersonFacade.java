package facades;

import dtos.PersonDTO;
import entities.Person;
import java.util.List;

public interface IPersonFacade {
    List<PersonDTO> getAllUsers();
    PersonDTO getUserById(int id);
    PersonDTO updateUser(int id, PersonDTO updatedPerson);
    PersonDTO deleteUser(int id);
    List<PersonDTO> getUsersByZipcode(String zipcode);
    List<PersonDTO> getUsersByHobby(String hobby);
    PersonDTO addUser(PersonDTO newUser);
}
