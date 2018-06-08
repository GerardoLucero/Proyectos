
package hola_mundo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Algoritmos 
{    
    private char[] letras = {'¡','H','o','l','a','_','M','u','n','d','o','!'};
    public String optimo = new String("¡Hola_Mundo!");
    private int[][] costos = {
        {0,1,0,0,0,0,0,0,0,0,0,0}, //¡
        {0,0,1,0,0,0,0,0,0,0,0,0}, //H
        {0,0,0,1,0,0,0,0,0,0,0,0}, //o
        {0,0,0,0,1,0,0,0,0,0,0,0}, //l
        {0,0,0,0,0,1,0,0,0,0,0,0}, //a
        {0,0,0,0,0,0,1,0,0,0,0,0}, //_
        {0,0,0,0,0,0,0,1,0,0,0,0}, //M
        {0,0,0,0,0,0,0,0,1,0,0,0}, //u 
        {0,0,0,0,0,0,0,0,0,1,0,0}, //n
        {0,0,0,0,0,0,0,0,0,0,1,0}, //d
        {0,0,0,0,0,0,0,0,0,0,0,1}, //o
        {1,0,0,0,0,0,0,0,0,0,0,0}  //!
    };   
    
    public Ciudades[] tour;
    
    public Algoritmos(){
    
        Inicializar();
        
        tour = Nuevo(tour); 

    }
    
    
    public Ciudades [] HillClimbing()
    {
        //Inicializamos las ciudades
        //Bandera que sirve para salir de una meseta (optimo local).
        boolean local;
        //Generamos un tour aleatorio para comparar con las mejores soluciones 
        Ciudades[] Elite = tour.clone();
        Elite = Nuevo(Elite);
        //Entero que guarda el numero de iteraciones que no ha mejorado la solucion
        int nomejora = 0;
        while(nomejora < 1000)
        {
            //Creamos una nueva permutacion y la guardamos para mejorarla.
            Ciudades[] Mejor = tour.clone();
            Mejor = Nuevo(Mejor);
            //For para recorrer todas las ciudades dentro del tour 
//            for(byte uactual = 0; uactual <12; uactual++)             
//            {
                //Tomamos una ciudad aleatoriamente
                int uactual = (int)Math.floor(Math.random()*12);
                //Ponemos nuestra bandera en falso al empezar un nuevo recorrido
                local = false;
                //Creamos un nuevo arreglo de Ciudades y le copiamos lo que tiene nuestro arreglo Mejor (permutacion de tour)
                Ciudades[] Actual = Mejor.clone();
                //Loop que evlua el recorrido actual
                do{
                    //Entero que almacena la aptitud entre la ciudad actual y el resto de ciudades del arreglo Actual 
                    int best = Evaluar(uactual, Actual);
                    //Seleccionamos todos los puntos nuevos en la vecindad uactual
                    for(byte unuevo = 0; unuevo <12; unuevo++)
                    {               
                        //Intercambia la ciudad actual (for exterior) con la ciudad nueva (del for interior) del Arreglo Actual
                        swap(uactual,unuevo,Actual);
                        //Evaluar si el cambio en las ciudades mejora la aptitud
                        if(Evaluar(unuevo,Actual) > best) 
                        {
                            //Actualizamos el valor de la mejor aptitud
                            best = Evaluar(uactual,Actual);
                            //Reiniciamos el recorrido
                            unuevo = 11;
                            //La bandera sigue siendo falsa ya que no nos encontramos en una meseta
                            local = false;
                        }
                        else
                        {
                            //Sino mejora entonces regresamos al estado anterior
                            swap(unuevo,uactual,Actual);
                            //La bandera se activa para romper el loop
                            local = true;
                        }
                    }
                }while(!local);
                //Si la aptitud del arreglo original (Mejor) es menor que la permutación (Actual) entonces 
                //igualamos Mejor a Actual
                if(Aptitud(Mejor) < Aptitud(Actual))
                {
                    Mejor = Actual;
                    uactual = 0;
                }
//            }
            nomejora++;
            //Si la aptitud de la permutación Mejor es mayor que la permutacion Elite entonces 
            //igualamos Elite a Mejor y reiniciamos las iteraciones
            if(Aptitud(Mejor) > Aptitud(Elite))
            {   
                Elite = Mejor;
                nomejora = 0;
            }
        }
        return Elite;
    }
    
    //Inicializa el tour
    public void Inicializar()
    {
        tour = new Ciudades[12];
        for(byte i = 0; i<12;i++)
            tour[i] = new Ciudades(i, costos[i], letras[i]);        
    }
    
    //Genera una permutación del tour
    public Ciudades[] Nuevo(Ciudades[] nuevo)
    {
        Collections.shuffle(Arrays.asList(nuevo)); 
        return nuevo;
    }
    
    
    //Calcula la aptitud del tour (max: 12)

    
    public  int Aptitud(Ciudades[] tour){//YAAA
        int aptitud = 0;
        char [] opt = optimo.toCharArray();
            for (int i = 0; i < opt.length; i++) {
                if(tour[i].getLetra() == opt[i])
                    aptitud++;  
            }
        return aptitud;
            
        }
    
    //Calcula las distancias entre la ciudad actual y las ciudades en el arreglo Ciudades
    public int Evaluar(int actual, Ciudades[] arr)
    {
        int e = 0;
        if(actual == 11)
            e = arr[11].getDistancia()[arr[0].getId()];        
        else
            e = arr[actual].getDistancia()[arr[actual+1].getId()];      
        return e;
    }
    
    //Intercambia dos ciudades dentro del arreglo de Ciudades
    public void swap(int i, int j, Ciudades[] arr)
    {
        Ciudades aux = arr[i];
        arr[i] = arr[j];
        arr[j] = aux;
    }
    
    
    //estructura Tabu
    private int[][] Estructura_tabu = new int[12][12]; 
    
    public Ciudades[] BusquedaTabu()
    {
        //Inicializamos las ciudades
        //Inicializar();
        //Inicializar estructura Tabu
        for(int i=0;i<12;i++)
            for(int j=0;j<12;j++)
                Estructura_tabu[i][j]=0;
        //Generamos un tour aleatorio para comparar con las mejores soluciones 
        Ciudades[] Mejor = tour.clone();
        Mejor = Nuevo(Mejor);  
        //Se inicializa una solucion que sera la modificada para comprar con la mejor
        Ciudades[] actual = Mejor.clone();
        //Se crea un arreglo elite para mejorar nunca quedar atrapados en un optimo local
        Ciudades[] Elite = Mejor.clone();
         //Entero que guarda el numero de iteraciones que no ha mejorado la solucion
        int nomejora = 0;
        while(nomejora < 10000)
        {
            actual = EvaluarVecinos(actual);
            if(Aptitud(actual) > Aptitud(Mejor)){
                Mejor = actual;
                nomejora = 0;
            }
            //Decrementa el numero de iteraciones en las que no podra participar
            DecrementarTabla();
            
            nomejora++;
        }    
        return Mejor;
    }
    
    public void DecrementarTabla(){
        for(int i=0;i<12;i++)
                for(int j=0;j<12;j++)
                    if(Estructura_tabu[i][j]!=0)
                        Estructura_tabu[i][j]-=1;
    }
    public Ciudades[] EvaluarVecinos(Ciudades[] tour)
    {
        //inicializa 2 arreglos de ciudades que alamacenaran a el tour candidato
        Ciudades[] candidato = null,casoespecial = null;
        //se inicializa en un numero negativo ya que el mejor candidato no siempre mejorara la aptitud
        int bestscore = -999999;
        int iTabu = 0, jTabu = 0, iEspecial=0,jEspecial=0;
        
        for(int i=0;i<12;i++)
            for(int j=i;j<12;j++)
            {
                if (i == j) {
                    continue;
                }
                Ciudades[] aux = tour.clone();
                //intercambia dos ciudades
                swap(i,j,aux);
                //Calcula cuanto mejoro o empeoro 
                int currentscore = Aptitud(aux) - Aptitud(tour);
                // si ha realizado el mismo movimiento mas de 5 veces se le penaliza
                if(Estructura_tabu[j][i] > 5){
                    currentscore -=1;
                }
                //Guarda el la mejor permitacion permitida
                if(Estructura_tabu[i][j]== 0 && bestscore < currentscore)               
                {
                    bestscore = currentscore;
                    candidato = aux;
                    iTabu = i;
                    jTabu = j;
                } 
                // Guarda permutaciones que mejoren la aptitud pero no son permitidas
                else if(bestscore < currentscore && currentscore > 0)
                {
                    casoespecial = aux;
                    iEspecial = i;
                    jEspecial = j;
                }

            }
        //Evalua si una permutacion no permitida es mejor que la mejor de todas las permitidas en caso de que si ignora la regla tabu.
        //Asignar valor tabu de 3 iteraciones a la memoria corto plazo y aumentamos la frecuencia en la memoria a largo plazo
        if(casoespecial != null && Aptitud(candidato) < Aptitud(casoespecial)){
            candidato = casoespecial;
            Estructura_tabu[iEspecial][jEspecial] = 3;
            Estructura_tabu[jEspecial][iEspecial] +=1;
        }
        else
        {
            Estructura_tabu[iTabu][jTabu] = 3;
            Estructura_tabu[jTabu][iTabu] +=1;
        }        
            
            
        
        return candidato;
    }
    
    
    
}
