package controller;

import botones.Button;
import gameObjeto.Basura;
import javafx.event.EventHandler;
import javafx.scene.input.TouchEvent;
import people.StatePlayer;
import resourceLoaders.ImageLoader;
import sample.Main;

public final class TouchControl {

    private static Button dpad;
    private static Button botonA;
    private static Button botonB;


    static {
        botonA = new Button(904, 430, ImageLoader.botonA.getWidth(), ImageLoader.botonA.getHeight(), ImageLoader.botonA);
        botonA.setOpacity(0.65);
        botonA.setOnTouchPressed(event -> {
            botonA.setOpacity(1.0);
            //Lo que quieres que haga el boton A
        });
        botonA.setOnTouchReleased(event -> {
            botonA.setOpacity(0.65);
            //Lo que quieres que haga el boton A cuando se suelta.
            if(!Main.getJugador().isOcupado())
            {
                for (Basura basura:
                        Main.getArrayBasura().getArrayBasura()) {
                    if(basura.isNextToPlayer())
                    {
                        basura.setMoving(true);

                        Main.getJugador().setOcupado(true);
                    }
                }
            }
        });


        botonB = new Button(784, 480, ImageLoader.botonB.getWidth(), ImageLoader.botonB.getHeight(), ImageLoader.botonB);
        botonB.setOpacity(0.65);
        botonB.setOnTouchPressed(event -> {
            botonB.setOpacity(1.0);
            //Lo que quieres que haga el boton B
        });
        botonB.setOnTouchReleased(event -> {
            botonB.setOpacity(0.65);
            //Lo que quieres que haga el boton B cuando se suelta.
            if(Main.getJugador().isOcupado())
            {
                for (Basura basura:
                        Main.getArrayBasura().getArrayBasura()){
                    if(basura.isMoving())
                    {
                        basura.setMoving(false);
                        //basura.setCollision(false);
                        Main.getJugador().setOcupado(false);
                    }
                }
            }

        });



        dpad = new Button(20, 430, ImageLoader.dpad.getWidth(), ImageLoader.dpad.getHeight(), ImageLoader.dpad);
        dpad.setOpacity(0.65);
        EventHandler te = event -> {
            double xTouchPoint = ((TouchEvent) event).getTouchPoint().getX();
            double yTouchPoint = ((TouchEvent) event).getTouchPoint().getY();

            dpad.setOpacity(1.0);

            if (xTouchPoint >= dpad.getX() && xTouchPoint <= dpad.getX() + dpad.getWidth() / 3) {

                if (yTouchPoint >= dpad.getY() && yTouchPoint <= dpad.getY() + dpad.getHeight() / 3) {
                    //Izquierda arriba
                    Main.setDx(-1.5);
                    Main.setDy(-1);
                    Main.getJugador().setState(StatePlayer.arriba);
                    System.out.println("Izquierda arriba");
                } else if (yTouchPoint >= dpad.getY() + dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + 2 * dpad.getHeight() / 3) {
                    //Izquierda
                    Main.setDx(-2);
                    Main.setDy(0);
                    Main.getJugador().setState(StatePlayer.izquierda);
                    System.out.println("Izquierda");
                } else if (yTouchPoint >= dpad.getY() + 2 * dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + dpad.getHeight()) {
                    //Izquierda abajo
                    Main.setDx(-1.5);
                    Main.setDy(1);
                    Main.getJugador().setState(StatePlayer.abajo);
                    System.out.println("Izquierda abajo");
                }

            } else if (xTouchPoint >= dpad.getX() + 2 * dpad.getWidth() / 3 && xTouchPoint <= dpad.getX() + dpad.getWidth()) {

                if (yTouchPoint >= dpad.getY() && yTouchPoint <= dpad.getY() + dpad.getHeight() / 3) {
                    //Derecha arriba
                    Main.setDx(1.5);
                    Main.setDy(-1);
                    Main.getJugador().setState(StatePlayer.arriba);
                    System.out.println("Derecha arriba");
                } else if (yTouchPoint >= dpad.getY() + dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + 2 * dpad.getHeight() / 3) {
                    //Derecha
                    Main.setDx(2);
                    Main.setDy(0);
                    Main.getJugador().setState(StatePlayer.derecha);
                    System.out.println("Derecha");
                } else if (yTouchPoint >= dpad.getY() + 2 * dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + dpad.getHeight()) {
                    //Derecha abajo
                    Main.setDx(1.5);
                    Main.setDy(1);
                    Main.getJugador().setState(StatePlayer.abajo);
                    System.out.println("Derecha abajo");
                }

            } else if (xTouchPoint >= dpad.getX() + dpad.getWidth() / 3 && xTouchPoint <= dpad.getX() + 2 * dpad.getWidth() / 3) {

                if (yTouchPoint >= dpad.getY() && yTouchPoint <= dpad.getY() + dpad.getHeight() / 3) {
                    //Arriba
                    Main.setDy(-2);
                    Main.setDx(0);
                    Main.getJugador().setState(StatePlayer.arriba);
                    System.out.println("Arriba");
                } else if (yTouchPoint >= dpad.getY() + dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + 2 * dpad.getHeight() / 3) {
                    //Centro
                } else if (yTouchPoint >= dpad.getY() + 2 * dpad.getHeight() / 3 && yTouchPoint <= dpad.getY() + dpad.getHeight()) {
                    //Abajo
                    Main.setDy(2);
                    Main.setDx(0);
                    Main.getJugador().setState(StatePlayer.abajo);
                    System.out.println("Abajo");
                }

            }
        };
        dpad.setOnTouchPressed(te);
        dpad.setOnTouchMoved(te);
        dpad.setOnTouchReleased(event -> {
            dpad.setOpacity(0.65);
            //Lo que quieres que haga el dpad cuando se suelta.
            Main.setDx(0);
            Main.setDy(0);
        });


    }

    public static Button getDpad() {
        return dpad;
    }

    public static void setDpad(Button dpad) {
        TouchControl.dpad = dpad;
    }

    public static Button getBotonA() {
        return botonA;
    }

    public static void setBotonA(Button botonA) {
        TouchControl.botonA = botonA;
    }

    public static Button getBotonB() {
        return botonB;
    }

    public static void setBotonB(Button botonB) {
        TouchControl.botonB = botonB;
    }
}
