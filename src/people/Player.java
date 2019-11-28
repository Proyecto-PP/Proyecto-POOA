package people;

import entidades.MovingIsoEntity;
import resourceLoaders.ImageLoader;
import sample.Main;

public class Player extends MovingIsoEntity {
    private static int vida=3;
    public StatePlayer state;
    private boolean colisionado;
    private boolean ocupado;

    private boolean dashing;

    public static final double SPEED = 4;

    public Player( double x, double y, double width, double height, double hitboxSize) {
        super( x, y, width, height, hitboxSize);
        state=StatePlayer.abajo;
        colisionado=false;
        ocupado=false;
    }

    public Player( double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super( x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
        state=StatePlayer.abajo;
        colisionado=false;
        ocupado=false;
    }


    public StatePlayer getState() {
        return state;
    }

    public void setState(StatePlayer state) {
        this.state = state;
    }

    public boolean isColisionado() {
        return colisionado;
    }

    public void setColisionado(boolean colisionado) {
        this.colisionado = colisionado;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    @Override
    public void move() {

        if(getX() + Main.getDx() < 0){
            setX(0);
            setHitboxX(0);
        }
        else if(getY() + Main.getDy() < 0){
            setY(0);
            setHitboxY(0);
        }
        else if(getX() + Main.getDx() > Main.WIDTH - ImageLoader.paradoArriba.getWidth()){
            setX(1024- ImageLoader.paradoArriba.getWidth());
            setHitboxX(1024- ImageLoader.paradoArriba.getWidth());
        }
        else if(getY() + Main.getDy() > Main.HEIGHT - ImageLoader.paradoArriba.getHeight()){
            setY(600 - ImageLoader.paradoArriba.getHeight());
            setHitboxY(600- ImageLoader.paradoArriba.getHeight());
        }
        else{
            setX(getX() + Main.getDx());
            setY(getY() + Main.getDy());
            setHitboxX(getHitboxX() + Main.getDx());
            setHitboxY(getHitboxY() + Main.getDy());
        }

    }


    public boolean isDashing() {
        return dashing;
    }

    public void setDashing(boolean dashing) {
        this.dashing = dashing;
    }

}
