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

    public double RelatorioAnual(boolean futuro){
        double soma = ReservaDAO.getReservaByAno(futuro);

        return soma;
    }

    public double RelatorioMensal(boolean futuro){
        double soma = ReservaDAO.getReservaByMes(futuro);

        return soma;
    }
}
