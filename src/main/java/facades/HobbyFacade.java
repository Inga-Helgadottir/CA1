package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HobbyFacade {
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

}
