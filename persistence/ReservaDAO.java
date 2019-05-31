package persistence;

import model.Reserva;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ReservaDAO {

    private static EntityManagerFactory _factory;
    private static EntityManager _manager;

    private static void startManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("reserva");
        _factory = factory;
        EntityManager manager = factory.createEntityManager();
        _manager = manager;
    }

    private static void closeManagerAndFactory(){
        _manager.close();
        _factory.close();
    }

    public static void cadastrarReserva(Reserva reserva){

        startManager();

        _manager.getTransaction().begin();
        _manager.persist(reserva);
        _manager.getTransaction().commit();

        closeManagerAndFactory();
    }

    public static Reserva getReserva(int numQuarto){
        Reserva reserva;

        startManager();

        reserva = (Reserva)_manager.createQuery("(select r from Reserva as r where quarto_num = )" + numQuarto + ";").getSingleResult();

        closeManagerAndFactory();

        return reserva;
    }
}
