package archivoSeriazable;

import java.io.Serializable;

public class Resultado implements Serializable {

    private String name;
    private int puntaje;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }



}
