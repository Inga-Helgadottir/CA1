package dtos;

import entities.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonDTO {
    private int idPerson;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private HobbyDTO hobby;
    private CityinfoDTO cityinfo;

    public PersonDTO(String firstName, String lastName, String phoneNumber, String email, HobbyDTO hobby, CityinfoDTO cityinfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.hobby = hobby;
        this.cityinfo = cityinfo;
    }

    public static List<PersonDTO> getDtos(List<Person> rms){
        List<PersonDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new PersonDTO(rm)));
        return rmdtos;
    }


    public PersonDTO(Person rm) {
        if(rm != null){
            this.idPerson = rm.getIdPerson();
            this.firstName = rm.getFirstName();
            this.lastName = rm.getLastName();
            this.phoneNumber = rm.getPhoneNumber();
            this.email = rm.getEmail();
        }
    }

    public HobbyDTO getHobby() {
        return hobby;
    }

    public void setHobby(HobbyDTO hobby) {
        this.hobby = hobby;
    }

    public CityinfoDTO getCityinfo() {
        return cityinfo;
    }

    public void setCityinfo(CityinfoDTO cityinfo) {
        this.cityinfo = cityinfo;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return idPerson == personDTO.idPerson && firstName.equals(personDTO.firstName) && lastName.equals(personDTO.lastName) && phoneNumber.equals(personDTO.phoneNumber) && email.equals(personDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerson, firstName, lastName, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "idPerson=" + idPerson +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
