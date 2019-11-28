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
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
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
    public static Button botonJugar=new Button(300,200,50,60, ImageLoader.spriteBotonJugar);
    public static Button botonInstruccion=new Button(300,300,50,60, ImageLoader.spriteBotonInstruccion);
    public static Button botonSalir=new Button(300,400,50,60, ImageLoader.spriteBotonSalir);



    final long startNanoTime = System.nanoTime();

    private static double dx;
    private static double dy;
    public static final double DASH_SPEED_MULT = 4;

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
        stateGame = StateGame.menu;

        initializeGroup();
        initializeControls();
        initializeCanvas();
        initializeReproductor();
        initializeArrayEntidad();
        initializeUtilities();
        inicializaBotonMenu();


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
               // addComponet();
                updateLogic();
                updateGraphic(gc, t);

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
        //primaryStage.setAlwaysOnTop(true);        Esto es incomodo en la tablet, ya que no es facil mover la pantalla para ver otras ventanas por detras
        primaryStage.centerOnScreen();
        primaryStage.requestFocus();
        primaryStage.setTitle("Proyecto - PP");
        primaryStage.setScene(escena);    //Uso una variable Scene para poder utilizar un teclado para pruebas.
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
        escena = new Scene(grupo);
    }

    private void initializeCanvas() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();     //Muevo esto aqui por relacion al canvas.
    }

    private void initializeReproductor() {
        reproductor = new MusicPlayer(AudioLoader.persona5Song);
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

            //Este es el metodo que determina que acciones estas tomando segun lo que presiones en el teclado (movimiento y demas)
            updatePlayerMovement();

            arrayBasura.getArrayBasura().forEach(basura -> {


                //collision de basura y jugador para agarra la basura
                if (basura.nextTo(jugador.getX(), jugador.getY(), jugador.getWidth(), jugador.getHeight()) == 1) {
                    basura.setNextToPlayer(true);
                } else {
                    basura.setNextToPlayer(false);
                }

                // si el camion esta chocando a una basura se agrega esa basura a la lista de elimina
                if (basura.collisionsWith(camion.getX(), camion.getY(), camion.getWidth(), camion.getHeight()) == 1) {
                    //Si la basura colisiono por que la cargaba el jugador:
                    if (basura.isMoving()) {
                        //El jugador deja de estar cargando esa basura.
                        jugador.setOcupado(false);
                    }
                    remove.add(basura);

                    //Por cierto, no deberiamos quitar esto ya que ya tenemos el bote azul?
                }

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
                }

                //Y solo es despues de todas estas comprobaciones que se mueve la basura.
                basura.move();

            });
            //collision de jugador con el
            if(camion.collisionsWith(jugador.getHitboxX()+dx,jugador.getHitboxY()+dy,jugador.getHitboxWidth(),jugador.getHitboxHeight())==1)
            {
                dx=0;
                dy=0;

                jugador.setDashing(false);

                //Hay problemas con esto, por que si el camion te atraviesa mientras avanza por su cuenta, tu dejas de poder
                //moverte completamente.
            }

            //Lista de elimianr (si es size de la lista eliminar se elimina)
            if(remove.size()>0)
            {
                arrayBasura.getArrayBasura().removeAll(remove);
                arrayEntidad.removeAll(remove);
                //Faltaba poner que se limpiara cada vez que eliminaba los objetos que hacia falta eliminar.
                remove.clear();

                //Quite lo de jugador.setOcupado por que habia basura que se encontraba en el camino del camion, chocaba,
                //se mandaba a eliminar y nunca estuvo en las manos del jugador.
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


        //Los movimientos deberian hacerse todos al ultimo para darle tiempo al programa de procesar que es lo que debe
        //hacer tomando en cuenta el estado actual del juego (y no me refiero a los states).
        camion.move();
        jugador.move();
        boteAzul.move();


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

        //Lo uso unicamente para el teclado.
        //Si lo ponia de forma independiente o en otro thread, lo que pasaba es que podia brincarse las verificaciones
        //que haciamos para colisiones y los botones.

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

            //Esto no funciona bien, no se alarmen
            if (ControlInput.isButtonPressed("S")) {
                if (!Main.getJugador().isOcupado()) {
                    for (Basura basura :
                            Main.getArrayBasura().getArrayBasura()) {
                        if (basura.isNextToPlayer()) {
                            basura.setMoving(true);

                            Main.getJugador().setOcupado(true);
                        }
                    }
                } else if (Main.getJugador().isOcupado()) {

                    //Deberia hacerse en release de alguna forma, recuerda que se hacen varios press si lo dejas presionado.
                    //Esto no funciona bien
                    for (Basura basura :
                            Main.getArrayBasura().getArrayBasura()) {
                        if (basura.isMoving()) {
                            basura.setMoving(false);
                            Main.getJugador().setOcupado(false);
                        }
                    }
                }
            }

            if (ControlInput.isButtonPressed("D")) {
                if(dx != 0 || dy != 0)      //Si se está moviendo hacia alguna direccion.
                    jugador.setDashing(true);
            }


        } else {

            //Si el jugador está haciendo un dash

            if(jugador.getDashFrames() != jugador.getDashTime()*60 ) {
                if(jugador.getDashFrames() == 0) {
                    dx = dx * DASH_SPEED_MULT;
                    dy = dy * DASH_SPEED_MULT;
                }
                jugador.setDashFrames(jugador.getDashFrames() + 1);
            } else {
                jugador.setDashing(false);
                jugador.setDashFrames(0);
            }


        }

    }

    public void updateGraphic(GraphicsContext gc,double t)
    {
        gc.clearRect(0,0,1024,600);
        if(stateGame==StateGame.menu)
        {
            gc.drawImage(ImageLoader.caminaderecho.getFrame(t),600,400,60,60);
        }

        else if(stateGame==StateGame.playing)
        {
           paintBackground(gc);
           arrayEntidad.forEach(objeto->
           {
               if(objeto instanceof Player) paintPlayer(gc,t);
               else if(objeto instanceof BasuraBotella) gc.drawImage(ImageLoader.spritePlastico,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraManzana) gc.drawImage(ImageLoader.spriteManzana,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraPeriodico) gc.drawImage(ImageLoader.spritePeriodico,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraBanana) gc.drawImage(ImageLoader.spriteBanana,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraSandia) gc.drawImage(ImageLoader.spriteSandia,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraBolaPapel) gc.drawImage(ImageLoader.spriteBolaPapel,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraPapelAvion) gc.drawImage(ImageLoader.spritePapelAvion,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraFoco) gc.drawImage(ImageLoader.spriteFoco,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraVentanaRoto) gc.drawImage(ImageLoader.spriteVentanaRoto,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
               else if(objeto instanceof BasuraBotellaRoto) gc.drawImage(ImageLoader.spriteBotellaRoto,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());


               else if (objeto instanceof Camion) gc.drawImage(ImageLoader.spriteCamion, objeto.getX(),objeto.getY(), objeto.getWidth(), objeto.getHeight());
               else if(objeto instanceof BoteBasura) gc.drawImage(ImageLoader.spriteBoteAzul,objeto.getX(),objeto.getY(),objeto.getWidth(),objeto.getHeight());
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

        if(stateGame==StateGame.menu)
        {
            grupo.getChildren().addAll(botonInstruccion,botonJugar,botonSalir);
        }

        if(stateGame==StateGame.playing)
        {
            grupo.getChildren().addAll(botonA, botonB, dpad, texto);

            //Temp
            //escena.setOnKeyPressed(keyboardListener);

        }

        //else if? switch tal vez para cuando tengamos mas?
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
    public void inicializaBotonMenu()
    {
        botonJugar.setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override
            public void handle(TouchEvent event) {
                setStateGame(StateGame.playing);
                addComponet();

            }
        });

        botonJugar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStateGame(StateGame.playing);
                addComponet();
            }
        });

        botonInstruccion.setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override
            public void handle(TouchEvent event) {
                //Main.setStateGame(StateGame.playing);
            }
        });

        botonInstruccion.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //   Main.setStateGame(StateGame.playing);
            }
        });

        botonSalir.setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override
            public void handle(TouchEvent event) {
                System.exit(0);
            }
        });

        botonSalir.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
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
