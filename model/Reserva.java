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
    // inserir quarto aqui

    public Reserva(Calendar dataEntrada, int qntdDias, boolean cafe){
        this.dataEntrada = dataEntrada;
        this.qtdDias = qntdDias;
        this.incluiCafe = cafe;
    }

}
