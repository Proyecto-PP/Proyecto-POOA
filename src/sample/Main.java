package sample;

import botones.Button;
import controller.ControlInput;
import controller.ControlsSetup;

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
import resourceLoaders.AudioLoader;
import resourceLoaders.ImageLoader;
import teclado.TecladoFX;

import java.util.*;


public class Main extends Application {

    public static final int WIDTH = 1024;       //Constantes para cambiar el size del canvas de manera remota.
    public static final int HEIGHT = 600;       //Podrian ser variables si queremos hacer que se escale el juego y sus elementos.
    //Pd. la resolucion COMPLETA de la tablet es 1280 x 800

    private Canvas canvas;
    private Group grupo;
    private Scene escena;
    private GraphicsContext gc;

    private MusicPlayer reproductor;

    private static StateGame stateGame;

    private Button botonA;
    private Button botonB;
    private Button dpad;
    private TecladoFX teclado;

    final long startNanoTime = System.nanoTime();

    private static double dx;
    private static double dy;
    public static final double DASH_SPEED_MULT = 4;
    private static int dashCooldown;        //En frames 60 frames - 1 seg
    private final double maxDashFrames = 10; //En Frames
    private double dashFrames;

    private int puntaje;        //puntaje, como es un atributo de clase, se inicializa en 0 por default.
    Text texto = new Text();

    /*
    *  ArrayBasura podria ser una clase estatica en la que se inicializara lo que ocupamos en un static block, llamamos a un metodo
    *  que devuelva un array de entidades que ya tenga todas las basuras. Asi te libras del foreach en initializeArrayEntidad. Ademas
    *  te libras de cosas redundantes como Main.getArrayBasura().getArrayBasura(); [TouchControl : 32]
    */
    private static ArrayBasura arrayBasura = new ArrayBasura();
    private static Player jugador = new Player("jugador", 100, 100, 32, 48, 0.5);
    private static Camion camion = new Camion("camion", 100, 200, 200, 100, 0.5);
    private static BoteAzul boteAzul = new BoteAzul("boteAzul", 50, 250, 50, 50, 1);
    Iterator<Basura> array = arrayBasura.getArrayBasura().iterator();
    List<Basura> remove = new ArrayList();
    private ArrayList<Entity> arrayEntidad;
    private Background bg;

    private Comparator cmpArrayEntidad;


