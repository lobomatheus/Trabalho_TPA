package control;

import exception.QuartoReservadoException;
import exception.ValorPagoException;
import model.Quarto;
import model.Reserva;
import model.Valores;
import persistence.ReservaDAO;
import persistence.ValoresDAO;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ControladorReserva {

    private static ControladorReserva controladorReserva = null;

    private ReservaBuilder reservaBuilder = null;

    private ControladorReserva(){

    }

    public static ControladorReserva getInstance(){
        if(controladorReserva==null){
            controladorReserva = new ControladorReserva();
        }
        return controladorReserva;
    }


    private Quarto encontrarQuarto(int idQuarto){
        ControladorQuarto controladorQuarto = ControladorQuarto.getInstance();
        return controladorQuarto.getQuarto(idQuarto);
    }

    public void editarReserva(Calendar data, int numDias, boolean comCafe, int idQuarto, Long id){
        Quarto quarto = encontrarQuarto(idQuarto);
        Reserva reserva = new Reserva(data, numDias, comCafe, quarto);

        reserva.setId(id);
        ReservaDAO.updateReserva(reserva);
    }

    private float calcularValorTotal(){
        Valores valores = (Valores)ValoresDAO.getValores();
        Quarto q = reservaBuilder.getQuarto();
        float custo = 0;
        custo += reservaBuilder.getQtdDias()*valores.getValorDiaria();
        if(reservaBuilder.isIncluiCafe()) custo += valores.getValorCafe();
        if(q.isInternet()) custo += valores.getValorInternet();
        if(q.isBanheiro()) custo += valores.getValorBanheiro();
        if(q.isCabo()) custo += valores.getValorTV();
        custo += q.getCamas_cas()*valores.getValorCamaCasal();
        custo += q.getCamas_solt()*valores.getValorCamaSolteiro();
        reservaBuilder.setPorcentagemPagamento(valores.getPorcentagemPagamento());
        reservaBuilder.setValorTotal(custo);
        return custo;

    }

    private boolean verificarCheckin(Reserva r){

        Calendar now = Calendar.getInstance();
        Calendar rDate = r.getDataEntrada();

        if(rDate.getTimeInMillis() - now.getTimeInMillis() < 24*60*60*1000){
            if(!r.isCheckin()){
                ReservaDAO.removeReserva(r);
                return true;
            }
        }

        return false;
    }

    public boolean verificarDisponibilidade(Quarto q, int numDias, Calendar data) {
        List<Reserva> reservas = ReservaDAO.getReservasByQuarto(q.getNum());

        System.out.println(reservas.size());

        long iniTime = data.getTimeInMillis();
        long fimTime = iniTime + numDias*20*60*60*1000; // convertendo tudo para milissegundos

        for(Reserva r : reservas){
            if(verificarCheckin(r)) continue;
            long rIni = r.getDataEntrada().getTimeInMillis();
            long rFim = rIni + r.getQtdDias()*20*60*60*1000;
            if(iniTime < rIni && fimTime > rFim ||
                    rIni < iniTime && rFim > iniTime ||
                    rIni < fimTime && rFim > fimTime
                    ) return false;
        }
        return true;
    }

    public float iniciarReserva(Calendar data, int numDias, boolean comCafe, int idQuarto) throws QuartoReservadoException{

        Quarto quarto = encontrarQuarto(idQuarto);

        if(!verificarDisponibilidade(quarto, numDias, data)) throw new QuartoReservadoException();

        reservaBuilder = new ReservaBuilder(data, numDias, comCafe, quarto);

        return calcularValorTotal();

    }

    public void pagarReservaEmConstrucao(float valor) throws ValorPagoException {
        reservaBuilder.setValorPago(valor);
    }

    public void realizarReserva() throws ValorPagoException{
        Reserva reserva = reservaBuilder.getReserva();

        ReservaDAO.cadastrarReserva(reserva);

        reservaBuilder = null;

    }

    public void cancelarReserva() {
        reservaBuilder = null;
    }

    public void FazerCheckin(int numReserva) throws NoResultException{

        Reserva reserva = ReservaDAO.getReserva(numReserva);

        reserva.doCheckin();

        ReservaDAO.updateReserva(reserva);
    }

    public void PagarReserva(float valor, int idReserva) throws ValorPagoException{

        Reserva reserva = ReservaDAO.getReserva(idReserva);

        reserva.realizarPagamento(valor);

        ReservaDAO.updateReserva(reserva);

    }

    public String[][] getListaQuartos(){
        ControladorQuarto controladorQuarto = ControladorQuarto.getInstance();
        List<Quarto> quartos = controladorQuarto.getQuartos();
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

    private String[][] gerarListaReservas(List<Reserva> reservas){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        int N = reservas.size()+1;
        String retorno[][] = new String[N][8];
        String[] colunas = {"Id", "Data de Entrada", "Quantidade de Dias", "Inclui Café", "Quarto", "Fez checkin", "Valor pago", "Valor Total"};
        retorno[0] = colunas;
        int i=1;
        for(Reserva r : reservas){
            retorno[i][0] = r.getId().toString();
            retorno[i][1] = dateFormat.format(r.getDataEntrada().getTime());
            retorno[i][2] = "" + r.getQtdDias();
            retorno[i][3] = "" + r.isIncluiCafe();
            retorno[i][4] = "" + r.getQuarto().getNum();
            retorno[i][5] = "" + r.isCheckin();
            retorno[i][6] = "" + r.getValorPago();
            retorno[i][7] = "" + r.getValorTotal();
            i++;
        }
        return retorno;
    }

    public String[][] getListaReservasSemCheckin(){
        return gerarListaReservas(ReservaDAO.getReservaSemCheckin());
    }

    public String[][] getListaReservas(){
        List<Reserva> reservas = ReservaDAO.getReserva();
        return gerarListaReservas(reservas);
    }

    public void removerReserva(int id){
        Reserva reserva = ReservaDAO.getReserva(id);

        ReservaDAO.removeReserva(reserva);

    }

    public float retornarPorcentagem(){
        Valores val = (Valores)ValoresDAO.getValores();
        return val.getPorcentagemPagamento();
    }
}
