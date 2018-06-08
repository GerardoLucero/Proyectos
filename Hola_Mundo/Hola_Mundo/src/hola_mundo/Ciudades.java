
package hola_mundo;

public class Ciudades {
    private int id;
    private int[] distancia;
    private char letra;
    public Ciudades(int id, int[]distancia, char letra){
        this.id = id;
        this.distancia = distancia;
        this.letra = letra;
    }
    public int getId() {
        return id;
    }
    public int[] getDistancia() {
        return distancia;
    }
    public char getLetra(){
        return letra;
    }
}
