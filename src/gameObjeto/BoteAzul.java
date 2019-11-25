package gameObjeto;

import entidades.IsometricEntity;
import entidades.MovingIsoEntity;
import sample.Main;

public class BoteAzul extends MovingIsoEntity {

    public BoteAzul(String name, double x, double y, double width, double height, double hitboxSize) {
        super(name, x, y, width, height, hitboxSize);
    }

    public BoteAzul(String name, double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(name, x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    @Override
    public void move() {
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
