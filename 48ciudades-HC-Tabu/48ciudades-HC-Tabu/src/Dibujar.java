

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Dibujar {
    
    public Dibujar(){
        
    }
    public void drawLine(Graphics g, int lonN1, int latN1, int lonN2, int latN2)
    {
        g.setColor(Color.blue);
        g.drawLine((lonN1/10)+95, (-latN1/10)+605, (lonN2/10)+95, (-latN2/10)+605);
    }
    
    public void drawNode(Graphics g, int lon, int lat)
    {
        g.setColor(Color.red);
        g.fillOval((lon/10)+90, (-lat/10)+600, 10, 10);       
    }    
    
    public void drawImagen(Graphics g, BufferedImage imagen, int x, int y, int w, int h)
    {
        g.drawImage(imagen, x, y, w, h, null);        
    }
}
