package sample;

import botones.Button;
import controller.TouchControl;

import display.Background;
import entidades.Entity;
import gameObjeto.ArrayBasura;
import gameObjeto.Basura;
import gameObjeto.BoteAzul;
import gameObjeto.Camion;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import people.Player;
import people.StatePlayer;
import reproductor.MusicPlayer;
import resourceLoaders.ImageLoader;

import java.util.*;


public class Main extends Application {

    private Canvas canvas=new Canvas(1024,600);
    private Group grupo;
    private GraphicsContext gc;
    private static StateGame stateGame;
    private Button botonA;
    private Button botonB;
    private Button dpad;
    final long starNanoTime=System.nanoTime();
    private static double dx;
    private static double dy;
    private int puntaje=0;
    Text texto =new Text();

    private static ArrayBasura arrayBasura=new ArrayBasura();
    private static Player jugador = new Player("jugador",100,100,32,48,0.5);
    private static Camion camion = new Camion("camion",100,200,200,100,0.5);
    private static BoteAzul boteAzul= new BoteAzul("boteAzul",50,250,50,50,1);
    Iterator<Basura> array=arrayBasura.getArrayBasura().iterator();
    List<Basura> remove=new ArrayList();
    private ArrayList<Entity> arrayEntidad=new ArrayList<>();
    private Background bg;



    @Override
    public void init() throws Exception
    {
        stateGame=StateGame.playing;
        initializeControls();
        initializeGroup();

        bg = new Background();
        texto.setX(0);
        texto.setY(40);
        texto.setFont(Font.font("Verdana",30));
        addComponet();
        arrayBasura.getArrayBasura().forEach(basura ->
        {
            arrayEntidad.add(basura);
        });
        arrayEntidad.add(jugador);
        arrayEntidad.add(camion);
        arrayEntidad.add(boteAzul);
        bg = new Background();

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setupPrimaryStage(primaryStage);
        gc=canvas.getGraphicsContext2D();
        primaryStage.setScene(new Scene(grupo));
        addComponet();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double t =(now-starNanoTime)/1000000000.0;
                updateLogic();
                updateGraphic(gc,t);
            }
        }.start();

        MusicPlayer reproductor = new MusicPlayer("resources/sounds/music/", "p5.mp3");
        reproductor.setVolume(0);
        reproductor.fadeToVolume(0.8);
        reproductor.play();

        primaryStage.show();
    }

