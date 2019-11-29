package gameObjeto.basura;

import entidades.MovingIsoEntity;
import gameObjeto.Camion;
import sample.Main;

public abstract class Basura extends MovingIsoEntity {
    private boolean nextToPlayer;       //Todos los atributos se inicializan en 0, false o null cuando se instancia un objetp.
    private boolean moving;

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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public void move() {
        if(moving)
        {
           setX(Main.getJugador().getX());
           setY(Main.getJugador().getY()-50);
           setHitboxX(Main.getJugador().getHitboxX());
           setHitboxY(Main.getJugador().getHitboxY()-50);
        }
        if(!moving)
        {
            setX(getX() + getDx());
            setHitboxX(getHitboxX() + getDx());
        }
    }
}
