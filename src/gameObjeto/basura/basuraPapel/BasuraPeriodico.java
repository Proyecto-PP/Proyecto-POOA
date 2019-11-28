package gameObjeto.basura.basuraPapel;

import gameObjeto.basura.Basura;

public class BasuraPeriodico extends Basura {
    public BasuraPeriodico(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public BasuraPeriodico(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }
}
