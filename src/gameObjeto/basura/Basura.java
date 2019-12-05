/*
 *    Proyecto Pro-Planeta
 *    Videojuego en Java construido con JavaFX
 *    Autores: Vera Arias Victor Manuel, Feng Haosheng, Melendez Lineros Leonardo
 *    Correo electronico: {victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonoma de Baja California
 *    http://www.uabc.mx
 */

package gameObjeto.basura;

import entidades.MovingIsoEntity;
import gameObjeto.Camion;
import sample.Main;

public abstract class Basura extends MovingIsoEntity {
    private boolean nextToPlayer;       //Todos los atributos se inicializan en 0, false o null cuando se instancia un objetp.
    private boolean recogida;

    public Basura( double x, double y, double width, double height, double hitboxSize) {
        super( x, y, width, height, hitboxSize);
        setDx(-2);
    }

    public Basura( double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super( x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
        setDx(-2);
    }

    public boolean isNextToPlayer() {
        return nextToPlayer;
    }

    public void setNextToPlayer(boolean nextToPlayer) {
        this.nextToPlayer = nextToPlayer;
    }

    public boolean isRecogida() {
        return recogida;
    }

    public void setRecogida(boolean recogida) {
        this.recogida = recogida;
    }

    @Override
    public void move() {
        if(recogida)
        {
           setX(Main.getJugador().getX());
           setY(Main.getJugador().getY()-50);
           setHitboxX(Main.getJugador().getHitboxX());
           setHitboxY(Main.getJugador().getHitboxY()-50);
        }
        if(!recogida)
        {
            setX(getX() - Camion.SPEED);
            setHitboxX(getHitboxX() - Camion.SPEED);
        }
    }
}
