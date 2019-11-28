package entidades;


public abstract class MovingEntity extends Entity implements Moveable {

    public MovingEntity(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}