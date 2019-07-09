package persistence;

import model.Valores;

import java.io.*;

public class ValoresDAO {

    public static Valores getValores(){
        Valores val=null;
        try {
            FileInputStream f = new FileInputStream("val.dat");
            ObjectInputStream s = new ObjectInputStream(f);
            val = (Valores)s.readObject();
        } catch (FileNotFoundException e){

        } catch (IOException e){

        } catch (ClassNotFoundException e){

        }
        return val;
    }

    public static void setValores(Valores val){
        try {
            FileOutputStream f = new FileOutputStream("val.dat");
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(val);
        } catch (FileNotFoundException e){

        } catch (IOException e){

        }
    }
}
