package facades;

import dtos.HobbyDTO;

import java.util.List;

public interface IHobbyFacade {
    HobbyDTO getHobbyById(int id);
    List<HobbyDTO> getAllHobbies();
}
