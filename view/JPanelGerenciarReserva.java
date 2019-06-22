package view;

import control.ControladorReserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPanelGerenciarReserva extends JPanel {

    private JPrincipal principal;
    private ControladorReserva controlador;

    public JPanelGerenciarReserva(JPrincipal principal){
        this.principal = principal;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controlador = ControladorReserva.getInstance();
        initComponents();

    }

    private void initComponents(){

        String[][] celulas = controlador.getListaReservas();
        String[] colunas = celulas[0];

        JTable tableReservas = new JTable(celulas, colunas){
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
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(tableReservas);
        tableReservas.setFillsViewportHeight(true);
        scrollPane.setSize(500, 100);

        JButton btnVoltar = new JButton("Voltar");
        JButton btnNova = new JButton("Nova");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.Voltar();
            }
        });

        btnNova.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.RealizarReserva(false, null);
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(tableReservas.getSelectedRow() != -1) {
                    int confirm = 0;
                    confirm = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir a reserva?", "Tem certeza?", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        controlador.removerReserva(Integer.parseInt(tableReservas.getValueAt(tableReservas.getSelectedRow(), 0).toString()));
                        JOptionPane.showMessageDialog(null, "Reserva removida com sucesso.");
                        principal.Voltar();
                        principal.GerenciarReserva();
                    }
                }
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(tableReservas.getSelectedRow()!=-1){
                    principal.RealizarReserva(true, Long.parseLong(tableReservas.getValueAt(tableReservas.getSelectedRow(), 0).toString()));
                }
            }
        });

        this.add(gerarPanel(btnEditar, btnExcluir));
        this.add(scrollPane);
        this.add(gerarPanel(btnVoltar, btnNova));


    }

    private JPanel gerarPanel(Component... compList) {
        JPanel panel = new JPanel();
        for (Component component : compList) {
            panel.add(component);
        }
        return panel;
    }

}
