package resourceLoaders;

import javafx.scene.media.Media;

import java.io.File;

public final class AudioLoader {

    public static final Media persona5Song = getMedia("resources/sounds/music/p5.mp3");



    private static Media getMedia(String filePath) {
        return new Media(new File(filePath).toURI().toString());
    }
}
