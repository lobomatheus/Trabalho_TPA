package view;

import control.ControladorReserva;
import model.Quarto;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;

public class JPanelReservar extends JPanel {

    private Calendar dataEntrada;
    private int spinnerValue = 0;
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


        String linhas[][] = {{"1", "true", "true", "1", "1", "true"},
                             {"2", "true", "true", "1", "2", "false"},
                             {"3", "true", "false", "3", "3", "true"},
                             {"4", "true", "true", "2", "1", "false"},
                             {"5", "false", "false", "2", "1", "true"},
                             {"6", "false", "true", "3", "3", "false"},
                             {"7", "false", "false", "1", "2", "true"},
                             {"8", "false", "true", "1", "2", "false"}};
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

                int idQuarto = Integer.parseInt(tableQuartos.getValueAt(tableQuartos.getSelectedRow(), 0).toString());
                System.out.println(idQuarto);

                RealizarReserva(dataEntrada, spinnerValue, boxCafe.isSelected(), idQuarto);

                JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso!");
                principal.Voltar();
            }
        });

        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                try{
                    spinner.commitEdit();
                    spinnerValue = (Integer) spinner.getValue();
                } catch(ParseException e){ }
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.Voltar();
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
