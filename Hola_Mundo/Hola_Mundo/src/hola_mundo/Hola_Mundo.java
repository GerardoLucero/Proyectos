/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hola_mundo;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 *
 * @author Jorge
 */
public class Hola_Mundo extends JComponent 
{
    private static Algoritmos algoritmo;        
    static DefaultListModel modelo;
    static JList<String> jList1;
    static JScrollPane jScrollPane1;
   
    public static void main(String[] args) 
    {
        
        JFrame mainframe = new JFrame("Hola mundo");
        JButton hillClimbing = new JButton("Hill Climbing");
        hillClimbing.setBounds(50, 520, 125, 25);
        JButton busquedaTabu = new JButton("Búsqueda Tabú");
        busquedaTabu.setBounds(225, 520, 125, 25);
        modelo = new DefaultListModel();
        jList1 = new JList<>();
        jList1.setModel(modelo);
        jScrollPane1 = new JScrollPane(jList1);
        jScrollPane1.setBounds(50, 10, 300, 500);        
        mainframe.add(hillClimbing);  
        mainframe.add(busquedaTabu);
        mainframe.add(jScrollPane1);
        mainframe.getContentPane().add(new Hola_Mundo());
        mainframe.pack();
        mainframe.setVisible(true);
        mainframe.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        hillClimbing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.clear();
                Ciudades[] tour =  new Ciudades[12];
                algoritmo = new Algoritmos();
                
                
                String cad = "";
                int ap = algoritmo.Aptitud(algoritmo.tour);
                for(int i = 0; i < 12; i++)
                    cad += algoritmo.tour[i].getLetra();
                modelo.addElement("Solucion Inicial: " + cad + " ; Aptitud: " + ap);
                
                
                tour = algoritmo.HillClimbing();
                cad = "";
                ap = algoritmo.Aptitud(tour);
                for(int i = 0; i < 12; i++)
                    cad += tour[i].getLetra();
                modelo.addElement("Solucion : " + cad + " ; Aptitud: " + ap);
            }
        });
        
        busquedaTabu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.clear();
                Ciudades[] tour = new Ciudades[12];
                algoritmo = new Algoritmos();
                
                String cad = "";
                int ap = algoritmo.Aptitud(algoritmo.tour);
                for(int i = 0; i < 12; i++)
                    cad += algoritmo.tour[i].getLetra();
                modelo.addElement("Solucion Inicial: " + cad + " ; Aptitud: " + ap);
                
                
                tour = algoritmo.BusquedaTabu();
                cad = "";
                ap = algoritmo.Aptitud(tour);
                for(int i = 0; i < 12; i++)
                    cad += tour[i].getLetra();
                modelo.addElement("Solucion: " + cad + " ; Aptitud: " + ap);
            }
        });
        
    }
    
    @Override
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(400, 600);
    }
    
}
