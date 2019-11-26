package gameObjeto.basura.basuraPapel;

public class BasuraBolaPapel extends BasuraPapel{
    public BasuraBolaPapel(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public BasuraBolaPapel(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }
}
