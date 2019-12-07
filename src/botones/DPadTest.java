/*
 *    Proyecto Pro-Planeta 1.0
 *    Videojuego en Java construido con JavaFX 11
 *    Autores: Castañón Puga Manuel, Vera Arias Victor Manuel, Feng Haosheng, Meléndez Lineros Leonardo
 *    Correo electrónico: {puga, victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonóma de Baja California
 *    http://www.uabc.mx
 */

package botones;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DPadTest extends Application {

    private DPad dpad;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group grupo = new Group();
        Scene escena = new Scene(grupo, 1024, 600);


        dpad = new DPad(50, 400, 150, 150, "resources/dpad.png");


        Button buttonA = new Button(570, 400, 100, 100, "resources/botonA.png");
        buttonA.setName("A");
        buttonA.setTestEventHandlers();

        Button buttonB = new Button(450, 450, 100, 100, "resources/botonB.png");
        buttonB.setName("B");
        buttonB.setTestEventHandlers();

        grupo.getChildren().add(dpad);
        grupo.getChildren().add(buttonA);
        grupo.getChildren().add(buttonB);

        primaryStage.setScene(escena);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.centerOnScreen();
        primaryStage.requestFocus();
        primaryStage.setTitle("PPP - Pepenadores Post 'Pocalipticos");

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
