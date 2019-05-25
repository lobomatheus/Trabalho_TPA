package view;

import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class JPanelReservar extends JPanel {

    private Calendar dataEntrada;
    private JPrincipal principal;

    public JPanelReservar(JPrincipal principal){
        this.principal = principal;
        initComponents();
    }

    private void initComponents(){
        JDatePicker picker = new JDatePicker();
        picker.setTextEditable(true);
        picker.setShowYearButtons(true);

        JLabel lblData = new JLabel("Data de entrada:  ");

        JSpinner spinner = new JSpinner();
        JLabel lblNdias = new JLabel("Quantidade de dias: ");

        JCheckBox boxCafe = new JCheckBox("Inclui café da manhã");

        JButton btnReservar = new JButton("Reservar");
        JButton btnVoltar = new JButton("Voltar");

        picker.addActionListener(e -> {
            dataEntrada = (Calendar) picker.getModel().getValue();
        });

        btnReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.RealizarReserva(dataEntrada, (Integer)spinner.getValue(), boxCafe.isSelected(), new Long(10));
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                
            }
        });

        this.add(lblData);
        this.add((JComponent)picker);
        this.add(lblNdias);
        this.add(spinner);
        this.add(boxCafe);
        this.add(btnReservar);
        this.add(btnVoltar);
    }
}
