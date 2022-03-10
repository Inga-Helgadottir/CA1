package dtos;

public class HobbyDTO {
    private int id;
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    public HobbyDTO() {
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
