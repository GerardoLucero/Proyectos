
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class Main extends JComponent {
    static int ejecuciones;
    static Genetico Algoritmo;
    static Dibujar dibujo = new Dibujar();
    static int[] x;
    static int[] y;
    static DefaultListModel modelo;
    static JList<String> jList1;
    static JScrollPane jScrollPane1;
    static JLabel lAptitud;
    
    
    public static void main(String[] args) {
        JFrame mainframe = new JFrame("48 Ciudades");
        JButton iniciar = new JButton();
        iniciar.setBounds(1200,600,120,25);
        iniciar.setText("Iniciar");
        JButton ejecutar = new JButton();
        ejecutar.setBounds(1200,630,120,25);
        ejecutar.setText("Ejecutar");       
        //Lista
        modelo = new DefaultListModel();
        jList1 = new JList<>();
        jList1.setModel(modelo);     
        jScrollPane1 = new JScrollPane(jList1);             
        jScrollPane1.setBounds(1200, 10, 125, 400);  
        lAptitud = new JLabel("Aptitud: ");
        lAptitud.setBounds(1200, 500, 250, 75);
        mainframe.add(ejecutar);           
        mainframe.add(jScrollPane1);
        mainframe.add(lAptitud);
        mainframe.getContentPane().add(new Main());
        mainframe.pack();
        mainframe.setVisible(true);
        mainframe.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
       
       
       ejecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {      
                dibujo.drawImagen(mainframe.getGraphics(), imagen(), 0, 0, 1100, 838);
                Algoritmo = new Genetico(1000);
                Algoritmo.Inicializar();                
                x = new int[48];
                y = new int[48];
                for(int i = 0;i <48;i++){
                    x[i] = Algoritmo.solucion.getTours().getTour()[i].getLon();
                    y[i] = Algoritmo.solucion.getTours().getTour()[i].getLat();
                    dibujo.drawNode(mainframe.getGraphics(), x[i], y[i]);
                }                                              
                if(Algoritmo != null)
                {                                  
                    Algoritmo.Ejecutar();                      
                    //mainframe.getGraphics().clearRect(0, 0, 800, 630);     
                    mainframe.getGraphics().drawImage(imagen(), 0, 0, 1100,838,null,null);
                    //dibujo.drawImagen(mainframe.getGraphics(), imagen(), 0, 0, 1100, 838);
                    modelo.clear();
                    lAptitud.setText("Aptitud: ");
                    for(int i = 0;i <48;i++)
                    {
                       dibujo.drawNode(mainframe.getGraphics(), x[i], y[i]);
                    } 

                    x = new int[48];
                    y = new int[48];
                    for(int i = 0;i <48;i++)
                    {
                        x[i] = Algoritmo.solucion.getTours().getTour()[i].getLon();
                        y[i] = Algoritmo.solucion.getTours().getTour()[i].getLat();                        
                        modelo.addElement(Algoritmo.solucion.getTours().getTour()[i].getId());
                    } 
                    modelo.addElement(Algoritmo.solucion.getTours().getTour()[0].getId());
                    lAptitud.setText("Aptidud: " + Algoritmo.solucion.getTours().getAptitud());
                    for(int i = 0;i<48;i++)
                    {
                        if(i < 47)                                                      
                            dibujo.drawLine(mainframe.getGraphics(), x[i], y[i], x[i+1], y[i+1]);                                                                              
                        else                       
                            dibujo.drawLine(mainframe.getGraphics(), x[i], y[i], x[0], y[0]);                                                                                   
                    }
                    try
                    {
                        Thread.sleep(100);                                    
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }                                                               
                }
            }
        });
    }    
    
    public static BufferedImage imagen()
    {
        BufferedImage mapa;
        try{
            String path = "usmap.jpg";
            File file = new File(path);
            mapa = ImageIO.read(file);            
            
        }
        catch(IOException e)
        {
            mapa = null;
        }
        return mapa;
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
    }
    @Override
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(1400, 800);
    }
}