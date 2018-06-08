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
public class ListaTabu {
    
    int [][] tabuList ;
    public ListaTabu(int numCiudades){
        tabuList = new int[numCiudades][numCiudades]; //La ciudad 0 no se utiliza aquí, pero se deja para la simplicidad
    }
    
    public void MovimientoTabu(int ciudad1, int ciudad2){ //la operación de intercambio Tabu
        tabuList[ciudad1][ciudad2]+= 5;
        tabuList[ciudad2][ciudad1]+= 5;
        
    }
    public void InvMovimientoTabu(int ciudad1, int ciudad2){ //la operación de intercambio Tabu
        tabuList[ciudad1][ciudad2]-= 5;
        tabuList[ciudad2][ciudad1]-= 5;
        
    }
    public void decrementoTabu(){//Aqui rompemos el tabu
        for(int i = 0; i<tabuList.length; i++){
           for(int j = 0; j<tabuList.length; j++){
            int ListaEntradaTabu = tabuList[i][j];
            if(ListaEntradaTabu  > 0) {
                ListaEntradaTabu = ListaEntradaTabu - 1;
                tabuList[i][j]=ListaEntradaTabu;
            }
         } 
           
        }
    }
}
