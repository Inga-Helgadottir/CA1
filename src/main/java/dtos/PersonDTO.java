package dtos;

public class PersonDTO {
    private int idPerson;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public PersonDTO() {
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
