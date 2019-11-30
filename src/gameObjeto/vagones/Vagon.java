package gameObjeto.vagones;

import entidades.IsometricEntity;
import entidades.MovingIsoEntity;
import gameObjeto.basura.Basura;

public abstract class Vagon extends IsometricEntity {

    public Vagon(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public Vagon(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public Vagon(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    public abstract void procesaBasura(Basura basura);

}
