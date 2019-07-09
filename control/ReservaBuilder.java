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
    private float porcentagemPagamento;


    public ReservaBuilder(Calendar data, int numDias, boolean comCafe, Quarto quarto){
        this.dataEntrada = data;
        this.qtdDias = numDias;
        this.incluiCafe = comCafe;
        this.quarto = quarto;
    }

    public void setValorPago(float valorPago) throws ValorPagoException {
        if(valorPago/valorTotal < porcentagemPagamento) throw new ValorPagoException();
        this.valorPago += valorPago;
    }

    public Reserva getReserva() throws ValorPagoException{
        if(valorPago == 0) throw new ValorPagoException();
        Reserva reserva = new Reserva(this.dataEntrada, this.qtdDias, this.incluiCafe, this.quarto);
        reserva.realizarPagamento(this.valorPago);
        return reserva;
    }

    public int getQtdDias() {
        return qtdDias;
    }

    public boolean isIncluiCafe() {
        return incluiCafe;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setPorcentagemPagamento(float porcentagemPagamento){
        this.porcentagemPagamento = porcentagemPagamento;
    }

    public float getPorcentagemPagamento() {
        return porcentagemPagamento;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
}
