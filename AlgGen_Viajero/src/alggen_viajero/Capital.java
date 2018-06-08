package alggen_viajero;


public class Capital {
    
    public int n;
    public float x;
    public float y;
    
    
    
    public Capital(){
        n = -1;
        x = -1;
        y = -1;
    }
    
    public Capital(int a, float b, float c){
        n = a;
        x = b;
        y = c;
    }
    
    public  Capital Copia(){
        return new Capital(n,x,y);
    }

    
    
}
