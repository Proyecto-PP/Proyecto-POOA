package people;

import entidades.MovingIsoEntity;
import sample.Main;

public class Player extends MovingIsoEntity {
    private static int vida=3;
    public StatePlayer state;
    private boolean colisionado;
    private boolean ocupado;

    public Player(String name, double x, double y, double width, double height, double hitboxSize) {
        super(name, x, y, width, height, hitboxSize);
        state=StatePlayer.abajo;
        colisionado=false;
        ocupado=false;
    }

    public Player(String name, double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(name, x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
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

        if(getX()+Main.getDx()>800)
        {

        }
        else
        {
            setX(getX() + Main.getDx());
            setY(getY() + Main.getDy());
            setHitboxX(getHitboxX() + Main.getDx());
            setHitboxY(getHitboxY() + Main.getDy());
        }

    }
}
