package gameObjeto.vagones;

import gameObjeto.basura.Basura;
import gameObjeto.basura.basuraPlastico.BasuraPlastico;
import gameObjeto.basura.basuraVidrio.BasuraVidrio;
import sample.Main;

public class VagonVidrio extends Vagon {
    public VagonVidrio(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public VagonVidrio(double x, double y, double width, double height, double hitboxSize) {
        super(x, y, width, height, hitboxSize);
    }

    public VagonVidrio(double x, double y, double width, double height, double hitboxX, double hitboxY, double hitboxWidth, double hitboxHeight) {
        super(x, y, width, height, hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    @Override
    public void procesaBasura(Basura basura) {
        int puntaje = -20;

        if(basura instanceof BasuraVidrio) {
            puntaje = 20;
        }

        if(puntaje>0)
        {
            Main.setPuntaje(Main.getPuntaje() + puntaje);
        }
    }
}
