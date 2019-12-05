/*
 *    Proyecto Pro-Planeta
 *    Videojuego en Java construido con JavaFX
 *    Autores: Vera Arias Victor Manuel, Feng Haosheng, Melendez Lineros Leonardo
 *    Correo electronico: {victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonoma de Baja California
 *    http://www.uabc.mx
 */

package resourceLoaders;

import javafx.scene.media.Media;
import java.io.File;

public final class AudioLoader {

    public static final Media persona5Song = getMedia("resources/sounds/music/LastSurprise.mp3");
    public static final Media niwaYumeGaAru = getMedia("resources/sounds/music/GiornoTheme.mp3");
    public static final Media bigBlue = getMedia("resources/sounds/music/BigBlue.mp3");
    public static final Media muteCity = getMedia("resources/sounds/music/MuteCity.mp3");

    private static Media getMedia(String filePath) {
        return new Media(new File(filePath).toURI().toString());
    }
}
