package gameObjeto.basura.basuraPlastico;

import gameObjeto.basura.Basura;

public abstract class BasuraPlastico extends Basura {
    public BasuraPlastico(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public BasuraPlastico(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }
}
