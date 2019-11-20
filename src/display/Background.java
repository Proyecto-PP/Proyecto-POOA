package display;

import javafx.scene.image.Image;
import resourceLoaders.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {
    public static int[][] GAME_MAP={{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //0
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
    BufferedImage backgroundMap;
    public Background(){
        backgroundMap = buildMap();
    }

    private BufferedImage buildMap(){
        int screenWidth = 1024;
        int screenHeight = 600;

        BufferedImage bg = new BufferedImage(screenWidth,screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bg.createGraphics();
    /*
        for(int i = 0; i<GAME_MAP.length; i++){
            for(int j = 0; j<GAME_MAP[0].length; j++)
              //  g.drawImage(ImageLoader.TILES[GAME_MAP[i][j]],  j*ImageLoader.TILES[0].getWidth(), 0);
        }
        g.dispose();
*/
        return bg;
    }
}
