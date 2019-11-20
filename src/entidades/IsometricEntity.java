package entidades;



public abstract class IsometricEntity extends Entity {

    private double hitboxX;
    private double hitboxY;
    private double hitboxWidth;
    private double hitboxHeight;

    public IsometricEntity(String name,double x, double y, double width, double height) {

        super(name,x, y, width, height);

        this.hitboxX = x;
        this.hitboxY = y;
        this.hitboxWidth = width;
        this.hitboxHeight = height;
    }

    public IsometricEntity(String name,double x, double y, double width, double height, double hitboxSize) {

        super(name,x, y, width, height);

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

    public IsometricEntity(String name,double x, double y, double width, double height,
                           double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {

        super(name,x, y, width, height);

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
        [No para esquinas]
    */
    @Override
    public int nextTo(double x, double y, double width, double height) {
        if( (x + width + 1 >= this.hitboxX && x - 1 <= this.hitboxX + this.hitboxWidth) &&
                (y + height + 1>= this.hitboxY && y - 1<= this.hitboxY + this.hitboxHeight)) {

            return 1;
        }

        return 0;
    }


}