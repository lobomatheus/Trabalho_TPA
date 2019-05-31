package control;

import model.Quarto;
import model.Reserva;

import java.util.Calendar;

public class ControladorReserva {

    public static void FazerReserva(Calendar data, int numDias, boolean comCafe, int idQuarto){

        // Quando o DAO de buscar quarto estiver funcional, substituir aqui
        Quarto quarto = new Quarto(1, 1, 1, true, true, true);

        Reserva reserva = new Reserva(data, numDias, comCafe, quarto);


    }
}
