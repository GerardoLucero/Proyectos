/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda_tabu;

/**
 *
 * @author Emmanuel
 */
public class AmbienteViajero {//Ambiente de Busqueda Tabu
    public int [][] distancias;
    
    public  AmbienteViajero(){}
    
    public int FunctionObjetivo(int solucion[]){ //regresa el costo del camino
        //La posicion de la primera y ultima ciudad no cambian
        // example solution : {0,1,2,4,3,5,6,7,9,8,0 }
      
        int costo = 0;
   
        for(int i = 0 ; i < solucion.length-1; i++){
            costo+= distancias[solucion[i]][solucion[i+1]];
        }
   
        return costo;
        
    }    
}
