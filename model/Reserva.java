package model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Reserva {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.DATE)
    private Calendar dataEntrada;

    private int qtdDias;

    private boolean incluiCafe;

    @ManyToOne
    private Quarto quarto;

    private boolean checkin;

    private float valorTotal;

    private float valorPago;

    // Construtor sem argumentos requerido pelo JPA
    public Reserva(){
        this.checkin = false;
        this.valorPago = 0;
    }

    public Reserva(Calendar dataEntrada, int qntdDias, boolean cafe, Quarto quarto){
        this.dataEntrada = dataEntrada;
        this.qtdDias = qntdDias;
        this.incluiCafe = cafe;
        this.quarto = quarto;
        this.checkin = false;
        this.valorPago = 0;
    }

    public Long getId() {
        return id;
    }

    public Calendar getDataEntrada() {
        return dataEntrada;
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

    public float getValorTotal() {
        return this.valorTotal;
    }

    public float getValorPago(){
        return this.valorPago;
    }

    public boolean isCheckin(){
        return this.checkin;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void doCheckin(){
        this.checkin = true;
    }

    public float calcularValor(){
        float valor = 0;

        this.valorTotal = valor;
        return valor;
    }

    public void realizarPagamento(float valor){
        // Exceção de valor pago ser superior ao necessário
        this.valorPago += valor;
    }
}
