/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package archivos;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Nico
 */
public class GirarFoto
{
    public static void main(String args[])
    {
        // The required drawing location
//        int drawLocationX = 300;
//        int drawLocationY = 300;

        // Rotation information
        
        try
        {
            String rutaImagenEntrada = "C:\\jtemp\\foto.jpg";
            String rutaImagenSalida = "C:\\jtemp\\foto-rotada.jpg";
            BufferedImage image = ImageIO.read(new File(rutaImagenEntrada));

            double angle = 90;
            
            int wAux= image.getWidth();    
            int hAux = image.getHeight();
            
            int w = hAux;    
            int h = wAux;
            
            
            BufferedImage rotated = new BufferedImage(w, h, image.getType());  
            Graphics2D graphic = rotated.createGraphics();
//            graphic.rotate(Math.toRadians(angle), w/2, h/2);
            graphic.translate((hAux - wAux) / 2, (hAux - wAux) / 2);
            graphic.rotate(Math.PI / 2, hAux / 2, wAux / 2);
            graphic.drawImage(image, null, 0, 0);
            graphic.dispose();
            

//            FileOutputStream fos = new FileOutputStream(rutaImagenSalida);
            File outputfile = new File(rutaImagenSalida);
            ImageIO.write(rotated, "jpg", outputfile);
//            sun.awt.image.codec.JPEGImageEncoderImpl j = new sun.awt.image.codec.JPEGImageEncoderImpl(fos);
//            j.encode(rotated);
//            fos.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
