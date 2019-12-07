/*
 *    Proyecto Pro-Planeta 1.0
 *    Videojuego en Java construido con JavaFX 11
 *    Autores: Castañón Puga Manuel, Vera Arias Victor Manuel, Feng Haosheng, Meléndez Lineros Leonardo
 *    Correo electrónico: {puga, victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonóma de Baja California
 *    http://www.uabc.mx
 */

package botones;

import javafx.event.EventHandler;
import javafx.scene.input.TouchEvent;

public class DPad extends Button {

    private final String path = "resources/";
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