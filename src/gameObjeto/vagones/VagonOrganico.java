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
import gameObjeto.basura.basuraOrganica.BasuraOrganica;
import sample.Main;

public class VagonOrganico extends Vagon {
    public VagonOrganico(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public VagonOrganico(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public VagonOrganico(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    @Override
    public void procesaBasura(Basura basura) {
        if(basura instanceof BasuraOrganica) {
            Main.getCamion().setGasolina(Main.getCamion().getGasolina() + 200);
        }
    }

}
