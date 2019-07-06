package view;

import control.ControladorReserva;
import exception.ValorPagoException;
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
    private ControladorReserva controlador;
    private boolean editing=false;
    private Long idEditing=null;

    public JPanelReservar(JPrincipal principal){
        this.principal = principal;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controlador = ControladorReserva.getInstance();
        initComponents();
    }

    public void setEditing(Long id){
        this.editing = true;
        this.idEditing = id;
    }

    private void initComponents(){
        JDatePicker picker = new JDatePicker();
        picker.setTextEditable(true);
        picker.setShowYearButtons(true);

        JLabel lblData = new JLabel("Data de entrada:  ");

        JSpinner spinner = new JSpinner();
        JLabel lblNdias = new JLabel("Quantidade de dias: ");

        JCheckBox boxCafe = new JCheckBox("Inclui café da manhã");


        String linhas[][] = controlador.getListaQuartos();
        String colunas[] = linhas[0];

        JTable tableQuartos = new JTable(linhas, colunas) {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }

            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend){
                if(rowIndex == 0){
                    super.changeSelection(1, columnIndex, toggle, extend);
                } else{
                    super.changeSelection(rowIndex, columnIndex, toggle, extend);
                }
            }
        };

        tableQuartos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(tableQuartos);
        tableQuartos.setFillsViewportHeight(true);
        scrollPane.setSize(500, 100);



        JButton btnReservar = new JButton("Reservar");
        JButton btnVoltar = new JButton("Voltar");

        picker.addActionListener(e -> {
            dataEntrada = (Calendar) picker.getModel().getValue();
        });

        btnReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                int idQuarto = Integer.parseInt(tableQuartos.getValueAt(tableQuartos.getSelectedRow(), 0).toString());

                if(!editing){

                    float valor = controlador.iniciarReserva(dataEntrada, spinnerValue, boxCafe.isSelected(), idQuarto);


                    float valorPago;

                    while(true){
                        try {
                            valorPago = Float.parseFloat(JOptionPane.showInputDialog(null,
                                    "O valor total da reserva deu: " + valor + "\nQuanto deseja pagar?"));
                            controlador.pagarReservaEmConstrucao(valorPago);
                            controlador.realizarReserva();
                            JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso!");
                            break;

                        } catch (ValorPagoException e){
                            valorPago = Float.parseFloat(JOptionPane.showInputDialog(null,
                                    "O valor total da reserva deu: " + valor + " e você precisa pagar [porcentagem]\n" +
                                            "Quanto deseja pagar (0 para cancelar)?"));

                            if(valorPago == 0){
                                JOptionPane.showMessageDialog(null, "Reserva cancelada com sucesso!");
                                controlador.cancelarReserva();
                                break;
                            }
                        } catch (NumberFormatException e){
                            JOptionPane.showMessageDialog(null, "Valor inserido incorretamente!");
                        }
                    }

                }
                else {

                    controlador.editarReserva(dataEntrada, spinnerValue, boxCafe.isSelected(), idQuarto, idEditing);

                    JOptionPane.showMessageDialog(null, "Reserva atualizada com sucesso!");
                }

                principal.GerenciarReserva();
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
                principal.GerenciarReserva();
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
}
