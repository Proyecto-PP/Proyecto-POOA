/*
 *    Proyecto Pro-Planeta 1.0
 *    Videojuego en Java construido con JavaFX 11
 *    Autores: Castañón Puga Manuel, Vera Arias Victor Manuel, Feng Haosheng, Meléndez Lineros Leonardo
 *    Correo electrónico: {puga, victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonóma de Baja California
 *    http://www.uabc.mx
 */

package teclado;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

public abstract class TecladoFX {

    private EventHandler keyPressHandler;
    private EventHandler keyReleaseHandler;

    //Son arrays de strings y sus booleanos respectivos. No hice un solo array de una clase que contuviera el string y
    //su estado por que entonces deberia instanciar un objeto de esa clase CADA VEZ que tuviera que realizar alguna comprobacion
    //o movimiento en el array.
    private ArrayList<String> keysToListen;
    private ArrayList<Boolean> keyStatus;

    public TecladoFX() {

        keysToListen = new ArrayList<>();
        keyStatus = new ArrayList<>();

        //Si presionas cualquier teclas
        keyPressHandler = (EventHandler<KeyEvent>) event -> {
            //Si la tecla esta en el array de las teclas registradas, entonces le dice al teclado que esa tecla esta activa.
            actionOnPress(event);
        };

        //Si sueltas una tecla...
        keyReleaseHandler = (EventHandler<KeyEvent>) event -> {
            //Si esta en el array de teclas registradas entonces pone que esa tecla esta deshabilitada.
            actionOnRelease(event);
        };

    }

    //Debes pasarle la escena para añadir los handlers
    public void setKeyboardOnScene(Scene scene) {
        scene.setOnKeyPressed(keyPressHandler);
        scene.setOnKeyReleased(keyReleaseHandler);
    }

    public abstract void actionOnPress(KeyEvent event);
    public abstract void actionOnRelease(KeyEvent event);

    public void addKey(String key) {
        //El KeyCode para el evento SI diferencia entre mayusculas y minusculas. Volver cualquiera a mayusculas lo hace indiferente a esos casos.
        //Se podria evitar la llamada a toUpperCase UNICAMENTE si tenemos cuidado haciendo tanto las verificaciones como
        //las asignaciones en mayusculas O minusculas, pero no mezcladas.
        key = key.toUpperCase();

        //Si la tecla no esta ya registrada...
        if(!keysToListen.contains(key)) {
            //Se registra la tecla an el arraylist.
            keysToListen.add(key);

            //Y se agrego un booleano al mismo tiempo que una tecla para relacionar el estado con el
            //mismo indice que usa la tecla en su array respectivo.
            keyStatus.add(false);
        }
    }

    private void setKeyPressed(String key, boolean pressed) {
        int index = keysToListen.indexOf(key.toUpperCase());

        //Si la tecla esta registrada dentro del arraylist
        if(index != -1) {
            //Se modifica el estado
            keyStatus.set(index, pressed);
        }
    }

    public boolean isKeyPressed(String key) {
        int index = keysToListen.indexOf(key.toUpperCase());

        //Si la tecla esta registrada dentro del arraylist
        if(index != -1) {
            //Se devuelve su estado
            return keyStatus.get(index);
        }

        //Si no, se devuelve siempre false
        return false;
    }

    public void addKeys(String[] keys) {
        for(String k : keys) {
            addKey(k);
        }
    }

}
