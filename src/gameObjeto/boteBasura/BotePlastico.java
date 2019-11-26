package gameObjeto.boteBasura;

public class BotePlastico extends BoteBasura {
    public BotePlastico(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public BotePlastico(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }
}
