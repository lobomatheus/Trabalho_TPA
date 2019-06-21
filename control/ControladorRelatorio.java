package control;

import model.Reserva;
import persistence.ReservaDAO;

import java.util.ArrayList;
import java.util.Calendar;

public class ControladorRelatorio {

    public static ControladorRelatorio controladorRelatorio = null;

    private ControladorRelatorio(){

    }

    public static ControladorRelatorio getInstance(){
        if(controladorRelatorio==null){
            controladorRelatorio = new ControladorRelatorio();
        }
        return controladorRelatorio;
    }

    private void VerificarCheckins(){
        // Esse método irá verificar se existe alguma reserva cujo checkin não foi feito para desconsiderar no cálculo.
    }

    public double RelatorioAnual(boolean checkin){
        double soma = ReservaDAO.getReservaByAno(checkin);

        return soma;
    }

    public float RelatorioMensal(boolean checkin){
        float soma = ReservaDAO.getReservaByMes(checkin);

        return soma;
    }
}
