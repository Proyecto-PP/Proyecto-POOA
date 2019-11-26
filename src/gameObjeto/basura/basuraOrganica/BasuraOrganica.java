package gameObjeto.basura.basuraOrganica;

import gameObjeto.basura.Basura;

public abstract class BasuraOrganica extends Basura {
    public BasuraOrganica(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public BasuraOrganica(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }
}
