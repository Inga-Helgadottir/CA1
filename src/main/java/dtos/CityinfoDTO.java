package dtos;

public class CityinfoDTO {
    private int id;
    private String zipcode;
    private String city;

    public CityinfoDTO() {
    }

    @Override
    public String toString() {
        return "CityinfoDTO{" +
                "id=" + id +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
