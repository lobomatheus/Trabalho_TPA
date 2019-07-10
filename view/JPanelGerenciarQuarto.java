package view;

import control.ControladorQuarto;
import exception.QuartoReservadoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPanelGerenciarQuarto extends JPanel {

    private JPrincipal principal;
    private ControladorQuarto controlador;

    public JPanelGerenciarQuarto(JPrincipal principal){
        this.principal = principal;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controlador = ControladorQuarto.getInstance();
        initComponents();
    }

    private void initComponents(){
        String celulas[][] = controlador.getListaQuartos();
        String[] colunas = celulas[0];

        JTable tableQuartos = new JTable(celulas,colunas){
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

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(tableQuartos.getSelectedRow() != -1) {
                    int confirm = 0;
                    confirm = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir o quarto?", "Tem certeza?", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            controlador.ExcluirQuarto(Integer.parseInt(tableQuartos.getValueAt(tableQuartos.getSelectedRow(), 0).toString()));
                            JOptionPane.showMessageDialog(null,
                                    "Quarto removido com sucesso.");
                            principal.Voltar();
                            principal.GerenciarQuarto();
                        } catch (QuartoReservadoException e){
                            JOptionPane.showMessageDialog(null,
                                    "Este quarto está reservado. Cancele a reserva para removê-lo.");
                        }
                    }
                }
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(tableQuartos.getSelectedRow() != -1){
                    principal.EditarQuarto(true,
                            Integer.parseInt(tableQuartos.getValueAt(tableQuartos.getSelectedRow(), 0).toString()));
                }
            }
        });

        btnNova.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.EditarQuarto(false, -1);
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
