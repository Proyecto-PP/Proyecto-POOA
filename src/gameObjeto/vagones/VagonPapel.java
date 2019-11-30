package gameObjeto.vagones;

import gameObjeto.basura.Basura;
import gameObjeto.basura.basuraPapel.BasuraPapel;
import gameObjeto.basura.basuraPlastico.BasuraPlastico;
import sample.Main;

public class VagonPapel extends Vagon {
    public VagonPapel(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public VagonPapel(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public VagonPapel(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    @Override
    public void procesaBasura(Basura basura) {
        int puntaje = -20;

        if(basura instanceof BasuraPapel) {
            puntaje = 20;
        }

        Main.setPuntaje(Main.getPuntaje() + puntaje);
    }
}
