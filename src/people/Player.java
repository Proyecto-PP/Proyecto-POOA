/*
 *    Proyecto Pro-Planeta
 *    Videojuego en Java construido con JavaFX
 *    Autores: Vera Arias Victor Manuel, Feng Haosheng, Melendez Lineros Leonardo
 *    Correo electronico: {victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonoma de Baja California
 *    http://www.uabc.mx
 */

package people;

import entidades.MovingIsoEntity;
import enums.Direccion;
import gameObjeto.basura.Basura;

public class Player extends MovingIsoEntity {
    private static int vida=3;
    private Direccion direccion;
    private boolean colisionado;
    private boolean cargandoBasura;
    private Basura basura;

    private boolean dashing;
    public static final double SPEED = 4;

    public Player( double x, double y, double width, double height, double hitboxSize) {
        super( x, y, width, height, hitboxSize);
        direccion = Direccion.derecha;
        colisionado=false;
        cargandoBasura =false;
    }

    public Player( double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super( x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
        direccion = Direccion.abajo;
        colisionado=false;
        cargandoBasura =false;
    }


    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public boolean isColisionado() {
        return colisionado;
    }

    public void setColisionado(boolean colisionado) {
        this.colisionado = colisionado;
    }

    public boolean isCargandoBasura() {
        return cargandoBasura;
    }

    public void setCargandoBasura(boolean cargandoBasura) {
        this.cargandoBasura = cargandoBasura;
    }

    @Override
    public void move() {    //Movimiento
            setX(getX() + getDx());
            setY(getY() + getDy());
            setHitboxX(getHitboxX() + getDx());
            setHitboxY(getHitboxY() + getDy());
    }


    public boolean isDashing() {
        return dashing;
    }

    public void setDashing(boolean dashing) {
        this.dashing = dashing;
    }

    public Basura getBasura() {
        return basura;
    }

    public void setBasura(Basura basura) {
        this.basura = basura;
    }
}
