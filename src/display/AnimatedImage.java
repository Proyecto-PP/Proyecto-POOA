/*
 *    Proyecto Pro-Planeta 1.0
 *    Videojuego en Java construido con JavaFX 11
 *    Autores: Castañón Puga Manuel, Vera Arias Victor Manuel, Feng Haosheng, Meléndez Lineros Leonardo
 *    Correo electrónico: {puga, victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonóma de Baja California
 *    http://www.uabc.mx
 */

package display;

import javafx.scene.image.Image;

public class AnimatedImage
{
    public Image[] frames;
    public double duration;


    public Image getFrame(double time)
    {
        int index = (int)((time % (frames.length * duration)) / duration);

        return frames[index];
    }




}
