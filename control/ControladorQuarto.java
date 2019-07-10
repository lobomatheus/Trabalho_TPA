package control;

import model.Quarto;
import persistence.QuartoDAO;

import java.util.List;

public class ControladorQuarto {
    private static ControladorQuarto controlador = null;

    private ControladorQuarto() {
    }

    public static ControladorQuarto getInstance(){
        if(controlador == null){
            controlador = new ControladorQuarto();
        }
        return controlador;
    }
    
    public void CadastrarQuarto(int num, int camas_solt, int camas_cas, boolean banheiro, boolean internet, boolean cabo){
        Quarto q = new Quarto(num,camas_solt, camas_cas, banheiro, internet, cabo);
        QuartoDAO.cadastrarQuarto(q);
    }

    public void AlterarQuarto(int num, int camas_solt, int camas_cas, boolean banheiro, boolean internet, boolean cabo){
        Quarto q = new Quarto(num,camas_solt, camas_cas, banheiro, internet, cabo);
        QuartoDAO.updateQuarto(q);
    }

    public void ExcluirQuarto(int num){
        Quarto q = QuartoDAO.getQuarto(num);
        QuartoDAO.removeQuarto(q);
    }

    public List<Quarto> getQuartos(){
        List<Quarto> quartos;
        quartos = QuartoDAO.getQuartos();
        return quartos;
    }

    public Quarto getQuarto(int num){
        Quarto q = QuartoDAO.getQuarto(num);
        return q;
    }


}
