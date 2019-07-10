package control;

import model.Valores;
import persistence.ValoresDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ControladorValores {

    private static ControladorValores controlador = null;
    private static Valores val=null;

    private ControladorValores(){

    }

    public static ControladorValores getInstance(){
        if(controlador==null){
            controlador = new ControladorValores();
        }
        return controlador;
    }

    public void updateValues(double camaSolt, double camaCasal, double banheiro,
            double internet, double tv, double cafe, float porcentagem, double diaria){
        Valores val = new Valores();

        val.setValorDiaria(diaria);
        val.setPorcentagemPagamento(porcentagem);
        val.setValorCafe(cafe);
        val.setValorCamaCasal(camaCasal);
        val.setValorCamaSolteiro(camaSolt);
        val.setValorBanheiro(banheiro);
        val.setValorInternet(internet);
        val.setValorTV(tv);

        ValoresDAO.setValores(val);
        if(ControladorValores.val != null) ControladorValores.val = (Valores)ValoresDAO.getValores();

    }

    public ArrayList<Double> getValores(){
        ArrayList<Double> custos = new ArrayList<Double>();

        if(val == null){
            val = (Valores)ValoresDAO.getValores();
        }

        custos.add(val.getValorDiaria());
        custos.add(val.getValorCafe());
        custos.add(val.getValorCamaCasal());
        custos.add(val.getValorCamaSolteiro());
        custos.add(val.getValorBanheiro());
        custos.add(val.getValorInternet());
        custos.add(val.getValorTV());

        return custos;
    }

    public float getPorcentagem(){
        if(val == null){
            val = (Valores)ValoresDAO.getValores();
        }

        return val.getPorcentagemPagamento();
    }

}
