package alggen_viajero;

import java.util.Random;

public class Solucion {
    
    int ind [];
    float aptitud;
    float ve;
    int enteros;
    float dif;
    
        
    public Solucion(){
        Capitales capi = new Capitales();
        ind = new int[48];
        ind = capi.Revolver();
        aptitud = 0;
        ve = 0;
        enteros = 0;
        dif = 0;
    }
    public Solucion(int a){
        ind = new int[48];
        for (int i = 0; i < ind.length; i++) {
            ind[i] = a;
        }
        aptitud = 0;
        ve = 0;
        enteros = 0;
        dif = 0;
    }
    
    public Solucion(int []ip, float ap, float v, int e, float d ){
        ind = new int[48];
        for (int i = 0; i < ind.length; i++) {
            ind[i] = ip[i];
        }
        aptitud = ap;
        ve = v;
        enteros = e;
        dif = d;
    }
    
    public int NoRepeat(int [] n, int i){
        int temp;
        Random rnd = new  Random();
        boolean v;
        do{
            v = false;
            temp = (int)(rnd.nextDouble() * 48 + 0);
            for(int j = 0; j < i; j++){
                if(temp == n[j]){
                    v = true;
                    break;
                }
            }
        }while(v);
        return temp;
    }
    
    public Solucion Copia(){
        return new Solucion(ind, aptitud, ve, enteros, dif);
    }
    
    public void Mutar(){
        Random azar1 = new Random();
        
        int r0, r1;
        r0 = (int)(azar1.nextDouble() * 48 + 0);
        r1 = (int)(azar1.nextDouble() * 48 + 0);
        
        while(r0 == r1)
            r0 = (int)(azar1.nextDouble() * 48 + 0);
        
      Solucion a = this.Copia();
      ind[r0] = a.ind[r1];
      ind[r1] = a.ind[r0];
    }
    
    public Solucion Cruza(Solucion s){   
        Solucion a = this.Copia();
        Solucion b = s.Copia();
        Solucion h = new Solucion(-1);
        Random azar1 = new Random();
        int r0, r1;

        r0 = (int)(azar1.nextDouble() * 48 + 0);
        r1 = (int)(azar1.nextDouble() * 48 + 0);
        
        while(r0 == r1)
            r0 = (int)(azar1.nextDouble() * 48 + 0);
        
        int i, e;
        if(r0 < r1){
            i = r0;
            e = r1;
        }
        else{
            i = r1;
            e = r0;
        }

        for (int j = i; j < e ; j++){
             h.ind[j] = a.ind[j];
            for (int k = 0; k < b.ind.length; k++) {
                if(b.ind[k] == a.ind[j] )
                     b.ind[k] = -1;
            }
        }

        for (int j = 0; j < b.ind.length ; j++) {
            if(b.ind[j] != -1){
                for (int k = 0; k < h.ind.length; k++) {
                    if(h.ind[k] == -1){
                        h.ind[k] = b.ind[j];
                        break;
                    }
                }
            }
        }
        
        return h;
    }
    
     public Solucion CruzaPar(Solucion s){   
        Solucion a = this.Copia();
        Solucion b = s.Copia();
        Solucion h = new Solucion(-1);
        Random azar1 = new Random();
        int r0, r1;
        r0 = 0;
        r1 = 23;
//        r0 = (int)(azar1.nextDouble() * 48 + 0);
//        r1 = (int)(azar1.nextDouble() * 48 + 0);
//        
//        while(r0 == r1)
//            r0 = (int)(azar1.nextDouble() * 48 + 0);
        
        int i, e;
        if(r0 < r1){
            i = r0;
            e = r1;
        }
        else{
            i = r1;
            e = r0;
        }

        for (int j = i; j < e ; j++){
             h.ind[j] = a.ind[j];
            for (int k = 0; k < b.ind.length; k++) {
                if(b.ind[k] == a.ind[j] )
                     b.ind[k] = -1;
            }
        }

        for (int j = 0; j < b.ind.length ; j++) {
            if(b.ind[j] != -1){
                for (int k = 0; k < h.ind.length; k++) {
                    if(h.ind[k] == -1){
                        h.ind[k] = b.ind[j];
                        break;
                    }
                }
            }
        }
        
        return h;
    }
      public Solucion CruzaImpar(Solucion s){   
        Solucion a = this.Copia();
        Solucion b = s.Copia();
        Solucion h = new Solucion(-1);
        Random azar1 = new Random();
        int r0, r1;
        r0 = 24;
        r1 = 47;
//        r0 = (int)(azar1.nextDouble() * 48 + 0);
//        r1 = (int)(azar1.nextDouble() * 48 + 0);
//        
//        while(r0 == r1)
//            r0 = (int)(azar1.nextDouble() * 48 + 0);
        
        int i, e;
        if(r0 < r1){
            i = r0;
            e = r1;
        }
        else{
            i = r1;
            e = r0;
        }

        for (int j = i; j < e ; j++){
             h.ind[j] = a.ind[j];
            for (int k = 0; k < b.ind.length; k++) {
                if(b.ind[k] == a.ind[j] )
                     b.ind[k] = -1;
            }
        }

        for (int j = 0; j < b.ind.length ; j++) {
            if(b.ind[j] != -1){
                for (int k = 0; k < h.ind.length; k++) {
                    if(h.ind[k] == -1){
                        h.ind[k] = b.ind[j];
                        break;
                    }
                }
            }
        }
        
        return h;
    }
    


    public void EvaluarAptitu(float [][] dat){
        aptitud = 0;
        for(int i = 0; i < ind.length-1; i++){
            aptitud+= dat[ind[i]][ind[i+1]];
        }
        aptitud+= dat[ind[47]][ind[0]];
    }

}