    @Override
    public void init() throws Exception {
        stateGame = StateGame.playing;

        initializeGroup();
        initializeControls();
        initializeCanvas();
        initializeReproductor();
        initializeArrayEntidad();
        initializeUtilities();

        bg = new Background();
        texto.setX(0);
        texto.setY(40);
        texto.setFont(Font.font("Verdana", 30));

        addComponet();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupPrimaryStage(primaryStage);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double t = (now - startNanoTime) / 1000000000.0;
                updateLogic();
                updateGraphic(gc, t);
            }
        }.start();

        reproductor.fadeInPlay(0.6);

        primaryStage.show();
    }



    ////////////////////////////////////////////
    /*             Bloque de setup            */
    ////////////////////////////////////////////

    //        Define propiedades basicas del stage.
    private void setupPrimaryStage(Stage primaryStage) {
        primaryStage.centerOnScreen();
        primaryStage.requestFocus();
        primaryStage.setTitle("Proyecto - PP");
        primaryStage.setScene(escena);    //Uso una variable Scene para poder utilizar los listeners del teclado.
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

    }

    private void initializeControls() {
        botonA = ControlsSetup.getBotonA();
        botonB = ControlsSetup.getBotonB();
        dpad = ControlsSetup.getDpad();

        teclado = ControlsSetup.getTeclado();
        teclado.setKeyboardOnScene(escena);
    }

    private void initializeGroup() {
        //Aqui va la configuracion inicial del grupo y escena
        grupo = new Group();
        escena = new Scene(grupo, WIDTH, HEIGHT);
    }

    private void initializeCanvas() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();     //Muevo esto aqui por relacion al canvas.
    }

    private void initializeReproductor() {
        reproductor = new MusicPlayer(AudioLoader.muteCity);
        reproductor.setVolume(0);
    }

    private void initializeArrayEntidad() {
        arrayEntidad = new ArrayList<>();

        arrayBasura.getArrayBasura().forEach(basura ->
        {
            arrayEntidad.add(basura);
        });
        arrayEntidad.add(jugador);
        arrayEntidad.add(camion);
        arrayEntidad.add(boteAzul);
    }

    private void initializeUtilities() {

        //Comparador para el sort de arrayEntidad con Collection.sort
        cmpArrayEntidad = new Comparator<Entity>() {
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
        };
    }

    ////////////////////////////////////////////
    /*          Bloque de setup [FIN]         */
    ////////////////////////////////////////////



    public void updateLogic()
    {

        if(stateGame==StateGame.playing)
        {
            Collections.sort(arrayEntidad, cmpArrayEntidad);    //Instancie el comparador en el initializeUtilities para que no se cree uno nuevo cada vez.

            texto.setText("EL puntaje es:"+puntaje+"                                   Gasolina:"+String.format("%.1f",camion.getGasolina()/10));

            //Este es el metodo que determina que acciones estas tomando segun lo que el jugador tenga presionado.
            updatePlayerMovement();

            arrayBasura.getArrayBasura().forEach(basura -> {

                //collision de basura y jugador para agarra la basura
                if (basura.nextTo(jugador.getX(), jugador.getY(), jugador.getWidth(), jugador.getHeight()) == 1) {
                    basura.setNextToPlayer(true);
                } else {
                    basura.setNextToPlayer(false);
                }

                //Elimine la parte del codigo que eliminaba las basuras cuando chocaban con el camion.

                //collision con para sacar puntaje
                if (basura.isMoving()) {
                    if (jugador.collisionsWith(boteAzul.getHitboxX(), boteAzul.getHitboxY(), boteAzul.getHitboxWidth(), boteAzul.getHitboxHeight()) == 1) {
                        remove.add(basura);
                        camion.setGasolina(camion.getGasolina() + 30);
                        puntaje++;
                        jugador.setOcupado(false);
                    }
                }

                //Si el jugador va a chocar con la basura...
                if(basura.collisionsWith(jugador.getHitboxX()+dx,jugador.getHitboxY()+dy,jugador.getHitboxWidth(),jugador.getHitboxHeight())==1)
                {
                    //Entonces su cambio en x y y se vuelven 0
                    dx=0;
                    dy=0;

                    jugador.setDashing(false);
                }

                //Y solo es despues de todas estas comprobaciones que se mueve la basura.
                basura.move();

            });

            //Si el jugador colisiona con el camion...
            if(camion.collisionsWith(jugador.getHitboxX()+dx,jugador.getHitboxY()+dy,jugador.getHitboxWidth(),jugador.getHitboxHeight())==1)
            {
                //Se frena al jugador y
                dx=0;
                dy=0;

                jugador.setDashing(false);

                //Si el jugador NO esta en frente del camion:
                if( !(jugador.getHitboxX() > camion.getHitboxX() &&
                      jugador.getHitboxY() + jugador.getHitboxHeight() > camion.getHitboxY() &&
                      jugador.getHitboxY() < camion.getHitboxY() + camion.getHitboxHeight()) ) {

                    //Que se mueva el camion aunque este colisionando con el.
                    camion.move();
                    boteAzul.move();
                }
            } else {
                camion.move();
                boteAzul.move();
            }

            //Lista de elimianr (si es size de la lista eliminar se elimina)
            if(remove.size()>0)
            {
                arrayBasura.getArrayBasura().removeAll(remove);
                arrayEntidad.removeAll(remove);
                //Faltaba poner que se limpiara cada vez que eliminaba los objetos que hacia falta eliminar.
                remove.clear();
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

        //Esto debe ir al ultimo por que se deben realizar todas las validaciones antes de darle permiso de cambiar
        //su posicion.
        jugador.move();

    }

    private void updatePlayerMovement() {

        //Tanto el touch como el teclado comparten funcionamiento, y se realizan cambios segun lo que se tenga presionado
        //en este metodo.

        if(!jugador.isDashing()) {
            if (ControlInput.isButtonPressed("Up") && !ControlInput.isButtonPressed("DOWN")) {
                Main.setDx(0);
                Main.setDy(-2);

                Main.getJugador().setState(StatePlayer.arriba);
            }

            if (ControlInput.isButtonPressed("DOWN") && !ControlInput.isButtonPressed("UP")) {
                Main.setDx(0);
                Main.setDy(2);

                Main.getJugador().setState(StatePlayer.abajo);
            }

            if (ControlInput.isButtonPressed("RIGHT") && !ControlInput.isButtonPressed("LEFT")) {
                Main.setDx(2);
                Main.setDy(0);

                if (ControlInput.isButtonPressed("UP")) {
                    Main.setDx(1.42);
                    Main.setDy(-1.42);
                } else if (ControlInput.isButtonPressed("DOWN")) {
                    Main.setDx(1.42);
                    Main.setDy(1.42);
                }

                Main.getJugador().setState(StatePlayer.derecha);
            }

            if (ControlInput.isButtonPressed("LEFT") && !ControlInput.isButtonPressed("RIGHT")) {
                Main.setDx(-2);
                Main.setDy(0);

                if (ControlInput.isButtonPressed("UP")) {
                    Main.setDx(-1.42);
                    Main.setDy(-1.42);
                } else if (ControlInput.isButtonPressed("DOWN")) {
                    Main.setDx(-1.42);
                    Main.setDy(1.42);
                }

                Main.getJugador().setState(StatePlayer.izquierda);
            }

            if (ControlInput.isButtonPressed("RIGHT") && ControlInput.isButtonPressed("LEFT")) {
                Main.setDx(0);

                if (ControlInput.isButtonPressed("UP")) {
                    Main.setDy(-2);

                    Main.getJugador().setState(StatePlayer.arriba);
                } else if (ControlInput.isButtonPressed("DOWN")) {
                    Main.setDy(2);

                    Main.getJugador().setState(StatePlayer.abajo);
                } else {
                    Main.setDy(0);
                }
            }

            if (ControlInput.isButtonPressed("UP") && ControlInput.isButtonPressed("DOWN")) {
                Main.setDy(0);

                if (ControlInput.isButtonPressed("RIGHT")) {
                    Main.setDx(2);

                    Main.getJugador().setState(StatePlayer.derecha);
                } else if (ControlInput.isButtonPressed("LEFT")) {
                    Main.setDx(-2);

                    Main.getJugador().setState(StatePlayer.izquierda);
                } else {
                    Main.setDx(0);
                }
            }

            if (!ControlInput.isButtonPressed("UP") && !ControlInput.isButtonPressed("DOWN") &&
                    !ControlInput.isButtonPressed("LEFT") && !ControlInput.isButtonPressed("RIGHT")) {

                Main.setDx(0);
                Main.setDy(0);
            }


            //Esto es para agarrar y soltar basura
            if (ControlInput.isButtonPressed("S")) {

                if(!ControlInput.isAltButtonA()) {
                    if (!Main.getJugador().isOcupado()) {

                        for (Basura basura :
                                Main.getArrayBasura().getArrayBasura()) {
                            if (basura.isNextToPlayer() && !jugador.isOcupado()) {      //Mas de una basura se podia mover
                                jugador.setOcupado(true);                               //por que no consideramos que el jugador
                                basura.setMoving(true);                                 //podia volverse ocupado dentro de este mismo
                            }                                                           //ciclo.
                        }
                    } else {

                        for (Basura basura :
                                Main.getArrayBasura().getArrayBasura()) {
                            if (basura.isMoving() && jugador.isOcupado()) {
                                jugador.setOcupado(false);
                                basura.setMoving(false);
                            }
                        }
                    }

                    ControlInput.setAltButtonA(true);   //Es un switch, para saber si soltaron o no la tecla.
                }

            } else {
                ControlInput.setAltButtonA(false);
            }


            //Este es el dash
            if (ControlInput.isButtonPressed("D")) {
                if( (dx != 0 || dy != 0) && !ControlInput.isAltButtonB() && dashCooldown == 0) {     //Si se está moviendo hacia alguna direccion.
                    jugador.setDashing(true);
                    ControlInput.setAltButtonB(true);
                }
            } else {        //Necesitamos que pueda realizar su funcionalidad UNA vez hasta que lo vuelva a presionar.
                            //Con esto lo que hago es obligar al usuario a levantar el dedo de la tecla, y solo despues
                            //de que lo haga es que puede volver a utilizar el boton. Lo mismo con el de arriba.
                ControlInput.setAltButtonB(false);
            }

            if(dashCooldown > 0) {      //Contador para evitar que hagan dash seguidos.
                dashCooldown--;
            }

        } else {

            //Si el jugador está haciendo un dash no deberia de poder moverse por en medio.

            //Se usa un contador para saber cuanto tiempo estara en el estado del dash. Y hasta que no llegue
            //a un limite [maxDashFrames] se suma 1 a ese contador. Cuando dashFrames llega a maxDashFrames, se saca
            //al jugador del estado de "dashing" y se ponen 60 frames [1 segundo] de cooldown al dash.
            if(dashFrames != maxDashFrames) {
                if(dashFrames == 0) {
                    dx = dx * DASH_SPEED_MULT;
                    dy = dy * DASH_SPEED_MULT;
                }
                dashFrames++;
            } else {
                jugador.setDashing(false);
                dashFrames = 0;
                dashCooldown = 60;     //son 60 frames - 1 seg
            }
        }

    }

    public void updateGraphic(GraphicsContext gc,double t)
    {
        gc.clearRect(0,0,1024,600);
        if(stateGame==StateGame.playing)
        {
           paintBackground(gc);
           arrayEntidad.forEach(objeto->
           {
               if(objeto instanceof Player) paintPlayer(gc,t);
               else if(objeto instanceof Basura) gc.drawImage(ImageLoader.spritePlastico,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if (objeto instanceof Camion) gc.drawImage(ImageLoader.spriteCamion, objeto.getX(),objeto.getY(), objeto.getWidth(), objeto.getHeight());
               else if(objeto instanceof BoteAzul) gc.drawImage(ImageLoader.spriteBoteAzul,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
           });

        }
         if(stateGame==StateGame.gameOver)
        {
            texto.setText("Has perdido!!!!!!!!!!");
        }

    }



    public void addComponet()
    {
        grupo.getChildren().clear();
        grupo.getChildren().add(canvas);

        if(stateGame==StateGame.playing)
        {
            grupo.getChildren().addAll(botonA, botonB, dpad, texto);
        }

        //else if? switch tal vez para cuando tengamos mas?
        if(stateGame==StateGame.gameOver)
        {

        }
    }

    private void paintBackground(GraphicsContext gc){
        gc.drawImage(Background.GAME_BG, -(jugador.getX()-100),0, WIDTH, HEIGHT);
        gc.drawImage(Background.GAME_BG, -(jugador.getX()-Background.MAP_WIDTH), 0, WIDTH, HEIGHT);

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
