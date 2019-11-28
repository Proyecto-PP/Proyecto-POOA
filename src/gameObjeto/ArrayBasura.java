package gameObjeto;

import gameObjeto.basura.Basura;
import gameObjeto.basura.basuraOrganica.BasuraBanana;
import gameObjeto.basura.basuraOrganica.BasuraManzana;
import gameObjeto.basura.basuraOrganica.BasuraSandia;
import gameObjeto.basura.basuraPapel.BasuraBolaPapel;
import gameObjeto.basura.basuraPapel.BasuraPapel;
import gameObjeto.basura.basuraPapel.BasuraPapelAvion;
import gameObjeto.basura.basuraPapel.BasuraPeriodico;
import gameObjeto.basura.basuraPlastico.BasuraBotella;
import gameObjeto.basura.basuraVidrio.BasuraFoco;

import java.util.ArrayList;

public  class ArrayBasura {

    private final int width=50;
    private final int height=50;
    private final double size=0.5;

    private  ArrayList<Basura> arrayBasura=new ArrayList<>();
    private BasuraBotella basura1 = new BasuraBotella(100,300,width,height,size);
    private BasuraBotella basura2 = new BasuraBotella(230,350,width,height,size);
    private BasuraBotella basura3 = new BasuraBotella(430,270,width,height,size);

    private BasuraSandia basura4 = new BasuraSandia(600,300,width,height,size);
    private BasuraManzana basura5 = new BasuraManzana(320,110,width,height,size);
    private BasuraBanana basura6 = new BasuraBanana(900,150,width,height,size);
    private BasuraPeriodico basura7 = new BasuraPeriodico(677,150,width,height,size);
    private BasuraBolaPapel basura8 = new BasuraBolaPapel(700,400,width,height,size);
    private BasuraBotella basura9= new BasuraBotella(890,40,width,height,size);
    private BasuraPapelAvion basura10 = new BasuraPapelAvion(100,300,width,height,size);
    private BasuraFoco basura11 = new BasuraFoco(600,200,width,height,size);

    public ArrayBasura() {
        arrayBasura.add(basura1);
        arrayBasura.add(basura2);
        arrayBasura.add(basura3);
        arrayBasura.add(basura4);
        arrayBasura.add(basura5);
        arrayBasura.add(basura6);
        arrayBasura.add(basura7);
        arrayBasura.add(basura8);
        arrayBasura.add(basura9);
        arrayBasura.add(basura10);
        arrayBasura.add(basura11);



    }

    public  ArrayList<Basura> getArrayBasura() {
        return arrayBasura;
    }

    public  void setArrayBasura(ArrayList<Basura> arrayBasura) {
        this.arrayBasura = arrayBasura;
    }

    public void remove(int numero)
    {
        arrayBasura.remove(numero);
    }

}
