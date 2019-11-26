package resourceLoaders;

import display.AnimatedImage;
import javafx.scene.image.Image;

public final class ImageLoader {

    /*Las imagenes que utilicen ImageView para mostrarse DEBEN ser cargadas con el tamaño que deseas por default.
      Si usas imageview.setFitWidth/setFitHeight se hace un smooth automatico e irreversible*/

    public final static Image botonA = new Image("file:resources/sprites/controller/botonA.png", 100, 100, true, false);
    public final static Image botonB = new Image("file:resources/sprites/controller/botonB.png", 100, 100, true, false);
    public final static Image dpad = new Image("file:resources/sprites/controller/dpad.png", 150, 150, true, false);
    public final static Image paradoAbajo = new Image("file:resources//sprites//SpritePlayer//persona1.png");
    public final static Image paradoIzquierda = new Image("file:resources//sprites//SpritePlayer//persona5.png");
    public final static Image paradoDerecho = new Image("file:resources//sprites//SpritePlayer//persona9.png");
    public final static Image paradoArriba = new Image("file:resources//sprites//SpritePlayer//persona13.png");
    public  static AnimatedImage caminaArriba=new AnimatedImage();
    public  static AnimatedImage caminaIzquierda=new AnimatedImage();
    public  static AnimatedImage caminaAbajo=new AnimatedImage();
    public static AnimatedImage caminaderecho=new AnimatedImage();
    public final static Image spritePlastico =new Image("file:resources//sprites//objeto//vidrio.png");
    public final static Image spriteManzana =new Image("file:resources//sprites//objeto//manzana.png");
    public final static Image spritePeriodico =new Image("file:resources//sprites//objeto//Periodico.png");
    public final static Image spriteBanana =new Image("file:resources//sprites//objeto//banana.png");
    public final static Image spriteSandia =new Image("file:resources//sprites//objeto//sandia.png");
    public final static Image spriteBolaPapel =new Image("file:resources//sprites//objeto//bolaPapel.png");
    public final static Image spritePapelAvion =new Image("file:resources//sprites//objeto//papelAvion.png");
    public final static Image spriteFoco =new Image("file:resources//sprites//objeto//Foco.png");

    public final static Image spriteCamion=new Image("file:resources//sprites//objeto//Camion.png");
    public final static Image spriteBoteAzul= new Image("file:resources//sprites//objeto//BoteBasura.png");
    public final static Image[][] arrayImage = new Image[4][4];

    public static Image[] TILES;
    public static final int GRASS_TILE = 0;
    public static final int ROAD_TILE = 1;
    public static final int DIRT_TILE = 2;
    public static final int BRICK_TILE = 3;
    public static final int BLOCK_TILE = 4;

    static {
        TILES = new Image[5];
        TILES[GRASS_TILE] = new Image("file:resources//sprites//tiles//grass.png", 50, 50, true, false);
        TILES[ROAD_TILE] = new Image("file:resources//sprites//tiles//road.png", 50, 50, true, false);
        TILES[DIRT_TILE] = new Image("file:resources//sprites//tiles//dirt.png", 50, 50, true, false);
        TILES[BRICK_TILE] = new Image("file:resources//sprites//tiles//brick.png", 50, 50, true, false);
        TILES[BLOCK_TILE] = new Image("file:resources//sprites//tiles//tile.png", 50, 50, true, false);

        double duracion=0.2;

        for(int i=0;i<4;i++)
        {
            arrayImage[0][i]= new Image("file:resources//sprites//SpritePlayer//persona"+(i+1)+".png");
            arrayImage[1][i]= new Image("file:resources//sprites//SpritePlayer//persona"+(i+5)+".png");
            arrayImage[2][i]= new Image("file:resources//sprites//SpritePlayer//persona"+(i+9)+".png");
            arrayImage[3][i]= new Image("file:resources//sprites//SpritePlayer//persona"+(i+13)+".png");
        }
        caminaAbajo.frames=arrayImage[0];
        caminaIzquierda.frames=arrayImage[1];
        caminaderecho.frames=arrayImage[2];
        caminaArriba.frames=arrayImage[3];
        caminaIzquierda.duration=duracion;
        caminaderecho.duration=duracion;
        caminaArriba.duration=duracion;
        caminaAbajo.duration=duracion;

    }



}
