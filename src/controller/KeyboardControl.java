package controller;

import javafx.scene.input.KeyEvent;
import people.StatePlayer;
import sample.Main;
import teclado.TecladoFX;

public final class KeyboardControl {

    private static TecladoFX teclado;

    static {
        teclado = new TecladoFX() {
            @Override
            public void actionOnPress(KeyEvent event) {
                ControlInput.setButtonPressed(event.getCode(), true);
            }

            @Override
            public void actionOnRelease(KeyEvent event) {
                ControlInput.setButtonPressed(event.getCode(), false);
            }
        };

        teclado.addKey("A");        //Boton A
        teclado.addKey("S");        //Boton B
        teclado.addKey("D");        //Boton para probar el dash.
        teclado.addKeys(new String[]{"LEFT", "UP", "DOWN", "RIGHT"});
    }

    public static TecladoFX getTeclado() {
        return teclado;
    }

}