//        Define propiedades basicas del stage.
    private void setupPrimaryStage(Stage primaryStage) {
        primaryStage.setAlwaysOnTop(true);
        primaryStage.centerOnScreen();
        primaryStage.requestFocus();
        primaryStage.setTitle("Proyecto - PP");
    }

    private void initializeControls() {
        botonA = TouchControl.getBotonA();
        botonB = TouchControl.getBotonB();
        dpad = TouchControl.getDpad();
    }

    public void updateLogic()
    {
        if(stateGame==StateGame.playing)
        {
            Collections.sort(arrayEntidad, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    if(o1.getY()+o1.getHeight()> o2.getY()+o2.getHeight())
                    {
                        return 1;
                    }
                    else if(o1.getY()+o1.getHeight()== o2.getY()+o2.getHeight())
                    {
                        return 0;
                    }
                    else
                    {
                        return -1;
                    }
                }
            });
            texto.setText("EL puntaje es:"+puntaje+"                                   Gasolina:"+String.format("%.1f",camion.getGasolina()/10));
            camion.move();
            jugador.move();
            boteAzul.move();
            arrayBasura.getArrayBasura().forEach(basura -> {
                basura.move();
                //collision de basura y jugador para agarra la basura
                if (basura.collisionsWith(jugador.getX() + dx, jugador.getY() + dy, jugador.getWidth(), jugador.getHeight()) == 1) {
                    jugador.setColisionado(true);
                    basura.setCollision(true);
                } else {
                    jugador.setColisionado(false);
                    basura.setCollision(false);
                }
                if(basura.collisionsWith(camion.getX(), camion.getY(), camion.getWidth(), camion.getHeight())==1)
                {   // si el camion esta chocando a una basura se agrega esa basura a la lista de elimina
                        remove.add(basura);
                }
                //collision con para sacar puntaje
                if(basura.isMoving())
                {
                    if (jugador.collisionsWith(boteAzul.getX(),boteAzul.getY(),boteAzul.getWidth(),boteAzul.getHeight())==1){
                        remove.add(basura);
                        camion.setGasolina(camion.getGasolina()+30);
                        puntaje++;
                        jugador.setOcupado(false);
                    }
                }
                if(basura.collisionsWith(jugador.getHitboxX()+dx,jugador.getHitboxY()+dy,jugador.getHitboxWidth(),jugador.getHitboxHeight())==1)
                {

                    dx=0;dy=0;
                }

            });
            //collision de jugador con el
            if(camion.collisionsWith(jugador.getHitboxX()+dx,jugador.getHitboxY()+dy,jugador.getHitboxWidth(),jugador.getHitboxHeight())==1)
            {
                dx=0;
                dy=0;
            }
            //Lista de elimianr (si es size de la lista eliminar se elimina)
            if(remove.size()>0)
            {
                arrayBasura.getArrayBasura().removeAll(remove);
                arrayEntidad.removeAll(remove);
                jugador.setOcupado(false);
            }
            //fin de juego
        }
        if(camion.getGasolina()<0)
        {
            setStateGame(StateGame.gameOver);
        }
        if(stateGame==StateGame.gameOver)
        {
            texto.setText("perdimos");
        }
    }

    public void updateGraphic(GraphicsContext gc,double t)
    {   gc.clearRect(0,0,1024,600);
        if(stateGame==StateGame.playing)
        {
           paintBackground(gc);
           arrayEntidad.forEach(objeto->
           {
               if(objeto.getName()=="jugador") paintPlayer(gc,t);
               else if(objeto.getName()=="basuraPlastico") gc.drawImage(ImageLoader.spritePlastico,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if (objeto.getName()=="camion") gc.drawImage(ImageLoader.spriteCamion, objeto.getX(),objeto.getY(), objeto.getWidth(), objeto.getHeight());
               else if(objeto.getName()=="boteAzul") gc.drawImage(ImageLoader.spriteBoteAzul,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
           });

        }
         if(stateGame==StateGame.gameOver)
        {
            texto.setText("Has perdido!!!!!!!!!!");
        }

    }

    private void initializeGroup() {

        //Aqui va la configuracion inicial del grupo
        grupo = new Group();

    }

    public void addComponet()
    {   grupo.getChildren().clear();
      grupo.getChildren().add(canvas);
        if(stateGame==StateGame.playing)
        {
            grupo.getChildren().addAll(botonA, botonB, dpad, texto);

        }
         if(stateGame==StateGame.gameOver)
        {

        }
    }

    private void paintBackground(GraphicsContext gc){
        gc.drawImage(Background.GAME_BG, -(jugador.getX()-100),0, 1024, 600);
        gc.drawImage(Background.GAME_BG, -(jugador.getX()-Background.MAP_WIDTH), 0, 1024, 600);

   //  gc.drawImage(Background.GAME_BG,0,0,1024,600);

    }


        public void paintPlayer(GraphicsContext gc,double t)
        {
            if (StatePlayer.abajo==jugador.getState()) {
                if(dy!=0)gc.drawImage(ImageLoader. caminaAbajo.getFrame(t),jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
                else gc.drawImage(ImageLoader.paradoAbajo,jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
            }
            else if(jugador.getState()==StatePlayer.arriba)
            {
                if (dy!=0) gc.drawImage(ImageLoader.caminaArriba.getFrame(t),jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
                else gc.drawImage(ImageLoader.paradoArriba,jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
            }
            else if(jugador.getState()==StatePlayer.derecha)
            {
                if (dx!=0) gc.drawImage(ImageLoader.caminaderecho.getFrame(t),jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
                else gc.drawImage(ImageLoader.paradoDerecho,jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
            }
            else if(jugador.getState()==StatePlayer.izquierda)
            {
                if(dx!=0) gc.drawImage(ImageLoader.caminaIzquierda.getFrame(t),jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
                else gc.drawImage(ImageLoader.paradoIzquierda,jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
            }


        }


    public static double getDx() {
        return dx;
    }

    public static void setDx(double dx) {
        Main.dx = dx;
    }

    public static double getDy() {
        return dy;
    }

    public static void setDy(double dy) {
        Main.dy = dy;
    }

    public static Player getJugador() {
        return jugador;
    }

    public static void setJugador(Player jugador) {
        Main.jugador = jugador;
    }

    public static ArrayBasura getArrayBasura() {
        return arrayBasura;
    }
    public static Camion getCamion() {
        return camion;
    }

    public static void setCamion(Camion camion) {
        Main.camion = camion;
    }
    public static StateGame getStateGame() {
        return stateGame;
    }

    public static void setStateGame(StateGame stateGame) {
        Main.stateGame = stateGame;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
