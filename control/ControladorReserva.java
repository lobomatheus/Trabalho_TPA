package control;

import model.Quarto;
import model.Reserva;
import persistence.ReservaDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Calendar;

public class ControladorReserva {

    public static void FazerReserva(Calendar data, int numDias, boolean comCafe, int idQuarto){

        // Quando o DAO de buscar quarto estiver funcional, substituir aqui
        // Caso o quarto não exista, lançar exceção
        // Caso o quarto já esteja reservado, verificar se o checkin foi feito dentro de 24h, caso negativo,
        // excluir a reserva e reservar o quarto.
        // Se o quarto estiver sido reservado para o período selecionado, não realizar a reserva

        // O código abaixo chama o hibernate pra cadastrar o quarto, como teste, já que o quarto não está
        // salvo ainda no banco, para evitar erros.
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("quartos");
        EntityManager manager = factory.createEntityManager();
        //------------------------------------------------------------------------------------------------

        Quarto quarto = manager.find(Quarto.class, idQuarto);

        // O código abaixo chama o hibernate pra cadastrar o quarto, como teste, já que o quarto não está
        // salvo ainda no banco, para evitar erros.

        manager.close();
        factory.close();
        //------------------------------------------------------------------------------------------------

        Reserva reserva = new Reserva(data, numDias, comCafe, quarto);

        ReservaDAO.cadastrarReserva(reserva);

    }

    public static void FazerCheckin(int numQuarto) throws NoResultException{

        Reserva reserva = ReservaDAO.getReserva(numQuarto);

        if(!reserva.isCheckin()){
            // throws checkin já feito
        }

        reserva.doCheckin();

        ReservaDAO.updateReserva(reserva);
    }

    // Não sei se esse método aqui é necessário
    public static boolean verificarCheckin(int numQuarto){

        Reserva reserva = ReservaDAO.getReserva(numQuarto);

        return reserva.isCheckin();
    }
}
