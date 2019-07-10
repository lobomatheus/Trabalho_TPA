package control;

import exception.QuartoReservadoException;
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
    
    public void CadastrarQuarto(int camas_solt, int camas_cas, boolean banheiro, boolean internet, boolean cabo){
        Quarto q = new Quarto(camas_solt, camas_cas, banheiro, internet, cabo);
        QuartoDAO.cadastrarQuarto(q);
    }

    public void AlterarQuarto(int num, int camas_solt, int camas_cas, boolean banheiro, boolean internet, boolean cabo){
        Quarto q = new Quarto(camas_solt, camas_cas, banheiro, internet, cabo);
        q.setNum(num);
        QuartoDAO.updateQuarto(q);
    }

    public void ExcluirQuarto(int num) throws QuartoReservadoException{
        ControladorReserva controladorReserva = ControladorReserva.getInstance();
        if(controladorReserva.verificarQuartoReservado(num)) throw new QuartoReservadoException();
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

    public String[][] getListaQuartos(){
        List<Quarto> quartos = QuartoDAO.getQuartos();
        int N = quartos.size() + 1;

        String retorno[][] = new String[N][6];
        String[] colunas =  {"Número", "Camas de Casal", "Camas de solteiro", "Internet", "TV a cabo", "Banheiro"};
        retorno[0] = colunas;
        int i =1;
        for(Quarto q : quartos){
            retorno[i][0] = String.valueOf(q.getNum());
            retorno[i][1] = String.valueOf(q.getCamas_cas());
            retorno[i][2] = String.valueOf(q.getCamas_solt());
            retorno[i][3] = String.valueOf(q.isInternet());
            retorno[i][4] = String.valueOf(q.isCabo());
            retorno[i][5] = String.valueOf(q.isBanheiro());
            i++;
        }
        return retorno;
    }


}
