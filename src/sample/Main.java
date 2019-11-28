package sample;

import botones.Button;
import controller.ControlInput;
import controller.ControlsSetup;

import display.Background;
import entidades.Entity;
import gameObjeto.*;
import gameObjeto.basura.Basura;
import gameObjeto.basura.basuraOrganica.BasuraBanana;
import gameObjeto.basura.basuraOrganica.BasuraManzana;
import gameObjeto.basura.basuraOrganica.BasuraSandia;
import gameObjeto.basura.basuraPapel.BasuraBolaPapel;
import gameObjeto.basura.basuraPapel.BasuraPapelAvion;
import gameObjeto.basura.basuraPapel.BasuraPeriodico;
import gameObjeto.basura.basuraPlastico.BasuraBotella;
import gameObjeto.basura.basuraPlastico.BasuraPlastico;
import gameObjeto.basura.basuraVidrio.BasuraBotellaRoto;
import gameObjeto.basura.basuraVidrio.BasuraFoco;
import gameObjeto.basura.basuraVidrio.BasuraVentanaRoto;
import gameObjeto.boteBasura.BoteBasura;
import gameObjeto.boteBasura.BotePlastico;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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

    private boolean intro = false;
    private boolean shift = false; //False = derecha

    private int puntaje;        //puntaje, como es un atributo de clase, se inicializa en 0 por default.
    Text texto = new Text();

    /*
    *  ArrayBasura podria ser una clase estatica en la que se inicializara lo que ocupamos en un static block, llamamos a un metodo
    *  que devuelva un array de entidades que ya tenga todas las basuras. Asi te libras del foreach en initializeArrayEntidad. Ademas
    *  te libras de cosas redundantes como Main.getArrayBasura().getArrayBasura(); [TouchControl : 32]
    */
    private static ArrayBasura arrayBasura = new ArrayBasura();
    private static Player jugador = new Player( 100, 100, 32, 48, 0.5);
    private static Camion camion = new Camion(100, 200, 200, 100, 0.5);
    private static BotePlastico boteAzul = new BotePlastico( 50, 250, 50, 50, 1);
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

                if(intro) {
                    showLevel(t);
                }
                else {
                    updateLogic();
                    updateGraphic(gc, t);
                }
            }
        }.start();

        reproductor.fadeInPlay(1);

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
                return Double.compare(o1.getY() + o1.getHeight(), o2.getY() + o2.getHeight());
            }
        };
    }

    ////////////////////////////////////////////
    /*          Bloque de setup [FIN]         */
    ////////////////////////////////////////////

    private void showLevel(double t) {
        if (!shift) {
            bg.setBackgroundX(bg.getBackgroundX() - 9);
            if(bg.getBackgroundX()-1100 < -bg.getGameBg().getWidth())
                shift = true;
        } else {
            bg.setBackgroundX(bg.getBackgroundX() + 9);
            if (bg.getBackgroundX() >= 0) {
                bg.setBackgroundX(0);
                intro = false;
            }
        }
        System.out.printf("%d\r", bg.getBackgroundX());
        updateGraphic(gc, t);
    }

    public void updateLogic()
    {

        if(stateGame==StateGame.playing)
        {
            if(bg.getBackgroundX() >= -bg.getGameBg().getWidth() + WIDTH){
                bg.setBackgroundX( -camion.getDistance() );
            }
            else{
                setStateGame(StateGame.gameOver);
            }

            Collections.sort(arrayEntidad, cmpArrayEntidad);    //Instancie el comparador en el initializeUtilities para que no se cree uno nuevo cada vez.

            texto.setText("EL puntaje es:"+puntaje+"                                   Gasolina:"+String.format("%.1f",camion.getGasolina()/10));

            //Este es el metodo que determina que acciones estas tomando segun lo que presiones en el teclado (movimiento y demas)
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
                    //si el jugador llevando una basura y conllisiona con el bote plastico
                    if (jugador.collisionsWith(boteAzul.getX(), boteAzul.getY(), boteAzul.getWidth(), boteAzul.getHeight()) == 1) {
                        if(basura instanceof BasuraPlastico)
                        {   // si la basura que lleva es plastico obtenr punto y gasolina
                            accionCollisionBoteObtenerPunto(basura);
                        }
                        else
                            // si l a basura no es plastico se pierde punto y gasolina
                            accionCollisionBotePierdePunto(basura);
                    }
                }

                //Si el jugador va a chocar con la basura...
                if(basura.collisionsWith(jugador.getHitboxX()+dx,jugador.getHitboxY()+dy,jugador.getHitboxWidth(),jugador.getHitboxHeight())==1)
                {
                    //Entonces su cambio en x y y se vuelven 0
                    dx=0;
                    dy=0;

                    jugador.setDashing(false);
                } else {
                    basura.move();
                }

            });

            //Si el jugador colisiona con el camion...
            if(camion.collisionsWith(jugador.getHitboxX()+dx,jugador.getHitboxY()+dy,jugador.getHitboxWidth(),jugador.getHitboxHeight())==1)
            {
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

            //Esto debe ir al ultimo por que se deben realizar todas las validaciones antes de darle permiso de cambiar
            //su posicion.
            jugador.move();
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

    private void accionCollisionBoteObtenerPunto(Basura basura)
    {
        remove.add(basura);
        camion.setGasolina(camion.getGasolina() + 30);
        puntaje++;
        jugador.setOcupado(false);
    }

    private void accionCollisionBotePierdePunto(Basura basura)
    {
        remove.add(basura);
        camion.setGasolina(camion.getGasolina() - 30);
        puntaje--;
        jugador.setOcupado(false);
    }

    private void updatePlayerMovement() {

        //Tanto el touch como el teclado comparten funcionamiento, y se realizan cambios segun lo que se tenga presionado
        //en este metodo.

        if(!jugador.isDashing()) {

            System.out.println("NYEEH");

            if (ControlInput.isButtonPressed("UP") && !ControlInput.isButtonPressed("DOWN")) {
                Main.setDx(0);
                Main.setDy(-Player.SPEED);

                Main.getJugador().setState(StatePlayer.arriba);
            }

            if (ControlInput.isButtonPressed("DOWN") && !ControlInput.isButtonPressed("UP")) {
                Main.setDx(0);
                Main.setDy(Player.SPEED);

                Main.getJugador().setState(StatePlayer.abajo);
            }

            if (ControlInput.isButtonPressed("RIGHT") && !ControlInput.isButtonPressed("LEFT")) {
                Main.setDx(Player.SPEED);
                Main.setDy(0);


                if (ControlInput.isButtonPressed("UP")) {
                    Main.setDx(Player.SPEED / 2);
                    Main.setDy(-Player.SPEED / 2);
                } else if (ControlInput.isButtonPressed("DOWN")) {
                    Main.setDx(Player.SPEED / 2);
                    Main.setDy(Player.SPEED / 2);
                }

                Main.getJugador().setState(StatePlayer.derecha);
            }

            if (ControlInput.isButtonPressed("LEFT") && !ControlInput.isButtonPressed("RIGHT")) {
                Main.setDx(-Player.SPEED);
                Main.setDy(0);

                if (ControlInput.isButtonPressed("UP")) {
                    Main.setDx(-Player.SPEED / 2);
                    Main.setDy(-Player.SPEED / 2);
                } else if (ControlInput.isButtonPressed("DOWN")) {
                    Main.setDx(-Player.SPEED / 2);
                    Main.setDy(Player.SPEED / 2);
                }

                Main.getJugador().setState(StatePlayer.izquierda);
            }

            if (ControlInput.isButtonPressed("RIGHT") && ControlInput.isButtonPressed("LEFT")) {
                Main.setDx(0);

                if (ControlInput.isButtonPressed("UP")) {
                    Main.setDy(-Player.SPEED);

                    Main.getJugador().setState(StatePlayer.arriba);
                } else if (ControlInput.isButtonPressed("DOWN")) {
                    Main.setDy(Player.SPEED);

                    Main.getJugador().setState(StatePlayer.abajo);
                } else {
                    Main.setDy(0);
                }
            }

            if (ControlInput.isButtonPressed("UP") && ControlInput.isButtonPressed("DOWN")) {
                Main.setDy(0);

                if (ControlInput.isButtonPressed("RIGHT")) {
                    Main.setDx(Player.SPEED);

                    Main.getJugador().setState(StatePlayer.derecha);
                } else if (ControlInput.isButtonPressed("LEFT")) {
                    Main.setDx(-Player.SPEED);

                    Main.getJugador().setState(StatePlayer.izquierda);
                } else {
                    Main.setDx(0);
                }
            }

            if (!ControlInput.isButtonPressed("LEFT") && !ControlInput.isButtonPressed("RIGHT") &&
                    !ControlInput.isButtonPressed("UP") && !ControlInput.isButtonPressed("DOWN")) {
                Main.setDx(0);
                Main.setDy(0);
                if (jugador.getX() + bg.getBackgroundX() < 0) {
                    jugador.setX(jugador.getX() - Camion.SPEED);
                    jugador.setHitboxX(jugador.getHitboxX() - Camion.SPEED);
                }

            }


            //Esto es para agarrar y soltar basura
            if (ControlInput.isButtonPressed("S")) {

                if (!ControlInput.isAltButtonA()) {
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
                if ((dx != 0 || dy != 0) && !ControlInput.isAltButtonB() && dashCooldown == 0) {     //Si se está moviendo hacia alguna direccion.
                    jugador.setDashing(true);
                    ControlInput.setAltButtonB(true);
                }
            } else {        //Necesitamos que pueda realizar su funcionalidad UNA vez hasta que lo vuelva a presionar.
                //Con esto lo que hago es obligar al usuario a levantar el dedo de la tecla, y solo despues
                //de que lo haga es que puede volver a utilizar el boton. Lo mismo con el de arriba.
                ControlInput.setAltButtonB(false);
            }

            if (dashCooldown > 0) {      //Contador para evitar que hagan dash seguidos.
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
        if(stateGame == StateGame.playing)
        {
           bg.paintBackground(gc);
           arrayEntidad.forEach(objeto->
           {
               double objetoX = (objeto.getX()+bg.getBackgroundX() < 0 && !intro) ? 0:objeto.getX()+bg.getBackgroundX();

               if(objeto instanceof Player) paintPlayer(gc,t);
               else if(objeto instanceof BasuraBotella) gc.drawImage(ImageLoader.spritePlastico,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraManzana) gc.drawImage(ImageLoader.spriteManzana,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraPeriodico) gc.drawImage(ImageLoader.spritePeriodico,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraBanana) gc.drawImage(ImageLoader.spriteBanana,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraSandia) gc.drawImage(ImageLoader.spriteSandia,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraBolaPapel) gc.drawImage(ImageLoader.spriteBolaPapel,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraPapelAvion) gc.drawImage(ImageLoader.spritePapelAvion,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraFoco) gc.drawImage(ImageLoader.spriteFoco,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraVentanaRoto) gc.drawImage(ImageLoader.spriteVentanaRoto,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraBotellaRoto) gc.drawImage(ImageLoader.spriteBotellaRoto,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());


               else if (objeto instanceof Camion) gc.drawImage(ImageLoader.spriteCamion, (intro)? objetoX:objeto.getX(),objeto.getY(), objeto.getWidth(), objeto.getHeight());
               else if(objeto instanceof BoteBasura) gc.drawImage(ImageLoader.spriteBoteAzul,(intro)? objetoX:objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
           });
        }

        showProgressBar(gc);

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

    private void showProgressBar(GraphicsContext gc){
        double progress = -bg.getBackgroundX()/64;

        gc.setStroke(Color.BLACK);
        gc.strokeRect(437,575,150,10);
        gc.setFill(Color.WHITE);

        double indicator = progress*2+437;
        gc.fillRect( (indicator> 587? 587:indicator), 565,10,30);
    }

    public void paintPlayer(GraphicsContext gc,double t)
    {
        double playerX = (jugador.getX()+bg.getBackgroundX() < 0 && !intro) ? 0:jugador.getX()+bg.getBackgroundX();

        if (StatePlayer.abajo==jugador.getState()) {
            if(dy!=0)gc.drawImage(ImageLoader. caminaAbajo.getFrame(t),(intro)? playerX:jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
            else gc.drawImage(ImageLoader.paradoAbajo,(intro)? playerX:jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
        }
        else if(jugador.getState()==StatePlayer.arriba)
        {
            if (dy!=0) gc.drawImage(ImageLoader.caminaArriba.getFrame(t),(intro)? playerX:jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
            else gc.drawImage(ImageLoader.paradoArriba,(intro)? playerX:jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
        }
        else if(jugador.getState()==StatePlayer.derecha)
        {
            if (dx!=0) {
                gc.drawImage(ImageLoader.caminaderecho.getFrame(t),(intro)? playerX:jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());

            }

            else {
                gc.drawImage(ImageLoader.paradoDerecho,(intro)? playerX:jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
            }

        }
        else if(jugador.getState()==StatePlayer.izquierda)
        {
            if(dx!=0) {
                gc.drawImage(ImageLoader.caminaIzquierda.getFrame(t), (intro)? playerX:jugador.getX(), jugador.getY(), jugador.getWidth(), jugador.getHeight());
            }
            else {
                gc.drawImage(ImageLoader.paradoIzquierda,(intro)? playerX:jugador.getX(),jugador.getY(),jugador.getWidth(),jugador.getHeight());
            }
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
