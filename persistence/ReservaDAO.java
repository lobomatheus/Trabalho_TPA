package persistence;

import model.Reserva;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Calendar;

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

    public static Reserva getReserva(int numQuarto) throws NoResultException {
        Reserva reserva;

        startManager();

        reserva = (Reserva)_manager.createQuery("select r " +
                                                    "from Reserva as r " +
                                                    "where 'quarto_num' = " + numQuarto + " " +
                                                    "order by dataEntrada").getSingleResult();

        closeManagerAndFactory();

        return reserva;
    }

    public static void updateReserva(Reserva reserva){
        startManager();

        _manager.getTransaction().begin();
        _manager.merge(reserva);
        _manager.getTransaction().commit();

        closeManagerAndFactory();
    }

    public static double getReservaByAno(boolean checkin){
        double sum=0.0;

        startManager();

        //Vai retornar o preço total arrecadado
        if(checkin){
            sum = (double)_manager.createQuery("select sum(r.valorPago) " +
                    "from Reserva as r " +
                    "where (year(current_date()) - year(r.dataEntrada)) < 1 " +
                    "and r.checkin = checkin").getSingleResult();

        } else{
            sum = (double)_manager.createQuery("select sum(r.valorTotal-r.valorPago) " +
                    "from Reserva as r " +
                    "where (year(current_date()) - year(r.dataEntrada)) < 1 " +
                    "and r.checkin = checkin").getSingleResult();

        }

        closeManagerAndFactory();

        return sum;
    }

    public static float getReservaByMes(boolean checkin){
        float sum=0.0f;

        startManager();

        // Vai retornar o preço total arrecadado
        if(checkin){
            sum = (float)_manager.createQuery("select sum(r.valorPago) " +
                    "from Reserva as r " +
                    "where (month(current_date()) - month(r.dataEntrada)) < 1 " +
                    "and r.checkin = checkin").getSingleResult();

        } else{
            sum = (float)_manager.createQuery("select sum(r.valorTotal-r.valorPago) " +
                    "from Reserva as r " +
                    "where (month(current_date()) - month(r.dataEntrada)) < 1 " +
                    "and r.checkin = checkin").getSingleResult();

        }

        closeManagerAndFactory();

        return sum;

    }
}
