package view;

import control.ControladorQuarto;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class JPanelQuarto extends JPanel {

    JPrincipal principal;
    private ControladorQuarto controlador;

    private int qtdCasal=0;
    private int qtdSolt=0;
    private boolean editing=false;
    private Integer idEditing=null;

    public JPanelQuarto(JPrincipal principal){
        this.principal = principal;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controlador = ControladorQuarto.getInstance();
        initComponents();
    }

    public void setEditing(int id){
        this.editing = true;
        this.idEditing = id;
    }

    private void initComponents(){

        SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        SpinnerNumberModel model2 = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner spnCasal = new JSpinner(model1);
        JSpinner spnSolt = new JSpinner(model2);

        JCheckBox boxBanheiro = new JCheckBox();
        JCheckBox boxInternet = new JCheckBox();
        JCheckBox boxTV = new JCheckBox();

        JLabel lblCasal = new JLabel("Camas de casal: ");
        JLabel lblSolt = new JLabel("Camas de solteiro: ");

        JLabel lblBanheiro = new JLabel("Inclui banheiro?");
        JLabel lblInternet = new JLabel("Inclui internet?");
        JLabel lblTV = new JLabel("Inclui TV a cabo?");

        JButton btnVoltar = new JButton("Voltar");
        JButton btnSalvar = new JButton("Salvar");

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                principal.GerenciarQuarto();
            }
        });

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(!editing){
                    controlador.CadastrarQuarto(qtdSolt, qtdCasal,
                            boxBanheiro.isSelected(), boxInternet.isSelected(), boxTV.isSelected());
                    JOptionPane.showMessageDialog(null, "Quarto adicionado com sucesso!");
                } else{
                    controlador.AlterarQuarto(idEditing, qtdSolt, qtdCasal,
                            boxBanheiro.isSelected(), boxInternet.isSelected(), boxTV.isSelected());
                    JOptionPane.showMessageDialog(null, "Quarto alterado com sucesso!");
                }

            }
        });

        spnCasal.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                try{
                    spnCasal.commitEdit();
                    qtdCasal = (Integer)spnCasal.getValue();
                } catch (ParseException e) {}
            }
        });

        spnSolt.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                try{
                    spnSolt.commitEdit();
                    qtdSolt = (Integer)spnSolt.getValue();
                } catch (ParseException e) { }
            }
        });

        this.add(gerarPanel(lblCasal, spnCasal));
        this.add(gerarPanel(lblSolt, spnSolt));
        this.add(gerarPanel(lblBanheiro, boxBanheiro));
        this.add(gerarPanel(lblInternet, boxInternet));
        this.add(gerarPanel(lblTV, boxTV));
        this.add(gerarPanel(btnVoltar, btnSalvar));

    }

    private JPanel gerarPanel(Component... compList) {
        JPanel panel = new JPanel();
        for (Component component : compList) {
            panel.add(component);
        }
        return panel;
    }
}
