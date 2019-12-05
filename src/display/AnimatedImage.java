/*
 *    Proyecto Pro-Planeta
 *    Videojuego en Java construido con JavaFX
 *    Autores: Vera Arias Victor Manuel, Feng Haosheng, Melendez Lineros Leonardo
 *    Correo electronico: {victor.vera, feng.haosheng, leonardo.melendez}@uabc.edu.mx
 *    Universidad Autonoma de Baja California
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
