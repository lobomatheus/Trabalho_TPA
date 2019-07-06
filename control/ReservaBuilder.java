package control;

import exception.ValorPagoException;
import model.Quarto;
import model.Reserva;

import java.util.Calendar;

public class ReservaBuilder {

    private Calendar dataEntrada;
    private int qtdDias;
    private boolean incluiCafe;
    private Quarto quarto;
    private boolean checkin;
    private float valorTotal;
    private float valorPago=0;


    public ReservaBuilder(Calendar data, int numDias, boolean comCafe, Quarto quarto){
        this.dataEntrada = data;
        this.qtdDias = numDias;
        this.incluiCafe = comCafe;
        this.quarto = quarto;
    }

    public void setValorPago(float valorPago) throws ValorPagoException {
        if(valorPago == 0) throw new ValorPagoException();
        this.valorPago += valorPago;
    }

    public float calcularValorTotal(){
        return 0;
    }

    public Reserva getReserva() throws ValorPagoException{
        if(valorPago == 0) throw new ValorPagoException();
        Reserva reserva = new Reserva(this.dataEntrada, this.qtdDias, this.incluiCafe, this.quarto);
        reserva.realizarPagamento(this.valorPago);
        return reserva;
    }


}
