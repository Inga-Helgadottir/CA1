package facades;

import dtos.HobbyDTO;
import entities.Hobby;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HobbyFacade implements IHobbyFacade{
    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    public HobbyFacade() {
    }

    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public HobbyDTO getHobbyById(int id) {
        EntityManager em = emf.createEntityManager();
        try{
            Hobby rm = em.find(Hobby.class, id);
            return new HobbyDTO(rm);
        }finally {
            em.close();
        }

    }
}
