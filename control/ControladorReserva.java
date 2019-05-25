package control;

import model.Reserva;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Calendar;

public class ControladorReserva {

    public static void FazerReserva(Calendar data, int numDias, boolean comCafe, Long idQuarto){
        Reserva reserva = new Reserva(data, numDias, comCafe);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("reserva");
        EntityManager manager = factory.createEntityManager();

        manager.getTransaction().begin();
        manager.persist(reserva);
        manager.getTransaction().commit();

        manager.close();
        factory.close();
    }
}
