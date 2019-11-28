package gameObjeto.boteBasura;

import entidades.MovingIsoEntity;
import sample.Main;

public abstract class BoteBasura extends MovingIsoEntity {

    public BoteBasura(double x, double y, double width, double height, double hitboxSize) {
        super( x, y, width, height, hitboxSize);
    }

    public BoteBasura(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    @Override
    public void move() {
        //setX( getX() + Camion.SPEED);
        //setHitboxX(getHitboxX() + Camion.SPEED);
        /*
        if(Main.getCamion().getGasolina()>0)
        {
            if(Main.getJugador().getX()+Main.getJugador().getWidth()>600&&Main.getDx()>0)
            {
                setX(getX() -0.2);
                setHitboxX(getHitboxX() - 0.2);

            }
            else if(Main.getDx()>0)
            {
                setX(getX() -0.08);
                setHitboxX(getHitboxX() - 0.08);

            }
            else if(Main.getDx()<0)
            {
                setX(getX() + 0.32);
                setHitboxX(getHitboxX() + 0.32);

            }
            else
            {
                setX(getX() + 0.16);
                setHitboxX(getHitboxX() + 0.16);

            }
        }

         */
    }
}
