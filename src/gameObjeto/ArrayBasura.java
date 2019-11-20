package gameObjeto;

import java.util.ArrayList;

public  class ArrayBasura {


    private  ArrayList<Basura> arrayBasura=new ArrayList<>();
    private  Basura botella1 = new Basura("basuraPlastico",100,300,40,40,0.5);
    private  Basura botella2 = new Basura("basuraPlastico",230,350,40,40,0.5);
    private  Basura botella3 = new Basura("basuraPlastico",430,270,40,40,0.5);
    private  Basura botella4 = new Basura("basuraPlastico",600,300,40,40,0.5);
    private  Basura botella5 = new Basura("basuraPlastico",320,110,40,40,0.5);
    private  Basura botella6 = new Basura("basuraPlastico",111,150,40,40,0.5);
    private  Basura botella7 = new Basura("basuraPlastico",677,30,40,40,0.5);
    private  Basura botella8 = new Basura("basuraPlastico",700,400,40,40,0.5);
    private  Basura botella9= new Basura("basuraPlastico",890,40,40,40,0.5);

    public ArrayBasura() {
        arrayBasura.add(botella1);
        arrayBasura.add(botella2);
        arrayBasura.add(botella3);
        arrayBasura.add(botella4);
        arrayBasura.add(botella5);
        arrayBasura.add(botella6);
        arrayBasura.add(botella7);
        arrayBasura.add(botella8);
        arrayBasura.add(botella9);



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
