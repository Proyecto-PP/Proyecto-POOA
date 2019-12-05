/*
 *    Proyecto Pro-Planeta
 *    Videojuego en Java construido con JavaFX
 *    Autores: Vera Arias Victor Manuel, Feng Haosheng, Melendez Lineros Leonardo
 *    Correo electronico: {victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonoma de Baja California
 *    http://www.uabc.mx
 */

package gameObjeto.vagones;

import gameObjeto.basura.Basura;
import gameObjeto.basura.basuraPapel.BasuraPapel;
import sample.Main;

public class VagonPapel extends Vagon {
    public VagonPapel(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public VagonPapel(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public VagonPapel(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    @Override
    public void procesaBasura(Basura basura) {
        int puntaje = -20;

        if(basura instanceof BasuraPapel) {
            puntaje = 20;
        }

       if(puntaje>0)
       {
           Main.setPuntaje(Main.getPuntaje() + puntaje);
       }
    }
}
