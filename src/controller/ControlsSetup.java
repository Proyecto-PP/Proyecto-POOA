/*
 *    Proyecto Pro-Planeta 1.0
 *    Videojuego en Java construido con JavaFX 11
 *    Autores: Castañón Puga Manuel, Vera Arias Victor Manuel, Feng Haosheng, Meléndez Lineros Leonardo
 *    Correo electrónico: {puga, victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonóma de Baja California
 *    http://www.uabc.mx
 */

package controller;

import sample.Main;
import botones.Button;
import resourceLoaders.ImageLoader;
import teclado.TecladoFX;
import gameObjeto.basura.Basura;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TouchEvent;

public final class ControlsSetup {

    private static Button dpad;
    private static Button botonA;
    private static Button botonB;

    private static TecladoFX teclado;

    static {

        ControlInput.addButton("S");        //Boton B
        ControlInput.addButton("D");        //Boton para probar el dash.
        ControlInput.addButton("Left");        //Boton para probar el dash.
        ControlInput.addButton("Right");        //Boton para probar el dash.
        ControlInput.addButton("Up");        //Boton para probar el dash.
        ControlInput.addButton("Down");        //Boton para probar el dash.



        botonA = new Button(904, 430, ImageLoader.botonA.getWidth(), ImageLoader.botonA.getHeight(), ImageLoader.botonA);
        botonA.setOpacity(0.65);
        botonA.setOnTouchPressed(event -> {
            botonA.setOpacity(1.0);
            //Lo que quieres que haga el boton A
            ControlInput.setButtonPressed("S", true);
        });
        botonA.setOnTouchReleased(event -> {
            botonA.setOpacity(0.65);
            //Lo que quieres que haga el boton A cuando se suelta.
            ControlInput.setButtonPressed("S", false);

        });


        botonB = new Button(784, 480, ImageLoader.botonB.getWidth(), ImageLoader.botonB.getHeight(), ImageLoader.botonB);
        botonB.setOpacity(0.65);
        botonB.setOnTouchPressed(event -> {
            botonB.setOpacity(1.0);
            //Lo que quieres que haga el boton B
            ControlInput.setButtonPressed("D", true);
        });
        botonB.setOnTouchReleased(event -> {
            botonB.setOpacity(0.65);
            //Lo que quieres que haga el boton B cuando se suelta.
            ControlInput.setButtonPressed("D", false);
        });



        dpad = new Button(20, 430, ImageLoader.dpad.getWidth(), ImageLoader.dpad.getHeight(), ImageLoader.dpad);
        dpad.setOpacity(0.65);
        EventHandler te = event -> {
            dpad.setOpacity(1.0);

            double xTouchPoint = ((TouchEvent) event).getTouchPoint().getX();
            double yTouchPoint = ((TouchEvent) event).getTouchPoint().getY();

            if (xTouchPoint >= dpad.getX() && xTouchPoint <= dpad.getX() + dpad.getWidth() / 3) {

                ControlInput.setButtonPressed("Left", true);

                if (yTouchPoint >= dpad.getY() && yTouchPoint <= dpad.getY() + dpad.getHeight() / 3) {
                    //Izquierda arriba
                    ControlInput.setButtonPressed("Up", true);
                } else if (yTouchPoint >= dpad.getY() + dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + 2 * dpad.getHeight() / 3) {
                    //Izquierda
                    //Ya esta puesto hasta arriba, por eso no se repite en cada if.
                } else if (yTouchPoint >= dpad.getY() + 2 * dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + dpad.getHeight()) {
                    //Izquierda abajo
                    ControlInput.setButtonPressed("Down", true);
                }

            } else if (xTouchPoint >= dpad.getX() + 2 * dpad.getWidth() / 3 && xTouchPoint <= dpad.getX() + dpad.getWidth()) {

                ControlInput.setButtonPressed("Right", true);

                if (yTouchPoint >= dpad.getY() && yTouchPoint <= dpad.getY() + dpad.getHeight() / 3) {
                    //Derecha arriba
                    ControlInput.setButtonPressed("Up", true);
                } else if (yTouchPoint >= dpad.getY() + dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + 2 * dpad.getHeight() / 3) {
                    //Derecha

                } else if (yTouchPoint >= dpad.getY() + 2 * dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + dpad.getHeight()) {
                    //Derecha abajo
                    ControlInput.setButtonPressed("Down", true);
                }

            } else if (xTouchPoint >= dpad.getX() + dpad.getWidth() / 3 && xTouchPoint <= dpad.getX() + 2 * dpad.getWidth() / 3) {

                if (yTouchPoint >= dpad.getY() && yTouchPoint <= dpad.getY() + dpad.getHeight() / 3) {
                    //Arriba
                    ControlInput.setButtonPressed("Up", true);
                } else if (yTouchPoint >= dpad.getY() + dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + 2 * dpad.getHeight() / 3) {
                    //Centro
                } else if (yTouchPoint >= dpad.getY() + 2 * dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + dpad.getHeight()) {
                    //Abajo
                    ControlInput.setButtonPressed("Down", true);
                }

            }
        };
        dpad.setOnTouchPressed(te);
        dpad.setOnTouchMoved(te);
        dpad.setOnTouchReleased(event -> {
            dpad.setOpacity(0.65);
            //Lo que quieres que haga el dpad cuando se suelta.
            ControlInput.setFalace();
        });

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

    }

    public static Button getDpad() {
        return dpad;
    }

    public static Button getBotonA() {
        return botonA;
    }

    public static Button getBotonB() {
        return botonB;
    }

    public static TecladoFX getTeclado() {
        return teclado;
    }
}
