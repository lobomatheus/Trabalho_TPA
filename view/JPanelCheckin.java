package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPanelCheckin extends JPanel {

    private JPrincipal principal; // trocar para o nome da classe que o Lobo fizer

    public JPanelCheckin(JPrincipal principal){
        this.principal = principal;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initComponents();
    }

    private void initComponents(){

        JLabel lblQuarto = new JLabel("Insira o número do quarto: ");

        JTextField txtNumQuarto = new JTextField(3);

        JButton btnVoltar = new JButton("Voltar");
        JButton btnCheckin = new JButton("Checkin");

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.Voltar();
            }
        });

        this.add(gerarPanel(lblQuarto, txtNumQuarto));
        this.add(gerarPanel(btnVoltar, btnCheckin));
    }

    private JPanel gerarPanel(Component... compList){
        JPanel panel = new JPanel();
        for(Component component : compList){
            panel.add(component);
        }
        return panel;
    }
}
