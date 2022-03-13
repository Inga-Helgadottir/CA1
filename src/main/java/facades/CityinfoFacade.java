package facades;

import dtos.CityinfoDTO;
import entities.Cityinfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class CityinfoFacade {

    private static CityinfoFacade instance;
    private static EntityManagerFactory emf;

    public CityinfoFacade() {
    }

    public static CityinfoFacade getCityinfoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityinfoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CityinfoDTO getZipcodeById(int id) {
        EntityManager em = emf.createEntityManager();
        try{
            Cityinfo rm = em.find(Cityinfo.class, id);
            return new CityinfoDTO(rm);
        }finally {
            em.close();
        }
    }

    public List<CityinfoDTO> getAllZipcodes() {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Cityinfo> query = em.createQuery("SELECT c FROM Cityinfo c", Cityinfo.class);
            List<Cityinfo> rms = query.getResultList();
            return CityinfoDTO.getDtos(rms);
        }finally {
            em.close();
        }
    }

}
