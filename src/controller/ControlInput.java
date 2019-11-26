package controller;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public final class ControlInput {

    private static ArrayList<KeyCode> buttons;
    private static ArrayList<Boolean> buttonStatus;

    static {
        buttons = new ArrayList<>();
        buttonStatus = new ArrayList<>();
    }

    public static void addButton(String button) {
        KeyCode kc = getKeyCode(button);

        if(kc != null) {
            buttons.add(kc);
            buttonStatus.add(false);
        }
    }

    public static boolean isButtonPressed(String button) {
        int index = buttons.indexOf(getKeyCode(button));

        if(index != -1) {
            return buttonStatus.get(index);
        }

        return false;
    }

    public static void setButtonPressed(String button, boolean pressed) {
        int index = buttons.indexOf(getKeyCode(button));

        //Si la tecla esta registrada dentro del arraylist
        if(index != -1) {
            //Se modifica el estado
            buttonStatus.set(index, pressed);
        }
    }

    public static void setButtonPressed(KeyCode keyCode, boolean pressed) {
        int index = buttons.indexOf(keyCode);

        //Si la tecla esta registrada dentro del arraylist
        if(index != -1) {
            //Se modifica el estado
            buttonStatus.set(index, pressed);
        }
    }

    public static void setFalace() {
        for(int i = 0; i < buttons.size(); i++)
            buttonStatus.set(i, false);
    }

    private static String normalizeString(String string) {       //pIPO
        if(string != null) {
            String firstChar = string.substring(0, 1);          //p
            firstChar = firstChar.toUpperCase();        //P

            if (string.length() > 1)
                string = firstChar.concat(string.substring(1).toLowerCase());        //P + ipo
        }

        return string;
    }

    private static KeyCode getKeyCode(String keyCode) {
        return KeyCode.getKeyCode(normalizeString(keyCode));
    }

}