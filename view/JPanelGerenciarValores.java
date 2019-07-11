package view;

import control.ControladorValores;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JPanelGerenciarValores extends JPanel {

    private JPrincipal principal;
    private ControladorValores controlador;

    public JPanelGerenciarValores(JPrincipal principal){
        this.principal = principal;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controlador = ControladorValores.getInstance();
        initComponents();

    }

    private void initComponents(){

        ArrayList<Double> valores = controlador.getValores();
        float porcentagem = controlador.getPorcentagem();

        JTextField txtDiaria = new JTextField(String.valueOf(valores.get(0)));
        JTextField txtPercent = new JTextField(String.valueOf(porcentagem));
        JTextField txtCafe = new JTextField(String.valueOf(valores.get(1)));
        JTextField txtCasal = new JTextField(String.valueOf(valores.get(2)));
        JTextField txtSolt = new JTextField(String.valueOf(valores.get(3)));
        JTextField txtBanheiro = new JTextField(String.valueOf(valores.get(4)));
        JTextField txtInternet = new JTextField(String.valueOf(valores.get(5)));
        JTextField txtTV = new JTextField(String.valueOf(valores.get(6)));

        JLabel lblDiaria = new JLabel("Diária");
        JLabel lblPercent = new JLabel("Porcentagem a pagar");
        JLabel lblCafe = new JLabel("Café da manhã");
        JLabel lblCasal = new JLabel("Cama casal");
        JLabel lblSolt = new JLabel("Cama Solteiro");
        JLabel lblBanheiro = new JLabel("Banheiro");
        JLabel lblInternet = new JLabel("Internet");
        JLabel lblTV = new JLabel("TV a cabo");

        JButton btnVoltar = new JButton("Voltar");
        JButton btnSalvar = new JButton("Salvar");

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.Voltar();
            }
        });

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    double diaria = Double.parseDouble(txtDiaria.getText());
                    float percent = Float.parseFloat(txtPercent.getText());
                    double cafe = Double.parseDouble(txtCafe.getText());
                    double casal = Double.parseDouble(txtCasal.getText());
                    double solteiro = Double.parseDouble(txtSolt.getText());
                    double banheiro = Double.parseDouble(txtBanheiro.getText());
                    double internet = Double.parseDouble(txtInternet.getText());
                    double tv = Double.parseDouble(txtTV.getText());

                    controlador.updateValues(solteiro, casal, banheiro, internet, tv, cafe, percent, diaria);

                    JOptionPane.showMessageDialog(null, "Valores alterados com sucesso.");
                } catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Valor inserido incorretamente!");
                }
            }
        });


        this.add(gerarPanel(lblDiaria, txtDiaria));
        this.add(gerarPanel(lblPercent, txtPercent));
        this.add(gerarPanel(lblCafe, txtCafe));
        this.add(gerarPanel(lblCasal, txtCasal));
        this.add(gerarPanel(lblSolt, txtSolt));
        this.add(gerarPanel(lblBanheiro, txtBanheiro));
        this.add(gerarPanel(lblInternet, txtInternet));
        this.add(gerarPanel(lblTV, txtTV));
        this.add(gerarPanel(btnVoltar, btnSalvar));


    }

    private JPanel gerarPanel(Component... compList){
        JPanel panel = new JPanel();
        for(Component component : compList){
            panel.add(component);
        }
        return panel;
    }
}
