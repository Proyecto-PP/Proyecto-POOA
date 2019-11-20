package entidades;


public abstract class MovingEntity extends Entity implements Moveable {

    public MovingEntity(String name,double x, double y, double width, double height) {
        super(name,x, y, width, height);
    }
}