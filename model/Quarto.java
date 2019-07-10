/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.persistence.*;

/**
 *
 * @author mathe
 */

@Entity
public class Quarto {
    
    @Id
    @GeneratedValue
    int num;
    
    int camas_solt;
    int camas_cas;
    boolean banheiro;
    boolean internet;
    boolean cabo;

    public Quarto(){

    }
    
    public Quarto(int camas_solt, int camas_cas, boolean banheiro, boolean internet, boolean cabo){
        this.camas_solt = camas_solt;
        this.camas_cas = camas_cas;
        this.banheiro = banheiro;
        this.internet = internet;
        this.cabo = cabo;
    }
    
    public int getNum() {
        return num;
    }

    public int getCamas_solt() {
        return camas_solt;
    }

    public int getCamas_cas() {    
        return camas_cas;
    }

    public boolean isBanheiro() {
        return banheiro;
    }

    public boolean isInternet() {
        return internet;
    }

    public boolean isCabo() {
        return cabo;
    }

    public String getCamas() {
        int solteiro = getCamas_solt();
        int casal = getCamas_cas();
        
        if(solteiro == 0)
            return "Casal: " + casal;
        else if (casal == 0)
            return "Solteiro: " + solteiro;        
        return "Solteiro: " + solteiro + "\nCasal: " + casal;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    public void setCabo(boolean cabo) {
        this.cabo = cabo;
    }

    //mudar esse para interagir com o mouse
    public void setCama(int tipo){
        if (tipo == 1)
            camas_solt++;
        else
            camas_cas++;
    }
}
