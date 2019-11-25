package display;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import resourceLoaders.ImageLoader;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Background {
                                    // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,19,20
    private static int[][] GAME_MAP ={{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//0
                                      {0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0}, //1
                                      {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 1}, //2
                                      {3, 0, 3, 1, 0, 3, 3, 0, 3, 1, 0, 3, 0, 1, 0, 0, 3, 0, 0, 1, 0}, //3
                                      {3, 3, 3, 0, 3, 0, 3, 0, 2, 0, 2, 0, 3, 2, 3, 3, 0, 3, 2, 3, 3}, //4
                                      {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 2, 0, 3, 0, 3}, //5
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0}, //6
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //7
                                      {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //8
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //9
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //10
                                      {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};//11

    public static Image GAME_BG;
    public static int MAP_WIDTH;
    public static int MAP_HEIGHT;

    private int bgX;

    public Background(){
        GAME_BG = createBackground();
        MAP_WIDTH = (int) (GAME_MAP[0].length*ImageLoader.TILES[0].getWidth());
        MAP_HEIGHT = (int) (GAME_MAP.length*ImageLoader.TILES[0].getHeight());
    }

    private Image createBackground(){
        BufferedImage newImage = new BufferedImage(1024,600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        int tileWidth = (int) ImageLoader.TILES[0].getWidth();
        int tileHeight = (int) ImageLoader.TILES[0].getHeight();

        for(int i=0; i< GAME_MAP.length; i++){
            for(int j=0; j< GAME_MAP[0].length; j++){
                g.drawImage(SwingFXUtils.fromFXImage(ImageLoader.TILES[GAME_MAP[i][j]],null), null,j*tileWidth, i*tileHeight);
            }
        }
        return SwingFXUtils.toFXImage(newImage,null);
    }

    public void paintBackground(GraphicsContext gc){
        gc.drawImage(GAME_BG, bgX,0, 1024, 600);
        gc.drawImage(GAME_BG, bgX+1024, 0, 1024, 600);

    }

    public int getBackgroundX() {
        return bgX;
    }

    public void setBackgroundX(int distance) {
        if(distance % 1024 == 0)
            this.bgX = 0;
        else
            this.bgX -= 1;
    }
}
