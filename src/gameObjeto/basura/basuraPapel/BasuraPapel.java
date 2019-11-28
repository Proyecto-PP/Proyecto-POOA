package gameObjeto.basura.basuraPapel;

import gameObjeto.basura.Basura;

public abstract class BasuraPapel extends Basura {
    public BasuraPapel(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public BasuraPapel(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }
}
