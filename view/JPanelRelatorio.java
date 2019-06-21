package view;

import control.ControladorRelatorio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPanelRelatorio extends JPanel {

    JPrincipal principal;
    private ControladorRelatorio controlador;

    public JPanelRelatorio(JPrincipal principal){
        this.principal = principal;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controlador = ControladorRelatorio.getInstance();
        initComponents();
    }

    private void initComponents(){
        JButton btnAnual = new JButton("Relatório Anual");

        JCheckBox checkAnual = new JCheckBox("Relatório futuro?");

        JButton btnMensal = new JButton("Relatório Mensal");

        JCheckBox checkMensal = new JCheckBox("Relatório futuro?");


        btnAnual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double res = controlador.RelatorioAnual(checkAnual.isSelected());
                JOptionPane.showMessageDialog(null, "O valor arrecadado anual foi de R$" + String.format("%.2f", res) + ".");
            }
        });

        btnMensal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                float res = controlador.RelatorioMensal(checkMensal.isSelected());
                JOptionPane.showMessageDialog(null, "O valor arrecadado mensal foi de R$" + String.format("%.2f", res) + ".");
            }
        });

        add(gerarPanel(btnAnual, checkAnual));
        add(gerarPanel(btnMensal, checkMensal));

    }

    private JPanel gerarPanel(Component... compList){
        JPanel panel = new JPanel();
        for(Component component : compList){
            panel.add(component);
        }
        return panel;
    }
}
