package view;

import control.ControladorReserva;

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

        String colunas[] = {"Número", "Camas de Casal", "Camas de solteiro", "Internet", "TV a cabo", "Valor"};
        JTable tableQuartos = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        JLabel lblQuarto = new JLabel("Insira o número do quarto: ");

        JTextField txtNumQuarto = new JTextField(3);

        JButton btnVoltar = new JButton("Voltar");
        JButton btnCheckin = new JButton("Checkin");

        btnCheckin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int numQuarto = Integer.parseInt(txtNumQuarto.getText());
                    controlador.FazerCheckin(numQuarto);
                    JOptionPane.showMessageDialog(null, "Checkin realizado com sucesso!");
                } catch (IllegalFormatConversionException e){
                    JOptionPane.showMessageDialog(null, "Valor do quarto inserido incorretamente!");
                } catch (NoResultException e){
                    JOptionPane.showMessageDialog(null, "Quarto não encontrado ou não reservado!");
                }
            }
        });

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
