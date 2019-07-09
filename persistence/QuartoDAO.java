package persistence;

import model.Quarto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class QuartoDAO {

    private static EntityManagerFactory _factory;
    private static EntityManager _manager;

    private static void startManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("quartos");
        _factory = factory;
        EntityManager manager = factory.createEntityManager();
        _manager = manager;
    }

    private static void closeManagerAndFactory(){
        _manager.close();
        _factory.close();
    }

    public static List<Quarto> getQuartos(){
        List<Quarto> quartos;

        startManager();

        quartos = (List<Quarto>)_manager.createQuery("select q from Quarto as q").getResultList();

        closeManagerAndFactory();

        return quartos;
    }

    public static Quarto getQuarto(int id){
        Quarto quarto;

        startManager();

        quarto = (Quarto)_manager.createQuery("select q from Quarto as q " +
                                                 "where q.num = " + id).getSingleResult();

        closeManagerAndFactory();

        return quarto;
    }
}
