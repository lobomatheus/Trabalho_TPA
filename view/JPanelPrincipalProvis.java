package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPanelPrincipalProvis extends JPanel  {

    JPrincipal principal;

    public JPanelPrincipalProvis(JPrincipal principal){
        this.principal = principal;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
    }

    private void initComponents(){

        JButton btnReserva = new JButton("Gerenciar Reservas");
        JButton btnQuarto = new JButton("Gerenciar Quartos");
        JButton btnCheckin = new JButton("Fazer Checkin");
        JButton btnRelatorio = new JButton("Fazer relatorio");
        JButton btnValores = new JButton("Alterar Valores");

        btnReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.GerenciarReserva();
            }
        });

        btnQuarto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.GerenciarQuarto();
            }
        });

        btnCheckin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.FazerCheckin();
            }
        });

        btnRelatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.Relatorio();
            }
        });

        btnValores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.Valores();
            }
        });

        add(gerarPanel(btnReserva, btnQuarto));
        add(gerarPanel(btnCheckin, btnRelatorio));
        add(btnValores);

    }

    private JPanel gerarPanel(Component... compList){
        JPanel panel = new JPanel();
        for(Component component : compList){
            panel.add(component);
        }
        return panel;
    }
}
