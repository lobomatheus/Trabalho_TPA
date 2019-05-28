/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

//import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Quarto;

/**
 *
 * @author mathe
 */
public class ControladorCadastros {
    //HashMap<Integer, Quarto> quartos;
    
    public ControladorCadastros(){ 
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("quartos");
        factory.close();
    }
        
    /*public Quarto findQuarto(int num){
        return quartos.get(num);
    }*/
    
    public void CadastrarQuarto(int num, int camas_solt, int camas_cas, boolean banheiro, boolean internet, boolean cabo){
        Quarto q = new Quarto(num,camas_solt, camas_cas, banheiro, internet, cabo);
        
        
        
    }
}
