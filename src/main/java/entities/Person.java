package entities;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person p")
@NamedQuery(name = "Person.size", query = "SELECT COUNT(p) FROM Person p")
public class Person {

    @Id
    @Column(name = "idPerson")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPerson;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    @ManyToOne
    @JoinColumn(name = "HOBBY_id", referencedColumnName = "id", nullable = false)
    private Hobby hobby;

    @ManyToOne
    @JoinColumn(name = "CITYINFO_id", referencedColumnName = "id", nullable = false)
    private Cityinfo cityinfo;

    public Person() {
    }

    public Person(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Person(int idPerson, String firstName, String lastName, String phoneNumber, String email) {
        this.idPerson = idPerson;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    @Basic
    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public Cityinfo getCityinfo() {
        return cityinfo;
    }

    public void setCityinfo(Cityinfo cityinfo) {
        this.cityinfo = cityinfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (idPerson != person.idPerson) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(person.phoneNumber) : person.phoneNumber != null) return false;
        if (email != null ? !email.equals(person.email) : person.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPerson;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
