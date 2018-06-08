

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Solucion 
{
    private int aptitud;
    private Ciudad[] tour;

    public Solucion(Ciudad [] original){
        this.tour = original.clone();
        //tour = original;
        //NuevoTour();
        this.aptitud = 0;
    }
    
    public Solucion(int aptitud,Ciudad[] tour){
        this.tour = tour;
        this.aptitud = aptitud;
    }
    
    public Solucion Copia()
    {
        Solucion nueva = new Solucion(tour);
        //nueva.setTour(this.tour);
        nueva.setAptitud(this.aptitud);
        return nueva;
    }
    
    public void NuevoTour()
    {
        Random r =  ThreadLocalRandom.current();
        Ciudad aux;
        int index1;
        int index2;
        for (int i = 0; i<48; i++){
            index1= r.nextInt(48);
            index2= r.nextInt(48);
            aux = tour[index1];
            tour[index1] = tour[index2];
            tour[index2] = aux;
        }
    }
    
    public int size()
    {
        return tour.length;
    }
    
    public void EvaluarAptitud()
    {
        aptitud = 0;
        for(int i = 0; i < 47; i++)
                aptitud += tour[i].Distancia(tour[i+1].getId()-1);
        aptitud += tour[47].Distancia(tour[0].getId()-1);
        /*for (int i = 0;i <47; i++)
            aptitud += Ciudad.Distancia(tour[i], tour[i+1]);
        aptitud += Ciudad.Distancia(tour[47], tour[0]);*/            
    }        
    
    public void cambiarCiudad(int pos,Ciudad ciudad)
    {
        tour[pos]=ciudad.Copia();
    }
      
    public Ciudad getCiudad(int pos)
    {
        return tour[pos];
    }
    
    public Solucion swap(int a, int b)
    { 
        Solucion n = new Solucion(tour);
//        int total = b-a +1;
//        int mitad = total /2;
//        Ciudad temp;
//        for(int i = 0; i <= mitad;i++){
//            temp = n.getTour()[a+i];
//            n.getTour()[a+i] = n.getTour()[b-i];
//            n.getTour()[b-i] = temp;
//        }
        Ciudad temp = n.getTour()[a];
        n.getTour()[a] = n.getTour()[b];
        n.getTour()[b] = temp;
        return n;     
    }  
    
     public Solucion invertir(int a, int b)
    { 
        Solucion n = new Solucion(tour);
        int total = b-a +1;
        int mitad = total /2;
        Ciudad temp;
        for(int i = 0; i <= mitad;i++){
            temp = n.getTour()[a+i];
            n.getTour()[a+i] = n.getTour()[b-i];
            n.getTour()[b-i] = temp;
        }
      
        return n;     
    }  
    
    public void swap2(int a, int b){ 
        Ciudad[] aux = tour.clone();
        int c =a;
        if(a < b)
        {
            while(b<a)
            {
                aux[c] = tour[b];
                c++;
                b++;
            }
        }
        else
        {
            while(b>a)
            {
                aux[c] = tour[b];
                c--;
                b++;
            }
        }
        tour = aux.clone();
    }  
    
    public Boolean contiene(Ciudad [] ind, Ciudad val)
    {
        for(int i = 0; i < 48; i++)
            if(ind[i] != null && ind[i].getId() == val.getId())
                return true;
        return false;
    }   
    
  
      
    public int getAptitud() 
    {
        return aptitud;
    }

    public void setAptitud(int aptitud) 
    {
        this.aptitud = aptitud;
    }

    public Ciudad[] getTour() 
    {
        return tour;
    }

    public void setTour(Ciudad[] tour) 
    {
        this.tour = tour;
    }    
}
