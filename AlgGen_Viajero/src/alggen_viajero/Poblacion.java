/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alggen_viajero;

import java.util.Random;

public class Poblacion {
    
    public Solucion [] s;
    public Solucion [] spadre;
    
    
    public Poblacion() {
        s = new Solucion[100];
        spadre = new Solucion[100];
        for (int i = 0; i < s.length ; i++) {
            s[i] = new Solucion();
            spadre[i] = new Solucion();
        }
    }
    
    public void MutarPoblacion(){
        for(int i = 1; i < s.length; i++){
            if((Math.random()*100) < 15)
                s[i].Mutar();
                
        }
    }
     
    public void CruzarPoblacion(float [][] dat){
//        for(int i = 0; i < s.length; i++){
//            if(i != 0)
//                if(i % 2 == 0)
//                    s[i]= spadre[i-1].CruzaPar(spadre[i]);
//                else
//                    s[i]= spadre[i-1].CruzaImpar(spadre[i]);
//        }
//        
        for(int i = 0; i < s.length; i++){
            if(i != 0)
                s[i]= spadre[i-1].Cruza(spadre[i]);
        }
        EvaluarPoblacion(dat);
    }
    public void EvaluarPoblacion(float [][] dat){
        for(int i = 0; i < s.length; i++){
            s[i].EvaluarAptitu(dat);
        }
    }
    
    public Solucion MejordeDos(Solucion a,Solucion b){
        if(a.aptitud < b.aptitud)
            return a;
        else
            return b;
    }
    
    public float promedioPoblacion(){
        float p =0;
        for (int i = 0; i < s.length; i++) {
            p+=s[i].aptitud;
        }
        return p/s.length;
    }
    
    public boolean SeleccionarValor(float dif){
        Random rnd = new Random();
        if((rnd.nextFloat() * dif + 0) <= dif)
            return true;
        else
            return false;
    }
    public void Seleccion(int x){
        
        if(x == 0){
            float promedio = promedioPoblacion();
            int n = 0;
            for (int i = 0; i < s.length; i++) {
                s[i].ve = s[i].aptitud/ promedio;
                s[i].enteros = (int) s[i].ve;
                int v = s[i].enteros;
                while(v != 0){
                    spadre[n] = s[i].Copia();
                    v--;
                    n++;
                }
                s[i].dif = s[i].ve - s[i].enteros;
            }
            int ic = n;
            while(n < spadre.length){
                if(ic == s.length)
                    ic = 0;
                if(SeleccionarValor(s[ic].dif)){
                    spadre[n] = s[ic].Copia();
                    n++;
                }
                ic++;
            } 
        }
        else
        {
            int n = 0;
        for(int i = 0; i < s.length; i+=2){
            spadre[n] = MejordeDos(s[i], s[i+1]).Copia(); 
            n++;
        }
        Mezclar();
        
        for(int i = 0; i < s.length; i+=2){
            spadre[n] = MejordeDos(s[i], s[i+1]).Copia(); 
            n++;
        }
            
        }
    }
    public void Mezclar(){
        Random azar1 = new Random();
        for(int j = 1; j < s.length ; j++)
        {
            int r0, r1;
            r0 = (int)(azar1.nextDouble() * 98 + 1);
            r1 = (int)(azar1.nextDouble() * 98 + 1);
            
            while(r0 == 0)
                r0 = (int)(azar1.nextDouble() * 98 + 1);
            
            while(r1 == 0)
                r1 = (int)(azar1.nextDouble() * 98 + 1);

            Solucion temp = s[r0].Copia();
            s[r0] = s[r1].Copia();
            s[r1] = temp;

        }
    }
    
    
    public void Elitismo(){      
        int n = -1;
        Solucion temp = s[0];
        for(int i= 1; i < s.length; i++){
            if(temp.aptitud > s[i].aptitud)
            {
                temp = s[i].Copia();
                n = i;
            }
        }
        if(n > 0){ 
            s[n] = s[0].Copia();
            s[0] = temp;
        }
    }
    
}
