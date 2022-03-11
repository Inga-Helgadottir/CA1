package dtos;

import entities.Cityinfo;
import java.util.ArrayList;
import java.util.List;

public class CityinfoDTO {
    private int id;
    private String zipcode;
    private String city;

    public CityinfoDTO(String zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

    public static List<CityinfoDTO> getDtos(List<Cityinfo> rms){
        List<CityinfoDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new CityinfoDTO(rm)));
        return rmdtos;
    }


    public CityinfoDTO(Cityinfo rm) {
        if(rm != null) {
            this.id = rm.getId();
            this.zipcode = rm.getZipcode();
            this.city = rm.getCity();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
