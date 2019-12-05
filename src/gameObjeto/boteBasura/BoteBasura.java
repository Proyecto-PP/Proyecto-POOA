/*
 *    Proyecto Pro-Planeta
 *    Videojuego en Java construido con JavaFX
 *    Autores: Vera Arias Victor Manuel, Feng Haosheng, Melendez Lineros Leonardo
 *    Correo electronico: {victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonoma de Baja California
 *    http://www.uabc.mx
 */

package gameObjeto.boteBasura;

import entidades.MovingIsoEntity;

public abstract class BoteBasura extends MovingIsoEntity {

    public BoteBasura(double x, double y, double width, double height, double hitboxSize) {
        super( x, y, width, height, hitboxSize);
    }

    public BoteBasura(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    @Override
    public void move() {

    }
}
