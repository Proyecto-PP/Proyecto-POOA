/*
 *    Proyecto Pro-Planeta
 *    Videojuego en Java construido con JavaFX
 *    Autores: Vera Arias Victor Manuel, Feng Haosheng, Melendez Lineros Leonardo
 *    Correo electronico: {victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonoma de Baja California
 *    http://www.uabc.mx
 */

package botones;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Button extends ImageView {

    private Image img;
    private String name;
    private double width;
    private double height;

    public Button(double x, double y, double width, double height, String imagePath) {
        if(imagePath.startsWith("file:")) {
            img = new Image(imagePath, width, height, true, false);
        } else {
            img = new Image("file:" + imagePath, width, height, true, false);
        }

        setImage(img);

        this.width = width;
        this.height = height;

        setX(x);
        setY(y);
    }

    public Button(double x, double y, double width, double height, Image imagen) {
        img = imagen;
        setImage(img);

        this.width = width;
        this.height = height;

        setX(x);
        setY(y);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTestEventHandlers() {
        //Esto se usa para pruebas, si ocupas distintos botones con distintas funcionalidades entonces
        //debes declarar por separado y utlizado la referencia al objeto para definir la funcionalidad.
        setOnTouchPressed(event -> {
            setOpacity(1.0);
            System.out.println("Pressed " + name + "!");
            //Lo que quieres que haga cuando lo presionas.
        });

        setOnTouchReleased(event -> {
            setOpacity(0.65);
            System.out.println("Released " + name + "!");
            //Lo que quieres que haga cuando lo sueltas.
        });
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
