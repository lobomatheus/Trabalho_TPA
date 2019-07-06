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

    public double RelatorioAnual(boolean checkin){
        double soma = ReservaDAO.getReservaByAno(checkin);

        return soma;
    }

    public float RelatorioMensal(boolean checkin){
        float soma = ReservaDAO.getReservaByMes(checkin);

        return soma;
    }
}
