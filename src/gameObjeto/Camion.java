package gameObjeto;

import entidades.MovingIsoEntity;
import sample.Main;

public class Camion extends MovingIsoEntity {


    private float gasolina=1000;

    public Camion(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public Camion( double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super( x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    public float getGasolina() {
        return gasolina;
    }

    public void setGasolina(float gasolina) {
        this.gasolina = gasolina;
    }

    @Override
    public void move() {
        if(gasolina>0)
        {
            if(Main.getJugador().getX()+Main.getJugador().getWidth()>600&&Main.getDx()>0)
            {
                setX(getX() -0.2);
                setHitboxX(getHitboxX() - 0.2);
                gasolina-=0.16;
            }
            else if(Main.getDx()>0)
            {
                setX(getX() -0.08);
                setHitboxX(getHitboxX() - 0.08);
                gasolina-=0.16;
            }
            else if(Main.getDx()<0)
            {
                setX(getX() + 0.32);
                setHitboxX(getHitboxX() + 0.32);
                gasolina-=0.16;
            }
            else
            {
                setX(getX() + 0.16);
                setHitboxX(getHitboxX() + 0.16);
                gasolina-=0.16;
            }
        }
    }
}
