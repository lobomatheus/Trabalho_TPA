package control;

import exception.ValorPagoException;
import model.Quarto;
import model.Reserva;
import persistence.ReservaDAO;

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

        return quarto;

    }

    public void editarReserva(Calendar data, int numDias, boolean comCafe, int idQuarto, Long id){
        Quarto quarto = encontrarQuarto(idQuarto);
        Reserva reserva = new Reserva(data, numDias, comCafe, quarto);

        reserva.setId(id);
        ReservaDAO.updateReserva(reserva);
    }

    public float iniciarReserva(Calendar data, int numDias, boolean comCafe, int idQuarto){

        Quarto quarto = encontrarQuarto(idQuarto);

        reservaBuilder = new ReservaBuilder(data, numDias, comCafe, quarto);

        return reservaBuilder.calcularValorTotal();

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
        // Irá retornar os quartos com ou sem checkin a partir do banco de dados
        // Quando acesso ao banco estiver pronto, precisa especificar qual quarto
        // está com checkin feito e qual não está.
        // Também será necessário exibir o horario da reserva
        String[][] quartos =  {{"Número", "Camas de Casal", "Camas de solteiro", "Internet", "TV a cabo", "Valor"},
                                {"1", "true", "true", "1", "1", "true"},
                                {"2", "true", "true", "1", "2", "false"},
                                {"3", "true", "false", "3", "3", "true"},
                                {"4", "true", "true", "2", "1", "false"},
                                {"5", "false", "false", "2", "1", "true"},
                                {"6", "false", "true", "3", "3", "false"},
                                {"7", "false", "false", "1", "2", "true"},
                                {"8", "false", "true", "1", "2", "false"}};
        return quartos;
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
}
