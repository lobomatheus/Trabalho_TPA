package persistence;

import model.Reserva;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    public static List<Reserva> getReserva() throws NoResultException {
        List<Reserva> reservas;

        startManager();

        reservas = (List<Reserva>)_manager.createQuery("select r from Reserva r").getResultList();

        closeManagerAndFactory();

        return reservas;
    }

    public static Reserva getReserva(int idReserva) throws NoResultException {
        Reserva reserva;

        startManager();

        reserva = (Reserva)_manager.createQuery("select r " +
                                                "from Reserva r " +
                                                "where r.id = " + idReserva).getSingleResult();

        closeManagerAndFactory();

        return reserva;
    }

    public static List<Reserva> getReservaSemCheckin() throws NoResultException {
        List<Reserva> reservas;

        startManager();

        reservas = (List<Reserva>)_manager.createQuery("select r " +
                "from Reserva r " +
                "where r.checkin = false ").getResultList();

        closeManagerAndFactory();

        return reservas;
    }

    public static Reserva getReservaByQuarto(int numQuarto) throws NoResultException {
        Reserva reserva;

        startManager();

        reserva = (Reserva)_manager.createQuery("select r " +
                                                    "from Reserva as r " +
                                                    "where quarto_num = " + numQuarto + " " +
                                                    "order by dataEntrada").getSingleResult();

        closeManagerAndFactory();

        return reserva;
    }

    public static List<Reserva> getReservasByQuarto(int numQuarto) throws NoResultException {
        List<Reserva> reservas;

        startManager();

        reservas = (List<Reserva>)_manager.createQuery("select r " +
                "from Reserva as r " +
                "where quarto_num = " + numQuarto).getResultList();

        closeManagerAndFactory();

        return reservas;
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

    public static void removeReserva(Reserva reserva){

        startManager();

        _manager.getTransaction().begin();
        _manager.remove(_manager.getReference(Reserva.class, reserva.getId()));
        _manager.getTransaction().commit();

        closeManagerAndFactory();
    }

}
