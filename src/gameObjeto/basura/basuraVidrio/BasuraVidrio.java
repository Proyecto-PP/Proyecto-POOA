package gameObjeto.basura.basuraVidrio;

import gameObjeto.basura.Basura;

public abstract class BasuraVidrio extends Basura {
    public BasuraVidrio(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public BasuraVidrio(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }
}
