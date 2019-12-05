/*
 *    Proyecto Pro-Planeta
 *    Videojuego en Java construido con JavaFX
 *    Autores: Vera Arias Victor Manuel, Feng Haosheng, Melendez Lineros Leonardo
 *    Correo electronico: {victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonoma de Baja California
 *    http://www.uabc.mx
 */

package entidades;

public abstract class MovingIsoEntity extends IsometricEntity implements Moveable{

    private double dx;
    private double dy;

    public MovingIsoEntity(double x, double y, double width, double height) {
        super( x, y, width, height);
    }

    public MovingIsoEntity( double x, double y, double width, double height, double hitboxSize) {
        super( x, y, width, height, hitboxSize);
    }

    public MovingIsoEntity( double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super( x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
