package reproductor;

import javafx.animation.AnimationTimer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer {

    private MediaPlayer player;
    private Media song;

    public MusicPlayer() {

    }

    public MusicPlayer(String filePath) {
        if(filePath.startsWith("file:")) {
            //getMedia crea una File, la cual no utiliza el inicio de "file:" pare encontrar archivos.
            song = getMedia(filePath.substring(5));
        } else {
            song = getMedia(filePath);
        }

        player = new MediaPlayer(song);
    }

    public MusicPlayer(Media file) {
        song = file;
        player = new MediaPlayer(song);
    }

    public int play() {
        if(player != null && !isSongPLaying()) {
            player.play();
            return 0;
        }

        return -1;
    }

    public int pause() {
        if(player != null && !isSongPLaying()) {
            player.pause();
            return 0;
        }

        return -1;
    }

    public int stop() {
        if(player != null && isSongPLaying()) {
            player.stop();
            return 0;
        }

        return -1;
    }

    public int fadeInPlay(double volume) {
        if(!isSongPLaying()) {
            fadeToVolume(volume);
            play();
            return 0;
        }

        return -1;
    }

    public int fadeOutStop() {
        if(isSongPLaying()) {

            new Thread(() -> new AnimationTimer() {
                @Override
                public void handle(long now) {

                    if(player.getVolume() > 0.01) {
                        player.setVolume(player.getVolume() - 0.01);
                    } else {
                        player.stop();
                        stop();
                    }
                }
            }.start()).start();

            return 0;
        }

        return -1;
    }

    public boolean isSongPLaying() {
        if(player != null)
            return player.getStatus() == MediaPlayer.Status.PLAYING;

        return false;
    }

    public int setSong(String filePath) {
        if(isSongPLaying()) {
            player.stop();
        }

        try {
            if(filePath.startsWith("file:")) {
                //getMedia crea una File, la cual no utiliza el inicio de "file:" pare encontrar archivos.
                song = getMedia(filePath.substring(5));
            } else {
                song = getMedia(filePath);
            }
        } catch (NullPointerException e) {
            song = null;
        }

        if(song != null) {
            player = new MediaPlayer(song);
            return 0;
        }

        return -1;
    }

    public int setSongAndPlay(String filePath) {
        if(setSong(filePath) != -1) {
            player.play();
            return 0;
        }

        return -1;
    }

    public int setSong(Media file) {
        if(isSongPLaying()) {
            player.stop();
        }

        song = file;

        if(song != null) {
            player = new MediaPlayer(song);
            return 0;
        }

        return -1;
    }

    public int setSongAndPlay(Media file) {
        if(setSong(file) != -1) {
            player.play();
            return 0;
        }

        return -1;
    }

    public int setVolume(double volume) {
        if(volume >= 0.0 && volume <= 1.0) {
            player.setVolume(volume);
            return 0;
        }

        return -1;
    }

    public int fadeToVolume(double volume) {
        if(volume >= 0.0 && volume <= 1.0 && volume != player.getVolume()) {

            if(volume < player.getVolume()) {

                new Thread(() -> new AnimationTimer() {
                    @Override
                    public void handle(long now) {

                        if(player.getVolume() > volume) {
                            player.setVolume(player.getVolume() - volume/240);  //Fade en 4 segundos
                        } else {
                            stop();
                        }
                    }
                }.start()).start();

            } else if(volume > player.getVolume()) {

                new Thread(() -> new AnimationTimer() {
                    @Override
                    public void handle(long now) {

                        if(player.getVolume() < volume) {
                            player.setVolume(player.getVolume() + volume/240);  //Fade en 4 segundos
                        } else {
                            stop();
                        }
                    }
                }.start()).start();

            }

            return 0;
        }

        return -1;
    }

    public int createPlayer() {
        if(player == null && song != null) {
            player = new MediaPlayer(song);
            return 0;
        }

        return -1;
    }

    private Media getMedia(String filePath) {
        return new Media(new File(filePath).toURI().toString());
    }

}
