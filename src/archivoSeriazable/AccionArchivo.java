/*
 *    Proyecto Pro-Planeta 1.0
 *    Videojuego en Java construido con JavaFX 11
 *    Autores: Castañón Puga Manuel, Vera Arias Victor Manuel, Feng Haosheng, Meléndez Lineros Leonardo
 *    Correo electrónico: {puga, victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonóma de Baja California
 *    http://www.uabc.mx
 */

package archivoSeriazable;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class AccionArchivo {

public static void escribir(Resultado resultado){

    try{
        //  FileOutputStream fileOutputStream= new FileOutputStream(File());
        FileOutputStream fileOutputStream= new FileOutputStream("Objeto.dat");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ObjectOutputStream objectOutputStream= new ObjectOutputStream(bufferedOutputStream);

        objectOutputStream.writeObject(resultado);
        bufferedOutputStream.flush();

        objectOutputStream.close();
        bufferedOutputStream.close();
        fileOutputStream.close();
        System.out.println("Si guardo");
    }catch (FileNotFoundException ex){
        ex.printStackTrace();
    }catch (IOException ex){
        ex.printStackTrace();
    }
}

public static Resultado leer()
{       Resultado resultado = null;
    try {

            FileInputStream fileInputStream = new FileInputStream("Objeto.dat");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

         resultado= (Resultado)objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();
    } catch (FileNotFoundException ex) {
        ex.printStackTrace();
    } catch (IOException ex) {
        ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
        ex.printStackTrace();
    }
    return resultado;
}
}
