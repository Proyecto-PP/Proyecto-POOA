package gameObjeto;

import entidades.MovingIsoEntity;
import sample.Main;

public class Camion extends MovingIsoEntity {

    private int distance;
    private float gasolina=1000;
    public static final int SPEED = 2;

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
            distance += SPEED;
            gasolina -= 0.16 ;
        }
    }

    public int getDistance() {
        return distance;
    }
}
