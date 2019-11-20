package entidades;


public abstract class MovingIsoEntity extends IsometricEntity implements Moveable{


    public MovingIsoEntity(String name, double x, double y, double width, double height) {
        super(name, x, y, width, height);
    }

    public MovingIsoEntity(String name, double x, double y, double width, double height, double hitboxSize) {
        super(name, x, y, width, height, hitboxSize);
    }

    public MovingIsoEntity(String name, double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(name, x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }
}
