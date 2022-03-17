package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Cityinfo.deleteAllRows", query = "DELETE from Cityinfo c")
public class Cityinfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String zipcode;
    private String city;
    @OneToMany(mappedBy = "cityinfo")
    private List<Person> people = new ArrayList<>();

    public Cityinfo() {
    }

    public Cityinfo(String zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "zipcode")
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void addPeople(Person p) {
        this.people.add(p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cityinfo cityinfo = (Cityinfo) o;

        if (id != cityinfo.id) return false;
        if (zipcode != null ? !zipcode.equals(cityinfo.zipcode) : cityinfo.zipcode != null) return false;
        if (city != null ? !city.equals(cityinfo.city) : cityinfo.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
