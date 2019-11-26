package gameObjeto.basura.basuraPapel;

public class BasuraPapelAvion extends BasuraPapel  {
    public BasuraPapelAvion(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public BasuraPapelAvion(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }
}
