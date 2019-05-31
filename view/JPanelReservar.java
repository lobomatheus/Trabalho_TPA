package view;

import control.ControladorReserva;
import model.Quarto;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class JPanelReservar extends JPanel {

    private Calendar dataEntrada;
    private JPrincipal principal;

    public JPanelReservar(JPrincipal principal){
        this.principal = principal;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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


        String linhas[][] = {{"1", "2", "3", "4", "5", "6"},
                             {"1", "2", "3", "4", "5", "6"}};
        String colunas[] = {"Número", "Camas de Casal", "Camas de solteiro", "Internet", "TV a cabo", "Valor"};
        JTable tableQuartos = new JTable(linhas, colunas) {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        tableQuartos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(tableQuartos);
        tableQuartos.setFillsViewportHeight(true);



        JButton btnReservar = new JButton("Reservar");
        JButton btnVoltar = new JButton("Voltar");

        picker.addActionListener(e -> {
            dataEntrada = (Calendar) picker.getModel().getValue();
        });

        btnReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RealizarReserva(dataEntrada, (Integer)spinner.getValue(), boxCafe.isSelected(), 10);
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Voltar para a tela inicial
            }
        });

        this.add(gerarPanel(lblData, picker));
        this.add(gerarPanel(lblNdias, spinner));
        this.add(boxCafe);
        this.add(scrollPane);
        this.add(gerarPanel(btnVoltar, btnReservar));
    }

    private JPanel gerarPanel(Component... compList){
        JPanel panel = new JPanel();
        for(Component component : compList){
            panel.add(component);
        }
        return panel;
    }


    private void RealizarReserva(Calendar data, int numDias, boolean comCafe, int idQuarto){
        ControladorReserva.FazerReserva(data, numDias, comCafe, idQuarto);
    }
}
