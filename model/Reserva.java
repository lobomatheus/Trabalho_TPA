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

    @OneToOne
    private Quarto quarto;

    private boolean checkin;

    public Reserva(Calendar dataEntrada, int qntdDias, boolean cafe, Quarto quarto){
        this.dataEntrada = dataEntrada;
        this.qtdDias = qntdDias;
        this.incluiCafe = cafe;
        this.quarto = quarto;
        this.checkin = false;
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

    public boolean isCheckin(){
        return this.checkin;
    }

    public void doCheckin(){
        this.checkin = true;
    }
}
