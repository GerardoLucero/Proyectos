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
public class Busqueda_Tabu {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {

        AmbienteViajero viajero = new AmbienteViajero();

        viajero.distancias = //Matriz de distancia de 10x10, usada para representar distancias

        
                           //H   O   L  ""   A   M   U   N   O   D
                new int[][]{{0,  1,  3,  3,  3,  3,  3,  5,  1, 10},//0H
                            {1,  0,  1,  4,  4,  4,  4,  4,  4,  4},//1O
                            {3,  1,  0, 10,  1,  5,  5,  5,  5,  5},//2L
                            {3,  4, 10,  0,  1,  1,  6,  6,  6,  6},//3""
                            {3,  4,  1,  1,  0, 10,  7,  7,  7,  7},//4A
                            {3,  4,  5,  1, 10,  0,  1,  9,  9, 10},//5M
                            {3,  4,  5,  6,  7,  1,  0,  1,  8,  8},//6U
                            {5,  4,  5,  6,  7,  9,  1,  0,  8,  1},//7N
                            {1,  4,  5,  6,  7,  9,  8,  8,  0,  1},//8O
                           {10,  4,  5,  6,  7, 10,  8,  1,  1,  0}};//9D**

        int[] actSolucion = new int[]{0,  1,  2,  3,  4,  5,  6,  7,  8,  9,  0};   //initial solution
        char[] solucion=new char[]  {'H','O','L',' ','A','M','U','N','O','D', 'H'};
          
        //El numero de ciudades empieza en 0
        //la posicion de la primera y ultima ciudad no cambian

        int Iteraciones = 100;
        int tabuLength = 10;
        ListaTabu tabuList = new ListaTabu(tabuLength);

        int[] bestSol = new int[actSolucion.length]; //Esta es la mejor solución hasta ahora
        char[] MejSol=new char[solucion.length];
        
        System.arraycopy(actSolucion, 0, bestSol, 0, bestSol.length);
        System.arraycopy(solucion, 0, MejSol, 0, MejSol.length);
    
        int bestCosto = viajero.FunctionObjetivo(bestSol);

        for (int i = 0; i < Iteraciones; i++) { // desarrollo de iteraciones

    
         
           if(i==95){//pruebas
             int a=0;
           }
            //Vecindad dinámica: no explora todo el universo de población vecina, solo las que no tengan atributos tabú.
            Busqueda_Tabu.MejorVecino(tabuList, viajero, actSolucion,solucion);//toma el valor de bestsolution
             
         
            int costoAct = viajero.FunctionObjetivo(actSolucion);//evaluar funcion objetivo

           

            if (costoAct < bestCosto) {//Criterio de aspiración: admitir soluciones que son mejores que 
                //la mejor solución conocida al momento.
                
                System.arraycopy(actSolucion, 0, bestSol, 0, bestSol.length);
                System.arraycopy(solucion, 0, MejSol, 0, MejSol.length);
                bestCosto = costoAct;
            }
        }

        System.out.println("Busqueda Finalizada! \nMejor solución encontrada = " + bestCosto + "\nMejor solución :");

        imprimirSolucionInt(bestSol);
        imprimirSolucionChar(MejSol);
        System.out.println("o");
        imprimirSolucionInt(invArrayInt(bestSol));
        imprimirSolucionChar(invArrayChar(MejSol));
    }
   
