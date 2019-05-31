package control;

import model.Reserva;
import persistence.ReservaDAO;

import java.util.ArrayList;
import java.util.Calendar;

public class ControladorRelatorio {

    private static void VerificarCheckins(){
        // Esse método irá verificar se existe alguma reserva cujo checkin não foi feito para desconsiderar no cálculo.
    }

    public static double RelatorioAnual(boolean checkin){
        double soma = ReservaDAO.getReservaByAno(checkin);

        return soma;
    }

    public static float RelatorioMensal(boolean checkin){
        float soma = ReservaDAO.getReservaByMes(checkin);

        return soma;
    }
}
