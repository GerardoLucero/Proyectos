package alggen_viajero;

public class Genetico {
    
    public float Datos [][];
    public Poblacion p;
    
    public Genetico(){
        Datos = new float[48][48];
        CalcularDistancias(Datos);
        p = new Poblacion();
        p.EvaluarPoblacion(Datos);
        p.Elitismo();
    }
    
    public void CalcularDistancias(float [][] dat){
        Capitales capi = new Capitales();
        for (int i = 0; i < 48; i++) {
            for (int j = 0; j < 48; j++) {
                if(i != j)
                    dat[i][j]= distacia(i,j,capi);
            }
        }
    }
    
    public float distacia(int i, int j, Capitales c){
        return (float) Math.sqrt( Math.pow((c.c[j].x - c.c[i].x), 2) +  Math.pow((c.c[j].y - c.c[i].y), 2));
    }
    
    public void Generar(){
        int i = 0;
        while(i < 100){
            p.Seleccion(1);
            p.CruzarPoblacion(Datos);
            p.MutarPoblacion();
            p.EvaluarPoblacion(Datos);
            p.Elitismo();
            i++;
        }
    }
    
}
