/*
 *    Proyecto Pro-Planeta 1.0
 *    Videojuego en Java construido con JavaFX 11
 *    Autores: Castañón Puga Manuel, Vera Arias Victor Manuel, Feng Haosheng, Meléndez Lineros Leonardo
 *    Correo electrónico: {puga, victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonóma de Baja California
 *    http://www.uabc.mx
 */

package entidades;

public abstract class IsometricEntity extends Entity {

    private double hitboxX;
    private double hitboxY;
    private double hitboxWidth;
    private double hitboxHeight;

    public IsometricEntity(double x, double y, double width, double height) {

        super(x, y, width, height);

        this.hitboxX = x;
        this.hitboxY = y;
        this.hitboxWidth = width;
        this.hitboxHeight = height;
    }

    public IsometricEntity(double x, double y, double width, double height, double hitboxSize) {

        super(x, y, width, height);

        if(hitboxSize > 0 && hitboxSize <= 1) {
            /*  Define el tamaño que ocupa la caja de colisiones
                de la entidad desde su punto mas bajo para arriba.
            */

            this.hitboxX = x;
            this.hitboxY = (y + height) - height*hitboxSize;
            this.hitboxWidth = width;
            this.hitboxHeight = height*hitboxSize;
        } else {
            /*
                Se define una caja de colisiones de la mitad hacia abajo
                de la entidad.
             */
            this.hitboxX = x;
            this.hitboxY = (y + height) - height/2;
            this.hitboxWidth = width;
            this.hitboxHeight = height/2;
        }
    }

    public IsometricEntity(double x, double y, double width, double height,
                           double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {

        super(x, y, width, height);

        /*
            Define el tamaño que ocupa la caja de colisiones
            de la entidad.
       */

        this.hitboxX = hitboxX;
        this.hitboxY = hitboxY;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
    }

    public double getHitboxX() {
        return hitboxX;
    }

    public void setHitboxX(double hitboxX) {
        this.hitboxX = hitboxX;
    }

    public double getHitboxY() {
        return hitboxY;
    }

    public void setHitboxY(double hitboxY) {
        this.hitboxY = hitboxY;
    }

    public double getHitboxWidth() {
        return hitboxWidth;
    }

    public void setHitboxWidth(double hitboxWidth) {
        this.hitboxWidth = hitboxWidth;
    }

    public double getHitboxHeight() {
        return hitboxHeight;
    }

    public void setHitboxHeight(double hitboxHeight) {
        this.hitboxHeight = hitboxHeight;
    }


    /*
        Devuelve 1 en caso de que las coordenadas y datos enviados se encuentren
        en culaquier punto -dentro- de la hitbox de la entidad. De otro modo, devuelve 0.
    */
    @Override
    public int collisionsWith(double x, double y, double width, double height) {
        if ((x + width >= this.hitboxX && x <= this.hitboxX + this.hitboxWidth) &&
                (y + height >= this.hitboxY && y <= this.hitboxY + this.hitboxHeight)) {

            return 1;
        }

        return 0;
    }

    /*
        Devuelve 1 en caso de que las coordenadas y datos enviados se encuentren
        en culaquier punto justo a un lado de la hitbox de la entidad. De otro modo, devuelve 0.
        [No para esquinas] [A 2 pixeles de adyacencia se considera que una entidad esta "nextTo" (al lado de) esta entidad]
    */
    @Override
    public int nextTo(double x, double y, double width, double height, double range) {
        if( (x + width + range >= this.hitboxX && x - range <= this.hitboxX + this.hitboxWidth) &&
            (y + height + range >= this.hitboxY && y - range <= this.hitboxY + this.hitboxHeight)) {

            return 1;
        }

        return 0;
    }


}