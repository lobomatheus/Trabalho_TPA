package model;

import java.io.Serializable;

public class Valores implements Serializable {

    private double valorCamaSolteiro;
    private double valorCamaCasal;
    private double valorBanheiro;
    private double valorInternet;
    private double valorTV;
    private double valorCafe;
    private float porcentagemPagamento;


    public double getValorCamaSolteiro() {
        return valorCamaSolteiro;
    }

    public void setValorCamaSolteiro(double valorCamaSolteiro) {
        this.valorCamaSolteiro = valorCamaSolteiro;
    }

    public double getValorCamaCasal() {
        return valorCamaCasal;
    }

    public void setValorCamaCasal(double valorCamaCasal) {
        this.valorCamaCasal = valorCamaCasal;
    }

    public double getValorBanheiro() {
        return valorBanheiro;
    }

    public void setValorBanheiro(double valorBanheiro) {
        this.valorBanheiro = valorBanheiro;
    }

    public double getValorInternet() {
        return valorInternet;
    }

    public void setValorInternet(double valorInternet) {
        this.valorInternet = valorInternet;
    }

    public double getValorTV() {
        return valorTV;
    }

    public void setValorTV(double valorTV) {
        this.valorTV = valorTV;
    }

    public float getPorcentagemPagamento() {
        return porcentagemPagamento;
    }

    public void setPorcentagemPagamento(float porcentagemPagamento) {
        this.porcentagemPagamento = porcentagemPagamento;
    }

    public double getValorCafe() {
        return valorCafe;
    }

    public void setValorCafe(double valorCafe) {
        this.valorCafe = valorCafe;
    }
}
