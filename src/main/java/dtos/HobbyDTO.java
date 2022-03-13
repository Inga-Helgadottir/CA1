package dtos;

import entities.Hobby;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HobbyDTO {
    private int id;
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    public HobbyDTO(String name, String wikiLink, String category, String type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
    }
    public static List<HobbyDTO> getDtos(List<Hobby> rms){
        List<HobbyDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new HobbyDTO(rm)));
        return rmdtos;
    }


    public HobbyDTO(Hobby rm) {
        if(rm != null) {
            this.id = rm.getId();
            this.name = rm.getName();
            this.wikiLink = rm.getWikiLink();
            this.category = rm.getCategory();
            this.type = rm.getType();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HobbyDTO hobbyDTO = (HobbyDTO) o;
        return id == hobbyDTO.id && name.equals(hobbyDTO.name) && wikiLink.equals(hobbyDTO.wikiLink) && category.equals(hobbyDTO.category) && type.equals(hobbyDTO.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, wikiLink, category, type);
    }

    @Override
    public String toString() {
        return "HobbyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wikiLink='" + wikiLink + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