   public static void MejorVecino(ListaTabu tabuList,AmbienteViajero viajero, int[] actSolucion,char[]iniSolucion){
           //Path relinking (reencadenamientos de trayectoria): 
            //construye nuevas soluciones explotando las trayectorias que conectan a las soluciones buenas, 
            //empezando con alguna de las soluciones, llamada la solución de iniciación (initiating solution), 
            //y generando un camino en la vecindad del espacio que lleva a otras soluciones,
            //llamadas las soluciones guías (guiding solutions).

       
       
        int[] bestSol = new int[actSolucion.length]; //Esta es la mejor solución hasta ahora
        char[]MejSol=new char[iniSolucion.length];
        
        
        System.arraycopy(actSolucion, 0, bestSol, 0, bestSol.length);
        System.arraycopy(iniSolucion, 0, MejSol, 0, MejSol.length);
        
        
          int bestCost = viajero.FunctionObjetivo(actSolucion);
        int ciudad1 = 0;
        int ciudad2 = 0;
        boolean primerVecino = true;

        for (int i = 1; i < bestSol.length - 1; i++) {//vecindad adaptiva: posee memoria a corto plazo (prohibición temporal)
            for (int j = 2; j < bestSol.length - 1; j++) {
                if (i == j) {
                    continue;
                }
                

                int[] newBestSol = new int[bestSol.length]; //Esta es la mejor solución hasta ahora
                char[]nuevaMejSol=new char[MejSol.length];
                
                System.arraycopy(bestSol, 0, newBestSol, 0, newBestSol.length);
                System.arraycopy(MejSol, 0, nuevaMejSol, 0, nuevaMejSol.length);
                
                newBestSol = IntercambiarOperInt(i, j, actSolucion); //Intentar intercambiar ciudades i y j
                nuevaMejSol=IntercambiarOperChar(i,j,iniSolucion);
                
                //tal vez obtengamos una mejor solucion
                
            
               int newBestCost = viajero.FunctionObjetivo(newBestSol);
                 if(newBestCost==10){//pruebas
                  int b=0;
                }


           //     if ((newBestCost < bestCost || primerVecino)) { 
                  if ((newBestCost < bestCost || primerVecino) && tabuList.tabuList[i][j] == 0)  {//Si conseguimos una mejor solucion la guardamos
                    primerVecino = false;
                    ciudad1 = i;
                    ciudad2 = j;
                    System.arraycopy(newBestSol, 0, bestSol, 0, newBestSol.length);
                    System.arraycopy(nuevaMejSol, 0, MejSol, 0, nuevaMejSol.length);
                    bestCost = newBestCost;
                } else if((newBestCost < bestCost || primerVecino) && tabuList.tabuList[i][j] < 5) {//Oscilacion estratégica: 
                    //criterio elemental como es “si un movimiento produce una solución mejor que cualquier otra 
                    //obtenida hasta el momento lo efectuamos aunque sea tabú”.
                    tabuList.InvMovimientoTabu(i, j);
                    primerVecino = false;
                    ciudad1 = i;
                    ciudad2 = j;
                    System.arraycopy(newBestSol, 0, bestSol, 0, newBestSol.length);
                    System.arraycopy(nuevaMejSol, 0, MejSol, 0, nuevaMejSol.length);
                    bestCost = newBestCost;
                }


            }
        }

        if (ciudad1 != 0) {
            tabuList.decrementoTabu();
//Vamos disminuyendo el Tabu de nuestra lista para en un futuro volver a 
            //tomar en cuenta a este vecino
            tabuList.MovimientoTabu(ciudad1, ciudad2);
        }
        System.arraycopy(bestSol, 0, actSolucion, 0, bestSol.length);
        System.arraycopy(MejSol, 0, iniSolucion, 0, MejSol.length);


    }
 
 
    
     public static int[] invArrayInt(int[] n) {//Inveritir Array
    int aux;
    for (int i = 0; i < n.length / 2; i++) {
        aux = n[i];
        n[i] = n[n.length - 1 - i];
        n[n.length - 1 - i] = aux;
    }
 
    return n;
}
 public static char[] invArrayChar(char[] n) {//Inveritir Array
    char aux;
    for (int i = 0; i < n.length / 2; i++) {
        aux = n[i];
        n[i] = n[n.length - 1 - i];
        n[n.length - 1 - i] = aux;
    }
 
    return n;
}
 //intercambiar dos ciudades
    public static int[] IntercambiarOperInt(int ciudad1, int ciudad2, int[] solucion) {
        int temp= solucion[ciudad1];
        solucion[ciudad1] = solucion[ciudad2];
        solucion[ciudad2] = temp;
        return solucion;
    }
    //intercambiar dos ciudades
    public static char[] IntercambiarOperChar(int ciudad1, int ciudad2, char[] solucion) {
        char temp = solucion[ciudad1];
        solucion[ciudad1] = solucion[ciudad2];
        solucion[ciudad2] = temp;
        return solucion;
    }
    public static void imprimirSolucionInt(int[] solucion) {
        for (int i = 0; i < solucion.length; i++) {
            System.out.print(solucion[i] + " ");
        }
        System.out.println();
    }
     public static void imprimirSolucionChar(char[] solucion) {
        for (int i = 0; i < solucion.length; i++) {
            System.out.print(solucion[i] + " ");
        }
        System.out.println();
    }
}
