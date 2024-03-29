package view;

import control.ControladorReserva;
import exception.QuartoReservadoException;
import exception.ValorPagoException;
import jdk.nashorn.internal.scripts.JO;
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

        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(model);
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
                    float valor = 0;

                    try{
                        valor = controlador.iniciarReserva(dataEntrada, spinnerValue, boxCafe.isSelected(), idQuarto);
                    } catch (QuartoReservadoException e){
                        JOptionPane.showMessageDialog(null, "Esse quarto já está reservado para a data selecionada!");
                        return;
                    }


                    float valorPago;
                    float porcentagem = controlador.retornarPorcentagem();
                    while(true){
                        try {
                            String input;
                            input = JOptionPane.showInputDialog(null,
                                    "O valor total da reserva deu: " + valor + "\nQuanto deseja pagar?");
                            if(input == null){
                                controlador.cancelarReserva();
                                return;
                            }
                            valorPago = Float.parseFloat(input);
                            controlador.pagarReservaEmConstrucao(valorPago);
                            controlador.realizarReserva();
                            JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso!");
                            break;

                        } catch (ValorPagoException e){
                            JOptionPane.showMessageDialog(null,
                                    "O valor total da reserva deu: " + valor + " e você precisa pagar " + porcentagem*100 + "% do total.");
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
