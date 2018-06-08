

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Poblacion 
{
    private Solucion Tours;
    private Ciudad[] original;
    private int TAM;
    
    public  Poblacion(int TAM,Ciudad[] original)
    {
        this.TAM = TAM;
        Tours = new Solucion(original);
        this.original = original;
    }
    public Poblacion()
    {

    }
    public void InicializarPob()
    {
        for (int i = 0; i < TAM; i++)
        {
            Tours= new Solucion(original);
        }
    }
    public  void Eaptitud()
    {
        for (int i = 0; i < TAM; i++)
        {
            Tours.EvaluarAptitud();
        }
    }
  
    public Solucion getTours() {
        return Tours;
    }

    public void setTours(Solucion tours) {
        Tours = tours;
    }
    public int getTAM() {
        return TAM;
    }

    public void setTAM(int TAM) {
        this.TAM = TAM;
    }
    
    public void RS()
    {
         // temperatura inicial
        double Temperatura = 1000;

        // parametro enfriamiento que multiplica a la temperatura disminuyendola un 0.000001 %
        double Velocidadenfriamiento = 0.99;

        // inicializar primer solucion
        Solucion SolucionActual = new Solucion(original); 
        SolucionActual.NuevoTour();
        SolucionActual = optimoLocal(SolucionActual);
        SolucionActual.EvaluarAptitud();
        Solucion SolucionOptima = SolucionActual.Copia();
        
        System.out.println("Costo de la solucion inicial: " + SolucionActual.getAptitud());
        System.out.println("Ruta: " + SolucionActual);

        // entra al ciclo que viene siendo el sistema de enfriamiento
        while (Temperatura > 1) 
        {           
            for (int i = 0; i < 100; i++) {
                int tourPos1 = (int) (48 * Math.random());
                int tourPos2 = (int) (48 * Math.random());
                SolucionActual.EvaluarAptitud();
                SolucionOptima.EvaluarAptitud();
                if(SolucionActual.getAptitud() < SolucionOptima.getAptitud())
                {
                    SolucionOptima=SolucionActual.Copia();
                }
                Solucion solucionVecina=SolucionActual.Copia();
                solucionVecina = solucionVecina.invertir(tourPos1, tourPos2);
                solucionVecina = optimoLocal(solucionVecina);    
                
                solucionVecina.EvaluarAptitud();
                SolucionActual.EvaluarAptitud();

                if (solucionVecina.getAptitud() < SolucionActual.getAptitud()) 
                {
                    //SolucionOptima = new Solucion(SolucionActual.getTour());
                    SolucionActual = solucionVecina.Copia();
                    System.out.println("Mejora en la aptitud: " + SolucionActual.getAptitud());
                    System.out.println("Ruta: " + SolucionActual);
                }
                else
                {

                    // Decidir si debemos aceptar al vecino
                    double comparador = Math.random();
                    int CostoVecino = solucionVecina.getAptitud();
                    int CostoActual = SolucionActual.getAptitud();
                    if (acceptanceProbability(CostoActual, CostoVecino, Temperatura) > comparador) 
                    {
                        //SolucionActual = new Solucion(NuevaSolucion.getTour());
                        SolucionActual = solucionVecina.Copia();
                    }
                }
               
            }
            // ir enfriando el sistema con el parametro de enfriamiento
            Temperatura *= Velocidadenfriamiento;
        }
        System.out.println("Costo de Solucion Final: " + SolucionOptima.getAptitud());
        System.out.println("Ruta: " + SolucionOptima);  
        Tours = SolucionOptima;   
    }
    
    public Solucion optimoLocal(Solucion Vecina)
    {
        for (int i = 0; i < 46; i++) {
            for (int j = i+1; j < 47; j++) {
                Vecina.EvaluarAptitud();
                int best = Vecina.getAptitud();
                Vecina = Vecina.swap(j, i+1);
                Vecina.EvaluarAptitud();
                int current = Vecina.getAptitud();
                if(best < current)
                {
                    Vecina.swap(j, i+1);
                    Vecina.setAptitud(current);
                }
            }
        }
        return Vecina;
   
    }
            
    public static double acceptanceProbability(int energy, int newEnergy, double temperature) 
    {       
        // Si la nueva solución es mejor, acepta
        if (newEnergy < energy)
        {
            return 1.0;
        }     
        // Si la nueva solución es peor, calcule una probabilidad de aceptación
        return Math.exp((energy - newEnergy) / temperature);
    }
    
    public Solucion HillClimbing(Ciudad [] tour)
    {               
        boolean local;        
        Solucion Elite = new Solucion(tour);
        Elite.setTour(tour);
        Solucion Mejor = Elite.Copia();
        Solucion Actual = Mejor.Copia();
        //int que almacenará la mejor aptitud
        int best = 0;
        //numero de iteraciones
        int t = 0;
        //int auxiliar para comparar la mejor aptitud
        int aux = 0;               
        while(t < 1000)
        {
            //ponemos la bandera en falso
            local = false;
            //Seleccionamos un punto aleatorio
            int uactual = (int)Math.floor(Math.random()*48);
            //evaluamos la aptitud de la solucion inicial
            Actual.EvaluarAptitud();
            best = Actual.getAptitud();
            do{
                //ciclo que selecciona todas las vecindades de uactual
                for(byte unuevo = 0; unuevo < 47; unuevo++)
                {
                    //intercambiamos las ciudades
                    swap(uactual,unuevo,Actual);
                    //evaluamos si la aptitud mejoró
                    Actual.EvaluarAptitud();
                    if(Actual.getAptitud() < aux){
                         aux = Actual.getAptitud();
                    }
                    else{
                        //en caso de no mejorar deshacemos el cambio
                        swap(unuevo, uactual, Actual);     
                    }
                }
                //si la aptitud de la solucion actual es mejor que la inicial continuamos ejecutando
                //de lo contrario colocamos la bandera en verdadero para romper el ciclo
                if(aux < best) 
                {                    
                    best = aux;                  
                    local = false;
                }  
                else
                    local = true;
            }while(!local);       
            Actual.EvaluarAptitud();
            Mejor.EvaluarAptitud();
            if(Actual.getAptitud() < Mejor.getAptitud())
            {
                Mejor = Actual.Copia();
            }
            Mejor.EvaluarAptitud();
            Elite.EvaluarAptitud();
            if(Mejor.getAptitud() < Elite.getAptitud())
            {   
                Elite = Mejor.Copia(); 
                t = 0;
            }
            t++;
        }
        //Para imprimir en la lista
        //hillClimbing = Elite;
        return Elite;
    }    
    
    public void swap(int i, int j, Solucion arr)
    {
        Ciudad aux = arr.getTour()[i];        
        arr.getTour()[i] = arr.getTour()[j];
        arr.getTour()[j] = aux;
    } 
    
    private int[][] Estructura_tabu = new int[48][48]; 
    public Solucion BusquedaTabu()
    {
        //Inicializamos las ciudades mediante el algoritmo hill climbing
        //Inicializar estructura Tabu
        for(int i=0;i<48;i++)
            for(int j=0;j<48;j++)
                Estructura_tabu[i][j]=0;
        //Copiamos el tour generado por Hill Climbing  
        Solucion Mejor = new Solucion(original);  
        Mejor.NuevoTour();
        Solucion actual = Mejor.Copia();
         //Entero que guarda el numero de iteraciones que no ha mejorado la solucion
        int nomejora = 0;
        while(nomejora < 1000)
        {       
            actual = EvaluarVecinos(actual);
            actual = HillClimbing(actual.getTour());
            actual.EvaluarAptitud();
            Mejor.EvaluarAptitud();
            if(actual.getAptitud() < Mejor.getAptitud())
            {
                Mejor = actual.Copia();
                nomejora = 0;
            }
            //Decrementa el numero de iteraciones en las que no podra participar
            for(int i=0;i<48;i++)
                for(int j=0;j<48;j++)
                    if(Estructura_tabu[i][j]!=0)
                        Estructura_tabu[i][j]-=1;
            
            nomejora++;
        }    
        return Mejor;
    }
    
    public Solucion EvaluarVecinos(Solucion tour)
    {
        //inicializa 2 arreglos de ciudades que alamacenaran a el tour candidato
        Solucion candidato = null,casoespecial = null;
        //se inicializa en un numero negativo ya que el mejor candidato no siempre mejorara la aptitud
        int bestscore = 999999;
        int iTabu = 0, jTabu = 0, iEspecial = 0, jEspecial = 0;
        
        for(int i=1;i<48;i++)
            for(int j=2;j<48;j++)
            {
                if (i == j) {
                    continue;
                }
                Solucion aux = tour.Copia();
                //intercambia dos ciudades
                swap(i,j,aux);
                //Calcula cuanto mejoro o empeoro 
                aux.EvaluarAptitud();
                tour.EvaluarAptitud();
                int currentscore = aux.getAptitud() - tour.getAptitud();
                // si ha realizado el mismo movimiento mas de 5 veces se le penaliza
                if(Estructura_tabu[j][i] >= 3)
                {
                    currentscore +=(300 *Estructura_tabu[j][i]) ;
                }
                //Guarda el la mejor permutacion permitida
                if(Estructura_tabu[i][j] == 0 && bestscore > currentscore)               
                {
                    bestscore = currentscore;
                    candidato = aux.Copia();
                    iTabu = i;
                    jTabu = j;
                }
                // Guarda permutaciones que mejoren la aptitud pero no son permitidas
                else if(bestscore > currentscore && currentscore > 0)
                {
                    casoespecial = aux.Copia();
                    iEspecial = i;
                    jEspecial = j;
                }
            }
        //Evalua si una permutacion no permitida es mejor que la mejor de todas las permitidas en caso de que si ignora la regla tabu.
        //Asignar valor tabu de 3 iteraciones a la memoria corto plazo y aumentamos la frecuencia en la memoria a largo plazo
        if(casoespecial != null)
        {
            candidato.EvaluarAptitud();
            casoespecial.EvaluarAptitud();
            if(candidato.getAptitud() > casoespecial.getAptitud()){
                candidato = casoespecial.Copia();
                Estructura_tabu[iEspecial][jEspecial] = 6;
                Estructura_tabu[jEspecial][iEspecial] +=1;
            }
        }
        else
        {
            Estructura_tabu[iTabu][jTabu] =6;
            Estructura_tabu[jTabu][iTabu] +=1;
        }                                        
        return candidato;
    }         
}
