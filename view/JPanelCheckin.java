package view;

import control.ControladorReserva;
import exception.ValorPagoException;
import jdk.nashorn.internal.scripts.JO;

import javax.persistence.NoResultException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.IllegalFormatConversionException;

public class JPanelCheckin extends JPanel {

    private JPrincipal principal; // trocar para o nome da classe que o Lobo fizer
    private ControladorReserva controlador;

    public JPanelCheckin(JPrincipal principal){
        this.principal = principal;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controlador = ControladorReserva.getInstance();
        initComponents();
    }

    private void initComponents(){

        String linhas[][] = controlador.getListaReservasSemCheckin();
        String colunas[] = linhas[0];
        JTable tableReservas = new JTable(linhas, colunas){
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

        tableReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ScrollPane scroll = new ScrollPane();
        scroll.add(tableReservas);
        tableReservas.setFillsViewportHeight(true);
        scroll.setSize(500, 100);


        JLabel lblQuarto = new JLabel("Selecione a reserva: ");

        JButton btnVoltar = new JButton("Voltar");
        JButton btnCheckin = new JButton("Checkin");
        JButton btnPagar = new JButton("Pagar");

        btnCheckin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(tableReservas.getSelectedRow() != -1){
                    try{
                        int numReserva = Integer.parseInt(tableReservas.getValueAt(tableReservas.getSelectedRow(), 0).toString());
                        if(!controlador.fezPagamento(numReserva)){
                            JOptionPane.showMessageDialog(null, "Você precisa pagar a reserva integralmente antes de realizar o checkin!");
                            return;
                        }
                        controlador.FazerCheckin(numReserva);
                        JOptionPane.showMessageDialog(null, "Checkin realizado com sucesso!");
                        principal.Voltar();
                        principal.FazerCheckin();
                    } catch (IllegalFormatConversionException e){
                        JOptionPane.showMessageDialog(null, "Valor da reserva inserida incorretamente!");
                    } catch (NoResultException e){
                        JOptionPane.showMessageDialog(null, "Reserva não encontrada ou não realizada!");
                    }
                }
            }
        });

        btnPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(tableReservas.getSelectedRow() != -1) {
                    int numReserva = Integer.parseInt(tableReservas.getValueAt(tableReservas.getSelectedRow(), 0).toString());
                    while (true) {
                        try {
                            float valor = Float.parseFloat(JOptionPane.showInputDialog(null, "Insira o valor a pagar:"));
                            controlador.PagarReserva(valor, numReserva);
                            principal.Voltar();
                            principal.FazerCheckin();
                            break;
                        } catch (ValorPagoException e) {
                            if(JOptionPane.showConfirmDialog(null,
                                    "Valor inserido acima do necessário! Deseja cancelar o pagamento?",
                                    "Erro!",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) break;

                        } catch (NumberFormatException e){
                            if(JOptionPane.showConfirmDialog(null,
                                    "Valor inserido incorretamente! Deseja cancelar o pagamento?",
                                    "Erro!",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) break;
                        } catch (NullPointerException e){
                            break;
                        }
                    }
                }

            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.Voltar();
            }
        });

        this.add(lblQuarto);
        this.add(scroll);
        this.add(gerarPanel(btnVoltar, btnCheckin, btnPagar));
    }

    private JPanel gerarPanel(Component... compList){
        JPanel panel = new JPanel();
        for(Component component : compList){
            panel.add(component);
        }
        return panel;
    }
}
