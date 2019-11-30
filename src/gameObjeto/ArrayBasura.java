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
import gameObjeto.basura.basuraPlastico.BasuraPlastico;
import gameObjeto.basura.basuraVidrio.BasuraBotellaRoto;
import gameObjeto.basura.basuraVidrio.BasuraFoco;
import gameObjeto.basura.basuraVidrio.BasuraVentanaRoto;

import java.util.ArrayList;
import java.util.Random;

public  class ArrayBasura {

    private final int width=50;
    private final int height=50;
    private final double size=0.5;

    private  ArrayList<Basura> arrayBasura=new ArrayList<>();
    private int[][] matrizBasura=new int[50][8];

    // 1 basura organica
    // 2 basura vidrio
    // 3 basura plastico
    // 4 basura Papel
    public ArrayBasura() {
        Random random = new Random();
        for(int i=0;i<50;i++)
            for(int j=0;j<5;j++)
            {
                matrizBasura[i][j]=random.nextInt(5);
            }

        for(int i=0;i<50;i++)
            for(int j=0;j<5;j++)
            {
                System.out.println("valor:"+matrizBasura[i][j]);
            }


        for(int i=0;i<50;i++)
            for(int j=0;j<5;j++){
                if(matrizBasura[i][j]==1)
                {
                    int num=random.nextInt(3);
                    if(num==0){
                        arrayBasura.add(new BasuraSandia(getRandom()+800*i+1,100*j+50,width,height,size));
                    }
                    else if(num==1){
                        arrayBasura.add(new BasuraBanana(getRandom()+800*i+1,100*j+50,width,height,size));
                    }
                    else if(num==2){
                        arrayBasura.add(new BasuraManzana(getRandom()+800*i+1,100*j+50,width,height,size));
                    }
                }
                else if(matrizBasura[i][j]==2)
                {
                    int num=random.nextInt(3);
                    if(num==0){
                        arrayBasura.add(new BasuraFoco(getRandom()+1000*i+1,100*j+50,width,height,size));
                    }
                    else if(num==1){
                        arrayBasura.add(new BasuraVentanaRoto(getRandom()+1000*i+1,100*j+50,width,height,size));
                    }
                    else if(num==2){
                        arrayBasura.add(new BasuraBotellaRoto(getRandom()+1000*i+1,100*j+50,width,height,size));
                    }
                }

                else if(matrizBasura[i][j]==3)
                {
                    arrayBasura.add(new BasuraBotella(getRandom() + 1000 * i+1, 100 * j, width, height, size) {
                    });
                }
                else if(matrizBasura[i][j]==4)
                {
                    int num=(int)random.nextInt(3);
                    if(num==0){
                        arrayBasura.add(new BasuraBolaPapel(getRandom() + 1000 * i+1, 100 * j, width, height, size) {
                        });
                    }
                    else if(num==1){
                        arrayBasura.add(new BasuraPapelAvion(getRandom()+1000*i+1,100*j+50,width,height,size));
                    }
                    else if(num==2){
                        arrayBasura.add(new BasuraPeriodico(getRandom()+1000*i+1,100*j+50,width,height,size));
                    }
                }

            }

    }

    private double getRandom()
    {
        return (double)Math.random()*800;
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
