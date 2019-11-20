package gameObjeto;

import entidades.MovingIsoEntity;
import sample.Main;

public class Basura extends MovingIsoEntity {
    private boolean collision;
    private boolean moving;

    public Basura(String name, double x, double y, double width, double height, double hitboxSize) {
        super(name, x, y, width, height, hitboxSize);
        moving=false;
        collision=false;
    }

    public Basura(String name, double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(name, x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
        moving=false;
        collision=false;
    }


    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
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
           setHitboxX(Main.getJugador().getX());
           setHitboxY(Main.getJugador().getY()-50);
        }
        if(!moving)
        {
            if(Main.getDx()>0)
            {
                setX(getX()-0.16);
                setHitboxX(getHitboxX()-0.16);
            }
            else
                if(Main.getDx()<0)
                {
                    setX(getX()+0.16);
                    setHitboxX(getHitboxX()+0.16);
                }
        }

    }
}
