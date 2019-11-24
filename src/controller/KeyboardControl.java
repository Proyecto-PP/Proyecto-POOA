package controller;

import people.StatePlayer;
import sample.Main;
import teclado.TecladoFX;

public final class KeyboardControl {

    private static TecladoFX teclado;

    static {
        teclado = new TecladoFX();

        teclado.addKey("A");        //Boton A
        teclado.addKey("S");        //Boton B
        teclado.addKeys(new String[]{"LEFT", "UP", "DOWN", "RIGHT"});
    }

    public static TecladoFX getTeclado() {
        return teclado;
    }

}
