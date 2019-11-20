package botones;

import botones.Button;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.File;


public class DPad extends Button {

    private final String path = "resources/";
    private Image img;
    private double xTouchPoint;
    private double yTouchPoint;

    public DPad(double x, double y, double width, double height, String imagePath) {
        super(x,y,width,height,imagePath);
    }

    //Se extiende la clase para poder poner el metodo con los eventos diferentes. De nuevo, estos son eventos de prueba,
    //para usar un boton con el funcionamiento que deseas, se deben sobreescribir los eventos directamente desde los objetos.

    @Override
    public void setTestEventHandlers() {

        EventHandler me = event -> {
            xTouchPoint = ((TouchEvent) event).getTouchPoint().getX();
            yTouchPoint = ((TouchEvent) event).getTouchPoint().getY();

            DPad.this.setOpacity(1.0);
            if (xTouchPoint >= getX() && xTouchPoint <= getX() + getFitWidth() / 3) {
                if (yTouchPoint >= getY() && yTouchPoint <= getY() + getFitHeight() / 3) {
                    //Izquierda arriba
                    System.out.println("Izquierda arriba");
                } else if (yTouchPoint >= getY() + getFitHeight() / 3 && yTouchPoint <= getY() + 2 * getFitHeight() / 3) {
                    //Izquierda
                    System.out.println("Izquierda");
                } else if (yTouchPoint >= getY() + 2 * getFitHeight() / 3 && yTouchPoint <= getY() + getFitHeight()) {
                    //Izquierda abajo
                    System.out.println("Izquierda abajo");
                }
            } else if (xTouchPoint >= getX() + 2 * getFitWidth() / 3 && xTouchPoint <= getX() + getFitWidth()) {
                if (yTouchPoint >= getY() && yTouchPoint <= getY() + getFitHeight() / 3) {
                    //Derecha arriba
                    System.out.println("Derecha arriba");
                } else if (yTouchPoint >= getY() + getFitHeight() / 3 && yTouchPoint <= getY() + 2 * getFitHeight() / 3) {
                    //Derecha
                    System.out.println("Derecha");
                } else if (yTouchPoint >= getY() + 2 * getFitHeight() / 3 && yTouchPoint <= getY() + getFitHeight()) {
                    //Derecha
                    System.out.println("Derecha abajo");
                }
            } else if (xTouchPoint >= getX() + getFitWidth() / 3 && xTouchPoint <= getX() + 2 * getFitWidth() / 3) {
                if (yTouchPoint >= getY() && yTouchPoint <= getY() + getFitHeight() / 3) {
                    //Arriba
                    System.out.println("Arriba");
                } else if (yTouchPoint >= getY() + getFitHeight() / 3 && yTouchPoint <= getY() + 2 * getFitHeight() / 3) {
                    //Centro
                } else if (yTouchPoint >= getY() + 2 * getFitHeight() / 3 && yTouchPoint <= getY() + getFitHeight()) {
                    //Abajo
                    System.out.println("Abajo");
                }
            }
        };

        setOnTouchPressed(me);
        setOnTouchMoved(me);

        setOnTouchReleased(event -> {
            setOpacity(0.65);
        });
    }
}


/*
presionas boton

metodo {
dashing = true;
dx = 2
animation timer
    if( i < 4)
        move
    else
        stop
        dashing = false;

    i++
}

en el dpad no lo dejas mover si dashing = true


 */